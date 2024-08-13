package se.skltp.cooperation.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.QLogicalAddress;
import se.skltp.cooperation.domain.QServiceConsumer;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.service.ServiceConsumerCriteria;
import se.skltp.cooperation.service.ServiceConsumerService;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;

/**
 * @author Peter Merikan
 */
@Service
@Transactional(readOnly = true)
public class ServiceConsumerServiceImpl implements ServiceConsumerService {

	@Value("${APP_USE_EXPERIMENTAL_FILTER:false}")
	private boolean useExperimentalFilter;

	private final ServiceConsumerRepository serviceConsumerRepository;

	private final Logger log = LoggerFactory.getLogger(ServiceConsumerServiceImpl.class);

	@Autowired
	public ServiceConsumerServiceImpl(ServiceConsumerRepository serviceConsumerRepository) {
		this.serviceConsumerRepository = serviceConsumerRepository;
	}

	@Override
	public List<ServiceConsumer> findAll() {
		return serviceConsumerRepository.findAll();
	}

	@Override
	public List<ServiceConsumer> findAll(ServiceConsumerCriteria criteria) {
		if (criteria.isEmpty()) {
			return findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return Lists.newArrayList(serviceConsumerRepository.findAll(predicate));
		}
	}

	@Override
	public ServiceConsumer find(Long id) {
		return serviceConsumerRepository.findById(id).orElse(null);
	}

	// CANDIDATE FOR ERRONEOUS FILTERING.
	Predicate buildPredicate(ServiceConsumerCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();

		if (criteria.getConnectionPointId() != null) {
			builder.and(QServiceConsumer.serviceConsumer.connectionPoint.id
				.eq(criteria.getConnectionPointId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QServiceConsumer.serviceConsumer.cooperations.any().logicalAddress.id
				.eq(criteria.getLogicalAddressId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(
				QServiceConsumer
					.serviceConsumer
					.cooperations.any()
					.serviceContract.id.eq(
						criteria.getServiceContractId()
					)
			);
		}

		if (criteria.getServiceProducerId() != null) {

			if (this.useExperimentalFilter) {
				log.info("Using Alternate Filtering Logic for Service Producer filtering.");

				builder.and(
					QServiceConsumer
						.serviceConsumer
						.cooperations.any()
						.logicalAddress
						.serviceProductions.any()
						.serviceProducer.id.eq(
							criteria.getServiceProducerId()
						)
				);

			} else {
				log.info("Using Standard Filtering Logic for Service Producer filtering.");
				builder.and(QServiceConsumer.serviceConsumer.cooperations.any().logicalAddress.id.in(
					JPAExpressions
						.select(QLogicalAddress.logicalAddress1.id)
						.from(QLogicalAddress.logicalAddress1)
						.where(QLogicalAddress.logicalAddress1.serviceProductions.any().serviceProducer.id
							.eq(criteria.getServiceProducerId()))
				));
			}
		}

		return builder.getValue();
	}
}
