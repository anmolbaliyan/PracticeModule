package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.ConsumerBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class ConsumerModel {
	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_consumer");
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

	public long add(ConsumerBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO st_consumer (id, code, sgroups, name, status) VALUES (?, ?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getConsumerCode());
			ps.setString(3, bean.getConsumerGroup());
			ps.setString(4, bean.getTopicName());
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
				throw new ApplicationException("Rollback failed");
			}

			throw new ApplicationException("Exception in add consumer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(ConsumerBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("UPDATE st_consumer SET code=?, sgroups=?, name=? WHERE id=?");

			ps.setString(1, bean.getConsumerCode());
			ps.setString(2, bean.getConsumerGroup());
			ps.setString(3, bean.getTopicName());
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getConsumerId());

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

			throw new ApplicationException("Exception in update Consumer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(ConsumerBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_consumer WHERE id=?");

			ps.setLong(1, bean.getConsumerId());

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

			throw new ApplicationException("Exception in delete Consumer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public ConsumerBean findByPk(long pk) throws ApplicationException {

		ConsumerBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_consumer WHERE id=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new ConsumerBean();

				bean.setConsumerId(rs.getLong("id"));
				bean.setConsumerCode(rs.getString("code"));
				bean.setConsumerGroup(rs.getString("sgroups"));
				bean.setTopicName(rs.getString("name"));
				bean.setStatus(rs.getString("status"));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk Consumer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<ConsumerBean> search(ConsumerBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_consumer WHERE 1=1");

		if (bean != null) {

			if (bean.getConsumerId() != null && bean.getConsumerId() > 0) {
				sql.append(" AND id = " + bean.getConsumerId());
			}

			if (bean.getConsumerCode() != null && bean.getConsumerCode().length() > 0) {
				sql.append(" AND code = " + bean.getConsumerCode());
			}

			if (bean.getConsumerGroup() != null && bean.getConsumerGroup().length() > 0) {
				sql.append(" AND sgroups = " + bean.getConsumerGroup());
			}

			if (bean.getTopicName() != null && bean.getTopicName().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getTopicName() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<ConsumerBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ConsumerBean b = new ConsumerBean();

				bean.setConsumerId(rs.getLong("id"));
				bean.setConsumerCode(rs.getString("code"));
				bean.setConsumerGroup(rs.getString("sgroups"));
				bean.setTopicName(rs.getString("name"));
				bean.setStatus(rs.getString("status"));

				list.add(b);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search consumer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
