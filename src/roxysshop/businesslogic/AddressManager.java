package roxysshop.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import roxysshop.dataaccess.DatabaseException;
import roxysshop.dataaccess.DatabaseOperations;
import roxysshop.dataaccess.DatabaseOperationsImplementation;
import roxysshop.general.Constants;
import roxysshop.helper.Record;

public class AddressManager extends EntityManager {

	public AddressManager() {
		table = "address";
	}
	
	public List<Record> getAddresses(long userId) {
		DatabaseOperations databaseOperations = null;
		List<Record> addresses = new ArrayList<>();
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<List<String>> content = databaseOperations.getTableContent(table, null,
					"user_id=\'" + userId + "\'", null, null, null);
			if (content != null && !content.isEmpty()) {
				for (List<String> field : content) {
					addresses.add(new Record(field.get(0), field.get(1)));
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
		return addresses;
	}
	
	public boolean verifyAddress(long id) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<List<String>> identifier = databaseOperations.getTableContent(table, null,
					"id=\'" + id + "\'", null, null, null);
			if (identifier != null && !identifier.isEmpty()) {
				return true;
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		return false;
	}
	
	public void deleteAddress(String id) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("id");
			
			List<String> values = new ArrayList<>();
			values.add(id);
			
			databaseOperations.deleteRecordsFromTable(table, attributes, values, null);

		} catch (SQLException | DatabaseException e) {
			System.out.println("An exception has occurred: " + e.getMessage());
			if (Constants.DEBUG) {
				e.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
	}
	
	public long getNextIdentifier() {
		long id = -1;
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			id = databaseOperations.getTablePrimaryKeyMaximumValue(table) + 1;

		} catch (SQLException e) {
			System.out.println("An exception has occurred: " + e.getMessage());
			if (Constants.DEBUG) {
				e.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		
		return id;
	}
}
