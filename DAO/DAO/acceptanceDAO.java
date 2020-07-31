package DAO;

import java.sql.SQLException;
import java.util.Vector;

import Acceptance.AcceptanceGuide;

public class acceptanceDAO extends DAO implements acceptanceDAOinterface{

	
	public void InsertAcceptanceGuide(AcceptanceGuide acceptanceGuide) {
		this.sql = "insert into Acceptance (acceptanceID, riskEvaluation, scamCase, insuranceID) values (?, ?, ?, ?)";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);

			this.statement.setInt(1, acceptanceGuide.getAcceptanceID());
			this.statement.setString(2, acceptanceGuide.getRiskEvaluation().toString());
			this.statement.setString(3, acceptanceGuide.getScamCase());
			this.statement.setInt(4, acceptanceGuide.getInsuranceID());
			this.statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("InsuranceDAO.InsertAcceptanceGuide : " + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
	}

	public Vector<AcceptanceGuide> searchAcceptanceForInsurance(String insuranceType) {
		AcceptanceGuide acceptanceGuide;
		Vector<AcceptanceGuide> acceptances = new Vector<AcceptanceGuide>();
		this.sql = "select acceptance.acceptanceid, acceptance.riskEvaluation, acceptance.scamcase "
				+ "from insurance, acceptance where insurance.insuranceid = acceptance.insuranceid AND insurance.insuranceType = ?";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(sql);
			this.statement.setString(1, insuranceType);
			this.resultSet = this.statement.executeQuery();
			while (this.resultSet.next()) {
				acceptanceGuide = new AcceptanceGuide();
				acceptanceGuide.setAcceptanceID(this.resultSet.getInt("acceptance.acceptanceid"));
				acceptanceGuide.setRiskEvaluation(
						AcceptanceGuide.RiskEvaluation.valueOf(this.resultSet.getString("riskEvaluation")));
				acceptanceGuide.setScamCase(this.resultSet.getString("acceptance.scamcase"));
				acceptances.add(acceptanceGuide);
			}
		} catch (Exception e) {
			throw new RuntimeException("InsuranceDAO.searchAcceptanceForInsurance :" + e.getMessage());
		} finally {
			closeConnection(connect);
		}
		return acceptances;
	}

/////////////////////////////////////////////////////////////////////////////////////	   
	 public int getInsuranceIDFromAcceptance(int insuranceID) {

		  this.sql = "select insuranceID from Acceptance where insuranceID = ?";
		  try {
			  this.connect = getConnection();
			  this.statement = this.connect.prepareStatement(sql);
			  this.statement.setInt(1, insuranceID);
			  this.resultSet = this.statement.executeQuery();
			  if (this.resultSet.next()) {
				  insuranceID = this.resultSet.getInt("insuranceID");
			  } else {
				  insuranceID = -1;
			  }
		  } catch (SQLException e) {
			  throw new RuntimeException("InsuranceDAO.getAcceptanceID :" + e.getMessage());
		  } finally {
			  closeConnection(this.connect);
		  }
		  return insuranceID;
	  }
	 
	 public AcceptanceGuide findAcceptance(int insuranceID) {
		  AcceptanceGuide acceptanceGuide = new AcceptanceGuide();
		  this.sql = "select * from Acceptance where insuranceID = ?";
		  try {
			  this.connect = getConnection();
			  this.statement = this.connect.prepareStatement(sql);
			  this.statement.setInt(1, insuranceID);
			  this.resultSet = this.statement.executeQuery();
			  if (this.resultSet.next()) {
				  acceptanceGuide.setAcceptanceID(this.resultSet.getInt("acceptanceID"));
				  acceptanceGuide.setRiskEvaluation(
						  AcceptanceGuide.RiskEvaluation.valueOf(this.resultSet.getString("riskEvaluation")));
				acceptanceGuide.setScamCase(this.resultSet.getString("scamCase"));
				
			}
		} catch (SQLException e) {
			throw new RuntimeException("InsuranceDAO.findAcceptance :" + e.getMessage());
		} finally {
			closeConnection(connect);
		}
		return acceptanceGuide;
	}
}
