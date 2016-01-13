package ro.pub.cs.aipi.roxysclothes.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ro.pub.cs.aipi.roxysclothes.dataaccess.DatabaseOperations;
import ro.pub.cs.aipi.roxysclothes.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.aipi.roxysclothes.general.Constants;
import ro.pub.cs.aipi.roxysclothes.helper.Record;

public class InvoiceManager extends EntityManager {

	public InvoiceManager() {
		table = "invoice";
	}
	
	public List<List<Record>> getInvoices(long userId) {
		List<List<Record>> result = new ArrayList<>();
		DatabaseOperations databaseOperations = null;
		try {			
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("i.id");
			attributes.add("i.invoice_date");
			attributes.add("GROUP_CONCAT(CONCAT(d.name, ' x ', id.no, ' = ', d.price, ';'))");
			attributes.add("a.address");
			String table = new String("invoice i, invoice_details id, user u, dish d, address a");
			String whereClause = new String("i.id=id.invoice_id AND u.id=i.user_id AND d.id=id.dish_id AND a.id=i.address_id AND u.id=\'" + userId + "\'");
			String groupByClause = new String("i.id");
			String orderByClause = new String("i.id");
			List<List<String>> invoices = databaseOperations.getTableContent(table, attributes, whereClause, null, orderByClause, groupByClause);
			
			for (List<String> invoice : invoices) {
				List<Record> r = new ArrayList<>();
				r.add(new Record("Invoice id", invoice.get(0)));
				r.add(new Record("Date", invoice.get(1)));
				r.add(new Record("Address", invoice.get(3)));
				StringTokenizer st = new StringTokenizer(invoice.get(2), ";,");
				int i = 1;
				while (st.hasMoreElements()) {
					String attribute = i + "";
					i++;
					String value = st.nextElement().toString();
					Record detail = new Record();
					if (value != null && !value.isEmpty()) {
						detail.setAttribute(attribute);
						detail.setValue(value);
						r.add(detail);
					}		
				}
				if (r != null && !r.isEmpty()) {
					result.add(r);
				}
			}
			
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
			return null;
		} finally {
			databaseOperations.releaseResources();
		}
		
		return result;
	}

}
