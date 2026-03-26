package in.com.practice.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.com.practice.bean.ReviewBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.model.ReviewModel;

public class ReviewTest {

	public static ReviewModel model = new ReviewModel();

	public static void main(String[] args) {

		// testAdd();
		// testDelete();
		// testUpdate();
		//testSearch();
		testFindByPk();
	}

	public static void testAdd() {

		try {
			ReviewBean bean = new ReviewBean();

			bean.setReviewCode("REV001");
			bean.setUserName("Anmol");
			bean.setRating(5);
			bean.setStatus("Active");

			long pk = model.add(bean);

			System.out.println("Review added with ID: " + pk);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		try {
			ReviewBean bean = new ReviewBean();
			long pk = 1L; // change this ID as needed

			bean.setReviewId(pk);
			model.delete(bean);

			System.out.println("Review deleted successfully");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		try {
			ReviewBean bean = new ReviewBean();

			bean.setReviewId(1L); // existing ID
			bean.setReviewCode("REV002");
			bean.setUserName("Updated User");
			bean.setRating(4);
			bean.setStatus("Inactive");

			model.update(bean);

			System.out.println("Review updated successfully");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		try {
			long pk = 1; // change to existing ID

			ReviewBean bean = model.findByPk(pk);

			if (bean == null) {
				System.out.println("Record not found");
				return;
			}

			System.out.println("ID: " + bean.getReviewId());
			System.out.println("Code: " + bean.getReviewCode());
			System.out.println("User Name: " + bean.getUserName());
			System.out.println("Rating: " + bean.getRating());
			System.out.println("Status: " + bean.getStatus());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		try {
			ReviewBean bean = new ReviewBean();
			List list = new ArrayList();

			// bean.setReviewCode("REV");
			// bean.setUserName("Anmol");
			// bean.setStatus("Active");

			list = model.search(bean, 0, 0);

			if (list.size() == 0) {
				System.out.println("No records found");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {

				bean = (ReviewBean) it.next();

				System.out.println("ID: " + bean.getReviewId());
				System.out.println("Code: " + bean.getReviewCode());
				System.out.println("User Name: " + bean.getUserName());
				System.out.println("Rating: " + bean.getRating());
				System.out.println("Status: " + bean.getStatus());
				System.out.println("------------------------");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}