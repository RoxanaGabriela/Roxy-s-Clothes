package ro.pub.cs.aipi.roxysclothes.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ro.pub.cs.aipi.roxysclothes.dataaccess.DatabaseOperations;
import ro.pub.cs.aipi.roxysclothes.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.aipi.roxysclothes.general.Constants;
import ro.pub.cs.aipi.roxysclothes.helper.Record;

public class ProductManager extends EntityManager {

	public ProductManager() {
		table = "product";
	}
	
	public List<List<Record>> getCollection(String currentSort, String currentCategory, List<String> labelsFilter) {
		DatabaseOperations databaseOperations = null;
		List<List<Record>> result = new ArrayList<>();
		
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			
			String tableName = new String("product p, fabric f");
			List<String> attributes = new ArrayList<>();
			attributes.add("p.name AS name");
			attributes.add("CONCAT(p.price, ' ', p.currency) AS price");
			attributes.add("p.producer AS producer");
			attributes.add("p.color AS color");
			attributes.add("p.description AS description");
			attributes.add("GROUP_CONCAT(CONCAT(f.percent, '% ', f.name)) AS fabrics");
			
			
			
			String orderByClause = new String();
			if (currentSort != null && !currentSort.isEmpty()) {
				orderByClause = currentSort;
			}
			
			String categoryClause = new String();
			if (currentCategory != null && !currentCategory.isEmpty()) {
				categoryClause = " AND p.category=\'" + currentCategory + "\'";
			}
			
			/*String labelsClause = new String();
			if (labelsFilter != null && !labelsFilter.isEmpty()) {
				labelsClause = new String(" AND (");
				for (String label : labelsFilter)
					labelsClause += "t.labels LIKE \'%" + label + "%\' OR ";
				labelsClause = labelsClause.substring(0, labelsClause.length() - 3) + ")";
			}*/
			
			String whereClause = new String("f.product_id=p.id" + categoryClause);
			String groupByClause = new String("p.id");
			List<List<String>> products = databaseOperations.getTableContent(tableName, attributes, whereClause, null, null, groupByClause);
			
			for (List<String> product : products) {
				Record name = new Record("Name", product.get(0));
				Record price = new Record("Price", product.get(1));
				Record producer = new Record("Producer", product.get(2));
				Record color = new Record("Color", product.get(3));
				Record description = new Record("Description", product.get(4));
				Record fabrics = new Record("Fabrics", product.get(5));
				
				List<Record> details = new ArrayList<>();
				details.add(name);
				details.add(price);
				details.add(producer);
				details.add(color);
				details.add(description);
				details.add(fabrics);
				
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
	
	public Record getInformation(long identifier) {
		DatabaseOperations databaseOperations = null;
		Record result = new Record();
		
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			
			List<String> attributes = new ArrayList<>();
			attributes.add("name");
			attributes.add("price");
						
			String whereClause = new String("id=\'" + identifier + "\'");
			List<List<String>> info = databaseOperations.getTableContent(table, attributes, whereClause, null, null, null);
			
			if (info != null && !info.isEmpty() && info.get(0) != null && !info.get(0).isEmpty()) {
				result.setAttribute(info.get(0).get(0));
				result.setValue(Double.parseDouble(info.get(0).get(1)));
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
	
	public int getStockpile(long id, String size) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("s.stockpile");
			List<List<String>> content = databaseOperations.getTableContent("products p, size s", attributes,
					"p.id=\'" + id + "\' AND s.product_id=p.id AND s.size=\'" + size + "\'", null, null, null);
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
	
	public List<String> getSizes(long id) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("s.size");
			List<List<String>> content = databaseOperations.getTableContent("products p, size s", attributes,
					"p.id=\'" + id + "\' AND s.product_id=p.id", null, null, null);
			
			return content.get(0);
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
}
