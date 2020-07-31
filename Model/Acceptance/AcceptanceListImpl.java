package Acceptance;

import java.util.Vector;

public class AcceptanceListImpl implements AcceptanceInterface {

	private Vector<AcceptanceGuide> acceptanceVector;
	public AcceptanceGuide acceptanceGuide;

	public AcceptanceListImpl(){
		this.acceptanceGuide = new AcceptanceGuide();
		this.acceptanceVector = new Vector<AcceptanceGuide>();

	}



	public Vector<AcceptanceGuide> getAcceptanceVector() {
		return acceptanceVector;
	}



	public void setAcceptanceVector(Vector<AcceptanceGuide> acceptanceVector) {
		this.acceptanceVector = acceptanceVector;
	}


	public boolean add(AcceptanceGuide acceptanceGuide){
		if(acceptanceGuide != null) {
			this.acceptanceVector.add(acceptanceGuide);
			return true;
		} else {
			return false;
		}
	}

	public boolean delete(int insuranceID){
		for(AcceptanceGuide AcceptanceGuide : this.acceptanceVector) {
			if(AcceptanceGuide.getInsuranceID() == insuranceID) {
				this.acceptanceVector.remove(AcceptanceGuide);
				return true;
			}
		}
		return false;
	}

	public AcceptanceGuide search(int insuranceID){
		for(AcceptanceGuide AcceptanceGuide : this.acceptanceVector) {
			if(AcceptanceGuide.getInsuranceID() == insuranceID) {
				return AcceptanceGuide;
			}
		}
		return new AcceptanceGuide();
	}
}