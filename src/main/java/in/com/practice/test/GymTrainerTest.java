package in.com.practice.test;

import java.util.List;

import in.com.practice.bean.GymTrainerBean;
import in.com.practice.model.GymTrainerModel;

public class GymTrainerTest {

    public static void main(String[] args) throws Exception {

         testAdd();
        // testUpdate();
        // testDelete();
        // testFindByPk();
        //testSearch();
    }

   
    public static void testAdd() throws Exception {

        GymTrainerBean bean = new GymTrainerBean();

        bean.setTrainerName("Rahul Sharma");
        bean.setSpecialization("Weight Training");
        bean.setSalary(25000.0);

        GymTrainerModel model = new GymTrainerModel();
        long pk = model.add(bean);

        System.out.println("Data Added Successfully, PK = " + pk);
    }

    
    public static void testUpdate() throws Exception {

        GymTrainerBean bean = new GymTrainerBean();

        bean.setTrainerId(1L); 
        bean.setTrainerName("Updated Name");
        bean.setSpecialization("Cardio");
        bean.setSalary(30000.0);

        GymTrainerModel model = new GymTrainerModel();
        model.update(bean);

        System.out.println("Data Updated Successfully");
    }

   
    public static void testDelete() throws Exception {

        GymTrainerBean bean = new GymTrainerBean();
        bean.setTrainerId(1L); // existing ID

        GymTrainerModel model = new GymTrainerModel();
        model.delete(bean);

        System.out.println("Data Deleted Successfully");
    }

   
    public static void testFindByPk() throws Exception {

        GymTrainerModel model = new GymTrainerModel();

        GymTrainerBean bean = model.findByPk(1);

        if (bean != null) {
            System.out.println("ID: " + bean.getTrainerId());
            System.out.println("Name: " + bean.getTrainerName());
            System.out.println("Specialization: " + bean.getSpecialization());
            System.out.println("Salary: " + bean.getSalary());
        } else {
            System.out.println("Record Not Found");
        }
    }

  
    public static void testSearch() throws Exception {

        GymTrainerBean bean = new GymTrainerBean();

        // Optional filters
        // bean.setTrainerName("Rahul");
        // bean.setSpecialization("Cardio");
        // bean.setSalary(25000.0);

        GymTrainerModel model = new GymTrainerModel();

        List<GymTrainerBean> list = model.search(bean, 1, 10);

        for (GymTrainerBean b : list) {

            System.out.println("ID: " + b.getTrainerId());
            System.out.println("Name: " + b.getTrainerName());
            System.out.println("Specialization: " + b.getSpecialization());
            System.out.println("Salary: " + b.getSalary());
            System.out.println("---------------------------");
        }
    }
}