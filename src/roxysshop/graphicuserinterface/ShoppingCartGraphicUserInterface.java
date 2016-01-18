package roxysshop.graphicuserinterface;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import roxysshop.businesslogic.ProductManager;
import roxysshop.businesslogic.SizeManager;
import roxysshop.general.Constants;
import roxysshop.general.Utilities;
import roxysshop.helper.Record;

public class ShoppingCartGraphicUserInterface {
	public static SizeManager sizeManager = new SizeManager();
	public static ProductManager productManager = new ProductManager();
	
	public ShoppingCartGraphicUserInterface() {
	}

	public static void displayShoppingCartGraphicUserInterface(List<Record> shoppingCart, String errorMessage, 
																boolean filterChange, PrintWriter printWriter) {
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
		content.append("		<form action=\"" + Constants.SHOPPING_CART_SERVLET_PAGE_CONTEXT + "\" method=\"post\" name=\"" + Constants.SHOPPING_CART_FORM + "\">\n");
		content.append("			<table>\n");
		content.append("				<tbody>\n");
		content.append("					<tr>\n");
		content.append("						<td>\n");
		content.append("							<input type=\"image\" name=\"" + Constants.HOME.toLowerCase() + "\" value=\"" + Constants.HOME + "\" src=\"./images/user_interface/home.png\" />\n");
		content.append("						</td>\n");
		content.append("						<td>\n");
		content.append("							<input type=\"image\" name=\"" + Constants.ACCOUNT.toLowerCase() + "\" value=\"" + Constants.ACCOUNT + "\" src=\"./images/user_interface/account.png\" />\n");
		content.append("						</td>\n");
		content.append("						<td>\n");
		content.append("							<input type=\"image\" name=\"" + Constants.SIGNOUT.toLowerCase() + "\" value=\"" + Constants.SIGNOUT + "\" src=\"./images/user_interface/signout.png\" />\n");
		content.append("						</td>\n");
		content.append("						<td>\n");
		content.append("							<input type=\"image\" name=\"" + Constants.SHOPPING_CART.toLowerCase() + "\" value=\"" + Constants.SHOPPING_CART + "\" src=\"./images/user_interface/signup.png\" />\n");
		content.append("						</td>\n");
		content.append("					</tr>\n");
		content.append("				</tbody>\n");
		content.append("			</table>\n");
		content.append("			<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 100%; background-image: url(./images/user_interface/background.jpg); margin: 0px auto;\">\n");
		content.append("				<tbody>\n");
		content.append("					<tr>\n");
		content.append("						<td style=\"width: 20%; text-align: left; vertical-align: top;\">\n");
		content.append("							<div id=\"wrapperrelative\">\n");
		content.append("								<div id=\"wrappertop\"></div>\n");
		content.append("								<div id=\"wrappermiddle\">\n");
		content.append("									<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
		content.append("										<tbody>\n");
		content.append("											<tr>\n");
		content.append("												<td>\n");
		content.append("													<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
		content.append("														<tbody>\n");
		content.append("															<tr>\n");
		content.append("																<td style=\"text-align: center\">" + Constants.SHOPPING_CART + " (" + shoppingCart.size() + ") </td>\n");
		content.append("															</tr>\n");
		if (!shoppingCart.isEmpty()) {
			double shoppingCartValue = 0.0;
			content.append("														<tr>\n");
			content.append("															<td>\n");
			content.append("																<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 100%; background: #ffffff;\">\n");
			content.append("																	<tbody>\n");
			for (Record shoppingCartContent : shoppingCart) {
				long identifier = Long.parseLong(shoppingCartContent.getAttribute());
				List<Record> details = productManager.getDetails(identifier); 
				String name = details.get(1).getValue().toString();
				double value = Double.parseDouble(details.get(2).getValue().toString());
				value *= Integer.parseInt(shoppingCartContent.getValue().toString());
				content.append("																	<tr style=\"background: #ebebeb;\">\n");				
				content.append("																		<td>" + name + " x </td>\n");
				content.append("																		<td><input type=\"text\" name=\"order_input" + "_" + identifier + "\" id=\"url\" value=\"" + shoppingCartContent.getValue() + "\" onclick=\"this.value = ''\"></td>\n");
				content.append("																		<td>" + "= " + new DecimalFormat("#.##").format(value) + "</td>");
				content.append("																		<td><input type=\"image\" name=\"" + Utilities.removeSpaces(Constants.DELETE_BUTTON_NAME) + "_" + identifier + "\" value=\"" + Constants.DELETE_BUTTON_NAME + "\" src=\"./images/user_interface/remove_from_shopping_cart.png\" /></td>\n");
				content.append("																		<td><input type=\"image\" name=\"" + Utilities.removeSpaces(Constants.UPDATE_BUTTON_NAME) + "_" + identifier + "\" value=\"" + Constants.UPDATE_BUTTON_NAME + "\" src=\"./images/user_interface/update.png\" /></td>\n");
				content.append("																	</tr>\n");
				shoppingCartValue += value;
			}
			content.append("																		<tr style=\"background: #ebebeb;\"><td></td></tr>\n");
			content.append("																		<tr style=\"background: #ebebeb;\">\n");
			content.append("																			<td name=\"" + Utilities.removeSpaces(Constants.ORDER_TOTAL.toLowerCase()) + "\" >" + Constants.ORDER_TOTAL + "<b>" + new DecimalFormat("#.##").format(shoppingCartValue) + "</b></td>\n");
			content.append("																		</tr>\n");
			content.append("																	</tbody>\n");
			content.append("																</table>\n");
			content.append("															</td>\n");
			content.append("														</tr>\n");
			content.append("														<tr>\n");
			content.append("															<td style=\"text-align: center\">\n");
			content.append("																<input type=\"image\" name=\"" + Utilities.removeSpaces(Constants.CANCEL_COMMAND.toLowerCase()) + "\" value=\"" + Constants.CANCEL_COMMAND + "\" src=\"./images/user_interface/remove_from_shopping_cart.png\" />\n");
			content.append("																&nbsp;&nbsp;\n");
			content.append("																<input type=\"image\" name=\"" + Utilities.removeSpaces(Constants.COMPLETE_COMMAND.toLowerCase()) + "\" value=\"" + Constants.COMPLETE_COMMAND + "\" src=\"./images/user_interface/shopping_cart_accept.png\" />\n");
			content.append("															</td>\n");
			content.append("														</tr>\n");
			/*List<List<String>> addresses = userManager.getAddresses(display);
			content.append("														<tr>\n");
			content.append("															<td colspan=\"3\">\n");
			content.append("																<table>\n");
			for (List<String> address : addresses) {
				content.append("																<tr>\n");
				content.append("																	<td style=\"background: #ebebeb; text-align: left;\">" + address.get(1) + "</td>\n");
				content.append("																	<td>\n");
				content.append("																		<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "_" + address.get(0) + "\" src=\"./images/user_interface/insert.png\" width=\"16\" height=\"16\" />\n");
				content.append("																	</td>\n");
				content.append("																</tr>\n");
			}
			content.append("																	<tr>\n");
			content.append("																		<td>\n");
			content.append("																			<input type=\"text\" name=\"" + Constants.ADDRESS.toLowerCase() + "\" size=\"5\"/>\n");
			content.append("																		</td>\n");
			content.append("																		<td>\n");
			content.append("																			<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "\" value=\"" + Constants.INSERT_BUTTON_NAME + "\" src=\"./images/user_interface/insert.png\"/>\n");
			content.append("																		</td>\n");
			content.append("																	</tr>\n");
			content.append("																</table>\n");
			content.append("															</td>\n");
			content.append("														</tr>\n");*/
			if (errorMessage != null && !errorMessage.isEmpty()) {
				content.append("													<tr>\n");
				content.append("														<td style=\"color: #ff0000;\">" + errorMessage +  "</td>\n");
				content.append("													</tr>\n");
			}
		} else {
			content.append("														<tr>\n");
			content.append("															<td style=\"text-align: center;\">" + Constants.EMPTY_CART + "</td>\n");
			content.append("														</tr>\n");
		}
		content.append("														</tbody>\n");
		content.append("													</table>\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		content.append("										</tbody>\n");
		content.append("									</table>\n");
		content.append("								</div>\n");
		content.append("								<div id=\"wrapperbottom\"></div>\n");
		content.append("							</div>\n");
		content.append("						</td>\n");
		content.append("					</tr>\n");
		content.append("				</tbody>\n");
		content.append("			</table>\n");
		content.append("		</form>");
		content.append("	</body>\n");
		content.append("</html>");
		printWriter.println(content.toString());
	}

}
