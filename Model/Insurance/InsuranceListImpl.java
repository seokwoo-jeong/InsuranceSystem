package Insurance;

import java.util.Vector;

public class InsuranceListImpl implements InsuranceInterface{

	private Vector<Insurance> insuranceVector;
	public Vector<Insurance> getInsuranceVector() {return this.insuranceVector;}

	public InsuranceListImpl(){
		this.insuranceVector = new Vector<Insurance>();
	}

	public boolean add(Insurance Insurance) {
		if(!(Insurance == null)) {
			this.insuranceVector.add(Insurance);
			return true;
		} else {
			return false;
		}
	}

	
	public boolean delete(int InsuranceID) {
		for(Insurance insurance : this.insuranceVector) {
			if(insurance.getInsuranceID() == InsuranceID) {
				this.insuranceVector.remove(insurance);
				return true;
			}
		}
		return false;
	}

	public Insurance search(int insuranceID) {
	      for(Insurance insurance : this.insuranceVector) {
	         if(insurance.getInsuranceID() == insuranceID) {
	            return insurance;
	         }
	      }
	      return new FireInsurance();
	   }
}