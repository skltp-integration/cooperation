package se.skltp.cooperation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.repository.LogicalAddressRepository;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.repository.ServiceContractRepository;
import se.skltp.cooperation.repository.ServiceProducerRepository;
import se.skltp.cooperation.repository.ServiceProductionRepository;

import javax.annotation.PostConstruct;

/**
 * Load database with test data
 *
 * @author Peter Merikan
 */

@Service
@Profile("dev")
public class DatabaseLoader {


	private final ConnectionPointRepository connectionPointRepository;
	private final CooperationRepository cooperationRepository;
	private final LogicalAddressRepository logicalAddressRepository;
	private final ServiceConsumerRepository serviceConsumerRepository;
	private final ServiceContractRepository serviceContractRepository;
	private final ServiceProducerRepository serviceProducerRepository;
	private final ServiceProductionRepository serviceProductionRepository;

	private LogicalAddress ei_logicalAddress;
	private ServiceProducer ei_update_producer;
	private ServiceConsumer serviceConsumer1;
	private ServiceConsumer serviceConsumer2;
	private ServiceConsumer serviceConsumer3;

	private LogicalAddress processNotification_producer1_logicalAddress;
	private LogicalAddress processNotification_producer2_logicalAddress;
	private ServiceProducer processNotification_producer1;
	private ServiceProducer processNotification_producer2;

	@Autowired
	public DatabaseLoader(ConnectionPointRepository connectionPointRepository,
						  CooperationRepository cooperationRepository,
						  LogicalAddressRepository logicalAddressRepository,
						  ServiceConsumerRepository serviceConsumerRepository,
						  ServiceContractRepository serviceContractRepository,
						  ServiceProducerRepository serviceProducerRepository,
						  ServiceProductionRepository serviceProductionRepository) {
		this.connectionPointRepository = connectionPointRepository;
		this.cooperationRepository = cooperationRepository;
		this.logicalAddressRepository = logicalAddressRepository;
		this.serviceConsumerRepository = serviceConsumerRepository;
		this.serviceContractRepository = serviceContractRepository;
		this.serviceProducerRepository = serviceProducerRepository;
		this.serviceProductionRepository = serviceProductionRepository;
	}

	@PostConstruct
	private void initDatabase() {

		// Load consumers, producers and logical address existing in all environments
		loadCommonTakDataForAllEnvironmentsInPlattform();

		// NTjP - Development, QA and Production
		saveDummyConnectionPoint("NTJP", "DEVELOPMENT");
		saveDummyConnectionPoint("NTJP", "QA");
		saveDummyConnectionPoint("NTJP", "PRODUCTION");
	}

	private void saveDummyConnectionPoint(String platform, String environment) {
		ConnectionPoint connectionPoint = new ConnectionPoint();
		connectionPoint.setPlatform(platform);
		connectionPoint.setEnvironment(environment);
		connectionPointRepository.save(connectionPoint);
		loadTAKData(connectionPoint);
	}

	private void loadCommonTakDataForAllEnvironmentsInPlattform() {

		processNotification_producer1_logicalAddress = new LogicalAddress();
		processNotification_producer1_logicalAddress.setDescription("Producer1:processNotification");
		processNotification_producer1_logicalAddress.setLogicalAddress("SE2120002825-LOGICALADDRESS1");
		logicalAddressRepository.save(processNotification_producer1_logicalAddress);

		processNotification_producer2_logicalAddress = new LogicalAddress();
		processNotification_producer2_logicalAddress.setDescription("Producer2:processNotification");
		processNotification_producer2_logicalAddress.setLogicalAddress("SE2120002825-LOGICALADDRESS2");
		logicalAddressRepository.save(processNotification_producer2_logicalAddress);

		processNotification_producer1 = new ServiceProducer();
		processNotification_producer1.setDescription("Producent: ProcessNotification:Producer1");
		processNotification_producer1.setHsaId("SE162321000032-PRODUCER1");
		serviceProducerRepository.save(processNotification_producer1);

		processNotification_producer2 = new ServiceProducer();
		processNotification_producer2.setDescription("Producent: ProcessNotification:Producer2");
		processNotification_producer2.setHsaId("SE162321000032-PRODUCER2");
		serviceProducerRepository.save(processNotification_producer2);

		ei_logicalAddress = new LogicalAddress();
		ei_logicalAddress.setDescription("EI-logicaladdress");
		ei_logicalAddress.setLogicalAddress("5565594230");
		logicalAddressRepository.save(ei_logicalAddress);

		ei_update_producer = new ServiceProducer();
		ei_update_producer.setDescription("Producent: Engagemangsidex - Update");
		ei_update_producer.setHsaId("SE2321000040T-PRODUCER1");
		serviceProducerRepository.save(ei_update_producer);

		serviceConsumer1 = new ServiceConsumer();
		serviceConsumer1.setDescription("ServiceConsumer 1");
		serviceConsumer1.setHsaId("SE2321000040T-CONSUMER1");
		serviceConsumerRepository.save(serviceConsumer1);

		serviceConsumer2 = new ServiceConsumer();
		serviceConsumer2.setDescription("ServiceConsumer 2");
		serviceConsumer2.setHsaId("SE2321000040T-CONSUMER2");
		serviceConsumerRepository.save(serviceConsumer2);

		serviceConsumer3 = new ServiceConsumer();
		serviceConsumer3.setDescription("ServiceConsumer 3");
		serviceConsumer3.setHsaId("SE2321000040T-CONSUMER3");
		serviceConsumerRepository.save(serviceConsumer3);

	}

	private void loadTAKData(ConnectionPoint connectionPoint) {
		String label = "[" + connectionPoint.getPlatform() + "-" + connectionPoint.getEnvironment() + "]";

		//Contract: Update
		ServiceContract ei_update_contract = new ServiceContract();
		ei_update_contract.setName("Engagemangsindex - Update");
		ei_update_contract.setNamespace("urn:riv:itintegration:engagementindex:UpdateResponder:1");
		ei_update_contract.setMajor(1);
		ei_update_contract.setMinor(0);
		serviceContractRepository.save(ei_update_contract);

		ServiceProduction ei_update_production = new ServiceProduction();
		ei_update_production.setRivtaProfile("RIVTABP21");
		ei_update_production.setPhysicalAddress("http://" + connectionPoint.getEnvironment() + "/producer1/Update/v1");
		ei_update_production.setConnectionPoint(connectionPoint);
		ei_update_production.setLogicalAddress(ei_logicalAddress);
		ei_update_production.setServiceProducer(ei_update_producer);
		ei_update_production.setServiceContract(ei_update_contract);
		serviceProductionRepository.save(ei_update_production);

		Cooperation cooperation = new Cooperation();
		cooperation.setConnectionPoint(connectionPoint);
		cooperation.setLogicalAddress(ei_logicalAddress);
		cooperation.setServiceContract(ei_update_contract);
		cooperation.setServiceConsumer(serviceConsumer1);
		cooperationRepository.save(cooperation);

		Cooperation cooperation2 = new Cooperation();
		cooperation2.setConnectionPoint(connectionPoint);
		cooperation2.setLogicalAddress(ei_logicalAddress);
		cooperation2.setServiceContract(ei_update_contract);
		cooperation2.setServiceConsumer(serviceConsumer2);
		cooperationRepository.save(cooperation2);

		//Contract: ProcessNotification
		ServiceContract ei_processNotification_contract = new ServiceContract();
		ei_processNotification_contract.setName("Engagemangsindex - ProcessNotification");
		ei_processNotification_contract.setNamespace("urn:riv:itintegration:engagementindex:ProcessNotificationResponder:1");
		ei_processNotification_contract.setMajor(1);
		ei_processNotification_contract.setMinor(0);
		serviceContractRepository.save(ei_processNotification_contract);

		ServiceProduction processNotification_producer1_production = new ServiceProduction();
		processNotification_producer1_production.setRivtaProfile("RIVTABP21");
		processNotification_producer1_production.setPhysicalAddress("http://" + connectionPoint.getEnvironment() + "/producer1/ProcessNotification/v1");
		processNotification_producer1_production.setConnectionPoint(connectionPoint);
		processNotification_producer1_production.setLogicalAddress(processNotification_producer1_logicalAddress);
		processNotification_producer1_production.setServiceProducer(processNotification_producer1);
		processNotification_producer1_production.setServiceContract(ei_processNotification_contract);
		serviceProductionRepository.save(processNotification_producer1_production);

		ServiceProduction processNotification_producer2_production = new ServiceProduction();
		processNotification_producer2_production.setRivtaProfile("RIVTABP21");
		processNotification_producer2_production.setPhysicalAddress("http://" + connectionPoint.getEnvironment() + "/producer2/ProcessNotification/v1");
		processNotification_producer2_production.setConnectionPoint(connectionPoint);
		processNotification_producer2_production.setLogicalAddress(processNotification_producer2_logicalAddress);
		processNotification_producer2_production.setServiceProducer(processNotification_producer2);
		processNotification_producer2_production.setServiceContract(ei_processNotification_contract);
		serviceProductionRepository.save(processNotification_producer2_production);

		Cooperation cooperation3 = new Cooperation();
		cooperation3.setConnectionPoint(connectionPoint);
		cooperation3.setLogicalAddress(processNotification_producer1_logicalAddress);
		cooperation3.setServiceContract(ei_processNotification_contract);
		cooperation3.setServiceConsumer(serviceConsumer3);
		cooperationRepository.save(cooperation3);
		cooperation3 = new Cooperation();
		cooperation3.setConnectionPoint(connectionPoint);
		cooperation3.setLogicalAddress(processNotification_producer2_logicalAddress);
		cooperation3.setServiceContract(ei_processNotification_contract);
		cooperation3.setServiceConsumer(serviceConsumer3);
		cooperationRepository.save(cooperation3);


	}

}
