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
import se.skltp.cooperation.api.v2.dto.ServiceDomainDTO;
import se.skltp.cooperation.domain.ServiceDomain;
import se.skltp.cooperation.service.ServiceDomainCriteria;
import se.skltp.cooperation.service.ServiceDomainService;

@ExtendWith(MockitoExtension.class)
class ServiceDomainControllerUnitTest {

	@Mock
	private ServiceDomainService service;

	@InjectMocks
	private ServiceDomainController controller;

	@Test
	void getAllReturnsJsonAndXmlAndForwardsNamespace() {
		ServiceDomain domain = new ServiceDomain();
		domain.setId(7L);
		domain.setName("domain-name");
		domain.setNamespace("urn:domain");
		when(service.findAll(any(ServiceDomainCriteria.class))).thenReturn(List.of(domain));

		List<ServiceDomainDTO> json = controller.getAllAsJson("urn:domain");
		var xml = controller.getAllAsXml("urn:domain");

		assertEquals(List.of(new ServiceDomainDTO(7L, "domain-name", "urn:domain")), json);
		assertEquals(json, xml.getServiceDomains());
		ArgumentCaptor<ServiceDomainCriteria> criteria =
				ArgumentCaptor.forClass(ServiceDomainCriteria.class);
		verify(service, times(2)).findAll(criteria.capture());
		assertEquals("urn:domain", criteria.getValue().getNamespace());
	}

	@Test
	void getOneReturnsMappedDomain() {
		ServiceDomain domain = new ServiceDomain();
		domain.setId(7L);
		domain.setName("domain-name");
		when(service.find(7L)).thenReturn(domain);

		assertEquals(new ServiceDomainDTO(7L, "domain-name", null), controller.getOne(7L));
		verify(service).find(7L);
	}

	@Test
	void getOneThrowsNotFoundWhenDomainDoesNotExist() {
		when(service.find(404L)).thenReturn(null);

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> controller.getOne(404L));

		assertEquals("ServiceDomain with id 404 not found", exception.getMessage());
		verify(service).find(404L);
	}
}
