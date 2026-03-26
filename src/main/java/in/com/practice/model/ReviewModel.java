package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.ReviewBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class ReviewModel {

	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_review");
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

	public long add(ReviewBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO st_review VALUES (?, ?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getReviewCode());
			ps.setString(3, bean.getUserName()); // maps to 'name'
			ps.setInt(4, bean.getRating());
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
			throw new ApplicationException("Exception in add Review");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(ReviewBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("UPDATE st_review SET code=?, name=?, rating=?, status=? WHERE id=?");

			ps.setString(1, bean.getReviewCode());
			ps.setString(2, bean.getUserName()); // name column
			ps.setInt(3, bean.getRating());
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getReviewId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}
			throw new ApplicationException("Exception in update Review");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(ReviewBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_review WHERE id=?");

			ps.setLong(1, bean.getReviewId());

			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback failed");
			}
			throw new ApplicationException("Exception in delete Review");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public ReviewBean findByPk(long pk) throws ApplicationException {

		ReviewBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_review WHERE id = ?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new ReviewBean();

				bean.setReviewId(rs.getLong("id"));
				bean.setReviewCode(rs.getString("code"));
				bean.setUserName(rs.getString("name")); 
				bean.setRating(rs.getInt("rating"));
				bean.setStatus(rs.getString("status"));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk Review");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<ReviewBean> search(ReviewBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_review WHERE 1=1");

		if (bean != null) {

			if (bean.getReviewId() != 0 && bean.getReviewId() > 0) {
				sql.append(" AND id = " + bean.getReviewId());
			}

			if (bean.getReviewCode() != null && bean.getReviewCode().length() > 0) {
				sql.append(" AND code LIKE '" + bean.getReviewCode() + "%'");
			}

			if (bean.getUserName() != null && bean.getUserName().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getUserName() + "%'");
			}

			if (bean.getRating() != 0 && bean.getRating() > 0) {
				sql.append(" AND rating = " + bean.getRating());
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND status LIKE '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<ReviewBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ReviewBean b = new ReviewBean();

				b.setReviewId(rs.getLong("id"));
				b.setReviewCode(rs.getString("code"));
				b.setUserName(rs.getString("name")); // fixed
				b.setRating(rs.getInt("rating"));
				b.setStatus(rs.getString("status"));

				list.add(b);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Review");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
