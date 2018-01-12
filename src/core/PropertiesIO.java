package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Magnus Stjernborg Koch - s175189 && Mathias
 * Translator is being handled using a properties file.
 */
public class PropertiesIO {

	private static Properties translations = new Properties();
	private static PropertiesIO propertiesIO;

	/**
	 * Translator constructor
	 * @throws IOException may throw exception if file is not found
	 */
	private PropertiesIO () {
		try {
			translations.load(ClassLoader.getSystemResourceAsStream("config.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found: "+e);
		} catch (IOException e) {
			System.out.println("IO error: "+e);
		}
	}
	/**
	 * Gets the translation of the given key
	 * @param toTranslate the key to look for
	 * @return The attached string to the key
	 */
	public static String getTranslation (String toTranslate) {
		System.out.println("Got translation: " + toTranslate);
		if (propertiesIO == null){ 
			propertiesIO = new PropertiesIO();
			}
		if(translations.getProperty(toTranslate).equals("null")) {
			System.out.println("error in io: " + toTranslate);
		}
		return translations.getProperty(toTranslate);
	}

}