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

	 // �� �����
	   public Customer CreateCustomerContent(Scanner scanner) {

	      System.out.println("1. ������ �� ���� �Է�, 2. ���ο� �� ���� �Է�");
	      int menuNum = 0;

	      while (true) {
	         if (!scanner.hasNextInt()) {
	            scanner.next();
	            System.err.println("���ڸ� �Է��Ͻÿ� 1. ������ �� ���� �Է�, 2. ���ο� �� ���� �Է�");
	         } else {
	            menuNum = scanner.nextInt();
	            if (!(menuNum > 0 && menuNum < 3)) {
	               System.err.println("������ ������ϴ� 1. ������ �� ���� �Է�, 2. ���ο� �� ���� �Է�");
	            } else {
	               break;
	            }
	         }
	      }

	      while (true) {
	         if (menuNum == 1) {
	            System.out.println("������ �� ���� �Է� �����մϴ�");
	            System.out.println("���� ���� ID�� �Է��Ͻÿ�");
	            int OldcustomerID = scanner.nextInt();

	            if (contractDAO.CheckIntData("customerID", "Customer", OldcustomerID)) {

	               String InsuranceType = CreateSubscription(OldcustomerID, scanner);   
	               Customer Oldcustomer = contractDAO.findCustomer(OldcustomerID);
	               CreatePersonalInsuranceInformation(Oldcustomer, InsuranceType, scanner);
	            
	               return Oldcustomer;
	            } else {
	               System.out.println("�ش��ϴ� ���� ID�� �������� �ʽ��ϴ�.");
	            }
	         } else if (menuNum == 2) {
	            System.out.println("���ο� �� ���� �Է� �����մϴ�");

	            Customer customer = new Customer();

	            System.out.println("******�� ������ �ۼ��Ͻʽÿ�.******");
	            // ����_System.out.println("�� ���� �Է¿� �����Ͽ����ϴ�. �ٽ� �õ��Ͽ� �ֽʽÿ�.");

	            int newCustomerID = contractDAO.SelectMaxID("customerID", "Customer") + 1;
	            System.out.println("��ID��" + newCustomerID + "�Դϴ�.");
	            customer.setCustomerID(newCustomerID);
	            
	            System.out.println("2. ���̸��� �Է��Ͻÿ�.");
	            customer.setCustomerName(scanner.next());

	            System.out.println("3. �� ����ó�� �Է��Ͻÿ�.");
	            customer.setPhoneNum(scanner.next());

	            // insuranceDAO ///////////////////////////
	            (this.customerDAO).insertCustomer(customer);

	            // PersonalInfomation ����
	            System.out.println("----���������� �ۼ��Ͻʽÿ�.----");
	            PersonalInformation personalInformation = new PersonalInformation();

	            System.out.println("������ �����Ͻÿ� 1.soldier, 2.driver, 3.officeWorker, "
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

	            System.out.println("����̷��� �Է��Ͻÿ�");
	            personalInformation.setAccidentHistory(scanner.next());

	            System.out.println("���¹�ȣ�� �Է��Ͻÿ�");
	            while (true) {
	               if (!scanner.hasNextInt()) {
	                  scanner.next();
	                  System.err.println("���ڷ� �Է��Ͻÿ�");
	               } else {
	                  personalInformation.setAccountNumber(scanner.nextInt());
	                  break;
	               }
	            }

	            System.out.println("������ �Է��Ͻÿ� M, W");
	            if (scanner.next().equals("M")) {
	               personalInformation.setGender(true);
	            } else {
	               personalInformation.setGender(false);
	            }

	            System.out.println("�ڻ��� �Է��Ͻÿ�");
	            while (true) {
	               if (!scanner.hasNextInt()) {
	                  scanner.next();
	                  System.err.println("���ڷ� �Է��Ͻÿ�");
	               } else {
	                  personalInformation.setProperty(scanner.nextInt());
	                  break;
	               }
	            }

	            System.out.println("�ֹι�ȣ�� �Է��Ͻÿ�");
	            personalInformation.setResidentRegistrationNumber(scanner.next());
	            

	            //Subscription�� ������ ����
	            String InsuranceType = CreateSubscription(newCustomerID, scanner);
	            // ���� ���� �Է�
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
		      System.out.println("****������ ����ID�� �Է��ϼ���*****");
		      int InsuranceID = 0;
		      
		      while (true) {
		         if (!scanner.hasNextInt()) {
		            scanner.next();
		            System.err.println("���ڸ� �Է��Ͻÿ�");
		         } else {
		            InsuranceID = scanner.nextInt();
		            if (!insuranceDAO.CheckIntData("InsuranceID", "Insurance", InsuranceID)) {
		               System.err.println("�ش��ϴ� ���� ID �������� �ʽ��ϴ�. ���Է��Ͻÿ�");
		            } else {
		               break;
		            }
		         }
		      }
		      
		      ////insuranceDAO ������ ����
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
		         System.out.println("*******ȭ�� ���� ������ �Է��մϴ�*********");
		         Building building = new Building();
		         
		         System.out.println("�ּҸ� �Է��Ͻÿ�");
		         building.setBuildingAddress(scanner.next());

		         System.out.println("�ǹ� �ü��� �Է��Ͻÿ�");
		         building.setBuildingPrice(scanner.nextInt());

		         System.out.println("�ǹ� �Ը� �Է��Ͻÿ�");
		         building.setBuildingScale(scanner.next());

		         customer.setPersonalInformation(building);
		         this.customerDAO.insertBuilding(building, customer.getCustomerID());

		         break;

		      case "Car":
		         System.out.println("*******�ڵ��� ���� ������ �Է��մϴ�*********");
		         Car car = new Car();
		         
		         System.out.println("���� ��ȣ�� �Է��Ͻÿ�");
		         car.setCarNumber(scanner.next());

		         System.out.println("���� ������ �Է��Ͻÿ� 1.����, 2.����, 3.����");
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

		         System.out.println("���� ����� �Է��Ͻÿ�");
		         car.setDrivingCareer(scanner.nextInt());

		         System.out.println("���� ���� ������ �Է��Ͻÿ� 1. 1�� ����, 2. 2�� ���� , 3. ����");
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
		         System.out.println("*******�Ǻ� ���� ������ �Է��մϴ�*********");
		         
		         ActualCost actualCost = new ActualCost();

		         System.out.println("�������� �Է��Ͻÿ� 1. A, 2. B, 3. O, 4. AB, 5. RHMinus");
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

		         System.out.println("������ �����Ͻÿ� 1. ��, 2. �索, 3. ������,  4. �ɱٰ��, 5. ������, 6. ������");
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
		         
		         System.out.println("���� ������ �Է��մϴ�. ���� ���踦 �Է��Ͻÿ�");
		         String Family = scanner.next();
		         System.out.println("������ ������ �����Ͻÿ� 1. ��, 2. �索, 3. ������,  4. �ɱٰ��, 5. ������, 6. ������");
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
		         System.err.println("�̹� �ش� ������ ������ ���̹Ƿ� �� ���� ���� �Է� ���� �Դϴ�");
		         break;
		         
		      default:
		         break;
		      }
		      return customer;
		   }

	private Insurance CreateInsurance(Insurance insurance, Scanner scanner) throws IOException {
		System.out.println("���� ��ǰ �Ұ� ���� - ����ID, �����, ���輳��, �Ǹ� �޴����� �ۼ��Ͽ� �ֽʽÿ�.");

		int insuranceID = this.contractDAO.SelectMaxID("insuranceID", "Insurance");
		if (insuranceID == 0) {
			insuranceID = 1000;
		}
		insuranceID = insuranceID + 1;
		insurance.setInsuranceID(insuranceID);
		System.out.println("1.���� ID '" + insuranceID + "'�� �����Ǿ����ϴ�.");

		scanner.nextLine();

		System.out.println("2.�����̸��� �Է��Ͻʽÿ�.");
		String insuranceName = "";
		while (true) {
			insuranceName = scanner.nextLine();
			if (this.contractDAO.CheckStringData("insuranceName", "Insurance", insuranceName)) {
				System.err.println("�����̸��� �ٽ� �ۼ��Ͻʽÿ�. �ߺ��� �����Դϴ�.");
			} else {
				break;
			}
		}
		insurance.setInsuranceName(insuranceName);

		System.out.println("3.����Ḧ �Է��Ͻʽÿ�.");
		while (!scanner.hasNextInt()) {
			scanner.nextLine();
			System.err.println("����Ḧ �ٽ� �Է��Ͻʽÿ�. ���ڸ� �Է��ؾ� �մϴ�.");
		}
		insurance.setInsuranceFee(scanner.nextInt());

		scanner.nextLine();

		System.out.println("5.���輳�� ������ �Է��Ͻʽÿ�.");
		insurance.setInsuranceManual(scanner.nextLine());

		System.out.println("7.�ǸŸ޴��� ������ �Է��Ͻʽÿ�.");
		insurance.setInsuranceSalesManual(scanner.nextLine());
		return insurance;

	}

	private DamageInformation CreateDamageInformation(Scanner scanner, String string, String insuranceName)
			throws IOException {
		DamageInformation damageInformation = new DamageInformation();

		System.out.println(string + "�� ������� �Է��Ͻʽÿ�.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("������� �ٽ� �Է��Ͻʽÿ�. ���ڸ� �Է��ؾ� �մϴ�.");
		}
		damageInformation.setDamageGuaranteedAmount(scanner.nextInt());

		scanner.nextLine();

		System.out.println(string + "�� ���峻���� �Է��Ͻʽÿ�.");
		damageInformation.setDamageGuaranteedContent(scanner.nextLine());
		return damageInformation;
	}

	private Goods_Personal CreateGoods_Personal(Scanner scanner, String string, String insuranceName)
			throws IOException {
		Goods_Personal goods_Personal = new Goods_Personal();
		System.out.println(string + "�� ������ �����Ͻʽÿ�.");
		System.out.println("1.���, 2.�λ�, 3.��������");

		int goodNum = 0;
		while (true) {
			if (!scanner.hasNextInt()) {
				scanner.next();
				System.err.println("���ڸ� �Է��Ͽ� �ֽʽÿ�. 1.���, 2.�λ�, 3.��������");
			} else {
				goodNum = scanner.nextInt();
				if (!(goodNum == 1 || goodNum == 2 || goodNum == 3)) {
					System.err.println("���� �� �ϳ���  �����Ͽ� �ֽʽÿ�. 1.���, 2.�λ�, 3.��������");
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

		System.out.println(string + "�� ���峻���� �Է��Ͻʽÿ�.");
		goods_Personal.setGuaranteeContent(scanner.nextLine());

		System.out.println(string + "�� ���� �ѵ��� �Է��Ͻÿ�.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("�����ѵ��� �ٽ� �Է��Ͻʽÿ�. ���ڸ� �Է��ؾ� �մϴ�.");
		}
		goods_Personal.setProvisionLimit(scanner.nextInt());
		return goods_Personal;
	}

	private SelfVehicleDamage CreateSelfVehicleDamage(Scanner scanner, String string) {
		SelfVehicleDamage selfVehicleDamage = new SelfVehicleDamage();
		System.out.println(string + "�� ������ �����Ͻʽÿ�.");
		System.out.println("1.�ڱ� ��ü ���, 2.�ڵ��� ����");

		int selfNum = 0;
		while (true) {
			if (!scanner.hasNextInt()) {
				scanner.next();
				System.err.println("���ڸ� �Է��Ͽ� �ֽʽÿ�. 1.�ڱ� ��ü ���, 2.�ڵ��� ����");
			} else {
				selfNum = scanner.nextInt();
				if (!(selfNum == 1 || selfNum == 2)) {
					System.err.println("���� �� �ϳ���  �����Ͽ� �ֽʽÿ�. 1.�ڱ� ��ü ���, 2.�ڵ��� ����");
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

		System.out.println(string + "�� �λ� ���Աݾ��� �Է��Ͻʽÿ�.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("�λ� ���Աݾ��� �ٽ� �Է��Ͻʽÿ�. ���ڸ� �Է��ؾ� �մϴ�.");
		}
		selfVehicleDamage.setSubscriptionFeeForInjury(scanner.nextInt());

		System.out.println(string + "�� ��� ���������� ���� �ݾ� �Է��Ͻÿ�.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("��� ���������� ���� �ݾ��� �ٽ� �Է��Ͻʽÿ�. ���ڸ� �Է��ؾ� �մϴ�.");
		}
		selfVehicleDamage.setSubscriptionFeeForAccidentalInjuries(scanner.nextInt());
		return selfVehicleDamage;

	}

	private Injury CreateInjury(Scanner scanner, String string, String insuranceName) throws IOException {
		Injury injury = new Injury();

		System.out.println(string + "�� ���� �ݾ��� �Է��Ͻʽÿ�.");
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.err.println("�����ѵ��� �ٽ� �Է��Ͻʽÿ�. ���ڸ� �Է��ؾ� �մϴ�.");
		}
		injury.setProvisionFee(scanner.nextInt());

		scanner.nextLine();

		System.out.println(string + "�� ���޻����� �Է��Ͻʽÿ�.");
		injury.setProvisionReason(scanner.nextLine());
		return injury;
	}

	public Insurance CreateInsuranceContent(Scanner scanner) {
		try {
			System.out.println("������ ��ǰ�� ������  �����Ͽ� �ֽʽÿ�.");
			System.out.println("1.ȭ�纸��, 2.�ڵ�������, 3.�Ǻ���");
			int insuranceNum = 1;

			while (true) {
				if (!scanner.hasNextInt()) {
					scanner.nextLine();
					System.err.println("���ڸ� �Է��Ͽ� �ֽʽÿ�. 1.ȭ�纸��, 2.�ڵ�������, 3.�Ǻ���");
				} else {
					insuranceNum = scanner.nextInt();
					if (!(insuranceNum == 1 || insuranceNum == 2 || insuranceNum == 3)) {
						System.err.println("��ǰ�� ���� �� �ϳ���  �����Ͽ� �ֽʽÿ�. 1.ȭ�纸��, 2.�ڵ�������, 3.�Ǻ���");
					} else {
						break;
					}
				}
			}

			switch (insuranceNum) {

			case 1:
				FireInsurance fireInsurance = new FireInsurance();
				System.out.println("***ȭ�纸��***");
				fireInsurance.setInsuranceType(Insurance.InsuranceType.Fire);
				fireInsurance = (FireInsurance) this.CreateInsurance(fireInsurance, scanner);
				System.out.println("���� ���� - �����, ���峻���� �ۼ��Ͽ��ֽʽÿ�.");
				System.out.println("8.���� ����");
				fireInsurance.setDirectDamage(
						this.CreateDamageInformation(scanner, "���� ����", fireInsurance.getInsuranceName()));

				System.out.println("9.�ҹ� ����");
				fireInsurance.setFireDamage(
						this.CreateDamageInformation(scanner, "�ҹ� ����", fireInsurance.getInsuranceName()));

				System.out.println("10.�ǳ� ����");
				fireInsurance.setRefugeDamage(
						this.CreateDamageInformation(scanner, "�ǳ� ����", fireInsurance.getInsuranceName()));

				this.insuranceDAO.InsertInsurance(fireInsurance);
				this.insuranceDAO.InsertFireInsurance(fireInsurance);
				System.out.println("�ش� ��ǰ ���踦 �Ϸ��Ͽ����ϴ�.");
				return fireInsurance;

			case 2:
				CarInsurance carinsurance = new CarInsurance();
				System.out.println("***�ڵ�������***");
				carinsurance.setInsuranceType(Insurance.InsuranceType.Car);
				carinsurance = (CarInsurance) this.CreateInsurance(carinsurance, scanner);

				System.out.println("��� ���� - ����, ���� ����, ���� �ѵ��� �ۼ��Ͽ� �ֽʽÿ�.");
				System.out.println("8.�빰���");
				carinsurance.setGoodsIndemnification(
						this.CreateGoods_Personal(scanner, "�빰 ���", carinsurance.getInsuranceName()));

				System.out.println("9.���ι��");
				carinsurance.setPersonalIndemnification(
						this.CreateGoods_Personal(scanner, "���� ���", carinsurance.getInsuranceName()));

				System.out.println("���� ���� - ����, �λ� ���� �ݾ�, ��� ���������� ���� �ݾ��� �ۼ��Ͽ� �ֽʽÿ�.");
				System.out.println("10.�ڱ� ���� ����");
				carinsurance.setSelfVehicleDamage(this.CreateSelfVehicleDamage(scanner, "�ڱ� ���� ����"));

				System.out.println("�ش� ��ǰ ���踦 �Ϸ��Ͽ����ϴ�.");
				this.insuranceDAO.InsertInsurance(carinsurance);
				this.insuranceDAO.InsertCarInsurance(carinsurance);
				return carinsurance;

			case 3:
				ActualCostInsurance actualinsurance = new ActualCostInsurance();
				System.out.println("***�Ǻ���***");
				actualinsurance.setInsuranceType(Insurance.InsuranceType.ActualCost);
				actualinsurance = (ActualCostInsurance) this.CreateInsurance(actualinsurance, scanner);

				System.out.println("8.���� �Կ�");
				actualinsurance.setInjuryHospitalization(
						this.CreateInjury(scanner, "���� �Կ�", actualinsurance.getInsuranceName()));

				System.out.println("9.���� ���");
				actualinsurance
						.setInjuryOutpatient(this.CreateInjury(scanner, "���� ���", actualinsurance.getInsuranceName()));

				System.out.println("�ش� ��ǰ ���踦 �Ϸ��Ͽ����ϴ�.");
				this.insuranceDAO.InsertInsurance(actualinsurance);
				this.insuranceDAO.InsertActualCostInsurance(actualinsurance);
				return actualinsurance;

			default:
				break;
			}
		} catch (Exception e) {
			System.out.println("��ǰ ���迡 �����߽��ϴ�. �ٽ� �õ��Ͽ� �ֽʽÿ�.");
		}
		return null;
	}

	public boolean ViewAcceptanceGuide(Scanner scanner) {
		System.out.println("***�μ� ��ħ�� Ȯ��***");
		System.out.println("������ �μ� ��ħ���� ���� ������ �����Ͽ� �ֽʽÿ�.");
		System.out.println("1.ȭ�� ����, 2.�ڵ��� ����, 3.�Ǻ� ����");

		int insuranceType = 1;
		while (true) {
			if (!scanner.hasNextInt()) {
				scanner.nextLine();
				System.err.println("���ڸ� �Է��Ͽ� �ֽʽÿ�. 1.ȭ�纸��, 2.�ڵ�������, 3.�Ǻ���");
			} else {
				insuranceType = scanner.nextInt();
				if (!(insuranceType == 1 || insuranceType == 2 || insuranceType == 3)) {
					System.err.println("������ �μ� ��ħ���� ���� ���� �� �ϳ���  �����Ͽ� �ֽʽÿ�. 1.ȭ�纸��, 2.�ڵ�������, 3.�Ǻ���");
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
			System.out.println("�μ� ��ħ�� ID: " + acceptanceGuide.getAcceptanceID());
			System.out.println("��� ���: " + acceptanceGuide.getScamCase());
			System.out.println("���� ��: " + acceptanceGuide.getRiskEvaluation());
			System.out.println("");
		}
		if (this.acceptanceListImpl.getAcceptanceVector().isEmpty()) {
			System.out.println("�ش� ����ID�� �μ� ��ħ���� �������� �ʽ��ϴ�.");
		}
		return false;

	}

	public AcceptanceGuide CreateAcceptanceGuide(Scanner scanner) {
		AcceptanceGuide acceptanceGuide = new AcceptanceGuide();
		System.out.println("***�μ� ��ħ ���***");
		System.out.println("�μ� ��ħ ���� - ����ID, �����, ���� �򰡸� �ۼ��Ͽ� �ֽʽÿ�.");
		int acceptanceID = this.contractDAO.SelectMaxID("acceptanceID", "Acceptance");
		if (acceptanceID == 0) {
			acceptanceID = 5000;
		}
		acceptanceID = acceptanceID + 1;
		acceptanceGuide.setAcceptanceID(acceptanceID);
		System.out.println("1.�μ���ħ�� ID '" + acceptanceID + "'�� �����Ǿ����ϴ�.");

		scanner.nextLine();

		System.out.println("2. ����ʸ� �Է��Ͽ� �ֽʽÿ�.");
		acceptanceGuide.setScamCase(scanner.nextLine());

		System.out.println("3. ���� �򰡸� �����Ͽ� �ֽʽÿ�.");
		System.out.println("1. ��, 2. ��, 3.��");

		int riskNum = 1;
		while (true) {
			if (!scanner.hasNextInt()) {
				scanner.nextLine();
				System.err.println("���ڸ� �Է��Ͽ� �ֽʽÿ�. 1.��, 2.��, 3.��");
			} else {
				riskNum = scanner.nextInt();
				if (!(riskNum == 1 || riskNum == 2 || riskNum == 3)) {
					System.err.println("���� �� �� �ϳ���  �����Ͽ� �ֽʽÿ�. 1.��, 2.��, 3.��");
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

		System.out.println("�ش� �μ� ��ħ���� ����ID�� �Է��Ͽ� �ֽʽÿ�.");
		this.insuranceDAO.searchInsuranceIDandName();
		int insuranceID = 0;
		boolean check = false;
		while (!check) {
			if (!scanner.hasNextInt()) {
				scanner.nextLine();
				System.err.println("ID�� �ٽ� �Է��Ͻʽÿ�. ���ڸ� �Է��ؾ� �մϴ�.");
			} else {
				insuranceID = scanner.nextInt();
				check = this.contractDAO.CheckIntData("insuranceID", "Insurance", insuranceID);
				if (!check) {
					System.err.println("�������� �ʴ� ����ID�Դϴ�. �ٽ��Է��Ͽ� �ֽʽÿ�.");
				}
			}
		}
		acceptanceGuide.setInsuranceID(insuranceID);
		this.acceptanceDAO.InsertAcceptanceGuide(acceptanceGuide);
		System.out.println("�μ� ��ħ ����� �Ϸ�Ǿ����ϴ�.");
		return acceptanceGuide;
	}

	// �Ķ���ͷ� PremiumRate�� ������ ���� insurancePremiumRate�� �������� ���� �� ���Ƽ� ����.
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

	      //���� ������ ���� �� ���� �Ʒ� ������ ��������
	      switch(this.customerDAO.getInsuranceType(insuranceID)) {
	      case Fire: //ȭ�纸��
	         Building building =  (Building) FirstAccept(acceptanceGuide, customer, insuranceID);
	         this.customerDAO.findBuildingCustomer(building, customerID);
	         System.out.println("�ǹ��ּ�:" + building.getBuildingAddress() + 
	               " �ǹ�����:" + building.getBuildingPrice() + " �ǹ��Ը�:" + building.getBuildingScale());
	         FinalAccept(customer, scanner, insuranceID);
	         return true;

	      case Car://�ڵ�������
	         Car car = (Car) FirstAccept(acceptanceGuide, customer, insuranceID);
	         this.customerDAO.findCarCustomer(car, customerID);
	         System.out.println("�� ��ȣ:" + car.getCarNumber() +" �� ����:" + car.getCarType() +
	               " �������: " + car.getDrivingCareer()  + " ��������:" +car.getLicenseType());
	         FinalAccept(customer, scanner, insuranceID);
	         return true;
	         
	      case ActualCost://�Ǻ���
	         ActualCost actualCost = (ActualCost) FirstAccept(acceptanceGuide, customer, insuranceID);
	         this.customerDAO.findActualCostCustomer(actualCost, customerID);
	         System.out.println("����:"+ actualCost.getDiseaseHistory()+ " ��������:" + actualCost.getFamilyHistory()
	         + " ������:" + actualCost.getBloodType());
	         FinalAccept(customer, scanner, insuranceID);
	         return true;
	         
	      default:
	         return false;
	      }
	   }
	 
	//���� ����, �� �⺻ ���� �������� �޼ҵ�
	   private PersonalInformation FirstAccept(AcceptanceGuide acceptanceGuide, Customer customer, int insuranceID) {
	      System.out.println("�μ�ID:" + acceptanceGuide.getAcceptanceID());
	      System.out.println(acceptanceGuide.getScamCase());
	      System.out.println("������:" + acceptanceGuide.getRiskEvaluation());

	      // ���� ���� �⺻ ���� ���� ���
	      System.out.println("��ID:" + customer.getCustomerID()+ " ���̸�:" + customer.getCustomerName()+ " ����ȭ��ȣ:" + customer.getPhoneNum());
	      PersonalInformation personalInformation = this.customerDAO.findPersonalInformation(customer, insuranceID);
	      System.out.println("�����Ż���̷�:"+ personalInformation.getAccidentHistory() + " �����¹�ȣ:" + personalInformation.getAccountNumber()
	      + " ������:" + personalInformation.getJob() + " �����:" + personalInformation.getProperty() + 
	      " ���ֹε�Ϲ�ȣ:" + personalInformation.getResidentRegistrationNumber());

	      return personalInformation;
	   }

	   //�μ� ���� or �ź� �޼ҵ�
	   private void FinalAccept(Customer customer, Scanner scanner, int insuranceID) {
	      //���� �μ���å�� �� ������ ���� ���� or �ź� ����
	      System.out.println("1.����, 2.�ź�");
	      while(!scanner.hasNextInt()) {
	         scanner.next();
	         System.err.println("���ڸ� �Է��ؾ� �մϴ�.");
	      }
	      switch(scanner.nextInt()) {
	      case 1:
	         //�μ� ���� ��ٸ� ���� ���������� true(true, false�� ���� ����� �ۼ����� ���ƾ� ���� �����ؾ��� - alternate)
	         customer.setSubscriptionStatus(true);
	         this.subscriptionDAO.updateSubscriptionStatus(customer, insuranceID);	    
	         break;
	      case 2:
	         
	         System.out.println("���� �Ұ� ������ �ۼ��ϼ���.");
	         String notAgree = scanner.next();
	         System.out.println("���� �Ұ� ������ '"+notAgree+"'�Դϴ�.");
	         this.subscriptionDAO.deleteSubscription(customer, insuranceID);
	         
	      }
	   }

	public void ShowMenual(Scanner sc) {
		System.out.println("*******�޴��� Ȯ���ϱ�****");
		System.out.println("1. �Ǹ� �޴��� Ȯ���ϱ�, 2. ��ǰ ���� Ȯ���ϱ�");
		int menuNum = 0;
		while (true) {
			if (!sc.hasNextInt()) {
				sc.next();
				System.err.println("���ڸ� �Է��Ͻÿ�.  1. �Ǹ� �޴��� Ȯ���ϱ�, 2. ��ǰ ���� Ȯ���ϱ�");
			} else {
				menuNum = sc.nextInt();
				if (!(menuNum > 0 && menuNum < 3)) {
					System.err.println("���� �޴� �� �����Ͻÿ�.  1. �Ǹ� �޴��� Ȯ���ϱ�, 2. ��ǰ ���� Ȯ���ϱ�");
				} else {
					break;
				}
			}
		}
		// saleManual, manual �Ѵ� ���⼭ �Ǵ�
		if (menuNum == 1) {
			System.out.println("*******�Ǹ� �޴��� Ȯ���ϱ�********");
		} else {
			System.out.println("*******��ǰ �޴��� Ȯ���ϱ�********");
		}
		this.InsuranceManual(sc, menuNum);
	}

	public void InsuranceManual(Scanner sc, int menuNum) {
		this.insuranceDAO.searchInsuranceIDandName();
		System.out.println("Ȯ���� ���� ID�� �Է��Ͻÿ�.");
		int InsuranceID = 0;
		while (true) {
			if (!sc.hasNextInt()) {
				sc.next();
				System.err.println("���ڸ� �Է��Ͻÿ�.");
			} else {
				InsuranceID = sc.nextInt();
				if (!(insuranceDAO.searchInsuranceIDforManual(InsuranceID))) {
					System.err.println("�ش��ϴ� ����ID Ȥ�� �޴����� �������� �ʽ��ϴ�");
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
        // �ǹ��� ������ 1������� ���.
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

    	 if(actualCost.getFamilyHistory().containsKey("��")) {
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
    	 } else if(actualCost.getFamilyHistory().containsKey("��")){
    		 insurancePremiumRate = (float) (insurancePremiumRate * 1.5);
    	 } else {
    		 insurancePremiumRate = (float) (insurancePremiumRate * 0.8);
    	 }
    	 return insurancePremiumRate;
     }

}