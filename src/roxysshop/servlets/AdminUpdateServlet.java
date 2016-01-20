package roxysshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import roxysshop.businesslogic.UserManager;
import roxysshop.general.Constants;
import roxysshop.graphicuserinterface.AdminUpdateGraphicUserInterface;

public class AdminUpdateServlet extends HttpServlet {
	final public static long serialVersionUID = 10021002L;
	
	private UserManager userManager;
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String username;
	private String password;
	
	private String updateError;
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userManager = new UserManager();
		updateError = null;
	
	}

	@Override
	public void destroy() {
	}
	
	public String isUpdateError(List<String> values) {
		return userManager.verifyUpdate(values);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if (session == null || session.getAttribute(Constants.DISPLAY) == null) {
			return;
		}
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			String display = session.getAttribute(Constants.DISPLAY).toString();
				
			boolean found = true;
			Enumeration<String> parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String parameter = (String) parameters.nextElement();
				if (parameter.equals(Constants.USERNAME)) {
					username = request.getParameter(parameter);
					if (username == null || username.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.PASSWORD)) {
					password = request.getParameter(parameter);
					if (password == null || password.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.FIRST_NAME)) {
					firstName = request.getParameter(parameter);
					if (firstName == null || firstName.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.LAST_NAME)) {
					lastName = request.getParameter(parameter);
					if (lastName == null || lastName.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.EMAIL)) {
					email = request.getParameter(parameter);
					if (email == null || email.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.PHONE_NUMBER)) {
					phoneNumber = request.getParameter(parameter);
					if (phoneNumber == null || phoneNumber.isEmpty())
						found = false;
				}
				
				
				if (parameter.equals(Constants.HOME.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.ADMINISTRATOR_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				if (parameter.equals(Constants.ACCOUNT.toLowerCase() + ".x")) {
					//TODO
				}
				
				if (parameter.equals(Constants.HISTORY.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.INVOICES_HISTORY_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if (parameter.equals(Constants.CLIENTS_HISTORY.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.CLIENTS_HISTORY_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if (parameter.equals(Constants.PRODUCTS.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.PRODUCTS_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if (parameter.equals(Constants.SIGNOUT.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.NOT_LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					session.invalidate();
					break;
				}
				
				if (parameter.equals(Constants.UPDATE.toLowerCase() + ".x")) {
					List<String> values = new ArrayList<>();
					values.add(userManager.getIdentifier(display) + "");
					values.add(firstName);
					values.add(lastName);
					values.add(phoneNumber);
					values.add(email);
					values.add(userManager.getUsername(display));
					values.add(password);
					if (found == false) AdminUpdateGraphicUserInterface.displayAdminUpdateGraphicUserInterface(display, Constants.ERROR_EMPTY_FIELDS, printWriter);
					else {
						updateError = isUpdateError(values);
						if (updateError == null) {
							userManager.update(values, userManager.getIdentifier(display));
						}
					}
				}
			}
			AdminUpdateGraphicUserInterface.displayAdminUpdateGraphicUserInterface(display, updateError, printWriter);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
