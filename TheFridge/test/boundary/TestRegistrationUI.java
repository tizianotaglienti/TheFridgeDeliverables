/**
 * Tested by Tiziano Taglienti
 */
package test.boundary;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

import logic.boundary.RegistrationUI;
import logic.entity.User;
import test.implementation.QueriesTest;

public class TestRegistrationUI {
	private static String USER = "root";
	private static String PASS = "zurigo70";
	private static String DB_URL = "jdbc:mysql://localhost:3306/TheFridgeDB";
	private static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	private Statement stmt;
	private Connection conn;
	
	private User user;
	
	public TestRegistrationUI() {
		this.user = new User();
		
		
	}
	
	@Test
	public void testNotExist() throws SQLException, ClassNotFoundException {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setUsername("foo");
		this.user.setPassword("foopass");
		this.user.setEmailAddress("fooemail");
		// STEP 2: loading dinamico del driver mysql
        Class.forName(DRIVER_CLASS_NAME);
        // STEP 3: apertura connessione
        this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
        // STEP 4: creazione ed esecuzione della query
        this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        QueriesTest.removeUser(this.stmt, this.user.getUsername());
        boolean actualRet = regUI.notExist(this.user.getUsername(), this.user.getEmailAddress(), this.user.getPassword());
        assertEquals(true, actualRet);
	}

	@Test
	public void testClickedOnRegistration() {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setUsername("foo");
		this.user.setPassword("foopass");
		this.user.setEmailAddress("fooemail");
		regUI.clickedOnRegistration(this.user.getUsername(), this.user.getEmailAddress(), this.user.getPassword());
		boolean actualRet = regUI.notExist(this.user.getUsername(), this.user.getEmailAddress(), this.user.getPassword());
		assertEquals( false, actualRet);
	}

	@Test
	public void testValidSyntaxUsernameFalse() {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setUsername(null);
		boolean actualRet = regUI.validSyntaxUsername(this.user.getUsername());
		assertEquals(false, actualRet);
	}

	@Test
	public void testValidSyntaxUsernameNotValidShort() {
		
		RegistrationUI regUI = new RegistrationUI();
		this.user.setUsername("aaa");
		boolean actualRet = regUI.validSyntaxUsername(this.user.getUsername());
		assertEquals(false, actualRet);
		
	}
	
	@Test
	public void testValidSyntaxPasswordTrue() {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setPassword("foopass");
		boolean actualRet = regUI.validSyntaxPassword(this.user.getPassword());
		assertEquals(true, actualRet);
	}
	
	@Test
	public void testValidSyntaxPasswordFalse() {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setPassword(null);
		boolean actualRet = regUI.validSyntaxPassword(this.user.getPassword());
		assertEquals(false, actualRet);
	}
	
	@Test
	public void testValidSyntaxPasswordShort() {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setPassword("aa");
		boolean actualRet = regUI.validSyntaxPassword(this.user.getPassword());
		assertEquals(false, actualRet);
	}
	
	@Test
	public void testValidSyntaxEmailFalse() {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setEmailAddress("fooemail");
		boolean actualRet = regUI.validSyntaxEmail(this.user.getEmailAddress());
		assertEquals(false, actualRet);
	}

	@Test
	public void testIsValid() throws ClassNotFoundException, SQLException {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setUsername("foo");
		this.user.setPassword("foopass");
		this.user.setEmailAddress("fooemail");
		// STEP 2: loading dinamico del driver mysql
        Class.forName(DRIVER_CLASS_NAME);
        // STEP 3: apertura connessione
        this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
        // STEP 4: creazione ed esecuzione della query
        this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        QueriesTest.removeUser(this.stmt, this.user.getUsername());
        boolean actualRet = regUI.isValid(this.user.getUsername(), this.user.getPassword());
        assertEquals(false, actualRet);
	}

	@Test
	public void tesValidSyntaxEmailFalse() {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setEmailAddress(null);
		boolean actualRet = regUI.validSyntaxEmail(this.user.getEmailAddress());
		assertEquals(false, actualRet);
	}
	
	@Test
	public void tesValidSyntaxEmailTrue() {
		RegistrationUI regUI = new RegistrationUI();
		this.user.setEmailAddress("test@gmail.com");
		boolean actualRet = regUI.validSyntaxEmail(this.user.getEmailAddress());
		assertEquals(true, actualRet);
	}

}
