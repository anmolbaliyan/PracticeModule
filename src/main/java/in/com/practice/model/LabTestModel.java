package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.LabTestBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class LabTestModel {

	// ===================== NEXT PK =====================
	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_labtest");
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

	// ===================== ADD =====================
	public long add(LabTestBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO st_labtest (id, name, cost, date) VALUES (?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getTestName());
			ps.setDouble(3, bean.getCost());
			ps.setDate(4, new java.sql.Date(bean.getTestDate().getTime()));

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (conn != null) conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in add LabTest");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ===================== UPDATE =====================
	public void update(LabTestBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_labtest SET name=?, cost=?, date=? WHERE id=?");

			ps.setString(1, bean.getTestName());
			ps.setDouble(2, bean.getCost());
			ps.setDate(3, new java.sql.Date(bean.getTestDate().getTime()));
			ps.setLong(4, bean.getLabTestId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (conn != null) conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in update LabTest");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ===================== DELETE =====================
	public void delete(LabTestBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"DELETE FROM st_labtest WHERE id=?");

			ps.setLong(1, bean.getLabTestId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (conn != null) conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in delete LabTest");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ===================== FIND BY PK =====================
	public LabTestBean findByPk(long pk) throws ApplicationException {

		LabTestBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM st_labtest WHERE id=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new LabTestBean();

				bean.setLabTestId(rs.getLong("id"));
				bean.setTestName(rs.getString("name"));
				bean.setCost(rs.getDouble("cost"));
				bean.setTestDate(rs.getDate("date"));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk LabTest");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ===================== SEARCH =====================
	public List<LabTestBean> search(LabTestBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_labtest WHERE 1=1");

		if (bean != null) {

			if (bean.getLabTestId() != null && bean.getLabTestId() > 0) {
				sql.append(" AND id = " + bean.getLabTestId());
			}

			if (bean.getTestName() != null && bean.getTestName().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getTestName() + "%'");
			}

			if (bean.getCost() != null && bean.getCost() > 0) {
				sql.append(" AND cost = " + bean.getCost());
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<LabTestBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				LabTestBean b = new LabTestBean();

				b.setLabTestId(rs.getLong("id"));
				b.setTestName(rs.getString("name"));
				b.setCost(rs.getDouble("cost"));
				b.setTestDate(rs.getDate("date"));

				list.add(b);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search LabTest");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}