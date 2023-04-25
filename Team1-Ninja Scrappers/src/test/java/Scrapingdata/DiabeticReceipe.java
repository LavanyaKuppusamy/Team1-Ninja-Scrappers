package Scrapingdata;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import org.openqa.selenium.NoSuchElementException;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;

public class DiabeticReceipe {

	WebDriver driver;

	JavascriptExecutor je = (JavascriptExecutor) driver;

	@Test(enabled = false)

	// @Test(priority = 1)

	public void LaunchBrowser() throws Exception {

		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();

		options.addArguments("--remote-allow-origins=*");

		driver = new ChromeDriver(options);

		driver.get("https://www.tarladalal.com/");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		driver.manage().window().maximize();

		driver.findElement(By.id("ctl00_txtsearch")).sendKeys("diabetic receipe");

		driver.findElement(By.xpath("//input[@type='submit']")).click();

		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollBy(0,50)");

		WebElement clickthelink = driver.findElement(By.xpath("//a[@class='rcpsrch_suggest']"));
		clickthelink.click();

		je.executeScript("window.scrollBy(0,5000)");

		Receipedetails();
	}
	
	

	public void Receipedetails() throws IOException, Exception {

		Thread.sleep(50);
		File file = new File("C:\\Users\\bsethur\\eclipse-workspace\\Test\\src\\test\\resources\\DiabeticReceipe.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheet("Sheet2");
		int rownum = 0;
		XSSFRow headingrow = sheet.createRow(0);
		headingrow.createCell(0).setCellValue("RecipeId");
		headingrow.createCell(1).setCellValue("RecipeName");
		headingrow.createCell(2).setCellValue("Recipe Category");
		headingrow.createCell(3).setCellValue("Food Category");
		headingrow.createCell(4).setCellValue("Ingredients");
		headingrow.createCell(5).setCellValue("Preparation Time");
		headingrow.createCell(6).setCellValue("Cooking Time");
		headingrow.createCell(7).setCellValue("method");
		headingrow.createCell(8).setCellValue("Nutrient");
		headingrow.createCell(9).setCellValue("Targetted");
		headingrow.createCell(10).setCellValue("Recipe URL");

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

				WebElement receipe = driver.findElement(By.xpath("//div[@class='recipelist']/article[" + i + "]"));
				String receipeId = receipe.getText().substring(0, 13);
				System.out.println(receipeId);
				row.createCell(0).setCellValue(receipeId);

				WebElement receipename = driver
						.findElement(By.xpath("//div[@class='recipelist']/article[" + i + "]/div[3]"));
				String receipeName = receipename.getText().substring(0, 30);
				row.createCell(1).setCellValue(receipeName);
				System.out.println(receipeName);

				WebElement item1 = driver
						.findElement(By.xpath("//div[@class='recipelist']/article[" + i + "]/div[3]/span/a"));
				item1.click();
				JavascriptExecutor je = (JavascriptExecutor) driver;
				je.executeScript("window.scrollBy(0,200)");

				try {
					WebElement receipeCat = driver.findElement(By.xpath("//a[@itemprop='recipeCategory'][1]"));
					if (receipeCat.isDisplayed()) {
						
						java.lang.String receipeCategory = receipeCat.getText();
						if (receipeCategory != "null") {
							row.createCell(2).setCellValue(receipeCategory);
							System.out.println(receipeCategory);
							je.executeScript("window.scrollBy(0,200)");
						} else {
							row.createCell(2).setCellValue("Not Applicable");
						}
					} else {
						row.createCell(2).setCellValue("Not Applicable");
					}
				} catch (NoSuchElementException e) {

				}

				if(driver.getPageSource().contains("Veg")) {
					String foodcategory = "Veg";
					row.createCell(3).setCellValue(foodcategory);
					je.executeScript("window.scrollBy(0,200)");
				}else if(driver.getPageSource().contains("punjabi")) {
				String foodcategory = "Veg-punjabi";
				row.createCell(3).setCellValue(foodcategory);
				je.executeScript("window.scrollBy(0,200)");

				}else if(driver.getPageSource().contains("gujarati")) {
					String foodcategory = "Veg-Gurajati";
					row.createCell(3).setCellValue(foodcategory);
					je.executeScript("window.scrollBy(0,200)");

					}else if(driver.getPageSource().contains("South Indian")) {
					String foodcategory = "Veg-South Indian";
					row.createCell(3).setCellValue(foodcategory);
					je.executeScript("window.scrollBy(0,200)");

					}else if(driver.getPageSource().contains("Chinese")) {
					String foodcategory = "Veg-Chinese";
					row.createCell(3).setCellValue(foodcategory);
					je.executeScript("window.scrollBy(0,200)");

					}else if(driver.getPageSource().contains("Italian")) {
						String foodcategory = "Veg-Italian";
						row.createCell(3).setCellValue(foodcategory);
						je.executeScript("window.scrollBy(0,200)");
						}
					else {
						String foodcategory = "Veg";
						row.createCell(3).setCellValue(foodcategory);
						je.executeScript("window.scrollBy(0,200)");
			}
				
				
				
				
				
				
				try {
					WebElement Ingredients = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
					if (Ingredients.isDisplayed()) {
						java.lang.String Ingredientstext = Ingredients.getText();
						if (Ingredientstext != null) {
							row.createCell(4).setCellValue(Ingredientstext);
							System.out.println(Ingredientstext);
							je.executeScript("window.scrollBy(0,200)");
						} else {
							row.createCell(4).setCellValue("Not Applicable");
						}
					} else {
						row.createCell(4).setCellValue("Not Applicable");
					}
				} catch (NoSuchElementException e) {

				}

				try {
					WebElement Preparationtime = driver.findElement(By.xpath("//time[@itemprop='prepTime']"));
					if (Preparationtime.isDisplayed()) {
						String Preparationtimetext = Preparationtime.getText();
						if (Preparationtimetext != null) {
							row.createCell(5).setCellValue(Preparationtimetext);
							System.out.println(Preparationtimetext);
							je.executeScript("window.scrollBy(0,100)");
						} else {
							row.createCell(5).setCellValue("Not Applicable");
						}
					} else {

						row.createCell(5).setCellValue("Not Applicable");
					}
				} catch (NoSuchElementException e) {
					e.printStackTrace();

				}

				try {
					WebElement Cookingtime = driver.findElement(By.xpath("//time[@itemprop='cookTime']"));
					if (Cookingtime.isDisplayed()) {
						String Cookingtimetext = Cookingtime.getText();
						if (Cookingtimetext != null) {
							row.createCell(6).setCellValue(Cookingtimetext);
							System.out.println(Cookingtime);
							je.executeScript("window.scrollBy(0,100)");
						} else {
							row.createCell(6).setCellValue("Not Applicable");
						}
					} else {
						row.createCell(6).setCellValue("Not Applicable");
					}
				}

				catch (NoSuchElementException e) {
					e.printStackTrace();

				}

				try {
					WebElement preparationmethod = driver.findElement(By.xpath("//ol[@itemprop='recipeInstructions']"));
					if (preparationmethod.isDisplayed()) {
						String prepaerationmethodtext = preparationmethod.getText();
						if (prepaerationmethodtext != null) {
							row.createCell(7).setCellValue(prepaerationmethodtext);
							System.out.println(preparationmethod);
							je.executeScript("window.scrollBy(0,100)");
						} else {
							row.createCell(7).setCellValue("Not Applicable");
						}
					} else {
						row.createCell(7).setCellValue("Not Applicable");
					}
				} catch (NoSuchElementException e) {
					e.printStackTrace();

				}

				try {
					WebElement NutrientsValue = driver.findElement(By.xpath("//table[@id='rcpnutrients']/tbody"));
					if (NutrientsValue.isDisplayed()) {
						String NutrientsValuetext = NutrientsValue.getText();
						if (NutrientsValuetext != null) {
							row.createCell(8).setCellValue(NutrientsValuetext);
							System.out.println(NutrientsValue);
							je.executeScript("window.scrollBy(0,100)");
						} else {
							row.createCell(8).setCellValue("Not Applicable");
						}
					} else {
						row.createCell(8).setCellValue("Not Applicable");
					}
				} catch (NoSuchElementException e) {
					e.printStackTrace();

				}

				row.createCell(9).setCellValue("Diabetic");
				je.executeScript("window.scrollBy(0,100)");

				String receipeurl = driver.getCurrentUrl();
				row.createCell(10).setCellValue(receipeurl);
				System.out.println(receipeurl);
				je.executeScript("window.scrollBy(0,100)");

				FileOutputStream outputStream = new FileOutputStream(
						"C:\\Users\\bsethur\\eclipse-workspace\\Test\\src\\test\\resources\\DiabeticReceipe.xlsx");
				wb.write(outputStream);

				driver.navigate().back();

			}
		}

		wb.close();

	}

	@Test(priority = 2)
	public void Eliminateingredientients() throws IOException {

		File file = new File("C:\\Users\\bsethur\\eclipse-workspace\\Test\\src\\test\\resources\\DiabeticReceipe.xlsx");
		FileInputStream inputStream1 = new FileInputStream(file);
		XSSFWorkbook wbs = new XSSFWorkbook(inputStream1);
		XSSFSheet sheet = wbs.getSheet("Sheet2");
		int LastRowNum = sheet.getLastRowNum();
		for (int i = 1; i <= LastRowNum; i++) {
			Row row = sheet.getRow(i);

			if (row.getCell(4).toString().toLowerCase().contains("rice")
					|| row.getCell(4).toString().toLowerCase().contains("rava")
					|| row.getCell(4).toString().toLowerCase().contains("sugar")
					|| row.getCell(4).toString().toLowerCase().contains("corn")
					|| row.getCell(4).toString().toLowerCase().toLowerCase().contains("white bread")
					|| row.getCell(4).toString().toLowerCase().contains("pasta")
					|| row.getCell(4).toString().toLowerCase().contains("processed grains")
					|| row.getCell(4).toString().toLowerCase().contains("cream of rice")
					|| row.getCell(4).toString().toLowerCase().contains("refined flour")
					|| row.getCell(4).toString().toLowerCase().contains("soda")
					|| row.getCell(4).toString().toLowerCase().contains("flavoured water")
					|| row.getCell(4).toString().toLowerCase().contains("gatorade")
					|| row.getCell(4).toString().toLowerCase().contains("apple juice")
					|| row.getCell(4).toString().toLowerCase().contains("orange juice")
					|| row.getCell(4).toString().toLowerCase().contains("pomegranate juice")
					|| row.getCell(4).toString().toLowerCase().contains("peanut butter")
					|| row.getCell(4).toString().toLowerCase().contains("spreads")
					|| row.getCell(4).toString().toLowerCase().contains("frozen foods")
					|| row.getCell(4).toString().toLowerCase().contains("Flavoured curd")
					|| row.getCell(4).toString().toLowerCase().contains("Flavoured yogurt")
					|| row.getCell(4).toString().toLowerCase().contains("flakes")
					|| row.getCell(4).toString().toLowerCase().contains("honey")
					|| row.getCell(4).toString().toLowerCase().contains("maple syrup")
					|| row.getCell(4).toString().toLowerCase().contains("jaggery")
					|| row.getCell(4).toString().toLowerCase().contains("sweets")
					|| row.getCell(4).toString().toLowerCase().contains("candies")
					|| row.getCell(4).toString().toLowerCase().contains("chocolates")
					|| row.getCell(4).toString().toLowerCase().contains("all purpose flour")
					|| row.getCell(4).toString().toLowerCase().contains("alcohol")
					|| row.getCell(4).toString().toLowerCase().contains("jams")
					|| row.getCell(4).toString().toLowerCase().contains("jelly")
					|| row.getCell(4).toString().toLowerCase().contains("mango")
					|| row.getCell(4).toString().toLowerCase().contains("banana")
					|| row.getCell(4).toString().toLowerCase().contains("butter")
					|| row.getCell(4).toString().toLowerCase().contains("cheese")
					|| row.getCell(4).toString().toLowerCase().contains("pineapple")
					|| row.getCell(4).toString().toLowerCase().contains("pickled")
					|| row.getCell(4).toString().toLowerCase().contains("maida")
					||row.getCell(4).toString().toLowerCase().contains("mayonnise")
					||row.getCell(4).toString().toLowerCase().contains("palmolien oil")
					||row.getCell(4).toString().toLowerCase().contains("dried food")
					||row.getCell(4).toString().toLowerCase().contains("maida")
					||row.getCell(4).toString().toLowerCase().contains("refined flour")
					||row.getCell(4).toString().toLowerCase().contains("maida")
					||row.getCell(4).toString().toLowerCase().contains("canned fruits")
					||row.getCell(4).toString().toLowerCase().contains("canned vegatables")
					||row.getCell(4).toString().toLowerCase().contains("chips")
					||row.getCell(4).toString().toLowerCase().contains("mayonnaise")
					||row.getCell(4).toString().toLowerCase().contains("palmolein oil")
					||row.getCell(4).toString().toLowerCase().contains("dried food")
					||row.getCell(4).toString().toLowerCase().contains("baked food")
					||row.getCell(4).toString().toLowerCase().contains("sweetend milk")
					||row.getCell(4).toString().toLowerCase().contains("sweetend tea")
					||row.getCell(4).toString().toLowerCase().contains("packaged snacks")
					||row.getCell(4).toString().toLowerCase().contains("soft drinks")
					||row.getCell(4).toString().toLowerCase().contains("banana")
					||row.getCell(4).toString().toLowerCase().contains("melon")
					||row.getCell(4).toString().toLowerCase().contains("milk"))

			{

				sheet.removeRow(row);
				FileOutputStream outputStream = new FileOutputStream(
						"C:\\Users\\bsethur\\eclipse-workspace\\Test\\src\\test\\resources\\DiabeticReceipe.xlsx");
				wbs.write(outputStream);
			}
		}

		wbs.close();
	}

	@Test(priority = 3)
	public void ToAdditemsingredientients() throws IOException {

		File file = new File("C:\\Users\\bsethur\\eclipse-workspace\\Test\\src\\test\\resources\\DiabeticReceipe.xlsx");
		FileInputStream inputStream1 = new FileInputStream(file);
		XSSFWorkbook wbs = new XSSFWorkbook(inputStream1);
		XSSFSheet sheet = wbs.getSheet("Sheet1");
		int LastRowNum = sheet.getLastRowNum();
		for (int i = 1; i <= LastRowNum; i++) {
			Row row = sheet.getRow(i);

			if (row.getCell(4).toString().toLowerCase().contains("broccoli")
					|| row.getCell(4).toString().toLowerCase().contains("pumpkin")
					|| row.getCell(4).toString().toLowerCase().contains("pumpkin seeds")
					|| row.getCell(4).toString().toLowerCase().contains("chia seeds")
					|| row.getCell(4).toString().toLowerCase().toLowerCase().contains("flaxseeds")
					|| row.getCell(4).toString().toLowerCase().contains("apples")
					|| row.getCell(4).toString().toLowerCase().contains("lady finger")
					|| row.getCell(4).toString().toLowerCase().contains("okra")
					|| row.getCell(4).toString().toLowerCase().contains("soda")
					|| row.getCell(4).toString().toLowerCase().contains("beans")
					|| row.getCell(4).toString().toLowerCase().contains("raspberries")
					|| row.getCell(4).toString().toLowerCase().contains("strawberries")
					|| row.getCell(4).toString().toLowerCase().contains("blueberries")
					|| row.getCell(4).toString().toLowerCase().contains("blackberries")
					|| row.getCell(4).toString().toLowerCase().contains("eggs")
					|| row.getCell(4).toString().toLowerCase().contains("yogurt")
					|| row.getCell(4).toString().toLowerCase().contains("bitter guard")
					|| row.getCell(4).toString().toLowerCase().contains("Rolled oats")
					|| row.getCell(4).toString().toLowerCase().contains("Steel cut oats")
					|| row.getCell(4).toString().toLowerCase().contains("quinoa")
					|| row.getCell(4).toString().toLowerCase().contains("mushroom"))

			{

				FileOutputStream outputStream = new FileOutputStream(
						"C:\\Users\\bsethur\\eclipse-workspace\\Test\\src\\test\\resources\\Toadditems.xlsx");
				wbs.write(outputStream);
			}
		}

		wbs.close();
	}

	@Test(priority = 4)
	public void EliminatingAllergyitems() throws IOException {

		File file = new File(
				"C:\\Users\\bsethur\\eclipse-workspace\\Test\\src\\test\\resources\\DiabeticReceipe_empty.xlsx");
		FileInputStream inputStream1 = new FileInputStream(file);
		XSSFWorkbook wbs = new XSSFWorkbook(inputStream1);
		XSSFSheet sheet = wbs.getSheet("Sheet1");
		int LastRowNum = sheet.getLastRowNum();

		for (int i = 1; i <= LastRowNum; i++) {
			System.out.println("Executing line number" + i);
			Row row = sheet.getRow(i);
			verifyAndWriteToFile(row, "milk");
			verifyAndWriteToFile(row, "soy");

			verifyAndWriteToFile(row, "egg");
			verifyAndWriteToFile(row, "sesame");
			verifyAndWriteToFile(row, "peanuts");
			verifyAndWriteToFile(row, "walnut");
			verifyAndWriteToFile(row, "almond");
			verifyAndWriteToFile(row, "hazelnut");
			verifyAndWriteToFile(row, "pecan");
			verifyAndWriteToFile(row, "cashew");
			verifyAndWriteToFile(row, "pistacho");
			
			if (row.getCell(4).toString().toLowerCase().contains("broccoli")
					|| row.getCell(4).toString().toLowerCase().contains("pumpkin")
					|| row.getCell(4).toString().toLowerCase().contains("pumpkin seeds")
					|| row.getCell(4).toString().toLowerCase().contains("chia seeds")
					|| row.getCell(4).toString().toLowerCase().toLowerCase().contains("flaxseeds")
					|| row.getCell(4).toString().toLowerCase().contains("apples")
					|| row.getCell(4).toString().toLowerCase().contains("lady finger")
					|| row.getCell(4).toString().toLowerCase().contains("okra")
					|| row.getCell(4).toString().toLowerCase().contains("soda")
					|| row.getCell(4).toString().toLowerCase().contains("beans")
					|| row.getCell(4).toString().toLowerCase().contains("raspberries")
					|| row.getCell(4).toString().toLowerCase().contains("strawberries")
					|| row.getCell(4).toString().toLowerCase().contains("blueberries")
					|| row.getCell(4).toString().toLowerCase().contains("blackberries")
					|| row.getCell(4).toString().toLowerCase().contains("eggs")
					|| row.getCell(4).toString().toLowerCase().contains("yogurt")
					|| row.getCell(4).toString().toLowerCase().contains("bitter guard")
					|| row.getCell(4).toString().toLowerCase().contains("rolled oats")
					|| row.getCell(4).toString().toLowerCase().contains("steel cut oats")
					|| row.getCell(4).toString().toLowerCase().contains("quinoa")
					|| row.getCell(4).toString().toLowerCase().contains("mushroom")) {
				writeToFile("ToAddItems", row, "DiabeticsReceipe.xlsx");
				
			}
		}

		wbs.close();
	}

	private void verifyAndWriteToFile(Row row, String verifyString) throws IOException {
		String sheetName;
		if (!row.getCell(4).toString().toLowerCase().contains(verifyString)) {
			sheetName = "Diabetic_"+verifyString+"_Allergy";
			writeToFile(sheetName, row, "Allergy.xlsx");
		}
	}

	private void writeToFile(String sheetName, Row newRowData, String fileName) throws IOException {

		File file = new File("C:\\Users\\bsethur\\eclipse-workspace\\Test\\src\\test\\resources\\"+fileName);
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = WorkbookFactory.create(inputStream);

		// specify the name of the sheet to find

		// find the index of the sheet with the given name
		int sheetIndex = workbook.getSheetIndex(sheetName);

		if (sheetIndex == -1) {
			workbook.createSheet(sheetName);
		}

		// get the Sheet object representing the sheet with the given name
		Sheet sheet = workbook.getSheet(sheetName);

		// create a new Row object representing the first row of the sheet

		Row row = sheet.createRow(sheet.getLastRowNum() + 1);

		Iterator<Cell> itr = newRowData.cellIterator();

		int cellCount = 0;
		while (itr.hasNext()) {
			Cell temp = itr.next();
			Cell newCell = row.createCell(cellCount);
			newCell.setCellValue(temp.getStringCellValue());

			cellCount++;

		}

		// save the changes to the Excel file
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();

		// close the FileInputStream and the Workbook
		inputStream.close();
		workbook.close();

	}
	

	
	@AfterTest
 public void teardown() {

driver.close();

}

}
