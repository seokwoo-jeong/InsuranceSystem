package Insurance;

public class DamageInformation {

	private int damageGuaranteedAmount;
	private String damageGuaranteedContent;
	
	public int getDamageGuaranteedAmount() {return this.damageGuaranteedAmount;}
	public void setDamageGuaranteedAmount(int damageGuaranteedAmount) {this.damageGuaranteedAmount = damageGuaranteedAmount;}

	public String getDamageGuaranteedContent() {return this.damageGuaranteedContent;}
	public void setDamageGuaranteedContent(String damageGuaranteedContent) {this.damageGuaranteedContent = damageGuaranteedContent;}

	public DamageInformation(){
		this.damageGuaranteedAmount = 0;
		this.damageGuaranteedContent = "";
	}
}