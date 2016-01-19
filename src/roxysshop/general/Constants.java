package roxysshop.general;

public interface Constants {

	public final static String APPLICATION_NAME = "Roxy's Shop";

	public final static String DATABASE_CONNECTION = "jdbc:mysql://localhost:3306/Roxys_Clothes";
	public final static String DATABASE_USERNAME = "root";
	public final static String DATABASE_PASSWORD = "waiting.for.you";
	public final static String DATABASE_NAME = "Roxys_Clothes";

	public final static boolean DEBUG = true;

	public final static long SEED = 20152015L;
	
	public final static String CLIENT_SERVLET_PAGE_CONTEXT = "ClientServlet";
	public final static String CLIENT_FORM = "client_form";

	public final static String LOGIN_SERVLET_PAGE_CONTEXT = "LoginServlet";
	public final static String REGISTER_SERVLET_PAGE_CONTEXT = "RegisterServlet";
	public final static String UPDATE_SERVLET_PAGE_CONTEXT = "UpdateServlet";
	public final static String HISTORY_SERVLET_PAGE_CONTEXT = "HistoryServlet";
	public final static String NOT_LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT = "NotLoggedInClientServlet";
	public final static String NOT_LOGGED_IN_PRODUCT_SERVLET_PAGE_CONTEXT = "NotLoggedInProductServlet";
	public final static String LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT = "LoggedInClientServlet";
	public final static String SHOPPING_CART_SERVLET_PAGE_CONTEXT = "ShoppingCartServlet";
	public final static String NOT_LOGGED_IN_SHOPPING_CART_SERVLET_PAGE_CONTEXT = "NotLoggedInShoppingCartServlet";
	public final static String PRODUCT_SERVLET_PAGE_CONTEXT = "ProductServlet";
	public final static String ADMINISTRATOR_SERVLET_PAGE_CONTEXT = "AdministratorServlet";
	public final static String INVOICES_HISTORY_SERVLET_PAGE_CONTEXT = "InvoicesHistoryServlet";
	public final static String PRODUCTS_SERVLET_PAGE_CONTEXT = "ProductsServlet";
	public final static String CLIENTS_SERVLET_PAGE_CONTEXT = "ClientsServlet";

	public final static String LOGIN_FORM = "login_form";
	public final static String REGISTER_FORM = "register_form";
	public final static String UPDATE_FORM = "update_form";
	public final static String HISTORY_FORM = "history_form";
	public final static String NOT_LOGGED_IN_CLIENT_FORM = "not_logged_in_client_form";
	public final static String NOT_LOGGED_IN_PRODUCT_FORM = "not_logged_in_product_form";
	public final static String LOGGED_IN_CLIENT_FORM = "logged_in_client_form";
	public final static String SHOPPING_CART_FORM = "shopping_cart_form";
	public final static String NOT_LOGGED_IN_SHOPPING_CART_FORM = "not_logged_in_shopping_cart_form";
	public final static String PRODUCT_FORM = "product_form";
	public final static String ADMINISTRATOR_FORM = "administrator_form";
	public final static String INVOICES_HISTORY_FORM = "invoices_history_form";
	public final static String PRODUCTS_FORM = "products_form";
	public final static String CLIENTS_FORM = "clients_form";
	
	public final static String SIGNUP = "Sign up";
	public final static String SIGNIN = "Sign in";
	public final static String SIGNOUT = "Sign out";
	public final static String UPDATE = "Update";
	public final static String HISTORY = "History";
	public final static String WELCOME_MESSAGE = "Welcome, ";
	public final static String DISPLAY = "display";
	public final static String INFO = "info";
	public final static String ACCOUNT = "account";
	public final static String HOME = "home";
	public final static String CLIENTS = "clients";
	public final static String PRODUCTS = "products";
	public final static String ISSUE = "issue";
	
	
	public final static String ERROR_USERNAME_PASSWORD = "Either username or password are incorrect!";
	public final static String ERROR_PERSONAL_IDENTIFIER = "The personal identifier is invalid!";
	public final static String ERROR_EXISTING_PERSONAL_IDENTIFIER = "The personal identifier is already in the database!";
	public final static String ERROR_EXISTING_USER = "A user with the same username and password already exists!";
	public final static String ERROR_EMAIL = "The email is invalid!";
	public final static String ERROR_PHONE_NUMBER = "The phone number is invalid!";
	public final static String ERROR_EMPTY_FIELDS = "All the fields are required!";
	
	public final static String USER_TYPE = "type";
	public final static String ADMINISTRATOR_TYPE = "administrator";
	public final static String CLIENT_TYPE = "client";

	public final static String FIRST_NAME = "first_name";
	public final static String LAST_NAME = "last_name";
	public final static String PHONE_NUMBER = "phone_number";
	public final static String EMAIL = "email";
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	
	public final static String ADDRESS = "address";
	public final static String COUNTY = "county";
	public final static String CITY = "city";
	public final static String STREET = "street";
	public final static String POSTAL_CODE = "postal_code";
	
	public final static String ORDER = "order";

	public final static int USER_NONE = 0;
	public final static int USER_CLIENT = 1;
	public final static int USER_ADMINISTRATOR = 2;

	public final static int OPERATION_NONE = 0;
	public final static int OPERATION_INSERT = 1;
	public final static int OPERATION_UPDATE_PHASE1 = 2;
	public final static int OPERATION_UPDATE_PHASE2 = 3;
	public final static int OPERATION_DELETE = 4;
	public final static int OPERATION_LOGOUT = 5;

	public final static String INSERT_BUTTON_NAME = "Insert";
	public final static String UPDATE_BUTTON_NAME = "Update";
	public final static String DELETE_BUTTON_NAME = "Delete";
	public final static String SELECT_BUTTON_NAME = "Select";

	public final static String OPERATION_TABLE_HEADER = "operation";

	public final static String INVALID_VALUE = "invalid";

	public final static String CURRENT_TABLE = "currentTable";

	public final static String RECORDS_PER_PAGE = "Records per Page ";
	public final static int RECORDS_PER_PAGE_VALUES[] = { 5, 10, 15, 20, 25, 40, 50, 60, 75, 80, 100 };
	public final static String PAGE = "Page ";

	public final static String PRODUCT = "product";
	public final static String SEARCH = "search";
	
	public final static String SORT = "Sort: ";
	public final static String CURRENT_SORT = "currentSort";
	
	public final static String SIZE = "Size: ";
	public final static String CURRENT_SIZE = "currentSize";
	
	public final static String CATEGORY_FILTER = "categoryFilter";
	public final static String CATEGORY = "CATEGORY: ";
	public final static String CURRENT_CATEGORY = "currentCategory";
	public final static String CATEGORY_ATTRIBUTE = "type";
	
	public final static String LABELS_FILTER = "labelFilter";
	public final static String LABEL = "LABEL: ";
	public final static String CURRENT_LABEL = "currentLabel";
	public final static String LABEL_ATTRIBUTE = "label";

	public final static int ID_INDEX = 0;
	public final static String ID_ATTRIBUTE = "id";

	public final static String COPIES = "Copies";
	public final static String SHOPPING_CART = "Shopping Cart";
	public final static String ADD_TO_CART = "Add to Cart";
	public final static String COMPLETE_COMMAND = "Complete Command";
	public final static String CANCEL_COMMAND = "Cancel Command";
	public final static String ORDER_TOTAL = "Order Total: ";
	public final static String EMPTY_CART = "The shopping cart is empty!";


	public final static String INVALID_COMMAND_ERROR1 = "the order cannot be meet for the ";
	public final static String INVALID_COMMAND_ERROR2 = " (insufficient stock)!";
	public final static String INVALID_COMMAND_ERROR3 = "please select an address!";

}
