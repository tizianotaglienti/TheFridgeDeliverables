package test.controller;
// tested by Tiziano Taglienti
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import logic.bean.BeanAddFood;
import logic.bean.BeanViewFridge;
import logic.controller.AddFoodController;
import logic.controller.ViewFridgeController;
import logic.dao.DaoFridge;
import logic.dao.DaoUser;
import logic.entity.Food;
import logic.entity.Fridge;
import logic.entity.User;
import logic.implementation.Queries;
import logic.implementation.exceptions.DuplicatedFoodException;
import logic.implementation.exceptions.EmptyException;
import logic.implementation.gof.SingletonInstances;
import test.implementation.QueriesTest;

public class TestViewFridgeController {

	private Statement stmt = null;;
	private Connection conn = null;
	private static String user = "root";
	private static String pass = "Zurigo70";
	protected static String dbUrl = "jdbc:mysql://localhost:3306/TheFridgeDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String driverClassName = "com.mysql.cj.jdbc.Driver";
	protected static String databaseName = "TheFridgeDB";

	@Test
	public void testViewFridgeController() {		
	}

	@Test
	public void testTakeContent() throws EmptyException, SQLException, ClassNotFoundException, DuplicatedFoodException {	
		
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
		
		
		Fridge testFridge = new Fridge();
		testFridge.setId(1111);
		testFridge.setName("Test");
		
		Food food = new Food();
		food.setName("banana");
		food.setQuantity(1);
		
		User testUser = new User();
		testUser.setUsername("Test80");
		
		Queries.insertUser(stmt, testUser);
		Queries.insertFridge(stmt, testFridge);
		Queries.insertFoodInTheFridge(stmt, food, testFridge);
		Queries.insertAdministration(stmt, testUser.getUsername(), testFridge);
			
		SingletonInstances.getSingletonInstance().setCurrentUser(testUser);
		SingletonInstances.getSingletonInstance().setCurrentFridge(testFridge);
		
		BeanViewFridge beanViewFridge = new BeanViewFridge();
		ViewFridgeController viewFridgeCTRL = new ViewFridgeController();
		beanViewFridge = viewFridgeCTRL.takeContent();

		System.out.println(beanViewFridge);
		
		assertEquals(testFridge.getListOfFood(), beanViewFridge.getListOfFood());
		Queries.deleteUserForUsername(stmt, testUser.getUsername());
		QueriesTest.deleteFridge(stmt, testFridge);
		

//		chiamo operazione da testare		
//		assertEquals(food.getName(), beanViewFridge.getFoodName());
//		aggiungo cibo alla lista e faccio take content
//		poi faccio beanviewfridge.getlistoffood e vedo se coincide
//		beanViewFridge.setFoodName("banana");
//		beanViewFridge.setFoodQuantity(2);
//		BeanAddFood beanAddFood = new BeanAddFood();
//		beanAddFood.setName("banana");
//		beanAddFood.setQuantity(2);
//		AddFoodController addFoodCTRL = new AddFoodController();
//		addFoodCTRL.insertFood(beanAddFood);
//		DaoFood daoFood = new DaoFood();
//		daoFood.saveFood(food, fridge);
//		BeanViewFridge content = viewFridgeCTRL.takeContent();
//		System.out.println(content.toString());		
//		assertEquals ("banana", content.toString());

	}

	@Test
	public void testChangeFridgeName() throws SQLException {
		
		DaoUser daoUser = new DaoUser();	
		ViewFridgeController viewFridgeCTRL = new ViewFridgeController();
		User user = new User();
		Fridge fridge = new Fridge();
		DaoFridge daoFridge = new DaoFridge();
		
		//daoUser.deleteUser(user);
		
		daoUser.saveRegistrationToDB(user);		
		fridge.setName("vecchio nome");
		fridge.setId(109);
		daoFridge.createFridge(fridge);
		
		SingletonInstances.getSingletonInstance().setCurrentUser(user);
		SingletonInstances.getSingletonInstance().setCurrentFridge(fridge);
		daoFridge.createAdministration(fridge, user);

		SingletonInstances.getSingletonInstance().becomeAdmin();
		viewFridgeCTRL.changeFridgeName("nuovo nome");

		Fridge fridge2 = new Fridge(); 

		fridge2 = daoFridge.getFridgeById(fridge.getId());
		SingletonInstances.getSingletonInstance().setCurrentFridge(fridge2);
		
		System.out.println(fridge2.getId());
		System.out.println(fridge2.getName());

		assertEquals ("nuovo nome", fridge2.getName());
		QueriesTest.deleteFridge(stmt, fridge);
		
		
	}

	@Test 
	public void testChangeFridgeNameNot() throws SQLException {
		
		DaoUser daoUser = new DaoUser();	
		ViewFridgeController viewFridgeCTRL = new ViewFridgeController();
		User user = new User();
		Fridge fridge = new Fridge();
		DaoFridge daoFridge = new DaoFridge();
		
		//daoUser.deleteUser(user);
		user.setUsername("test78");
		daoUser.saveRegistrationToDB(user);		
		fridge.setId(1091);
		daoFridge.createFridge(fridge);
		
		SingletonInstances.getSingletonInstance().setCurrentUser(user);
		SingletonInstances.getSingletonInstance().setCurrentFridge(fridge);
		//daoFridge.createAdministration(fridge, user);

		SingletonInstances.getSingletonInstance().becomeUser();
		viewFridgeCTRL.changeFridgeName("nuovo nome");

		Fridge fridge2 = new Fridge(); 

		fridge2 = daoFridge.getFridgeById(fridge.getId());
		SingletonInstances.getSingletonInstance().setCurrentFridge(fridge2);
		
		System.out.println(fridge2.getId());
		System.out.println(fridge2.getName());
		
		Queries.deleteUserForUsername(stmt, user.getUsername());
		QueriesTest.deleteFridge(stmt, fridge);
		
		

		//assertEquals ("nuovo nome", fridge2.getName());
		
	}
	@Test
	public void testRemoveFood() throws ClassNotFoundException, SQLException {
		ViewFridgeController viewFridgeCTRL = new ViewFridgeController();
		Food food = new Food();
		Fridge fridge = new Fridge();
		BeanViewFridge beanViewFridge = new BeanViewFridge();	
		BeanAddFood beanAddFood = new BeanAddFood();
		AddFoodController addFoodCTRL = new AddFoodController();
		DaoFridge daoFridge = new DaoFridge();
		
		beanViewFridge.setFoodName("banana");
		beanViewFridge.setFoodQuantity(2);
		beanViewFridge.setId(11100);
		food.setName(beanViewFridge.getFoodName());
		food.setQuantity(beanViewFridge.getFoodQuantity());
		food.setExpirationDate("domani");
		fridge.setId(beanViewFridge.getId());
		daoFridge.createFridge(fridge);

		SingletonInstances.getSingletonInstance().setCurrentFridge(fridge);
		beanAddFood.setNameFood(beanViewFridge.getFoodName());
		beanAddFood.setQuantityFood(beanViewFridge.getFoodQuantity());
		beanAddFood.setExpirationDateFood(food.getExpirationDate());
		addFoodCTRL.insertFood(beanAddFood);
		viewFridgeCTRL.removeFood(beanViewFridge);
		
		// STEP 2: loading dinamico del driver mysql
        Class.forName(driverClassName);
        // STEP 3: apertura connessione
        this.conn = DriverManager.getConnection(dbUrl, user, pass);
        // STEP 4: creazione ed esecuzione della query
        this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);		
		//ResultSet rs = Queries.isFoodInFridge(this.stmt, food, fridge);				
        int rs = Queries.insertFoodInTheFridge(stmt, food, fridge);				
        //assertEquals (false, rs.first());
        assertEquals (1, rs);
        
		QueriesTest.deleteFridge(stmt, fridge);
        
	}
	
	@Test
	public void testTakeFridgeName() {
		
		Fridge fridge = new Fridge();
		fridge.setName("testFridge76");
		SingletonInstances.getSingletonInstance().setCurrentFridge(fridge);
		ViewFridgeController viewFridgeController = new ViewFridgeController(); 
		String s = viewFridgeController.takeFridgeName();
		assertEquals(fridge.getName(),s);
	}

}
