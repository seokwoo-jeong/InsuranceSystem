package Customer;

public class Car extends PersonalInformation {
	
	public enum LicenseType {
		Class1, Class2, HGV
	};

	public enum CarType {
		Compact, Midsize, FullSize
	}; 
	
	private String CarNumber;
	private CarType carType;
	private int DrivingCareer;
	private LicenseType licenseType;

	public Car() {
		this.CarNumber = null;
		this.carType = null;
		this.DrivingCareer = 0;
		this.licenseType = null;
	}

	public String getCarNumber() {
		return CarNumber;
	}

	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public int getDrivingCareer() {
		return DrivingCareer;
	}

	public void setDrivingCareer(int drivingCareer) {
		DrivingCareer = drivingCareer;
	}

	public LicenseType getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(LicenseType licenseType) {
		this.licenseType = licenseType;
	}

}