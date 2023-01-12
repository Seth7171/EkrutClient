package common.connectivity;

import java.io.Serializable;

/**
 * @author Lior Jigalo
 */
public enum MessageFromClient implements Serializable {
    REQUEST_ALL_MACHINES_ORDERS_MONTHLY_REPORT, // V
    REQUEST_ORDER_BY_ORDER_ID_AND_CUSTOMER_ID,  // V
    REQUEST_UPDATE_USERS_STATUSES,              // V
    REQUEST_UPDATE_DEALS,                       // V
    REQUEST_CUSTOMER_DATA,                      // TODO
    REQUEST_DISCOUNT_LIST,                      // TODO
    REQUEST_UPDATE_ORDER_STATUS,                // TODO
    REQUEST_IMPORT_USERS,
    REQUEST_OPERATIONS_EMPLOYEE_DATA,
    REQUEST_UPDATE_CUSTOMER_STATUS,
    REQUEST_UPDATE_MULTIPLE_ORDER_STATUSES,     // V
    REQUEST_ORDERS_BY_CUSTOMER_ID,
    REQUEST_ALL_CUSTOMER_DATA,                  // TODO
    REQUEST_ORDERS_BY_AREA,
    REQUEST_GENERATE_MONTHLY_INVENTORY_REPORT,  // V
    REQUEST_MACHINE_MONTHLY_INVENTORY_REPORT,   // V
    REQUEST_ADD_NEW_PRODUCT_TO_PRODUCT_TABLE,   // V
    REQUEST_ADD_NEW_PRODUCT_TO_WAREHOUSE,       // V
    REQUEST_CREDIT_CARD_CHECK,                  // V
    REQUEST_CHECK_IF_SUB,                       // V
    REQUEST_CUSTOMERS_FROM_USER_TABLE,          // V
    REQUEST_ADD_NEW_PRODUCT_TO_MACHINE,         // V
    REQUEST_UPDATE_WAREHOUSE_PRODUCTS,          // V
    REQUEST_SET_FIRST_TIME_BUY_AS_SUB,          // TODO
    REQUEST_MACHINE_INVENTORY_REPORT,           // V
    REQUEST_UPDATE_MACHINE_PRODUCTS,            // V
    REQUEST_UPDATE_MACHINE_PRODUCT_AMOUNT,      // V
    REQUEST_ALL_MACHINE_LOCATIONS,              // V
    REQUEST_ALL_MACHINE_PRODUCTS,               // V
    REQUEST_ASSIGN_EMPLOYEE_TO_REFILL_ORDER,    // V
    REQUEST_WAREHOUSE_PRODUCTS,                 // V
    REQUEST_DISCONNECT_CLIENT,                  // V
    REQUEST_LOCATION_PRODUCTS,                  // V
    REQUEST_MACHINE_PRODUCTS,                   // V
    REQUEST_REFILL_ORDERS,                      // V
    REQUEST_CLIENT_REPORT,                      // V
    REQUEST_ADD_NEW_ORDER,                      // V
    REQUEST_ALL_PRODUCTS,                       // V
    REQUEST_UPDATE_USER,                        // TODO
    REQUEST_TABLE_ORDER,                        // TODO
    REQUEST_DELETE_USER,                        // V
    REQUEST_MACHINE_IDS,                        // V
    REQUEST_TABLE_USER,                         // TODO
    REQUEST_DISCONNECT,                         // V
    REQUEST_ADD_USER,                           // V
    REQUEST_LOGOUT,                             // V
    REQUEST_LOGIN,                              // V
    UNKNOWN
}
