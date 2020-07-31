
package InsuranceTreatment;

import java.util.Scanner;

import Accident.Accident;
import Accident.AccidentListImpl;
import ContractManagement.ContractManagement;
import DAO.DAO;
import DAO.accidentDAO;
import DAO.insuranceDAO;
import InsuranceSubscription.InsuranceSubscription;

public class InsuranceTreatment {
	private insuranceDAO insuranceDAO;
	private accidentDAO accidentDAO;

	private AccidentListImpl accidentListImpl;

	@SuppressWarnings("unused")
	private InsuranceSubscription insuranceSubscription;
	@SuppressWarnings("unused")
	private ContractManagement contractManagement;


	public AccidentListImpl getAccidentListImpl() {return accidentListImpl;}

	public InsuranceTreatment(){
		this.accidentListImpl = new AccidentListImpl();
	}

	public void associate(ContractManagement contractManagement, InsuranceSubscription insuranceSubscription, DAO insuranceDAO, DAO accidentDAO) {
		this.contractManagement = contractManagement;
		this.insuranceSubscription = insuranceSubscription;
		this.insuranceDAO = (insuranceDAO)insuranceDAO;
		this.accidentDAO = (accidentDAO)accidentDAO;
	}
	//사고 접수하기
	public Accident AccidentReception(int customerID, int insuranceID, Scanner scanner){
		Accident accident = new Accident();

		accident.setInsuranceID(insuranceID);
		accident.setCustomerID(customerID);

		System.out.println("******사고 내용을 입력하여 주십시오.******");
		int accidentID = this.insuranceDAO.SelectMaxID("accidentID", "Accident");
		if(accidentID == 0) {
			accidentID = 6000;
		}
		accidentID = accidentID+1;

		accident.setAccidentID(accidentID);
		System.out.println("사고ID " +"'"+ accident.getAccidentID()+ "'" + "이 생성되었습니다.");
		System.out.println("2. 사고 날짜를 입력하여 주십시오.(yyyy-mm-dd)");
		accident.setAccidentDate(scanner.next());

		System.out.println("3. 시고 시간을 입력하여 주십시오.(00:00:00)");
		accident.setAccidentTime(scanner.next());


		System.out.println("******사고가 정상적으로 저장되었습니다.******");
		System.out.println("사고ID:" + accident.getAccidentID() +" 고객ID:" +  accident.getCustomerID()
		+ " 사고날짜:" + accident.getAccidentDate() + " 사고시간:" +accident.getAccidentTime());


		System.out.println("******사건 내용을 작성해 주십시요.*******");
		System.out.println("1. 사고 원인을 입력하여 주십시요.");
		accident.setAccidentCause(scanner.next());
		System.out.println("2. 사고 장소를 입력하여 주십시오");
		accident.setAccidentLocation(scanner.next());
		System.out.println("3. 전문가 소견서를 입력하여 주십시오");
		accident.setExpertOpinion(scanner.next());

		System.out.println("사고 원인:" + accident.getAccidentCause() + " 사고 장소:" + accident.getAccidentLocation() + " 전문가 소견서:"+ accident.getExpertOpinion());

		return this.accidentDAO.insertAccident(accident);
	}

	//결정 보험금 산출하기
	public Accident CalculatingDecisionInsuranceFunds(int accidentID, Scanner scanner) {
		if(accidentID == this.accidentDAO.getAccidentID(accidentID)) {
			Accident accident = this.accidentDAO.findAccident(accidentID);
			if(accident.getInsurancePremium() != 0 && accident.getInsurancePremiumCause() != null) {
				System.out.println("이미 보험금이 산출되었습니다.");
				return null;
			}else {
				System.out.println("사고ID:" + accident.getAccidentID() +" 고객ID:" +  accident.getCustomerID() + " 보험ID:" + accident.getInsuranceID()
				+ " 사고날짜:" + accident.getAccidentDate() + " 사고시간:" +accident.getAccidentTime());
				System.out.println("사고 원인:" + accident.getAccidentCause() + " 전문가 소견서:"+ accident.getExpertOpinion());
				System.out.println("********결정 보험금 산출하기********");
				
				System.out.println("1. 최종 금액을 작성하여 주십시오.(최대 보장액:" + this.insuranceDAO.getInsuranceFee(accident.getInsuranceID()) + "원)");
				while(!scanner.hasNextInt()) {
					scanner.next();
					System.err.println("숫자를 입력해야 합니다.");
				}
				int insuranceFee = scanner.nextInt();
				while(insuranceFee > this.insuranceDAO.getInsuranceFee(accident.getInsuranceID()) || insuranceFee<=0) {
					System.out.println("최대 보장액을 초과했습니다.");
					insuranceFee = scanner.nextInt();
				}
				accident.setInsurancePremium(insuranceFee);
				System.out.println("2. 금액 결정 사유를 입력하여 주십시오");
				accident.setInsurancePremiumCause(scanner.next());
				
				
				

				this.accidentDAO.insertInsurancePayment(accident, accidentID);
				return accident;
			}
		}else {
			System.out.println("존재하지 않는 사고ID 입니다.");
		}
		return null;
	}
	public void PaymentDecisionInsuranceFunds(int accidentID, Scanner scanner) {
		Accident accident = this.accidentDAO.findAccident(accidentID);

		if(accidentID == this.accidentDAO.getAccidentID(accidentID)) {
			if(accident.isPayInsurancePremium() == true) {
				System.out.println("이미 납입이 완료된 사고입니다");
			}else {
				if(accident.getInsurancePremiumCause() == null || accident.getInsurancePremium() ==0) {
					System.out.println("결정보험금 또는 금액 결정사유가 없습니다.");
				}else {
					System.out.println("**************작성내용****************");
					System.out.println("사고ID:" + accident.getAccidentID() +" 고객ID:" +  accident.getCustomerID()
					+ " 사고날짜:" + accident.getAccidentDate() + " 사고시간:" +accident.getAccidentTime());
					System.out.println("사고 원인:" + accident.getAccidentCause() + " 전문가 소견서:"+ accident.getExpertOpinion());
					System.out.println("금액 결정 사유:" + accident.getInsurancePremiumCause() + " 최종 금액:" + accident.getInsurancePremium());
					System.out.println("납입완료시 1번을 누르세요");
					while(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("숫자를 입력해야 합니다.");
					}
					if(scanner.nextInt() == 1) {
						System.out.println("납입완료");
						accident.setPayInsurancePremium(true);
						this.accidentDAO.updatePayInsurancePremium(accident);
					}
				}
			}
		}else {
			System.out.println("존재하지 않는 사고ID 입니다.");
		}
	}
}