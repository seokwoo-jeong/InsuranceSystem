package Customer;

public class PersonalInformation {

	public enum Job {
		soldier, driver, officeWorker, constructionLaborer, fireman, policeman, theOther
	};

	private String AccidentHistory;
	private int AccountNumber;
	private boolean Gender;
	private Job job;
	private int Property;
	private String ResidentRegistrationNumber;

	public PersonalInformation() {
		this.AccidentHistory = null;
		this.AccountNumber = 0;
		this.Gender = false;
		this.job = null;
		this.Property = 0;
		this.ResidentRegistrationNumber = null;
	}

	public String getAccidentHistory() {
		return AccidentHistory;
	}

	public void setAccidentHistory(String accidentHistory) {
		AccidentHistory = accidentHistory;
	}

	public boolean getGender() {
		return Gender;
	}

	public void setGender(boolean gender) {
		Gender = gender;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public int getProperty() {
		return Property;
	}

	public void setProperty(int property) {
		Property = property;
	}

	public String getResidentRegistrationNumber() {
		return ResidentRegistrationNumber;
	}

	public void setResidentRegistrationNumber(String residentRegistrationNumber) {
		ResidentRegistrationNumber = residentRegistrationNumber;
	}

	public int getAccountNumber() {
		return AccountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		AccountNumber = accountNumber;
	}

}