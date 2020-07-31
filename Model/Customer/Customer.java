package Customer;

public class Customer {
	
	public enum InsuranceType{Car, ActualCost, Building};

	private int customerID;
	private String customerName;
	private String phoneNum;
	private boolean subscriptionStatus;
	public PersonalInformation personalInformation;	

	public Customer() {
		this.customerID = 0;
		this.customerName = null;
		this.phoneNum = null;
		this.subscriptionStatus = false;
		this.personalInformation = null;
	}
	

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public boolean isSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(boolean subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public PersonalInformation getPersonalInformation() {
		return personalInformation;
	}

	public void setPersonalInformation(PersonalInformation personalInformation) {
		this.personalInformation = personalInformation;
	}

}