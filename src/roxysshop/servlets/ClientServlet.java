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
import roxysshop.graphicuserinterface.ClientGraphicUserInterface;
import roxysshop.helper.Record;

public class ClientServlet extends HttpServlet {
	final public static long serialVersionUID = 10021002L;

	private ProductManager productManager;
	
	List<List<Record>> products;
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;
	private String currentCategory;
	private String currentSort;
	private String currentSearch;
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
			String sortBy = new String("name ASC");
			String display = session.getAttribute(Constants.DISPLAY).toString();
			shoppingCart = (List<List<String>>) session.getAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()));
			if (shoppingCart == null) {
				shoppingCart = new ArrayList<>();
			}
			labelsFilter = (List<String>) session.getAttribute(Constants.LABELS_FILTER);
			if (labelsFilter == null) {
				labelsFilter = new ArrayList<>();
			}
			
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
				if (parameter.equals(Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.SEARCH + ".x")) {
					currentSearch = request.getParameter(Constants.SEARCH.toLowerCase());
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
					System.out.println(labelsFilter);
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
				session.setAttribute(Constants.LABELS_FILTER, labelsFilter);
				
				if (parameter.equals(Constants.HOME.toLowerCase() + ".x")) {
					//TODO
				}
				if (parameter.equals(Constants.ACCOUNT.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.UPDATE_SERVLET_PAGE_CONTEXT);
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
				
				if (parameter.startsWith(Constants.PRODUCT.toLowerCase() + "_") 
						&&  parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
					session.setAttribute(Constants.PRODUCT, id);
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.PRODUCT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if (parameter.equals(Constants.SIGNOUT.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					currentCategory = null;
					previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
					currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
					currentPage = String.valueOf(1);
					products = null;
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.NOT_LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					session.invalidate();
					break;
				}
			}
			
			if (products == null || filterChange) {
				products = productManager.getCollection(sortBy, currentCategory, labelsFilter, currentSearch);
			}
			
			ClientGraphicUserInterface.displayClientGraphicUserInterface(display,
					products,
					shoppingCart,
					(currentRecordsPerPage != null && filterChange) ? Integer.parseInt(currentRecordsPerPage)
							: Constants.RECORDS_PER_PAGE_VALUES[0],
					(currentPage != null && filterChange && currentRecordsPerPage != null
							&& currentRecordsPerPage.equals(previousRecordsPerPage)) ? Integer.parseInt(currentPage)
									: 1,
									currentSort,
									currentCategory,
									labelsFilter,
									currentSearch,
									filterChange,
									printWriter);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
