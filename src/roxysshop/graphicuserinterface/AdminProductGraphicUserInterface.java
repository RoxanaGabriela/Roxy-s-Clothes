package roxysshop.graphicuserinterface;

import java.io.PrintWriter;
import java.util.List;

import roxysshop.businesslogic.ProductManager;
import roxysshop.businesslogic.SizeManager;
import roxysshop.general.Constants;
import roxysshop.general.Utilities;
import roxysshop.helper.Record;

public class AdminProductGraphicUserInterface {
	public static SizeManager sizeManager = new SizeManager();
	public static ProductManager productManager = new ProductManager();
	
	public AdminProductGraphicUserInterface() {
	}

	public static void displayAdminProductGraphicUserInterface(String display, List<Record> product, String currentSize, List<List<String>> shoppingCart, PrintWriter printWriter) {
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
		content.append("		<form action=\"" + Constants.ADMIN_PRODUCT_SERVLET_PAGE_CONTEXT + "\" method=\"post\" name=\"" + Constants.ADMIN_PRODUCT_FORM + "\">\n");
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
		content.append("					</tr>\n");
		content.append("				</tbody>\n");
		content.append("			</table>\n");
		content.append("			<p style=\"text-align:right\">\n");
		content.append("				" + Constants.WELCOME_MESSAGE + display + "\n");
		content.append("				<br/>\n");
		content.append("			</p>\n");
		content.append("			<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 100%; background-image: url(./images/user_interface/background.jpg); margin: 0px auto;\">\n");
		content.append("				<tbody>\n");
		content.append("					<tr>\n");
		content.append("						<td>\n");
		String currentIdentifier = product.get(0).getValue().toString();
			content.append("						<div id=\"wrapperrelative\">\n");
			content.append("							<div id=\"wrappertop\"></div>\n");
			content.append("							<div id=\"wrappermiddle\">\n");
			content.append("								<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
			content.append("									<tbody>\n");
			content.append("										<tr>\n");
			content.append("											<td>\n");
			content.append("												<input type=\"image\" name=\"" + Constants.PRODUCT + "_" + currentIdentifier + "\" value=\"" + Constants.PRODUCT + "\" src=\"" + product.get(7).getValue() + "\" height=\"629\" width=\"417\"/>\n");
			content.append("											</td>\n");
			content.append("											<td>\n");
			content.append("												<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
			content.append("													<tbody>\n");
			for (Record field : product) {
				if (field.getAttribute().equals("Picture")) continue;
				content.append("													<tr>\n");
				content.append("														<td>&nbsp;</td>\n");
				content.append("														<td style=\"text-align: left;\">" + field.getAttribute() + ": </td>\n");
				content.append("														<td>\n");
				content.append("															<input type=\"text\" name=\"" + field.getAttribute() + "\" id=\"url\" value=\"" + field.getValue() + "\" onclick=\"this.value = ''\">\n");
				content.append("														</td>\n");
				content.append("													</tr>\n");
			}				
			content.append("											<tr>\n");
			content.append("												<td colspan=\"2\">\n");
			content.append("													<input type=\"image\" src=\"./images/user_interface/update_button.png\" id=\"update\" name=\"" + Constants.UPDATE.toLowerCase() + "\" value=\"" + Constants.UPDATE + "\">\n");
			content.append("												</td>\n");
			content.append("											</tr>\n");
			content.append("													</tbody>\n");
			content.append("												</table>\n");
			content.append("											</td>\n");
			content.append("											<td>\n");
			content.append("												<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
			content.append("													<tbody>\n");
			content.append("														<tr>\n");
			content.append("															<td>\n");
			content.append("																<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + currentIdentifier + "\" value=\"" + Constants.INSERT_BUTTON_NAME + "\" src=\"./images/user_interface/add_to_shopping_cart.png\"/>\n");
			content.append("															</td>\n");
			content.append("														</tr>");
			content.append("													</tbody>\n");
			content.append("												</table>\n");
			content.append("											</td>\n");			
			content.append("										</tr>\n");
			content.append("									</tbody>\n");
			content.append("								</table>\n");
			content.append("							</div>\n");
			content.append("							<div id=\"wrapperbottom\"></div>\n");
			content.append("						</div>\n");
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
