package application.client;

public class MessageHandler {
    private static String message = null;
    private static Object data = null;

    public static String getMessage() {
        return message;
    }
    public static void setMessage(String message) {
        MessageHandler.message = message;
    }

    public static Object getData() {
        return data;
    }

    public static void setData(Object data) {
        MessageHandler.data = data;
    }
}
