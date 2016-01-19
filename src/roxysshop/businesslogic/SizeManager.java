package roxysshop.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import roxysshop.dataaccess.DatabaseException;
import roxysshop.dataaccess.DatabaseOperations;
import roxysshop.dataaccess.DatabaseOperationsImplementation;
import roxysshop.general.Constants;

public class SizeManager extends EntityManager {

	public SizeManager() {
		table = "size";
	}
	
	public int update(List<String> attributes, List<String> values, long id, String size) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			int result = databaseOperations.updateRecordsIntoTable("size", attributes, values,
					 "product_id=\'" + id + "\' AND size=\'" + size + "\'");
			if (result != 1) {
				if (Constants.DEBUG) {
					System.out.println("Update operation failed!");
				}
			}
			return result;
		} catch (DatabaseException | SQLException exception) {
			System.out.println("An exception has occurred: " + exception.getMessage());
			if (Constants.DEBUG) {
				exception.printStackTrace();
			}
		}
		return -1;
	}
	
	public int getStockpile(long id, String size) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("stockpile");
			List<List<String>> content = databaseOperations.getTableContent("size", attributes,
					"product_id=\'" + id + "\' AND size=\'" + size + "\'", null, null, null);
			if (content == null || content.size() != 1) {
				if (Constants.DEBUG) {
					System.out.format("The query returned a different number of results than expected (%d)!%n",
							content.size());
				}
				return -1;
			}
			return Integer.parseInt(content.get(0).get(0));
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
	
	public List<String> getSizes(long productId) {
		DatabaseOperations databaseOperations = null;
		List<String> sizes = new ArrayList<>();
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("s.size");
			List<List<String>> content = databaseOperations.getTableContent("product p, size s", attributes,
					"p.id=\'" + productId + "\' AND s.product_id=p.id AND s.stockpile>0", null, null, null);
			
			if (content != null) {
				for (List<String> field : content) {
					sizes.add(field.get(0));
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
		return sizes;
	}
}
