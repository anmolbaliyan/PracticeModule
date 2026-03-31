package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.BudgetBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class BudgetModel {

	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_budget");
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	public long add(BudgetBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO st_budget (id, allocated_amt, spent_amt, department) VALUES (?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setDouble(2, bean.getAllocatedAmount());
			ps.setDouble(3, bean.getSpentAmount());
			ps.setString(4, bean.getDepartment());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in add Budget");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(BudgetBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("UPDATE st_budget SET allocated_amt=?, spent_amt=?, department=? WHERE id=?");

			ps.setDouble(1, bean.getAllocatedAmount());
			ps.setDouble(2, bean.getSpentAmount());
			ps.setString(3, bean.getDepartment());
			ps.setLong(4, bean.getBudgetId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in update Budget");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(BudgetBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_budget WHERE id=?");

			ps.setLong(1, bean.getBudgetId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in delete Budget");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public BudgetBean findByPk(long pk) throws ApplicationException {

		BudgetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_budget WHERE id=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new BudgetBean();

				bean.setBudgetId(rs.getLong("id"));
				bean.setAllocatedAmount(rs.getDouble("allocated_amt"));
				bean.setSpentAmount(rs.getDouble("spent_amt"));
				bean.setDepartment(rs.getString("department"));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk Budget");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<BudgetBean> search(BudgetBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_budget WHERE 1=1");

		if (bean != null) {

			if (bean.getBudgetId() != null && bean.getBudgetId() > 0) {
				sql.append(" AND id = " + bean.getBudgetId());
			}

			if (bean.getAllocatedAmount() != null && bean.getAllocatedAmount() > 0) {
				sql.append(" AND allocated_amt = " + bean.getAllocatedAmount());
			}

			if (bean.getSpentAmount() != null && bean.getSpentAmount() > 0) {
				sql.append(" AND spent_amt = " + bean.getSpentAmount());
			}

			if (bean.getDepartment() != null && bean.getDepartment().length() > 0) {
				sql.append(" AND department LIKE '" + bean.getDepartment() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<BudgetBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BudgetBean b = new BudgetBean();

				b.setBudgetId(rs.getLong("id"));
				b.setAllocatedAmount(rs.getDouble("allocated_amt"));
				b.setSpentAmount(rs.getDouble("spent_amt"));
				b.setDepartment(rs.getString("department"));

				list.add(b);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Budget");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
