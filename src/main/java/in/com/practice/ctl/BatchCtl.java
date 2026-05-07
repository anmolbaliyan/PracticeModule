package in.com.practice.ctl;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import in.com.practice.bean.BatchBean;
import in.com.practice.model.BatchModel;

@WebServlet("/BatchCtl")
public class BatchCtl extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BatchModel model = new BatchModel();
		BatchBean bean = new BatchBean();

		String id = request.getParameter("id");

		if (id != null && id.length() > 0) {
			try {
				bean = model.findByPk(Long.parseLong(id));
				request.setAttribute("bean", bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/jsp/BatchView.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("operation");

		BatchModel model = new BatchModel();
		BatchBean bean = new BatchBean();

		try {

			bean.setBatchCode(request.getParameter("batchCode"));

			if (request.getParameter("totalMessages") != null)
				bean.setTotalMessages(Integer.parseInt(request.getParameter("totalMessages")));

			if (request.getParameter("processedCount") != null)
				bean.setProcessedCount(Integer.parseInt(request.getParameter("processedCount")));

			bean.setStatus(request.getParameter("status"));

			if ("update".equalsIgnoreCase(op)) {

				bean.setBatchId(Long.parseLong(request.getParameter("id")));
				model.update(bean);

				request.setAttribute("successMsg", "Batch updated successfully");

			} else {

				model.add(bean);
				request.setAttribute("successMsg", "Batch added successfully");
			}

		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("BatchView.jsp");
		rd.forward(request, response);
	}
}