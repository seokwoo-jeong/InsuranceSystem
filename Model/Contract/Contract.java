package Contract;

import java.util.Date;

public class Contract {
	public enum PaymentType {
		creditCard, e_bancking, accountTransfer
	};
	//changed: add customerID, insurance field.. Type change at PersonalInformationRetentionPeriod
	private int customerID;
	private Date ContractExpirationDate; 
	private int ContractID;
//	private int GuaranteedAmount;
	private int PaymentAmount;
	private Date PaymentDate;
	private int PaymentPeriod;
	private boolean PaymentStatus;
	private PaymentType paymentType;
	private Date PersonalInformationRetentionPeriod; // type change.. calculate in ContractManagement

	public Contract() {
		this.customerID = 0;
		this.ContractExpirationDate = null;
		this.ContractID = 0;
//		this.GuaranteedAmount = 0;
		this.PaymentAmount = 0;
		this.PaymentDate = null;
		this.PaymentPeriod = 0;
		this.PaymentStatus = false;
		this.paymentType = null;
		this.PersonalInformationRetentionPeriod = null;
	}
	
	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public Date getContractExpirationDate() {
		return ContractExpirationDate;
	}

	public void setContractExpirationDate(Date contractExpirationDate) {
		ContractExpirationDate = contractExpirationDate;
	}

	public int getContractID() {
		return ContractID;
	}

	public void setContractID(int contractID) {
		ContractID = contractID;
	}

//	public int getGuaranteedAmount() {
//		return GuaranteedAmount;
//	}
//
//	public void setGuaranteedAmount(int guaranteedAmount) {
//		GuaranteedAmount = guaranteedAmount;
//	}

	public int getPaymentAmount() {
		return PaymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		PaymentAmount = paymentAmount;
	}

	public Date getPaymentDate() {
		return PaymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		PaymentDate = paymentDate;
	}

	public int getPaymentPeriod() {
		return PaymentPeriod;
	}

	public void setPaymentPeriod(int paymentPeriod) {
		PaymentPeriod = paymentPeriod;
	}

	public boolean getPaymentStatus() {
		return PaymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		PaymentStatus = paymentStatus;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Date getPersonalInformationRetentionPeriod() {
		return PersonalInformationRetentionPeriod;
	}

	public void setPersonalInformationRetentionPeriod(Date personalInformationRetentionPeriod) {
		PersonalInformationRetentionPeriod = personalInformationRetentionPeriod;
	}



}