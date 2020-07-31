package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAO {

	protected Connection connect;
	protected PreparedStatement statement;
	protected ResultSet resultSet;
	protected String sql;
	
	public DAO() {
		this.connect = null;
		this.statement = null;
		this.resultSet = null;
		this.sql = null;
	}

	public Connection getConnection() {
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/insuranceDB_test?serverTimezone=UTC&useSSL=false","root","seokwoojeong");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connect;
	}

	public void closeConnection(Connection connect) {
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
			}
		}
	}

	public int SelectMaxID(String columnName, String entityName) {
		int MaxID = 0;
		this.sql = "select Max(" + columnName + ") as " + columnName + " from " + entityName;
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);
			this.resultSet = this.statement.executeQuery();
			this.resultSet.next();
			MaxID = this.resultSet.getInt(columnName);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("InsuranceDAO.SelectMaxInsuranceID : " + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
		return MaxID;
	}

	public boolean CheckStringData(String columnName, String entityName, String record) {
		this.sql = "select " + columnName + " from " + entityName + " where " + columnName + " = ?";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);
			this.statement.setString(1, record);
			this.resultSet = this.statement.executeQuery();
			if (this.resultSet.next()) {
				if (this.resultSet.getString("insuranceName").equals(record)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("InsuranceDAO.CheckString : " + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
		return false;
	}
	
	public boolean CheckIntData(String columnName, String entityName, int record) {
		this.sql = "select " + columnName + " from " + entityName + " where " + columnName + " = ?";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);
			this.statement.setInt(1, record);
			this.resultSet = this.statement.executeQuery();
			if (this.resultSet.next()) {
				if (this.resultSet.getInt(columnName) == record) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("InsuranceDAO.CheckIntData : " + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
		return false;
	}
}
