package Contract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import Contract.Contract.PaymentType;
import Customer.Customer;
import Customer.CustomerListImpl;

public class ContractListImpl implements ContractInterface {

	private Vector<Contract> ContractVector;
	public CustomerListImpl CustomerListImpl;

	public SimpleDateFormat SDF;

	public ContractListImpl() {
		this.ContractVector = new Vector<Contract>();
		this.SDF = new SimpleDateFormat("yyyy-MM-dd");
	}

	public Vector<Contract> getContractVector() {
		return this.ContractVector;
	}

	public void setCustomerListImpl(CustomerListImpl customerListImpl) {
		this.CustomerListImpl = customerListImpl;
	}

	public boolean add(Contract Contract) {
		this.ContractVector.add(Contract);
		return true;
	}


//	public boolean BreakContract(int ContractID) {
//		for(Contract contract : this.ContractVector) {
//			if (contract.getContractID() == ContractID) {
//				ContractVector.remove(contract);
//				System.out.println("계약 파기 완료");
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public boolean delete(int ContractID) {
//		for(Contract contract : this.ContractVector) {
//			if (contract.getContractID() == ContractID) {
//				ContractVector.remove(contract);
//				System.out.println("계약 정보 삭제 완료");
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public Contract search(int ContractID) {
//		for(Contract contract : this.ContractVector){
//			if (contract.getContractID() == ContractID) {
//				return contract;
//			}
//		}
//		return null;
//	}
//
//	public boolean ReContract(int ContractID, Scanner sc) {
//		System.out.println("***********재계약 하기************");
//
//		for(Contract contract : this.ContractVector) { 
//			if(contract.getContractID() == ContractID) {
//				for(Customer customer : this.CustomerListImpl.getCustomerVector()) {
//					if(customer.getCustomerID() == contract.getCustomerID()) {
//
//						System.out.println("고객이름: "+ customer.getCustomerName());
//						System.out.println("연락처: "+ customer.getPhoneNum());
//						System.out.println("계약만료일: "+ contract.getContractExpirationDate());
//						System.out.println("개인정보보유기간: "+ contract.getPersonalInformationRetentionPeriod());
//
//						//modify information at 
//						System.out.println("*****수정을 시작합니다****");
//						System.out.println("새로운 계약만료일을 입력하세요");
//						Date ContractExpirationDate = null;
//
//						try {
//							ContractExpirationDate = SDF.parse(sc.next());
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//						contract.setContractExpirationDate(ContractExpirationDate);
//
//						System.out.println("수정할 부분을 입력하세요 1. 계좌번호, 2. 납입일, 3. 납입방식 " ); // how to express select 1 of 4 
//						int menueNum = 0;
//						while(true) {
//							if(!sc.hasNextInt()) {
//								sc.next();
//								System.err.println("숫자를 입력하시오. 1. 계좌번호, 2. 납입일, 3. 납입방식");
//							}else {
//								menueNum = sc.nextInt();
//								if(!(menueNum>0 && menueNum<4)) {
//									System.err.println("다음 메뉴 중 선택하시오. 1. 계좌번호, 2. 납입일, 3. 납입방식");
//								}else {
//									break;
//								}
//							}
//						}
//
//						switch(menueNum) {
//						case 1: 
//							System.out.println("새로운 계좌번호를 입력하세요");
//							while(true) {
//								if(!sc.hasNextInt()) {
//									System.out.println("입력이 잘못되었습니다. 입력 재확인 부탁합니다");    //대안_   
//								}
//								else{
//									customer.getPersonalInformation().setAccountNumber(sc.nextInt());
//									break;
//								}                  
//							}
//
//
//						case 2:
//							System.out.println("새로운 납입일을 입력하세요");
//							Date PaymentDate = null;
//							try {
//								PaymentDate = SDF.parse(sc.next());
//								//대안_   System.out.println("입력이 잘못되었습니다. 입력 재확인 부탁합니다");
//							} catch (ParseException e) {
//								e.printStackTrace();
//							}
//							contract.setPaymentDate(PaymentDate);
//							break;
//
//						case 3:
//							System.out.println("새로운 납입방식을 입력하세요 1.creditCard, 2.e_bancking, 3.accountTransfer");
//							//대안_   System.out.println("입력이 잘못되었습니다. 입력 재확인 부탁합니다");
//							switch(sc.nextInt()) {
//							case 1: 
//								contract.setPaymentType(PaymentType.creditCard);
//								break;
//							case 2: contract.setPaymentType(PaymentType.e_bancking);
//							break;
//							case 3: contract.setPaymentType(PaymentType.accountTransfer);
//							break;
//							default:
//								break;
//							}
//							break;
//						default:
//							break;
//						}
//					}
//				}
//				// send message subscription   
//				System.out.println("수정이 완료되었습니다 청약서를 발송하였습니다");   
//				//예외_ System.out.println("서버 문제로 발송에 실패하였습니다. 재시도 부탁드립니다");
//				return true;
//			}
//		}      
//		System.out.println("검색 결과 CustomerID 혹은 ContractID가 Vecotor에 존재 하지 않음");
//		return false;   
//	}
//
//	public Vector<Contract> searchUnpaidCustomer() { 
//		System.out.println("********미납자 조회하기*******");
//		Date now = new Date();
//		for(Contract contract : ContractVector) {
//			if(now.after(contract.getContractExpirationDate())) {
//				//show all information of contract
//				System.out.println("계약 ID :"+ contract.getContractID() + ", 고객 ID :"+ contract.getCustomerID()+ 
//						", 납입일 :" + contract.getPaymentDate()+ ", 납입여부 :" + false +", 납입방식 :" + contract.getPaymentType());
//			}
//		}
//		return null;
//	}
//
//	public Vector<Contract> searchFullContractCustomer(Scanner sc) { 
//		System.out.println("********만기계약자 조회하기*******");
//		Date now = new Date();
//		//예외_System.out.println("계약 정보 로딩에 실패하였습니다. 다시 시도하여 주십시오");
//		for(Contract contract : ContractVector) {
//			if(now.after(contract.getContractExpirationDate())) {
//				System.out.println("고객 ID :"+ contract.getCustomerID()+ "계약 ID :"+ contract.getContractID());      
//			}
//		}
//		System.out.println("재계약을 진행할 계약ID을 입력하세요");
//		while(true) {
//			if(!sc.hasNextInt()) {
//				sc.next();
//				System.err.println("숫자를 입력하시오. 계약ID");
//			}else {
//				ReContract(sc.nextInt(), sc); 
//				break;
//			}
//		}
//		return null;
//	}
}