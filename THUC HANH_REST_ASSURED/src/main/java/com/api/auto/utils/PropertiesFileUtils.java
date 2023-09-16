package main.java.com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class PropertiesFileUtils {
	// đường dẫn đến properties files trong folder configuration
	private static String CONFIG_PATH = "./configuration/configs.properties";
	private static String TOKEN_PATH = "./configuration/token.properties";

	// 1 lấy ra giá trị property từ config file bất kỳ theo key.
	public static String getProperty(String key) {
		Properties properties = new Properties();
		String value = null;
		FileInputStream fileInputStream = null;
		// bắt exception
		try {
			fileInputStream = new FileInputStream(CONFIG_PATH);
			properties.load(fileInputStream);
			value = properties.getProperty(key);
			return value;
		} catch (Exception ex) {
			System.out.println("Xảy ra lỗi khi đọc giá trị của  " + key);
			ex.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	// 2 save token vào file token.properties với key là "token"
	public static void saveToken(String key) {
		// khai báo properties, biến cần thiết
		Properties properties = new Properties();
		String value = null;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(TOKEN_PATH);
			properties.setProperty("token", key);
			properties.store(fileOutputStream, "Token Information");
			value = properties.getProperty(key);
			System.out.println("Token has been saved to token.properties");
		} catch (Exception ex) {
			System.out.println("Xảy ra lỗi khi save token vào file token.properties");
			ex.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 3 lấy token ra
	public static String getToken(String token) {
		Properties properties = new Properties();
		String value = null;
		FileInputStream fileInputStream = null;
		// bắt exception
		try {
			fileInputStream = new FileInputStream(TOKEN_PATH);
			properties.load(fileInputStream);
			value = properties.getProperty("token");
			return value;
		} catch (Exception ex) {
			System.out.println("Xảy ra lỗi khi đọc giá trị của token");
			ex.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

}
