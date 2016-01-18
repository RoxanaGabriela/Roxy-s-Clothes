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
import roxysshop.graphicuserinterface.UpdateGraphicUserInterface;
import roxysshop.helper.Record;

public class UpdateServlet extends HttpServlet {
	final public static long serialVersionUID = 10021002L;
	
	private UserManager userManager;
	private AddressManager addressManager;
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String username;
	private String password;
	
	private List<Record> addresses;
	
	private String updateError;
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userManager = new UserManager();
		updateError = null;
		addressManager = new AddressManager();
	
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
			if (addresses == null) addresses = addressManager.getAddresses(userManager.getIdentifier(display));
				
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
				
				if (parameter.equals(Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + ".x")) {
					String new_address = request.getParameter(Constants.ADDRESS.toLowerCase());
					boolean exists = false;
					for ( Record address : addresses) {
						if (address.getValue().equals(new_address)) {
							exists = true;
							break;
						}
					}
					if (!exists) {
						addresses.add(new Record(addressManager.getNextIdentifier() + "", new_address));
					}
				}
				if (parameter.startsWith(Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "_")
						&& parameter.endsWith(".x")) {
					String del_address = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
					for (Record address : addresses) {
						if (address.getAttribute().equals(del_address)) {
							addresses.remove(address);
							addressManager.delete(Long.parseLong(address.getAttribute()));
							break;
						}
					}
				}
				
				if (parameter.equals(Constants.HOME.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.CLIENT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				if (parameter.equals(Constants.ACCOUNT.toLowerCase() + ".x")) {
					//TODO
				}
				
				if (parameter.equals(Constants.UPDATE.toLowerCase() + ".x")) {
					//TODO
				}
				
				if (parameter.equals(Constants.HISTORY.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.HISTORY_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if (parameter.equals(Constants.SHOPPING_CART.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.SHOPPING_CART_SERVLET_PAGE_CONTEXT);
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
					if (found == false) UpdateGraphicUserInterface.displayUpdateGraphicUserInterface(display, addresses, Constants.ERROR_EMPTY_FIELDS, printWriter);
					else {
						updateError = isUpdateError(values);
						if (updateError == null) {
							userManager.update(values, userManager.getIdentifier(display));
							for (Record address : addresses) {
								if (addressManager.verifyAddress(Long.parseLong(address.getAttribute()))) {
									continue;
								}
								List<String> val = new ArrayList<>();
								val.add(address.getValue().toString());
								val.add(userManager.getIdentifier(display) + "");
								addressManager.create(val);
							}
						}
					}
				}
			}
			UpdateGraphicUserInterface.displayUpdateGraphicUserInterface(display, addresses, updateError, printWriter);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
