package ro.pub.cs.aipi.roxysclothes.general;

public interface Constants {

	public final static String APPLICATION_NAME = "Roxy's Clothes";

	public final static String DATABASE_CONNECTION = "jdbc:mysql://localhost:3306/Roxys_Clothes";
	public final static String DATABASE_USERNAME = "root";
	public final static String DATABASE_PASSWORD = "waiting.for.you";
	public final static String DATABASE_NAME = "Roxys_Clothes";

	public final static boolean DEBUG = true;

	public final static long SEED = 20152015L;
	
	public final static String CLIENT_SERVLET_PAGE_CONTEXT = "ClientServlet";
	
	public final static String CLIENT_FORM = "client_form";

	public final static String USER_TYPE = "type";
	public final static String ADMINISTRATOR_TYPE = "administrator";
	public final static String CLIENT_TYPE = "client";
	
	public final static int USER_NONE = 0;
	public final static int USER_ADMINISTRATOR = 1;
	public final static int USER_CLIENT = 2;
	
	public final static String SIGNUP = "Sign up";
	public final static String SIGNIN = "Sign in";
	public final static String SIGNOUT = "Sign out";
	public final static String UPDATE = "Update";
	public final static String WELCOME_MESSAGE = "Welcome, ";
	public final static String DISPLAY = "display";
	public final static String INFO = "info";
	public final static String ACCOUNT = "account";
	public final static String HOME = "home";
	
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	
	public final static String INSERT_BUTTON_NAME = "Insert";
	public final static String UPDATE_BUTTON_NAME = "Update";
	public final static String DELETE_BUTTON_NAME = "Delete";
	
	public final static String OPERATION_TABLE_HEADER = "operation";

	public final static String INVALID_VALUE = "invalid";

	public final static String CURRENT_TABLE = "currentTable";

	public final static String RECORDS_PER_PAGE = "Records per Page ";
	public final static int RECORDS_PER_PAGE_VALUES[] = { 5, 10, 15, 20, 25, 40, 50, 60, 75, 80, 100 };
	public final static String PAGE = "Page ";

	public final static String SORT = "Sort: ";
	public final static String CURRENT_SORT = "currentSort";
	
	public final static String CATEGORY = "CATEGORY: ";
	public final static String CURRENT_CATEGORY = "currentCategory";
	
	public final static String LABELS_FILTER = "labelFilter";
	public final static String LABEL = "LABEL: ";
	public final static String CURRENT_LABEL = "currentLabel";
	public final static String LABEL_ATTRIBUTE = "label";
	
	public final static String SEARCH = "Search";
	
	public final static int ID_INDEX = 0;
	public final static String ID_ATTRIBUTE = "id";
	public final static String TYPE_ATTRIBUTE = "type";

	public final static String COPIES = "Copies";
	public final static String SHOPPING_CART = "Shopping Cart";
	public final static String ADD_TO_CART = "Add to Cart";
	public final static String COMPLETE_COMMAND = "Complete Command";
	public final static String CANCEL_COMMAND = "Cancel Command";
	public final static String ORDER_TOTAL = "Order Total: ";
	public final static String EMPTY_CART = "The shopping cart is empty!";


	public final static String INVALID_COMMAND_ERROR1 = "the order cannot be meet for the ";
	public final static String INVALID_COMMAND_ERROR2 = " (insufficient stock)";
	
	
	
	
	public final static String LOGIN_SERVLET_PAGE_CONTEXT = "LoginServlet";
	public final static String REGISTER_SERVLET_PAGE_CONTEXT = "RegisterServlet";
	public final static String ACCOUNT_SERVLET_PAGE_CONTEXT = "AccountServlet";
	public final static String NOT_LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT = "NotLoggedInClientServlet";
	public final static String LOGGED_IN_CLIENT_SERVLET_PAGE_CONTEXT = "LoggedInClientServlet";

	public final static String LOGIN_SERVLET_PAGE_NAME = "Authentication";
	public final static String ACCOUNT_SERVLET_PAGE_NAME = "Account Page";
	public final static String CLIENT_SERVLET_PAGE_NAME = "Food Catalog";

	public final static String LOGIN_FORM = "login_form";
	public final static String REGISTER_FORM = "register_form";
	public final static String ACCOUNT_FORM = "account_form";
	public final static String NOT_LOGGED_IN_CLIENT_FORM = "not_logged_in_client_form";
	public final static String LOGGED_IN_CLIENT_FORM = "logged_in_client_form";

	public final static String ERROR_USERNAME_PASSWORD = "Either username or password are incorrect!";
	public final static String ERROR_PERSONAL_IDENTIFIER = "The personal identifier is invalid!";
	public final static String ERROR_EXISTING_PERSONAL_IDENTIFIER = "The personal identifier is already in the database!";
	public final static String ERROR_EXISTING_USER = "A user with the same username and password already exists!";
	public final static String ERROR_EMAIL = "The email is invalid!";
	public final static String ERROR_PHONE_NUMBER = "The phone number is invalid!";
	public final static String ERROR_EMPTY_FIELDS = "All the fields are required!";
	
	public final static String PERSONAL_IDENTIFIER = "personalIdentifier";
	public final static String FIRST_NAME = "firstName";
	public final static String LAST_NAME = "lastName";
	public final static String ADDRESS = "address";
	public final static String PHONE_NUMBER = "phoneNumber";
	public final static String EMAIL = "email";
	public final static String IBAN = "iban";
	public final static String ORDER = "order";

	public final static int OPERATION_NONE = 0;
	public final static int OPERATION_INSERT = 1;
	public final static int OPERATION_UPDATE_PHASE1 = 2;
	public final static int OPERATION_UPDATE_PHASE2 = 3;
	public final static int OPERATION_DELETE = 4;
	public final static int OPERATION_LOGOUT = 5;
}