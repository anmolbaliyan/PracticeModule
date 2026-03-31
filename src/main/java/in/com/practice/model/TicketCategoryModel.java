package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.TicketCategoryBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class TicketCategoryModel {

	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_ticket");
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

	public long add(TicketCategoryBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO st_ticket (id, category, price, avb_seat) VALUES (?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getCategoryName());
			ps.setDouble(3, bean.getPrice());
			ps.setInt(4, bean.getAvailableSeats());

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

			throw new ApplicationException("Exception in add TicketCategory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(TicketCategoryBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("UPDATE st_ticket SET category=?, price=?, avb_seat=? WHERE id=?");

			ps.setString(1, bean.getCategoryName());
			ps.setDouble(2, bean.getPrice());
			ps.setInt(3, bean.getAvailableSeats());
			ps.setLong(4, bean.getTicketCategoryId());

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

			throw new ApplicationException("Exception in update TicketCategory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(TicketCategoryBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_ticket WHERE id=?");

			ps.setLong(1, bean.getTicketCategoryId());

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

			throw new ApplicationException("Exception in delete TicketCategory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public TicketCategoryBean findByPk(long pk) throws ApplicationException {

		TicketCategoryBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_ticket WHERE id=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new TicketCategoryBean();

				bean.setTicketCategoryId(rs.getLong("id"));
				bean.setCategoryName(rs.getString("category"));
				bean.setPrice(rs.getDouble("price"));
				bean.setAvailableSeats(rs.getInt("avb_seat"));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk TicketCategory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<TicketCategoryBean> search(TicketCategoryBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_ticket WHERE 1=1");

		if (bean != null) {

			if (bean.getTicketCategoryId() != null && bean.getTicketCategoryId() > 0) {
				sql.append(" AND id = " + bean.getTicketCategoryId());
			}

			if (bean.getCategoryName() != null && bean.getCategoryName().length() > 0) {
				sql.append(" AND category LIKE '" + bean.getCategoryName() + "%'");
			}

			if (bean.getPrice() != null && bean.getPrice() > 0) {
				sql.append(" AND price = " + bean.getPrice());
			}

			if (bean.getAvailableSeats() != null && bean.getAvailableSeats() > 0) {
				sql.append(" AND avb_seat = " + bean.getAvailableSeats());
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<TicketCategoryBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TicketCategoryBean b = new TicketCategoryBean();

				b.setTicketCategoryId(rs.getLong("id"));
				b.setCategoryName(rs.getString("category"));
				b.setPrice(rs.getDouble("price"));
				b.setAvailableSeats(rs.getInt("avb_seat"));

				list.add(b);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search TicketCategory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
