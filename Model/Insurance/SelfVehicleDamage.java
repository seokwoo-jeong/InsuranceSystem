package Insurance;

public class SelfVehicleDamage {

	public enum SSeparation{SelfBodyAccident, CarInjury};
	private SSeparation separation;
	private int subscriptionFeeForAccidentalInjuries;
	private int subscriptionFeeForInjury;
	
	public int getSubscriptionFeeForAccidentalInjuries() {return this.subscriptionFeeForAccidentalInjuries;}
	public void setSubscriptionFeeForAccidentalInjuries(int subscriptionFeeForAccidentalInjuries) {this.subscriptionFeeForAccidentalInjuries = subscriptionFeeForAccidentalInjuries;}

	public int getSubscriptionFeeForInjury() {return this.subscriptionFeeForInjury;}
	public void setSubscriptionFeeForInjury(int subscriptionFeeForInjury) {this.subscriptionFeeForInjury = subscriptionFeeForInjury;}

	public SSeparation getSeparation() {return this.separation;}
	public void setSeparation(SSeparation separation) {this.separation = separation;}

	public SelfVehicleDamage(){
		this.subscriptionFeeForAccidentalInjuries = 0;
		this.subscriptionFeeForInjury = 0;
		this.separation = SSeparation.SelfBodyAccident;
	}
}