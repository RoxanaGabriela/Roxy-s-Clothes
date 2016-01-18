package roxysshop.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import roxysshop.dataaccess.DatabaseOperations;
import roxysshop.dataaccess.DatabaseOperationsImplementation;
import roxysshop.general.Constants;
import roxysshop.helper.Record;

public class ProductManager extends EntityManager {

	public ProductManager() {
		table = "product";
	}
	
	public List<List<Record>> getCollection(String currentSort, String currentCategory, List<String> labelsFilter, String currentSearch) {
		DatabaseOperations databaseOperations = null;
		List<List<Record>> result = new ArrayList<>();
		
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			
			String tableName = new String("product p, fabric f");
			
			List<String> attributes = new ArrayList<>();
			attributes.add("p.id AS id");
			attributes.add("p.name AS name");
			attributes.add("CONCAT(p.price, ' ', p.currency) AS price");
			attributes.add("p.producer AS producer");
			attributes.add("p.color AS color");
			attributes.add("p.description AS description");
			attributes.add("GROUP_CONCAT(CONCAT(f.percent, '% ', f.name)) AS fabrics");
			attributes.add("p.picture AS picture");
			
			String whereClause = new String("f.product_id=p.id");
				
			String orderByClause = new String();
			if (currentSort != null && !currentSort.isEmpty()) {
				orderByClause = currentSort;
			}
			
			String categoryClause = new String();
			if (currentCategory != null && !currentCategory.isEmpty()) {
				categoryClause = new String(" AND category=\'" + currentCategory + "\'");
			}
			
			String labelsClause = new String();
			if (labelsFilter != null && !labelsFilter.isEmpty()) {
				labelsClause = new String(" AND (");
				for (String label : labelsFilter)
					labelsClause += "p.name LIKE \'%" + label + "%\' OR p.category LIKE \'%" + label + 
									"%\' OR p.color LIKE \'%" + label + "%\' OR p.description LIKE \'%" + label + "%\' OR ";
				labelsClause = labelsClause.substring(0, labelsClause.length() - 3) + ")";
			}
			
			String searchClause = new String();
			if (currentSearch != null && !currentSearch.isEmpty()) {
				searchClause += " AND (p.name LIKE \'%" + currentSearch + "%\' OR p.category LIKE \'%" + currentSearch + 
									"%\' OR p.color LIKE \'%" + currentSearch + "%\' OR p.description LIKE \'%" + currentSearch + "%\' OR ";
				searchClause = searchClause.substring(0, searchClause.length() - 3) + ")";
			}
			
			String groupByClause = "p.id";
			List<List<String>> products = databaseOperations.getTableContent(tableName, attributes, 
												whereClause + categoryClause + labelsClause + searchClause, null, orderByClause, groupByClause);
			System.out.println(products);
			for (List<String> product : products) {
				Record id = new Record("Id", product.get(0));
				Record name = new Record("Name", product.get(1));
				Record price = new Record("Price", product.get(2));
				Record producer = new Record("Producer", product.get(3));
				Record color = new Record("Color", product.get(4));
				Record description = new Record("Description", product.get(5));
				Record fabrics = new Record("Fabrics", product.get(6));
				Record picture = new Record("Picture", product.get(7));

				List<Record> details = new ArrayList<>();
				details.add(id);
				details.add(name);
				details.add(price);
				details.add(producer);
				details.add(color);
				details.add(description);
				details.add(fabrics);
				details.add(picture);
				
				result.add(details);
			}
			
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		
		return result;
	}
	
	public List<Record> getDetails(long identifier) {
		DatabaseOperations databaseOperations = null;
		List<Record> details = new ArrayList<>();
		
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			String tableName = new String("product p, fabric f");
			
			List<String> attributes = new ArrayList<>();			
			attributes.add("p.id AS id");
			attributes.add("p.name AS name");
			attributes.add("p.price AS price");
			attributes.add("p.producer AS producer");
			attributes.add("p.color AS color");
			attributes.add("p.description AS description");
			attributes.add("GROUP_CONCAT(CONCAT(f.percent, '% ', f.name)) AS fabrics");
			attributes.add("p.picture AS picture");
			
			String whereClause = new String("f.product_id=p.id AND p.id=\'" + identifier + "\'");
			List<List<String>> product = databaseOperations.getTableContent(tableName, attributes, whereClause, null, null, null);
			
			if (product != null && !product.isEmpty()) {
				Record id = new Record("Id", product.get(0).get(0));
				Record name = new Record("Name", product.get(0).get(1));
				Record price = new Record("Price", product.get(0).get(2));
				Record producer = new Record("Producer", product.get(0).get(3));
				Record color = new Record("Color", product.get(0).get(4));
				Record description = new Record("Description", product.get(0).get(5));
				Record fabrics = new Record("Fabrics", product.get(0).get(6));
				Record picture = new Record("Picture", product.get(0).get(7));
				
				details.add(id);
				details.add(name);
				details.add(price);
				details.add(producer);
				details.add(color);
				details.add(description);
				details.add(fabrics);
				details.add(picture);
				
				return details;
			}		
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		return null;
	}
}
