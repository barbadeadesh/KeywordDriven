package com.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class KeywordDrivenEngine extends Base {

	private Workbook workbook;
	private Sheet sheet;
	private FileInputStream excelFile;
	private String locatorName = null;
	private String locatorValue = null;
	private String action;
	private String value;
	private Base base;

	public final String FILE_PATH = ".\\src\\main\\resources\\Repository\\LoginTestKeywordData.xlsx";

	public void startExecution(String sheetName) {

		try {

			excelFile = new FileInputStream(FILE_PATH); // file_path= path of xlsx sheet
			try {
				workbook = WorkbookFactory.create(excelFile); // create another excelfile file for getting the data
																// which is present in xlsx sheet
			

			}   catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		
		sheet = workbook.getSheet("LoginTestData");              // sheetName
		int lastRow =sheet.getLastRowNum();
		System.out.println("Last row of sheet:" + lastRow);      // printing the last row of sheet
		int k=0;                                                 // use variable k for handing every column we use this variable
		for (int i=0; i< lastRow; i++) {                         // we use for loop for itterating till last row
 
			try {
				

				String data = sheet.getRow(i + 1).getCell(k + 1).getStringCellValue().trim().toString(); // IT will take 1st row
																								// and 2 column
				System.out.println("Data:" + data);
				if (!data.equalsIgnoreCase("NA")) { // if Data= NA then it will skip if loop and go for nxt step
					locatorName = data.split("=")[0].trim(); // before the = evrything is store into locator name (i.e=
																// xpath is save into locator name) and other is saving
																// into locatorn value at line no 62
					System.out.println("LocatorName: " + locatorName);
					locatorValue = data.split("=")[1].trim();
					System.out.println("LocatorValue: " + locatorValue);
				}

				action = sheet.getRow(i + 1).getCell(k + 2).getStringCellValue().trim().toString(); // It will take 1st row and 3rd
																							// column
				System.out.println("Action:" + action); // action is open browser
				value = sheet.getRow(i + 1).getCell(k + 3).getStringCellValue().trim().toString(); // It will take 1st row and 4th
																						// column
				System.out.println("Value:" + value); // and value is chrome

				switch (action) {
				case "open browser":
					base = new Base();
					base.intit_Properties(); // this init_properties load the property file which is in base class
					if (value.isEmpty() || value.equals("NA")) { // If the value is empty or equal to NA then
						prop.getProperty("browser"); // data should be taken from properties file of browser
					} else { // or else we have to intialize the driver
						base.init_Driver(value);
					}

					break;

				case "enter url":
					driver.manage().window().maximize();
					driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

					if (value.isEmpty() || value.equals("NA")) {
						prop.getProperty("url");
					} else {
						driver.get(value);
					}

					break;

				case "sendkeys":

					WebElement element1 = driver.findElement(By.xpath(locatorValue));
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					element1.clear();
					element1.sendKeys(value);
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					break;

				case "click":
					WebElement element2 = driver.findElement(By.xpath(locatorValue));
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					element2.click();
					Thread.sleep(5000);
					break;
				case "quit":
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					driver.quit();
					break;

				default:
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
}
}
