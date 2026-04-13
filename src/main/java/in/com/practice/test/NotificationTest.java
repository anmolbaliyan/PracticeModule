package in.com.practice.test;

import java.util.List;

import in.com.practice.bean.NotificationBean;
import in.com.practice.model.NotificationModel;

public class NotificationTest {

	public static void main(String[] args) throws Exception {

		// testAdd();
		// testUpdate();
		// testDelete();
		// testFindByPk();
		testSearch();
	}

	// ===================== ADD =====================
	public static void testAdd() throws Exception {

		NotificationBean bean = new NotificationBean();

		bean.setPrefCode("EMAIL");
		bean.setUserName("Anmol");
		bean.setPreference("Daily Alerts");
		bean.setStatus("Active");

		NotificationModel model = new NotificationModel();
		long pk = model.add(bean);

		System.out.println("Data Added Successfully, PK = " + pk);
	}

	// ===================== UPDATE =====================
	public static void testUpdate() throws Exception {

		NotificationBean bean = new NotificationBean();

		bean.setPrefId(1L); // existing ID
		bean.setPrefCode("SMS");
		bean.setUserName("Anmol Updated");
		bean.setPreference("Weekly Alerts");
		bean.setStatus("Inactive");

		NotificationModel model = new NotificationModel();
		model.update(bean);

		System.out.println("Data Updated Successfully");
	}

	// ===================== DELETE =====================
	public static void testDelete() throws Exception {

		NotificationBean bean = new NotificationBean();
		bean.setPrefId(1L); // existing ID

		NotificationModel model = new NotificationModel();
		model.delete(bean);

		System.out.println("Data Deleted Successfully");
	}

	// ===================== FIND BY PK =====================
	public static void testFindByPk() throws Exception {

		NotificationModel model = new NotificationModel();

		NotificationBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println("ID: " + bean.getPrefId());
			System.out.println("Code: " + bean.getPrefCode());
			System.out.println("Name: " + bean.getUserName());
			System.out.println("Preference: " + bean.getPreference());
			System.out.println("Status: " + bean.getStatus());
		} else {
			System.out.println("Record Not Found");
		}
	}

	// ===================== SEARCH =====================
	public static void testSearch() throws Exception {

		NotificationBean bean = new NotificationBean();

		// Optional filters (uncomment if needed)
		// bean.setPrefCode("EMAIL");
		// bean.setUserName("Anmol");
		// bean.setStatus("Active");

		NotificationModel model = new NotificationModel();

		List<NotificationBean> list = model.search(bean, 1, 10);

		for (NotificationBean b : list) {

			System.out.println("ID: " + b.getPrefId());
			System.out.println("Code: " + b.getPrefCode());
			System.out.println("Name: " + b.getUserName());
			System.out.println("Preference: " + b.getPreference());
			System.out.println("Status: " + b.getStatus());
			System.out.println("----------------------------");
		}
	}
}