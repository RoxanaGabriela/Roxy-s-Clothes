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
import roxysshop.graphicuserinterface.NotLoggedInClientGraphicUserInterface;
import roxysshop.helper.Record;

public class NotLoggedInClientServlet extends HttpServlet {

	public final static long serialVersionUID = 20152015L;

	private ProductManager productManager;
	List<List<Record>> products;
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;
	private String currentSort;
	private String currentCategory;
	private String currentSearch;
	private String currentLowPrice;
	private String currentHighPrice;
	private List<String> labelsFilter;
	private List<List<String>> shoppingCart;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		productManager = new ProductManager();
		previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentSort = null;
		currentSearch = new String();
		currentPage = String.valueOf(1);
		products = null;
		currentCategory = null;
		currentLowPrice = new String();
		currentHighPrice = new String();
	}

	@Override
	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		response.setContentType("text/html");
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			boolean filterChange = false;
			String sortBy = new String("name ASC");

			shoppingCart = (List<List<String>>) session.getAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()));
			if (shoppingCart == null) {
				shoppingCart = new ArrayList<>();
			}
			labelsFilter = (List<String>) session.getAttribute(Constants.LABELS_FILTER);
			if (labelsFilter == null) {
				labelsFilter = new ArrayList<>();
			}
			
			Enumeration<String> parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String parameter = (String) parameters.nextElement();
				if (parameter.equals(Utilities.removeSpaces(Constants.RECORDS_PER_PAGE.toLowerCase().trim()))) {
					currentRecordsPerPage = request.getParameter(parameter);
				}
				if (parameter.equals(Utilities.removeSpaces(Constants.PAGE.toLowerCase().trim()))) {
					currentPage = request.getParameter(parameter);
				}
				if (parameter.equals(Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.SEARCH + ".x")) {
					currentSearch = request.getParameter(Constants.SEARCH.toLowerCase());
					filterChange = true;
				}
				if (parameter.equals(Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.PRICE + ".x")) {
					currentLowPrice = request.getParameter(Constants.PRICE.toLowerCase() + "_low");
					currentHighPrice = request.getParameter(Constants.PRICE.toLowerCase() + "_high");
					filterChange = true;
				}
				if (parameter.equals(Constants.CURRENT_SORT)) {
					currentSort = request.getParameter(parameter);
					if (currentSort.equals("Pret crescator")) sortBy = "price ASC";
					if (currentSort.equals("Pret descrescator")) sortBy = "price DESC";
					if (currentSort.equals("Data crescator")) sortBy = "date ASC";
					if (currentSort.equals("Data descrescator")) sortBy = "date DESC";
					
					filterChange = true;
				}
				
				if (parameter.equals(Constants.CURRENT_CATEGORY)) {
					currentCategory = request.getParameter(parameter);
					if (currentCategory.equals("-"))
						currentCategory = null;
					filterChange = true;
				}
				if (parameter.equals(Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.LABEL + ".x")) {
					String label = request.getParameter(Constants.LABEL.toLowerCase());
					if (!labelsFilter.contains(label)) {
						labelsFilter.add(label);
						filterChange = true;
					}
				}
				if (parameter.startsWith(Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + Constants.LABEL + "_")
						&& parameter.endsWith(".x")) {
					String label = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
					if (labelsFilter.contains(label)) {
						labelsFilter.remove(label);
						filterChange = true;
					}
				}
				
				session.setAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()), shoppingCart);
				session.setAttribute(Constants.DISPLAY, "");
				session.setAttribute(Constants.LABELS_FILTER, labelsFilter);

				if (parameter.startsWith(Constants.PRODUCT.toLowerCase() + "_") 
						&&  parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
					session.setAttribute(Constants.PRODUCT, id);
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.NOT_LOGGED_IN_PRODUCT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if (parameter.equals(Constants.SIGNIN.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					products = null;
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
					products = null;
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.REGISTER_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					break;
				}
				
				if (parameter.equals(Constants.SHOPPING_CART.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.NOT_LOGGED_IN_SHOPPING_CART_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
			}
			
			if (products == null || filterChange) {
				products = productManager.getCollection(sortBy, currentCategory, labelsFilter, currentSearch, new String(), currentLowPrice, currentHighPrice);
			}
			
			NotLoggedInClientGraphicUserInterface.displayNotLoggedInClientGraphicUserInterface(products,
					shoppingCart,
					currentLowPrice,
					currentHighPrice,
					(currentRecordsPerPage != null && filterChange) ? Integer.parseInt(currentRecordsPerPage)
							: Constants.RECORDS_PER_PAGE_VALUES[0],
					(currentPage != null && filterChange && currentRecordsPerPage != null
							&& currentRecordsPerPage.equals(previousRecordsPerPage)) ? Integer.parseInt(currentPage)
									: 1,
									currentSort,
									currentCategory,
									labelsFilter,
									currentSearch,
									printWriter);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
