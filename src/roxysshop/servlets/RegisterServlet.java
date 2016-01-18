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

import roxysshop.businesslogic.AddressManager;
import roxysshop.businesslogic.UserManager;
import roxysshop.general.Constants;
import roxysshop.graphicuserinterface.RegisterGraphicUserInterface;
import roxysshop.helper.Record;

public class RegisterServlet extends HttpServlet {

	public final static long serialVersionUID = 20152015L;

	private UserManager userManager;
	private AddressManager addressManager;
	
	private List<String> addresses;
	private List<String> values;
	String registerError;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userManager = new UserManager();
		addressManager = new AddressManager();
	}

	@Override
	public void destroy() {
	}
	
	public String isRegisterError(List<String> values) {
		return userManager.verifyRegistration(values);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			values = new ArrayList<>();
			values.add(Constants.FIRST_NAME);
			values.add(Constants.LAST_NAME);
			values.add(Constants.PHONE_NUMBER);
			values.add(Constants.EMAIL);
			values.add(Constants.USERNAME);
			values.add(Constants.PASSWORD);
			
			registerError = null;
			addresses = new ArrayList<>();
			
			Enumeration<String> parameters = request.getParameterNames();
			boolean found = true;
			while (parameters.hasMoreElements()) {
				String parameter = (String) parameters.nextElement();
				if (parameter.equals(Constants.FIRST_NAME)) {
					String firstName = request.getParameter(parameter);
					values.set(0, firstName);
					if (firstName == null || firstName.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.LAST_NAME)) {
					String lastName = request.getParameter(parameter);
					values.set(1, lastName);
					if (lastName == null || lastName.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.PHONE_NUMBER)) {
					String phoneNumber = request.getParameter(parameter);
					values.set(2, phoneNumber);
					if (phoneNumber == null || phoneNumber.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.EMAIL)) {
					String email = request.getParameter(parameter);
					values.set(3, email);
					if (email == null || email.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.USERNAME)) {
					String username = request.getParameter(parameter);
					values.set(4, username);
					if (username == null || username.isEmpty())
						found = false;
				}
				if (parameter.equals(Constants.PASSWORD)) {
					String password = request.getParameter(parameter);
					values.set(5, password);
					if (password == null || password.isEmpty())
						found = false;
				}			
				if (parameter.equals(Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + ".x")) {
					String address = request.getParameter(Constants.ADDRESS.toLowerCase());
					if (!addresses.contains(address)) {
						addresses.add(address);
						//filterChange = true;
					}
				}
				if (parameter.startsWith(Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "_")
						&& parameter.endsWith(".x")) {
					String address = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
					if (addresses.contains(address)) {
						addresses.remove(address);
					}
				}
				
				if (parameter.equals(Constants.SIGNUP.toLowerCase() + ".x")) {
					if (!values.get(4).equals(Constants.USERNAME) || !values.get(5).equals(Constants.PASSWORD)
						|| !values.get(0).equals(Constants.FIRST_NAME) || !values.get(1).equals(Constants.LAST_NAME)
						|| !values.get(3).equals(Constants.EMAIL) || !values.get(2).equals(Constants.PHONE_NUMBER)) {
						
						if (found == false || addresses.isEmpty() || addresses == null) {
							registerError = Constants.ERROR_EMPTY_FIELDS;
						} else {
							registerError = isRegisterError(values);
							if (registerError == null) {
								long id = userManager.create(values);
								for (String address : addresses) {
									List<String> val = new ArrayList<>();
									val.add(address);
									val.add(id + "");
									addressManager.create(val);
								}
								
								HttpSession session = request.getSession(true);
								session.setAttribute(Constants.DISPLAY, userManager.getDisplay(values.get(4), values.get(5)));
								RequestDispatcher dispatcher = null;
								dispatcher = getServletContext().getRequestDispatcher("/" + Constants.CLIENT_SERVLET_PAGE_CONTEXT);
								if (dispatcher != null) {
									dispatcher.forward(request, response);
								}
							}
						}
					}
				}
			}
			RegisterGraphicUserInterface.displayRegisterGraphicUserInterface(values, addresses, registerError, printWriter);
		}
	}
	

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
