package in.com.practice.test;

import java.util.List;

import in.com.practice.bean.GeoFenceBean;
import in.com.practice.model.GeoFenceModel;

public class GeoFenceTest {

    public static void main(String[] args) throws Exception {

         //testAdd();
        // testUpdate();
         //testDelete();
        // testFindByPk();
        testSearch();
    }

    public static void testAdd() throws Exception {

        GeoFenceBean bean = new GeoFenceBean();

        bean.setGeoFenceCode("GF002");
        bean.setLocationName("Indore");
        bean.setRadius(500);
        bean.setStatus("ACTIVE");

        GeoFenceModel model = new GeoFenceModel();
        long pk = model.add(bean);

        System.out.println("Data Added Successfully, PK = " + pk);
    }

    public static void testUpdate() throws Exception {

        GeoFenceBean bean = new GeoFenceBean();

        bean.setGeoFenceId(2L); // existing ID
        bean.setGeoFenceCode("GF002");
        bean.setLocationName("Bhopal");
        bean.setRadius(800);
        bean.setStatus("INACTIVE");

        GeoFenceModel model = new GeoFenceModel();
        model.update(bean);

        System.out.println("Data Updated Successfully");
    }

    public static void testDelete() throws Exception {

        GeoFenceBean bean = new GeoFenceBean();
        bean.setGeoFenceId(2L); // existing ID

        GeoFenceModel model = new GeoFenceModel();
        model.delete(bean);

        System.out.println("Data Deleted Successfully");
    }

    public static void testFindByPk() throws Exception {

        GeoFenceModel model = new GeoFenceModel();

        GeoFenceBean bean = model.findByPk(1);

        if (bean != null) {
            System.out.println("ID: " + bean.getGeoFenceId());
            System.out.println("Code: " + bean.getGeoFenceCode());
            System.out.println("Location: " + bean.getLocationName());
            System.out.println("Radius: " + bean.getRadius());
            System.out.println("Status: " + bean.getStatus());
        } else {
            System.out.println("Record Not Found");
        }
    }

    public static void testSearch() throws Exception {

        GeoFenceBean bean = new GeoFenceBean();

        GeoFenceModel model = new GeoFenceModel();

        List<GeoFenceBean> list = model.search(bean, 1, 10);

        for (GeoFenceBean b : list) {

            System.out.println("ID: " + b.getGeoFenceId());
            System.out.println("Code: " + b.getGeoFenceCode());
            System.out.println("Location: " + b.getLocationName());
            System.out.println("Radius: " + b.getRadius());
            System.out.println("Status: " + b.getStatus());
            System.out.println("---------------------------");
        }
    }
}