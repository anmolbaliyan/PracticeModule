package in.com.practice.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import in.com.practice.bean.BatchBean;
import in.com.practice.model.BatchModel;

@WebServlet("/BatchListCtl")
public class BatchListCtl extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BatchModel model = new BatchModel();

        int pageNo = 1;
        int pageSize = 5;

        try {
            List<BatchBean> list = model.search(null, pageNo, pageSize);

            request.setAttribute("list", list);
            request.setAttribute("pageNo", pageNo);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("nextListSize", list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher("/jsp/BatchListView.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BatchModel model = new BatchModel();
        BatchBean bean = new BatchBean();

        String op = request.getParameter("operation");

        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        if ("next".equalsIgnoreCase(op)) pageNo++;
        if ("previous".equalsIgnoreCase(op) && pageNo > 1) pageNo--;

        if ("delete".equalsIgnoreCase(op)) {

            String[] ids = request.getParameterValues("ids");

            if (ids != null) {
                for (String id : ids) {
                    try {
                        bean.setBatchId(Long.parseLong(id));
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
            bean.setBatchCode(request.getParameter("batchCode"));
            bean.setStatus(request.getParameter("status"));
        }

        try {
            List<BatchBean> list = model.search(bean, pageNo, pageSize);

            request.setAttribute("list", list);
            request.setAttribute("pageNo", pageNo);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("nextListSize", list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher("BatchListView.jsp");
        rd.forward(request, response);
    }
}