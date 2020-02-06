package testSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

//tested by Cristofori Valerio
public class registrationTestSelenium {

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver","Drivers/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/TheFridgeWeb/");
		
		driver.findElement(By.xpath("/html/body/div/div[2]/form/div[2]/div/h5/a")).click();
		driver.findElement(By.xpath("/html/body/div/form/div/input[1]")).sendKeys("seleniumTest0");
		driver.findElement(By.xpath("/html/body/div/form/div/input[2]")).sendKeys("seleniumTest0@gmail.com");
		driver.findElement(By.xpath("/html/body/div/form/div/input[3]")).sendKeys("123456");
		driver.findElement(By.xpath("/html/body/div/form/div/input[4]")).click();
		
		driver.findElement(By.xpath("/html/body/div[1]/header/nav/a/img")).click();
		
		
		WebElement TxtBoxContent1 = driver.findElement(By.xpath("/html/body/div[2]/div/form/div[2]/label[2][text()='seleniumTest0']"));
		String name = TxtBoxContent1.getText();
		System.out.print(name);

		driver.close();
	}
}








