/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import se.skltp.cooperation.api.v2.dto.ServiceProducerDTO;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.service.ServiceProducerCriteria;
import se.skltp.cooperation.service.ServiceProducerService;

@ExtendWith(MockitoExtension.class)
class ServiceProducerControllerUnitTest {

	@Mock
	private ServiceProducerService service;

	@InjectMocks
	private ServiceProducerController controller;

	@Test
	void getAllReturnsJsonAndXmlAndForwardsFilters() {
		ServiceProducer producer = new ServiceProducer();
		producer.setId(7L);
		producer.setDescription("producer-description");
		producer.setHsaId("hsa-id");
		when(service.findAll(any(ServiceProducerCriteria.class))).thenReturn(List.of(producer));

		List<ServiceProducerDTO> json = controller.getAllAsJson("hsa-id", 11L, 12L, 13L, 14L);
		var xml = controller.getAllAsXml("hsa-id", 11L, 12L, 13L, 14L);

		assertEquals(List.of(new ServiceProducerDTO(7L, "producer-description", "hsa-id", null)), json);
		assertEquals(json, xml.getServiceProducers());
		ArgumentCaptor<ServiceProducerCriteria> criteria =
				ArgumentCaptor.forClass(ServiceProducerCriteria.class);
		verify(service, times(2)).findAll(criteria.capture());
		ServiceProducerCriteria forwarded = criteria.getValue();
		assertEquals("hsa-id", forwarded.getHsaId());
		assertEquals(11L, forwarded.getConnectionPointId());
		assertEquals(12L, forwarded.getLogicalAddressId());
		assertEquals(13L, forwarded.getServiceContractId());
		assertEquals(14L, forwarded.getServiceConsumerId());
	}

	@Test
	void getAllClearsConnectionPointWhenFilteringByConnectionPoint() {
		ServiceProducer producer = new ServiceProducer();
		producer.setId(7L);
		producer.setConnectionPoint(new ConnectionPoint());
		when(service.findAll(any(ServiceProducerCriteria.class))).thenReturn(List.of(producer));

		List<ServiceProducerDTO> result = controller.getAllAsJson(null, 11L, null, null, null);

		assertNull(result.get(0).connectionPoint());
		assertNull(producer.getConnectionPoint());
	}

	@Test
	void getReturnsMappedProducer() {
		ServiceProducer producer = new ServiceProducer();
		producer.setId(7L);
		producer.setDescription("producer-description");
		producer.setHsaId("hsa-id");
		when(service.find(7L)).thenReturn(producer);

		assertEquals(new ServiceProducerDTO(7L, "producer-description", "hsa-id", null),
				controller.get(7L));
		verify(service).find(7L);
	}

	@Test
	void getThrowsNotFoundWhenProducerDoesNotExist() {
		when(service.find(404L)).thenReturn(null);

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> controller.get(404L));

		assertEquals("Service Producer with id 404 not found", exception.getMessage());
		verify(service).find(404L);
	}
}
