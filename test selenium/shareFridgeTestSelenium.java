import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;;

public class SeleniumShareFridge {

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/TheFridgeWeb/");		
		
		driver.findElement(By.xpath("/html/body/div/div[2]/form/div[2]/div/h5/a")).click();
		driver.findElement(By.xpath("/html/body/div/form/div/input[1]")).sendKeys("invitato6");
		driver.findElement(By.xpath("/html/body/div/form/div/input[2]")).sendKeys("invitato6@gmail.com");
		driver.findElement(By.xpath("/html/body/div/form/div/input[3]")).sendKeys("123456");
		driver.findElement(By.xpath("/html/body/div/form/div/input[4]")).click();
		driver.findElement(By.xpath("/html/body/div[1]/header/nav/a/img")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div/form/input")).click();

		driver.findElement(By.xpath("/html/body/div/div[2]/form/div[2]/div/h5/a")).click();
		driver.findElement(By.xpath("/html/body/div/form/div/input[1]")).sendKeys("newname6");
		driver.findElement(By.xpath("/html/body/div/form/div/input[2]")).sendKeys("newname6@gmail.com");
		driver.findElement(By.xpath("/html/body/div/form/div/input[3]")).sendKeys("123456");
		driver.findElement(By.xpath("/html/body/div/form/div/input[4]")).click();

		driver.findElement(By.xpath("/html/body/div[1]/header/nav/ul[2]/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"usernameInpId\"]")).sendKeys("invitato6");
		driver.findElement(By.xpath("//*[@id=\"messageInpId\"]")).sendKeys("ciao");
		driver.findElement(By.xpath("//*[@id=\"inviteButtonId\"]")).click();
		driver.findElement(By.xpath("/html/body/div[1]/header/nav/a/img")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div/form/input")).click();		
		
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("invitato6");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("123456");
		driver.findElement(By.xpath("/html/body/div/div[2]/form/div[2]/div/input")).click();
		driver.findElement(By.xpath("/html/body/div[1]/header/nav/a/img")).click();
		driver.findElement(By.xpath("/html/body/div[3]/form/div[1]/table/tbody/tr[2]/td[1]/label[text()='newname6']"));
		driver.findElement(By.xpath("/html/body/div[3]/form/div[1]/table/tbody/tr[2]/td[3]/button/img")).click();
		driver.findElement(By.xpath("/html/body/div[3]/form/div[2]/div/div/button/img")).click();
		
		driver.close();
		}

}
