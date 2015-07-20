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

        // NTjP - Development

        ConnectionPoint connectionPoint = new ConnectionPoint();
        connectionPoint.setPlatform("NTjP");
        connectionPoint.setEnvironment("development");
        connectionPointRepository.save(connectionPoint);

        loadTAKData(connectionPoint);


    }

    private void loadTAKData(ConnectionPoint connectionPoint) {
        String label = "[" + connectionPoint.getPlatform() + "-"+ connectionPoint.getEnvironment() + "]";

        //Contract: Update
        ServiceContract ei_update_contract = new ServiceContract();
        ei_update_contract.setName("Engagemangsindex - Update");
        ei_update_contract.setNamespace("urn:riv:itintegration:engagementindex:UpdateResponder:1");
        ei_update_contract.setMajor(1);
        ei_update_contract.setMinor(0);
        serviceContractRepository.save(ei_update_contract);


        LogicalAddress ei_update_logicalAddress = new LogicalAddress();
        ei_update_logicalAddress.setDescription(label + " EI-update ");
        logicalAddressRepository.save(ei_update_logicalAddress);

        ServiceProducer ei_update_producer = new ServiceProducer();
        ei_update_producer.setDescription(label + " Producent: Engagemangsidex - Update");
        serviceProducerRepository.save(ei_update_producer);

        ServiceProduction ei_update_production = new ServiceProduction();
        ei_update_production.setRivtaProfile("RIVTABP21");
        ei_update_production.setPhysicalAddress("PhysicalAddress:Producent: Engagemangsidex - Update");
        ei_update_production.setConnectionPoint(connectionPoint);
        ei_update_production.setLogicalAddress(ei_update_logicalAddress);
        ei_update_production.setServiceProducer(ei_update_producer);
        ei_update_production.setServiceContract(ei_update_contract);
        serviceProductionRepository.save(ei_update_production);

        ServiceConsumer serviceConsumer1 = new ServiceConsumer();
        serviceConsumer1.setDescription(label + " serviceConsumer 1");
        serviceConsumer1.setHsaId("hsaid");
        serviceConsumer1.setConnectionPoint(connectionPoint);
        serviceConsumerRepository.save(serviceConsumer1);

        Cooperation cooperation = new Cooperation();
        cooperation.setConnectionPoint(connectionPoint);
        cooperation.setLogicalAddress(ei_update_logicalAddress);
        cooperation.setServiceContract(ei_update_contract);
        cooperation.setServiceConsumer(serviceConsumer1);
        cooperationRepository.save(cooperation);

        ServiceConsumer serviceConsumer2 = new ServiceConsumer();
        serviceConsumer2.setDescription(label + " serviceConsumer 2");
        serviceConsumer2.setHsaId("hsaid");
        serviceConsumer2.setConnectionPoint(connectionPoint);
        serviceConsumerRepository.save(serviceConsumer2);

        Cooperation cooperation2 = new Cooperation();
        cooperation2.setConnectionPoint(connectionPoint);
        cooperation2.setLogicalAddress(ei_update_logicalAddress);
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

        LogicalAddress processNotification_producer1_logicalAddress = new LogicalAddress();
        processNotification_producer1_logicalAddress.setDescription(label + " producer1:processNotification");
        logicalAddressRepository.save(processNotification_producer1_logicalAddress);

        ServiceProducer processNotification_producer1 = new ServiceProducer();
        processNotification_producer1.setDescription(label + " ProcessNotification:Producer1");
        serviceProducerRepository.save(processNotification_producer1);

        ServiceProduction processNotification_producer1_production = new ServiceProduction();
        processNotification_producer1_production.setRivtaProfile("RIVTABP21");
        processNotification_producer1_production.setPhysicalAddress("PhysicalAddress - Producent1: ProcessNotification");
        processNotification_producer1_production.setConnectionPoint(connectionPoint);
        processNotification_producer1_production.setLogicalAddress(processNotification_producer1_logicalAddress);
        processNotification_producer1_production.setServiceProducer(processNotification_producer1);
        processNotification_producer1_production.setServiceContract(ei_processNotification_contract);
        serviceProductionRepository.save(processNotification_producer1_production);

        LogicalAddress processNotification_producer2_logicalAddress = new LogicalAddress();
        processNotification_producer1_logicalAddress.setDescription(label + " producer2:processNotification");
        logicalAddressRepository.save(processNotification_producer2_logicalAddress);

        ServiceProducer processNotification_producer2 = new ServiceProducer();
        processNotification_producer1.setDescription(label + " ProcessNotification:Producer2");
        serviceProducerRepository.save(processNotification_producer2);

        ServiceProduction processNotification_producer2_production = new ServiceProduction();
        processNotification_producer1_production.setRivtaProfile("RIVTABP21");
        processNotification_producer1_production.setPhysicalAddress("PhysicalAddress - Producent2: ProcessNotification");
        processNotification_producer1_production.setConnectionPoint(connectionPoint);
        processNotification_producer1_production.setLogicalAddress(processNotification_producer2_logicalAddress);
        processNotification_producer1_production.setServiceProducer(processNotification_producer2);
        processNotification_producer1_production.setServiceContract(ei_processNotification_contract);
        serviceProductionRepository.save(processNotification_producer2_production);

        ServiceConsumer serviceConsumer3 = new ServiceConsumer();
        serviceConsumer3.setDescription(label + " serviceConsumer 3");
        serviceConsumer3.setHsaId("hsaid");
        serviceConsumer3.setConnectionPoint(connectionPoint);
        serviceConsumerRepository.save(serviceConsumer3);

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
