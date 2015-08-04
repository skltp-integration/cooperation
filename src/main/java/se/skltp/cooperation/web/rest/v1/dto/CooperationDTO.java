package se.skltp.cooperation.web.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Cooperation Data Transfer Object
 *
 * @author Peter Merikan
 */
@XmlRootElement(name = "cooperation")
public class CooperationDTO {
	private Long id;
	@JsonBackReference
	private ServiceConsumerDTO serviceConsumer;
	@JsonBackReference
	private LogicalAddressDTO logicalAddress;
	@JsonBackReference
	private ConnectionPointDTO connectionPoint;
	@JsonBackReference
	private ServiceContractDTO serviceContract;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceConsumerDTO getServiceConsumer() {
		return serviceConsumer;
	}

	public void setServiceConsumer(ServiceConsumerDTO serviceConsumer) {
		this.serviceConsumer = serviceConsumer;
	}

	public LogicalAddressDTO getLogicalAddress() {
		return logicalAddress;
	}

	public void setLogicalAddress(LogicalAddressDTO logicalAddress) {
		this.logicalAddress = logicalAddress;
	}

	public ConnectionPointDTO getConnectionPoint() {
		return connectionPoint;
	}

	public void setConnectionPoint(ConnectionPointDTO connectionPoint) {
		this.connectionPoint = connectionPoint;
	}

	public ServiceContractDTO getServiceContract() {
		return serviceContract;
	}

	public void setServiceContract(ServiceContractDTO serviceContract) {
		this.serviceContract = serviceContract;
	}
}
