package Accident;

public class Accident {
   
   private int accidentID;
   private int customerID;
   private int insuranceID;
   private String accidentCause;
   private String accidentLocation;
   private String expertOpinion;
   
   //산출된 보험료 저장하는 곳
   private int insurancePremium;
   //금액 결정 사유 저장하는 곳
   private String insurancePremiumCause;
   
   //Data타입이였으나 저장하기 불편해 보여서 String으로 바꿈.
   private String accidentDate;
   private String accidentTime;
   
   private boolean payInsurancePremium;
   
   
   
   public int getInsuranceID() {
	return insuranceID;
}
public void setInsuranceID(int insuranceID) {
	this.insuranceID = insuranceID;
}
public boolean isPayInsurancePremium() {
      return payInsurancePremium;
   }
   public void setPayInsurancePremium(boolean payInsurancePremium) {
      this.payInsurancePremium = payInsurancePremium;
   }
   public int getInsurancePremium() {
      return insurancePremium;
   }
   public void setInsurancePremium(int insurancePremium) {
      this.insurancePremium = insurancePremium;
   }
   public String getInsurancePremiumCause() {
      return insurancePremiumCause;
   }
   public void setInsurancePremiumCause(String insurancePremiumCause) {
      this.insurancePremiumCause = insurancePremiumCause;
   }
   public int getCustomerID() {return this.customerID;}
   public void setCustomerID(int customerID) {this.customerID = customerID;}
   
   public int getAccidentID() {return this.accidentID;}
   public void setAccidentID(int accidentID) {this.accidentID = accidentID;}

   public String getAccidentCause() {return this.accidentCause;}
   public void setAccidentCause(String accidentCause) {this.accidentCause = accidentCause;}

   public String getAccidentLocation() {return this.accidentLocation;}
   public void setAccidentLocation(String accidentLocation) {this.accidentLocation = accidentLocation;}

   public String getExpertOpinion() {return this.expertOpinion;}
   public void setExpertOpinion(String expertOpinion) {this.expertOpinion = expertOpinion;}

   public String getAccidentDate() {return this.accidentDate;}
   public void setAccidentDate(String accidentDate) {this.accidentDate = accidentDate;}

   public String getAccidentTime() {return this.accidentTime;}
   public void setAccidentTime(String accidentTime) {this.accidentTime = accidentTime;}

   public Accident(){
      this.customerID = 0;
      this.accidentID = 0;
      this.accidentCause = "";
      this.accidentLocation="";
      this.expertOpinion = "";
      this.accidentDate = "";
      this.accidentTime = "";
      this.payInsurancePremium = false;
   }
   
}