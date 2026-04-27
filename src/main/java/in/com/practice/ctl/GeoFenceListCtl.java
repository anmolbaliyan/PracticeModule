package in.com.practice.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.practice.bean.GeoFenceBean;
import in.com.practice.model.GeoFenceModel;

@WebServlet("/GeoFenceListCtl")
public class GeoFenceListCtl extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		GeoFenceModel model = new GeoFenceModel();

		int pageNo = 1;
		int pageSize = 5;

		try {
			List<GeoFenceBean> list = model.search(null, pageNo, pageSize);

			request.setAttribute("list", list);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("nextListSize", list.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("GeoFenceListView.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		GeoFenceModel model = new GeoFenceModel();
		GeoFenceBean bean = new GeoFenceBean();

		String op = request.getParameter("operation");

		int pageNo = 1;
		int pageSize = 5;

		if (request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}

		if (request.getParameter("pageSize") != null) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}

		if ("next".equalsIgnoreCase(op)) {
			pageNo++;
		}

		if ("previous".equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		}

		if ("delete".equalsIgnoreCase(op)) {

			String[] ids = request.getParameterValues("ids");

			if (ids != null) {
				for (String id : ids) {
					try {
						bean.setGeoFenceId(Long.parseLong(id));
						model.delete(bean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				request.setAttribute("successMsg", "Deleted successfully");
			} else {
				request.setAttribute("errorMsg", "Select at least one record");
			}
		}

		if ("search".equalsIgnoreCase(op)) {
			bean.setGeoFenceCode(request.getParameter("geoFenceCode"));
			bean.setLocationName(request.getParameter("locationName"));
			bean.setStatus(request.getParameter("status"));
		}

		try {
			List<GeoFenceBean> list = model.search(bean, pageNo, pageSize);

			request.setAttribute("list", list);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("nextListSize", list.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("GeoFenceListView.jsp");
		rd.forward(request, response);
	}
}