package Insurance;

public abstract class Insurance {
	public enum InsuranceType{Fire, Car, ActualCost};
	private int insuranceFee;
	private int insuranceID;
	private String insuranceName;
	private String insuranceManual;
	private String insuranceSalesManual;
	private InsuranceType insuranceType;
	
	public InsuranceType getInsuranceType() {return this.insuranceType;}
	public void setInsuranceType(InsuranceType insuranceType) {this.insuranceType = insuranceType;}
	
	public int getInsuranceFee() {return this.insuranceFee;}
	public void setInsuranceFee(int insuranceFee) {this.insuranceFee = insuranceFee;}

	public int getInsuranceID() {return this.insuranceID;}
	public void setInsuranceID(int insuranceID) {this.insuranceID = insuranceID;}
	
	public String getInsuranceName() {return this.insuranceName;}
	public void setInsuranceName(String insuranceName) {this.insuranceName = insuranceName;}

	public String getInsuranceManual() {return this.insuranceManual;}
	public void setInsuranceManual(String insuranceManual) {this.insuranceManual = insuranceManual;}

	public String getInsuranceSalesManual() {return this.insuranceSalesManual;}
	public void setInsuranceSalesManual(String insuranceSalesManual) {this.insuranceSalesManual = insuranceSalesManual;}

	public Insurance(){
		this.insuranceID = 0;
		this.insuranceFee = 0;
		this.insuranceName = "";
		this.insuranceManual = "";
		this.insuranceSalesManual = "";
	}
}