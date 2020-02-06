/**
 * @author Valerio Cristofori
 */
package test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import logic.bean.BeanChooseRecipes;
import logic.controller.ChooseRecipesController;
import logic.dao.DaoFood;
import logic.dao.DaoFridge;
import logic.entity.Food;
import logic.entity.Fridge;
import logic.implementation.exceptions.DuplicatedFoodException;
import logic.implementation.exceptions.EmptyException;
import logic.implementation.gof.SingletonInstances;

public class TestChooseRecipesController {
	private Fridge fridge;
	private Food food1;
	private Food food2;
	
	
	public TestChooseRecipesController() {
		this.fridge = new Fridge();
		this.food2 = new Food("mela", 10, "10/10/2020");
		this.food1 = new Food("pollo", 20, "20/02/2020");
		
		
		this.fridge.setName("foofridge");
		
		
		ArrayList<Food> list = new ArrayList<Food>();
		list.add(food1);
		list.add(food2);
		
		this.fridge.setListOfFood(list);
		
	} 


	@Test
	public void testTakeFood() throws EmptyException, DuplicatedFoodException {
		DaoFridge daoFridge = new DaoFridge();
		this.fridge.setId( (int) (Math.random() * 10000 ) );
		daoFridge.createFridge(this.fridge);
		DaoFood daoFood = new DaoFood();
		daoFood.saveFood(food1, this.fridge);
		daoFood.saveFood(food2, this.fridge);
		SingletonInstances.getSingletonInstance();
		SingletonInstances.getSingletonInstance().setCurrentFridge(this.fridge);
		BeanChooseRecipes beanChooseRecipe = ChooseRecipesController.takeFood();
		ArrayList<String> expected = new ArrayList<String>();
		expected.add(food2.getName());
		expected.add(food1.getName());
		ArrayList<String> actualList = (ArrayList<String>) beanChooseRecipe.getListFood();
		assertEquals( expected, actualList);
	}

	@Test
	public void testStartSearch() {
		
		BeanChooseRecipes beanChooseRecipes = new BeanChooseRecipes();
		beanChooseRecipes.setNumRecipes(1);
		ArrayList<String> listFoodToExclude = new ArrayList<String>();
		listFoodToExclude.add("Banana");
		
		beanChooseRecipes.setListFood(listFoodToExclude);
		
		ChooseRecipesController chooseRecipesController = new ChooseRecipesController();
		beanChooseRecipes = chooseRecipesController.startSearch(beanChooseRecipes);
		boolean res = false;
		if( beanChooseRecipes.getListRecipe() != null) {
			res = true;
		}
		assertEquals( true, res);
	}
	
}
