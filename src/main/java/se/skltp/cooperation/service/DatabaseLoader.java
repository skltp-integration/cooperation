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
package se.skltp.cooperation.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.InstalledContract;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.domain.ServiceDomain;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.repository.InstalledContractRepository;
import se.skltp.cooperation.repository.LogicalAddressRepository;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.repository.ServiceContractRepository;
import se.skltp.cooperation.repository.ServiceDomainRepository;
import se.skltp.cooperation.repository.ServiceProducerRepository;
import se.skltp.cooperation.repository.ServiceProductionRepository;

/**
 * Load database with test data
 *
 * @author Peter Merikan
 */

@Service
@Profile("dev")
public class DatabaseLoader {

	@Autowired
	private  ConnectionPointRepository connectionPointRepository;
	@Autowired
	private  CooperationRepository cooperationRepository;
	@Autowired
	private  LogicalAddressRepository logicalAddressRepository;
	@Autowired
	private  ServiceConsumerRepository serviceConsumerRepository;
	@Autowired
	private  ServiceContractRepository serviceContractRepository;
	@Autowired
	private  ServiceProducerRepository serviceProducerRepository;
	@Autowired
	private  ServiceProductionRepository serviceProductionRepository;
	@Autowired
	private  ServiceDomainRepository serviceDomainRepository;
	@Autowired
	private  InstalledContractRepository installedContractRepository;

	private LogicalAddress ei_logicalAddress;
	private ServiceProducer ei_update_producer;
	private ServiceConsumer serviceConsumer1;
	private ServiceConsumer serviceConsumer2;
	private ServiceConsumer serviceConsumer3;

	private LogicalAddress producer1_logicalAddress;
	private LogicalAddress producer2_logicalAddress;
	private ServiceProducer producer1;
	private ServiceProducer producer2;
	
	private ServiceContract ei_update_contract;
	private ServiceContract ei_contract;
	private ServiceDomain serviceDomain_ei;


	@PostConstruct
	private void initDatabase() {

		// Load consumers, producers and logical address existing in all
		// environments
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
		connectionPoint.setSnapshotTime(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
		connectionPointRepository.save(connectionPoint);
		loadTAKData(connectionPoint);
	}

	private void loadCommonTakDataForAllEnvironmentsInPlattform() {

		producer1_logicalAddress = new LogicalAddress();
		producer1_logicalAddress
				.setDescription("Producer1:processNotification");
		producer1_logicalAddress
				.setLogicalAddress("SE2120002825-LOGICALADDRESS1");
		logicalAddressRepository.save(producer1_logicalAddress);

		producer2_logicalAddress = new LogicalAddress();
		producer2_logicalAddress
				.setDescription("Producer2:processNotification");
		producer2_logicalAddress
				.setLogicalAddress("SE2120002825-LOGICALADDRESS2");
		logicalAddressRepository.save(producer2_logicalAddress);

		producer1 = new ServiceProducer();
		producer1.setDescription("Producent: ProcessNotification:Producer1");
		producer1.setHsaId("SE162321000032-PRODUCER1");
		serviceProducerRepository.save(producer1);

		producer2 = new ServiceProducer();
		producer2.setDescription("Producent: ProcessNotification:Producer2");
		producer2.setHsaId("SE162321000032-PRODUCER2");
		serviceProducerRepository.save(producer2);

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

		ei_update_contract = new ServiceContract();
		ei_update_contract.setName("Engagemangsindex - Update");
		ei_update_contract.setNamespace("urn:riv:itintegration:engagementindex:UpdateResponder:1");
		ei_update_contract.setMajor(1);
		ei_update_contract.setMinor(0);
		serviceContractRepository.save(ei_update_contract);

		serviceDomain_ei = new ServiceDomain();
		serviceDomain_ei.setName("EI");
		serviceDomain_ei.setNamespace("urn:riv:itintegration:engagementindex");
		serviceDomainRepository.save(serviceDomain_ei);
		
		ei_contract = new ServiceContract();
		ei_contract.setName("Engagemangsindex - ProcessNotification");
		ei_contract
				.setNamespace("urn:riv:itintegration:engagementindex:ProcessNotificationResponder:1");
		ei_contract.setMajor(1);
		ei_contract.setMinor(0);
		ei_contract.setServiceDomain(serviceDomain_ei);
		serviceContractRepository.save(ei_contract);
		

	}

	private void loadTAKData(ConnectionPoint connectionPoint) {
		
		InstalledContract installedContract = new InstalledContract();
		installedContract.setConnectionPoint(connectionPoint);
		installedContract.setServiceContract(ei_contract);
		installedContractRepository.save(installedContract);

		 installedContract = new InstalledContract();
		installedContract.setConnectionPoint(connectionPoint);
		installedContract.setServiceContract(ei_update_contract);
		installedContractRepository.save(installedContract);

		ServiceProduction ei_update_production = new ServiceProduction();
		ei_update_production.setRivtaProfile("RIVTABP21");
		ei_update_production.setPhysicalAddress("http://" + connectionPoint.getEnvironment()
				+ "/producer1/Update/v1");
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

		if (connectionPoint.getEnvironment().equals("PRODUCTION")) {

			ServiceProduction producer1_production = new ServiceProduction();
			producer1_production.setRivtaProfile("RIVTABP21");
			producer1_production.setPhysicalAddress("http://"
					+ connectionPoint.getEnvironment() + "/producer1/ProcessNotification/v1");
			producer1_production.setConnectionPoint(connectionPoint);
			producer1_production
					.setLogicalAddress(producer1_logicalAddress);
			producer1_production
					.setServiceProducer(producer1);
			producer1_production
					.setServiceContract(ei_contract);
			serviceProductionRepository.save(producer1_production);

			ServiceProduction producer2_production = new ServiceProduction();
			producer2_production.setRivtaProfile("RIVTABP21");
			producer2_production.setPhysicalAddress(
					connectionPoint.getEnvironment() + "123455678/");
			producer2_production.setConnectionPoint(connectionPoint);
			producer2_production
					.setLogicalAddress(producer2_logicalAddress);
			producer2_production
					.setServiceProducer(producer2);
			producer2_production
					.setServiceContract(ei_contract);
			serviceProductionRepository.save(producer2_production);

			Cooperation cooperation3 = new Cooperation();
			cooperation3.setConnectionPoint(connectionPoint);
			cooperation3.setLogicalAddress(producer1_logicalAddress);
			cooperation3.setServiceContract(ei_contract);
			cooperation3.setServiceConsumer(serviceConsumer3);
			cooperationRepository.save(cooperation3);
			cooperation3 = new Cooperation();
			cooperation3.setConnectionPoint(connectionPoint);
			cooperation3.setLogicalAddress(producer2_logicalAddress);
			cooperation3.setServiceContract(ei_contract);
			cooperation3.setServiceConsumer(serviceConsumer3);
			cooperationRepository.save(cooperation3);

		}
	}

}
