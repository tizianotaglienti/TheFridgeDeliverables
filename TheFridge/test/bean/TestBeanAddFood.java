//Tested by Valerio Cristofori

package logic.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestBeanAddFood {

	@Test
	void testSetQuantity() {
		BeanAddFood beanAddFood = new BeanAddFood();
		beanAddFood.setQuantity(10);
		int quantity = beanAddFood.getQuantity();
		assertEquals(10, quantity);		
	}


	@Test
	void testSetName() {
		BeanAddFood beanAddFood = new BeanAddFood();
		beanAddFood.setName("pollo");
		String nome = beanAddFood.getName();
		assertEquals("pollo", nome);
	}


	@Test
	void testSetExpirationDate() {
		BeanAddFood beanAddFood = new BeanAddFood();
		beanAddFood.setExpirationDate("20/02/2020");
		String data = beanAddFood.getExpirationDate();
		assertEquals("20/02/2020", data);
	}

}
