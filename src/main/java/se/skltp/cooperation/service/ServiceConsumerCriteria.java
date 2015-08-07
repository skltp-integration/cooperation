package se.skltp.cooperation.service;

/**
 * A criteria object to be used when fetching {@link se.skltp.cooperation.domain.ServiceConsumer}
 *
 * @author Peter Merikan
 */
public class ServiceConsumerCriteria {
	private Long connectionPointId;

	public Long getConnectionPointId() {
		return connectionPointId;
	}

	public void setConnectionPointId(Long connectionPointId) {
		this.connectionPointId = connectionPointId;
	}

	public boolean isEmpty() {
		return (connectionPointId == null);
	}
}
