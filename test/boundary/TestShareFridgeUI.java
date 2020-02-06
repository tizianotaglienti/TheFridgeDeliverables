package test.boundary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logic.boundary.ShareFridgeUI;
import logic.dao.DaoUser;
import logic.entity.User;
import logic.implementation.gof.SingletonInstances;

//tested by Di Battista Mattia
public class TestShareFridgeUI {
	
	private ShareFridgeUI shareFridgeUi;
	private User user = new User();
	private User user2 = new User();;
	private boolean val;
	private String message = "TestString";
	
	@Test
	public void testShareFridgeUI() {
		
		shareFridgeUi = new ShareFridgeUI();
	}
	
	@Test
	void testIsValidUsernameMine()  {		
		
		user.setUsername("mattia");
		user2.setUsername("account");
		
		DaoUser daoUser = new DaoUser();
		daoUser.saveRegistrationToDB(user);
		daoUser.saveRegistrationToDB(user2);
		
		SingletonInstances.getSingletonInstance().setCurrentUser(user2);
		shareFridgeUi = new ShareFridgeUI();
		val = shareFridgeUi.isValidUsername(user.getUsername());
		assertEquals(true,val);
	}
	
	@Test
	void testIsNotValidUsernameMine() {

		user.setUsername("mattia");
		user2.setUsername("mattia");
		
		SingletonInstances.getSingletonInstance().setCurrentUser(user2);
		shareFridgeUi = new ShareFridgeUI();
		val = shareFridgeUi.isValidUsername(user.getUsername());
		assertEquals(false,val);
	}
	
	@Test
	void testIsValidEmailMine() {
		
		user = new User();
		user.setEmailAddress("mattia@gamil.com");
		user2 = new User();
		user2.setEmailAddress("mattia2@gamil.com");
		
		SingletonInstances.getSingletonInstance().setCurrentUser(user2);
		shareFridgeUi = new ShareFridgeUI();
		boolean val = shareFridgeUi.isValidEmail(user.getEmailAddress());
		assertEquals(true,val);
		
	}

	@Test
	void testClickedOnInviteWithUsername() {

		user.setUsername("mattia3");
		shareFridgeUi = new ShareFridgeUI();
		shareFridgeUi.clickedOnInviteWithUsername(user.getUsername(),message);
		
	}
	
	@Test
	void testClickedOnInviteWithEmail() {

		user.setEmailAddress("mattia@gamil.com");
		shareFridgeUi = new ShareFridgeUI();
		shareFridgeUi.clickedOnInviteWithEmail(user.getEmailAddress(),message);
		
	}

}






