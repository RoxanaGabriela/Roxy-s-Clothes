package ro.pub.cs.aipi.roxysclothes.graphicuserinterface;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ro.pub.cs.aipi.roxysclothes.businesslogic.ProductManager;
import ro.pub.cs.aipi.roxysclothes.businesslogic.UserManager;
import ro.pub.cs.aipi.roxysclothes.general.Constants;
import ro.pub.cs.aipi.roxysclothes.general.Utilities;
import ro.pub.cs.aipi.roxysclothes.helper.Record;

public class ClientGraphicUserInterface {
	
	private static ProductManager productManager = new ProductManager();
	private static UserManager userManager = new UserManager();

	public ClientGraphicUserInterface() {
	}

	public static void displayClientGraphicUserInterface(String display, List<List<Record>> products,
			List<Record> shoppingCart, int currentRecordsPerPage, int currentPage, String currentSort,
			String currentCategory, List<String> labelsFilter,String errorMessage, boolean filterChange,
			PrintWriter printWriter) {
		StringBuilder content = new StringBuilder();
		content.append(
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
		content.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
		content.append("	<head>\n");
		content.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n");
		content.append("		<title>" + Constants.APPLICATION_NAME + "</title>\n");
		content.append("		<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />\n");
		content.append("		<link rel=\"icon\" type=\"image/x-icon\" href=\"./images/favicon.ico\" />\n");
		content.append("	</head>\n");

		content.append("	<body>\n");
		content.append("		<h2 style=\"text-align: center\">" + Constants.APPLICATION_NAME.toUpperCase() + "</h2>\n");
		content.append("		<form action=\"" + Constants.CLIENT_SERVLET_PAGE_CONTEXT + "\" method=\"post\" name=\"" + Constants.CLIENT_FORM + "\">\n");
		content.append("			<p style=\"text-align:right\">");
		content.append("				" + Constants.WELCOME_MESSAGE + display + "\n");
		content.append("				<br/>\n");
		content.append("				<input type=\"image\" name=\"" + Constants.ACCOUNT.toLowerCase() + "\" value=\"" + Constants.ACCOUNT + "\" src=\"./images/user_interface/account.png\" />\n");
		content.append("				<br/>\n");
		content.append("				<input type=\"image\" name=\"" + Constants.SIGNOUT.toLowerCase() + "\" value=\"" + Constants.SIGNOUT + "\" src=\"./images/user_interface/signout.png\" />\n");
		content.append("				<br/>\n");
		content.append("			</p>\n");
		content.append("			<table style=\"width: 50%; text-align:right\">");
		content.append("				<tbody>");
		content.append("					<tr>");
		content.append("						<td>");
		content.append("							<input type=\"image\" name=\"" + Constants.HOME.toLowerCase() + "\" value=\"" + Constants.HOME + "\" src=\"./images/user_interface/home.png\" />\n");
		content.append("						</td>");
		
		if (display == null || display.isEmpty()) {
			content.append("						<td>");
			content.append("							<input type=\"image\" name=\"" + Constants.SIGNIN.toLowerCase() + "\" value=\"" + Constants.SIGNIN + "\" src=\"./images/user_interface/signin.png\" />\n");
			content.append("						</td>");
			content.append("						<td>");
			content.append("							<input type=\"image\" name=\"" + Constants.SIGNUP.toLowerCase() + "\" value=\"" + Constants.SIGNUP + "\" src=\"./images/user_interface/signup.png\" />\n");
			content.append("						</td>");
		} else {
			content.append("						<td>");
			content.append("							<input type=\"image\" name=\"" + Constants.ACCOUNT.toLowerCase() + "\" value=\"" + Constants.ACCOUNT + "\" src=\"./images/user_interface/account.png\" />\n");
			content.append("						</td>");
			content.append("						<td>");
			content.append("							<input type=\"image\" name=\"" + Constants.SIGNOUT.toLowerCase() + "\" value=\"" + Constants.SIGNOUT + "\" src=\"./images/user_interface/signout.png\" />\n");
			content.append("						</td>");
		}

		content.append("							<td>\n");
		content.append("								<input type=\"text\" name=\"" + Constants.SEARCH.toLowerCase() + "\" size=\"5\"/>\n");
		content.append("							</td>\n");
		content.append("							<td>\n");
		content.append("								<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.SEARCH + "\" value=\"" + Constants.INSERT_BUTTON_NAME + "\" src=\"./images/user_interface/insert.png\"/>\n");
		content.append("							</td>\n");

		content.append("					</tr>");
		content.append("				</tbody>");
		content.append("			</table>");
		content.append("			<p style=\"text-align:right\">\n");
		content.append("				" + Constants.RECORDS_PER_PAGE + "<select name=\"" + Utilities.removeSpaces(Constants.RECORDS_PER_PAGE.toLowerCase().trim()) + "\" onchange=\"document." + Constants.CLIENT_FORM + ".submit()\">\n");
		for (int recordsPerPageValue : Constants.RECORDS_PER_PAGE_VALUES) {
			content.append("				<option value=\"" + recordsPerPageValue + "\"" + ((recordsPerPageValue == currentRecordsPerPage) ? " SELECTED" : "") + ">" + recordsPerPageValue + "</option>\n");
		}
		content.append("				</select>\n");
		content.append("				" + Constants.PAGE + "<select name=\"" + Utilities.removeSpaces(Constants.PAGE.toLowerCase().trim()) + "\" onchange=\"document." + Constants.CLIENT_FORM + ".submit()\">\n");
		for (int pageValue = 1; pageValue <= products.size() / currentRecordsPerPage + ((products.size() % currentRecordsPerPage) != 0 ? 1 : 0); pageValue++) {
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
		content.append("												<td>" + Constants.CATEGORY + "</td>\n");
		content.append("												<td>\n");		
		content.append("													<select name=\"" + Constants.CURRENT_CATEGORY+ "\" onchange=\"document." + Constants.CLIENT_FORM + ".submit()\"" + "\" style=\"width: 100%\">\n");
		List<String> categoryList = new ArrayList<>();
		categoryList.add("-");
		categoryList.add("Pantaloni");
		categoryList.add("Bluze");
		categoryList.add("Tricouri");
		categoryList.add("Fuste");
		categoryList.add("Rochii");
		categoryList.add("Jachete");
		for (String categoryAttribute : categoryList) {
			content.append("													<option value=\"" + categoryAttribute + "\"" + ((categoryAttribute.equals(currentCategory)) ? " SELECTED" : "") + ">" + categoryAttribute + "</option>\n");
		}
		content.append("													</select>\n");		
		content.append("												</td>\n");
		content.append("											</tr>\n");

		content.append("											<tr>\n");
		content.append("												<td>" + Constants.SORT + "</td>\n");
		content.append("												<td>\n");		
		content.append("													<select name=\"" + Constants.CURRENT_SORT+ "\" onchange=\"document." + Constants.CLIENT_FORM + ".submit()\"" + "\" style=\"width: 100%\">\n");
		List<String> sortList = new ArrayList<>();
		categoryList.add("Implicit");
		categoryList.add("Pret crescator");
		categoryList.add("Pret descrescator");
		categoryList.add("Data crescator");
		categoryList.add("Data descrescator");
		for (String sortAttribute : sortList) {
			content.append("													<option value=\"" + sortAttribute + "\"" + ((sortAttribute.equals(currentSort)) ? " SELECTED" : "") + ">" + sortAttribute + "</option>\n");
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
		for (List<Record> product : products) {
			index++;
			if (index < ((currentPage - 1) * currentRecordsPerPage + 1)
					|| index > (currentPage * currentRecordsPerPage)) {
				continue;
			}
			content.append("						<div id=\"wrapperrelative\">\n");
			content.append("							<div id=\"wrappertop\"></div>\n");
			content.append("							<div id=\"wrappermiddle\">\n");
			content.append("								<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
			content.append("									<tbody>\n");
			content.append("										<tr>\n");
			content.append("											<td>\n");
			content.append("												<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
			content.append("													<tbody>\n");
			content.append("														<tr>\n");
			content.append("															<td>&nbsp;</td>\n");
			content.append("															<td style=\"text-align: left;\">\n");
			String currentIdentifier = new String();
			for (Record field : product) {
				if (field.getAttribute().equals("Id")) currentIdentifier = field.getValue().toString();
				content.append("															<b>" + field.getAttribute() + "</b>: " + field.getValue() + "\n");
				content.append("															<br/>\n");
			}
			content.append("																<br/>\n");
			content.append("															</td>\n");
			content.append("														</tr>\n");
			content.append("													</tbody>\n");
			content.append("												</table>\n");
			content.append("											</td>\n");
			content.append("											<td>\n");
			content.append("												<input type=\"text\" name=\"" + Constants.COPIES.toLowerCase() + "_" + Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()) + "_" + currentIdentifier + "\" size=\"3\"/>\n");
			content.append("												<br/>\n");
			content.append("												<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()) + "_" + currentIdentifier + "\" value=\"" + Constants.INSERT_BUTTON_NAME + "\" src=\"./images/user_interface/add_to_shopping_cart.png\"/>\n");
			content.append("											</td>\n");
			content.append("										</tr>\n");
			content.append("									</tbody>\n");
			content.append("								</table>\n");
			content.append("							</div>\n");
			content.append("							<div id=\"wrapperbottom\"></div>\n");
			content.append("						</div>\n");
		}
		content.append("						</td>\n");
		content.append("						<td style=\"width: 20%; text-align: left; vertical-align: top;\">\n");
		content.append("							<div id=\"wrapperrelative\">\n");
			content.append("							<div id=\"wrappertop\"></div>\n");
			content.append("							<div id=\"wrappermiddle\">\n");
			content.append("								<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
			content.append("									<tbody>\n");
			content.append("										<tr>\n");
			content.append("											<td>\n");
			content.append("												<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
			content.append("													<tbody>\n");
			content.append("														<tr>\n");
			content.append("															<td style=\"text-align: center\">" + Constants.SHOPPING_CART + " (" + shoppingCart.size() + ") </td>\n");
			content.append("														</tr>\n");
			if (!shoppingCart.isEmpty()) {
				double shoppingCartValue = 0.0;
				content.append("													<tr>\n");
				content.append("														<td>\n");
				content.append("															<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 100%; background: #ffffff;\">\n");
				content.append("																<tbody>\n");
				for (Record shoppingCartContent : shoppingCart) {
					long identifier = Long.parseLong(shoppingCartContent.getAttribute());
					Record record = productManager.getInformation(identifier); 
					String name = record.getAttribute();
					double value = Double.parseDouble(record.getValue().toString());
					value *= Integer.parseInt(shoppingCartContent.getValue().toString());
					content.append("																<tr style=\"background: #ebebeb;\">\n");				
					content.append("																	<td>" + name + " x </td>\n");
					content.append("																	<td><input type=\"text\" name=\"order_input" + "_" + identifier + "\" id=\"url\" value=\"" + shoppingCartContent.getValue() + "\" onclick=\"this.value = ''\"></td>\n");
					content.append("																	<td>" + "= " + new DecimalFormat("#.##").format(value) + "</td>");
					content.append("																	<td><input type=\"image\" name=\"" + Utilities.removeSpaces(Constants.DELETE_BUTTON_NAME) + "_" + identifier + "\" value=\"" + Constants.DELETE_BUTTON_NAME + "\" src=\"./images/user_interface/remove_from_shopping_cart.png\" /></td>\n");
					content.append("																	<td><input type=\"image\" name=\"" + Utilities.removeSpaces(Constants.UPDATE_BUTTON_NAME) + "_" + identifier + "\" value=\"" + Constants.UPDATE_BUTTON_NAME + "\" src=\"./images/user_interface/update.png\" /></td>\n");
					content.append("																</tr>\n");
					shoppingCartValue += value;
				}
				content.append("																	<tr style=\"background: #ebebeb;\"><td></td></tr>\n");
				content.append("																	<tr style=\"background: #ebebeb;\">\n");
				content.append("																		<td name=\"" + Utilities.removeSpaces(Constants.ORDER_TOTAL.toLowerCase()) + "\" >" + Constants.ORDER_TOTAL + "<b>" + new DecimalFormat("#.##").format(shoppingCartValue) + "</b></td>\n");
				content.append("																	</tr>\n");
				content.append("																</tbody>\n");
				content.append("															</table>\n");
				content.append("														</td>\n");
				content.append("													</tr>\n");
				content.append("													<tr>\n");
				content.append("														<td style=\"text-align: center\">\n");
				content.append("															<input type=\"image\" name=\"" + Utilities.removeSpaces(Constants.CANCEL_COMMAND.toLowerCase()) + "\" value=\"" + Constants.CANCEL_COMMAND + "\" src=\"./images/user_interface/remove_from_shopping_cart.png\" />\n");
				content.append("															&nbsp;&nbsp;\n");
				content.append("															<input type=\"image\" name=\"" + Utilities.removeSpaces(Constants.COMPLETE_COMMAND.toLowerCase()) + "\" value=\"" + Constants.COMPLETE_COMMAND + "\" src=\"./images/user_interface/shopping_cart_accept.png\" />\n");
				content.append("														</td>\n");
				content.append("													</tr>\n");
				List<List<String>> addresses = userManager.getDisplayAddresses(display);
				content.append("													<tr>\n");
				content.append("														<td colspan=\"3\">\n");
				content.append("															<table>\n");
				for (List<String> address : addresses) {
					content.append("															<tr>\n");
					content.append("																<td style=\"background: #ebebeb; text-align: left;\">" + address.get(1) + "</td>\n");
					content.append("																<td>\n");
					content.append("																	<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "_" + address.get(0) + "\" src=\"./images/user_interface/insert.png\" width=\"16\" height=\"16\" />\n");
					content.append("																</td>\n");
					content.append("															</tr>\n");
				}
				content.append("																<tr>\n");
				content.append("																	<td>\n");
				content.append("																		<input type=\"text\" name=\"" + Constants.ADDRESS.toLowerCase() + "\" size=\"5\"/>\n");
				content.append("																	</td>\n");
				content.append("																	<td>\n");
				content.append("																		<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "\" value=\"" + Constants.INSERT_BUTTON_NAME + "\" src=\"./images/user_interface/insert.png\"/>\n");
				content.append("																	</td>\n");
				content.append("																</tr>\n");
				content.append("															</table>\n");
				content.append("														</td>\n");
				content.append("													</tr>\n");
				if (errorMessage != null && !errorMessage.isEmpty()) {
					content.append("												<tr>\n");
					content.append("													<td style=\"color: #ff0000;\">" + errorMessage +  "</td>\n");
					content.append("												</tr>\n");
				}
			} else {
				content.append("													<tr>\n");
				content.append("														<td style=\"text-align: center;\">" + Constants.EMPTY_CART + "</td>\n");
				content.append("													</tr>\n");
			}
			content.append("													</tbody>\n");
			content.append("												</table>\n");
			content.append("											</td>\n");
			content.append("										</tr>\n");
			content.append("									</tbody>\n");
			content.append("								</table>\n");
			content.append("							</div>\n");
			content.append("							<div id=\"wrapperbottom\"></div>\n");
			content.append("						</div>\n");
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
