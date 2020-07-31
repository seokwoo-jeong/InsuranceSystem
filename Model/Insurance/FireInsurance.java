package Insurance;

public class FireInsurance extends Insurance {

	private DamageInformation directDamage;
	private DamageInformation fireDamage;
	private DamageInformation refugeDamage;
	
	public DamageInformation getDirectDamage() {return this.directDamage;}
	public void setDirectDamage(DamageInformation directDamage) {this.directDamage = directDamage;}

	public DamageInformation getFireDamage() {return this.fireDamage;}
	public void setFireDamage(DamageInformation fireDamage) {this.fireDamage = fireDamage;}

	public DamageInformation getRefugeDamage() {return this.refugeDamage;}
	public void setRefugeDamage(DamageInformation refugeDamage) {this.refugeDamage = refugeDamage;}

	public FireInsurance(){
		this.directDamage = new DamageInformation();
		this.fireDamage = new DamageInformation();
		this.refugeDamage = new DamageInformation();
	}


}