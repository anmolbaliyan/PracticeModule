package in.com.practice.ctl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.practice.bean.GeoFenceBean;
import in.com.practice.model.GeoFenceModel;

/**
 * Servlet implementation class GeoFenceCtl
 */
@WebServlet("/GeoFenceCtl")
public class GeoFenceCtl extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        GeoFenceModel model = new GeoFenceModel();
        GeoFenceBean bean = new GeoFenceBean();

        String id = request.getParameter("id");

        if (id != null && id.length() > 0) {
            try {
                bean = model.findByPk(Long.parseLong(id));
                request.setAttribute("bean", bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("GeoFenceView.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("operation");

        GeoFenceModel model = new GeoFenceModel();
        GeoFenceBean bean = new GeoFenceBean();

        String code = request.getParameter("geoFenceCode");
        String name = request.getParameter("locationName");
        String radius = request.getParameter("radius");
        String status = request.getParameter("status");

        try {

            bean.setGeoFenceCode(code);
            bean.setLocationName(name);

            if (radius != null && radius.length() > 0) {
                bean.setRadius(Integer.parseInt(radius));
            }

            bean.setStatus(status);

            if ("update".equalsIgnoreCase(op)) {

                bean.setGeoFenceId(Long.parseLong(request.getParameter("id")));
                model.update(bean);

                request.setAttribute("successMsg", "GeoFence updated successfully");

            } else {

                model.add(bean);

                request.setAttribute("successMsg", "GeoFence added successfully");
            }

        } catch (Exception e) {

            request.setAttribute("errorMsg", e.getMessage());
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher("GeoFenceView.jsp");
        rd.forward(request, response);
    }
}