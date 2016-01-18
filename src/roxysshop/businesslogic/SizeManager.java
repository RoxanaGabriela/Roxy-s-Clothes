package roxysshop.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import roxysshop.dataaccess.DatabaseOperations;
import roxysshop.dataaccess.DatabaseOperationsImplementation;
import roxysshop.general.Constants;

public class SizeManager extends EntityManager {

	public SizeManager() {
		table = "size";
	}

	public List<String> getSizes(long productId) {
		DatabaseOperations databaseOperations = null;
		List<String> sizes = new ArrayList<>();
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("s.size");
			List<List<String>> content = databaseOperations.getTableContent("product p, size s", attributes,
					"p.id=\'" + productId + "\' AND s.product_id=p.id", null, null, null);
			
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
