package Insurance;

public class CarInsurance extends Insurance {

	private Goods_Personal goodsIndemnification;
	private Goods_Personal personalIndemnification;
	private SelfVehicleDamage selfVehicleDamage;
	
	public Goods_Personal getGoodsIndemnification() {return this.goodsIndemnification;}
	public void setGoodsIndemnification(Goods_Personal goodsIndemnification) {this.goodsIndemnification = goodsIndemnification;}

	public Goods_Personal getPersonalIndemnification() {return this.personalIndemnification;}
	public void setPersonalIndemnification(Goods_Personal personalIndemnification) {this.personalIndemnification = personalIndemnification;}

	public SelfVehicleDamage getSelfVehicleDamage() {return this.selfVehicleDamage;}
	public void setSelfVehicleDamage(SelfVehicleDamage selfVehicleDamage) {this.selfVehicleDamage = selfVehicleDamage;}
	
	public CarInsurance(){
		this.goodsIndemnification = new Goods_Personal();
		this.personalIndemnification = new Goods_Personal();
		this.selfVehicleDamage = new SelfVehicleDamage();
	}
}