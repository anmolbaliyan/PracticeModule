package in.com.practice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.practice.bean.NotifictionRuleBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.exception.DatabaseException;
import in.com.practice.util.JDBCDataSource;

public class NotificationRuleModel {

    public Integer nextPk() throws DatabaseException {

        int pk = 0;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM st_notirule");
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

    public long add(NotifictionRuleBean bean) throws ApplicationException {

        Connection conn = null;
        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            String sql = "INSERT INTO st_notirule (id, code, event, `trigger`, status) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, pk);
            ps.setString(2, bean.getRuleCode());
            ps.setString(3, bean.getEvent());
            ps.setString(4, bean.getTriggerTime());
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

            throw new ApplicationException("Exception in add NotificationRule");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(NotifictionRuleBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            String sql = "UPDATE st_notirule SET code=?, event=?, `trigger`=?, status=? WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, bean.getRuleCode());
            ps.setString(2, bean.getEvent());
            ps.setString(3, bean.getTriggerTime());
            ps.setString(4, bean.getStatus());
            ps.setLong(5, bean.getRuleId());

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

            throw new ApplicationException("Exception in update NotificationRule");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(NotifictionRuleBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("DELETE FROM st_notirule WHERE id=?");

            ps.setLong(1, bean.getRuleId());

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

            throw new ApplicationException("Exception in delete NotificationRule");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public NotifictionRuleBean findByPk(long pk) throws ApplicationException {

        NotifictionRuleBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_notirule WHERE id=?");

            ps.setLong(1, pk);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bean = new NotifictionRuleBean();

                bean.setRuleId(rs.getLong("id"));
                bean.setRuleCode(rs.getString("code"));
                bean.setEvent(rs.getString("event"));
                bean.setTriggerTime(rs.getString("trigger"));
                bean.setStatus(rs.getString("status"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk NotificationRule");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<NotifictionRuleBean> search(NotifictionRuleBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("SELECT * FROM st_notirule WHERE 1=1");

        if (bean != null) {

            if (bean.getRuleId() > 0) {
                sql.append(" AND id = " + bean.getRuleId());
            }

            if (bean.getRuleCode() != null && bean.getRuleCode().length() > 0) {
                sql.append(" AND code LIKE '" + bean.getRuleCode() + "%'");
            }

            if (bean.getEvent() != null && bean.getEvent().length() > 0) {
                sql.append(" AND event LIKE '" + bean.getEvent() + "%'");
            }

            if (bean.getTriggerTime() != null && bean.getTriggerTime().length() > 0) {
                sql.append(" AND `trigger` LIKE '" + bean.getTriggerTime() + "%'");
            }

            if (bean.getStatus() != null && bean.getStatus().length() > 0) {
                sql.append(" AND status LIKE '" + bean.getStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        List<NotifictionRuleBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                NotifictionRuleBean b = new NotifictionRuleBean();

                b.setRuleId(rs.getLong("id"));
                b.setRuleCode(rs.getString("code"));
                b.setEvent(rs.getString("event"));
                b.setTriggerTime(rs.getString("trigger"));
                b.setStatus(rs.getString("status"));

                list.add(b);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search NotificationRule");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}