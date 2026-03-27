package in.com.practice.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.com.practice.bean.InvoiceBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.model.InvoiceModel;

public class InvoiceTest {

	public static InvoiceModel model = new InvoiceModel();

	public static void main(String[] args) {

		 //testAdd();
		 //testDelete();
		 testUpdate();
		// testSearch();
		// testFindByPk();
	}

	public static void testAdd() {

		try {
			InvoiceBean bean = new InvoiceBean();

			bean.setInvoiceCode("REV00bdiGT567");
			bean.setCustomerName("anmol");
			bean.setTotalAmount(1567856.98);
			bean.setStatus("processing");

			long pk = model.add(bean);

			System.out.println("Invoice added with ID: " + pk);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		try {
			InvoiceBean bean = new InvoiceBean();
			long pk = 1L; // change this ID as needed

			bean.setInvoiceId(pk);
			model.delete(bean);

			System.out.println("Invoice deleted successfully");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		try {
			InvoiceBean bean = new InvoiceBean();

			bean.setInvoiceId(2L); // existing ID
			bean.setInvoiceCode("REV006478SGHG");
			bean.setCustomerName("Baliyan");
			bean.setTotalAmount(164774.64);
			bean.setStatus("Processing");

			model.update(bean);

			System.out.println("Invoice updated successfully");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		try {
			long pk = 1; // change to existing ID

			InvoiceBean bean = model.findByPk(pk);

			if (bean == null) {
				System.out.println("Record not found");
				return;
			}

			System.out.println("ID: " + bean.getInvoiceId());
			System.out.println("Code: " + bean.getInvoiceCode());
			System.out.println("User Name: " + bean.getCustomerName());
			System.out.println("Rating: " + bean.getTotalAmount());
			System.out.println("Status: " + bean.getStatus());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		try {
			InvoiceBean bean = new InvoiceBean();
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

				bean = (InvoiceBean) it.next();

				System.out.println("ID: " + bean.getInvoiceId());
				System.out.println("Code: " + bean.getInvoiceCode());
				System.out.println("User Name: " + bean.getCustomerName());
				System.out.println("Rating: " + bean.getTotalAmount());
				System.out.println("Status: " + bean.getStatus());
				System.out.println("------------------------");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}