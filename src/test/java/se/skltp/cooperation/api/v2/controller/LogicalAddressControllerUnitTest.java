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
import se.skltp.cooperation.api.v2.dto.LogicalAddressDTO;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.service.LogicalAddressCriteria;
import se.skltp.cooperation.service.LogicalAddressService;

@ExtendWith(MockitoExtension.class)
class LogicalAddressControllerUnitTest {

	@Mock
	private LogicalAddressService service;

	@InjectMocks
	private LogicalAddressController controller;

	@Test
	void getAllReturnsJsonAndXmlAndForwardsFilters() {
		LogicalAddress address = new LogicalAddress();
		address.setId(7L);
		address.setDescription("address-description");
		address.setLogicalAddress("logical-address");
		when(service.findAll(any(LogicalAddressCriteria.class))).thenReturn(List.of(address));

		List<LogicalAddressDTO> json = controller.getAllAsJson("logical-address", 11L, 12L, 13L, 14L);
		var xml = controller.getAllAsXml("logical-address", 11L, 12L, 13L, 14L);

		assertEquals(List.of(new LogicalAddressDTO(7L, "address-description", "logical-address")), json);
		assertEquals(json, xml.getLogicalAddresss());
		ArgumentCaptor<LogicalAddressCriteria> criteria =
				ArgumentCaptor.forClass(LogicalAddressCriteria.class);
		verify(service, times(2)).findAll(criteria.capture());
		LogicalAddressCriteria forwarded = criteria.getValue();
		assertEquals("logical-address", forwarded.getLogicalAdress());
		assertEquals(11L, forwarded.getConnectionPointId());
		assertEquals(12L, forwarded.getServiceContractId());
		assertEquals(13L, forwarded.getServiceConsumerId());
		assertEquals(14L, forwarded.getServiceProducerId());
	}

	@Test
	void getReturnsMappedAddress() {
		LogicalAddress address = new LogicalAddress();
		address.setId(7L);
		address.setLogicalAddress("logical-address");
		when(service.find(7L)).thenReturn(address);

		assertEquals(new LogicalAddressDTO(7L, null, "logical-address"), controller.get(7L));
		verify(service).find(7L);
	}

	@Test
	void getThrowsNotFoundWhenAddressDoesNotExist() {
		when(service.find(404L)).thenReturn(null);

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> controller.get(404L));

		assertEquals("Logical Adress with id 404 not found", exception.getMessage());
		verify(service).find(404L);
	}
}
