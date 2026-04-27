package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.GeoFenceBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class GeoFenceModel {

	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_geofence");
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(GeoFenceBean bean)throws ApplicationException {
		
		Connection conn = null;
		int pk = 0;
		
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement("INSERT INTO st_geofence VALUES (?, ?, ?, ?, ?)");
			
			ps.setLong(1, pk);
			ps.setString(2, bean.getGeoFenceCode());
			ps.setString(3, bean.getLocationName());
			ps.setInt(4, bean.getRadius());
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

	public void update(GeoFenceBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn
					.prepareStatement("UPDATE st_geofence SET code=?, name=?, radius=?, status=? WHERE id=?");

			ps.setString(1, bean.getGeoFenceCode());
			ps.setString(2, bean.getLocationName());
			ps.setInt(3, bean.getRadius());
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getGeoFenceId());
			
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

	public void delete(GeoFenceBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_geofence WHERE id=?");

			ps.setLong(1, bean.getGeoFenceId());

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

	public GeoFenceBean findByPk(long pk) throws ApplicationException {

		GeoFenceBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_geofence WHERE id = ?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new GeoFenceBean();

				bean.setGeoFenceId(rs.getLong("id"));
				bean.setGeoFenceCode(rs.getString("code"));
				bean.setLocationName(rs.getString("name")); 
				bean.setRadius(rs.getInt("radius")); 
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

	public List<GeoFenceBean> search(GeoFenceBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_geofence WHERE 1=1");

		if (bean != null) {

			if (bean.getGeoFenceId() != 0 && bean.getGeoFenceId() > 0) {
				sql.append(" AND id = " + bean.getGeoFenceId());
			}

			if (bean.getGeoFenceCode() != null && bean.getGeoFenceCode().length() > 0) {
				sql.append(" AND code LIKE '" + bean.getGeoFenceCode() + "%'");
			}

			if (bean.getLocationName() != null && bean.getLocationName().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getLocationName() + "%'");
			}

			if (bean.getRadius() != 0 && bean.getRadius() > 0) {
				sql.append(" AND radius = " + bean.getRadius());
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND status LIKE '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<GeoFenceBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				GeoFenceBean b = new GeoFenceBean();

				b.setGeoFenceId(rs.getLong("id"));
				b.setGeoFenceCode(rs.getString("code"));
				b.setLocationName(rs.getString("name")); // fixed
				b.setRadius(rs.getInt("radius"));
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

