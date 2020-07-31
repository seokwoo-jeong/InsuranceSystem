package Accident;

import java.util.Vector;

public class AccidentListImpl implements AccidentInterface{

	private Vector<Accident> accidentVector;
	
	public Vector<Accident> getAccidentVector() {return this.accidentVector;}

	public AccidentListImpl(){
		this.accidentVector = new Vector<Accident>();
	}

	public boolean add(Accident accident) {
		if(!(accident == null)) {
			this.accidentVector.add(accident);
			return true;
		} else {
			return false;
		}
	}

	//삭제할때 InsuranceID로 검색해서 삭제하던데 AccidentID로 삭제하는 게 맞는거 같아서 수정.
	public boolean delete(int accidentID) {
		for(Accident accident : this.accidentVector) {
			if(accident.getAccidentID() == accidentID) {
				this.accidentVector.remove(accident);
				return true;
			}
		}
		return false;
	}
	
	//삭제할때 InsuranceID로 검색해서 삭제하던데 AccidentID로 삭제하는 게 맞는거 같아서 수정.
	public Accident search(int accidentID) {
		for(Accident accident : this.accidentVector) {
			if(accident.getAccidentID() == accidentID) {
				return accident;
			}
		}
		return new Accident();
	}
}