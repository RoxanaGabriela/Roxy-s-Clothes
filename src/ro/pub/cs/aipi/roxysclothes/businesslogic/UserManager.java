package ro.pub.cs.aipi.roxysclothes.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ro.pub.cs.aipi.roxysclothes.dataaccess.DatabaseOperations;
import ro.pub.cs.aipi.roxysclothes.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.aipi.roxysclothes.general.Constants;

public class UserManager extends EntityManager {

	public UserManager() {
		table = "user";
	}
	
	public int verifyUser(String username, String password) {
		try {
			DatabaseOperations databaseOperations = DatabaseOperationsImplementation.getInstance();
			int result = Constants.USER_NONE;
			List<String> attributes = new ArrayList<>();
			attributes.add(Constants.TYPE_ATTRIBUTE);
			List<List<String>> type = databaseOperations.getTableContent(table, attributes,
					Constants.USERNAME + "=\'" + username + "\' AND " + Constants.PASSWORD + "=\'" + password + "\'",
					null, null, null);
			if (type != null && !type.isEmpty() && type.get(0) != null && type.get(0).get(0) != null) {
				if (type.equals(Constants.CLIENT_TYPE))
					return Constants.USER_CLIENT;
				return Constants.USER_ADMINISTRATOR;
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		}
		return Constants.USER_NONE;
	}
	
	public String getDisplay(String username, String password) {
		try {
			DatabaseOperations databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("CONCAT(first_name, ' ', last_name)");
			List<List<String>> display = databaseOperations.getTableContent(table, attributes,
					Constants.USERNAME + "=\'" + username + "\' AND " + Constants.PASSWORD + "=\'" + password + "\'",
					null, null, null);
			if (display != null && display.get(0) != null) {
				return display.get(0).get(0);
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String verifyRegistration(List<String> values) {
		if (values.get(4).length() != 13) return Constants.ERROR_PERSONAL_IDENTIFIER;
		if (values.get(6).length() != 10) return Constants.ERROR_PHONE_NUMBER;
		
		Pattern pattern = Pattern.compile(".*@.*.*");
		Matcher matcher = pattern.matcher(values.get(5));
		if (!matcher.matches()) return Constants.ERROR_EMAIL;
		
		try {
			DatabaseOperations databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add(Constants.ID_ATTRIBUTE);
			List<List<String>> id = databaseOperations.getTableContent(table, attributes,
					"personal_identifier" + "=\'" + values.get(4) + "\'",
					null, null, null);
			if (id != null && !id.isEmpty() && id.get(0) != null && id.get(0).get(0) != null) {
				return Constants.ERROR_EXISTING_PERSONAL_IDENTIFIER;
			}
			
			id = databaseOperations.getTableContent(table, attributes,
					Constants.USERNAME + "=\'" + values.get(6) + "\' AND " + Constants.PASSWORD + "=\'" + values.get(1) + "\'",
					null, null, null);
			if (id != null && !id.isEmpty() && id.get(0) != null && id.get(0).get(0) != null) {
				return Constants.ERROR_EXISTING_USER;
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		}		
		return null;
	}
	
	public String verifyUpdate(List<String> values) {
		if (values.get(5).length() != 13) return Constants.ERROR_PERSONAL_IDENTIFIER;
		if ((values.get(7).startsWith("0") && values.get(7).length() != 10) && values.get(7).length() != 9) return Constants.ERROR_PHONE_NUMBER;
		
		Pattern pattern = Pattern.compile(".*@.*.*");
		Matcher matcher = pattern.matcher(values.get(6));
		if (!matcher.matches()) return Constants.ERROR_EMAIL;
			
		return null;
	}
	
	public long getIdentifier(String display) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add(Constants.ID_ATTRIBUTE);
			List<List<String>> identifier = databaseOperations.getTableContent(table, attributes,
					"CONCAT(first_name, \' \', last_name)=\'" + display + "\'", null, null, null);
			if (identifier != null && !identifier.isEmpty()) {
				return Long.parseLong(identifier.get(0).get(0));
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		return -1;
	}
	
	public String getUsername(String display) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add(Constants.USERNAME);
			List<List<String>> username = databaseOperations.getTableContent(table, attributes,
					"CONCAT(first_name, \' \', last_name)=\'" + display + "\'", null, null, null);
			if (username != null && !username.isEmpty()) {
				return username.get(0).get(0);
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		return null;
	}
	
	public List<String> getInformation(String display) {
		long identifier = getIdentifier(display);
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<List<String>> info = databaseOperations.getTableContent(table, null,
					"id=\'" + identifier + "\'", null, null, null);
			if (info != null && !info.isEmpty() && info.get(0) != null && !info.get(0).isEmpty()) {
				return info.get(0);
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		return null;
	}
	
	public List<List<String>> getDisplayAddresses(String display) {
		DatabaseOperations databaseOperations = null;
		List<List<String>> addresses = new ArrayList<>();
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			
			List<String> attributes = new ArrayList<>();
			attributes.add("a.id");
			attributes.add("a.address");
			String table = new String("user u, address a");
			String whereClause = new String("a.user_id=u.id AND CONCAT(first_name, \' \', last_name)=\'" + display + "\'");
			
			addresses = databaseOperations.getTableContent(table, attributes, whereClause, null, null, null);
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		return addresses;
	}
	
	public List<String> getAddresses(String display) {
		List<String> addressesList = new ArrayList<>();
		List<List<String>> addresses = getDisplayAddresses(display);
		for (List<String> address : addresses) {
			addressesList.add(address.get(1));
		}
		
		return addressesList;
	}
}
