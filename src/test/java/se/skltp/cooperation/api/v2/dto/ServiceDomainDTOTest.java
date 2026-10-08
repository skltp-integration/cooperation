package se.skltp.cooperation.api.v2.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import se.skltp.cooperation.domain.ServiceDomain;

class ServiceDomainDTOTest {

	@Test
	void from_shouldMapAllFields() {
		ServiceDomain domain = new ServiceDomain();
		domain.setId(1L);
		domain.setName("Domain name");
		domain.setNamespace("urn:test:domain");

		ServiceDomainDTO expected = new ServiceDomainDTO(
			1L, "Domain name", "urn:test:domain"
		);

		assertEquals(expected, ServiceDomainDTO.from(domain));
	}

	@Test
	void from_withNullFields_shouldPreserveNullValues() {
		ServiceDomain domain = new ServiceDomain();

		ServiceDomainDTO expected = new ServiceDomainDTO(null, null, null);

		assertEquals(expected, ServiceDomainDTO.from(domain));
	}
}
