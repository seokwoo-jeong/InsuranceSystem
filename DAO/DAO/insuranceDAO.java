package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import Insurance.ActualCostInsurance;
import Insurance.CarInsurance;
import Insurance.FireInsurance;
import Insurance.Insurance;

public class insuranceDAO extends DAO implements insuracneDAOinterface {
	
	
	public void searchInsuranceIDandName() {
		this.sql = "select insuranceID, insuranceName from Insurance";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);
			this.resultSet = this.statement.executeQuery();
			while (this.resultSet.next()) {
				System.out.println("--" + this.resultSet.getString("insuranceName") + "의 ID: "
						+ this.resultSet.getInt("insuranceID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("InsuranceDAO.searchInsuranceIDandName : " + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
	}
	
	public void InsertInsurance(Insurance insuracne) {
		this.sql = "insert into insurance values (?, ?, ?, ?, ?, ?)";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);

			this.statement.setInt(1, insuracne.getInsuranceID());
			this.statement.setString(2, insuracne.getInsuranceName());
			this.statement.setInt(3, insuracne.getInsuranceFee());
			this.statement.setString(4, insuracne.getInsuranceType().toString());
			this.statement.setString(5, insuracne.getInsuranceManual());
			this.statement.setString(6, insuracne.getInsuranceSalesManual());
			this.statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("InsuranceDAO.insertInsurance : " + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
	}
	
	public void InsertFireInsurance(FireInsurance insurance) {
		this.sql = "insert into Fireinsurance (FinsuranceID, directGuaranteedAmount, directGuaranteedContent, fireGuaranteedAmount, fireGuaranteedContent, refugeGuaranteedAmount, refugeGuaranteedContent) values (?, ?, ?, ?, ?, ?, ?)";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);

			this.statement.setInt(1, insurance.getInsuranceID());
			this.statement.setInt(2, insurance.getDirectDamage().getDamageGuaranteedAmount());
			this.statement.setString(3, insurance.getDirectDamage().getDamageGuaranteedContent());
			this.statement.setInt(4, insurance.getFireDamage().getDamageGuaranteedAmount());
			this.statement.setString(5, insurance.getFireDamage().getDamageGuaranteedContent());
			this.statement.setInt(6, insurance.getRefugeDamage().getDamageGuaranteedAmount());
			this.statement.setString(7, insurance.getRefugeDamage().getDamageGuaranteedContent());
			this.statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("InsuranceDAO.insertInsurance : " + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
	}

	public void InsertCarInsurance(CarInsurance insurance) {
		this.sql = "insert into Carinsurance (CinsuranceID, goodsGuaranteeContent, goodsGuaranteeLimit, goodsSeparation, personalGuaranteeContent, personalProvisionLimit, personalSeparation, subscriptionFeeForAccidentalInjuries, subscriptionFeeForInjury, selfVehicleSeparation) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);

			this.statement.setInt(1, insurance.getInsuranceID());
			this.statement.setString(2, insurance.getGoodsIndemnification().getGuaranteeContent());
			this.statement.setInt(3, insurance.getGoodsIndemnification().getProvisionLimit());
			this.statement.setString(4, insurance.getGoodsIndemnification().getSeparation().toString());
			this.statement.setString(5, insurance.getPersonalIndemnification().getGuaranteeContent());
			this.statement.setInt(6, insurance.getPersonalIndemnification().getProvisionLimit());
			this.statement.setString(7, insurance.getPersonalIndemnification().getSeparation().toString());
			this.statement.setInt(8, insurance.getSelfVehicleDamage().getSubscriptionFeeForAccidentalInjuries());
			this.statement.setInt(9, insurance.getSelfVehicleDamage().getSubscriptionFeeForInjury());
			this.statement.setString(10, insurance.getSelfVehicleDamage().getSeparation().toString());
			this.statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("InsuranceDAO.insertInsurance : " + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
	}

	public void InsertActualCostInsurance(ActualCostInsurance insurance) {
		this.sql = "insert into ActualCostinsurance (AinsuranceID, hospitalizationFee, hospitalizationReason, outpatientFee, outpatientReason) values (?, ?, ?, ?, ?)";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);

			this.statement.setInt(1, insurance.getInsuranceID());
			this.statement.setInt(2, insurance.getInjuryHospitalization().getProvisionFee());
			this.statement.setString(3, insurance.getInjuryHospitalization().getProvisionReason());
			this.statement.setInt(4, insurance.getInjuryOutpatient().getProvisionFee());
			this.statement.setString(5, insurance.getInjuryOutpatient().getProvisionReason());
			this.statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("InsuranceDAO.insertInsurance : " + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
	}
	
	public void showAllInsuranceID() {
		this.sql = "select insuranceID, insuranceType from Insurance";
		Vector<Integer> fireVector = new Vector<Integer>();
		Vector<Integer> carVector = new Vector<Integer>();
		Vector<Integer> actualCostVector = new Vector<Integer>();
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(sql);
			this.resultSet = this.statement.executeQuery();
			while (this.resultSet.next()) {
				if (Insurance.InsuranceType.valueOf(this.resultSet.getString("insuranceType"))
						.equals(Insurance.InsuranceType.Fire)) {
					fireVector.add(this.resultSet.getInt("insuranceID"));
				} else if (Insurance.InsuranceType.valueOf(this.resultSet.getString("insuranceType"))
						.equals(Insurance.InsuranceType.Car)) {
					carVector.add(this.resultSet.getInt("insuranceID"));
				} else if (Insurance.InsuranceType.valueOf(this.resultSet.getString("insuranceType"))
						.equals(Insurance.InsuranceType.ActualCost)) {
					actualCostVector.add(this.resultSet.getInt("insuranceID"));
				}
			}
			System.out.print("(화재보험:");
			for (int i = 0; i < fireVector.size(); i++) {

				System.out.print(fireVector.get(i) + " ");
			}
			System.out.print(") (실비보험:");
			for (int i = 0; i < actualCostVector.size(); i++) {
				System.out.print(actualCostVector.get(i) + " ");
			}
			System.out.print(") (자동차보험:");
			for (int i = 0; i < carVector.size(); i++) {
				System.out.print(carVector.get(i) + " ");
			}
			System.out.print(")" + "\n");

		} catch (SQLException e) {
			throw new RuntimeException("InsuranceDAO.showAllInsuranceID :" + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}

	}
	
	public boolean searchInsuranceIDforManual(int insuranceID) {
	      this.sql = "select * from Insurance where insuranceID";

	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(sql);
	         this.resultSet = this.statement.executeQuery();

	         if (this.resultSet.next()) {
	            return true;
	         }
	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.searchInsuranceIDforManual :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	      return false;
	   }

	   public void searchInsuranceSalesManual(int insuranceID) {
	      this.sql = "select insuranceSalesManual from Insurance where insuranceID = ?";

	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(this.sql);
	         this.statement.setInt(1, insuranceID);
	         this.resultSet = this.statement.executeQuery();

	         if (this.resultSet.next()) {
	            String InsuranceSalesManual = resultSet.getString("insuranceSalesManual");
	            System.out.println(InsuranceSalesManual);
	         } else {
	            System.out.println("해당하는 판매 메뉴얼 존재 않음");
	         }
	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.searchInsuranceSalesManual :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	   }

	   public void searchInsuranceManual(int insuranceID) {
	      this.sql = "select insuranceManual from Insurance where InsuranceID = ?";

	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(sql);
	         this.statement.setInt(1, insuranceID);
	         this.resultSet = this.statement.executeQuery();
	         if (this.resultSet.next()) {
	            String InsuranceManual = resultSet.getString("insuranceManual");
	            System.out.println(InsuranceManual);
	         } else {
	            System.out.println("해당하는 상품 메뉴얼 존재 않음");
	         }
	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.searchInsuranceManual :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	   }
	   
	   public float getInsuranceFee(int insuranceID) {
		      this.sql = "select insuranceFee from Insurance where insuranceID = ?";
		      float insuranceFee = 0;
		      
		      try {
		         this.connect = this.getConnection();
		         PreparedStatement statement = connect.prepareStatement(this.sql);
		         
		         statement.setInt(1, insuranceID);
		         ResultSet resultSet = statement.executeQuery();

		      if (resultSet.next()) {
		            insuranceFee = resultSet.getInt("insuranceFee");
		         }
		         
		      } catch (SQLException e) {
		         throw new RuntimeException("InsuranceDAO.getInsurance :" + e.getMessage());
		      } finally {
		         closeConnection(connect);
		      }
		      return insuranceFee;
		   }



}
