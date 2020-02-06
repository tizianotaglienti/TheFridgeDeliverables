package test.bean;
// tested by Tiziano Taglienti
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import logic.bean.BeanViewFridge;
import logic.entity.Food;

class TestBeanViewFridge {
	

	@Test
	void testSetID() {
		BeanViewFridge beanViewFridge = new BeanViewFridge();
		beanViewFridge.setId(69);
		int ID = beanViewFridge.getId();
		assertEquals(69, ID);
	}

	@Test
	void testSetName() {
		BeanViewFridge beanViewFridge = new BeanViewFridge();
		beanViewFridge.setName("pippofrigo");
		String fridgename = beanViewFridge.getName();
		assertEquals("pippofrigo", fridgename);
	}

	@Test
	void testSetListOfFood() {
		BeanViewFridge beanViewFridge = new BeanViewFridge();
		ArrayList<Food> listOfFood = new ArrayList<Food>(); 
		Food food = new Food();
		food.setName("banana");
		food.setQuantity(2);	
		listOfFood.add(food);
		
		beanViewFridge.setListOfFood(listOfFood);
		
		assertEquals(food,beanViewFridge.getListOfFood());
	}

	@Test
	void testSetFoodName() {
		BeanViewFridge beanViewFridge = new BeanViewFridge();
		beanViewFridge.setFoodName("banana");
		String foodname = beanViewFridge.getFoodName();
		assertEquals("banana", foodname);
	}

	@Test
	void testSetFoodQuantity() {
		BeanViewFridge beanViewFridge = new BeanViewFridge();
		beanViewFridge.setFoodQuantity(69);
		int quantity = beanViewFridge.getFoodQuantity();
		assertEquals(69, quantity);
	}

	
}
