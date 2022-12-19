package application.client;

public class MessageHandler {
    private static String message = null;

    public static String getMessage() {
        return message;
    }
    public static void setMessage(String message) {
        MessageHandler.message = message;
    }
}
