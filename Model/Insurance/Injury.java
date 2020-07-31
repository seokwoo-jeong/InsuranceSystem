package Insurance;

public class Injury {

	private int provisionFee;
	private String provisionReason;
	
	public int getProvisionFee() {return this.provisionFee;}
	public void setProvisionFee(int provisionFee) {this.provisionFee = provisionFee;}

	public String getProvisionReason() {return this.provisionReason;}
	public void setProvisionReason(String provisionReason) {this.provisionReason = provisionReason;}

	public Injury(){
		this.provisionFee = 0;
		this.provisionReason = "";
	}
}