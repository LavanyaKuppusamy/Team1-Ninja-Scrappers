package Scrapingdata;
	import java.time.Duration;
	import java.util.List;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeTest;
	import org.testng.annotations.Test;
	import io.github.bonigarcia.wdm.WebDriverManager;

	public class HyperTension extends ExcelWriter {

		@BeforeTest
		public void setup() throws InterruptedException {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().window().maximize();
			driver.get("https://www.tarladalal.com/");
			//driver.manage().timeouts().pageLoadTimeout(10 ,TimeUnit.SECONDS);
			//driver.manage().timeouts().implicitlyWait(10 ,TimeUnit.SECONDS);
		}

		@Test
		public void recipeTest() throws InterruptedException {
		
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("milk shakes for high blood pressure");
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("High blood pressure");
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("indian deserts for high blood pressure");
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys(" watermelon High blood pressure");
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("avocado high blood pressure");
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("chia seeds high blood pressure");
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("yogurt high blood pressure");
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("banana for high blood pressure");
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("dark chocolate 50 - 70% for high blood pressure");
			//driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("cinnamon");
			driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys("kale for high blood pressure");
			
			
			
			driver.findElement(By.xpath("//input[@class='txtsearch']")).sendKeys(Keys.ENTER);
			List<WebElement> linkSize = driver.findElements(By.xpath("//div[@class='rcc_rcpcore']/span/a"));
			linksCount = linkSize.size();
			for (int j = 1; j <= 8; j++) {
				driver.findElement(By.xpath("(//a[text()='" + j + "'])[1]")).click();
				for (int i = 1; i <= linksCount; i++) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0,250)", "");
					driver.findElement(By.xpath("(//span[@class='rcc_recipename'])[" + i + "]")).click();
					String ingredient = driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText();
					//ingredientList.add(ingredient);
					System.out.println("ingredient------>>" + ingredient);
					// ingredient = ingredientList.get(i).toString();
					
					if
					 ((ingredient.contains("salt") || ingredient.contains("crackers") ||
					  ingredient.contains("chips") || ingredient.contains("pretzels") ||
					  ingredient.contains("coffee") || ingredient.contains("tea") ||
					  ingredient.contains("alcohol") || ingredient.contains("frozen food") ||
					 ingredient.contains("pickles") || ingredient.contains("canned food") ||
					  ingredient.contains("fries food") || ingredient.contains("sauces") ||
					  ingredient.contains("mayonnaise") || ingredient.contains("white rice") ||
					 ingredient.contains("white bread")))
					{
						System.out.println("Inside If loop");
						System.out.println("Its not a high blood pressure recipe");
						driver.navigate().back();
						// break;
					} 
					else {
						ingredientList.add(ingredient);
						
						String currentURL = driver.getCurrentUrl();
						 RecipeLink.add(currentURL); 
						 System.out.println(currentURL);
						 Thread.sleep(1000);
						System.out.println("Inside else loop**" + ingredientList.size());
						String PrepTime = driver.findElement(By.xpath("//p//time[1]")).getText();
						prepTimeList.add(PrepTime);
						System.out.println(PrepTime);
						String cookTime = driver.findElement(By.xpath("//p//time[2]")).getText();
						cookTimeList.add(cookTime);
						System.out.println(cookTime);
						String Method = driver.findElement(By.xpath("//div[@id='recipe_small_steps']")).getText();
						MethodList.add(Method);
						System.out.println(Method);
						js.executeScript("window.scrollBy(0,5000)", "");
						Thread.sleep(2000);
						
	                    try {
						String NutrientsValue = driver.findElement(By.xpath("//table[@id='rcpnutrients']/tbody")).getText();
						NutrientList.add(NutrientsValue);
						System.out.println(NutrientsValue);
	                    }
	                    catch(Exception e) {
	                    	NutrientList.add("NA");
	                    }
						Thread.sleep(2000);
						driver.navigate().back();
						Thread.sleep(1000);
						String recipeID = driver
								.findElement(By.xpath("//div[@class='rcc_recipecard'][" + i + "]/div[2]/span ")).getText();
						recipeidList.add(recipeID);
						System.out.println(recipeID);
						String recipeName = driver.findElement(By.xpath("(//span[@class='rcc_recipename'])[" + i + "]"))
								.getText();
						recipeNameList.add(recipeName);
						System.out.println(recipeName);
						Thread.sleep(1000);
						

						
						 
					}
					// driver.findElement(By.xpath("(//a[text()='" + j + "'])[1]")).click();
					System.out.println("Page Number is : " + j);
					ExcelWriter.writeExcelSheet();
					System.out.println("Writing excelsheet");
				}
			}
		}

		@AfterTest
		public void teardown() {
			driver.close();
		}
	}


