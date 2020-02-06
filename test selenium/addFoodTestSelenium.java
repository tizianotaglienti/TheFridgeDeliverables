package testSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

//tested by Di Battista Mattia
public class addFoodTestSelenium {

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver","Drivers/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/TheFridgeWeb/");
		

		driver.findElement(By.xpath("/html/body/div/div[2]/form/div[2]/div/h5/a")).click();
		driver.findElement(By.xpath("/html/body/div/form/div/input[1]")).sendKeys("teset19");
		driver.findElement(By.xpath("/html/body/div/form/div/input[2]")).sendKeys("teset@gmail.com");
		driver.findElement(By.xpath("/html/body/div/form/div/input[3]")).sendKeys("123456");
		driver.findElement(By.xpath("/html/body/div/form/div/input[4]")).click();
		
		driver.findElement(By.xpath("/html/body/div[1]/header/nav/ul[1]/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"tags\"]")).sendKeys("Banana");
		driver.findElement(By.xpath("//*[@id=\"quantityInpId\"]")).sendKeys("3");
		driver.findElement(By.xpath("//*[@id=\"addFoodButtonId\"]")).click();
		driver.findElement(By.xpath("/html/body/div[1]/header/nav/ul[1]/li[1]/a")).click();	
		

		WebElement TxtBoxContent1 = driver.findElement(By.xpath("/html/body/div[2]/div/form/table/tbody/tr/td[1]/label[text()='Banana']"));
		String name = TxtBoxContent1.getText();
		System.out.print(name + "\n");
		
		WebElement TxtBoxContent2 = driver.findElement(By.xpath("/html/body/div[2]/div/form/table/tbody/tr/td[2]/label[text()='3']"));
		String quantity = TxtBoxContent2.getText();
		System.out.print(quantity);
		
		driver.close();
 
	}
}
	

