package InsuranceSubscription;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import Acceptance.AcceptanceGuide;
import Acceptance.AcceptanceGuide.RiskEvaluation;
import Acceptance.AcceptanceListImpl;
import ContractManagement.ContractManagement;
import Customer.CustomerListImpl;
import Customer.PersonalInformation;
import Customer.ActualCost;
import Customer.Building;
import Customer.Car;
import Customer.Customer;
import Insurance.ActualCostInsurance;
import Insurance.CarInsurance;
import Insurance.DamageInformation;
import Insurance.FireInsurance;
import Insurance.Goods_Personal;
import Insurance.Goods_Personal.GSeparation;
import Insurance.Injury;
import Insurance.Insurance;
import Insurance.InsuranceListImpl;
import Insurance.SelfVehicleDamage;
import Insurance.SelfVehicleDamage.SSeparation;
import InsuranceTreatment.InsuranceTreatment;
import Customer.PersonalInformation.Job;
import DAO.DAO;
import DAO.contractDAO;
import DAO.insuranceDAO;
import DAO.acceptanceDAO;
import DAO.subscriptionDAO;
import DAO.customerDAO;
import Customer.Car.LicenseType;
import Customer.Car.CarType;
import Customer.ActualCost.BloodType;
import Customer.ActualCost.DiseaseHistory;

public class InsuranceSubscription {

	// associate
	private contractDAO contractDAO;
	private insuranceDAO insuranceDAO;
	private acceptanceDAO acceptanceDAO;
	private subscriptionDAO subscriptionDAO;
	private customerDAO customerDAO;
	
	@SuppressWarnings("unused")
	private ContractManagement contractManagement;
	@SuppressWarnings("unused")
	private InsuranceTreatment insuranceTreatment;

	private InsuranceListImpl insuranceListImpl;
	private AcceptanceListImpl acceptanceListImpl;
	private CustomerListImpl customerListImpl;

	private float insurancePremiumRate;

	public InsuranceListImpl getInsuranceListImpl() {
		return insuranceListImpl;
	}

	public AcceptanceListImpl getAcceptanceListImpl() {
		return acceptanceListImpl;
	}

	public CustomerListImpl getCustomerListImpl() {
		return customerListImpl;
	}

	public InsuranceSubscription() {
		this.insurancePremiumRate = 0;
		this.contractDAO = null;

		this.insuranceListImpl = new InsuranceListImpl();
		this.acceptanceListImpl = new AcceptanceListImpl();
		this.customerListImpl = new CustomerListImpl();
	}

	public void associate(InsuranceTreatment insuranceTreatment, ContractManagement contractManagement,
			DAO DAO, DAO subscriptionDAO2, DAO contractDAO2, DAO acceptanceDAO2, DAO customerDAO2) {
		
		this.insuranceTreatment = insuranceTreatment;
		this.contractManagement = contractManagement;
		
		this.contractDAO = (contractDAO)contractDAO2;
		this.acceptanceDAO = (acceptanceDAO) acceptanceDAO2;
		this.insuranceDAO = (insuranceDAO)DAO;
		this.customerDAO = (customerDAO)customerDAO2;
		this.subscriptionDAO = (subscriptionDAO)subscriptionDAO2;
	}

	 // 고객 만들기
	   public Customer CreateCustomerContent(Scanner scanner) {

	      System.out.println("1. 기존의 고객 정보 입력, 2. 새로운 고객 정보 입력");
	      int menuNum = 0;

	      while (true) {
	         if (!scanner.hasNextInt()) {
	            scanner.next();
	            System.err.println("숫자를 입력하시오 1. 기존의 고객 정보 입력, 2. 새로운 고객 정보 입력");
	         } else {
	            menuNum = scanner.nextInt();
	            if (!(menuNum > 0 && menuNum < 3)) {
	               System.err.println("범위를 벗어났습니다 1. 기존의 고객 정보 입력, 2. 새로운 고객 정보 입력");
	            } else {
	               break;
	            }
	         }
	      }

	      while (true) {
	         if (menuNum == 1) {
	            System.out.println("기존의 고객 정보 입력 시작합니다");
	            System.out.println("기존 고객의 ID을 입력하시오");
	            int OldcustomerID = scanner.nextInt();

	            if (contractDAO.CheckIntData("customerID", "Customer", OldcustomerID)) {

	               String InsuranceType = CreateSubscription(OldcustomerID, scanner);   
	               Customer Oldcustomer = contractDAO.findCustomer(OldcustomerID);
	               CreatePersonalInsuranceInformation(Oldcustomer, InsuranceType, scanner);
	            
	               return Oldcustomer;
	            } else {
	               System.out.println("해당하는 고객의 ID가 존재하지 않습니다.");
	            }
	         } else if (menuNum == 2) {
	            System.out.println("새로운 고객 정보 입력 시작합니다");

	            Customer customer = new Customer();

	            System.out.println("******고객 내용을 작성하십시오.******");
	            // 예외_System.out.println("고객 정보 입력에 실패하였습니다. 다시 시도하여 주십시오.");

	            int newCustomerID = contractDAO.SelectMaxID("customerID", "Customer") + 1;
	            System.out.println("고객ID는" + newCustomerID + "입니다.");
	            customer.setCustomerID(newCustomerID);
	            
	            System.out.println("2. 고객이름을 입력하시오.");
	            customer.setCustomerName(scanner.next());

	            System.out.println("3. 고객 연락처를 입력하시오.");
	            customer.setPhoneNum(scanner.next());

	            // insuranceDAO ///////////////////////////
	            (this.customerDAO).insertCustomer(customer);

	            // PersonalInfomation 시작
	            System.out.println("----개인정보를 작성하십시오.----");
	            PersonalInformation personalInformation = new PersonalInformation();

	            System.out.println("직업을 선택하시오 1.soldier, 2.driver, 3.officeWorker, "
	                  + "4.constructionLaborer, 5.fireman, 6.policeman, 7.theOther");

	            switch (scanner.nextInt()) {
	            case 1:
	               personalInformation.setJob(Job.soldier);
	               break;
	            case 2:
	               personalInformation.setJob(Job.driver);
	               break;
	            case 3:
	               personalInformation.setJob(Job.officeWorker);
	               break;
	            case 4:
	               personalInformation.setJob(Job.constructionLaborer);
	               break;
	            case 5:
	               personalInformation.setJob(Job.fireman);
	               break;
	            case 6:
	               personalInformation.setJob(Job.policeman);
	               break;
	            case 7:
	               personalInformation.setJob(Job.theOther);
	               break;
	            default:
	               break;
	            }

	            System.out.println("사고이력을 입력하시오");
	            personalInformation.setAccidentHistory(scanner.next());

	            System.out.println("계좌번호를 입력하시오");
	            while (true) {
	               if (!scanner.hasNextInt()) {
	                  scanner.next();
	                  System.err.println("숫자로 입력하시오");
	               } else {
	                  personalInformation.setAccountNumber(scanner.nextInt());
	                  break;
	               }
	            }

	            System.out.println("성별을 입력하시오 M, W");
	            if (scanner.next().equals("M")) {
	               personalInformation.setGender(true);
	            } else {
	               personalInformation.setGender(false);
	            }

	            System.out.println("자산을 입력하시오");
	            while (true) {
	               if (!scanner.hasNextInt()) {
	                  scanner.next();
	                  System.err.println("숫자로 입력하시오");
	               } else {
	                  personalInformation.setProperty(scanner.nextInt());
	                  break;
	               }
	            }

	            System.out.println("주민번호를 입력하시오");
	            personalInformation.setResidentRegistrationNumber(scanner.next());
	            

	            //Subscription에 데이터 삽입
	            String InsuranceType = CreateSubscription(newCustomerID, scanner);
	            // 보험 정보 입력
	            Customer Fcustomer = CreatePersonalInsuranceInformation(customer, InsuranceType, scanner);

	            // insuranceDAO /////////
	            customerDAO.insertPersonalInformation(personalInformation, customer.getCustomerID());
	            customerListImpl.add(Fcustomer);
	            
	            return Fcustomer;
	         }
	      }
	   }
	
	   private String CreateSubscription(int CustomerID, Scanner scanner) {
		      insuranceDAO.showAllInsuranceID();
		      System.out.println("****선택할 보험ID를 입력하세요*****");
		      int InsuranceID = 0;
		      
		      while (true) {
		         if (!scanner.hasNextInt()) {
		            scanner.next();
		            System.err.println("숫자를 입력하시오");
		         } else {
		            InsuranceID = scanner.nextInt();
		            if (!insuranceDAO.CheckIntData("InsuranceID", "Insurance", InsuranceID)) {
		               System.err.println("해당하는 보험 ID 존재하지 않습니다. 재입력하시오");
		            } else {
		               break;
		            }
		         }
		      }
		      
		      ////insuranceDAO 데이터 삽입
		      if(this.subscriptionDAO.insertSubscription(InsuranceID, CustomerID)) {
		      String InsuranceType = this.customerDAO.getInsuranceType(InsuranceID).toString();      
		         return InsuranceType;
		      }else {
		         return "not";
		      }
		   }
		   
		   private Customer CreatePersonalInsuranceInformation(Customer Scustomer, String insuranceType, Scanner scanner) {
		      Customer customer = Scustomer;
		      
		      switch (insuranceType) {
		      case "Fire":
		         System.out.println("*******화재 보험 정보를 입력합니다*********");
		         Building building = new Building();
		         
		         System.out.println("주소를 입력하시오");
		         building.setBuildingAddress(scanner.next());

		         System.out.println("건물 시세를 입력하시오");
		         building.setBuildingPrice(scanner.nextInt());

		         System.out.println("건물 규모를 입력하시오");
		         building.setBuildingScale(scanner.next());

		         customer.setPersonalInformation(building);
		         this.customerDAO.insertBuilding(building, customer.getCustomerID());

		         break;

		      case "Car":
		         System.out.println("*******자동차 보험 정보를 입력합니다*********");
		         Car car = new Car();
		         
		         System.out.println("차량 번호를 입력하시오");
		         car.setCarNumber(scanner.next());

		         System.out.println("차량 종류를 입력하시오 1.소형, 2.중형, 3.대형");
		         switch (scanner.nextInt()) {
		         case 1:
		            car.setCarType(CarType.Compact);
		            break;
		         case 2:
		            car.setCarType(CarType.Midsize);
		            break;
		         case 3:
		            car.setCarType(CarType.FullSize);
		            break;
		         default:
		            break;
		         }

		         System.out.println("운전 경력을 입력하시오");
		         car.setDrivingCareer(scanner.nextInt());

		         System.out.println("운전 면허 종류를 입력하시오 1. 1종 보통, 2. 2종 보통 , 3. 대형");
		         switch (scanner.nextInt()) {
		         case 1:
		            car.setLicenseType(LicenseType.Class1);
		            break;
		         case 2:
		            car.setLicenseType(LicenseType.Class2);
		            break;
		         case 3:
		            car.setLicenseType(LicenseType.HGV);
		            break;
		         default:
		            break;
		         }

		         customer.setPersonalInformation(car);
		         this.customerDAO.insertCar(car, customer.getCustomerID());
		         break;

		      case "ActualCost":
		         System.out.println("*******실비 보험 정보를 입력합니다*********");
		         
		         ActualCost actualCost = new ActualCost();

		         System.out.println("혈액형을 입력하시오 1. A, 2. B, 3. O, 4. AB, 5. RHMinus");
		         switch (scanner.nextInt()) {
		         case 1:
		            actualCost.setBloodType(BloodType.A);
		            break;
		         case 2:
		            actualCost.setBloodType(BloodType.B);
		            break;
		         case 3:
		            actualCost.setBloodType(BloodType.O);
		            break;
		         case 4:
		            actualCost.setBloodType(BloodType.AB);
		            break;
		         case 5:
		            actualCost.setBloodType(BloodType.RHMinus);
		            break;
		         default:
		            break;
		         }

		         System.out.println("질병을 선택하시오 1. 암, 2. 당뇨, 3. 고혈압,  4. 심근경색, 5. 뇌졸증, 6. 뇌출혈");
		         switch (scanner.nextInt()) {
		         case 1:
		            actualCost.setDiseaseHistory(DiseaseHistory.Cancer);
		            break;
		         case 2:
		            actualCost.setDiseaseHistory(DiseaseHistory.Diabetes);
		            break;
		         case 3:
		            actualCost.setDiseaseHistory(DiseaseHistory.Hypertension);
		            break;
		         case 4:
		            actualCost.setDiseaseHistory(DiseaseHistory.MyocardialInfarction);
		            break;
		         case 5:
		            actualCost.setDiseaseHistory(DiseaseHistory.Hyperlipidemia);
		            break;
		         case 6:
		            actualCost.setDiseaseHistory(DiseaseHistory.Stroke);
		            break;
		         default:
		            break;
		         }
		         
		         System.out.println("가족 질병을 입력합니다. 가족 관계를 입력하시오");
		         String Family = scanner.next();
		         System.out.println("가족의 질병을 선택하시오 1. 암, 2. 당뇨, 3. 고혈압,  4. 심근경색, 5. 뇌졸증, 6. 뇌출혈");
		         DiseaseHistory diseaseHistory = null;
		         
		         switch (scanner.nextInt()) {
		         case 1:
		            diseaseHistory = DiseaseHistory.Cancer;
		            break;
		         case 2:
		            diseaseHistory = DiseaseHistory.Diabetes;
		            break;
		         case 3:
		            diseaseHistory = DiseaseHistory.Hypertension;
		            break;
		         case 4:
		            diseaseHistory = DiseaseHistory.MyocardialInfarction;
		            break;
		         case 5:
		            diseaseHistory = DiseaseHistory.Hyperlipidemia;
		            break;
		         case 6:
		            diseaseHistory = DiseaseHistory.Stroke;
		            break;
		         default:
		            break;
		         }
		         
		         HashMap<String, String> A = new HashMap<String, String>();
		         A.put(Family, diseaseHistory.toString());
		         actualCost.setFamilyHistory(A);

		         customer.setPersonalInformation(actualCost);
		         this.customerDAO.insertActualCost(actualCost, customer.getCustomerID());
		         break;
		      
		      case "not":
		         System.err.println("이미 해당 보험을 가입한 고객이므로 상세 보험 정보 입력 실패 입니다");
		         break;
		         
		      default:
		         break;
		      }
		      return customer;
		   }

	private Insurance CreateInsurance(Insurance insurance, Scanner scanner) throws IOException {
		System.out.println("보험 상품 소개 정보 - 보험ID, 보험명, 보험설명서, 판매 메뉴얼을 작성하여 주십시오.");

		int insuranceID = this.contractDAO.SelectMaxID("insuranceID", "Insurance");
		if (insuranceID == 0) {
			insuranceID = 1000;
		}
		insuranceID = insuranceID + 1;
		insurance.setInsuranceID(insuranceID);
		System.out.println("1.보험 ID '" + insuranceID + "'이 생성되었습니다.");

		scanner.nextLine();

		System.out.println("2.보험이름을 입력하십시오.");
		String insuranceName = "";
		while (true) {
			insuranceName = scanner.nextLine();
			if (this.contractDAO.CheckStringData("insuranceName", "Insurance", insuranceName)) {
				System.err.println("보험이름을 다시 작성하십시오. 중복된 정보입니다.");
			} else {
				break;
			}
		}
		insurance.setInsuranceName(insuranceName);

		System.out.println("3.보험료를 입력하십시오.");
		while (!scanner.hasNextInt()) {
			scanner.nextLine();
			System.err.println("보험료를 다시 입력하십시오. 숫자를 입력해야 합니다.");
		}
		insurance.setInsuranceFee(scanner.nextInt());

		scanner.nextLine();

		System.out.println("5.보험설명서 내용을 입력하십시오.");
		insurance.setInsuranceManual(scanner.nextLine());

		System.out.println("7.판매메뉴얼 내용을 입력하십시오.");
		insurance.setInsuranceSalesManual(scanner.nextLine());
		return insurance;

	}

	private DamageInformation CreateDamageInformation(Scanner scanner, String string, String insuranceName)
			throws IOException {
		DamageInformation damageInformation = new DamageInformation();

		System.out.println(string + "의 보장액을 입력하십시오.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("보장액을 다시 입력하십시오. 숫자를 입력해야 합니다.");
		}
		damageInformation.setDamageGuaranteedAmount(scanner.nextInt());

		scanner.nextLine();

		System.out.println(string + "의 보장내용을 입력하십시오.");
		damageInformation.setDamageGuaranteedContent(scanner.nextLine());
		return damageInformation;
	}

	private Goods_Personal CreateGoods_Personal(Scanner scanner, String string, String insuranceName)
			throws IOException {
		Goods_Personal goods_Personal = new Goods_Personal();
		System.out.println(string + "의 구분을 선택하십시오.");
		System.out.println("1.사망, 2.부상, 3.후유장해");

		int goodNum = 0;
		while (true) {
			if (!scanner.hasNextInt()) {
				scanner.next();
				System.err.println("숫자를 입력하여 주십시오. 1.사망, 2.부상, 3.후유장해");
			} else {
				goodNum = scanner.nextInt();
				if (!(goodNum == 1 || goodNum == 2 || goodNum == 3)) {
					System.err.println("구분 중 하나를  선택하여 주십시오. 1.사망, 2.부상, 3.후유장해");
				} else {
					break;
				}
			}
		}
		switch (goodNum) {
		case 1:
			goods_Personal.setSeparation(GSeparation.Death);
			break;
		case 2:
			goods_Personal.setSeparation(GSeparation.Injury);
			break;
		case 3:
			goods_Personal.setSeparation(GSeparation.Aftereffect);
			break;
		default:
			break;
		}
		scanner.nextLine();

		System.out.println(string + "의 보장내용을 입력하십시오.");
		goods_Personal.setGuaranteeContent(scanner.nextLine());

		System.out.println(string + "의 지급 한도를 입력하시오.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("지급한도를 다시 입력하십시오. 숫자를 입력해야 합니다.");
		}
		goods_Personal.setProvisionLimit(scanner.nextInt());
		return goods_Personal;
	}

	private SelfVehicleDamage CreateSelfVehicleDamage(Scanner scanner, String string) {
		SelfVehicleDamage selfVehicleDamage = new SelfVehicleDamage();
		System.out.println(string + "의 구분을 선택하십시오.");
		System.out.println("1.자기 신체 사고, 2.자동차 상해");

		int selfNum = 0;
		while (true) {
			if (!scanner.hasNextInt()) {
				scanner.next();
				System.err.println("숫자를 입력하여 주십시오. 1.자기 신체 사고, 2.자동차 상해");
			} else {
				selfNum = scanner.nextInt();
				if (!(selfNum == 1 || selfNum == 2)) {
					System.err.println("구분 중 하나를  선택하여 주십시오. 1.자기 신체 사고, 2.자동차 상해");
				} else {
					break;
				}
			}
		}

		switch (selfNum) {
		case 1:
			selfVehicleDamage.setSeparation(SSeparation.SelfBodyAccident);
			break;
		case 2:
			selfVehicleDamage.setSeparation(SSeparation.CarInjury);
			break;
		default:
			break;
		}

		System.out.println(string + "의 부상 가입금액을 입력하십시오.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("부상 가입금액을 다시 입력하십시오. 숫자를 입력해야 합니다.");
		}
		selfVehicleDamage.setSubscriptionFeeForInjury(scanner.nextInt());

		System.out.println(string + "의 사고 후유장해의 가입 금액 입력하시오.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("사고 후유장해의 가입 금액을 다시 입력하십시오. 숫자를 입력해야 합니다.");
		}
		selfVehicleDamage.setSubscriptionFeeForAccidentalInjuries(scanner.nextInt());
		return selfVehicleDamage;

	}

	private Injury CreateInjury(Scanner scanner, String string, String insuranceName) throws IOException {
		Injury injury = new Injury();

		System.out.println(string + "의 지급 금액을 입력하십시오.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("지급한도를 다시 입력하십시오. 숫자를 입력해야 합니다.");
		}
		injury.setProvisionFee(scanner.nextInt());

		scanner.nextLine();

		System.out.println(string + "의 지급사유를 입력하십시오.");
		injury.setProvisionReason(scanner.nextLine());
		return injury;
	}

	public Insurance CreateInsuranceContent(Scanner scanner) {
		try {
			System.out.println("설계할 상품의 종류를  선택하여 주십시오.");
			System.out.println("1.화재보험, 2.자동차보험, 3.실비보험");
			int insuranceNum = 1;

			while (true) {
				if (!scanner.hasNextInt()) {
					scanner.nextLine();
					System.err.println("숫자를 입력하여 주십시오. 1.화재보험, 2.자동차보험, 3.실비보험");
				} else {
					insuranceNum = scanner.nextInt();
					if (!(insuranceNum == 1 || insuranceNum == 2 || insuranceNum == 3)) {
						System.err.println("상품의 종류 중 하나를  선택하여 주십시오. 1.화재보험, 2.자동차보험, 3.실비보험");
					} else {
						break;
					}
				}
			}

			switch (insuranceNum) {

			case 1:
				FireInsurance fireInsurance = new FireInsurance();
				System.out.println("***화재보험***");
				fireInsurance.setInsuranceType(Insurance.InsuranceType.Fire);
				fireInsurance = (FireInsurance) this.CreateInsurance(fireInsurance, scanner);
				System.out.println("손해 정보 - 보장액, 보장내용을 작성하여주십시오.");
				System.out.println("8.직접 손해");
				fireInsurance.setDirectDamage(
						this.CreateDamageInformation(scanner, "직접 손해", fireInsurance.getInsuranceName()));

				System.out.println("9.소방 손해");
				fireInsurance.setFireDamage(
						this.CreateDamageInformation(scanner, "소방 손해", fireInsurance.getInsuranceName()));

				System.out.println("10.피난 손해");
				fireInsurance.setRefugeDamage(
						this.CreateDamageInformation(scanner, "피난 손해", fireInsurance.getInsuranceName()));

				this.insuranceDAO.InsertInsurance(fireInsurance);
				this.insuranceDAO.InsertFireInsurance(fireInsurance);
				System.out.println("해당 상품 설계를 완료하였습니다.");
				return fireInsurance;

			case 2:
				CarInsurance carinsurance = new CarInsurance();
				System.out.println("***자동차보험***");
				carinsurance.setInsuranceType(Insurance.InsuranceType.Car);
				carinsurance = (CarInsurance) this.CreateInsurance(carinsurance, scanner);

				System.out.println("배상 정보 - 구분, 보장 내용, 지급 한도를 작성하여 주십시오.");
				System.out.println("8.대물배상");
				carinsurance.setGoodsIndemnification(
						this.CreateGoods_Personal(scanner, "대물 배상", carinsurance.getInsuranceName()));

				System.out.println("9.대인배상");
				carinsurance.setPersonalIndemnification(
						this.CreateGoods_Personal(scanner, "대인 배상", carinsurance.getInsuranceName()));

				System.out.println("손해 정보 - 구분, 부상 가입 금액, 사고 후유장해의 가입 금액을 작성하여 주십시오.");
				System.out.println("10.자기 차량 손해");
				carinsurance.setSelfVehicleDamage(this.CreateSelfVehicleDamage(scanner, "자기 차량 손해"));

				System.out.println("해당 상품 설계를 완료하였습니다.");
				this.insuranceDAO.InsertInsurance(carinsurance);
				this.insuranceDAO.InsertCarInsurance(carinsurance);
				return carinsurance;

			case 3:
				ActualCostInsurance actualinsurance = new ActualCostInsurance();
				System.out.println("***실비보험***");
				actualinsurance.setInsuranceType(Insurance.InsuranceType.ActualCost);
				actualinsurance = (ActualCostInsurance) this.CreateInsurance(actualinsurance, scanner);

				System.out.println("8.상해 입원");
				actualinsurance.setInjuryHospitalization(
						this.CreateInjury(scanner, "상해 입원", actualinsurance.getInsuranceName()));

				System.out.println("9.상해 통원");
				actualinsurance
						.setInjuryOutpatient(this.CreateInjury(scanner, "상해 통원", actualinsurance.getInsuranceName()));

				System.out.println("해당 상품 설계를 완료하였습니다.");
				this.insuranceDAO.InsertInsurance(actualinsurance);
				this.insuranceDAO.InsertActualCostInsurance(actualinsurance);
				return actualinsurance;

			default:
				break;
			}
		} catch (Exception e) {
			System.out.println("상품 설계에 실패했습니다. 다시 시도하여 주십시오.");
		}
		return null;
	}

	public boolean ViewAcceptanceGuide(Scanner scanner) {
		System.out.println("***인수 지침서 확인***");
		System.out.println("열람할 인수 지침서의 보험 종류를 선택하여 주십시오.");
		System.out.println("1.화재 보험, 2.자동차 보험, 3.실비 보험");

		int insuranceType = 1;
		while (true) {
			if (!scanner.hasNextInt()) {
				scanner.nextLine();
				System.err.println("숫자를 입력하여 주십시오. 1.화재보험, 2.자동차보험, 3.실비보험");
			} else {
				insuranceType = scanner.nextInt();
				if (!(insuranceType == 1 || insuranceType == 2 || insuranceType == 3)) {
					System.err.println("열람할 인수 지침서의 보험 종류 중 하나를  선택하여 주십시오. 1.화재보험, 2.자동차보험, 3.실비보험");
				} else {
					break;
				}
			}
		}
		switch (insuranceType) {
		case 1:
			this.acceptanceListImpl.setAcceptanceVector(
					this.acceptanceDAO.searchAcceptanceForInsurance((Insurance.InsuranceType.Fire).toString()));
			break;
		case 2:
			this.acceptanceListImpl.setAcceptanceVector(
					this.acceptanceDAO.searchAcceptanceForInsurance((Insurance.InsuranceType.Car).toString()));
			break;
		case 3:
			this.acceptanceListImpl.setAcceptanceVector(
					this.acceptanceDAO.searchAcceptanceForInsurance((Insurance.InsuranceType.ActualCost).toString()));
			break;
		default:
			break;
		}

		for (AcceptanceGuide acceptanceGuide : this.acceptanceListImpl.getAcceptanceVector()) {
			System.out.println("인수 지침서 ID: " + acceptanceGuide.getAcceptanceID());
			System.out.println("사기 사례: " + acceptanceGuide.getScamCase());
			System.out.println("위험 평가: " + acceptanceGuide.getRiskEvaluation());
			System.out.println("");
		}
		if (this.acceptanceListImpl.getAcceptanceVector().isEmpty()) {
			System.out.println("해당 보험ID의 인수 지침서가 존재하지 않습니다.");
		}
		return false;

	}

	public AcceptanceGuide CreateAcceptanceGuide(Scanner scanner) {
		AcceptanceGuide acceptanceGuide = new AcceptanceGuide();
		System.out.println("***인수 지침 등록***");
		System.out.println("인수 지침 정보 - 보험ID, 사고사례, 위험 평가를 작성하여 주십시오.");
		int acceptanceID = this.contractDAO.SelectMaxID("acceptanceID", "Acceptance");
		if (acceptanceID == 0) {
			acceptanceID = 5000;
		}
		acceptanceID = acceptanceID + 1;
		acceptanceGuide.setAcceptanceID(acceptanceID);
		System.out.println("1.인수지침서 ID '" + acceptanceID + "'이 생성되었습니다.");

		scanner.nextLine();

		System.out.println("2. 사고사례를 입력하여 주십시오.");
		acceptanceGuide.setScamCase(scanner.nextLine());

		System.out.println("3. 위험 평가를 선택하여 주십시오.");
		System.out.println("1. 하, 2. 중, 3.상");

		int riskNum = 1;
		while (true) {
			if (!scanner.hasNextInt()) {
				scanner.nextLine();
				System.err.println("숫자를 입력하여 주십시오. 1.하, 2.중, 3.상");
			} else {
				riskNum = scanner.nextInt();
				if (!(riskNum == 1 || riskNum == 2 || riskNum == 3)) {
					System.err.println("위험 평가 중 하나를  선택하여 주십시오. 1.하, 2.중, 3.상");
				} else {
					break;
				}
			}
		}
		switch (riskNum) {
		case 1:
			acceptanceGuide.setRiskEvaluation(RiskEvaluation.Low);
			break;
		case 2:
			acceptanceGuide.setRiskEvaluation(RiskEvaluation.Middle);
			break;
		case 3:
			acceptanceGuide.setRiskEvaluation(RiskEvaluation.High);
			break;
		default:
			break;
		}

		System.out.println("해당 인수 지침서의 보험ID를 입력하여 주십시오.");
		this.insuranceDAO.searchInsuranceIDandName();
		int insuranceID = 0;
		boolean check = false;
		while (!check) {
			if (!scanner.hasNextInt()) {
				scanner.nextLine();
				System.err.println("ID를 다시 입력하십시오. 숫자를 입력해야 합니다.");
			} else {
				insuranceID = scanner.nextInt();
				check = this.contractDAO.CheckIntData("insuranceID", "Insurance", insuranceID);
				if (!check) {
					System.err.println("존재하지 않는 보험ID입니다. 다시입력하여 주십시오.");
				}
			}
		}
		acceptanceGuide.setInsuranceID(insuranceID);
		this.acceptanceDAO.InsertAcceptanceGuide(acceptanceGuide);
		System.out.println("인수 지침 등록이 완료되었습니다.");
		return acceptanceGuide;
	}

	// 파라미터로 PremiumRate가 있으나 위의 insurancePremiumRate를 목적으로 넣은 것 같아서 삭제.
	public int CalculatePaymentAmount(int InsuranceID) {
		int PaymentAmount = 1;
		return PaymentAmount;
	}

	public float CalculatePremiumRate(int customerID, int FactorGuideID) {
		return this.insurancePremiumRate;
	}

	public boolean Accept(int insuranceID, int customerID, Scanner scanner){
	      AcceptanceGuide acceptanceGuide = this.acceptanceDAO.findAcceptance(insuranceID);
	      Customer customer = contractDAO.findCustomer(customerID);

	      //보험 종류에 따라 고객 가장 아래 데이터 가져오기
	      switch(this.customerDAO.getInsuranceType(insuranceID)) {
	      case Fire: //화재보험
	         Building building =  (Building) FirstAccept(acceptanceGuide, customer, insuranceID);
	         this.customerDAO.findBuildingCustomer(building, customerID);
	         System.out.println("건물주소:" + building.getBuildingAddress() + 
	               " 건물가격:" + building.getBuildingPrice() + " 건물규모:" + building.getBuildingScale());
	         FinalAccept(customer, scanner, insuranceID);
	         return true;

	      case Car://자동차보험
	         Car car = (Car) FirstAccept(acceptanceGuide, customer, insuranceID);
	         this.customerDAO.findCarCustomer(car, customerID);
	         System.out.println("차 번호:" + car.getCarNumber() +" 차 종류:" + car.getCarType() +
	               " 운전경력: " + car.getDrivingCareer()  + " 면허종류:" +car.getLicenseType());
	         FinalAccept(customer, scanner, insuranceID);
	         return true;
	         
	      case ActualCost://실비보험
	         ActualCost actualCost = (ActualCost) FirstAccept(acceptanceGuide, customer, insuranceID);
	         this.customerDAO.findActualCostCustomer(actualCost, customerID);
	         System.out.println("병력:"+ actualCost.getDiseaseHistory()+ " 가족병력:" + actualCost.getFamilyHistory()
	         + " 혈액형:" + actualCost.getBloodType());
	         FinalAccept(customer, scanner, insuranceID);
	         return true;
	         
	      default:
	         return false;
	      }
	   }
	 
	//보험 정보, 고객 기본 정보 가져오는 메소드
	   private PersonalInformation FirstAccept(AcceptanceGuide acceptanceGuide, Customer customer, int insuranceID) {
	      System.out.println("인수ID:" + acceptanceGuide.getAcceptanceID());
	      System.out.println(acceptanceGuide.getScamCase());
	      System.out.println("위험평가:" + acceptanceGuide.getRiskEvaluation());

	      // 고객에 대한 기본 정보 먼저 출력
	      System.out.println("고객ID:" + customer.getCustomerID()+ " 고객이름:" + customer.getCustomerName()+ " 고객전화번호:" + customer.getPhoneNum());
	      PersonalInformation personalInformation = this.customerDAO.findPersonalInformation(customer, insuranceID);
	      System.out.println("고객과거사고이력:"+ personalInformation.getAccidentHistory() + " 고객계좌번호:" + personalInformation.getAccountNumber()
	      + " 고객직업:" + personalInformation.getJob() + " 고객재산:" + personalInformation.getProperty() + 
	      " 고객주민등록번호:" + personalInformation.getResidentRegistrationNumber());

	      return personalInformation;
	   }

	   //인수 승인 or 거부 메소드
	   private void FinalAccept(Customer customer, Scanner scanner, int insuranceID) {
	      //위에 인수정책과 고객 정보를 토대로 승인 or 거부 선택
	      System.out.println("1.승인, 2.거부");
	      while(!scanner.hasNextInt()) {
	         scanner.next();
	         System.err.println("숫자를 입력해야 합니다.");
	      }
	      switch(scanner.nextInt()) {
	      case 1:
	         //인수 승인 됬다면 고객의 가입유무는 true(true, false를 보고 계약을 작성할지 말아야 할지 결정해야함 - alternate)
	         customer.setSubscriptionStatus(true);
	         this.subscriptionDAO.updateSubscriptionStatus(customer, insuranceID);	    
	         break;
	      case 2:
	         
	         System.out.println("승인 불가 이유를 작성하세요.");
	         String notAgree = scanner.next();
	         System.out.println("승인 불가 이유는 '"+notAgree+"'입니다.");
	         this.subscriptionDAO.deleteSubscription(customer, insuranceID);
	         
	      }
	   }

	public void ShowMenual(Scanner sc) {
		System.out.println("*******메뉴얼 확인하기****");
		System.out.println("1. 판매 메뉴얼 확인하기, 2. 상품 설명서 확인하기");
		int menuNum = 0;
		while (true) {
			if (!sc.hasNextInt()) {
				sc.next();
				System.err.println("숫자를 입력하시오.  1. 판매 메뉴얼 확인하기, 2. 상품 설명서 확인하기");
			} else {
				menuNum = sc.nextInt();
				if (!(menuNum > 0 && menuNum < 3)) {
					System.err.println("다음 메뉴 중 선택하시오.  1. 판매 메뉴얼 확인하기, 2. 상품 설명서 확인하기");
				} else {
					break;
				}
			}
		}
		// saleManual, manual 둘다 여기서 판단
		if (menuNum == 1) {
			System.out.println("*******판매 메뉴얼 확인하기********");
		} else {
			System.out.println("*******상품 메뉴얼 확인하기********");
		}
		this.InsuranceManual(sc, menuNum);
	}

	public void InsuranceManual(Scanner sc, int menuNum) {
		this.insuranceDAO.searchInsuranceIDandName();
		System.out.println("확인할 보험 ID를 입력하시오.");
		int InsuranceID = 0;
		while (true) {
			if (!sc.hasNextInt()) {
				sc.next();
				System.err.println("숫자를 입력하시오.");
			} else {
				InsuranceID = sc.nextInt();
				if (!(insuranceDAO.searchInsuranceIDforManual(InsuranceID))) {
					System.err.println("해당하는 보험ID 혹은 메뉴얼이 존재하지 않습니다");
				} else {
					break;
				}
			}
		}
		// DAO///////////////
		if (menuNum == 1) {
			insuranceDAO.searchInsuranceSalesManual(InsuranceID);
		} else {
			insuranceDAO.searchInsuranceManual(InsuranceID);
		}
	}
	
	public float CalculatePremiumRateOfFire(float buildingP, float insuranceFee) {
        float insurancePremiumRate = insuranceFee;
        // 건물의 가격은 1억단위로 계산.
        float buildingPrice = buildingP / 100000000;
        if (buildingPrice > 50) {
           insurancePremiumRate = (float)(insurancePremiumRate * 6.0);
        } else if (buildingPrice > 45) {
           insurancePremiumRate = (float)(insurancePremiumRate * 5.5);
        } else if (buildingPrice > 40) {
           insurancePremiumRate = (float)(insurancePremiumRate * 5.0);
        } else if (buildingPrice > 35) {
           insurancePremiumRate = (float)(insurancePremiumRate * 4.5);
        } else if (buildingPrice > 30) {
           insurancePremiumRate = (float)(insurancePremiumRate * 4.0);
        } else if (buildingPrice > 25) {
           insurancePremiumRate = (float)(insurancePremiumRate * 3.5);
        } else if (buildingPrice > 20) {
           insurancePremiumRate = (float)(insurancePremiumRate * 3.0);
        } else if (buildingPrice > 15) {
           insurancePremiumRate = (float)(insurancePremiumRate * 2.5);
        } else if (buildingPrice > 10) {
           insurancePremiumRate = (float)(insurancePremiumRate * 2.0);
        } else if (buildingPrice > 5) {
           insurancePremiumRate = (float)(insurancePremiumRate * 1.5);
        } else if (buildingPrice <= 5) {
           insurancePremiumRate = (float)(insurancePremiumRate * 1.0);
        }      
        return insurancePremiumRate;
     }
     
     public float CalculatePremiumRateCar(Car car, float insuranceFee) {
        float insurancePremiumRate = insuranceFee;
        
        switch (car.getJob()) {
        case soldier:
           insurancePremiumRate = (float) (insurancePremiumRate * 0.7);
           break;      
        case constructionLaborer:
           insurancePremiumRate = (float) (insurancePremiumRate * 0.8);   
           break;
        case driver:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
           break;
        case fireman:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.1);
           break;
        case officeWorker:
           insurancePremiumRate = (float) (insurancePremiumRate * 0.7);
           break;
        case policeman:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.2);
           break;
        case theOther:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.0);
           break;
        default:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.0);
           break; 
        }
        if(car.getGender()) {
           insurancePremiumRate = (float) (insurancePremiumRate * 0.8);
        } else if (!car.getGender()) {
           insurancePremiumRate = (float) (insurancePremiumRate * 1.2);
        }
        
        switch(car.getCarType()) {
        case FullSize :
           insurancePremiumRate = (float) (insurancePremiumRate * 1.3);
           break;
        case Compact:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.0);
           break;
        case Midsize:
           insurancePremiumRate = (float) (insurancePremiumRate * 0.7);
           break;
        default:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.0);
           break;
        }
        
        switch(car.getLicenseType()) {
        case Class1:
           insurancePremiumRate = (float) (insurancePremiumRate * 0.8);
           break;
        case Class2:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.1);
           break;
        case HGV:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.1);
           break;
        default:
           insurancePremiumRate = (float) (insurancePremiumRate * 1.0);
           break;
        }
        
        if(car.getDrivingCareer() > 20) {
           insurancePremiumRate = (float) (insurancePremiumRate * 0.5);
        } else if (car.getDrivingCareer() > 15) {
           insurancePremiumRate = (float) (insurancePremiumRate * 0.75);
        } else if (car.getDrivingCareer() > 10) {
           insurancePremiumRate = (float) (insurancePremiumRate * 1.0);
        } else if (car.getDrivingCareer() > 5) {
           insurancePremiumRate = (float) (insurancePremiumRate * 1.25);
        } else if (car.getDrivingCareer() <= 5) {
           insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
        }
        
        return insurancePremiumRate;
     }

     public float CalculatePremiumRateActual(ActualCost actualCost, float insuranceFee) {
    	 float insurancePremiumRate =  insuranceFee;

    	 switch (actualCost.getJob()) {
    	 case soldier:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 0.7);
    		 break;
    	 case constructionLaborer:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.3);
    		 break;
    	 case driver:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.1);
    		 break;
    	 case fireman:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.2);
    		 break;
    	 case officeWorker:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 0.9);
    		 break;
    	 case policeman:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.3);
    		 break;
    	 case theOther:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.0);
    		 break;
    	 default:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.0);
    		 break; 
    	 }
    	 if(actualCost.getGender()) {
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.1);
    	 } else if (!actualCost.getGender()) {
    		 insurancePremiumRate = (float) (insurancePremiumRate * 0.9);
    	 }

    	 switch (actualCost.getBloodType()) {
    	 case A:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 0.8);
    		 break;
    	 case AB:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.2);
    		 break;
    	 case B:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.1);
    		 break;
    	 case O:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 0.9);
    		 break;
    	 case RHMinus:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
    		 break;
    	 default:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.0);
    		 break;
    	 }

    	 switch(actualCost.getDiseaseHistory()) {
    	 case Cancer:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.7);
    		 break;
    	 case Diabetes:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
    		 break;
    	 case Hyperlipidemia:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.2);
    		 break;
    	 case Hypertension:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.6);
    		 break;
    	 case MyocardialInfarction:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
    		 break;
    	 case Stroke:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
    		 break;
    	 default:
    		 insurancePremiumRate = (float) (insurancePremiumRate * 0.8);
    		 break;
    	 }

    	 if(actualCost.getFamilyHistory().containsKey("부")) {
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
    	 } else if(actualCost.getFamilyHistory().containsKey("모")){
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
    	 } else {
    		 insurancePremiumRate = (float) (insurancePremiumRate * 0.8);
    	 }
    	 return insurancePremiumRate;
     }

}