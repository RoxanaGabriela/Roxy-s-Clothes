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
import roxysshop.graphicuserinterface.ShoppingCartGraphicUserInterface;
import roxysshop.helper.Record;

public class ShoppingCartServlet  extends HttpServlet {
	final public static long serialVersionUID = 10021002L;
	
	private UserManager userManager;
	private AddressManager addressManager;
	private InvoiceManager invoiceManager;
	private InvoiceDetailsManager invoiceDetailsManager;
	private SizeManager sizeManager;
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String username;
	private String password;
	
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
		
		errorMessage = null;
		selected_address_id = null;
	}
	
	@Override
	public void destroy() {
	}
	
	public String isUpdateError(List<String> values) {
		return userManager.verifyUpdate(values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if (session == null || session.getAttribute(Constants.DISPLAY) == null) {
			return;
		}
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			String display = session.getAttribute(Constants.DISPLAY).toString();
			if (addresses == null) addresses = addressManager.getAddresses(userManager.getIdentifier(display));
			
			shoppingCart = (List<List<String>>) session.getAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()));
			if (shoppingCart == null) {
				shoppingCart = new ArrayList<>();
			}
						
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
					List<String> values = new ArrayList<>();
					values.add(userManager.getIdentifier(display) + "");
					values.add(firstName);
					values.add(lastName);
					values.add(phoneNumber);
					values.add(email);
					values.add(userManager.getUsername(display));
					values.add(password);
					if (found == false) {
						ShoppingCartGraphicUserInterface.displayShoppingCartGraphicUserInterface(display, shoppingCart, addresses, Constants.ERROR_EMPTY_FIELDS, printWriter);
						return;
					} else {
						errorMessage = isUpdateError(values);
						if (errorMessage != null) {
							ShoppingCartGraphicUserInterface.displayShoppingCartGraphicUserInterface(display, shoppingCart, addresses, errorMessage, printWriter);
							return;
						}
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
						
						List<String> invoice = new ArrayList<>();
						invoice.add(String.valueOf(userManager.getIdentifier(display)));
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

				if (parameter.equals(Utilities.removeSpaces(Constants.CANCEL_COMMAND.toLowerCase()) + ".x")) {
					shoppingCart = new ArrayList<>();
					session.setAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()), shoppingCart);
				}
				
				if (parameter.equals(Constants.HOME.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.CLIENT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				if (parameter.equals(Constants.ACCOUNT.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.UPDATE_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if (parameter.equals(Constants.SHOPPING_CART.toLowerCase() + ".x")) {
					//TODO
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
			}
			

			ShoppingCartGraphicUserInterface.displayShoppingCartGraphicUserInterface(display, shoppingCart, addresses, errorMessage, printWriter);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
