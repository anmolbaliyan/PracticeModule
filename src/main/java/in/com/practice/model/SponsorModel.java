package in.com.practice.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.SponsorBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class SponsorModel {

    
    public Integer nextPk() throws DatabaseException {

        int pk = 0;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_sponsor");
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

    
    public long add(SponsorBean bean) throws ApplicationException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO st_sponsor VALUES (?, ?, ?, ?)");

            ps.setLong(1, pk); // id
            ps.setString(2, bean.getSponsorName()); // name
            ps.setDouble(3, bean.getContribution());
            ps.setString(4, bean.getContactPerson()); // person

            ps.executeUpdate();
            conn.commit();

            ps.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback failed");
            }
            throw new ApplicationException("Exception in add");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    
    public void update(SponsorBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                "UPDATE st_sponsor SET name=?, contribution=?, person=? WHERE id=?");

            ps.setString(1, bean.getSponsorName());
            ps.setDouble(2, bean.getContribution());
            ps.setString(3, bean.getContactPerson());
            ps.setLong(4, bean.getSponsorId());

            ps.executeUpdate();
            conn.commit();

            ps.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback failed");
            }
            throw new ApplicationException("Exception in update");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

  
    public void delete(SponsorBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM st_sponsor WHERE id=?");

            ps.setLong(1, bean.getSponsorId());

            ps.executeUpdate();
            conn.commit();

            ps.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback failed");
            }
            throw new ApplicationException("Exception in delete");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    
    public List<SponsorBean> search(SponsorBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("SELECT * FROM st_sponsor WHERE 1=1");

        if (bean != null) {

            if (bean.getSponsorId() != null && bean.getSponsorId() > 0) {
                sql.append(" AND id = " + bean.getSponsorId());
            }

            if (bean.getSponsorName() != null && bean.getSponsorName().length() > 0) {
                sql.append(" AND name LIKE '" + bean.getSponsorName() + "%'");
            }

            if (bean.getContactPerson() != null && bean.getContactPerson().length() > 0) {
                sql.append(" AND person LIKE '" + bean.getContactPerson() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        List<SponsorBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SponsorBean b = new SponsorBean();

                b.setSponsorId(rs.getLong(1));
                b.setSponsorName(rs.getString(2));
                b.setContribution(rs.getDouble(3));
                b.setContactPerson(rs.getString(4));

                list.add(b);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}