package Scrapingdata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExcelWriter {
	public static WebDriver driver;
	static WebElement pgno;
	public static String[] links = null;
	public static int linksCount = 0;
	
	public static ArrayList<String> recipeNameList = new ArrayList<String>();
	public static ArrayList<String> recipeidList = new ArrayList<String>();
	public static ArrayList<String> ingredientList = new ArrayList<String>();
	public static ArrayList<String> prepTimeList = new ArrayList<String>();
	public static ArrayList<String> cookTimeList = new ArrayList<String>();
	public static ArrayList<String> MethodList = new ArrayList<String>();
	public static ArrayList<String> NutrientList = new ArrayList<String>();
	public static ArrayList<String> RecipeLink = new ArrayList<String>();
	public static String Foodcategory = "Veg";
	public static String Morbidity = "HyperTension";
	public static void writeExcelSheet() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet01");

		sheet.createRow(0);
		sheet.getRow(0).createCell(0).setCellValue("RecipeId");
		sheet.getRow(0).createCell(1).setCellValue("Recipe Name");
		//sheet.getRow(0).createCell(2).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
		sheet.getRow(0).createCell(2).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
		sheet.getRow(0).createCell(3).setCellValue("Ingredients");
		sheet.getRow(0).createCell(4).setCellValue("Preparation Time");
		sheet.getRow(0).createCell(5).setCellValue("Cooking Time");
		sheet.getRow(0).createCell(6).setCellValue("Preparation method");
		sheet.getRow(0).createCell(7).setCellValue("Nutrient values");
		sheet.getRow(0).createCell(8).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		sheet.getRow(0).createCell(9).setCellValue("Recipe URL");
		int rowno = 1;
		int Totalrecipecount = recipeNameList.size();
		//String ingredient = null;
		System.out.println("ingredientList size in excel writer:::"+ingredientList.size());
		for (int i = 0; i < Totalrecipecount; i++) {
			 {
				XSSFRow row = sheet.createRow(rowno++);
				row.createCell(0).setCellValue(recipeidList.get(i).toString());
				row.createCell(1).setCellValue(recipeNameList.get(i).toString());
				row.createCell(2).setCellValue(Foodcategory);
				row.createCell(3).setCellValue(ingredientList.get(i).toString());//ingredientList.get(i).toString()
				row.createCell(4).setCellValue(prepTimeList.get(i).toString());
				row.createCell(5).setCellValue(cookTimeList.get(i).toString());
				row.createCell(6).setCellValue(MethodList.get(i).toString());
				row.createCell(7).setCellValue(NutrientList.get(i).toString());
				row.createCell(8).setCellValue(Morbidity);
				row.createCell(10).setCellValue(RecipeLink.get(i).toString());
			}

		}
		// creates demo file in the folder
		
		String path = System.getProperty("user.dir") + "/src/test/resources/Recipeexcel/kale.xlsx";
		File excelFile = new File(path);
		FileOutputStream Fos = null;
		try {
			Fos = new FileOutputStream(excelFile);

			workbook.write(Fos);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		writeExcelSheet();
	}
}