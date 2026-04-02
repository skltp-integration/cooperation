/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

/**
 * @author Jan Västernäs
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ConnectionPointServiceImplIntegrationTest {

	@Autowired
	private ConnectionPointService uut;

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
	public void setUp() throws Exception {
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
	public void tearDown() throws Exception {
		util.deleteAll();
	}

	@Test
	public void findAll_shouldReturnAll() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null, null,
				null, null);
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(2, result.size());

	}

	@Test
	public void findByEnvironment() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria("PROD", null, null, null,
				null, null);
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("NTJP", result.get(0).getPlatform());
	}

	@Test
	public void findByPlatform() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, "NTJP", null, null,
				null, null);
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(2, result.size());
	}

	@Test
	public void findByPlatform_noHits() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, "XYZ", null, null,
				null, null);
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(0, result.size());
	}

	@Test
	public void findByServiceConsumerId() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null,
				serviceConsumer1.getId(), null, null, null);
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.get(0).getEnvironment());
	}

	@Test
	public void findByLogicalAdressId() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null,
				logicalAddress1.getId(), null, null);
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.get(0).getEnvironment());
	}

	@Test
	public void findByServiceContractId() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null, null,
				serviceContract1.getId(), null);
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.get(0).getEnvironment());
	}

	@Test
	public void findByServiceProducerId() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null, null,null,
				serviceProducer1.getId());
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.get(0).getEnvironment());
	}

	@Test
	public void findByMultipleCriteria() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, "NTJP", null, logicalAddress1.getId(),serviceContract1.getId(),
				serviceProducer1.getId());
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals("TEST", result.get(0).getEnvironment());
	}

}
