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

import roxysshop.businesslogic.ProductManager;
import roxysshop.general.Constants;
import roxysshop.general.Utilities;
import roxysshop.graphicuserinterface.ProductGraphicUserInterface;
import roxysshop.helper.Record;

public class ProductServlet extends HttpServlet {
	final public static long serialVersionUID = 10021002L;
	
	private ProductManager productManager;
	
	List<Record> product;	
	private String currentSize;
	private List<List<String>> shoppingCart;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		productManager = new ProductManager();
		
		currentSize = null;
	}

	@Override
	public void destroy() {
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
			
			shoppingCart = (List<List<String>>) session.getAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()));
			if (shoppingCart == null) {
				shoppingCart = new ArrayList<>();
			}
			
			String identifier = (String) session.getAttribute(Constants.PRODUCT);
			product = productManager.getDetails(Long.parseLong(identifier));
			if (product == null) {
				product = new ArrayList<>();
			}
			
			Enumeration<String> parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String parameter = (String) parameters.nextElement();
			
				if (parameter.equals(Constants.CURRENT_SIZE)) {
					currentSize = request.getParameter(parameter);
				}
				
				if (parameter
						.startsWith(Constants.INSERT_BUTTON_NAME.toLowerCase() + "_"
								+ Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()) + "_")
						&& parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1,
							parameter.indexOf(".x"));
					String quantity = request.getParameter(Constants.COPIES.toLowerCase() + "_"
							+ Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()) + "_" + id);
					boolean found = false;
					for (List<String> shoppingCartRecord : shoppingCart) {
						if (shoppingCartRecord.get(0).equals(id) && shoppingCartRecord.get(2).equals(currentSize)) {
							found = true;
							if (Integer.parseInt(quantity) == 0) {
								shoppingCart.remove(shoppingCartRecord);
							} else {
								shoppingCartRecord.set(1, quantity);
							}
							break;
						}
					}
					if (!found && Integer.parseInt(quantity) != 0) {
						List<String> record = new ArrayList<>();
						record.add(id);
						record.add(quantity);
						record.add(currentSize);
						shoppingCart.add(record);
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
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.ADMIN_UPDATE_SERVLET_PAGE_CONTEXT);
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
					currentSize = null;
					product = null;
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.NOT_LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					session.invalidate();
					break;
				}
			}
			ProductGraphicUserInterface.displayProductGraphicUserInterface(display, product, currentSize, shoppingCart, printWriter);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
