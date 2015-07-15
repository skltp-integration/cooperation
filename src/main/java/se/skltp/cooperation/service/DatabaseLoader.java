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
        ConnectionPoint connectionPoint = new ConnectionPoint();
        connectionPoint.setPlatform("NTjP");
        connectionPoint.setEnvironment("development");
        connectionPointRepository.save(connectionPoint);

        LogicalAddress logicalAddress = new LogicalAddress();
        logicalAddress.setDescription("logiskadress");
        logicalAddressRepository.save(logicalAddress);

        ServiceContract serviceContract = new ServiceContract();
        serviceContract.setName("Engagemangsindex - Update");
        serviceContract.setNamespace("urn:riv:itintegration:engagementindex:UpdateResponder:1");
        serviceContract.setMajor(2);
        serviceContract.setMinor(1);
        serviceContractRepository.save(serviceContract);

        ServiceProducer serviceProducer = new ServiceProducer();
        serviceProducer.setDescription("Producent: Engagemangsidex - Update");
        serviceProducerRepository.save(serviceProducer);

        ServiceProduction serviceProduction = new ServiceProduction();
        serviceProduction.setRivtaProfile("RIVTABP21");
        serviceProduction.setPhysicalAddress("PhysicalAddress");
        serviceProduction.setConnectionPoint(connectionPoint);
        serviceProduction.setLogicalAddress(logicalAddress);
        serviceProduction.setServiceProducer(serviceProducer);
        serviceProduction.setServiceContract(serviceContract);
        serviceProductionRepository.save(serviceProduction);

        ServiceConsumer serviceConsumer = new ServiceConsumer();
        serviceConsumer.setDescription("serviceConsumer");
        serviceConsumer.setHsaId("hsaid");
        serviceConsumer.setConnectionPoint(connectionPoint);
        serviceConsumerRepository.save(serviceConsumer);

        Cooperation cooperation = new Cooperation();
        cooperation.setConnectionPoint(connectionPoint);
        cooperation.setLogicalAddress(logicalAddress);
        cooperation.setServiceContract(serviceContract);
        cooperation.setServiceConsumer(serviceConsumer);
        cooperationRepository.save(cooperation);

    }

}
