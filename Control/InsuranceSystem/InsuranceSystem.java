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
			System.out.println("******보험 시스템 시작******");
			System.out.println("1.상품 설계하기, 2.인수정책수립하기, 3.계약 관리하기, 4.영업활동, 5.인수심사하기, 6.보험처리하기");
			int menuNum = 1;
			while(true) {
				if(!scanner.hasNextInt()) {
					scanner.next();
					System.err.println("숫자를 입력하여 주십시오. 1.상품 설계하기, 2.인수정책수립하기, 3.계약 관리하기, 4.영업활동, 5.인수심사하기, 6.보험처리하기");
				} else {
					menuNum = scanner.nextInt();
					if (!(menuNum > 0 && menuNum < 7)) {
						System.err.println("매뉴 중 하나를  선택하여 주십시오. 1.상품 설계하기, 2.인수정책수립하기, 3.계약 관리하기, 4.영업활동, 5.인수심사하기, 6.보험처리하기");
					} else {
						break;
					}
				}
			}
			switch(menuNum) {
			case 1: 
				System.out.println("******상품 설계******");
				insuranceSubscription.CreateInsuranceContent(scanner);
				break;
			case 2: 
				System.out.println("******인수 정책 수립******");
				insuranceSubscription.ViewAcceptanceGuide(scanner);
				System.out.println("인수 지침 등록 화면으로 넘어가시겠습니까? Y 또는 N을 입력하여 주십시오.");
				while(true) {
					String YorN = scanner.next();
					if(YorN.equals("y") || YorN.equals("Y")) {
						insuranceSubscription.CreateAcceptanceGuide(scanner);
						break;
					} else if (YorN.equals("n") || YorN.equals("N")){
						break;
					} else {
						System.out.println("인수 지침 등록 화면으로 넘어가시겠습니까? Y 또는 N을 입력하여 주십시오.");
					}
				}
				break;
			case 3:
				//예외_ System.out.println("메뉴 선택 화면이 로딩에 실패하였습니다. 다시 시도하여 주십시오");
				System.out.println("계약관리하기");
				System.out.println("1. 납부관리하기, 2.만기계약관리하기, 3. 계약서 작성하기");
				//계약 만들기는 인수가 끝나면 진행
				while(true) {
					if(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("숫자를 입력하여 주십시오. 1. 납부관리하기, 2.만기계약관리하기, 3. 계약서 작성하기");
					} else {
						menuNum = scanner.nextInt();
						if (!(menuNum > 0 && menuNum < 4)) {
							System.err.println("매뉴 중 하나를  선택하여 주십시오.1. 납부관리하기, 2.만기계약관리하기, 3. 계약서 작성하기");
						} else {
							break;
						}
					}
				}
				switch(menuNum) {
				case 1:
					//예외_System.out.println("계약정보 리스트 화면 로딩에 실패하였습니다. 다시 시도하여 주십시오.");               
					contractManagement.searchUnpaidCustomer(scanner);

					break;
				case 2:
					//예외_System.out.println("리스트 로딩에 실패하였습니다. 다시 시도하여 주십시오.");
					contractManagement.searchFullContractCustomer(scanner);
				case 3:
					//예외_System.out.println("리스트 로딩에 실패하였습니다. 다시 시도하여 주십시오.");
					contractManagement.WriteContractContent(scanner);
					break;
				default:
					break;   
				}

				break;
			case 4:
				//예외_ System.out.println("메뉴 버튼 출력에 실패하였습니다. 다시 시도하여 주십시오");
				System.out.println("영업활동");
				System.out.println("1. 메뉴얼 확인하기, 2. 상품 가입 신청하기");

				while(true) {
					if(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("숫자를 입력하여 주십시오.1. 메뉴얼 확인하기, 2. 상품 가입 신청하기");
					} else {
						menuNum = scanner.nextInt();
						if (!(menuNum > 0 && menuNum < 3)) {
							System.err.println("매뉴 중 하나를  선택하여 주십시오.1. 메뉴얼 확인하기, 2. 상품 가입 신청하기");
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
				//인수 승인 대기 신청서가 없는 경우
				if(subscriptionDAO.showAcceptanceAprove(0).size() == 0) {
					System.out.println("인수 승인 대기 신청서가 없습니다.");
					break;
				}

				System.out.println("인수 승인 대기중인 보험ID와 고객 ID");
				//no = showacceptanceapprove에서 받은 백터의 index
				int no = 0;
				for(no=0; no<subscriptionDAO.showAcceptanceAprove(0).size(); no++) {
					if(no%2 == 0) {
						System.out.print(no/2+1);
						System.out.print(".("+"보험ID:" + subscriptionDAO.showAcceptanceAprove(0).get(no));
					}else {
						System.out.println(" 고객ID:" + subscriptionDAO.showAcceptanceAprove(0).get(no) + ")");
					}

				}

				System.out.println("인수 심사를 할 번호를 눌러주세요");
				while(!scanner.hasNextInt()) {
					scanner.next();
					System.err.println("숫자를 입력해야 합니다.");
				}
				int num = scanner.nextInt();
				while(num> no/2 || num<1) {
					System.err.println("존재하지 않는 번호입니다.");
					num = scanner.nextInt();

				}              
				int index = (num *2) - 2;

				insuranceSubscription.Accept(subscriptionDAO.showAcceptanceAprove(0).get(index),subscriptionDAO.showAcceptanceAprove(0).get(index+1), scanner);
				break;
			case 6:
				System.out.println("1.사고 내용 작성하기, 2.결정보험금 산출하기, 3.결정보험금 지급하기");
				while(!scanner.hasNextInt()) {
					scanner.next();
					System.err.println("숫자를 입력해야 합니다.");
				}
				switch(scanner.nextInt()) {
				case 1:

					//인수 승인 대기 신청서가 없는 경우
					if(subscriptionDAO.showAcceptanceAprove(1).size() == 0) {
						System.out.println("인수 승인 대기 신청서가 없습니다.");
						break;
					}

					System.out.println("보험ID와 고객 ID");
					//no = showacceptanceapprove에서 받은 백터의 index
					no = 0;
					for(no=0; no<subscriptionDAO.showAcceptanceAprove(1).size(); no++) {
						if(no%2 == 0) {
							System.out.print(no/2+1);
							System.out.print(".("+"보험ID:" + subscriptionDAO.showAcceptanceAprove(1).get(no));
						}else {
							System.out.println(" 고객ID:" + subscriptionDAO.showAcceptanceAprove(1).get(no) + ")");
						}

					}

					System.out.println("보험처리할 번호를 눌러주세요.");
					while(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("숫자를 입력해야 합니다.");
					}
					num = scanner.nextInt();
					while(num> no/2 || num<1) {
						System.err.println("존재하지 않는 번호입니다.");
						num = scanner.nextInt();

					}              
					index = (num *2) - 2;
					insuranceTreatment.AccidentReception(subscriptionDAO.showAcceptanceAprove(1).get(index+1),subscriptionDAO.showAcceptanceAprove(1).get(index), scanner);
					break;
				case 2:
					//사고가 없는 경우
					if(accidentDAO.showAllAccidentID()==0) {
						break;
					}
					//결정보험금 산출
					System.out.println("사고ID를 입력하십시오.");

					while(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("숫자를 입력해야 합니다.");
					}
					insuranceTreatment.CalculatingDecisionInsuranceFunds(scanner.nextInt(), scanner);
					break;
				case 3:
					//사고가 없는 경우
					if(accidentDAO.showAllAccidentID()==0) {
						break;
					}
					//결정보험금 지급
					System.out.println("사고ID를 입력하십시오.");
					while(!scanner.hasNextInt()) {
						scanner.next();
						System.err.println("숫자를 입력해야 합니다.");
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
