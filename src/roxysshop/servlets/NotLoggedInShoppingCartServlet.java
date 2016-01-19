package roxysshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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
import roxysshop.businesslogic.InvoiceDetailsManager;
import roxysshop.businesslogic.InvoiceManager;
import roxysshop.businesslogic.SizeManager;
import roxysshop.businesslogic.UserManager;
import roxysshop.general.Constants;
import roxysshop.general.Utilities;
import roxysshop.graphicuserinterface.NotLoggedInShoppingCartGraphicUserInterface;
import roxysshop.helper.Record;

public class NotLoggedInShoppingCartServlet  extends HttpServlet {
	final public static long serialVersionUID = 10021002L;
	
	private UserManager userManager;
	private AddressManager addressManager;
	private InvoiceManager invoiceManager;
	private InvoiceDetailsManager invoiceDetailsManager;
	private SizeManager sizeManager;
	
	List<String> values;
	
	private List<List<String>> shoppingCart;
	private String errorMessage;
	private List<Record> addresses;
	String selected_address_id;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userManager = new UserManager();
		addressManager = new AddressManager();
		invoiceManager = new InvoiceManager();
		invoiceDetailsManager = new InvoiceDetailsManager();
		sizeManager = new SizeManager();
		
		selected_address_id = null;
	}
	
	@Override
	public void destroy() {
	}
	
	public String isRegisterError(List<String> values) {
		return userManager.verifyRegistration(values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if (session == null) {
			return;
		}
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			values = new ArrayList<>();
			values.add(Constants.FIRST_NAME);
			values.add(Constants.LAST_NAME);
			values.add(Constants.PHONE_NUMBER);
			values.add(Constants.EMAIL);
			values.add(Constants.USERNAME);
			values.add(Constants.PASSWORD);
			
			errorMessage = null;
			if (addresses == null) addresses = new ArrayList<>();
			
			shoppingCart = (List<List<String>>) session.getAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()));
			if (shoppingCart == null) {
				shoppingCart = new ArrayList<>();
			}
						
			boolean found = true;
			Enumeration<String> parameters = request.getParameterNames();
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
				if (parameter.startsWith(Constants.SELECT_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "_")
						&& parameter.endsWith(".x")) {
					selected_address_id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
				}
				
				if (parameter.startsWith(Utilities.removeSpaces(Constants.DELETE_BUTTON_NAME) + "_" + Constants.SHOPPING_CART + "_")
						&& parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1,
							parameter.indexOf(".x"));
					String size = request.getParameter("size_" + id);
					for (List<String> shoppingCartRecord : shoppingCart) {
						if (shoppingCartRecord.get(0).equals(id) && shoppingCartRecord.get(2).equals(size)) {
							shoppingCart.remove(shoppingCartRecord);
							break;
						}
					}
				}
				
				if (parameter.startsWith(Utilities.removeSpaces(Constants.UPDATE_BUTTON_NAME) + "_" + Constants.SHOPPING_CART + "_") 
						&& parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1,
							parameter.indexOf(".x"));
					String size = request.getParameter("size_" + id);
					String value = request.getParameter("order_input_" + id);
					for (List<String> shoppingCartRecord : shoppingCart) {
						if (shoppingCartRecord.get(0).equals(id)) {
							shoppingCartRecord.set(1, value);
							shoppingCartRecord.set(2, size);
							break;
						}
					}

				}
				
				if (parameter.equals(Utilities.removeSpaces(Constants.COMPLETE_COMMAND.toLowerCase()) + ".x")) {
					if (!values.get(4).equals(Constants.USERNAME) || !values.get(5).equals(Constants.PASSWORD)
							|| !values.get(0).equals(Constants.FIRST_NAME) || !values.get(1).equals(Constants.LAST_NAME)
							|| !values.get(3).equals(Constants.EMAIL) || !values.get(2).equals(Constants.PHONE_NUMBER)) {
							
						if (found == false || addresses.isEmpty() || addresses == null) {
							NotLoggedInShoppingCartGraphicUserInterface.displayNotLoggedInShoppingCartGraphicUserInterface(values, shoppingCart, addresses, Constants.ERROR_EMPTY_FIELDS, printWriter);
							return;
						} else {
							errorMessage = isRegisterError(values);
							if (errorMessage != null) {
								NotLoggedInShoppingCartGraphicUserInterface.displayNotLoggedInShoppingCartGraphicUserInterface(values, shoppingCart, addresses, errorMessage, printWriter);
								return;
							}
							long identifier = userManager.create(values);
							for (Record address : addresses) {
								if (addressManager.verifyAddress(Long.parseLong(address.getAttribute()))) {
									continue;
								}
								List<String> val = new ArrayList<>();
								val.add(address.getValue().toString());
								val.add(identifier + "");
								addressManager.create(val);
							}
							
							List<String> invoice = new ArrayList<>();
							invoice.add(identifier + "");
							invoice.add(selected_address_id);
							invoice.add("0");
							invoice.add(LocalDate.now().toString());
							invoice.add("0");
							long invoiceId = invoiceManager.create(invoice);
							
							for (List<String> shoppingCartRecord : shoppingCart) {
								long productId = Long.parseLong(shoppingCartRecord.get(0));
								int quantity = Integer.parseInt(shoppingCartRecord.get(1).toString());
								String size = shoppingCartRecord.get(2);
								int stockpile = sizeManager.getStockpile(productId, size);
								if (quantity <= stockpile) {
									List<String> productAttributes = new ArrayList<>();
									productAttributes.add("stockpile");
									List<String> productValues = new ArrayList<>();
									productValues.add(String.valueOf(stockpile - quantity));
									sizeManager.update(productAttributes, productValues,
											productId, size);
									List<String> invoiceDetails = new ArrayList<>();
									invoiceDetails.add(String.valueOf(invoiceId));
									invoiceDetails.add(String.valueOf(productId));
									invoiceDetails.add(String.valueOf(quantity));
									invoiceDetails.add(size);
									invoiceDetailsManager.create(invoiceDetails);
									shoppingCart = new ArrayList<>();
									session.setAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()), shoppingCart);
								} else {
									errorMessage = Constants.INVALID_COMMAND_ERROR1 + productId + Constants.INVALID_COMMAND_ERROR2;
								}
							}
						}
					}
				}

				if (parameter.equals(Utilities.removeSpaces(Constants.CANCEL_COMMAND.toLowerCase()) + ".x")) {
					shoppingCart = new ArrayList<>();
					session.setAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()), shoppingCart);
				}
				
				if (parameter.equals(Constants.HOME.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.NOT_LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				if (parameter.equals(Constants.SIGNIN.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.LOGIN_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					break;
				}
				if (parameter.equals(Constants.SIGNUP.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.REGISTER_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					break;
				}
				
				if (parameter.equals(Constants.SHOPPING_CART.toLowerCase() + ".x")) {
					//TODO
				}
			}
			

			NotLoggedInShoppingCartGraphicUserInterface.displayNotLoggedInShoppingCartGraphicUserInterface(values, shoppingCart, addresses, errorMessage, printWriter);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
