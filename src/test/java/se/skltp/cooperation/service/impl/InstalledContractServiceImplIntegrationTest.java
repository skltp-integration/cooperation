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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.InstalledContract;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.domain.ServiceDomain;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.service.InstalledContractCriteria;
import se.skltp.cooperation.service.InstalledContractService;
import se.skltp.cooperation.api.TestUtil;

/**
 * @author Jan Västernäs
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class InstalledContractServiceImplIntegrationTest {

	@Autowired
	private InstalledContractService uut;

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
	InstalledContract installedContract1;
	InstalledContract installedContract2;

	ServiceProduction serviceProduction1;
	ServiceProduction serviceProduction2;
	ServiceProduction serviceProduction3;
	ServiceProducer serviceProducer1;
	ServiceProducer serviceProducer2;

	ServiceDomain serviceDomain;

	@Before
	public void setUp() throws Exception {
		connectionPoint1 = util.createConnectionPoint("NTJP", "TEST");
		connectionPoint2 = util.createConnectionPoint("NTJP", "PROD");
		serviceConsumer1 = util.createServiceConsumer("consumer1", "hsaId1",connectionPoint1);
		serviceConsumer2 = util.createServiceConsumer("consumer2", "hsaId2",connectionPoint2);
		logicalAddress1 = util.createLogicalAddress("description1", "adress1");
		logicalAddress2 = util.createLogicalAddress("description2", "adress2");
		serviceContract1 = util.createServiceContract("name1", "namespace1", 1, 0);
		serviceContract2 = util.createServiceContract("name2", "namespace2", 2, 0, serviceDomain);
		installedContract1 = util.createInstlledContract(connectionPoint1, serviceContract1);
		installedContract2 = util.createInstlledContract(connectionPoint2, serviceContract2);

		serviceDomain = util.createServiceDomain("name", "namespace");
		cooperation1 = util.createCooperation(connectionPoint1, logicalAddress1, serviceContract1,
				serviceConsumer1);
		cooperation2 = util.createCooperation(connectionPoint2, logicalAddress2, serviceContract2,
				serviceConsumer2);

		serviceProducer1 = util.createServiceProducer("description1", "hsaId1",connectionPoint1);
		serviceProducer2 = util.createServiceProducer("description2", "hsaId2",connectionPoint2);

		serviceProduction1 = util.createServiceProduction("rivTa1", "physicalAdress1",
				connectionPoint1, logicalAddress1, serviceProducer2, serviceContract1);
		serviceProduction2 = util.createServiceProduction("rivTa2", "physicalAdress2",
				connectionPoint2, logicalAddress2, serviceProducer1, serviceContract2);
		serviceProduction3 = util.createServiceProduction("rivTa3", "physicalAdress3",
				connectionPoint1, logicalAddress2, serviceProducer2, serviceContract1);
	}

	@After
	public void tearDown() throws Exception {
		util.deleteAll();
	}

	@Test
	public void findAll_shouldReturnAll() throws Exception {

		InstalledContractCriteria criteria = new InstalledContractCriteria(null, null,null);
		List<InstalledContract> result = uut.findAll(criteria);
		assertEquals(2, result.size());

	}


	@Test
	public void findByConnectionPointId() throws Exception {

		InstalledContractCriteria criteria = new InstalledContractCriteria(connectionPoint1.getId(), null,null);
		List<InstalledContract> result = uut.findAll(criteria);
		assertEquals(1, result.size());

		criteria = new InstalledContractCriteria(connectionPoint2.getId(), null,null);
		result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(connectionPoint2.getId(), result.get(0).getConnectionPoint().getId());
	}

	@Test
	public void findByConnectionPointId_noHits() throws Exception {

		InstalledContractCriteria criteria = new InstalledContractCriteria(9999L, null,null);
		List<InstalledContract> result = uut.findAll(criteria);
		assertEquals(0, result.size());

	}

	@Test
	public void findByServiceContractId() throws Exception {

		InstalledContractCriteria criteria = new InstalledContractCriteria(null, serviceContract1.getId(),null);
		List<InstalledContract> result = uut.findAll(criteria);
		assertEquals(1, result.size());

		criteria = new InstalledContractCriteria(null,serviceContract2.getId(),null);
		result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceContract2.getId(), result.get(0).getServiceContract().getId());
	}

	public void findByServiceDomainId() throws Exception {

		InstalledContractCriteria criteria = new InstalledContractCriteria(null, null,serviceDomain.getId());
		List<InstalledContract> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceDomain.getId(), result.get(0).getServiceContract().getServiceDomain().getId());

	}

}
