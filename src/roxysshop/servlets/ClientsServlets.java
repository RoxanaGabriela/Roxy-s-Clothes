package roxysshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import roxysshop.businesslogic.UserManager;
import roxysshop.general.Constants;
import roxysshop.general.Utilities;
import roxysshop.graphicuserinterface.ClientsGraphicUserInterface;

public class ClientsServlets extends HttpServlet {
final public static long serialVersionUID = 10021002L;
	
	UserManager userManager;
	
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		userManager = new UserManager();
		
		previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentPage = String.valueOf(1);
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
				
				if (parameter.equals(Constants.HOME.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.ADMINISTRATOR_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if (parameter.startsWith(Constants.DELETE_BUTTON_NAME.toLowerCase() + "_")
						&& parameter.endsWith(".x")) {
					String identifier = parameter.substring(parameter.lastIndexOf("_") + 1, 
							parameter.indexOf(".x"));
					userManager.updateNotified(identifier);
				}
				
				if (parameter.equals(Constants.ACCOUNT.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.UPDATE_SERVLET_PAGE_CONTEXT);
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
				
				if (parameter.equals(Constants.CLIENTS.toLowerCase() + ".x")) {
					//TODO
				}
				
				if (parameter.equals(Constants.PRODUCTS.toLowerCase() + ".x")) {
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.PRODUCTS_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
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
			}
			
			ClientsGraphicUserInterface.displayClientsGraphicUserInterface(display,
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
