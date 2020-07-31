package Insurance;

public class ActualCostInsurance extends Insurance {

	private Injury injuryHospitalization;
	private Injury injuryOutpatient;
	
	public Injury getInjuryHospitalization() {return this.injuryHospitalization;}
	public void setInjuryHospitalization(Injury injuryHospitalization) {this.injuryHospitalization = injuryHospitalization;}
	
	public Injury getInjuryOutpatient() {return this.injuryOutpatient;}
	public void setInjuryOutpatient(Injury injuryOutpatient) {this.injuryOutpatient = injuryOutpatient;}
	
	public ActualCostInsurance() {
		this.injuryHospitalization = new Injury();
		this.injuryOutpatient = new Injury();
	}
}