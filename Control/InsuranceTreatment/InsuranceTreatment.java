
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
	//��� �����ϱ�
	public Accident AccidentReception(int customerID, int insuranceID, Scanner scanner){
		Accident accident = new Accident();

		accident.setInsuranceID(insuranceID);
		accident.setCustomerID(customerID);

		System.out.println("******��� ������ �Է��Ͽ� �ֽʽÿ�.******");
		int accidentID = this.insuranceDAO.SelectMaxID("accidentID", "Accident");
		if(accidentID == 0) {
			accidentID = 6000;
		}
		accidentID = accidentID+1;

		accident.setAccidentID(accidentID);
		System.out.println("���ID " +"'"+ accident.getAccidentID()+ "'" + "�� �����Ǿ����ϴ�.");
		System.out.println("2. ��� ��¥�� �Է��Ͽ� �ֽʽÿ�.(yyyy-mm-dd)");
		accident.setAccidentDate(scanner.next());

		System.out.println("3. �ð� �ð��� �Է��Ͽ� �ֽʽÿ�.(00:00:00)");
		accident.setAccidentTime(scanner.next());


		System.out.println("******��� ���������� ����Ǿ����ϴ�.******");
		System.out.println("���ID:" + accident.getAccidentID() +" ��ID:" +  accident.getCustomerID()
		+ " ���¥:" + accident.getAccidentDate() + " ���ð�:" +accident.getAccidentTime());


		System.out.println("******��� ������ �ۼ��� �ֽʽÿ�.*******");
		System.out.println("1. ��� ������ �Է��Ͽ� �ֽʽÿ�.");
		accident.setAccidentCause(scanner.next());
		System.out.println("2. ��� ��Ҹ� �Է��Ͽ� �ֽʽÿ�");
		accident.setAccidentLocation(scanner.next());
		System.out.println("3. ������ �Ұ߼��� �Է��Ͽ� �ֽʽÿ�");
		accident.setExpertOpinion(scanner.next());

		System.out.println("��� ����:" + accident.getAccidentCause() + " ��� ���:" + accident.getAccidentLocation() + " ������ �Ұ߼�:"+ accident.getExpertOpinion());

		return this.accidentDAO.insertAccident(accident);
	}

	//���� ����� �����ϱ�
	public Accident CalculatingDecisionInsuranceFunds(int accidentID, Scanner scanner) {
		if(accidentID == this.accidentDAO.getAccidentID(accidentID)) {
			Accident accident = this.accidentDAO.findAccident(accidentID);
			if(accident.getInsurancePremium() != 0 && accident.getInsurancePremiumCause() != null) {
				System.out.println("�̹� ������� ����Ǿ����ϴ�.");
				return null;
			}else {
				System.out.println("���ID:" + accident.getAccidentID() +" ��ID:" +  accident.getCustomerID() + " ����ID:" + accident.getInsuranceID()
				+ " ���¥:" + accident.getAccidentDate() + " ���ð�:" +accident.getAccidentTime());
				System.out.println("��� ����:" + accident.getAccidentCause() + " ������ �Ұ߼�:"+ accident.getExpertOpinion());
				System.out.println("********���� ����� �����ϱ�********");
				
				System.out.println("1. ���� �ݾ��� �ۼ��Ͽ� �ֽʽÿ�.(�ִ� �����:" + this.insuranceDAO.getInsuranceFee(accident.getInsuranceID()) + "��)");
				while(!scanner.hasNextInt()) {
					scanner.next();
					System.err.println("���ڸ� �Է��ؾ� �մϴ�.");
				}
				int insuranceFee = scanner.nextInt();
				while(insuranceFee > this.insuranceDAO.getInsuranceFee(accident.getInsuranceID()) || insuranceFee<=0) {
					System.out.println("�ִ� ������� �ʰ��߽��ϴ�.");
					insuranceFee = scanner.nextInt();
				}
				accident.setInsurancePremium(insuranceFee);
				System.out.println("2. �ݾ� ���� ������ �Է��Ͽ� �ֽʽÿ�");
				accident.setInsurancePremiumCause(scanner.next());
				
				
				

				this.accidentDAO.insertInsurancePayment(accident, accidentID);
				return accident;
			}
		}else {
			System.out.println("�������� �ʴ� ���ID �Դϴ�.");
		}
		return null;
	}
	public void PaymentDecisionInsuranceFunds(int accidentID, Scanner scanner) {
		Accident accident = this.accidentDAO.findAccident(accidentID);

		if(accidentID == this.accidentDAO.getAccidentID(accidentID)) {
			if(accident.isPayInsurancePremium() == true) {
				System.out.println("�̹� ������ �Ϸ�� ����Դϴ�");
			}else {
				if(accident.getInsurancePremiumCause() == null || accident.getInsurancePremium() ==0) {
					System.out.println("��������� �Ǵ� �ݾ� ���������� �����ϴ�.");
				}else {
					System.out.println("**************�ۼ�����****************");
					System.out.println("���ID:" + accident.getAccidentID() +" ��ID:" +  accident.getCustomerID()
					+ " ���¥:" + accident.getAccidentDate() + " ���ð�:" +accident.getAccidentTime());
					System.out.println("��� ����:" + accident.getAccidentCause() + " ������ �Ұ߼�:"+ accident.getExpertOpinion());
					System.out.println("�ݾ� ���� ����:" + accident.getInsurancePremiumCause() + " ���� �ݾ�:" + accident.getInsurancePremium());
					System.out.println("���ԿϷ�� 1���� ��������");
					while(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("���ڸ� �Է��ؾ� �մϴ�.");
					}
					if(scanner.nextInt() == 1) {
						System.out.println("���ԿϷ�");
						accident.setPayInsurancePremium(true);
						this.accidentDAO.updatePayInsurancePremium(accident);
					}
				}
			}
		}else {
			System.out.println("�������� �ʴ� ���ID �Դϴ�.");
		}
	}
}