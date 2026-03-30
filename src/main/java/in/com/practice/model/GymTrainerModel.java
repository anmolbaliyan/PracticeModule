package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.GymTrainerBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class GymTrainerModel {

	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_gym");
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

	public long add(GymTrainerBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("INSERT INTO st_gym VALUES (?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getTrainerName());
			ps.setString(3, bean.getSpecialization());
			ps.setDouble(4, bean.getSalary());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}
			throw new ApplicationException("Exception in add Trainer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(GymTrainerBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("UPDATE st_gym SET name=?, specialization=?, salary=? WHERE id=?");

			ps.setString(1, bean.getTrainerName());
			ps.setString(2, bean.getSpecialization());
			ps.setDouble(3, bean.getSalary());
			ps.setLong(4, bean.getTrainerId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}
			throw new ApplicationException("Exception in update Trainer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(GymTrainerBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_gym WHERE id=?");

			ps.setLong(1, bean.getTrainerId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}
			throw new ApplicationException("Exception in delete Trainer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public GymTrainerBean findByPk(long pk) throws ApplicationException {

		GymTrainerBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_gym WHERE id=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new GymTrainerBean();

				bean.setTrainerId(rs.getLong("id"));
				bean.setTrainerName(rs.getString("name"));
				bean.setSpecialization(rs.getString("specialization"));
				bean.setSalary(rs.getDouble("salary"));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk Trainer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<GymTrainerBean> search(GymTrainerBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_gym WHERE 1=1");

		if (bean != null) {

			if (bean.getTrainerId() != null && bean.getTrainerId() > 0) {
				sql.append(" AND id = " + bean.getTrainerId());
			}

			if (bean.getTrainerName() != null && bean.getTrainerName().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getTrainerName() + "%'");
			}

			if (bean.getSpecialization() != null && bean.getSpecialization().length() > 0) {
				sql.append(" AND specialization LIKE '" + bean.getSpecialization() + "%'");
			}

			if (bean.getSalary() != null && bean.getSalary() > 0) {
				sql.append(" AND salary = " + bean.getSalary());
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<GymTrainerBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				GymTrainerBean b = new GymTrainerBean();

				b.setTrainerId(rs.getLong("id"));
				b.setTrainerName(rs.getString("name"));
				b.setSpecialization(rs.getString("specialization"));
				b.setSalary(rs.getDouble("salary"));

				list.add(b);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Trainer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}