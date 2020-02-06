package test.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.entity.Food;
import logic.entity.Fridge;

public class QueriesTest {
	
	public static ResultSet selectFoodFridge(Statement stmt, Food food,  Fridge fridge) throws SQLException  {
		String sql = String.format("SELECT name, fridge, quantity FROM Food WHERE name = '%s' and fridge = '%d' and quantity = '%d'",food.getName(), fridge.getId(), food.getQuantity() );
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }
	
	public static int deleteFridge(Statement stmt, Fridge fridge) throws SQLException  {
        String deleteStatement = String.format("DELETE FROM  fridge  WHERE ID = '%d'", fridge.getId() );
        System.out.println(deleteStatement);
        return stmt.executeUpdate(deleteStatement);
    }
		
	public static ResultSet selectAdministration(Statement stmt, int ID, String username ) throws SQLException  {
        String sql = String.format("SELECT * FROM Administration WHERE user = '%s' and fridge = '%d';",  username, ID);
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }
		
	public static ResultSet selectFridge(Statement stmt, int ID ) throws SQLException{
		 String sql = String.format("SELECT * FROM Fridge WHERE ID = '%d';", ID);
	     System.out.println(sql);
	     return stmt.executeQuery(sql);
	}
		
	public static int removeUser(Statement stmt, String username ) throws SQLException {
		String removeStatement = String.format("DELETE FROM User WHERE username = '%s';", username );
        System.out.println(removeStatement);
        return stmt.executeUpdate(removeStatement);
	}
	
	public static ResultSet getUserByCredentials(Statement stmt, String username, String emailAddress, String password) throws SQLException {
		String sql = "SELECT * FROM User where username = '" + username + "' and emailAddress = '" + emailAddress + "' and password = '" + password + "';";
		System.out.println(sql);
		return stmt.executeQuery(sql);
	}
		
		
}






