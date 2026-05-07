package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.BatchBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class BatchModel {

	// ========================= NEXT PK =========================
	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_batch");
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exception in nextPk()");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	// ========================= ADD =========================
	public long add(BatchBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO st_batch (id, code, message, count, status) VALUES (?, ?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getBatchCode());

			// NULL SAFE
			ps.setInt(3, bean.getTotalMessages() != null ? bean.getTotalMessages() : 0);
			ps.setInt(4, bean.getProcessedCount() != null ? bean.getProcessedCount() : 0);

			ps.setString(5, bean.getStatus());

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
				ex.printStackTrace();
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in add Batch: " + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ========================= UPDATE =========================
	public void update(BatchBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("UPDATE st_batch SET code=?, message=?, count=?, status=? WHERE id=?");

			ps.setString(1, bean.getBatchCode());
			ps.setInt(2, bean.getTotalMessages() != null ? bean.getTotalMessages() : 0);
			ps.setInt(3, bean.getProcessedCount() != null ? bean.getProcessedCount() : 0);
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getBatchId());

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
				ex.printStackTrace();
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in update Batch: " + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================= DELETE =========================
	public void delete(BatchBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_batch WHERE id=?");

			ps.setLong(1, bean.getBatchId());

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
				ex.printStackTrace();
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in delete Batch: " + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================= FIND =========================
	public BatchBean findByPk(long pk) throws ApplicationException {

		BatchBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_batch WHERE id=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new BatchBean();

				bean.setBatchId(rs.getLong("id"));
				bean.setBatchCode(rs.getString("code"));
				bean.setTotalMessages(rs.getInt("message"));
				bean.setProcessedCount(rs.getInt("count"));
				bean.setStatus(rs.getString("status"));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in findByPk Batch");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ========================= SEARCH =========================
	public List<BatchBean> search(BatchBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_batch WHERE 1=1");

		if (bean != null) {

			if (bean.getBatchId() != null && bean.getBatchId() > 0) {
				sql.append(" AND id = " + bean.getBatchId());
			}

			if (bean.getBatchCode() != null && bean.getBatchCode().length() > 0) {
				sql.append(" AND code LIKE '" + bean.getBatchCode() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND status LIKE '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<BatchBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BatchBean b = new BatchBean();

				b.setBatchId(rs.getLong("id"));
				b.setBatchCode(rs.getString("code"));
				b.setTotalMessages(rs.getInt("message"));
				b.setProcessedCount(rs.getInt("count"));
				b.setStatus(rs.getString("status"));

				list.add(b);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in search Batch");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}