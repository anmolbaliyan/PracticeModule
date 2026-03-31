package in.com.practice.test;

import java.util.List;

import in.com.practice.bean.BudgetBean;
import in.com.practice.model.BudgetModel;

public class BudgetTest {

    public static void main(String[] args) throws Exception {

        // testAdd();
        // testUpdate();
        // testDelete();
        // testFindByPk();
        testSearch();
    }

    
    public static void testAdd() throws Exception {

        BudgetBean bean = new BudgetBean();

        bean.setAllocatedAmount(500000.0);
        bean.setSpentAmount(200000.0);
        bean.setDepartment("IT");

        BudgetModel model = new BudgetModel();
        long pk = model.add(bean);

        System.out.println("Data Added Successfully, PK = " + pk);
    }

    
    public static void testUpdate() throws Exception {

        BudgetBean bean = new BudgetBean();

        bean.setBudgetId(1L); // existing ID
        bean.setAllocatedAmount(600000.0);
        bean.setSpentAmount(250000.0);
        bean.setDepartment("HR");

        BudgetModel model = new BudgetModel();
        model.update(bean);

        System.out.println("Data Updated Successfully");
    }

   
    public static void testDelete() throws Exception {

        BudgetBean bean = new BudgetBean();
        bean.setBudgetId(1L); // existing ID

        BudgetModel model = new BudgetModel();
        model.delete(bean);

        System.out.println("Data Deleted Successfully");
    }

   
    public static void testFindByPk() throws Exception {

        BudgetModel model = new BudgetModel();

        BudgetBean bean = model.findByPk(1);

        if (bean != null) {
            System.out.println("ID: " + bean.getBudgetId());
            System.out.println("Allocated Amount: " + bean.getAllocatedAmount());
            System.out.println("Spent Amount: " + bean.getSpentAmount());
            System.out.println("Department: " + bean.getDepartment());
        } else {
            System.out.println("Record Not Found");
        }
    }

  
    public static void testSearch() throws Exception {

        BudgetBean bean = new BudgetBean();

        // Optional filters (uncomment if needed)
        // bean.setDepartment("IT");
        // bean.setAllocatedAmount(500000.0);

        BudgetModel model = new BudgetModel();

        List<BudgetBean> list = model.search(bean, 1, 10);

        for (BudgetBean b : list) {

            System.out.println("ID: " + b.getBudgetId());
            System.out.println("Allocated Amount: " + b.getAllocatedAmount());
            System.out.println("Spent Amount: " + b.getSpentAmount());
            System.out.println("Department: " + b.getDepartment());
            System.out.println("----------------------------");
        }
    }
}