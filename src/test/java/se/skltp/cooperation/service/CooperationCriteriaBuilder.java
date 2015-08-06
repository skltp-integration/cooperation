package se.skltp.cooperation.service;

/**
 * A builder object for CooperationCriteria
 *
 * @author Peter Merikan
 */
public class CooperationCriteriaBuilder {

	private CooperationCriteria object = new CooperationCriteria();

	public CooperationCriteria build() {
		return object;
	}

	public CooperationCriteriaBuilder connectionPointId(Long id) {
		object.setConnectionPointId(id);
		return this;
	}

	public CooperationCriteriaBuilder logicalAddressId(Long id) {
		object.setLogicalAddressId(id);
		return this;
	}

	public CooperationCriteriaBuilder serviceConsumerId(Long id) {
		object.setServiceConsumerId(id);
		return this;
	}

	public CooperationCriteriaBuilder serviceContractId(Long id) {
		object.setServiceContractId(id);
		return this;
	}
}
