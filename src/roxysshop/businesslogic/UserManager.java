package roxysshop.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import roxysshop.dataaccess.DatabaseException;
import roxysshop.dataaccess.DatabaseOperations;
import roxysshop.dataaccess.DatabaseOperationsImplementation;
import roxysshop.general.Constants;
import roxysshop.helper.Record;

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
	
	public List<List<Record>> getCLients() {
		DatabaseOperations databaseOperations = null;
		List<List<Record>> clients = new ArrayList<>();
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<List<String>> content = databaseOperations.getTableContent(table, null, "notified<3 AND type NOT LIKE 'administrator'", null, null, null);
			if (content != null && !content.isEmpty()) {
				for (List<String> field : content) {
					Record id = new Record ("Id", field.get(0));
					Record firstName = new Record ("First name", field.get(1));
					Record lastName = new Record ("Last name", field.get(2));
					Record phoneNumber = new Record ("Phone number", field.get(3));
					Record email = new Record ("Email", field.get(4));
					Record username = new Record ("Username", field.get(5));
					Record password = new Record ("Password", field.get(6));
					Record notified = new Record ("Notified", field.get(8));
					
					List<Record> client = new ArrayList<>();
					client.add(id);
					client.add(firstName);
					client.add(lastName);
					client.add(phoneNumber);
					client.add(email);
					client.add(username);
					client.add(password);
					client.add(notified);
					
					clients.add(client);
				}
			} 
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		return clients;
	}
	
	public int updateNotified(String identifier) {
		DatabaseOperations databaseOperations = null;
		int updated = -1;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			
			List<String> attributes = new ArrayList<>();
			attributes.add("notified");
			
			List<List<String>> content = databaseOperations.getTableContent(table, attributes, "id=\'" + identifier + "\'", null, null, null);
			Integer notified = Integer.parseInt(content.get(0).get(0)) + 1;
			
			List<String> values = new ArrayList<>();
			values.add(notified + "");
			updated = databaseOperations.updateRecordsIntoTable(table, attributes, values, "id=\'" + identifier + "\'");
		} catch (SQLException | DatabaseException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		return updated;
	}
}
