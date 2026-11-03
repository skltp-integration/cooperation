/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.api.v2.dto.ServiceContractDTO;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.service.ServiceContractCriteria;
import se.skltp.cooperation.service.ServiceContractService;

@ExtendWith(MockitoExtension.class)
class ServiceContractControllerUnitTest {

	@Mock
	private ServiceContractService service;

	@InjectMocks
	private ServiceContractController controller;

	@Test
	void getAllReturnsJsonAndXmlAndForwardsFilters() {
		ServiceContract contract = new ServiceContract();
		contract.setId(7L);
		contract.setName("contract-name");
		contract.setNamespace("urn:contract");
		contract.setMajor(2);
		contract.setMinor(1);
		when(service.findAll(any(ServiceContractCriteria.class))).thenReturn(List.of(contract));

		List<ServiceContractDTO> json = controller.getAllAsJson(
				"urn:contract", 11L, 12L, 13L, 14L, 15L);
		var xml = controller.getAllAsXml("urn:contract", 11L, 12L, 13L, 14L, 15L);

		assertEquals(List.of(new ServiceContractDTO(7L, "contract-name", "urn:contract", 2, 1)), json);
		assertEquals(json, xml.getServiceContracts());
		ArgumentCaptor<ServiceContractCriteria> criteria =
				ArgumentCaptor.forClass(ServiceContractCriteria.class);
		verify(service, times(2)).findAll(criteria.capture());
		ServiceContractCriteria forwarded = criteria.getValue();
		assertEquals("urn:contract", forwarded.getNamespace());
		assertEquals(11L, forwarded.getConnectionPointId());
		assertEquals(12L, forwarded.getLogicalAddressId());
		assertEquals(13L, forwarded.getServiceConsumerId());
		assertEquals(14L, forwarded.getServiceProducerId());
		assertEquals(15L, forwarded.getServiceDomainId());
	}

	@Test
	void getReturnsMappedContract() {
		ServiceContract contract = new ServiceContract();
		contract.setId(7L);
		contract.setName("contract-name");
		when(service.find(7L)).thenReturn(contract);

		assertEquals(new ServiceContractDTO(7L, "contract-name", null, null, null),
				controller.get(7L));
		verify(service).find(7L);
	}

	@Test
	void getThrowsNotFoundWhenContractDoesNotExist() {
		when(service.find(404L)).thenReturn(null);

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> controller.get(404L));

		assertEquals("ServiceContract with id 404 not found", exception.getMessage());
		verify(service).find(404L);
	}
}
