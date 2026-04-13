package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.NotificationBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class NotificationModel {

	// ===================== NEXT PK =====================
	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_notification");
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
	public long add(NotificationBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO st_notification (id, code, name, preference, status) VALUES (?, ?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getPrefCode());
			ps.setString(3, bean.getUserName());
			ps.setString(4, bean.getPreference());
			ps.setString(5, bean.getStatus());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in add Notification");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ===================== UPDATE =====================
	public void update(NotificationBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("UPDATE st_notification SET code=?, name=?, preference=?, status=? WHERE id=?");

			ps.setString(1, bean.getPrefCode());
			ps.setString(2, bean.getUserName());
			ps.setString(3, bean.getPreference());
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getPrefId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in update Notification");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ===================== DELETE =====================
	public void delete(NotificationBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_notification WHERE id=?");

			ps.setLong(1, bean.getPrefId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in delete Notification");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ===================== FIND BY PK =====================
	public NotificationBean findByPk(long pk) throws ApplicationException {

		NotificationBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_notification WHERE id=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new NotificationBean();

				bean.setPrefId(rs.getLong("id"));
				bean.setPrefCode(rs.getString("code"));
				bean.setUserName(rs.getString("name"));
				bean.setPreference(rs.getString("preference"));
				bean.setStatus(rs.getString("status"));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk Notification");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ===================== SEARCH =====================
	public List<NotificationBean> search(NotificationBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_notification WHERE 1=1");

		if (bean != null) {

			if (bean.getPrefId() != null && bean.getPrefId() > 0) {
				sql.append(" AND id = " + bean.getPrefId());
			}

			if (bean.getPrefCode() != null && bean.getPrefCode().length() > 0) {
				sql.append(" AND code LIKE '" + bean.getPrefCode() + "%'");
			}

			if (bean.getUserName() != null && bean.getUserName().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getUserName() + "%'");
			}

			if (bean.getPreference() != null && bean.getPreference().length() > 0) {
				sql.append(" AND preference LIKE '" + bean.getPreference() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND status LIKE '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<NotificationBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				NotificationBean b = new NotificationBean();

				b.setPrefId(rs.getLong("id"));
				b.setPrefCode(rs.getString("code"));
				b.setUserName(rs.getString("name"));
				b.setPreference(rs.getString("preference"));
				b.setStatus(rs.getString("status"));

				list.add(b);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Notification");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}