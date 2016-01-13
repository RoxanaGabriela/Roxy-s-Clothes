package ro.pub.cs.aipi.roxysclothes.servlets;

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

import ro.pub.cs.aipi.roxysclothes.businesslogic.ProductManager;
import ro.pub.cs.aipi.roxysclothes.general.Constants;
import ro.pub.cs.aipi.roxysclothes.general.Utilities;
import ro.pub.cs.aipi.roxysclothes.graphicuserinterface.NotLoggedInClientGraphicUserInterface;
import ro.pub.cs.aipi.roxysclothes.helper.Record;

public class NotLoggedInClientServlet extends HttpServlet {

	public final static long serialVersionUID = 20152015L;

	private ProductManager dishManager;
	List<List<Record>> dishes;
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;
	private String currentDate;
	private String currentCategory;
	private List<String> labelsFilter;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dishManager = new ProductManager();
		previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentDate = null;
		currentPage = String.valueOf(1);
		dishes = null;
		currentCategory = null;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			boolean filterChange = false;

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
				if (parameter.equals(Constants.CURRENT_MENU)) {
					currentDate = request.getParameter(parameter);
					if (currentDate.equals("all"))
						currentDate = null;
					filterChange = true;
				}
				if (parameter.equals(Constants.CURRENT_CATEGORY)) {
					currentCategory = request.getParameter(parameter);
					if (currentCategory.equals("all"))
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

				if (parameter.equals(Constants.SIGNIN.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					dishes = null;
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
					dishes = null;
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.REGISTER_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					break;
				}
			}
			
			if (dishes == null || filterChange) {
				dishes = dishManager.getCollection(currentDate, currentCategory, labelsFilter);
			}
			
			NotLoggedInClientGraphicUserInterface.displayNotLoggedInClientGraphicUserInterface(dishes, 
					(currentRecordsPerPage != null && filterChange) ? Integer.parseInt(currentRecordsPerPage)
							: Constants.RECORDS_PER_PAGE_VALUES[0],
					(currentPage != null && filterChange && currentRecordsPerPage != null
							&& currentRecordsPerPage.equals(previousRecordsPerPage)) ? Integer.parseInt(currentPage)
									: 1,
									currentDate,
									currentCategory,
									labelsFilter,
									printWriter);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
