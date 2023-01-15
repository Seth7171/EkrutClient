package application.client;

/**
 * The MessageHandler class holds and manages the message 
 * and data fields for the client side of the application.
 * @author Lior
 */
public class MessageHandler {
    /**
     * The message variable holds the message that is to be sent or received by the client.
     */
    private static String message = null;
    /**
     * The data variable holds any additional data that is to be sent or received along with the message.
     */
    private static Object data = null;

    /**
     * Returns the current message.
     * 
     * @return The current message.
     */
    public static String getMessage() {
        return message;
    }
    
    /**
     * Sets the message to a new value.
     * 
     * @param message The new value for the message.
     */
    public static void setMessage(String message) {
        MessageHandler.message = message;
    }

    /**
     * Returns the current data.
     * 
     * @return The current data.
     */
    public static Object getData() {
        return data;
    }

    /**
     * Sets the data to a new value.
     * 
     * @param data The new value for the data.
     */
    public static void setData(Object data) {
        MessageHandler.data = data;
    }
}
