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
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.domain.ServiceDomain;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.repository.ServiceProductionRepository;
import se.skltp.cooperation.service.ServiceProductionCriteria;
import se.skltp.cooperation.service.ServiceProductionService;
import se.skltp.cooperation.web.rest.TestUtil;

/**
 * @author Jan Västernäs
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ServiceProductionServiceImplIntegrationTest {

	@Autowired
	private ServiceProductionService uut;

	@Autowired
	private TestUtil util;

	@Autowired
	private ServiceProductionRepository serviceProductionRepository;

	ConnectionPoint connectionPoint1;
	ConnectionPoint connectionPoint2;
	LogicalAddress logicalAddress1;
	LogicalAddress logicalAddress2;
	ServiceContract serviceContract1;
	ServiceContract serviceContract2;
	ServiceProduction serviceProduction1;
	ServiceProduction serviceProduction2;
	ServiceProduction serviceProduction3;
	ServiceProducer serviceProducer1;
	ServiceProducer serviceProducer2;
	ServiceDomain serviceDomain1;
	ServiceDomain serviceDomain2;

	@Before
	public void setUp() throws Exception {
		connectionPoint1 = util.createConnectionPoint("NTJP", "TEST");
		connectionPoint2 = util.createConnectionPoint("NTJP", "PROD");

		logicalAddress1 = util.createLogicalAddress("description1", "adress1");
		logicalAddress2 = util.createLogicalAddress("description2", "adress2");

		serviceDomain1 = util.createServiceDomain("domain1", "namespace1");
		serviceDomain2 = util.createServiceDomain("domain2", "namespace2");
		
		serviceContract1 = util.createServiceContract("name1", "namespace1", 1, 0,serviceDomain1);
		serviceContract2 = util.createServiceContract("name2", "namespace2", 2, 0, serviceDomain2);

		serviceProducer1 = util.createServiceProducer("description1", "hsaId1",connectionPoint1);
		serviceProducer2 = util.createServiceProducer("description2", "hsaId2",connectionPoint2);

		serviceProduction1 = util.createServiceProduction("rivTa1", "physicalAdress1",
				connectionPoint1, logicalAddress1, serviceProducer1, serviceContract1);
		serviceProduction2 = util.createServiceProduction("rivTa2", "physicalAdress2",
				connectionPoint2, logicalAddress2, serviceProducer2, serviceContract2);
		serviceProduction3 = util.createServiceProduction("rivTa3", "physicalAdress3",
				connectionPoint1, logicalAddress2, serviceProducer2, serviceContract1);
	}

	@After
	public void tearDown() throws Exception {
		util.deleteAll();
	}

	@Test
	public void findAll_shouldReturnAll() throws Exception {

		ServiceProductionCriteria criteria = new ServiceProductionCriteria(null, null, null, null,
				null, null,null);
		List<ServiceProduction> result = uut.findAll(criteria);
		assertEquals(3, result.size());
	}

	@Test
	public void findByAttributes() throws Exception {

		ServiceProductionCriteria criteria = new ServiceProductionCriteria("physicalAdress1",
				"rivTa1", null, null, null, null,null);
		List<ServiceProduction> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceProduction1.getId(), result.get(0).getId());

	}

	@Test
	public void findByServiceProducerId() throws Exception {

		ServiceProductionCriteria criteria = new ServiceProductionCriteria(null, null,
				serviceProducer1.getId(), null, null, null,null);
		List<ServiceProduction> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceProduction1.getId(), result.get(0).getId());

		criteria = new ServiceProductionCriteria(null, null, serviceProducer2.getId(), null, null,
				null,null);
		result = uut.findAll(criteria);
		assertEquals(2, result.size());
	}

	@Test
	public void findByServiceProducerId_noHits() throws Exception {

		ServiceProductionCriteria criteria = new ServiceProductionCriteria(null, null, 999L, null,
				null, null,null);
		List<ServiceProduction> result = uut.findAll(criteria);
		assertEquals(0, result.size());

	}

	@Test
	public void findByLogicalAddressId() throws Exception {

		ServiceProductionCriteria criteria = new ServiceProductionCriteria(null, null, null,
				logicalAddress1.getId(), null, null,null);
		List<ServiceProduction> result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceProduction1.getId(), result.get(0).getId());

		assertEquals(serviceProduction1.getId(), result.get(0).getId());
		criteria = new ServiceProductionCriteria(null, null, null, logicalAddress2.getId(), null,
				null,null);
		result = uut.findAll(criteria);
		assertEquals(2, result.size());
	}

	@Test
	public void findByServiceContractId() throws Exception {

		ServiceProductionCriteria criteria = new ServiceProductionCriteria(null, null, null, null,
				serviceContract1.getId(), null,null);
		List<ServiceProduction> result = uut.findAll(criteria);
		assertEquals(2, result.size());

		criteria = new ServiceProductionCriteria(null, null, null, null, serviceContract2.getId(),
				null,null);
		result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceProduction2.getId(), result.get(0).getId());
	}

	@Test
	public void findByServiceDomainId() throws Exception {

		ServiceProductionCriteria criteria = new ServiceProductionCriteria(null, null, null, null,
				null, null,serviceDomain1.getId());
		List<ServiceProduction> result = uut.findAll(criteria);
		assertEquals(2, result.size());

		criteria = new ServiceProductionCriteria(null, null, null, null, null,
				null,serviceDomain2.getId());
		result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceProduction2.getId(), result.get(0).getId());

		criteria = new ServiceProductionCriteria(null, null, null, null, null,
				null,999L);
		result = uut.findAll(criteria);
		assertEquals(0, result.size());

	}

	@Test
	public void findByConnectionPointId() throws Exception {

		ServiceProductionCriteria criteria = new ServiceProductionCriteria(null, null, null, null,
				null, connectionPoint1.getId(),null);
		List<ServiceProduction> result = uut.findAll(criteria);
		assertEquals(2, result.size());

		criteria = new ServiceProductionCriteria(null, null, null, null, null,
				connectionPoint2.getId(),null);
		result = uut.findAll(criteria);
		assertEquals(1, result.size());
		assertEquals(serviceProduction2.getId(), result.get(0).getId());
	}

}
