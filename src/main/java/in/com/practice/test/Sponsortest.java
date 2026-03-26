package in.com.practice.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.com.practice.bean.SponsorBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.model.SponsorModel;

public class Sponsortest {

    public static SponsorModel model = new SponsorModel();

    public static void main(String[] args) {

        testAdd();
        // testDelete();
        // testUpdate();
        // testSearch();
    }

   
    public static void testAdd() {

        try {
            SponsorBean bean = new SponsorBean();

            bean.setSponsorName("Tata");
            bean.setContribution(50000.0);
            bean.setContactPerson("Ramesh");

            long pk = model.add(bean);

            System.out.println("Sponsor added with ID: " + pk);

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

   
    public static void testDelete() {

        try {
            SponsorBean bean = new SponsorBean();
            long pk = 1L; // change as needed

            bean.setSponsorId(pk);
            model.delete(bean);

            System.out.println("Sponsor deleted");

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    
    public static void testUpdate() {

        try {
            SponsorBean bean = new SponsorBean();

            bean.setSponsorId(1L); // existing ID
            bean.setSponsorName("Infosys");
            bean.setContribution(75000.0);
            bean.setContactPerson("Suresh");

            model.update(bean);

            System.out.println("Sponsor updated successfully");

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

   
    public static void testSearch() {

        try {
            SponsorBean bean = new SponsorBean();
            List list = new ArrayList();

            // Filters (optional)
            // bean.setSponsorName("Tata");
            // bean.setContactPerson("Ramesh");

            list = model.search(bean, 0, 0);

            if (list.size() == 0) {
                System.out.println("No records found");
            }

            Iterator it = list.iterator();

            while (it.hasNext()) {

                bean = (SponsorBean) it.next();

                System.out.println("ID: " + bean.getSponsorId());
                System.out.println("Name: " + bean.getSponsorName());
                System.out.println("Contribution: " + bean.getContribution());
                System.out.println("Person: " + bean.getContactPerson());
                System.out.println("----------------------");
            }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}