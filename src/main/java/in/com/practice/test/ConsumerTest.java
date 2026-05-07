package in.com.practice.test;

import java.util.List;

import in.com.practice.bean.ConsumerBean;
import in.com.practice.model.ConsumerModel;

public class ConsumerTest {

    public static void main(String[] args) throws Exception {

         testAdd();
        // testUpdate();
        // testDelete();
        // testFindByPk();
        //testSearch();
    }

    // ======================= ADD =======================
    public static void testAdd() throws Exception {

        ConsumerBean bean = new ConsumerBean();

        bean.setConsumerCode("C001");
        bean.setConsumerGroup("GROUP_A");
        bean.setTopicName("Topic1");
        bean.setStatus("ACTIVE");

        ConsumerModel model = new ConsumerModel();
        long pk = model.add(bean);

        System.out.println("Consumer Added Successfully, PK = " + pk);
    }

    // ======================= UPDATE =======================
    public static void testUpdate() throws Exception {

        ConsumerBean bean = new ConsumerBean();

        bean.setConsumerId(1L); // existing ID
        bean.setConsumerCode("C002");
        bean.setConsumerGroup("GROUP_B");
        bean.setTopicName("Topic2");
        bean.setStatus("INACTIVE");

        ConsumerModel model = new ConsumerModel();
        model.update(bean);

        System.out.println("Consumer Updated Successfully");
    }

    // ======================= DELETE =======================
    public static void testDelete() throws Exception {

        ConsumerBean bean = new ConsumerBean();
        bean.setConsumerId(1L); // existing ID

        ConsumerModel model = new ConsumerModel();
        model.delete(bean);

        System.out.println("Consumer Deleted Successfully");
    }

    // ======================= FIND BY PK =======================
    public static void testFindByPk() throws Exception {

        ConsumerModel model = new ConsumerModel();

        ConsumerBean bean = model.findByPk(1);

        if (bean != null) {
            System.out.println("ID: " + bean.getConsumerId());
            System.out.println("Code: " + bean.getConsumerCode());
            System.out.println("Group: " + bean.getConsumerGroup());
            System.out.println("Topic Name: " + bean.getTopicName());
            System.out.println("Status: " + bean.getStatus());
        } else {
            System.out.println("Record Not Found");
        }
    }

    // ======================= SEARCH =======================
    public static void testSearch() throws Exception {

        ConsumerBean bean = new ConsumerBean();

        // Optional filters
        // bean.setConsumerCode("C001");
        // bean.setConsumerGroup("GROUP_A");
        // bean.setTopicName("Topic");

        ConsumerModel model = new ConsumerModel();

        List<ConsumerBean> list = model.search(bean, 1, 10);

        for (ConsumerBean b : list) {

            System.out.println("ID: " + b.getConsumerId());
            System.out.println("Code: " + b.getConsumerCode());
            System.out.println("Group: " + b.getConsumerGroup());
            System.out.println("Topic Name: " + b.getTopicName());
            System.out.println("Status: " + b.getStatus());
            System.out.println("----------------------------");
        }
    }
}