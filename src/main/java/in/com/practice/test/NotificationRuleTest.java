package in.com.practice.test;

import java.util.List;

import in.com.practice.bean.NotifictionRuleBean;
import in.com.practice.model.NotificationRuleModel;

public class NotificationRuleTest {

    public static void main(String[] args) throws Exception {

        // testAdd();
        // testUpdate();
        // testDelete();
        // testFindByPk();
        testSearch();
    }

    public static void testAdd() throws Exception {

        NotifictionRuleBean bean = new NotifictionRuleBean();

        bean.setRuleCode("NR001");
        bean.setEvent("LOGIN");
        bean.setTriggerTime("IMMEDIATE");
        bean.setStatus("ACTIVE");

        NotificationRuleModel model = new NotificationRuleModel();
        long pk = model.add(bean);

        System.out.println("Data Added Successfully, PK = " + pk);
    }

    public static void testUpdate() throws Exception {

        NotifictionRuleBean bean = new NotifictionRuleBean();

        bean.setRuleId(1L);
        bean.setRuleCode("NR002");
        bean.setEvent("LOGOUT");
        bean.setTriggerTime("DELAYED");
        bean.setStatus("INACTIVE");

        NotificationRuleModel model = new NotificationRuleModel();
        model.update(bean);

        System.out.println("Data Updated Successfully");
    }

    public static void testDelete() throws Exception {

        NotifictionRuleBean bean = new NotifictionRuleBean();
        bean.setRuleId(1L);

        NotificationRuleModel model = new NotificationRuleModel();
        model.delete(bean);

        System.out.println("Data Deleted Successfully");
    }

    public static void testFindByPk() throws Exception {

        NotificationRuleModel model = new NotificationRuleModel();

        NotifictionRuleBean bean = model.findByPk(1);

        if (bean != null) {
            System.out.println("ID: " + bean.getRuleId());
            System.out.println("Code: " + bean.getRuleCode());
            System.out.println("Event: " + bean.getEvent());
            System.out.println("Trigger: " + bean.getTriggerTime());
            System.out.println("Status: " + bean.getStatus());
        } else {
            System.out.println("Record Not Found");
        }
    }

    public static void testSearch() throws Exception {

        NotifictionRuleBean bean = new NotifictionRuleBean();

        // bean.setStatus("ACTIVE");

        NotificationRuleModel model = new NotificationRuleModel();

        List<NotifictionRuleBean> list = model.search(bean, 1, 10);

        for (NotifictionRuleBean b : list) {

            System.out.println("ID: " + b.getRuleId());
            System.out.println("Code: " + b.getRuleCode());
            System.out.println("Event: " + b.getEvent());
            System.out.println("Trigger: " + b.getTriggerTime());
            System.out.println("Status: " + b.getStatus());
            System.out.println("----------------------------");
        }
    }
}