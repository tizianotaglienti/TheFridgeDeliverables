package test.controller;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import logic.bean.BeanChangeFridge;
import logic.bean.BeanCredentials;
import logic.controller.ChangeFridgeController;
import logic.controller.RegistrationController;
import logic.dao.DaoFridge;
import logic.entity.Admin;
import logic.entity.Fridge;
import logic.entity.Invitation;
import logic.entity.User;
import logic.implementation.Queries;
import test.TestQueries.QueriesTest;
import logic.implementation.gof.SingletonInstances;
import logic.implementation.exceptions.EmptyException;
import logic.implementation.exceptions.EmptyInvitationException;
import logic.implementation.exceptions.TooManyFridgesException;

//tested by Di Battista Mattia
class TestChangeFridgeController {

	protected static String user = "root";
	protected static String pass = "Zurigo70";
	protected static String dbUrl = "jdbc:mysql://localhost:3306/TheFridgeDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	protected static String driverClassName = "com.mysql.cj.jdbc.Driver";
	protected static String databaseName = "TheFridgeDB";
	protected Statement stmt = null;
	protected Connection conn = null;
	private User user1;
	private ChangeFridgeController changeFridgeController;
	private BeanCredentials beanCredentials;
	private ArrayList<Invitation> invitationList2;
	
	TestChangeFridgeController(){
		
		this.changeFridgeController = new ChangeFridgeController();
		this.beanCredentials = new BeanCredentials();
		this.user1 = new User();
		
	}
	
	@Test
	void testDeleteAccount() throws SQLException {
		
	    try {
			// STEP 2: loading dinamico del driver mysql
            Class.forName(driverClassName);

            // STEP 3: apertura connessione
            conn = DriverManager.getConnection(dbUrl, user, pass);

            // STEP 4: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}
		
		user1.setEmailAddress("test@prova.com");
		user1.setPassword("123456");
		user1.setUsername("AccountTest");
		
		SingletonInstances.getSingletonInstance().setCurrentUser(user1);
		changeFridgeController.deleteAccount();
		
		Queries.deleteUserForUsername(stmt, user1.getUsername());
		
	}

	@Test
	void testTakeInvitations() throws EmptyException, SQLException, EmptyInvitationException {
		
	    try {
			// STEP 2: loading dinamico del driver mysql
            Class.forName(driverClassName);

            // STEP 3: apertura connessione
            conn = DriverManager.getConnection(dbUrl, user, pass);

            // STEP 4: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	    
		SingletonInstances.getSingletonInstance();

		//creo un nuovo utente
		beanCredentials.setUsernameBean("AccountTestFirst");
		beanCredentials.setPasswordBean("123456");
		beanCredentials.setEmailAddressBean("test@test.com");
		
		RegistrationController registrationTest1 = new RegistrationController();
		registrationTest1.registration(beanCredentials);
		
		//creo un nuovo utente
		BeanCredentials beanCredentials2 = new BeanCredentials();
		beanCredentials2.setUsernameBean("AccountTestSecond");
		beanCredentials2.setPasswordBean("123456");
		beanCredentials2.setEmailAddressBean("test@test.com");

		RegistrationController registrationTest2 = new RegistrationController();
		registrationTest2.registration(beanCredentials2);
				
		//inserisco un invito nel DB
		Queries.insertInvitation( stmt , beanCredentials2.getUsernameBean(), beanCredentials.getUsernameBean(), "thisIsATest");
	
		//chiamo l'operazione da testare		
		SingletonInstances.getSingletonInstance().getCurrentUser().setUsername(beanCredentials.getUsernameBean());
		ChangeFridgeController changeFridgeController = new ChangeFridgeController();
		BeanChangeFridge beanChangeFridge3 = changeFridgeController.takeInvitations();

		//creo l'invito aspettato
		Invitation invitation = new Invitation();
		invitation.setInvitedUser(beanCredentials.getUsernameBean());
		invitation.setInvitingUser(beanCredentials2.getUsernameBean());
		invitation.setMessage("thisIsATest");
	
		//confronto
		assertEquals(invitation.toString(), beanChangeFridge3.getInvitationList().get(0).toString());

		Queries.deleteUserForUsername(stmt, beanCredentials2.getUsernameBean());
		Queries.deleteUserForUsername(stmt, beanCredentials.getUsernameBean());		
		
	}
	
	
	
	@Test
	void testValidAcceptInvitation() throws TooManyFridgesException, SQLException {
		
	    try {
			// STEP 2: loading dinamico del driver mysql
            Class.forName(driverClassName);

            // STEP 3: apertura connessione
            conn = DriverManager.getConnection(dbUrl, user, pass);

            // STEP 4: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	    
		User user = new User();
		user.setUsername("AccountTest");
		user.setPassword("123456");
		user.setEmailAddress("test@test.com");
	
		Fridge fridge = new Fridge();
		fridge.setId(1234);
		fridge.setName("testFridge");
		
		BeanCredentials beanCredentials = new BeanCredentials();
		beanCredentials.setEmailAddressBean("test@test.com");
		beanCredentials.setPasswordBean("123456");
		beanCredentials.setUsernameBean("AccountTest");
		
		RegistrationController registrationController = new RegistrationController();
		registrationController.registration(beanCredentials);
		
		DaoFridge daoFridge = new DaoFridge();
		daoFridge.createFridge(fridge);
		
		Queries.insertMembership(stmt, beanCredentials.getUsernameBean(), fridge.getId());
		
		SingletonInstances.getSingletonInstance().setCurrentUser(user);
		
		BeanChangeFridge beanChangeFridge = new BeanChangeFridge();
		
		Invitation invitation = new Invitation();
		invitation.setInvitedUser(user.getUsername());
		
		invitationList2 = null;
		invitationList2 = new ArrayList<Invitation>(); 
		invitationList2.add(invitation);
		beanChangeFridge.setInvitationList(invitationList2);

		ChangeFridgeController changeFridgeController = new ChangeFridgeController();
		changeFridgeController.acceptInvitation(beanChangeFridge);
		
		QueriesTest.deleteFridge(stmt, fridge);
		Queries.deleteUserForUsername(stmt,user.getUsername());
	}
	
	
	@Test
	void testTakeMyFridges() throws EmptyException, SQLException {

		this.stmt = null;
	    this.conn = null;
		
	    try {
			// STEP 2: loading dinamico del driver mysql
            Class.forName(driverClassName);

            // STEP 3: apertura connessione
            conn = DriverManager.getConnection(dbUrl, user, pass);

            // STEP 4: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	    
		Fridge fridge = new Fridge();
		fridge.setId(1234);
		fridge.setName("testFridge");

		QueriesTest.deleteFridge(stmt, fridge);
		Queries.deleteUserForUsername(stmt, "AccountTest6");

		//creo un utente e lo registro
		BeanCredentials beanCredentials= new BeanCredentials();
		beanCredentials.setEmailAddressBean("test@test.com");
		beanCredentials.setUsernameBean("AccountTest6");
		beanCredentials.setEmailAddressBean("123456");
		
		RegistrationController registrationController = new RegistrationController();
		registrationController.registration(beanCredentials);
		
		//aggiungo un frigo
		Queries.insertFridge(stmt, fridge);
		Queries.insertMembership(stmt, beanCredentials.getUsernameBean(), fridge.getId());
		
		ChangeFridgeController changeFridgeController = new ChangeFridgeController();
		//chiamo l'operazione
		BeanChangeFridge beanChangeFridge = changeFridgeController.takeMyFridges();
		
		assertEquals(1,beanChangeFridge.getListOfNameFridges().size());
		
		Queries.deleteUserForUsername(stmt, beanCredentials.getUsernameBean());
		QueriesTest.deleteFridge(stmt, fridge);
	}
	
	
	
	@Test
	void testNotChangeInMyFridge() throws SQLException {
		
		this.stmt = null;
	    this.conn = null;
		
	    try {
			// STEP 2: loading dinamico del driver mysql
            Class.forName(driverClassName);

            // STEP 3: apertura connessione
            conn = DriverManager.getConnection(dbUrl, user, pass);

            // STEP 4: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	    
		Admin admin = new Admin();
			
		admin.setEmailAddress("testUser@test.com");
		admin.setPassword("123456");
		admin.setUsername("UserTest");
			
		SingletonInstances.getSingletonInstance().setCurrentUser(admin);
		
		ChangeFridgeController changeFridgeController = new ChangeFridgeController();
		changeFridgeController.changeInMyFridge();
		
		assertEquals(true,SingletonInstances.getSingletonInstance().getCurrentUser().isAdmin());
				
	}
	
	@Test
	void testYesChangeInMyFridge() throws SQLException {
		
		this.stmt = null;
	    this.conn = null;
		
	    try {
			// STEP 2: loading dinamico del driver mysql
            Class.forName(driverClassName);

            // STEP 3: apertura connessione
            conn = DriverManager.getConnection(dbUrl, user, pass);

            // STEP 4: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	    
		Fridge fridge = new Fridge();
		User user = new User();
	
		user.setEmailAddress("testUser@test.com");
		user.setPassword("123456");
		user.setUsername("UserTest");
		fridge.setId(1234);

		
		SingletonInstances.getSingletonInstance().setCurrentUser(user);
		SingletonInstances.getSingletonInstance().setMyFridge(fridge);
		
		ChangeFridgeController changeFridgeController = new ChangeFridgeController();
		changeFridgeController.changeInMyFridge();

		//controllo che effettivamente getMyFridge ritorni il frigo 1234
		assertEquals(fridge,SingletonInstances.getSingletonInstance().getCurrentFridge());
	}
	
	@Test
	void testTakeUsername() {
		
		user1.setUsername("prova99");
		SingletonInstances.getSingletonInstance().setCurrentUser(user1);
		assertEquals("prova99",changeFridgeController.takeUsername());
	}
	
	@Test
	void testTakeEmail() {
		
		user1.setUsername("prova99");
		user1.setEmailAddress("prova99@gamail.com");
		SingletonInstances.getSingletonInstance().setCurrentUser(user1);
		assertEquals("prova99@gamail.com",changeFridgeController.takeEmail());
		
	}

	@Test
	void testTakeMyFridgeName() {
		
		Fridge fridge = new Fridge();
		fridge.setId(123456);
		fridge.setName("TestFridge");
		SingletonInstances.getSingletonInstance().setMyFridge(fridge);;
		assertEquals("TestFridge",changeFridgeController.takeMyFridgeName());
	}


}













