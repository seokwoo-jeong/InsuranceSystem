package Customer;

public class Building extends PersonalInformation {

	private String BuildingAddress;
	private long BuildingPrice; //type change
	private String BuildingScale;

	public Building() {
		this.BuildingAddress = null;
		this.BuildingPrice = 0;
		this.BuildingScale = null;
	}

	public String getBuildingAddress() {
		return BuildingAddress;
	}

	public void setBuildingAddress(String buildingAddress) {
		BuildingAddress = buildingAddress;
	}

	public long getBuildingPrice() {
		return BuildingPrice;
	}

	public void setBuildingPrice(long string) {
		BuildingPrice = string;
	}

	public String getBuildingScale() {
		return BuildingScale;
	}

	public void setBuildingScale(String buildingScale) {
		BuildingScale = buildingScale;
	}

}