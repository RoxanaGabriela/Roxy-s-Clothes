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
import roxysshop.helper.Record;
import roxysshop.graphicuserinterface.ProductsGraphicUserInterface;

public class ProductsServlet extends HttpServlet {
final public static long serialVersionUID = 10021002L;
	
	ProductManager productManager;
	
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;
	List<Record> product;
	List<Record> fabrics;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		productManager = new ProductManager();
		
		previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentPage = String.valueOf(1);
		fabrics = new ArrayList<>();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if (session == null || session.getAttribute(Constants.DISPLAY) == null) {
			return;
		}
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			String display = session.getAttribute(Constants.DISPLAY).toString();
			
			product = new ArrayList<>();
			Record name = new Record("Name", "");
			Record price = new Record("Price", "");
			Record producer = new Record("Producer", "");
			Record color = new Record("Color", "");
			Record description = new Record("Description", "");
			Record category = new Record("Category", "");
			Record picture = new Record("Picture", "");
			
			product.add(name);
			product.add(price);
			product.add(producer);
			product.add(color);
			product.add(description);
			product.add(category);
			product.add(picture);
			
			boolean filterChange = false;
			Enumeration<String> parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String parameter = (String) parameters.nextElement();
				if (parameter.equals(Utilities.removeSpaces(Constants.RECORDS_PER_PAGE.toLowerCase().trim()))) {
					currentRecordsPerPage = request.getParameter(parameter);
				}
				if (parameter.equals(Utilities.removeSpaces(Constants.PAGE.toLowerCase().trim()))) {
					currentPage = request.getParameter(parameter);
				}
				
				for (Record field : product) {
					if (parameter.equals(field.getAttribute())) {
						field.setValue(request.getParameter(parameter));
					}
				}
				
				if (parameter.startsWith(Constants.PRODUCT.toLowerCase() + "_") 
						&&  parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
					session.setAttribute(Constants.PRODUCT, id);
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.ADMIN_PRODUCT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if (parameter.equals(Constants.HOME.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.ADMINISTRATOR_SERVLET_PAGE_CONTEXT);
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
					//TODO
				}
				
				if (parameter.equals(Constants.SIGNOUT.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
					currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
					currentPage = String.valueOf(1);
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.NOT_LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					session.invalidate();
					break;
				}
				
				if (parameter.equals(Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.FABRIC_NAME + ".x")) {
					String fabric_name = request.getParameter(Constants.FABRIC_NAME.toLowerCase());
					String fabric_value = request.getParameter(Constants.FABRIC_VALUE.toLowerCase());
					fabrics.add(new Record(fabric_name, fabric_value));
				}
				if (parameter.startsWith(Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + Constants.FABRIC_NAME + "_")
						&& parameter.endsWith(".x")) {
					String fabric_name = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
					int found = -1;
					for (int index = 0; index < fabrics.size(); index++) {
						if (fabrics.get(index).getAttribute().equals(fabric_name)) {
							found = index;
							break;
						}
					}
					if (found != -1) {
						fabrics.remove(found);
					}
				}
				
				if (parameter.equals(Constants.UPDATE.toLowerCase() + ".x")) {
					productManager.addProduct(product, fabrics);
				}
			}
			
			ProductsGraphicUserInterface.displayProductsGraphicUserInterface(display,
					product,
					fabrics,
					(currentRecordsPerPage != null && filterChange) ? Integer.parseInt(currentRecordsPerPage)
							: Constants.RECORDS_PER_PAGE_VALUES[0],
					(currentPage != null && filterChange && currentRecordsPerPage != null
							&& currentRecordsPerPage.equals(previousRecordsPerPage)) ? Integer.parseInt(currentPage)
									: 1,
									printWriter);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
