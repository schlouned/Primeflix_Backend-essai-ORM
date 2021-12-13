package com.BackPrimeflix.constants;

public class ResponseCode {

	// Response
	public static final String SUCCESS_CODE = "200";
	public static final String SUCCESS_MESSAGE = "SUCCESS";
	public static final String FAILURE_CODE = "500";
	public static final String FAILURE_MESSAGE = "ERROR";
	public static final String BAD_REQUEST_CODE = "400";
	public static final String BAD_REQUEST_CODE_ERROR_TOKEN = "498";
	public static final String BAD_REQUEST_MESSAGE = "BAD_REQUEST";

	// CLIENT
	public static final String USER_HAS_NO_TOKEN = "USER_HAS_NO_TOKEN";
	public static final String USER_HAS_INVALID_TOKEN = "USER_HAS_INVALID_TOKEN";
	public static final String USER_TOKEN_OK = "USER_TOKEN_OK";
	public static final String USER_INFO_MISSING = "USER_INFO_MISSING";
	public static final String USER_INFO_UPDATED = "USER_INFO_UPDATED";
	public static final String USER_INFO_OK = "USER_INFO_OK";

	//errorObject
	public static final String USER_NOT_COMPLETED = "USER_NOT_COMPLETED";
	public static final String USER_NOT_COMPLETED_MESSAGE = "User information are not fully completed!";
	public static final String USER_ALREADY_EXIST = "USER_ALREADY_EXIST";
	public static final String USER_ALREADY_EXIST_MESSAGE = "This email is already used!";
	public static final String USER_REG = "USER_REGISTERED";
	public static final String USER_REG_MESSAGE = "The user is successfully registered!";
	public static final String USER_NOT_VERIFIED = "USER_NOT_VERIFIED";
	public static final String USER_NOT_VERIFIED_MESSAGE = "The user is not verified!";
	public static final String USER_VERIFIED = "USER_VERIFIED";
	public static final String USER_VERIFIED_MESSAGE = "The user is verified!";
	public static final String USER_VERIFIED_ACCOUNT_PROBLEM = "USER_VERIFIED_ACCOUNT_PROBLEM";
	public static final String USER_VERIFIED_ACCOUNT_PROBLEM_MESSAGE = "An error occurred while verifying account, please check details or try again!";
	public static final String USER_CREATING_ACCOUNT_PROBLEM = "USER_CREATING_ACCOUNT_PROBLEM";
	public static final String USER_CREATING_ACCOUNT_PROBLEM_MESSAGE = "An error occurred while creating account!";
	public static final String USER_LOGGED_OUT = "USER_LOGGED_OUT";
	public static final String USER_LOGGED_OUT_MESSAGE = "The user is logged out!";
	public static final String USER_NOT_EXIST = "USER_NOT_EXIST";
	public static final String USER_NOT_EXIST_MESSAGE = "This user don't exist!";
	public static final String USER_ACCOUNT_UPDATED = "USER_ACCOUNT_UPDATED";
	public static final String USER_ACCOUNT_UPDATED_MESSAGE = "The user account has been successfully updated!";
	public static final String USER_ACCOUNT_UPDATE_PROBLEM = "USER_ACCOUNT_UPDATE_PROBLEM";
	public static final String USER_ACCOUNT_UPDATE_PROBLEM_MESSAGE = "Problem when updating account info!";
	public static final String USER_ACCOUNT_GET_PROBLEM = "USER_ACCOUNT_GET_PROBLEM";
	public static final String USER_ACCOUNT_GET_PROBLEM_MESSAGE = "Problem when getting account info!";
	public static final String USER_FACEBOOK_LOGIN_PROBLEM = "USER_FACEBOOK_LOGIN_PROBLEM";
	public static final String USER_FACEBOOK_LOGIN_PROBLEM_MESSAGE = "Problem when facebook login!";
	public static final String USER_CREDENTIAL_INVALID = "USER_CREDENTIAL_INVALID";
	public static final String USER_CREDENTIAL_INVALID_MESSAGE = "The user credential is not valid!";

	public static final String MOVIES_GETALL_SUCCESS = "MOVIES_GETALL_SUCCESS";
	public static final String MOVIES_GETALL_SUCCESS_MESSAGE = "Recover all movies successfully!";
	public static final String MOVIES_GETALL_ERROR = "MOVIES_GETALL_ERROR";
	public static final String MOVIES_GETALL_ERROR_MESSAGE = "Recover all movies error!";
	public static final String MOVIES_GETFILTER_SUCCESS = "MOVIES_GETFILTER_SUCCESS";
	public static final String MOVIES_GETFILTER_SUCCESS_MESSAGE = "Recover movies with filter successfully!";
	public static final String MOVIES_GETFILTER_ERROR = "MOVIES_GETFILTER_ERROR";
	public static final String MOVIES_GETFILTER_ERROR_MESSAGE = "Recover movies with filter error!";
	public static final String MOVIES_GETMOVIECATEGORY_SUCCESS = "MOVIES_GETMOVIECATEGORY_SUCCESS";
	public static final String MOVIES_GETMOVIECATEGORY_SUCCESS_MESSAGE = "Recover movies with category filter successfully!";
	public static final String MOVIES_GETMOVIECATEGORY_ERROR = "MOVIES_GETMOVIECATEGORY_ERROR";
	public static final String MOVIES_GETMOVIECATEGORY_ERROR_MESSAGE = "Recover movies with category filter error!";

	public static final String CART_STOCK_0_ERROR = "CART_STOCK_0_ERROR";
	public static final String CART_STOCK_0_ERROR_MESSAGE = "The stock is 0!";
	public static final String CART_ADD_MOVIE_SUCCESS = "CART_ADD_MOVIE_SUCCESS";
	public static final String CART_ADD_MOVIE_SUCCESS_MESSAGE = "Movie successfully add to cart!";
	public static final String CART_ADD_MOVIE_ERROR = "CART_ADD_MOVIE_ERROR";
	public static final String CART_ADD_MOVIE_ERROR_MESSAGE = "An error occurred while adding movie to the cart!";

	public static final String GET_CATEGORY_SUCCESS = "GET_CATEGORY_SUCCESS";
	public static final String GET_CATEGORY_SUCCESS_MESSAGE = "Categories successfully recovered!";
	public static final String GET_CATEGORY_ERROR = "GET_CATEGORY_ERROR";
	public static final String GET_CATEGORY_ERROR_MESSAGE = "An error occurred while recovering categories!";

	public static final String SAVE_DISCOUNT_SUCCESS = "SAVE_DISCOUNT_SUCCESS";
	public static final String SAVE_DISCOUNT_SUCCESS_MESSAGE = "Discount successfully saved!";
	public static final String SAVE_DISCOUNT_ERROR = "SAVE_DISCOUNT_ERROR";
	public static final String SAVE_DISCOUNT_ERROR_MESSAGE = "An error occurred while saving discount!";
	public static final String OVERLAP_DISCOUNT_ERROR = "OVERLAP_DISCOUNT_ERROR";
	public static final String OVERLAP_DISCOUNT_ERROR_MESSAGE = "overlap from the new discount on a former one!";

	public static final String DELETE_DISCOUNT_SUCCESS = "DELETE_DISCOUNT_SUCCESS";
	public static final String DELETE_DISCOUNT_ERROR = "DELETE_DISCOUNT_ERROR";

	public static final String GET_CART_SUCCESS = "GET_CART_SUCCESS";
	public static final String GET_CART_SUCCESS_MESSAGE = "Cart successfully returned!";
	public static final String GET_CART_ERROR = "GET_CART_ERROR";
	public static final String GET_CART_ERROR_MESSAGE = "An error occurred while getting cart!";

	public static final String CARTITEM_UPDATE_SUCCESS = "CARTITEM_UPDATE_SUCCESS";
	public static final String CARTITEM_UPDATE_SUCCESS_MESSAGE = "Cart item successfully updated!";
	public static final String CARTITEM_UPDATE_ERROR = "CARTITEM_UPDATE_ERROR";
	public static final String CARTITEM_UPDATE_ERROR_MESSAGE = "An error occurred while updating cart item!";
	public static final String CARTITEM_UPDATE_STOCK_ERROR = "CARTITEM_UPDATE_STOCK_ERROR";
	public static final String CARTITEM_UPDATE_STOCK_ERROR_MESSAGE = "The quantity of your cart item is superior to the stock quantity!";

	public static final String CARTITEM_DELETED_SUCCESS = "CARTITEM_DELETED_SUCCESS";
	public static final String CARTITEM_DELETED_SUCCESS_MESSAGE = "Cart item successfully deleted!";
	public static final String CARTITEM_DELETED_ERROR = "CARTITEM_DELETED_ERROR";
	public static final String CARTITEM_DELETED_ERROR_MESSAGE = "An error occurred while deleting cart item!";

	public static final String GET_COMPANY_SUCCESS = "GET_COMPANY_SUCCESS";
	public static final String GET_COMPANY_SUCCESS_MESSAGE = "Company address recovered successfully!";
	public static final String GET_COMPANY_ERROR = "GET_COMPANY_ERROR";
	public static final String GET_COMPANY_ERROR_MESSAGE = "An error occurred while recovering company address!";

	public static final String ORDER_SUCCESS = "ORDER_SUCCESS";
	public static final String ORDER_SUCCESS_MESSAGE = "Order created successfully!";
	public static final String ORDER_ERROR = "ORDER_ERROR";
	public static final String ORDER_ERROR_MESSAGE = "An error occurred while creating order!";

	public static final String GET_ORDER_SUCCESS = "GET_ORDER_SUCCESS";
	public static final String GET_ORDER_SUCCESS_MESSAGE = "Order recovered successfully!";
	public static final String GET_ORDER_ERROR = "GET_ORDER_ERROR";
	public static final String GET_ORDER_ERROR_MESSAGE = "An error occurred while recovering order!";

	public static final String DELETE_ORDER_SUCCESS = "DELETE_ORDER_SUCCESS";
	public static final String DELETE_ORDER_SUCCESS_MESSAGE = "Order deleted successfully!";
	public static final String DELETE_ORDER_ERROR = "DELETE_ORDER_ERROR";
	public static final String DELETE_ORDER_ERROR_MESSAGE = "An error occurred while deleting order!";

	public static final String ORDER_STATUS_PAYED_SUCCESS = "ORDER_STATUS_PAYED_SUCCESS";
	public static final String ORDER_STATUS_PAYED_SUCCESS_MESSAGE = "Order status PAYED successfully!";
	public static final String ORDER_STATUS_PAYED_ERROR = "ORDER_STATUS_PAYED_ERROR";
	public static final String ORDER_STATUS_PAYED_ERROR_MESSAGE = "An error occurred while changing order status to PAYED!";

	public static final String INVENTORY_SAVED_SUCCESS = "INVENTORY_SAVED_SUCCESS";
	public static final String INVENTORY_SAVED_SUCCESS_MESSAGE = "Inventory saved successfully!";
	public static final String INVENTORY_SAVED_ERROR = "INVENTORY_SAVED_ERROR";
	public static final String INVENTORY_SAVED_ERROR_MESSAGE = "An error occurred while saving inventory!";

	public static final String ORDER_STATUS_CHANGED_SUCCESS = "ORDER_STATUS_CHANGED_SUCCESS";
	public static final String ORDER_STATUS_CHANGED_SUCCESS_MESSAGE = "Order status changed successfully!";
	public static final String ORDER_STATUS_CHANGED_ERROR = "ORDER_STATUS_CHANGED_ERROR";
	public static final String ORDER_STATUS_CHANGED_ERROR_MESSAGE = "An error occurred while changing order status!";

	public static final String PERSON_CRUD_SUCCESS = "PERSON_CRUD_SUCCESS";
	public static final String PERSON_CRUD_SUCCESS_MESSAGE = "person CRUD action executed successfully!";
	public static final String PERSON_CRUD_ERROR = "PERSON_CRUD_ERROR";
	public static final String PERSON_CRUD_ERROR_MESSAGE = "An error occurred while CRUD operation on a person!";

	public static final String ADDRESS_DONT_EXIST = "ADDRESS_DONT_EXIST";
	public static final String ADDRESS_DONT_EXIST_MESSAGE = "The delivery address don't exist!";


	//Exception code
	/*public static final String EX_USER_CREATING_ACCOUNT_BY_EMAIL = "EX_USER_CREATING_ACCOUNT_BY_EMAIL";
	public static final String EX_USER_VERIFY_ACCOUNT_BY_EMAIL = "EX_USER_VERIFY_ACCOUNT_BY_EMAIL";
	public static final String EX_USER_LOGIN_BY_EMAIL= "EX_USER_LOGIN_BY_EMAIL";
	public static final String EX_USER_LOGIN_BY_FACEBOOK = "EX_USER_LOGIN_BY_FACEBOOK";
	public static final String EX_USER_LOGOUT= "EX_USER_LOGOUT";
*/

}
