package se.skltp.cooperation.api.v2.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.domain.ServiceProduction;

class ServiceProductionDTOTest {

	@Test
	void from_shouldMapAllFieldsAndAssociations() {
		Date snapshotTime = new Date(123456789L);

		ConnectionPoint connectionPoint = new ConnectionPoint();
		connectionPoint.setId(2L);
		connectionPoint.setPlatform("Platform");
		connectionPoint.setEnvironment("Test");
		connectionPoint.setSnapshotTime(snapshotTime);

		ServiceProducer producer = new ServiceProducer();
		producer.setId(3L);
		producer.setDescription("Producer description");
		producer.setHsaId("producer-hsa-id");
		producer.setConnectionPoint(connectionPoint);

		ServiceProduction production = getProduction(producer, connectionPoint);

		ServiceProductionDTO expected = getExpected(snapshotTime);

		assertEquals(expected, ServiceProductionDTO.from(production));
	}

	private static @NonNull ServiceProductionDTO getExpected(Date snapshotTime) {
		ConnectionPointDTO expectedConnectionPoint = new ConnectionPointDTO(
			2L, "Platform", "Test", snapshotTime
		);
		return new ServiceProductionDTO(
			1L,
			"https://example.org/service",
			"RIVTA",
			new ServiceProducerDTO(
				3L, "Producer description", "producer-hsa-id",
				expectedConnectionPoint
			),
			new LogicalAddressDTO(
				4L, "Logical address description", "logical-address"
			),
			expectedConnectionPoint,
			new ServiceContractDTO(5L, "Contract name", "urn:test:contract", 1, 2)
		);
	}

	private static @NonNull ServiceProduction getProduction(ServiceProducer producer, ConnectionPoint connectionPoint) {
		LogicalAddress logicalAddress = new LogicalAddress();
		logicalAddress.setId(4L);
		logicalAddress.setDescription("Logical address description");
		logicalAddress.setLogicalAddress("logical-address");

		ServiceContract contract = new ServiceContract();
		contract.setId(5L);
		contract.setName("Contract name");
		contract.setNamespace("urn:test:contract");
		contract.setMajor(1);
		contract.setMinor(2);

		ServiceProduction production = new ServiceProduction();
		production.setId(1L);
		production.setPhysicalAddress("https://example.org/service");
		production.setRivtaProfile("RIVTA");
		production.setServiceProducer(producer);
		production.setLogicalAddress(logicalAddress);
		production.setConnectionPoint(connectionPoint);
		production.setServiceContract(contract);
		return production;
	}

	@Test
	void from_withoutAssociations_shouldPreserveScalarFields() {
		ServiceProduction production = new ServiceProduction();
		production.setId(1L);
		production.setPhysicalAddress("https://example.org/service");
		production.setRivtaProfile("RIVTA");

		ServiceProductionDTO expected = new ServiceProductionDTO(
			1L, "https://example.org/service", "RIVTA",
			null, null, null, null
		);

		assertEquals(expected, ServiceProductionDTO.from(production));
	}
}
