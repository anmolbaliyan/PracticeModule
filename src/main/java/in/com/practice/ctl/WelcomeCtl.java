package in.com.practice.ctl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WelcomeCtl")
public class WelcomeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	// ===================== GET =====================
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// IMPORTANT: absolute path (starts with /)
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/Welcome.jsp");
		rd.forward(request, response);
	}

	// ===================== POST =====================
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	@Override
	protected String getView() {
		return ORSView.WELCOME_VIEW;
	}
}