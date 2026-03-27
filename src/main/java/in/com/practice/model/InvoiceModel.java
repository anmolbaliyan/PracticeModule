package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.InvoiceBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class InvoiceModel {
	
	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_invoice");
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

	public long add(InvoiceBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO st_invoice VALUES (?, ?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getInvoiceCode());
			ps.setString(3, bean.getCustomerName()); 
			ps.setDouble(4, bean.getTotalAmount());
			ps.setString(5, bean.getStatus());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}
			throw new ApplicationException("Exception in add Invoice");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(InvoiceBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("UPDATE st_invoice SET code=?, name=?, amount=?, status=? WHERE id=?");

			ps.setString(1, bean.getInvoiceCode());
			ps.setString(2, bean.getCustomerName()); // name column
			ps.setDouble(3, bean.getTotalAmount());
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getInvoiceId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}
			throw new ApplicationException("Exception in update Invoice");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(InvoiceBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_invoice WHERE id=?");

			ps.setLong(1, bean.getInvoiceId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}
			throw new ApplicationException("Exception in delete Invoice");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public InvoiceBean findByPk(long pk) throws ApplicationException {

		InvoiceBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_invoice WHERE id = ?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new InvoiceBean();

				bean.setInvoiceId(rs.getLong("id"));
				bean.setInvoiceCode(rs.getString("code"));
				bean.setCustomerName(rs.getString("name")); 
				bean.setTotalAmount(rs.getDouble("amount"));
				bean.setStatus(rs.getString("status"));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk Invoice");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<InvoiceBean> search(InvoiceBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_invoice WHERE 1=1");

		if (bean != null) {

			if (bean.getInvoiceId() != 0 && bean.getInvoiceId() > 0) {
				sql.append(" AND id = " + bean.getInvoiceId());
			}

			if (bean.getInvoiceCode() != null && bean.getInvoiceCode().length() > 0) {
				sql.append(" AND code LIKE '" + bean.getInvoiceCode() + "%'");
			}

			if (bean.getCustomerName() != null && bean.getCustomerName().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getCustomerName() + "%'");
			}

			if (bean.getTotalAmount() != 0 && bean.getTotalAmount() > 0) {
				sql.append(" AND rating = " + bean.getTotalAmount());
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND status LIKE '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<InvoiceBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				InvoiceBean b = new InvoiceBean();

				b.setInvoiceId(rs.getLong("id"));
				b.setInvoiceCode(rs.getString("code"));
				b.setCustomerName(rs.getString("name")); // fixed
				b.setTotalAmount(rs.getDouble("amount"));
				b.setStatus(rs.getString("status"));

				list.add(b);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Invoice");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}

