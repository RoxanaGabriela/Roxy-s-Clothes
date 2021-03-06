package roxysshop.graphicuserinterface;

import java.io.PrintWriter;
import java.util.List;

import roxysshop.general.Constants;

public class RegisterGraphicUserInterface {
	public RegisterGraphicUserInterface() {
	}

        public static void displayRegisterGraphicUserInterface(List<String> values, List<String> addressesList, String isRegisterError, PrintWriter printWriter) {
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
		content.append("	<body style=\"text-align: center\">\n");
		content.append("		<h2>" + Constants.APPLICATION_NAME.toUpperCase() + "</h2>\n");
		content.append("		<form action=\"" + Constants.REGISTER_SERVLET_PAGE_CONTEXT + "\" method=\"post\" name=\"" + Constants.REGISTER_FORM + "\">\n");
        content.append("            <h2>" + Constants.SIGNUP + "</h2>\n");
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
		content.append("											<td>\n");
		content.append("												<table>\n");
		content.append("													<tbody>\n");
		content.append("														<tr>\n");
		content.append("															<td>\n");
		content.append("																<p>" + Constants.FIRST_NAME + ": </p>\n");
		content.append("															</td>\n");
		content.append("															<td>\n");
		content.append("																<input type=\"text\" name=\"" + Constants.FIRST_NAME + "\" id=\"url\" value=\"" + values.get(0) + "\" onclick=\"this.value = ''\">\n");
		content.append("															</td>\n");
		content.append("														</tr>\n");
		content.append("														<tr>\n");
		content.append("															<td>\n");
		content.append("																<p>" + Constants.LAST_NAME + ": </p>\n");
		content.append("															</td>\n");
		content.append("															<td>\n");
		content.append("																<input type=\"text\" name=\"" + Constants.LAST_NAME + "\" id=\"url\" value=\"" + values.get(1) + "\" onclick=\"this.value = ''\">\n");
		content.append("															</td>\n");
		content.append("														</tr>\n");
		content.append("														<tr>\n");
		content.append("															<td>\n");
		content.append("																<p>" + Constants.PHONE_NUMBER + ": </p>\n");
		content.append("															</td>\n");
		content.append("															<td>\n");
		content.append("																<input type=\"text\" name=\"" + Constants.PHONE_NUMBER + "\" id=\"url\" value=\"" + values.get(2) + "\" onclick=\"this.value = ''\">\n");
		content.append("															</td>\n");
		content.append("														</tr>\n");
		content.append("														<tr>\n");
		content.append("															<td>\n");
		content.append("																<p>" + Constants.EMAIL + ": </p>\n");
		content.append("															</td>\n");
		content.append("															<td>\n");
		content.append("																<input type=\"text\" name=\"" + Constants.EMAIL + "\" id=\"url\" value=\"" + values.get(3) + "\" onclick=\"this.value = ''\">\n");
		content.append("															</td>\n");
		content.append("														</tr>\n");
		content.append("														<tr>\n");
		content.append("															<td>\n");
		content.append("																<p>" + Constants.USERNAME + ": </p>\n");
		content.append("															</td>\n");
		content.append("															<td>\n");
		content.append("																<input type=\"username\" name=\"" + Constants.USERNAME + "\" id=\"url\" value=\"" + values.get(4) + "\" onclick=\"this.value = ''\">\n");
		content.append("															</td>\n");
		content.append("														</tr>\n");
		content.append("														<tr>\n");
		content.append("															<td>\n");
		content.append("																<p>" + Constants.PASSWORD + ": </p>\n");
		content.append("															</td>\n");
		content.append("															<td>\n");
		content.append("																<input type=\"password\" name=\"" + Constants.PASSWORD + "\" id=\"url\" value=\"" + values.get(5) + "\" onclick=\"this.value = ''\">\n");
		content.append("															</td>\n");
		content.append("														</tr>\n");
		content.append("													</tbody>\n");
		content.append("												</table>\n");
		content.append("											</td>\n");
		content.append("											<td>\n");
		content.append("												<table>\n");
		content.append("													<tbody>\n");
		content.append("														<tr>\n");
		content.append("															<td>\n");
		content.append("																<p>" + Constants.ADDRESS + ": </p>\n");
		content.append("															</td>\n");
		content.append("															<td>\n");
		content.append("																<input type=\"text\" name=\"" + Constants.ADDRESS + "\" id=\"url\" value=\"\" onclick=\"this.value = ''\">\n");
		content.append("															</td>\n");
     	content.append("															<td>\n");
     	content.append("                                            					<input type=\"image\" name=\"" + Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "\" id=\"insert_address\" value=\"" + Constants.INSERT_BUTTON_NAME + "\" src=\"./images/user_interface/insert.png\"/>\n");
     	content.append("															</td>\n");
     	content.append("                                    					</tr>\n");
     	content.append("                                     					<tr>\n");
     	content.append("															<td colspan=\"2\">\n");
     	content.append("                                        						<table>\n");
     	content.append("                                                					<tbody>\n");
     	for (String address : addressesList) {
     	    content.append("												<tr>\n");
     	    content.append("                                                	<td style=\"background: #ebebeb; text-align: left;\">" + address + "</td>\n");
     	    content.append("                                                    <td>\n");
     	    content.append("														<input type=\"image\" name=\"" + Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + Constants.ADDRESS + "_" + address + "\" src=\"./images/user_interface/delete.png\" width=\"16\" height=\"16\" />\n");
     	    content.append("                                                    </td>\n");
     	    content.append("												</tr>\n");
     	}
        content.append("                                            	</tbody>\n");
        content.append("                                      		</table>\n");
        content.append("										</td>\n");
        content.append("                            		</tr>\n");
		content.append("													</tbody>\n");
		content.append("												</table>\n");
		content.append("											</td>\n");
		content.append("											</tr>\n");
		content.append("											<tr>\n");
		content.append("												<td colspan=\"3\">\n");
		content.append("													<input type=\"image\" src=\"./images/user_interface/signup.png\" id=\"register2\" name=\"" + Constants.SIGNUP.toLowerCase() + "\" value=\"" + Constants.SIGNUP + "\">\n");
		content.append("												</td>\n");
		content.append("											</tr>\n");
		if (isRegisterError != null) {
			content.append("										<tr>\n");
			content.append("											<td colspan=\"3\">\n");
			content.append("												<h3 style=\"color: red\">" + isRegisterError + "</h3>\n");
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
		content.append("				</tbody>\n");
		content.append("			</table>\n");
		content.append("		</form>");
		content.append("	</body>\n");
		content.append("</html>");
		printWriter.println(content.toString());
	}
}