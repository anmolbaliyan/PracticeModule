package in.com.practice.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.practice.bean.BaseBean;
import in.com.practice.bean.ConsumerBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.model.ConsumerModel;
import in.com.practice.util.DataUtility;
import in.com.practice.util.PropertyReader;
import in.com.practice.util.ServletUtility;

/**
 * Controller class for handling Consumer List operations such as search,
 * pagination, and deletion.
 */
@WebServlet(name = "ConsumerListCtl", urlPatterns = { "/ConsumerListCtl" })
public class ConsumerListCtl extends BaseCtl {

	// ===================== POPULATE BEAN =====================
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ConsumerBean bean = new ConsumerBean();

		bean.setConsumerId(DataUtility.getLong(request.getParameter("id")));
		bean.setConsumerCode(DataUtility.getString(request.getParameter("consumerCode")));
		bean.setConsumerGroup(DataUtility.getString(request.getParameter("consumerGroup")));
		bean.setTopicName(DataUtility.getString(request.getParameter("topicName")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		return bean;
	}

	// ===================== DO GET =====================
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		ConsumerBean bean = (ConsumerBean) populateBean(request);
		ConsumerModel model = new ConsumerModel();

		try {
			List<ConsumerBean> list = model.search(bean, pageNo, pageSize);
			List<ConsumerBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	// ===================== DO POST =====================
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		ConsumerBean bean = (ConsumerBean) populateBean(request);
		ConsumerModel model = new ConsumerModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			// ===== SEARCH / PAGINATION =====
			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

				// ===== NEW =====
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.CONSUMER_CTL, request, response);
				return;

				// ===== DELETE =====
			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					ConsumerBean deleteBean = new ConsumerBean();

					for (String id : ids) {
						deleteBean.setConsumerId(DataUtility.getLong(id));
						model.delete(deleteBean);
					}

					ServletUtility.setSuccessMessage("Record deleted successfully", request);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

				// ===== RESET =====
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.CONSUMER_LIST_CTL, request, response);
				return;

				// ===== BACK =====
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.CONSUMER_LIST_CTL, request, response);
				return;
			}

			// ===== FETCH DATA =====
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	// ===================== VIEW =====================
	@Override
	protected String getView() {
		return ORSView.CONSUMER_LIST_VIEW;
	}
}