package common.connectivity;

import java.io.Serializable;

public class Customer extends User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
		// CLASS FIELDS ***********************************************
		private String creditCardNumber;
		private boolean isSub;
		//****************************************************************

		
		// CLASS Constructors ********************************************
		public Customer() {
			super();
		}
		public Customer(String username, String password, String firstname,String lastname, String id,
					String phonenumber, String emailaddress, String isLoggedIn, String department, String status, String creditCardNumber) {
			super(username, password, firstname,lastname, id, phonenumber, emailaddress, isLoggedIn, department, status);
			this.creditCardNumber = creditCardNumber;
		}
		//  **************************************************************
		
		
		// CLASS GETTERS/SETTERS *****************************************
		public String getCreditCardNumber() {
			return creditCardNumber;
		}
		public void setCreditCardNumber(String creditCardNumber) {
			this.creditCardNumber = creditCardNumber;
		}
		public boolean isSub() {
			return isSub;
		}
		public void setSub(boolean isSub) {
			this.isSub = isSub;
		}
	    //  **************************************************************
		
		// CLASS TO-STRING ***********************************************
		@Override
		public String toString(){
			return String.format("Card number : %s, Is a subscriber : %b\n" , creditCardNumber , isSub);
		}
		//  **************************************************************
}
