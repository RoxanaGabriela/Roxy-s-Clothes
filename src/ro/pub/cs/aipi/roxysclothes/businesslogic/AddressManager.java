package ro.pub.cs.aipi.roxysclothes.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.pub.cs.aipi.roxysclothes.dataaccess.DatabaseException;
import ro.pub.cs.aipi.roxysclothes.dataaccess.DatabaseOperations;
import ro.pub.cs.aipi.roxysclothes.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.aipi.roxysclothes.general.Constants;

public class AddressManager extends EntityManager {

	public AddressManager() {
		table = "address";
	}
	
	public boolean verifyAddress(String id) {
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
