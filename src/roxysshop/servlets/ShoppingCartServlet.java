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

import roxysshop.general.Constants;
import roxysshop.general.Utilities;
import roxysshop.graphicuserinterface.ShoppingCartGraphicUserInterface;
import roxysshop.helper.Record;

public class ShoppingCartServlet  extends HttpServlet {
	final public static long serialVersionUID = 10021002L;
	
	private List<Record> shoppingCart;
	private String errorMessage;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		errorMessage = new String();
	}
	
	@Override
	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			shoppingCart = (List<Record>) session.getAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()));
			System.out.println(shoppingCart);
			if (shoppingCart == null) {
				shoppingCart = new ArrayList<>();
			}
						
			boolean filterChange = false;
			Enumeration<String> parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String parameter = (String) parameters.nextElement();
				
				if (parameter.startsWith(Utilities.removeSpaces(Constants.DELETE_BUTTON_NAME)) && parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1,
							parameter.indexOf(".x"));
					for (Record shoppingCartRecord : shoppingCart) {
						if (shoppingCartRecord.getAttribute().equals(id)) {
							shoppingCart.remove(shoppingCartRecord);
							break;
						}
					}
				}
				
				if (parameter.startsWith(Utilities.removeSpaces(Constants.UPDATE_BUTTON_NAME)) && parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1,
							parameter.indexOf(".x"));
					String value = request.getParameter("order_input_" + id);
					for (Record shoppingCartRecord : shoppingCart) {
						if (shoppingCartRecord.getAttribute().equals(id)) {
							shoppingCartRecord.setValue(value);
							break;
						}
					}

				}
				
				/*if (parameter.equals(Utilities.removeSpaces(Constants.COMPLETE_COMMAND.toLowerCase()) + ".x")) {
					List<String> invoice = new ArrayList<>();
					invoice.add(LocalDate.now().toString());
					invoice.add(String.valueOf(productManager.getIdentifier(display)));
					invoice.add(address);
						long invoiceId = invoiceManager.create(invoice);
						for (Record shoppingCartRecord : shoppingCart) {
							long dishId = Long.parseLong(shoppingCartRecord.getAttribute());
							int quantity = Integer.parseInt(shoppingCartRecord.getValue().toString());
							int stockpile = dishManager.getStockpile(dishId);
							if (quantity <= stockpile) {
								List<String> dishAttributes = new ArrayList<>();
								dishAttributes.add("stockpile");
								List<String> dishValues = new ArrayList<>();
								dishValues.add(String.valueOf(stockpile - quantity));
								dishManager.update(dishAttributes, dishValues,
										dishId);
								List<String> invoiceDetails = new ArrayList<>();
								invoiceDetails.add(String.valueOf(invoiceId));
								invoiceDetails.add(String.valueOf(dishId));
								invoiceDetails.add(String.valueOf(quantity));
								invoiceDetailsManager.create(invoiceDetails);
								shoppingCart = new ArrayList<>();
							} else {
								errorMessage = Constants.INVALID_COMMAND_ERROR1 + dishId + Constants.INVALID_COMMAND_ERROR2;
							}
						}
					} else {
							errorMessage = Constants.INVALID_COMMAND_ERROR3;
					}
				}

				if (parameter.equals(Utilities.removeSpaces(Constants.CANCEL_COMMAND.toLowerCase()) + ".x")) {
					shoppingCart = new ArrayList<>();
				}*/
				
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
			

			ShoppingCartGraphicUserInterface.displayShoppingCartGraphicUserInterface(shoppingCart, errorMessage,
																					filterChange, printWriter);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
