package gui.UserScreens;


import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import application.client.ChatClient;
import application.user.UserController;
import javafx.event.ActionEvent;
import javafx.event.Event;

class LogInScreenControllerTest {
   private LogInScreenController mocklogInScreenController;
   private ActionEvent event;
   private Method loginTest;
   

	@BeforeEach
	public void setUp() throws NoSuchMethodException, SecurityException {
		mocklogInScreenController = spy(new LogInScreenController());
	}

  // Check Functionality: check login with null credentials
  // Input Data: (null) getUsernameAndPassword()
  // Expected Result: "FillAllUserCredentials"
  @Test
  void TestloginNullCredentials() throws Exception {
	doReturn(null).when(mocklogInScreenController).getUsernameAndPassword();
	String expected = "FillAllUserCredentials";
	String result = mocklogInScreenController.logIn(event);
	Assertions.assertEquals(expected, result);
   }
  
  //Check functionality: check login with wrong credentials
  //Input data: (ArrayList) credentials with wrong username and password
  //Expected result: "WorngUserCredentials"
  @Test
  void TestloginWorngCredentials() throws Exception {
	ArrayList<String> credentials = new ArrayList<>();
	credentials.add("WORNGusername");
	credentials.add("WORNGpassword");
	doReturn(credentials).when(mocklogInScreenController).getUsernameAndPassword();
	doReturn(false).when(mocklogInScreenController).requestLogIn(credentials);
	String expected = "WorngUserCredentials";
	String result = mocklogInScreenController.logIn(event);
	Assertions.assertEquals(expected, result);
   }
  
  //Check functionality: check if the user is not OL and not customer or subscriber
  //Input data: username: NOTCustomerOrSubscriberusername, password: password
  //Expected result: "Unauthorized account"
  @Test
  void TestloginNOTOLNOTCustomerORSubscriber() throws Exception {
	ArrayList<String> credentials = new ArrayList<>();
	credentials.add("NOTCustomerOrSubscriberusername");
	credentials.add("password");
	doReturn(credentials).when(mocklogInScreenController).getUsernameAndPassword();
	doReturn(true).when(mocklogInScreenController).requestLogIn(credentials);
	doReturn(true).when(mocklogInScreenController).requestLogOut(credentials);
	doReturn(false).when(mocklogInScreenController).isOL();
	doReturn("NOT Customer OR Subscriber").when(mocklogInScreenController).getUserDepartment();
	String expected = "Unauthorized account";
	String result = mocklogInScreenController.logIn(event);
	Assertions.assertEquals(expected, result);
   }
  
  //Check functionality: check if the user is not OL and a customer
  //Input data: username: Customerusername, password: password
  //Expected result: "LoggedIn"
  @Test
  void TestloginNOTOLCustomer() throws Exception {
	ArrayList<String> credentials = new ArrayList<>();
	credentials.add("Customerusername");
	credentials.add("password");
	doReturn(credentials).when(mocklogInScreenController).getUsernameAndPassword();
	doReturn(true).when(mocklogInScreenController).requestLogIn(credentials);
	doReturn(true).when(mocklogInScreenController).requestLogOut(credentials);
	doReturn(false).when(mocklogInScreenController).isOL();
	doReturn("customer").when(mocklogInScreenController).getUserDepartment();
	doReturn(true).when(mocklogInScreenController).loadUserHomeScreen(event);
	String expected = "LoggedIn";
	String result = mocklogInScreenController.logIn(event);
	Assertions.assertEquals(expected, result);
   }
  
  //Check functionality: check if the user is not OL and a subscriber
  //Input data: username: Subscriberusername, password: password
  //Expected result: "LoggedIn"
  @Test
  void TestloginNOTOLSubscriber() throws Exception {
	ArrayList<String> credentials = new ArrayList<>();
	credentials.add("Subscriberusername");
	credentials.add("password");
	doReturn(credentials).when(mocklogInScreenController).getUsernameAndPassword();
	doReturn(true).when(mocklogInScreenController).requestLogIn(credentials);
	doReturn(true).when(mocklogInScreenController).requestLogOut(credentials);
	doReturn(false).when(mocklogInScreenController).isOL();
	doReturn("subscriber").when(mocklogInScreenController).getUserDepartment();
	doReturn(true).when(mocklogInScreenController).loadUserHomeScreen(event);
	String expected = "LoggedIn";
	String result = mocklogInScreenController.logIn(event);
	Assertions.assertEquals(expected, result);
   }
  
  //Check functionality: check successful OL login
  //Input data: username: username, password: password
  //Expected result: "LoggedIn"
  @Test
  void TestloginOL() throws Exception {
	ArrayList<String> credentials = new ArrayList<>();
	credentials.add("username");
	credentials.add("password");
	doReturn(credentials).when(mocklogInScreenController).getUsernameAndPassword();
	doReturn(true).when(mocklogInScreenController).requestLogIn(credentials);
	doReturn(true).when(mocklogInScreenController).requestLogOut(credentials);
	doReturn(true).when(mocklogInScreenController).isOL();
	doReturn(true).when(mocklogInScreenController).loadUserHomeScreen(event);
	String expected = "LoggedIn";
	String result = mocklogInScreenController.logIn(event);
	Assertions.assertEquals(expected, result);
   }
  
}
