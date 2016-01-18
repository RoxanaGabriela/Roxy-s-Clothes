package roxysshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import roxysshop.businesslogic.UserManager;
import roxysshop.general.Constants;
import roxysshop.graphicuserinterface.LoginGraphicUserInterface;

public class LoginServlet extends HttpServlet {

	public final static long serialVersionUID = 20152015L;

	private UserManager userManager;

	private String username, password;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userManager = new UserManager();
		username = new String();
		password = new String();
	}

	@Override
	public void destroy() {
	}

	public boolean isLoginError(String username, String password) {
		return (username != null && !username.isEmpty() && password != null && !password.isEmpty()
				&& userManager.verifyUser(username, password) == Constants.USER_NONE);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Enumeration<String> parameters = request.getParameterNames();
		boolean found = false;
		while (parameters.hasMoreElements()) {
			String parameter = (String) parameters.nextElement();
			if (parameter.equals(Constants.USERNAME)) {
				found = true;
				username = request.getParameter(parameter);
			}
			if (parameter.equals(Constants.PASSWORD)) {
				found = true;
				password = request.getParameter(parameter);
			}
		}
		if (!found) {
			username = "";
			password = "";
		}
		response.setContentType("text/html");
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			int type = userManager.verifyUser(username, password);
			if (type != Constants.USER_NONE) {
				session.setAttribute(Constants.DISPLAY, userManager.getDisplay(username, password));
				RequestDispatcher dispatcher = null;
				dispatcher = getServletContext().getRequestDispatcher("/" + Constants.CLIENT_SERVLET_PAGE_CONTEXT);
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}

			LoginGraphicUserInterface.displayLoginGraphicUserInterface(isLoginError(username, password), printWriter);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
