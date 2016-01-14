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
		List<List<Record>> result = new ArrayList<>();
		
		try {
			DatabaseOperations databaseOperations = DatabaseOperationsImplementation.getInstance();
			
			String tableName = new String("product p, fabric f");
			List<String> attributes = new ArrayList<>();
			attributes.add("p.name AS name");
			attributes.add("CONCAT(p.price, ' ', p.currency) AS price");
			attributes.add("p.producer AS producer");
			attributes.add("p.color AS color");
			attributes.add("p.description AS description");
			attributes.add("GROUP_CONCAT(CONCAT(f.percent, '% ', f.name))");
			
			
			
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
			
			String whereClause = new String("f.product_id=p.id" + dateClause + categoryClause + labelsClause);
			String groupByClause = new String("p.id");
			List<List<String>> dishes = databaseOperations.getTableContent(tableName, attributes, whereClause, null, null, groupByClause);
			
			for (List<String> dish : dishes) {
				StringTokenizer st = new StringTokenizer(dish.get(0), ";");
				while (st.hasMoreElements()) {
					List<Record> r = new ArrayList<>();
					StringTokenizer st2 = new StringTokenizer(st.nextElement().toString(), ")(");
					while (st2.hasMoreElements()) {
						String element = st2.nextElement().toString();
						String attribute = element.substring(0, element.indexOf(' '));
						String value = element.substring(element.indexOf(' ') + 1);
						Record detail = new Record();
						if (attribute != null && !attribute.isEmpty()) {
							detail.setAttribute(attribute);
							detail.setValue(value);
							r.add(detail);
						}		
					}
					if (r != null && !r.isEmpty()) {
						r.add(new Record("Date", dish.get(1)));
						result.add(r);
					}
				}
			}
			
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
			return null;
		}
		return result;
	}
	
	public Record getInformation(long identifier) {
		Record result = new Record();
		
		try {
			DatabaseOperations databaseOperations = DatabaseOperationsImplementation.getInstance();
			
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
			return null;
		}
		return result;
	}
	
	public int getStockpile(long id) {
		DatabaseOperations databaseOperations = null;
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("stockpile");
			List<List<String>> content = databaseOperations.getTableContent(table, attributes,
					"id=\'" + id + "\'", null, null, null);
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
}
