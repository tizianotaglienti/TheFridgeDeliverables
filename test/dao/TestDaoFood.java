package test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

import logic.dao.DaoFood;
import logic.entity.Food;
import logic.entity.Fridge;
import logic.implementation.Queries;
import logic.implementation.exceptions.DuplicatedFoodException;
import test.TestQueries.QueriesTest;

//tested by Di Battista Mattia
class TestDaoFood {
	//il test consiste nell'inserire banana in fridge e di verificare
	//che sia stata effettivamente inserita
	//tutto ciò lo faccio confrontando il valore di ritorno della query che verifica l'inserimento

	protected static String user = "root";
	protected static String pass = "Zurigo70";
	protected static String dbUrl = "jdbc:mysql://localhost:3306/TheFridgeDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	protected static String driverClassName = "com.mysql.cj.jdbc.Driver";
	protected static String databaseName = "TheFridgeDB";
	protected Statement stmt = null;
	protected Connection conn = null;

	
	@Test
	void testSaveFood() throws SQLException, DuplicatedFoodException {
		
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

		Food testFood = new Food();
		Fridge testFridge = new Fridge();
		
		DaoFood daofood = new DaoFood();
		ResultSet result2;
		boolean val;
		
		testFood.setName("banana");
		testFood.setQuantity(1);
		testFridge.setName("testFridge");
		testFridge.setId(53);
		
		Queries.insertFridge(stmt, testFridge);
		
		daofood.saveFood(testFood,testFridge);
 
		result2 = QueriesTest.selectFoodFridge( stmt, testFood, testFridge);
		if(result2.first() == true) {
			val=true;
		}else {
			val=false;
		}
				
        assertEquals(true,val);
        QueriesTest.deleteFridge(stmt, testFridge);
		
	}
	
	@Test 
	void testSaveFoodException() throws SQLException, DuplicatedFoodException {
		
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
		 
		Food testFood = new Food();
		Fridge testFridge = new Fridge();
		Fridge testFridge2 = new Fridge();

		DaoFood daofood = new DaoFood();
		
		testFood.setName("banana");
		testFood.setQuantity(1);
		testFridge.setName("testFridge4");
		testFridge.setId(53);
		
		QueriesTest.deleteFridge(stmt, testFridge);
		
		Queries.insertFridge(stmt, testFridge);
	
		daofood.saveFood(testFood,testFridge2);
		
		daofood.saveFood(testFood,testFridge);
		
	}
	
	
	@Test
	void testRemoveFood() throws SQLException, DuplicatedFoodException {
		

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
		 
		Food testFood = new Food();
		Fridge testFridge = new Fridge();
		DaoFood daofood = new DaoFood();
		ResultSet result2;
		boolean val;
		testFood.setName("banana");
		testFood.setQuantity(1);
		testFridge.setName("testFridge8");
		testFridge.setId(59);
		
		int result = Queries.insertFridge( stmt, testFridge);
		System.out.println( result );

		daofood.saveFood(testFood,testFridge);
		
		daofood.removeFood(testFood, testFridge.getId());
		
		result2 = QueriesTest.selectFoodFridge( stmt, testFood, testFridge);
		if(result2.first() == true) {
			val=true;
		}else {
			val=false;
		}			
        assertEquals(false,val);
        QueriesTest.deleteFridge(stmt, testFridge);
		
		
	}
	
	
	@Test
	void testRemoveFoodException() throws SQLException, DuplicatedFoodException {
		
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
		 
		Food testFood = new Food();
		Fridge testFridge = new Fridge();
		DaoFood daofood = new DaoFood();
	
		testFood.setName("banana");
		testFood.setQuantity(1);
		testFridge.setName("testFridge");
		testFridge.setId(59);
		
		Queries.insertFridge( stmt, testFridge);
		daofood.saveFood(testFood, testFridge);
		
		daofood.removeFood(testFood, 14737);

        QueriesTest.deleteFridge(stmt, testFridge);
		
		
	}

	
	@Test
	void testSaveSafelyFoodException() throws SQLException {
		
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

	Food testFood = new Food();
	Fridge testFridge = new Fridge();
	
	DaoFood daoFood = new DaoFood();
	
	testFood.setName("banana");
	testFood.setQuantity(1);
	testFridge.setName("testFridge");
	testFridge.setId(57);
	
	daoFood.saveSafelyFood(testFood, testFridge);
    QueriesTest.deleteFridge(stmt, testFridge);
		
	}

	
	@Test
	void testSaveSafelyFood() throws SQLException {
		
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

	Food testFood = new Food();
	Fridge testFridge = new Fridge();
	
	DaoFood daoFood = new DaoFood();
	
	testFood.setName("banana");
	testFood.setQuantity(1);
	testFridge.setName("testFridge");
	testFridge.setId(54);
	
	Queries.insertFridge(stmt, testFridge);
	
	
	daoFood.saveSafelyFood(testFood, testFridge);
	
	ResultSet result2 = QueriesTest.selectFoodFridge( stmt, testFood, testFridge);
	boolean val;
	if(result2.first() == true) {
		val=true;
	}else {
		val=false;
	}
			
    assertEquals(true,val);
    QueriesTest.deleteFridge(stmt, testFridge);
	}

	
}









