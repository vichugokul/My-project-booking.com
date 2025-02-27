package utils;

import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class PropertyReader {

    public static String getPropertyValue(String file, String key) throws IOException {
        Properties prop = new Properties();
        prop.load(Files.newInputStream(new File(file).toPath()));
        return prop.get(key).toString();
    }

    public static void  setPropertyValue(String file, String key, String value) throws IOException {
        Properties prop = new Properties();
        prop.load(Files.newInputStream(new File(file).toPath()));
        prop.setProperty(key, value);
    }

    public static By getLocator(String file, String key) throws IOException {

        String elementLocator = getPropertyValue(file, key);
        By by = null;

        String[] locator = elementLocator.split(";");
        System.out.println(locator[0]+ " : "+locator[1]);

        switch (locator[0]) {
            case "id":
                by = By.id(locator[1]);
                break;
            case "name":
                by =  By.name(locator[1]);
                break;
            case "cssSelector":
                by = By.cssSelector(locator[1]);
                break;
            case "xpath":
                by = By.xpath(locator[1]);
                break;
            case "linkText":
                by = By.linkText(locator[1]);
                break;
            case "partialLinkText":
                by = By.partialLinkText(locator[1]);
                break;
            case "tagname":
                by = By.tagName(locator[1]);
                break;
            case "class":
                by = By.className(locator[1]);
                break;
            default:
                System.out.println("Invalid");
                break;
        }

        return by;

    }



}
