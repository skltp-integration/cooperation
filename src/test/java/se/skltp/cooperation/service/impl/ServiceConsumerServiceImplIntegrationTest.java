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
package se.skltp.cooperation.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.service.ServiceConsumerCriteria;
import se.skltp.cooperation.service.ServiceConsumerService;
import se.skltp.cooperation.web.rest.TestUtil;

/**
 * @author Jan Västernäs
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ServiceConsumerServiceImplIntegrationTest {

	@Autowired
	private ServiceConsumerService uut;

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

	@Before
	public void setUp() throws Exception {
		connectionPoint1 = util.createConnectionPoint("NTJP", "TEST");
		connectionPoint2 = util.createConnectionPoint("NTJP", "PROD");
		serviceConsumer1 = util.createServiceConsumer("consumer1", "hsaId1");
		serviceConsumer2 = util.createServiceConsumer("consumer2", "hsaId2");
		logicalAddress1 = util.createLogicalAddress("description1", "adress1");
		logicalAddress2 = util.createLogicalAddress("description2", "adress2");
		serviceContract1 = util.createServiceContract("name1", "namespace1", 1, 0);
		serviceContract2 = util.createServiceContract("name2", "namespace2", 2, 0);
		cooperation1 = util.createCooperation(connectionPoint1, logicalAddress1, serviceContract1,
				serviceConsumer1);
		cooperation2 = util.createCooperation(connectionPoint2, logicalAddress2, serviceContract2,
				serviceConsumer2);
		cooperation3 = util.createCooperation(connectionPoint1, logicalAddress2, serviceContract1,
				serviceConsumer2);
		serviceProducer1 = util.createServiceProducer("description", "hsaId");
		serviceProduction1 = util.createServiceProduction("rivtaProfile", "physicalAdress",
				connectionPoint1, logicalAddress1, serviceProducer1, serviceContract1);
	}

	@After
	public void tearDown() throws Exception {
		util.deleteAll();
	}

	@Test
	public void findAll_shouldReturnAll() throws Exception {

		ServiceConsumerCriteria criteria = new ServiceConsumerCriteria(null, null, null);
		List<ServiceConsumer> result = uut.findAll(criteria);
		assertEquals(2, result.size());

	}

	@Test
	public void findByConnectionPointId() throws Exception {

		ServiceConsumerCriteria criteria = new ServiceConsumerCriteria(connectionPoint1.getId(), null,
				null);
		List<ServiceConsumer> result = uut.findAll(criteria);
		assertEquals(2, result.size());

		criteria = new ServiceConsumerCriteria(connectionPoint2.getId(), null, null);
		result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceConsumer2.getId(), result.get(0).getId());
	}

	@Test
	public void findByConnectionPointId_noHits() throws Exception {

		ServiceConsumerCriteria criteria = new ServiceConsumerCriteria(999L, null, null);
		List<ServiceConsumer> result = uut.findAll(criteria);
		assertEquals(0, result.size());

	}

	@Test
	public void findByLogicalAddressId() throws Exception {

		ServiceConsumerCriteria criteria = new ServiceConsumerCriteria(null, logicalAddress1.getId(), null
				);
		List<ServiceConsumer> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceConsumer1.getId(), result.get(0).getId());

		criteria = new ServiceConsumerCriteria(null, logicalAddress2.getId(), null);
		result = uut.findAll(criteria);
		assertEquals(1, result.size());
	}

	@Test
	public void findByServiceContractId() throws Exception {

		ServiceConsumerCriteria criteria = new ServiceConsumerCriteria(null, null,
				serviceContract1.getId());
		List<ServiceConsumer> result = uut.findAll(criteria);
		assertEquals(2, result.size());

		criteria = new ServiceConsumerCriteria(null, null, serviceContract2.getId());
		result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceConsumer2.getId(), result.get(0).getId());
	}

}
