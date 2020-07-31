package Acceptance;


public class AcceptanceGuide {

	private int InsuranceID;
	private int acceptanceID;
	public enum RiskEvaluation{High, Middle, Low};
	private RiskEvaluation riskEvaluation;
	private String ScamCase;

	public  AcceptanceGuide(){
		this.riskEvaluation = RiskEvaluation.Low;
		this.ScamCase = "";
		this.InsuranceID = 0;
	}

	public int getInsuranceID() {
		return InsuranceID;
	}

	public void setInsuranceID(int insuranceID) {
		InsuranceID = insuranceID;
	}

	public String getScamCase() {
		return ScamCase;
	}

	public void setScamCase(String scamCase) {
		ScamCase = scamCase;
	}

	public RiskEvaluation getRiskEvaluation() {
		return riskEvaluation;
	}

	public void setRiskEvaluation(RiskEvaluation riskEvaluation) {
		this.riskEvaluation = riskEvaluation;
	}

	public int getAcceptanceID() {
		return acceptanceID;
	}

	public void setAcceptanceID(int acceptanceID) {
		this.acceptanceID = acceptanceID;
	}
	
	
	
}