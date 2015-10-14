/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.cooperation.web.rest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.domain.ServiceDomain;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.repository.LogicalAddressRepository;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.repository.ServiceContractRepository;
import se.skltp.cooperation.repository.ServiceDomainRepository;
import se.skltp.cooperation.repository.ServiceProducerRepository;
import se.skltp.cooperation.repository.ServiceProductionRepository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for testing REST controllers.
 *
 * @author Peter Merikan
 */
@Service
public class TestUtil {

	@Autowired
	private ConnectionPointRepository connectionPointRepository;
	@Autowired
	private CooperationRepository cooperationRepository;
	@Autowired
	private LogicalAddressRepository logicalAddressRepository;
	@Autowired
	private ServiceConsumerRepository serviceConsumerRepository;
	@Autowired
	private ServiceContractRepository serviceContractRepository;
	@Autowired
	private ServiceProducerRepository serviceProducerRepository;
	@Autowired
	private ServiceProductionRepository serviceProductionRepository;
	@Autowired
	private ServiceDomainRepository serviceDomainRepository;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

	public ConnectionPoint createConnectionPoint(String platform, String environment) {
		ConnectionPoint connectionPoint = new ConnectionPoint();
		connectionPoint.setPlatform(platform);
		connectionPoint.setEnvironment(environment);
		connectionPoint.setSnapshotTime(new GregorianCalendar(2014, Calendar.FEBRUARY, 11)
				.getTime());
		connectionPointRepository.save(connectionPoint);
		return connectionPoint;
	}

	public ServiceConsumer createServiceConsumer(String description, String hsaId) {
		ServiceConsumer serviceConsumer = new ServiceConsumer();
		serviceConsumer.setDescription(description);
		serviceConsumer.setHsaId(hsaId);
		serviceConsumerRepository.save(serviceConsumer);
		return serviceConsumer;
	}

	public Cooperation createCooperation(ConnectionPoint connectionPoint,
			LogicalAddress logicalAddress, ServiceContract serviceContract,
			ServiceConsumer serviceConsumer) {
		Cooperation cooperation = new Cooperation();
		cooperation.setConnectionPoint(connectionPoint);
		cooperation.setLogicalAddress(logicalAddress);
		cooperation.setServiceContract(serviceContract);
		cooperation.setServiceConsumer(serviceConsumer);
		cooperationRepository.save(cooperation);
		return cooperation;
	}

	public ServiceProduction createServiceProduction(String rivtaProfile, String physicalAdress,
			ConnectionPoint connectionPoint, LogicalAddress logicalAddress,
			ServiceProducer serviceProducer, ServiceContract serviceContract) {
		ServiceProduction serviceProduction = new ServiceProduction();
		serviceProduction.setRivtaProfile(rivtaProfile);
		serviceProduction.setPhysicalAddress(physicalAdress);
		serviceProduction.setConnectionPoint(connectionPoint);
		serviceProduction.setLogicalAddress(logicalAddress);
		serviceProduction.setServiceProducer(serviceProducer);
		serviceProduction.setServiceContract(serviceContract);
		serviceProductionRepository.save(serviceProduction);
		return serviceProduction;

	}

	public LogicalAddress createLogicalAddress(String decription, String logicalAdress) {
		LogicalAddress logicalAddress = new LogicalAddress();
		logicalAddress.setDescription(decription);
		logicalAddress.setLogicalAddress(logicalAdress);
		logicalAddressRepository.save(logicalAddress);
		return logicalAddress;
	}

	public ServiceContract createServiceContract(String name, String namespace, Integer major,
			Integer minor) {
		ServiceContract serviceContract = new ServiceContract();
		serviceContract.setName(name);
		serviceContract.setNamespace(namespace);
		serviceContract.setMajor(major);
		serviceContract.setMinor(minor);
		serviceContractRepository.save(serviceContract);
		return serviceContract;
	}
	public ServiceContract createServiceContract(String name, String namespace, Integer major,
			Integer minor,ServiceDomain serviceDomain) {
		ServiceContract serviceContract = new ServiceContract();
		serviceContract.setName(name);
		serviceContract.setNamespace(namespace);
		serviceContract.setMajor(major);
		serviceContract.setMinor(minor);
		serviceContract.setServiceDomain(serviceDomain);
		serviceContractRepository.save(serviceContract);
		return serviceContract;
	}

	public ServiceDomain createServiceDomain(String name, String namespace) {
		ServiceDomain serviceDomain = new ServiceDomain();
		serviceDomain.setName(name);
		serviceDomain.setNamespace(namespace);
		serviceDomainRepository.save(serviceDomain);
		return serviceDomain;
	}

	public ServiceProducer createServiceProducer(String description, String hsaId) {
		ServiceProducer serviceProducer = new ServiceProducer();
		serviceProducer.setDescription(description);
		serviceProducer.setHsaId(hsaId);
		serviceProducerRepository.save(serviceProducer);
		return serviceProducer;
	}

	public void deleteAll() {
		cooperationRepository.deleteAll();
		serviceProductionRepository.deleteAll();
		serviceProducerRepository.deleteAll();
		connectionPointRepository.deleteAll();
		logicalAddressRepository.deleteAll();
		serviceConsumerRepository.deleteAll();
		serviceContractRepository.deleteAll();
		serviceDomainRepository.deleteAll();

	}

}
