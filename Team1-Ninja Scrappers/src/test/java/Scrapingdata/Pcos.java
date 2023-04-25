package Scrapingdata;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Pcos  {

	
	WebDriver driver;
	WebElement String = null;

	JavascriptExecutor je = (JavascriptExecutor) driver;
	String keyword = "pcos";
	String filename = keyword + "pcosrecipe.xlsx";
	
	//@Test(enabled=false)
	@Test(priority=1)
	public void LaunchBrowser() throws Exception {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		driver.get("https://www.tarladalal.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		
		driver.findElement(By.id("ctl00_txtsearch")).sendKeys(keyword);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
	    JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollBy(0,200)");

		driver.findElement(By.xpath("//a[@class='rcpsrch_suggest']")).click();
		je.executeScript("window.scrollBy(0,5000)");
		Receipedetails();

	}

	public void Receipedetails() throws IOException, Exception {

		Thread.sleep(50);
	
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("Sheet1");
		
	    XSSFRow headingrow = sheet.createRow(0);
		headingrow.createCell(0).setCellValue("RecipeId");
		headingrow.createCell(1).setCellValue("Recipe Name");
		headingrow.createCell(2).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
		headingrow.createCell(3).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
		headingrow.createCell(4).setCellValue("Ingredients");
		headingrow.createCell(5).setCellValue("Preparation Time");
		headingrow.createCell(6).setCellValue("Cooking Time");
		headingrow.createCell(7).setCellValue("Preparation method");
		headingrow.createCell(8).setCellValue("Nutrient values");
		headingrow.createCell(9).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		headingrow.createCell(10).setCellValue("Recipe URL");
		
        int rownum=1;

		List<WebElement> Page = driver.findElements(By.xpath("//div[@id='pagination']/a"));
		int pagesize = Page.size();
		for (int j = 1; j <= pagesize; j++) {


		WebElement pagei = driver.findElement(By.xpath("//div[@id='pagination']/a[" + j + "]"));
		pagei.click();

		List<WebElement> noofitems = driver.findElements(By.xpath("//div[@class='recipelist']/article"));
		int count = noofitems.size();
		System.out.println(count);

		for (int i = 1; i <= count; i++) {

			XSSFRow row = sheet.createRow(rownum++);
			WebElement receipeId = driver.findElement(By.xpath("//div[@class='recipelist']/article["+i+"]"));
			String St=receipeId.getText().split("\\n")[0].split(" ")[1];
			System.out.println(St);

			row.createCell(0).setCellValue(St);
			String receipeName = driver.findElement(By.xpath("//div[@class='recipelist']/article["+i+"]/div[3]")).getText();

			row.createCell(1).setCellValue(receipeName);
			System.out.println(receipeName);

			WebElement item1 = driver.findElement(By.xpath("//div[@class='recipelist']/article[" + i + "]/div[3]/span/a"));
			item1.click();

			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("window.scrollBy(0,200)");

			String receipeCategory = driver.findElement(By.xpath("//a[@itemprop='recipeCategory'][1]")).getText();
			String RecipeCategory= null;
			if(receipeCategory.toLowerCase().contains("lunch"))
			 {
				 RecipeCategory = "Lunch"; 
			 }
			 else if(receipeCategory.toLowerCase().contains("breakfast"))
			 {
				 RecipeCategory = "Breakfast";  
			 }
			 else if(receipeCategory.toLowerCase().contains("dinner"))
			 {
				 RecipeCategory = "Dinner";  
			 }
			 else
			 {
				 RecipeCategory = "Snack";  
			 }
			
			row.createCell(2).setCellValue(RecipeCategory );
			System.out.println(RecipeCategory );
			je.executeScript("window.scrollBy(0,200)");

			String Ingredients = driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText();
			row.createCell(4).setCellValue(Ingredients);
			System.out.println(Ingredients);
			je.executeScript("window.scrollBy(0,200)");
			
			String foodcategory = driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText();
			 String   foodcateg="null";                   
			if(foodcategory.contains("egg"))
			{
				 foodcateg="Eggitarian";
			}
			else
			{
				 foodcateg="Vegetarian";
			}
			row.createCell(3).setCellValue(foodcateg);
			System.out.println(foodcateg);
			je.executeScript("window.scrollBy(0,200)");

			String Preparationtime = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
			row.createCell(5).setCellValue(Preparationtime);
			je.executeScript("window.scrollBy(0,200)");

			String Cookingtime = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
			row.createCell(6).setCellValue(Cookingtime);
			System.out.println(Cookingtime);
			je.executeScript("window.scrollBy(0,100)");

			String preparationmethod = driver.findElement(By.xpath("//ol[@itemprop='recipeInstructions']")).getText();
			row.createCell(7).setCellValue(preparationmethod);
			System.out.println(preparationmethod);
			je.executeScript("window.scrollBy(0,100)");
			
            try {
			String NutrientsValue = driver.findElement(By.xpath("//table[@id='rcpnutrients']/tbody")).getText();
			row.createCell(8).setCellValue(NutrientsValue);
			System.out.println(NutrientsValue);
            }
            catch(Exception e) {
            	System.out.println("NutrientsValue:" + "null");
            	
            }
			je.executeScript("window.scrollBy(0,100)");

			row.createCell(9).setCellValue("Pcos");
			je.executeScript("window.scrollBy(0,100)");

			String receipeurl = driver.getCurrentUrl();
			row.createCell(10).setCellValue(receipeurl);
			System.out.println(receipeurl);
			je.executeScript("window.scrollBy(0,100)");
			
			
			FileOutputStream outputStream = new FileOutputStream(filename);

			wb.write(outputStream);

			driver.navigate().back();

		}}

		wb.close();

	}

	@Test(priority=2)

	public void Eliminateingredientients() throws IOException {
		
	
		XSSFWorkbook wbs = new XSSFWorkbook();
		XSSFSheet sheet = wbs.createSheet("Sheet1");
		int LastRowNum = sheet.getLastRowNum();

		for(int i=1;i<=LastRowNum;i++)
		{
			Row row = sheet.getRow(i);

			

				if(row.getCell(4).toString().toLowerCase().contains("cakes")||row.getCell(4).toString().toLowerCase().contains("pastries")||row.getCell(4).toString().toLowerCase().contains("white bread")

				 ||row.getCell(4).toString().toLowerCase().contains("fried food")||row.getCell(4).toString().toLowerCase().toLowerCase().contains("pizza")||row.getCell(4).toString().toLowerCase().contains("burger")

				 ||row.getCell(4).toString().toLowerCase().contains("carbonated beverages")||row.getCell(4).toString().toLowerCase().contains("soft drinks")||row.getCell(4).toString().toLowerCase().contains("soda")

				 ||row.getCell(4).toString().toLowerCase().contains("sugary foods")||row.getCell(4).toString().toLowerCase().contains("sweets")||row.getCell(4).toString().toLowerCase().contains("icecreams")

	            ||row.getCell(4).toString().toLowerCase().contains("juice")||row.getCell(4).toString().toLowerCase().contains("juices")||row.getCell(4).toString().toLowerCase().contains("beverages")

	            ||row.getCell(4).toString().toLowerCase().contains("icecream")||row.getCell(4).toString().toLowerCase().contains("red meat")||row.getCell(4).toString().toLowerCase().contains("processed meat")

	            ||row.getCell(4).toString().toLowerCase().contains("curd")||row.getCell(4).toString().toLowerCase().contains("yogurt")||row.getCell(4).toString().toLowerCase().contains("milk")

	            ||row.getCell(4).toString().toLowerCase().contains("buttermilk")||row.getCell(4).toString().toLowerCase().contains("condensed milk")||row.getCell(4).toString().toLowerCase().contains("sugar")

	            ||row.getCell(4).toString().toLowerCase().contains("soy")||row.getCell(4).toString().toLowerCase().contains("soya")||row.getCell(4).toString().toLowerCase().contains("soya milk")

	            ||row.getCell(4).toString().toLowerCase().contains("gluten")||row.getCell(4).toString().toLowerCase().contains("pasta")||row.getCell(4).toString().toLowerCase().contains("white rice")

	            ||row.getCell(4).toString().toLowerCase().contains("doughnuts")||row.getCell(4).toString().toLowerCase().contains("fries")||row.getCell(4).toString().toLowerCase().contains("coffee")

	            ||row.getCell(4).toString().toLowerCase().contains("seed oils")||row.getCell(4).toString().toLowerCase().contains("vegetable oil")||row.getCell(4).toString().toLowerCase().contains("soybean oil")
	 
				||row.getCell(4).toString().toLowerCase().contains("canola oil")||row.getCell(4).toString().toLowerCase().contains("rapeseed oil")||row.getCell(4).toString().toLowerCase().contains("sunflower oil")
				 
				||row.getCell(4).toString().toLowerCase().contains("safflower oil")||row.getCell(4).toString().toLowerCase().contains("pepsi")||row.getCell(4).toString().toLowerCase().contains("full fat milk")
				
				||row.getCell(4).toString().toLowerCase().contains("cococola")||row.getCell(4).toString().toLowerCase().contains("sprite")||row.getCell(4).toString().toLowerCase().contains("paneer")
	    
	            ||row.getCell(4).toString().toLowerCase().contains("Low fat Paneer")||row.getCell(4).toString().toLowerCase().contains("Cottage cheese")||row.getCell(4).toString().toLowerCase().contains("Cheese")

	            ||row.getCell(4).toString().toLowerCase().contains("sugar")||row.getCell(4).toString().toLowerCase().contains("Honey")||row.getCell(4).toString().toLowerCase().contains("Brown sugar")
	            
	            ||row.getCell(4).toString().toLowerCase().contains("Chocolate")||row.getCell(4).toString().toLowerCase().contains("Low fat milk")||row.getCell(4).toString().toLowerCase().contains("Tea")
	            
	            ||row.getCell(4).toString().toLowerCase().contains("Low fat curd")||row.getCell(4).toString().toLowerCase().contains("Dahi")||row.getCell(4).toString().toLowerCase().contains("powdered sugar")
	            
	            ||row.getCell(4).toString().toLowerCase().contains("cocoa powder")||row.getCell(4).toString().toLowerCase().contains("Butter")||row.getCell(4).toString().toLowerCase().contains("Low fat butter")
	           
	            ||row.getCell(4).toString().toLowerCase().contains("chocolate chips")||row.getCell(4).toString().toLowerCase().contains("All purpose flour"))

	            

			{

			sheet.removeRow(row);

			FileOutputStream outputStream = new FileOutputStream(filename);

			wbs.write(outputStream);
	
				}

				}

		wbs.close();

	}


@AfterTest

public void teardown() {

	driver.close();

}

}


