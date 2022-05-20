package com.narola.pharmacy.utility;

public class Constant {

	public static final String URL_CATEGORY_ADD_FORM = "";
	public static final String URL_CATEGORY_ADD_ACTION = "/AddCategoryAction";
	public static final String URL_CATEGORY_UPDATE_ACTION = "/updateCategoryAction";
	public static final String URL_CATEGORY_DELETE = "";

	public static final boolean POPULAR_TRUE = true;

	public static final String MEDICINE_IMG_FOLDER = "/images/medicines/";
	public static final String RESOURCE_IMG_FOLDER = "/images/resource/";
	public static final String URL_MEDICINE_ADD_ACTION = "/AddMedicineAction";
	public static final String URL_MEDICINE_ADD_FORM = "/AddMedicineForm";
	public static final String URL_MEDICINE_UPDATE_FORM = "/UpdateMedicineForm";
	public static final String URL_MEDICINE_UPDATE_ACTION = "/UpdateMedicineAction";
	public static final String URL_MEDICINE_DELETE_ACTION = "/RemoveMedicineAction";
	public static final String URL_MEDICINE_SINGLE_VIEW = "/ViewMedicineForm";

	public static final String URL_TEST_ADD_ACTION = "/AddTestAction";
	public static final String URL_TEST_UPDATE = "";
	public static final String URL_TEST_DELETE = "";

	public static final String URL_CUSTOMER_HOME = "/ShowCustomerHome";
	public static final String URL_CUSTOMER_CATEGORY = "/ShowCustomerCategory";
	public static final String URL_CUSTOMER_MEDICINE = "/ShowCustomerMedicine";
	public static final String URL_CUSTOMER_TEST = "/ShowCustomerTest";

	public static final String URL_ADMIN_HOME = "/ShowAdminHome";
	public static final String URL_SHOW_ALL_CATEGORY = "/ShowAllCategory";
	public static final String URL_SHOW_ALL_MEDICINE = "/ShowAllMedicine";
	public static final String URL_SHOW_ALL_TEST = "/ShowAllTest";

	public static final String ORDER_STATUS_NEW = "New";
	public static final String ORDER_STATUS_DISPATCHED = "Dispatched";
	public static final String ORDER_STATUS_IN_TRANSIT = "In-Transit";
	public static final String ORDER_STATUS_OUT_FOR_DELIVERY = "Out for Delivery";
	public static final String ORDER_STATUS_DELIVERED = "Delivered";
	public static final String ORDER_STATUS_CANCELLED = "Cancelled";

	public static final String TRNS_STATUS_INITIATED = "Initiated";
	public static final String TRNS_STATUS_IN_PROGRESS = "In Progress";
	public static final String TRNS_STATUS_SUCCESS = "Success";
	public static final String TRNS_STATUS_FAIL = "Fail";

	public static final int ORDER_DEFAULT_QUANTITY=1;
	public static final String RAZORPAY_KEY_ID="rzp_test_lE5sdzy5xBqJSJ";
	public static final String RAZORPAY_KEY_SECRET="LLCnm21B6uuja3WaqXdfyTPu";
	public static final String RAZORPAY_ID_SECRET_BASE64STRING="cnpwX3Rlc3RfbEU1c2R6eTV4QnFKU0o6TExDbm0yMUI2dXVqYTNXYXFYZGZ5VFB1";
	
	
	public static final String CONST_PHARMACY_DB_EXCEPTION_MESSAGE="Pharmacy Database Exception";
	public static final String CONST_CATEGORY_NAME="categoryName";
	public static final String CONST_ERROR_MESSAGE="errMsg";
	public static final String CONST_MED_ID="medId";
	public static final String CONST_ACTION="action";
	public static final String SUCCESS_FILE_UPLOAD=": File has been uploaded successfully!";
	
	
	public static final String CTRL_IMAGE_CONTROL="picturetxt";
	public static final String CTRL_MED_NAME="medNametxt";
	public static final String CTRL_MED_PRICE="medPricetxt";
	public static final String CTRL_MED_DISCOUNT="medDiscounttxt";
	public static final String CTRL_MED_MANUFACTURER="medManufacturertxt";
	public static final String CTRL_MED_DESCRIPTION="medDescriptiontxt";
	public static final String CTRL_MED_MFGDATE="medMfgDatetxt";
	public static final String CTRL_MED_EXPDATE="medExpDatetxt";
	public static final String CTRL_MED_QUANTITY="quantitytxt";
	
	public static final String ERR_MED_INSERT="Error occured while inserting medicine...";
	public static final String ERR_MED_MANAGE_POPULAR="Error occured while Managing popular medicine...";
	public static final String ERR_MED_UPDATE="Error occured while updating";
	public static final String ERR_MED_GET_ALL_MED="Error occured while Getting all medicine...";
	public static final String ERR_MED_SHOW_UPDATE_FORM="Error occured while Showing update medicine form...";
	public static final String ERR_SOMETHING_WENT_WRONG="Something went wrong";
}
