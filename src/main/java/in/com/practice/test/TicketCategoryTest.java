package in.com.practice.test;

import java.util.List;

import in.com.practice.bean.TicketCategoryBean;
import in.com.practice.model.TicketCategoryModel;

public class TicketCategoryTest {

    public static void main(String[] args) throws Exception {

        // testAdd();
        // testUpdate();
        // testDelete();
        // testFindByPk();
        testSearch();
    }

    // ================= ADD =================
    public static void testAdd() throws Exception {

        TicketCategoryBean bean = new TicketCategoryBean();

        bean.setCategoryName("VIP");
        bean.setPrice(1500.0);
        bean.setAvailableSeats(50);

        TicketCategoryModel model = new TicketCategoryModel();
        long pk = model.add(bean);

        System.out.println("Data Added Successfully, PK = " + pk);
    }

    // ================= UPDATE =================
    public static void testUpdate() throws Exception {

        TicketCategoryBean bean = new TicketCategoryBean();

        bean.setTicketCategoryId(1L); // existing ID
        bean.setCategoryName("Premium");
        bean.setPrice(2000.0);
        bean.setAvailableSeats(40);

        TicketCategoryModel model = new TicketCategoryModel();
        model.update(bean);

        System.out.println("Data Updated Successfully");
    }

    // ================= DELETE =================
    public static void testDelete() throws Exception {

        TicketCategoryBean bean = new TicketCategoryBean();
        bean.setTicketCategoryId(1L); // existing ID

        TicketCategoryModel model = new TicketCategoryModel();
        model.delete(bean);

        System.out.println("Data Deleted Successfully");
    }

    // ================= FIND BY PK =================
    public static void testFindByPk() throws Exception {

        TicketCategoryModel model = new TicketCategoryModel();

        TicketCategoryBean bean = model.findByPk(1);

        if (bean != null) {
            System.out.println("ID: " + bean.getTicketCategoryId());
            System.out.println("Category: " + bean.getCategoryName());
            System.out.println("Price: " + bean.getPrice());
            System.out.println("Available Seats: " + bean.getAvailableSeats());
        } else {
            System.out.println("Record Not Found");
        }
    }

    // ================= SEARCH =================
    public static void testSearch() throws Exception {

        TicketCategoryBean bean = new TicketCategoryBean();

        // Optional filters
        // bean.setCategoryName("VIP");
        // bean.setPrice(1500.0);
        // bean.setAvailableSeats(50);

        TicketCategoryModel model = new TicketCategoryModel();

        List<TicketCategoryBean> list = model.search(bean, 1, 10);

        for (TicketCategoryBean b : list) {

            System.out.println("ID: " + b.getTicketCategoryId());
            System.out.println("Category: " + b.getCategoryName());
            System.out.println("Price: " + b.getPrice());
            System.out.println("Available Seats: " + b.getAvailableSeats());
            System.out.println("----------------------------");
        }
    }
}