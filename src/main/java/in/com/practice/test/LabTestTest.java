package in.com.practice.test;

import java.text.SimpleDateFormat;
import java.util.List;

import in.com.practice.bean.LabTestBean;
import in.com.practice.model.LabTestModel;

public class LabTestTest {

	public static void main(String[] args) throws Exception {

		// testAdd();
		 testUpdate();
		// testDelete();
		// testFindByPk();
		//testSearch();
	}

	// ===================== ADD =====================
	public static void testAdd() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		LabTestBean bean = new LabTestBean();

		bean.setTestName("Blood Test");
		bean.setCost(500.0);
		bean.setTestDate(sdf.parse("2026-04-14"));

		LabTestModel model = new LabTestModel();
		long pk = model.add(bean);

		System.out.println("Data Added Successfully, PK = " + pk);
	}

	// ===================== UPDATE =====================
	public static void testUpdate() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		LabTestBean bean = new LabTestBean();

		bean.setLabTestId(1L);
		bean.setTestName("Urine Test");
		bean.setCost(300.0);
		bean.setTestDate(sdf.parse("2026-04-15"));

		LabTestModel model = new LabTestModel();
		model.update(bean);

		System.out.println("Data Updated Successfully");
	}

	// ===================== DELETE =====================
	public static void testDelete() throws Exception {

		LabTestBean bean = new LabTestBean();
		bean.setLabTestId(1L);

		LabTestModel model = new LabTestModel();
		model.delete(bean);

		System.out.println("Data Deleted Successfully");
	}

	// ===================== FIND BY PK =====================
	public static void testFindByPk() throws Exception {

		LabTestModel model = new LabTestModel();

		LabTestBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println("ID: " + bean.getLabTestId());
			System.out.println("Name: " + bean.getTestName());
			System.out.println("Cost: " + bean.getCost());
			System.out.println("Date: " + bean.getTestDate());
		} else {
			System.out.println("Record Not Found");
		}
	}

	// ===================== SEARCH =====================
	public static void testSearch() throws Exception {

		LabTestBean bean = new LabTestBean();

		// Optional filters
		// bean.setTestName("Blood");

		LabTestModel model = new LabTestModel();

		List<LabTestBean> list = model.search(bean, 1, 10);

		for (LabTestBean b : list) {

			System.out.println("ID: " + b.getLabTestId());
			System.out.println("Name: " + b.getTestName());
			System.out.println("Cost: " + b.getCost());
			System.out.println("Date: " + b.getTestDate());
			System.out.println("----------------------");
		}
	}
}