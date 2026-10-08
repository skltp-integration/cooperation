package se.skltp.cooperation.api.v2.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.ServiceProducer;

class ServiceProducerDTOTest {

	@Test
	void from_shouldMapAllFieldsAndConnectionPoint() {
		Date snapshotTime = new Date(123456789L);

		ConnectionPoint connectionPoint = new ConnectionPoint();
		connectionPoint.setId(2L);
		connectionPoint.setPlatform("Platform");
		connectionPoint.setEnvironment("Test");
		connectionPoint.setSnapshotTime(snapshotTime);

		ServiceProducer producer = new ServiceProducer();
		producer.setId(1L);
		producer.setDescription("Producer description");
		producer.setHsaId("producer-hsa-id");
		producer.setConnectionPoint(connectionPoint);

		ServiceProducerDTO expected = new ServiceProducerDTO(
			1L,
			"Producer description",
			"producer-hsa-id",
			new ConnectionPointDTO(2L, "Platform", "Test", snapshotTime)
		);

		assertEquals(expected, ServiceProducerDTO.from(producer));
	}

	@Test
	void from_withoutConnectionPoint_shouldPreserveScalarFields() {
		ServiceProducer producer = new ServiceProducer();
		producer.setId(1L);
		producer.setDescription("Producer description");
		producer.setHsaId("producer-hsa-id");

		ServiceProducerDTO expected = new ServiceProducerDTO(
			1L, "Producer description", "producer-hsa-id", null
		);

		assertEquals(expected, ServiceProducerDTO.from(producer));
	}
}
