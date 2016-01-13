package ro.pub.cs.aipi.roxysclothes.graphicuserinterface;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ro.pub.cs.aipi.roxysclothes.businesslogic.MenuManager;
import ro.pub.cs.aipi.roxysclothes.general.Constants;
import ro.pub.cs.aipi.roxysclothes.general.Utilities;
import ro.pub.cs.aipi.roxysclothes.helper.Record;

public class NotLoggedInClientGraphicUserInterface {

	private static MenuManager menuManager = new MenuManager();
	
	public NotLoggedInClientGraphicUserInterface() {
	}

	public static void displayNotLoggedInClientGraphicUserInterface(List<List<Record>> dishes, int currentRecordsPerPage, int currentPage, 
																	String currentDate, String currentCategory, List<String> labelsFilter,
																	PrintWriter printWriter) {
		StringBuilder content = new StringBuilder();
		content.append(
					"	<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
		content.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
		content.append("	<head>\n");
		content.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n");
		content.append("		<title>" + Constants.APPLICATION_NAME + "</title>\n");
		content.append("		<link rel=\"stylesheet\" type=\"text/css\" href=\"css/tema02.css\" />\n");
		content.append("		<link rel=\"icon\" type=\"image/x-icon\" href=\"./images/favicon.ico\" />\n");
		content.append("	</head>\n");

		content.append("	<body >\n");
		content.append("		<h2 style=\"text-align: center\">" + Constants.APPLICATION_NAME.toUpperCase() + "</h2>\n");
		content.append("		<form action=\"" + Constants.NOT_LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT + "\" method=\"post\" name=\"" + Constants.NOT_LOGGED_IN_CLIENT_FORM + "\">\n");
		content.append("			<p style=\"text-align:right\">\n");
		content.append("				<input type=\"image\" name=\"" + Constants.SIGNIN.toLowerCase() + "\" value=\"" + Constants.SIGNIN + "\" src=\"./images/user_interface/signin.png\" />\n");
		content.append("				<br/>\n");
		content.append("				<input type=\"image\" name=\"" + Constants.SIGNUP.toLowerCase() + "\" value=\"" + Constants.SIGNUP + "\" src=\"./images/user_interface/signup.png\" />\n");
		content.append("				<br/>\n");
		content.append("			</p>\n");
		content.append("			<h2 style=\"text-align: center\">" + Constants.CLIENT_SERVLET_PAGE_NAME + "</h2>\n");
		content.append("			<p style=\"text-align:right\">\n");
		content.append("				" + Constants.RECORDS_PER_PAGE + "<select name=\"" + Utilities.removeSpaces(Constants.RECORDS_PER_PAGE.toLowerCase().trim()) + "\" onchange=\"document." + Constants.NOT_LOGGED_IN_CLIENT_FORM + ".submit()\">\n");
		for (int recordsPerPageValue : Constants.RECORDS_PER_PAGE_VALUES) {
			content.append("				<option value=\"" + recordsPerPageValue + "\"" + ((recordsPerPageValue == currentRecordsPerPage) ? " SELECTED" : "") + ">" + recordsPerPageValue + "</option>\n");
		}
		content.append("				</select>\n");
		content.append("				" + Constants.PAGE + "<select name=\"" + Utilities.removeSpaces(Constants.PAGE.toLowerCase().trim()) + "\" onchange=\"document." + Constants.NOT_LOGGED_IN_CLIENT_FORM + ".submit()\">\n");
		for (int pageValue = 1; pageValue <= dishes.size() / currentRecordsPerPage + ((dishes.size() % currentRecordsPerPage) != 0 ? 1 : 0); pageValue++) {
			content.append("				<option value=\"" + pageValue + "\"" + ((pageValue == currentPage) ? " SELECTED" : "") + ">" + pageValue + "</option>\n");
		}
		content.append("				</select>\n");
		content.append("			</p>\n");
		content.append("			<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 100%; background-image: url(./images/user_interface/background.jpg); margin: 0px auto;\">\n");
		content.append("				<tbody>\n");
		content.append("					<tr>\n");
		content.append("						<td style=\"width: 20%; text-align: left; vertical-align: top;\">\n");
		content.append("							<div id=\"wrapperrelative\">\n");
		content.append("								<div id=\"wrappertop\"></div>\n");
		content.append("								<div id=\"wrappermiddle\">\n");
		content.append("									<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\">\n");
		content.append("										<tbody>\n");
		content.append("											<tr>\n");
		content.append("												<td>Date: </td>\n");
		content.append("												<td>\n");
		content.append("													<select name=\"" + Constants.CURRENT_MENU + "\" onchange=\"document." + Constants.NOT_LOGGED_IN_CLIENT_FORM + ".submit()\"" + "\" style=\"width: 100%\">\n");
		List<String> menuAttributes = new ArrayList<>();
		menuAttributes.add(Constants.MENU_ATTRIBUTE);
		List<List<String>> menus = menuManager.getCollection(menuAttributes);
		List<String> menuList = new ArrayList<>();
		menuList.add("all");
		for (List<String> menu : menus) {
			menuList.add(menu.get(0));
		}
		for (String menuAttribute : menuList) {
			content.append("													<option value=\"" + menuAttribute + "\"" + ((menuAttribute.equals(currentDate)) ? " SELECTED" : "") + ">" + menuAttribute + "</option>\n");
		}
		content.append("													</select>\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		content.append("												<td>Category: </td>\n");
		content.append("												<td>\n");
		content.append("													<select name=\"" + Constants.CURRENT_CATEGORY+ "\" onchange=\"document." + Constants.NOT_LOGGED_IN_CLIENT_FORM + ".submit()\"" + "\" style=\"width: 100%\">\n");
		List<String> categoryList = new ArrayList<>();
		categoryList.add("all");
		categoryList.add("aperitiv");
		categoryList.add("fel principal");
		categoryList.add("desert");
		for (String categoryAttribute : categoryList) {
			content.append("													<option value=\"" + categoryAttribute + "\"" + ((categoryAttribute.equals(currentCategory)) ? " SELECTED" : "") + ">" + categoryAttribute + "</option>\n");
		}
		content.append("													</select>\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		content.append("											<tr>\n");
		content.append("												<td>" + Constants.LABEL + "</td>\n");
		content.append("												<td>\n");
		content.append("													<input type=\"text\" name=\"" + Constants.LABEL.toLowerCase() + "\" size=\"5\"/>\n");
		content.append("												</td>\n");
		content.append("												<td>\n");
		content.append("												<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.LABEL + "\" value=\"" + Constants.INSERT_BUTTON_NAME + "\" src=\"./images/user_interface/insert.png\"/>\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		content.append("											<tr>\n");
		content.append("												<td colspan=\"3\">\n");
		content.append("													<table>\n");
		for (String labelFilter : labelsFilter) {
			content.append("													<tr>\n");
			content.append("														<td style=\"background: #ebebeb; text-align: left;\">" + labelFilter + "</td>\n");
			content.append("														<td>\n");
			content.append("															<input type=\"image\" name=\"" + Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + Constants.LABEL + "_" + labelFilter + "\" src=\"./images/user_interface/delete.png\" width=\"16\" height=\"16\" />\n");
			content.append("														</td>\n");
			content.append("													</tr>\n");
		}
		content.append("													</table>\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		content.append("										</tbody>\n");
		content.append("									</table>\n");
		content.append("								</div>\n");
		content.append("								<div id=\"wrapperbottom\"></div>\n");
		content.append("							</div>\n");
		content.append("						</td>\n");
		content.append("						<td>\n");
		int index = 0;
		for (List<Record> dish : dishes) {
			index++;
			if (index < ((currentPage - 1) * currentRecordsPerPage + 1)
					|| index > (currentPage * currentRecordsPerPage)) {
				continue;
			}
			content.append("						<div id=\"wrapperrelative\">\n");
			content.append("							<div id=\"wrappertop\"></div>\n");
			content.append("							<div id=\"wrappermiddle\">\n");
			content.append("								<table style=\"width: 80%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
			content.append("									<tbody>\n");
			content.append("										<tr>\n");
			content.append("											<td>&nbsp;</td>\n");
			content.append("											<td style=\"text-align: left;\">\n");
			for (Record field : dish) {	
				content.append("											<b>" + field.getAttribute() + "</b>: " + field.getValue() + "\n");
				content.append("											<br/>\n");
			}
			content.append("												<br/>\n");
			content.append("											</td>\n");
			content.append("										</tr>\n");
			content.append("									</tbody>\n");
			content.append("								</table>\n");
			content.append("							</div>\n");
			content.append("							<div id=\"wrapperbottom\"></div>\n");
			content.append("						</div>\n");
		}
			content.append("					</td>\n");
			content.append("				</tr>\n");
			content.append("			</tbody>\n");
			content.append("		</table>\n");
		content.append("		</form>");
		content.append("	</body>\n");
		content.append("</html>");
		printWriter.println(content.toString());
	}
}
