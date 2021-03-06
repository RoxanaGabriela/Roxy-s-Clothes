package roxysshop.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import roxysshop.dataaccess.DatabaseException;
import roxysshop.dataaccess.DatabaseOperations;
import roxysshop.dataaccess.DatabaseOperationsImplementation;
import roxysshop.general.Constants;
import roxysshop.helper.Record;

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
			attributes.add("i.date");
			attributes.add("GROUP_CONCAT(CONCAT(p.name, ' x ', id.quantity, ' = ', p.price, ' ', p.currency, ';'))");
			attributes.add("a.address");
			attributes.add("i.issued");
			String table = new String("invoice i, invoice_details id, user u, product p, address a");
			String whereClause = new String("i.id=id.invoice_id AND u.id=i.user_id AND p.id=id.product_id AND a.id=i.address_id AND u.id=\'" + userId + "\'");
			String groupByClause = new String("i.id");
			String orderByClause = new String("i.date");
			List<List<String>> invoices = databaseOperations.getTableContent(table, attributes, whereClause, null, orderByClause, groupByClause);
			
			for (List<String> invoice : invoices) {
				List<Record> r = new ArrayList<>();
				r.add(new Record("Invoice id", invoice.get(0)));
				r.add(new Record("Date", invoice.get(1)));
				r.add(new Record("Address", invoice.get(3)));
				if (Integer.parseInt(invoice.get(4)) == 0) r.add(new Record("Issued", "false"));
				else r.add(new Record("Issued", "true"));
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
	
	public List<List<Record>> getAllInvoices() {
		List<List<Record>> result = new ArrayList<>();
		DatabaseOperations databaseOperations = null;
		try {			
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("i.id");
			attributes.add("i.date");
			attributes.add("GROUP_CONCAT(CONCAT(p.name, ' x ', id.quantity, ' = ', p.price, ' ', p.currency, ';'))");
			attributes.add("a.address");
			attributes.add("i.issued");
			String table = new String("invoice i, invoice_details id, user u, product p, address a");
			String whereClause = new String("i.id=id.invoice_id AND u.id=i.user_id AND p.id=id.product_id AND a.id=i.address_id");
			String groupByClause = new String("i.id");
			String orderByClause = new String("i.date");
			List<List<String>> invoices = databaseOperations.getTableContent(table, attributes, whereClause, null, orderByClause, groupByClause);
			
			for (List<String> invoice : invoices) {
				List<Record> r = new ArrayList<>();
				r.add(new Record("Invoice id", invoice.get(0)));
				r.add(new Record("Date", invoice.get(1)));
				r.add(new Record("Address", invoice.get(3)));
				if (Integer.parseInt(invoice.get(4)) == 0) r.add(new Record("Issued", "false"));
				else r.add(new Record("Issued", "true"));
				
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
	
	public List<List<Record>> getNotIssuedInvoices() {
		List<List<Record>> result = new ArrayList<>();
		DatabaseOperations databaseOperations = null;
		try {			
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("i.id");
			attributes.add("i.date");
			attributes.add("GROUP_CONCAT(CONCAT(p.name, ' x ', id.quantity, ' = ', p.price, ' ', p.currency, ';'))");
			attributes.add("a.address");
			String table = new String("invoice i, invoice_details id, user u, product p, address a");
			String whereClause = new String("i.issued=\'0\' AND i.id=id.invoice_id AND u.id=i.user_id AND p.id=id.product_id AND a.id=i.address_id");
			String groupByClause = new String("i.id");
			String orderByClause = new String("i.date");
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
	
	public boolean issueInvoice(long id) {
		boolean issued = false;
		DatabaseOperations databaseOperations = null;
		try {			
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> attributes = new ArrayList<>();
			attributes.add("issued");

			List<String> values = new ArrayList<>();
			values.add("1");
			int result = databaseOperations.updateRecordsIntoTable(table, attributes, values, "id=\'" + id + "\'");
			if (result != -1) issued = true;
			
		} catch (SQLException | DatabaseException e) {
			System.out.println("An exception has occurred: " + e.getMessage());
			if (Constants.DEBUG) {
				e.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		
		return issued;
	}

}
