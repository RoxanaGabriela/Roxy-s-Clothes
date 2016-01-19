package roxysshop.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import roxysshop.dataaccess.DatabaseOperations;
import roxysshop.dataaccess.DatabaseOperationsImplementation;
import roxysshop.general.Constants;

public class UserManager extends EntityManager {

	public UserManager() {
		table = "user";
	}
	
	@Override
	public long create(List<String> values) {
		values.add("client");
		values.add("0");
		
		return super.create(values);
	}
	
	@Override
	public int update(List<String> values, long id) {
		DatabaseOperations databaseOperations = null;
		
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			
			List<String> attributes = new ArrayList<>();
			attributes.add("type");
			attributes.add("notified");
			List<List<String>> content = databaseOperations.getTableContent(table, attributes,
					"id=\'" + id + "\'",
					null, null, null);
			
			if (content != null && !content.isEmpty()) {
				values.add(content.get(0).get(0));
				values.add(content.get(0).get(1));
				
				return super.update(values, id);
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
	
	public int verifyUser(String username, String password) {
		DatabaseOperations databaseOperations = null;
		int result = Constants.USER_NONE;
		
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			
			List<String> attributes = new ArrayList<>();
			attributes.add("type");
			List<List<String>> type = databaseOperations.getTableContent(table, attributes,
					Constants.USERNAME + "=\'" + username + "\' AND " + Constants.PASSWORD + "=\'" + password + "\'",
					null, null, null);
			
			if (type != null && !type.isEmpty() && type.get(0) != null && type.get(0).get(0) != null) {
				if (type.get(0).get(0).equals("client"))
					result = Constants.USER_CLIENT;
				else
					result = Constants.USER_ADMINISTRATOR;
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		System.out.println(result);
		return result;
	}
	
	public String getDisplay(String username, String password) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("CONCAT("+ Constants.FIRST_NAME + ", ' ', " + Constants.LAST_NAME + ")");
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
		} finally {
			databaseOperations.releaseResources();
		}
		
		return null;
	}
	
	public String verifyRegistration(List<String> values) {
		if (values.get(2).length() != 10) return Constants.ERROR_PHONE_NUMBER;
		
		Pattern pattern = Pattern.compile(".*@.*.*");
		Matcher matcher = pattern.matcher(values.get(3));
		if (!matcher.matches()) return Constants.ERROR_EMAIL;
		
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add(Constants.ID_ATTRIBUTE);
			List<List<String>> id = databaseOperations.getTableContent(table, attributes,
					Constants.USERNAME + "=\'" + values.get(4) + "\' AND " + Constants.PASSWORD + "=\'" + values.get(5) + "\'",
					null, null, null);
			if (id != null && !id.isEmpty() && id.get(0) != null && id.get(0).get(0) != null) {
				return Constants.ERROR_EXISTING_USER;
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
	
	public String verifyUpdate(List<String> values) {
		if ((values.get(3).startsWith("0") && values.get(3).length() != 10) && values.get(3).length() != 9) return Constants.ERROR_PHONE_NUMBER;
		
		Pattern pattern = Pattern.compile(".*@.*.*");
		Matcher matcher = pattern.matcher(values.get(4));
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
					"CONCAT("+ Constants.FIRST_NAME + ", ' ', " + Constants.LAST_NAME + ")=\'" + display + "\'", null, null, null);
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
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<List<String>> info = databaseOperations.getTableContent(table, null,
					"CONCAT("+ Constants.FIRST_NAME + ", ' ', " + Constants.LAST_NAME + ")=\'" + display + "\'", null, null, null);
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
	
	public List<List<String>> getAddresses(String display) {
		DatabaseOperations databaseOperations = null;
		List<List<String>> addresses = new ArrayList<>();
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			
			List<String> attributes = new ArrayList<>();
			attributes.add("a.id");
			attributes.add("a.county");
			attributes.add("a.city");
			attributes.add("a.street");
			attributes.add("a.postal_code");
			String table = new String("user u, address a");
			String whereClause = new String("a.user_id=u.id AND CONCAT("+ Constants.FIRST_NAME + ", ' ', " + Constants.LAST_NAME + ")=\'" + display + "\'");
			
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
}
