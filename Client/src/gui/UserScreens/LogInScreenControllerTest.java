package gui.UserScreens;


import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javafx.event.ActionEvent;

class LogInScreenControllerTest {
   private LogInScreenController mocklogInScreenController;
   private ActionEvent event;
	private Method loginTest;

	@BeforeEach
	public void setUp() throws NoSuchMethodException, SecurityException {
		loginTest = LogInScreenController.class.getDeclaredMethod("logIn", ActionEvent.class);
		loginTest.setAccessible(true);
		mocklogInScreenController = Mockito.mock(LogInScreenController.class);
		//mocklogInScreenController = new LogInScreenController();
	}

  @Test
  void loginTesjtForNotExistUser() throws Exception {
	//mocklogInScreenController.setUserNameField("lior");
	//mocklogInScreenController.setPasswordField("123");
	doNothing().when(mocklogInScreenController).requestLogIn(new ArrayList<String>());
	doNothing().when(mocklogInScreenController).requestLogOut(new ArrayList<String>());
	doNothing().when(mocklogInScreenController).loadUserHomeScreen(event);
	doNothing().when(this.mocklogInScreenController).requestLogIn(new ArrayList<String>());
	//((LogInScreenController)Mockito.doNothing().when(this.logInScreenController)).requestLogOut(new ArrayList<String>());
	//UserController userController = new UserController();
	String result = mocklogInScreenController.logIn(event);
	String expected = "FillAllUserCredentials";
	//((LogInScreenController)Mockito.verify(this.logInScreenController, Mockito.atLeastOnce())).setErrorTxtUserNotExists();
	Assertions.assertEquals(expected, result);
   }
}
