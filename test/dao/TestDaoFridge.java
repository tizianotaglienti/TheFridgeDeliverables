/**
 * @author Valerio Cristofori
 */
package test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import logic.dao.DaoFood;
import logic.dao.DaoFridge;
import logic.dao.DaoUser;
import logic.entity.Admin;
import logic.entity.Food;
import logic.entity.Fridge;
import logic.entity.User;
import logic.implementation.exceptions.DuplicatedFoodException;
import logic.implementation.exceptions.EmptyException;
import test.implementation.QueriesTest;


public class TestDaoFridge {
	private static String USER = "root";
	private static String PASS = "zurigo70";
	private static String DB_URL = "jdbc:mysql://localhost:3306/TheFridgeDB";
	private static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	private Statement stmt;
	private Connection conn;

	private Fridge fridge;
	private Food food1;
	private Food food2;
	private ArrayList<Food> listOfFood;
	
	public TestDaoFridge() {
		this.fridge = new Fridge();		
		this.fridge.setName("fooFridge");
		this.food1 = new Food("pollo", 2, "20/12/2020");
		this.food2 = new Food("mela", 1, null);
		this.listOfFood = new ArrayList<Food>();
		this.listOfFood.add(food1);
		this.listOfFood.add(food2);
		this.fridge.setListOfFood(this.listOfFood);
	}
	
	@Test
	public void testCreateFridge() throws SQLException, ClassNotFoundException {
		DaoFridge daoFridge = new DaoFridge();
		this.fridge.setId( (int) (Math.random() * 10000 ) );
		daoFridge.createFridge(this.fridge);
		// STEP 2: loading dinamico del driver mysql
        Class.forName(DRIVER_CLASS_NAME);
        // STEP 3: apertura connessione
        this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
        // STEP 4: creazione ed esecuzione della query
        this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = QueriesTest.selectFridge(this.stmt, this.fridge.getId());
        rs.first();
        int actualID = rs.getInt("ID");
        String actualName = rs.getString("name");
        
		Fridge actualFridge = new Fridge();
		actualFridge.setName( actualName );
		actualFridge.setId(actualID);
		actualFridge.setListOfFood(this.listOfFood);
		assertEquals( this.fridge.toString(), actualFridge.toString() );
	}

	@Test
	public void testCheckFridgeID() {
		DaoFridge daoFridge = new DaoFridge();
		boolean actualRet = daoFridge.checkFridgeID(this.fridge);
		assertEquals(true, actualRet);
		
	}

	@Test
	public void testGetFridgeById() {
		DaoFridge daoFridge = new DaoFridge();
		int id = (int) (Math.random() * 10000 );
		this.fridge.setId( id );
		this.fridge.setName("pippo fridge");
		daoFridge.createFridge(this.fridge);
		Fridge actualFridge = daoFridge.getFridgeById( id );
		assertEquals( this.fridge.getId(), actualFridge.getId() );
	}

	@Test
	public void testCreateAdministration() throws SQLException, ClassNotFoundException {
		
		User admin = new Admin();
		admin.setUsername(String.format("testUser: %d" , (int) (Math.random() * 10000 ) ));
		admin.setEmailAddress("emailfoo");
		admin.setPassword("foopass");
		DaoUser daoUser = new DaoUser();
		daoUser.saveRegistrationToDB(admin);
		DaoFridge daoFridge = new DaoFridge();
		this.fridge.setId( (int) (Math.random() * 10000 ) );
		daoFridge.createFridge(this.fridge);
		daoFridge.createAdministration(this.fridge, admin);
		// STEP 2: loading dinamico del driver mysql
        Class.forName(DRIVER_CLASS_NAME);
        // STEP 3: apertura connessione
        this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
        // STEP 4: creazione ed esecuzione della query
        this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = QueriesTest.selectAdministration( this.stmt, this.fridge.getId(), admin.getUsername() );
		assertEquals( true, rs.first());
	}
	
	@Test
	public void testGetContentFridge() throws EmptyException, DuplicatedFoodException {
		DaoFridge daoFridge = new DaoFridge();
		this.fridge.setId( (int) (Math.random() * 10000 ) );
		daoFridge.createFridge(this.fridge);
		DaoFood daoFood = new DaoFood();
		daoFood.saveFood( this.food1, this.fridge);
		ArrayList<Food> actualList = (ArrayList<Food>) daoFridge.getContentFridge(this.fridge);
		assertEquals( food1.getName(), actualList.get(0).getName());
	}

	@Test
	public void testUpdateFridgeNameInDB() throws SQLException, ClassNotFoundException {
		DaoFridge daoFridge = new DaoFridge();
		this.fridge.setId( (int) (Math.random() * 10000 ) );
		daoFridge.createFridge(this.fridge);
		String newName = "foo2";
		this.fridge.setName(newName);
		daoFridge.updateFridgeNameInDB(this.fridge);
		
		// STEP 2: loading dinamico del driver mysql
        Class.forName(DRIVER_CLASS_NAME);
        // STEP 3: apertura connessione
        this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
        // STEP 4: creazione ed esecuzione della query
        this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = QueriesTest.selectFridge(this.stmt, this.fridge.getId());
        rs.first();
        String actualName = rs.getString("name");
        assertEquals( newName, actualName);
	}

}
