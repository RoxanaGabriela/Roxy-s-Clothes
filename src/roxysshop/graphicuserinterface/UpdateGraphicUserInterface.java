package roxysshop.graphicuserinterface;

import java.io.PrintWriter;
import java.util.List;

import roxysshop.businesslogic.AddressManager;
import roxysshop.businesslogic.InvoiceManager;
import roxysshop.businesslogic.UserManager;
import roxysshop.general.Constants;
import roxysshop.general.Utilities;
import roxysshop.helper.Record;

public class UpdateGraphicUserInterface {

	private static AddressManager addressManager = new AddressManager();
	private static UserManager userManager = new UserManager();
	
	public UpdateGraphicUserInterface() {
	}

	public static void displayAccountGraphicUserInterface(String display, String isUpdateError, PrintWriter printWriter) {
		StringBuilder content = new StringBuilder();
		List<String> values = userManager.getInformation(display);
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
		content.append("		<form action=\"" + Constants.UPDATE_SERVLET_PAGE_CONTEXT + "\" method=\"post\" name=\"" + Constants.UPDATE_FORM + "\">\n");
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
		content.append("			<p style=\"text-align:right\">\n");
		content.append("				" + Constants.WELCOME_MESSAGE + display + "\n");
		content.append("				<br/>\n");
		content.append("			</p>\n");
		content.append("			<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 50%; background-image: url(./images/user_interface/background.jpg); margin: 0px auto;\">\n");
		content.append("				<tbody>\n");
		content.append("					<tr>\n");
		content.append("						<td>\n");
		content.append("							<div id=\"wrapperrelative\">\n");
		content.append("								<div id=\"wrappertop\"></div>\n");
		content.append("								<div id=\"wrappermiddle\">\n");
		content.append("									<table style=\"width: 50%; text-align: center; vertical-align: top;\" border=\"0\" cellpadding=\"4\" cellspacing=\"1\">\n");
		content.append("										<tbody>\n");
		content.append("											<tr>\n");
		content.append("												<td>\n");
		content.append("													<table>\n");
		content.append("														<tbody>\n");
		content.append("															<tr>\n");
		content.append("																<td>\n");
		content.append("																	<p>" + Constants.FIRST_NAME + ": </p>\n");
		content.append("																</td>\n");
		content.append("																<td>\n");
		content.append("																	<input type=\"text\" name=\"" + Constants.FIRST_NAME + "\" id=\"url\" value=\"" + values.get(1) + "\" onclick=\"this.value = ''\">\n");
		content.append("																</td>\n");
		content.append("															</tr>\n");
		content.append("															<tr>\n");
		content.append("																<td>\n");
		content.append("																	<p>" + Constants.LAST_NAME + ": </p>\n");
		content.append("																</td>\n");
		content.append("																<td>\n");
		content.append("																	<input type=\"text\" name=\"" + Constants.LAST_NAME + "\" id=\"url\" value=\"" + values.get(2) + "\" onclick=\"this.value = ''\">\n");
		content.append("																</td>\n");
		content.append("															</tr>\n");
		content.append("															<tr>\n");
		content.append("																<td>\n");
		content.append("																	<p>" + Constants.PHONE_NUMBER + ": </p>\n");
		content.append("																</td>\n");
		content.append("																<td>\n");
		content.append("																	<input type=\"text\" name=\"" + Constants.PHONE_NUMBER + "\" id=\"url\" value=\"" + values.get(3) + "\" onclick=\"this.value = ''\">\n");
		content.append("																</td>\n");
		content.append("															</tr>\n");
		content.append("															<tr>\n");
		content.append("																<td>\n");
		content.append("																	<p>" + Constants.EMAIL + ": </p>\n");
		content.append("																</td>\n");
		content.append("																<td>\n");
		content.append("																	<input type=\"text\" name=\"" + Constants.EMAIL + "\" id=\"url\" value=\"" + values.get(4) + "\" onclick=\"this.value = ''\">\n");
		content.append("																</td>\n");
		content.append("															</tr>\n");
		content.append("															<tr>\n");
		content.append("																<td>\n");
		content.append("																	<p>" + Constants.USERNAME + ": </p>\n");
		content.append("																</td>\n");
		content.append("																<td>\n");
		content.append("																	<p>" + values.get(5) + "</p>\n");
		content.append("																</td>\n");
		content.append("															</tr>\n");
		content.append("															<tr>\n");
		content.append("																<td>\n");
		content.append("																	<p>" + Constants.PASSWORD + ": </p>\n");
		content.append("																</td>\n");
		content.append("																<td>\n");
		content.append("																	<input type=\"password\" name=\"" + Constants.PASSWORD + "\" id=\"url\" value=\"" + values.get(6) + "\" onclick=\"this.value = ''\">\n");
		content.append("																</td>\n");
		content.append("															</tr>\n");
		content.append("														</tbody>\n");
		content.append("													</table>\n");
		content.append("												</td>\n");
		content.append("												<td>\n");
		List<Record> addresses = addressManager.getAddresses(userManager.getIdentifier(display));
		content.append("													<table>\n");
		content.append("														<tbody>\n");
		content.append("															<tr>\n");
		content.append("																<td>\n");
		content.append("																	<p>" + Constants.ADDRESS + ": </p>\n");
		content.append("																</td>\n");
     	content.append("                                    							<td>\n");
     	content.append("                                                    				<input type=\"text\" name=\"" + Constants.ADDRESS + "\" id=\"url\" value=\"address\" onclick=\"this.value = ''\">\n");
     	content.append("																</td>\n");
     	content.append("																<td>\n");
     	content.append("                                            						<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "\" id=\"insert_address\" value=\"" + Constants.INSERT_BUTTON_NAME + "\" src=\"./images/user_interface/insert.png\"/>\n");
     	content.append("																</td>\n");
     	content.append("                                    						</tr>\n");
     	content.append("                                     						<tr>\n");
     	content.append("																<td colspan=\"2\">\n");
     	content.append("                                        							<table>\n");
     	content.append("                                                						<tbody>\n");
     	for (Record address : addresses) {
     	    content.append("																		<tr>\n");
     	    content.append("                                                							<td style=\"background: #ebebeb; text-align: left;\">" + address.getValue() + "</td>\n");
     	    content.append("                                                						    <td>\n");
     	    content.append("																				<input type=\"image\" name=\"" + Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "_" + address.getAttribute() + "\" src=\"./images/user_interface/delete.png\" width=\"16\" height=\"16\" />\n");
     	    content.append("                                    						                </td>\n");
     	    content.append("																		</tr>\n");
     	}	
        content.append(" 							                                           	</tbody>\n");
        content.append("                            					        	  		</table>\n");
        content.append("																</td>\n");
        content.append("                            								</tr>\n");
		content.append("														</tbody>\n");
		content.append("													</table>\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		content.append("											<tr>\n");
		content.append("												<td colspan=\"3\">\n");
		content.append("													<input type=\"image\" src=\"./images/user_interface/update_button.png\" id=\"update\" name=\"" + Constants.UPDATE.toLowerCase() + "\" value=\"" + Constants.UPDATE + "\">\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		if (isUpdateError != null) {
			content.append("										<tr>\n");
			content.append("											<td colspan=\"3\">\n");
			content.append("											<h3 style=\"color: red\">" + isUpdateError + "</h3>\n");
			content.append("											</td>\n");
			content.append("										</tr>\n");
		}
		content.append("										</tbody>\n");
		content.append("									</table>\n");
		content.append("								</div>\n");
		content.append("								<div id=\"wrapperbottom\"></div>\n");
		content.append("							</div>\n");
		content.append("						</td>\n");
		content.append("					</tr>\n");
		content.append("					<tr>\n");
		content.append("						<td>\n");
		/*List<List<Record>> invoices = invoiceManager.getInvoices(userManager.getIdentifier(username));
		System.out.println(invoices);
		for (List<Record> invoice : invoices) {
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
			for (Record field : invoice) {
				content.append("															<b>" + field.getAttribute() + "</b>: " + field.getValue() + "\n");
				content.append("															<br/>\n");
			}
			content.append("																<br/>\n");
			content.append("															</td>\n");
			content.append("														</tr>\n");
			content.append("													</tbody>\n");
			content.append("												</table>\n");
			content.append("											</td>\n");
			content.append("										</tr>\n");
			content.append("									</tbody>\n");
			content.append("								</table>\n");
			content.append("							</div>\n");
			content.append("							<div id=\"wrapperbottom\"></div>\n");
			content.append("						</div>\n");
		}*/
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