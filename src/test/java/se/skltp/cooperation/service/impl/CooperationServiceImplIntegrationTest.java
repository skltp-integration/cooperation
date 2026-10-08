/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.domain.ServiceDomain;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.service.CooperationCriteria;
import se.skltp.cooperation.service.CooperationService;
import se.skltp.cooperation.api.TestUtil;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
class CooperationServiceImplIntegrationTest {

	@Autowired
	private CooperationService coopSrv;

	@Autowired
	private TestUtil util;

	ConnectionPoint connectionPoint1;
	ConnectionPoint connectionPoint2;
	ServiceConsumer serviceConsumer1;
	ServiceConsumer serviceConsumer2;
	LogicalAddress logicalAddress1;
	LogicalAddress logicalAddress2;
	ServiceContract serviceContract1;
	ServiceContract serviceContract2;
	Cooperation cooperation1;
	Cooperation cooperation2;
	Cooperation cooperation3;
	ServiceProduction serviceProduction1;
	ServiceProducer serviceProducer1;
	ServiceDomain serviceDomain1;
	ServiceDomain serviceDomain2;

	@BeforeEach
	void setUp() {
		connectionPoint1 = util.createConnectionPoint("NTJP", "TEST");
		connectionPoint2 = util.createConnectionPoint("NTJP", "PROD");
		serviceConsumer1 = util.createServiceConsumer("consumer1", "hsaId1",connectionPoint1);
		serviceConsumer2 = util.createServiceConsumer("consumer2", "hsaId2",connectionPoint2);
		logicalAddress1 = util.createLogicalAddress("description1", "adress1");
		logicalAddress2 = util.createLogicalAddress("description2", "adress2");

		serviceDomain1 = util.createServiceDomain("domain1", "namespace1");
		serviceDomain2 = util.createServiceDomain("domain2", "namespace2");

		serviceContract1 = util.createServiceContract("name1", "namespace1", 1, 0, serviceDomain1);
		serviceContract2 = util.createServiceContract("name2", "namespace2", 2, 0, serviceDomain2);
		cooperation1 = util.createCooperation(connectionPoint1, logicalAddress1, serviceContract1,
				serviceConsumer1);
		cooperation2 = util.createCooperation(connectionPoint2, logicalAddress2, serviceContract2,
				serviceConsumer2);
		cooperation3 = util.createCooperation(connectionPoint1, logicalAddress2, serviceContract1,
				serviceConsumer2);
		serviceProducer1 = util.createServiceProducer("description", "hsaId",connectionPoint1);
		serviceProduction1 = util.createServiceProduction("rivtaProfile", "physicalAdress",
				connectionPoint1, logicalAddress1, serviceProducer1, serviceContract1);
	}

	@AfterEach
	void tearDown() {
		util.deleteAll();
	}

	@Test
	void findAll_shouldReturnAll() {

		CooperationCriteria criteria = new CooperationCriteria(null, null, null, null, null);
		List<Cooperation> result = coopSrv.findAll(criteria);
		assertEquals(3, result.size());

	}

	@Test
	void findByServiceConsumerId() {

		CooperationCriteria criteria = new CooperationCriteria(serviceConsumer1.getId(), null,
				null, null, null);
		List<Cooperation> result = coopSrv.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(cooperation1.getId(), result.getFirst().getId());

		criteria = new CooperationCriteria(serviceConsumer2.getId(), null, null, null, null);
		result = coopSrv.findAll(criteria);
		assertEquals(2, result.size());
	}

	@Test
	void findByServiceConsumerId_noHits() {

		CooperationCriteria criteria = new CooperationCriteria(999L, null, null, null, null);
		List<Cooperation> result = coopSrv.findAll(criteria);
		assertEquals(0, result.size());

	}

	@Test
	void findByLogicalAddressId() {

		CooperationCriteria criteria = new CooperationCriteria(null, logicalAddress1.getId(), null,
				null, null);
		List<Cooperation> result = coopSrv.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(cooperation1.getId(), result.getFirst().getId());

		assertEquals(cooperation1.getId(), result.getFirst().getId());
		criteria = new CooperationCriteria(null, logicalAddress2.getId(), null, null, null);
		result = coopSrv.findAll(criteria);
		assertEquals(2, result.size());
	}

	@Test
	void findByServiceContractId() {

		CooperationCriteria criteria = new CooperationCriteria(null, null,
				serviceContract1.getId(), null, null);
		List<Cooperation> result = coopSrv.findAll(criteria);
		assertEquals(2, result.size());

		criteria = new CooperationCriteria(null, null, serviceContract2.getId(), null, null);
		result = coopSrv.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(cooperation2.getId(), result.getFirst().getId());
	}

	@Test
	void findByServiceDomainId() {

		CooperationCriteria criteria = new CooperationCriteria(null, null,
				null, null, serviceDomain1.getId());
		List<Cooperation> result = coopSrv.findAll(criteria);
		assertEquals(2, result.size());

		criteria = new CooperationCriteria(null, null, null, null, serviceDomain2.getId());
		result = coopSrv.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(cooperation2.getId(), result.getFirst().getId());
	}

	@Test
	void findByConnectionPointId() {

		CooperationCriteria criteria = new CooperationCriteria(null, null, null,
				connectionPoint1.getId(), null);
		List<Cooperation> result = coopSrv.findAll(criteria);
		assertEquals(2, result.size());

		criteria = new CooperationCriteria(null, null, null, connectionPoint2.getId(), null);
		result = coopSrv.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(cooperation2.getId(), result.getFirst().getId());
	}

}
