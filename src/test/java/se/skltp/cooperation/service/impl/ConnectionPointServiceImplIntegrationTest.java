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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.service.ConnectionPointCriteria;
import se.skltp.cooperation.service.ConnectionPointService;
import se.skltp.cooperation.api.TestUtil;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
class ConnectionPointServiceImplIntegrationTest {

	@Autowired
	private ConnectionPointService connPtSer;

	@Autowired
	private TestUtil util;

	ConnectionPoint connectionPoint1;
	ConnectionPoint connectionPoint2;
	ServiceConsumer serviceConsumer1;
	LogicalAddress logicalAddress1;
	ServiceContract serviceContract1;
	Cooperation cooperation1;
	ServiceProduction serviceProduction1;
	ServiceProducer serviceProducer1;

	@BeforeEach
	void setUp() {
		util.deleteAll(); // Make sure DB is clean before injecting test data.
		connectionPoint1 = util.createConnectionPoint("NTJP", "TEST");
		connectionPoint2 = util.createConnectionPoint("NTJP", "PROD");
		serviceConsumer1 = util.createServiceConsumer("description", "hsaId", connectionPoint1);
		logicalAddress1 = util.createLogicalAddress("","");
		serviceContract1 = util.createServiceContract("name", "namespace", 0, 0);
		cooperation1 = util.createCooperation(connectionPoint1, logicalAddress1, serviceContract1,
				serviceConsumer1);
		serviceProducer1 = util.createServiceProducer("description", "hsaId", connectionPoint1);
		serviceProduction1 = util.createServiceProduction("rivtaProfile", "physicalAdress",
				connectionPoint1, logicalAddress1, serviceProducer1, serviceContract1);
	}

	@AfterEach
	void tearDown() {
		util.deleteAll();
	}

	@ParameterizedTest
	@CsvSource(value = {"NULL, 2", "NTJP, 2", "XYZ, 0"},
		nullValues = "NULL")
	void findAll_testVariousPlatformCriteria(String platform, int expectedCount) {
		ConnectionPointCriteria criteria =
			new ConnectionPointCriteria(null, platform, null, null, null, null);

		assertEquals(expectedCount, connPtSer.findAll(criteria).size());
	}

	@Test
	void findByEnvironment() {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria("PROD", null, null, null,
				null, null);
		List<ConnectionPoint> result = connPtSer.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("NTJP", result.getFirst().getPlatform());
	}

	@Test
	void findByServiceConsumerId() {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null,
				serviceConsumer1.getId(), null, null, null);
		List<ConnectionPoint> result = connPtSer.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.getFirst().getEnvironment());
	}

	@Test
	void findByLogicalAdressId() {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null,
				logicalAddress1.getId(), null, null);
		List<ConnectionPoint> result = connPtSer.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.getFirst().getEnvironment());
	}

	@Test
	void findByServiceContractId() {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null, null,
				serviceContract1.getId(), null);
		List<ConnectionPoint> result = connPtSer.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.getFirst().getEnvironment());
	}

	@Test
	void findByServiceProducerId() {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null, null,null,
				serviceProducer1.getId());
		List<ConnectionPoint> result = connPtSer.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.getFirst().getEnvironment());
	}

	@Test
	void findByMultipleCriteria() {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, "NTJP", null, logicalAddress1.getId(),serviceContract1.getId(),
				serviceProducer1.getId());
		List<ConnectionPoint> result = connPtSer.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.getFirst().getEnvironment());
	}
}
