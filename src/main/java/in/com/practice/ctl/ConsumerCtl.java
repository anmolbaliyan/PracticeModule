package in.com.practice.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.practice.bean.BaseBean;
import in.com.practice.bean.ConsumerBean;
import in.com.practice.exception.ApplicationException;
import in.com.practice.model.ConsumerModel;
import in.com.practice.util.DataUtility;
import in.com.practice.util.DataValidator;
import in.com.practice.util.PropertyReader;
import in.com.practice.util.ServletUtility;

/**
 * Controller class for handling Consumer operations such as
 * adding, updating, and viewing consumer details.
 *
 * This servlet is mapped to /ctl/ConsumerCtl and follows MVC pattern.
 */
@WebServlet(name = "ConsumerCtl", urlPatterns = { "/ConsumerCtl" })
public class ConsumerCtl extends BaseCtl {

    // ===================== VALIDATION =====================
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("consumerCode"))) {
            request.setAttribute("consumerCode",
                    PropertyReader.getValue("error.require", "Consumer Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("consumerGroup"))) {
            request.setAttribute("consumerGroup",
                    PropertyReader.getValue("error.require", "Consumer Group"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("topicName"))) {
            request.setAttribute("topicName",
                    PropertyReader.getValue("error.require", "Topic Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    // ===================== POPULATE BEAN =====================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        ConsumerBean bean = new ConsumerBean();

        bean.setConsumerId(DataUtility.getLong(request.getParameter("id")));
        bean.setConsumerCode(DataUtility.getString(request.getParameter("consumerCode")));
        bean.setConsumerGroup(DataUtility.getString(request.getParameter("consumerGroup")));
        bean.setTopicName(DataUtility.getString(request.getParameter("topicName")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        populateDTO(bean, request);

        return bean;
    }

    // ===================== DO GET =====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        ConsumerModel model = new ConsumerModel();

        if (id > 0) {
            try {
                ConsumerBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    // ===================== DO POST =====================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        ConsumerModel model = new ConsumerModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        // ===== SAVE =====
        if (OP_SAVE.equalsIgnoreCase(op)) {

            ConsumerBean bean = (ConsumerBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Consumer added successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        // ===== UPDATE =====
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            ConsumerBean bean = (ConsumerBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Consumer updated successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        // ===== CANCEL =====
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.CONSUMER_LIST_CTL, request, response);
            return;

        // ===== RESET =====
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.CONSUMER_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    // ===================== VIEW =====================
    @Override
    protected String getView() {
        return ORSView.CONSUMER_VIEW;
    }
}