package Scrapingdata;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
public class Hypothroridism {
	 WebDriver driver;
	 
	public HashMap<Integer, ReceipeDetails> listofReceipes = new HashMap<Integer, ReceipeDetails>();
	List<Integer> receipeIDKey = new ArrayList<Integer>();
	 	 
	public void RecipeDataopen() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*", "--disable-site-isolation-trials");
		options.addArguments("enable-automation");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-extensions");
		options.addArguments("--dns-prefetch-disable");
		options.addArguments("--disable-gpu");
		
		driver=new ChromeDriver(options);
		driver.get("https://www.tarladalal.com/");
		
		// driver.manage().window().maximize();
	} 
	 @Test(priority=1)
	 public void test001() throws IOException {
		 
		 RecipeDataopen();
		 WebElement Recipe=driver.findElement(By.xpath("//a[@href='RecipeCategories.aspx']  "));	
		 Recipe.click();
		 WebElement Hypothyroidism=driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht226"));	
		 Hypothyroidism.click();

		 
		 LoopThroughPages();

		 driver.quit();
		 
	 }
	 
	 @Test(priority=2)
	 public void test002() throws IOException {
		 
		 RecipeDataopen();
		 WebElement Recipe=driver.findElement(By.xpath("//a[@href='RecipeCategories.aspx']  "));	
		 Recipe.click();
		 WebElement Lactation=driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht260"));	
		 Lactation.click();

		 
		 LoopThroughPages();

		 driver.quit();

		 
	 }
	 @Test(priority=3)
	 public void test003() throws IOException {
		 
		 RecipeDataopen();
	
		 driver.findElement(By.id("ctl00_txtsearch")).sendKeys("diabetic");

		 driver.findElement(By.xpath("//input[@type='submit']")).click();

		 driver.findElement(By.xpath("//li[@class='rcpsrch_suggest'][2]")).click();

		 LoopThroughPages();

		 driver.quit();

		 
	 }
	 @Test(priority=4)
	 public void test004() throws IOException {
		 
		 RecipeDataopen();
		 WebElement Recipe=driver.findElement(By.xpath("//a[@href='RecipeCategories.aspx']  "));	
		 Recipe.click();
		 
		 WebElement byVegCuisine=driver.findElement(By.id("ctl00_cntleftpanel_cattreecuisine_tvCuisinet28"));	
		 byVegCuisine.click();
		 LoopThroughPages();
		 driver.quit();	 
	 }
	 
	 @Test(priority=5, alwaysRun = true)
	 public void WriteteExcel() throws IOException 
	 {
		 CreateExcel();
	 }
	 
	 public void LoopThroughPages()
	 {
		 WebElement PageIndex=driver.findElement(By.id("pagination"));	
		 List<WebElement> ListOfPages = PageIndex.findElements(By.tagName("a"));
		 
		 printConsole("no of pages "+ ListOfPages.size());
		 
		 
		 for (int i = 1; i <= ListOfPages.size(); i++)
		 {
			 WebElement pages = driver.findElement(By.xpath("//div[@id='pagination']//a[text()="+(i)+"]"));
			
			 printConsole("Recipes on Pages \n "+ (i) );
			 pages.click();
			 
			 List<WebElement> RecipeID   = driver.findElements(By.xpath("//div[@class='rcc_rcpno']"));
			 	 
			 int noOfReceipes = RecipeID.size();
			 

			 
			 for(int j = 1 ; j <= noOfReceipes; j++)
			 {
				 WebElement recipeid = driver.findElement(By.xpath("(//div[@class='recipelist']//article)["+j+"]/div[@class='rcc_rcpno']"));
		
				 printConsole("RecipeID is  " + recipeid.getText().split("\\n")[0].split(" ")[1]+ "\n\n");
				 
				
				 ReceipeDetails rcp = new ReceipeDetails();
				
				 rcp.receipeID = Integer.parseInt(recipeid.getText().split("\\n")[0].split(" ")[1]);
				 
				 receipeIDKey.add(rcp.receipeID);
		
				 
				 WebElement RecipeNames = driver.findElement(By.xpath("(//div[@class='recipelist']//article)["+j+"]/div[@class='rcc_rcpcore']/span/a"));
				 
				 printConsole("RecipeName is  " + RecipeNames.getText() + "\n\n");
				 
				 rcp.recipeName = RecipeNames.getText();
				 
				 RecipeNames.click();
				 
				 
				 String url = driver.getCurrentUrl();
				 
				 rcp.Recipe_URL = url;
				 
				 printConsole("Current URL of the receipe is :" + url + "\n");
				 
				 WebElement recipecategory = driver.findElement(By.id("recipe_tags"));	 
				 recipecategory.getText();
				 printConsole("RecipeCategory is  " + recipecategory.getText() + "\n\n");
				 
				 if(recipecategory.getText().toLowerCase().contains("lunch"))
				 {
					 rcp.RecipeCategory = "Lunch"; 
				 }
				 else if(recipecategory.getText().toLowerCase().contains("breakfast"))
				 {
					 rcp.RecipeCategory = "Breakfast";  
				 }
				 else if(recipecategory.getText().toLowerCase().contains("dinner"))
				 {
					 rcp.RecipeCategory = "Dinner";  
				 }
				 else
				 {
					 rcp.RecipeCategory = "Snack";  
				 }
				
				
				 if (driver.findElements(By.xpath("//time[@itemprop='prepTime']")).size() != 0)
				 {
					 WebElement PreparationTime=driver.findElement(By.xpath("//time[@itemprop='prepTime']"));
					
					 printConsole("PreparationTime is" +PreparationTime.getText()+ "\n\n");
								 
					 rcp.Preparation_Time = PreparationTime.getText();
				 }
				 else
				 {
					 printConsole("PreparationTime is is Not Found"+ "\n"); 
					 
					 rcp.Preparation_Time = "PreparationTime is Not Found";
				 }
				 
				 if (driver.findElements(By.xpath("//time[@itemprop='cookTime']")).size() != 0)
				 {
					 WebElement CookingTime=driver.findElement(By.xpath("//time[@itemprop='cookTime']"));	
					 printConsole("CookingTime is" +CookingTime.getText() + "\n");
				 
					 rcp.Cooking_Time = CookingTime.getText();
				 }
				 else
				 {
					 printConsole(" CookingTime is Not Found"+ "\n"); 
					 
					 rcp.Cooking_Time = "CookingTime is Not Found";
				 } 
				 WebElement Ingredients=driver.findElement(By.id("rcpinglist"));	

				 printConsole("Ingredients is" +Ingredients.getText() + "\n");
				 
				 rcp.Ingredients = Ingredients.getText();
				 
				 if(Ingredients.getText().toLowerCase().contains("egg"))
				 {
					 rcp.FoodCategory = "Eggeterian";
				 }
				 else
				 {
					 rcp.FoodCategory = "Vegetarian"; 
				 }
				 
				 WebElement PreparationMethod=driver.findElement(By.id("recipe_small_steps"));	
				 printConsole("PreparationMethod is" +PreparationMethod.getText() + "\n");
				 
				 rcp.Preparation_method = PreparationMethod.getText();
				 
				 if ((driver.findElements(By.id("rcpnutrients")).size() != 0))
				 {
					 WebElement NutrientValues=driver.findElement(By.id("rcpnutrients"));
					 printConsole("NutrientValues is" +NutrientValues .getText() + "\n");
					 
					 rcp.Nutrient_values = NutrientValues.getText();
				 }
				 else
				 {
					 printConsole("NutrientValues is Not Found"+ "\n"); 
					 
					 rcp.Nutrient_values = "NutrientValues is Not Found";
				 }
				 
		

			     printConsole("Receipe "+ rcp.recipeName+ "is added");
				 listofReceipes.put(rcp.receipeID, rcp);
				 
				 // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				 driver.navigate().back();
				 
					 
			 }
			 
			 //driver.navigate().back();
		 }
	 }
	 
	 public boolean EliminateReceipeForHypoThroidism(String ingredients)
	 {
		boolean eliminate = false;
		
		List<String> listOfAvoidableIngredients = Arrays.asList("Tofu",
														   "Edamame",
															"Tempeh",
															"Cauliflower",
															"Cabbage",
															"Broccoli",
															"Kale",
															"Spinach",
															"Sweet potatoes",
															"Strawberry",
															"strawberries",
															"Pine nuts",
															"Peanuts",
															"Peaches",
															"Green tea",
															"Coffee",
															"Alcohol",
															"Soy milk",
															"White bread",
															"Cakes",
														     "pastries",
															"Fried food",
															"Sugar",
															"Processed food- ham",
															"bacon",
													         "salami",
													         "sausages",
															"Frozen food",
															"Gluten",
															"Sodas",
															"Energy drinks containing caffeine",
															"caffeine",
															"Packaged food- noodles",
															"noodles",
															"soups",
													        "salad dressings",
															"sauces",
													        "peanut butter",
													        "brown sugar",
													        "chips",
													        "sauce",
															"Candies");
		
		for (int i = 0 ; i < listOfAvoidableIngredients.size(); i++)
		{
			if (true == ingredients.toLowerCase().contains(listOfAvoidableIngredients.get(i).toLowerCase()))
			{
				eliminate = true;
				break;
			}
			else
			{
				eliminate = false;
			}
		}
		
		 
		return eliminate;	 
	 }
	
	 public void CreateExcel() throws IOException {
		    //File file = new File("Receipe.xlsx");
			//FileInputStream inputStream = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = null;
			
			sheet = wb.createSheet("HypoThroidism");
			
			
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
			
			
			for(int i=0, rownum=1; i < receipeIDKey.size(); i++) {
			
				// Iterate Through all receipes stored
				ReceipeDetails rcp = listofReceipes.get(receipeIDKey.get(i));
					
				if (null != rcp) {
					
					boolean eliminate = false;
					
					// If recpi ingredients contain item for hypothroidism 
					// This function returns true.
					eliminate = EliminateReceipeForHypoThroidism(rcp.Ingredients);
					
					/// Eliminate Item , Doont write if it needs to be eliminated
					if (false == eliminate)
					 {
						XSSFRow row = sheet.createRow(rownum++);
						rcp.morbid_conditions = "HypoThroidism";
					
						row.createCell(0).setCellValue(rcp.receipeID);
						row.createCell(1).setCellValue(rcp.recipeName);
						row.createCell(2).setCellValue(rcp.RecipeCategory);
						row.createCell(3).setCellValue(rcp.FoodCategory);
						row.createCell(4).setCellValue(rcp.Ingredients);
						row.createCell(5).setCellValue(rcp.Preparation_Time);
						row.createCell(6).setCellValue(rcp.Cooking_Time);
						row.createCell(7).setCellValue(rcp.Preparation_method );
						row.createCell(8).setCellValue(rcp.Nutrient_values );
						row.createCell(9).setCellValue(rcp.morbid_conditions );
						row.createCell(10).setCellValue(rcp.Recipe_URL);
					 }
				}
			}
			
			FileOutputStream outputStream = new FileOutputStream("HypoThroidism_Receipe.xlsx");
			wb.write(outputStream);
			
			wb.close();
				
	 }
	 
	 void printConsole(String strToPrint)
	 {
		 boolean enablePrint=true;
		 
		 if (enablePrint == true)
		 {
			 System.out.println(strToPrint);
		 }	 
	 }
}


class ReceipeDetails
{
	public int receipeID;
	public String recipeName;
	public String RecipeCategory;
	public String FoodCategory;
	public String Ingredients;
	public String Preparation_Time;
	public String Cooking_Time;
	public String Preparation_method;
	public String Nutrient_values;
	public String morbid_conditions;
	public String Recipe_URL;
	
	ReceipeDetails()
	{
	}
}