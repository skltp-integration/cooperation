package se.skltp.cooperation.web.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A ServiceProduction Data Transfer Object with associations
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName = "serviceProduction")
@JsonInclude(Include.NON_EMPTY)
public class ServiceProductionDTO {

	private Long id;
	private String physicalAddress;
	private String rivtaProfile;

	private ServiceProducerDTO serviceProducer;
	private LogicalAddressDTO logicalAddress;
	private ConnectionPointDTO connectionPoint;
	private ServiceContractDTO serviceContract;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public String getRivtaProfile() {
		return rivtaProfile;
	}

	public void setRivtaProfile(String rivtaProfile) {
		this.rivtaProfile = rivtaProfile;
	}

	public ServiceProducerDTO getServiceProducer() {
		return serviceProducer;
	}

	public void setServiceProducer(ServiceProducerDTO serviceProducer) {
		this.serviceProducer = serviceProducer;
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
