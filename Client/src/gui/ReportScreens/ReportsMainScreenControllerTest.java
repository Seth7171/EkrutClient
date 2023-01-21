package gui.ReportScreens;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import application.client.ChatClient;
import application.user.UserController;
import gui.ScreenController;
import gui.UserScreens.LogInScreenController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportsMainScreenControllerTest {
	   private ReportsMainScreenController mockReportsMainScreenController;
	   private MouseEvent event;
	@BeforeEach
	void setUp() throws Exception {
		mockReportsMainScreenController = spy(new ReportsMainScreenController());
	}

	  // Check Functionality: 
	  // Input Data: 
	  // Expected Result: 
	  @Test
	  void TestclickShowReportNULLMonth() {
		doReturn(null).when(mockReportsMainScreenController).getMonth();
		doNothing().when(mockReportsMainScreenController).alertHandler(anyString(), anyBoolean());
		String expected = "You didn't choose Month!";
		String result = mockReportsMainScreenController.clickShowReport(event);
		Assertions.assertEquals(expected, result);
	   }
	  
	  // Check Functionality: 
	  // Input Data: 
	  // Expected Result: 
	  @Test
	  void TestclickShowReportNULLYear() {
		doReturn("10").when(mockReportsMainScreenController).getMonth();
		doNothing().when(mockReportsMainScreenController).alertHandler(anyString(), anyBoolean());
		doReturn(null).when(mockReportsMainScreenController).getYearComboBox();
		String expected = "You didn't choose Year!";
		String result = mockReportsMainScreenController.clickShowReport(event);
		Assertions.assertEquals(expected, result);
	   }
	  
	  // Check Functionality: 
	  // Input Data: 
	  // Expected Result: 
	  @Test
	  void TestclickShowReportNULLType() {
		doReturn("10").when(mockReportsMainScreenController).getMonth();
		doNothing().when(mockReportsMainScreenController).alertHandler(anyString(), anyBoolean());
		doReturn("2022").when(mockReportsMainScreenController).getYearComboBox();
		doReturn(null).when(mockReportsMainScreenController).getType();
		String expected = "You didn't choose Report Type!";
		String result = mockReportsMainScreenController.clickShowReport(event);
		Assertions.assertEquals(expected, result);
	   }
	  
	  // Check Functionality: 
	  // Input Data: 
	  // Expected Result: 
	  @Test
	  void TestclickShowReportNULLLocationFlag1() {
		doReturn("10").when(mockReportsMainScreenController).getMonth();
		doNothing().when(mockReportsMainScreenController).alertHandler(anyString(), anyBoolean());
		doReturn("2022").when(mockReportsMainScreenController).getYearComboBox();
		doReturn("Inventory").when(mockReportsMainScreenController).getType();
		doReturn(null).when(mockReportsMainScreenController).getLocation();
		doReturn(1).when(mockReportsMainScreenController).getLocationFlag();
		String expected = "You didn't choose Location !";
		String result = mockReportsMainScreenController.clickShowReport(event);
		Assertions.assertEquals(expected, result);
	   }
	  
	  // Check Functionality: 
	  // Input Data: 
	  // Expected Result: 
	  @Test
	  void TestclickShowReportNULLLocationFlag0() {
		doReturn("10").when(mockReportsMainScreenController).getMonth();
		doNothing().when(mockReportsMainScreenController).alertHandler(anyString(), anyBoolean());
		doReturn("2022").when(mockReportsMainScreenController).getYearComboBox();
		doReturn("Inventory").when(mockReportsMainScreenController).getType();
		doReturn(null).when(mockReportsMainScreenController).getLocation();
		doReturn(0).when(mockReportsMainScreenController).getLocationFlag();
		doReturn(null).when(mockReportsMainScreenController).getMachineID();
		doReturn(1).when(mockReportsMainScreenController).getMachineIDFlag();
		String expected = "You didn't choose Machine ID!";
		String result = mockReportsMainScreenController.clickShowReport(event);
		Assertions.assertEquals(expected, result);
	   }
	  
	  // Check Functionality: 
	  // Input Data: 
	  // Expected Result: 
	  @Test
	  void TestclickShowReportNULLMachineID() {
		doReturn("10").when(mockReportsMainScreenController).getMonth();
		doNothing().when(mockReportsMainScreenController).alertHandler(anyString(), anyBoolean());
		doReturn("2022").when(mockReportsMainScreenController).getYearComboBox();
		doReturn("Inventory").when(mockReportsMainScreenController).getType();
		doReturn("north").when(mockReportsMainScreenController).getLocation();
		doReturn(0).when(mockReportsMainScreenController).getLocationFlag();
		doReturn(null).when(mockReportsMainScreenController).getMachineID();
		doReturn(1).when(mockReportsMainScreenController).getMachineIDFlag();
		String expected = "You didn't choose Machine ID!";
		String result = mockReportsMainScreenController.clickShowReport(event);
		Assertions.assertEquals(expected, result);
	   }
	  
	  // Check Functionality: 
	  // Input Data: 
	  // Expected Result: 
	  @Test
	  void TestclickShowReportNULLMachineIDFlag0() {
		doReturn("1").when(mockReportsMainScreenController).getMonth();
		doNothing().when(mockReportsMainScreenController).alertHandler(anyString(), anyBoolean());
		doReturn("2022").when(mockReportsMainScreenController).getYearComboBox();
		doReturn("Inventory").when(mockReportsMainScreenController).getType();
		doReturn("north").when(mockReportsMainScreenController).getLocation();
		doReturn(0).when(mockReportsMainScreenController).getLocationFlag();
		doReturn(null).when(mockReportsMainScreenController).getMachineID();
		doReturn(0).when(mockReportsMainScreenController).getMachineIDFlag();
		doNothing().when(mockReportsMainScreenController).saveChatClientData();
		ArrayList<String> monthYearMachine = new ArrayList<>();
		monthYearMachine.add("1");
		monthYearMachine.add("2022");
		doReturn("Report not found").when(mockReportsMainScreenController).inventoryReportCase(monthYearMachine,null);
		String expected = "Report not found";
		String result = mockReportsMainScreenController.clickShowReport(event);
		Assertions.assertEquals(expected, result);
	   }
	  
	  // Check Functionality: 
	  // Input Data: 
	  // Expected Result: 
	  @Test
	  void TestclickShowReportNotFound() {
		doReturn("1").when(mockReportsMainScreenController).getMonth();
		doNothing().when(mockReportsMainScreenController).alertHandler(anyString(), anyBoolean());
		doReturn("2022").when(mockReportsMainScreenController).getYearComboBox();
		doReturn("Inventory").when(mockReportsMainScreenController).getType();
		doReturn("north").when(mockReportsMainScreenController).getLocation();
		doReturn(0).when(mockReportsMainScreenController).getLocationFlag();
		doReturn("NOR1").when(mockReportsMainScreenController).getMachineID();
		doReturn(0).when(mockReportsMainScreenController).getMachineIDFlag();
		doNothing().when(mockReportsMainScreenController).saveChatClientData();
		ArrayList<String> monthYearMachine = new ArrayList<>();
		monthYearMachine.add("1");
		monthYearMachine.add("2022");
		doReturn("Report not found").when(mockReportsMainScreenController).inventoryReportCase(monthYearMachine,null);
		String expected = "Report not found";
		String result = mockReportsMainScreenController.clickShowReport(event);
		Assertions.assertEquals(expected, result);
	   }
	  
	  // Check Functionality: 
	  // Input Data: 
	  // Expected Result: 
	  @Test
	  void TestclickShowReportDoneSuccessfull() {
		doReturn("10").when(mockReportsMainScreenController).getMonth();
		doNothing().when(mockReportsMainScreenController).alertHandler(anyString(), anyBoolean());
		doReturn("2022").when(mockReportsMainScreenController).getYearComboBox();
		doReturn("Inventory").when(mockReportsMainScreenController).getType();
		doReturn("north").when(mockReportsMainScreenController).getLocation();
		doReturn(0).when(mockReportsMainScreenController).getLocationFlag();
		doReturn("NOR1").when(mockReportsMainScreenController).getMachineID();
		doReturn(0).when(mockReportsMainScreenController).getMachineIDFlag();
		doNothing().when(mockReportsMainScreenController).saveChatClientData();
		ArrayList<String> monthYearMachine = new ArrayList<>();
		monthYearMachine.add("10");
		monthYearMachine.add("2022");
		doReturn("Report was found").when(mockReportsMainScreenController).inventoryReportCase(monthYearMachine,null);
		doNothing().when(mockReportsMainScreenController).switchToReport(event,null);
		String expected = "done";
		String result = mockReportsMainScreenController.clickShowReport(event);
		Assertions.assertEquals(expected, result);
	   }

}
