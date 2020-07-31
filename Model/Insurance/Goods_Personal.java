package Insurance;

public class Goods_Personal {
	
	public enum GSeparation{Death, Injury, Aftereffect};

	private String guaranteeContent;
	private int provisionLimit;
	private GSeparation separation;
	
	public String getGuaranteeContent() {return this.guaranteeContent;}
	public void setGuaranteeContent(String guaranteeContent) {this.guaranteeContent = guaranteeContent;}

	public int getProvisionLimit() {return this.provisionLimit;}
	public void setProvisionLimit(int provisionLimit) {this.provisionLimit = provisionLimit;}

	public GSeparation getSeparation() {return this.separation;}
	public void setSeparation(GSeparation separation) {this.separation = separation;}
	
	public Goods_Personal(){
		this.provisionLimit = 0;
		this.guaranteeContent = "";
		this.separation = GSeparation.Death;
	}	
}