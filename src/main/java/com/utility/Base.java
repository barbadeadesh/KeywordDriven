package com.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	protected static WebDriver driver;
	protected static Properties prop;
	protected static FileInputStream file;

	//To Initialize the WebDriver
	public WebDriver init_Driver(String browserName) {

		if (browserName.equals("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		return driver;
	}

	//To Initialize the Property file
	
	public Properties intit_Properties() {
		
		prop=new Properties();
		try {
			file=new FileInputStream(System.getProperty("user.dir")+".\\src\\main\\resources\\Repository\\config.properties");
			prop.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}return prop;
	}

}

