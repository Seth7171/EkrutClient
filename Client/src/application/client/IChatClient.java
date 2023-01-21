package application.client;

public interface IChatClient {
    public String handleMessageFromServer(Object msg);
    public String handleMessageFromClientUI(Object message);
}
