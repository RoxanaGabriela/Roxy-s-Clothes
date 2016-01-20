package roxysshop.graphicuserinterface;

import java.io.PrintWriter;
import java.util.List;

import roxysshop.businesslogic.ProductManager;
import roxysshop.general.Constants;
import roxysshop.general.Utilities;
import roxysshop.helper.Record;

public class ProductsGraphicUserInterface {
	public static ProductManager productManager = new ProductManager();
	
	public ProductsGraphicUserInterface() {
	}

	public static void displayProductsGraphicUserInterface(String display, List<Record> product, List<Record> fabrics, int currentRecordsPerPage, 
			int currentPage, PrintWriter printWriter) {
		StringBuilder content = new StringBuilder();
		List<List<Record>> products = productManager.getCollection("p.id ASC", null, null, null, null, null, null);
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
		content.append("		<form action=\"" + Constants.PRODUCTS_SERVLET_PAGE_CONTEXT + "\" method=\"post\" name=\"" + Constants.PRODUCTS_FORM + "\">\n");
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
		content.append("				" + Constants.RECORDS_PER_PAGE + "<select name=\"" + Utilities.removeSpaces(Constants.RECORDS_PER_PAGE.toLowerCase().trim()) + "\" onchange=\"document." + Constants.PRODUCTS_FORM + ".submit()\">\n");
		for (int recordsPerPageValue : Constants.RECORDS_PER_PAGE_VALUES) {
			content.append("				<option value=\"" + recordsPerPageValue + "\"" + ((recordsPerPageValue == currentRecordsPerPage) ? " SELECTED" : "") + ">" + recordsPerPageValue + "</option>\n");
		}
		content.append("				</select>\n");
		content.append("				" + Constants.PAGE + "<select name=\"" + Utilities.removeSpaces(Constants.PAGE.toLowerCase().trim()) + "\" onchange=\"document." + Constants.PRODUCTS_FORM + ".submit()\">\n");
		
		for (int pageValue = 1; pageValue <= products.size() / currentRecordsPerPage + ((products.size() % currentRecordsPerPage) != 0 ? 1 : 0); pageValue++) {
			content.append("				<option value=\"" + pageValue + "\"" + ((pageValue == currentPage) ? " SELECTED" : "") + ">" + pageValue + "</option>\n");
		}
		content.append("				</select>\n");
		content.append("			</p>\n");
		content.append("			<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 100%; background-image: url(./images/user_interface/background.jpg); margin: 0px auto;\">\n");
		content.append("				<tbody>\n");
		content.append("					<tr>\n");
		content.append("						<td style=\"width: 10%; text-align: left; vertical-align: top;\">\n");
		content.append("							<div id=\"wrapperrelative\">\n");
		content.append("								<div id=\"wrappertop\"></div>\n");
		content.append("								<div id=\"wrappermiddle\">\n");
		content.append("									<table border=\"0\" cellpadding=\"4\" cellspacing=\"1\">\n");
		content.append("										<tbody>\n");
		content.append("											<tr>\n");
		content.append("												<td>\n");
		content.append("													<input type=\"image\" name=\"" + Constants.HISTORY.toLowerCase() + "\" value=\"" + Constants.HISTORY + "\" src=\"./images/user_interface/home.png\" />\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		content.append("											<tr>\n");
		content.append("												<td>\n");	
		content.append("													<input type=\"image\" name=\"" + Constants.CLIENTS_HISTORY.toLowerCase() + "\" value=\"" + Constants.CLIENTS_HISTORY + "\" src=\"./images/user_interface/home.png\" />\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		content.append("											<tr>\n");
		content.append("												<td>\n");	
		content.append("													<input type=\"image\" name=\"" + Constants.PRODUCTS.toLowerCase() + "\" value=\"" + Constants.PRODUCTS + "\" src=\"./images/user_interface/home.png\" />\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		content.append("										</tbody>\n");
		content.append("									</table>\n");
		content.append("								</div>\n");
		content.append("								<div id=\"wrapperbottom\"></div>\n");
		content.append("							</div>\n");
		content.append("						</td>\n");

		content.append("						<td>\n");
		content.append("							<table>\n");
		content.append("								<tbody>\n");
		content.append("									<tr>\n");
		content.append("									<td>\n");
		content.append("										<div id=\"wrapperrelative\">\n");
		content.append("											<div id=\"wrappertop\"></div>\n");
		content.append("												<div id=\"wrappermiddle\">\n");
		content.append("													<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
		content.append("														<tbody>\n");
		content.append("															<tr>\n");
		content.append("																<td>\n");
		content.append("																	<table>\n");
		content.append("																		<tbody>\n");
		for (Record field : product) {
			content.append("																		<tr>\n");
			content.append("																			<td>&nbsp;</td>\n");
			content.append("																				<td style=\"text-align: left;\">" + field.getAttribute() + ": </td>\n");
			content.append("																			<td>\n");
			content.append("																				<input type=\"text\" name=\"" + field.getAttribute() + "\" id=\"url\" value=\"" + field.getValue() + "\" onclick=\"this.value = ''\">\n");
			content.append("																			</td>\n");
			content.append("																		</tr>\n");
		}
		content.append("																		</tbody>\n");
		content.append("																	</table>\n");
		content.append("																</td>\n");
		content.append("																<td>\n");
		content.append("																	<table>\n");
		content.append("																		<tbody>\n");
		content.append("																			<tr>\n");
		content.append("																				<td>\n");
		content.append("																					<p>Fabrics: </p>\n");
		content.append("																				</td>\n");
		content.append("																				<td>\n");
		content.append("																					<input type=\"text\" name=\"" + Constants.FABRIC_NAME + "\" id=\"url\" value=\"\" onclick=\"this.value = ''\">\n");
		content.append("																				</td>\n");
		content.append("																				<td>\n");
		content.append("																					<input type=\"text\" name=\"" + Constants.FABRIC_VALUE + "\" id=\"url\" value=\"\" onclick=\"this.value = ''\">\n");
		content.append("																				</td>\n");
     	content.append("																				<td>\n");
     	content.append("                        					                    					<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.FABRIC_NAME + "\" id=\"insert_address\" value=\"" + Constants.INSERT_BUTTON_NAME + "\" src=\"./images/user_interface/insert.png\"/>\n");
     	content.append("																				</td>\n");
     	content.append("            					                        					</tr>\n");
     	content.append("                                					     					<tr>\n");
     	content.append("																				<td colspan=\"2\">\n");
     	content.append("                                        											<table>\n");
     	content.append("                                                										<tbody>\n");
     	for (Record fabric : fabrics) {
     	    content.append("																						<tr>\n");
     	    content.append("                    										                            	<td style=\"background: #ebebeb; text-align: left;\">" + fabric.getValue() + "% " + fabric.getAttribute() + "</td>\n");
     	    content.append("                            										                        <td>\n");
     	    content.append("																								<input type=\"image\" name=\"" + Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + Constants.FABRIC_NAME + "_" + fabric.getAttribute() + "\" src=\"./images/user_interface/delete.png\" width=\"16\" height=\"16\" />\n");
     	    content.append("                        										                            </td>\n");
     	    content.append("																						</tr>\n");
     	}
        content.append("                            										                	</tbody>\n");
        content.append("                            										          		</table>\n");
		content.append("																				</td>\n");
		content.append("																			</tr>\n");
		content.append("																		</tbody>\n");
		content.append("																	</table>\n");
		content.append("																</td>\n");
		content.append("															</tr>\n");
		content.append("															<tr>\n");
		content.append("																<td colspan=\"2\">\n");
		content.append("																	<input type=\"image\" src=\"./images/user_interface/update_button.png\" id=\"update\" name=\"" + Constants.UPDATE.toLowerCase() + "\" value=\"" + Constants.UPDATE + "\">\n");
		content.append("																</td>\n");
		content.append("															</tr>\n");
		content.append("														</tbody>\n");
		content.append("													</table>\n");
		content.append("												</div>\n");
		content.append("												<div id=\"wrapperbottom\"></div>\n");
		content.append("											</div>\n");
		content.append("										</td>\n");
		content.append("										</tr>\n");
		content.append("										<tr>\n");
		content.append("										<td>\n");
		
		for (int index = 0; index < products.size(); index += 3) {
			if (index + 1 < ((currentPage - 1) * currentRecordsPerPage + 1)
					|| index + 1 > (currentPage * currentRecordsPerPage)) {
				continue;
			}
				content.append("					<div id=\"wrapperrelative\">\n");
				content.append("						<div id=\"wrappertop\"></div>\n");
				content.append("						<div id=\"wrappermiddle\">\n");
				content.append("							<table style=\"width: 100%;\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n");
				content.append("								<tbody>\n");
				content.append("									<tr>\n");
				for (int i = 0; i < 3; i++) {
					if (index + i >= products.size()) break;
					String currentIdentifier = products.get(index + i).get(0).getValue().toString();
					System.out.println(currentIdentifier);
					content.append("									<td>\n");
					content.append("										<table>\n");
					content.append("											<tbody>\n");
					content.append("												<tr>\n");
					content.append("													<td style=\"text-align: center;\">\n");
					content.append("														<input type=\"image\" name=\"" + Constants.PRODUCT + "_" + currentIdentifier + "\" value=\"" + Constants.PRODUCT + "\" src=\"" + products.get(index + i).get(7).getValue() + "\" height=\"314\" width=\"208\"/>\n");
					content.append("													</td>\n");
					content.append("												</tr>\n");
					content.append("												<tr>\n");
					content.append("													<td style=\"text-align: center;\">" + products.get(index + i).get(1).getAttribute() + ": " + products.get(index + i).get(1).getValue() + "</td>\n");
					content.append("												</tr>\n");
					content.append("												<tr>\n");
					content.append("													<td style=\"text-align: center;\">" + products.get(index + i).get(2).getAttribute() + ": " + products.get(index + i).get(2).getValue() + "</td>\n");
					content.append("												</tr>\n");
					content.append("											</tbody>\n");
					content.append("										</table>\n");
					content.append("									</td>\n");
				}
				content.append("									</tr>\n");
				content.append("								</tbody>\n");
				content.append("							</table>\n");
				content.append("						</div>\n");
				content.append("						<div id=\"wrapperbottom\"></div>\n");
				content.append("					</div>\n");
		}
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
