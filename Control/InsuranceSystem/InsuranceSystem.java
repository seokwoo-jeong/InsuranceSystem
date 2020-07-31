package InsuranceSystem;

import java.text.ParseException;
import java.util.Scanner;

import ContractManagement.ContractManagement;
import DAO.acceptanceDAO;
import DAO.accidentDAO;
import DAO.contractDAO;
import DAO.customerDAO;
import DAO.insuranceDAO;
import DAO.subscriptionDAO;
import InsuranceSubscription.InsuranceSubscription;
import InsuranceTreatment.InsuranceTreatment;

public class InsuranceSystem {

	public static void main(String[] args) throws ParseException {

		
		
		//insuranceDAO.getConnection();

		ContractManagement contractManagement = new ContractManagement();	
		InsuranceSubscription insuranceSubscription = new InsuranceSubscription();
		InsuranceTreatment insuranceTreatment = new InsuranceTreatment();
		insuranceDAO insuranceDAO = new insuranceDAO();
		subscriptionDAO subscriptionDAO = new subscriptionDAO();
		contractDAO contractDAO = new contractDAO();
		acceptanceDAO acceptanceDAO = new acceptanceDAO();
		accidentDAO accidentDAO = new accidentDAO();
		customerDAO customerDAO = new customerDAO();
	

		contractManagement.associate(insuranceSubscription,insuranceTreatment, insuranceDAO, contractDAO, subscriptionDAO, customerDAO);
		insuranceSubscription.associate(insuranceTreatment, contractManagement, insuranceDAO, subscriptionDAO, contractDAO, acceptanceDAO, customerDAO);
		insuranceTreatment.associate(contractManagement, insuranceSubscription, insuranceDAO, accidentDAO);
		

		Scanner scanner = new Scanner(System.in);		

		boolean check = true;
		while(check) {
			System.out.println("******���� �ý��� ����******");
			System.out.println("1.��ǰ �����ϱ�, 2.�μ���å�����ϱ�, 3.��� �����ϱ�, 4.����Ȱ��, 5.�μ��ɻ��ϱ�, 6.����ó���ϱ�");
			int menuNum = 1;
			while(true) {
				if(!scanner.hasNextInt()) {
					scanner.next();
					System.err.println("���ڸ� �Է��Ͽ� �ֽʽÿ�. 1.��ǰ �����ϱ�, 2.�μ���å�����ϱ�, 3.��� �����ϱ�, 4.����Ȱ��, 5.�μ��ɻ��ϱ�, 6.����ó���ϱ�");
				} else {
					menuNum = scanner.nextInt();
					if (!(menuNum > 0 && menuNum < 7)) {
						System.err.println("�Ŵ� �� �ϳ���  �����Ͽ� �ֽʽÿ�. 1.��ǰ �����ϱ�, 2.�μ���å�����ϱ�, 3.��� �����ϱ�, 4.����Ȱ��, 5.�μ��ɻ��ϱ�, 6.����ó���ϱ�");
					} else {
						break;
					}
				}
			}
			switch(menuNum) {
			case 1: 
				System.out.println("******��ǰ ����******");
				insuranceSubscription.CreateInsuranceContent(scanner);
				break;
			case 2: 
				System.out.println("******�μ� ��å ����******");
				insuranceSubscription.ViewAcceptanceGuide(scanner);
				System.out.println("�μ� ��ħ ��� ȭ������ �Ѿ�ðڽ��ϱ�? Y �Ǵ� N�� �Է��Ͽ� �ֽʽÿ�.");
				while(true) {
					String YorN = scanner.next();
					if(YorN.equals("y") || YorN.equals("Y")) {
						insuranceSubscription.CreateAcceptanceGuide(scanner);
						break;
					} else if (YorN.equals("n") || YorN.equals("N")){
						break;
					} else {
						System.out.println("�μ� ��ħ ��� ȭ������ �Ѿ�ðڽ��ϱ�? Y �Ǵ� N�� �Է��Ͽ� �ֽʽÿ�.");
					}
				}
				break;
			case 3:
				//����_ System.out.println("�޴� ���� ȭ���� �ε��� �����Ͽ����ϴ�. �ٽ� �õ��Ͽ� �ֽʽÿ�");
				System.out.println("�������ϱ�");
				System.out.println("1. ���ΰ����ϱ�, 2.����������ϱ�, 3. ��༭ �ۼ��ϱ�");
				//��� ������ �μ��� ������ ����
				while(true) {
					if(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("���ڸ� �Է��Ͽ� �ֽʽÿ�. 1. ���ΰ����ϱ�, 2.����������ϱ�, 3. ��༭ �ۼ��ϱ�");
					} else {
						menuNum = scanner.nextInt();
						if (!(menuNum > 0 && menuNum < 4)) {
							System.err.println("�Ŵ� �� �ϳ���  �����Ͽ� �ֽʽÿ�.1. ���ΰ����ϱ�, 2.����������ϱ�, 3. ��༭ �ۼ��ϱ�");
						} else {
							break;
						}
					}
				}
				switch(menuNum) {
				case 1:
					//����_System.out.println("������� ����Ʈ ȭ�� �ε��� �����Ͽ����ϴ�. �ٽ� �õ��Ͽ� �ֽʽÿ�.");               
					contractManagement.searchUnpaidCustomer(scanner);

					break;
				case 2:
					//����_System.out.println("����Ʈ �ε��� �����Ͽ����ϴ�. �ٽ� �õ��Ͽ� �ֽʽÿ�.");
					contractManagement.searchFullContractCustomer(scanner);
				case 3:
					//����_System.out.println("����Ʈ �ε��� �����Ͽ����ϴ�. �ٽ� �õ��Ͽ� �ֽʽÿ�.");
					contractManagement.WriteContractContent(scanner);
					break;
				default:
					break;   
				}

				break;
			case 4:
				//����_ System.out.println("�޴� ��ư ��¿� �����Ͽ����ϴ�. �ٽ� �õ��Ͽ� �ֽʽÿ�");
				System.out.println("����Ȱ��");
				System.out.println("1. �޴��� Ȯ���ϱ�, 2. ��ǰ ���� ��û�ϱ�");

				while(true) {
					if(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("���ڸ� �Է��Ͽ� �ֽʽÿ�.1. �޴��� Ȯ���ϱ�, 2. ��ǰ ���� ��û�ϱ�");
					} else {
						menuNum = scanner.nextInt();
						if (!(menuNum > 0 && menuNum < 3)) {
							System.err.println("�Ŵ� �� �ϳ���  �����Ͽ� �ֽʽÿ�.1. �޴��� Ȯ���ϱ�, 2. ��ǰ ���� ��û�ϱ�");
						} else {
							break;
						}
					}
				}

				switch(menuNum) {
				case 1:
					insuranceSubscription.ShowMenual(scanner);
					break;
				case 2:
					insuranceSubscription.CreateCustomerContent(scanner);
					break;
				default:
					break;   
				}

				break;

			case 5:
				//�μ� ���� ��� ��û���� ���� ���
				if(subscriptionDAO.showAcceptanceAprove(0).size() == 0) {
					System.out.println("�μ� ���� ��� ��û���� �����ϴ�.");
					break;
				}

				System.out.println("�μ� ���� ������� ����ID�� �� ID");
				//no = showacceptanceapprove���� ���� ������ index
				int no = 0;
				for(no=0; no<subscriptionDAO.showAcceptanceAprove(0).size(); no++) {
					if(no%2 == 0) {
						System.out.print(no/2+1);
						System.out.print(".("+"����ID:" + subscriptionDAO.showAcceptanceAprove(0).get(no));
					}else {
						System.out.println(" ��ID:" + subscriptionDAO.showAcceptanceAprove(0).get(no) + ")");
					}

				}

				System.out.println("�μ� �ɻ縦 �� ��ȣ�� �����ּ���");
				while(!scanner.hasNextInt()) {
					scanner.next();
					System.err.println("���ڸ� �Է��ؾ� �մϴ�.");
				}
				int num = scanner.nextInt();
				while(num> no/2 || num<1) {
					System.err.println("�������� �ʴ� ��ȣ�Դϴ�.");
					num = scanner.nextInt();

				}              
				int index = (num *2) - 2;

				insuranceSubscription.Accept(subscriptionDAO.showAcceptanceAprove(0).get(index),subscriptionDAO.showAcceptanceAprove(0).get(index+1), scanner);
				break;
			case 6:
				System.out.println("1.��� ���� �ۼ��ϱ�, 2.��������� �����ϱ�, 3.��������� �����ϱ�");
				while(!scanner.hasNextInt()) {
					scanner.next();
					System.err.println("���ڸ� �Է��ؾ� �մϴ�.");
				}
				switch(scanner.nextInt()) {
				case 1:

					//�μ� ���� ��� ��û���� ���� ���
					if(subscriptionDAO.showAcceptanceAprove(1).size() == 0) {
						System.out.println("�μ� ���� ��� ��û���� �����ϴ�.");
						break;
					}

					System.out.println("����ID�� �� ID");
					//no = showacceptanceapprove���� ���� ������ index
					no = 0;
					for(no=0; no<subscriptionDAO.showAcceptanceAprove(1).size(); no++) {
						if(no%2 == 0) {
							System.out.print(no/2+1);
							System.out.print(".("+"����ID:" + subscriptionDAO.showAcceptanceAprove(1).get(no));
						}else {
							System.out.println(" ��ID:" + subscriptionDAO.showAcceptanceAprove(1).get(no) + ")");
						}

					}

					System.out.println("����ó���� ��ȣ�� �����ּ���.");
					while(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("���ڸ� �Է��ؾ� �մϴ�.");
					}
					num = scanner.nextInt();
					while(num> no/2 || num<1) {
						System.err.println("�������� �ʴ� ��ȣ�Դϴ�.");
						num = scanner.nextInt();

					}              
					index = (num *2) - 2;
					insuranceTreatment.AccidentReception(subscriptionDAO.showAcceptanceAprove(1).get(index+1),subscriptionDAO.showAcceptanceAprove(1).get(index), scanner);
					break;
				case 2:
					//��� ���� ���
					if(accidentDAO.showAllAccidentID()==0) {
						break;
					}
					//��������� ����
					System.out.println("���ID�� �Է��Ͻʽÿ�.");

					while(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("���ڸ� �Է��ؾ� �մϴ�.");
					}
					insuranceTreatment.CalculatingDecisionInsuranceFunds(scanner.nextInt(), scanner);
					break;
				case 3:
					//��� ���� ���
					if(accidentDAO.showAllAccidentID()==0) {
						break;
					}
					//��������� ����
					System.out.println("���ID�� �Է��Ͻʽÿ�.");
					while(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("���ڸ� �Է��ؾ� �մϴ�.");
					}
					insuranceTreatment.PaymentDecisionInsuranceFunds(scanner.nextInt(), scanner);
					break;
				default:
					break;
				}
				break;
			default:
				check = false;
				scanner.close();
				break;
			}
		}
	}
}
