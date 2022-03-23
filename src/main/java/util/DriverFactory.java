package util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    WebDriver driver;
    public static ThreadLocal<RemoteWebDriver> threadLocalDriver = new ThreadLocal<>();

    /**
     * @param browser
     * @return
     */
    public WebDriver initDriver(String browser) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        System.out.println("browser value is: " + browser);
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            threadLocalDriver.set(new ChromeDriver());
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            threadLocalDriver.set(new FirefoxDriver());
        } else if (browser.equals("safari")) {
            WebDriverManager.safaridriver().setup();
            threadLocalDriver.set(new SafariDriver());
        } else if (browser.equals("opera")) {
            WebDriverManager.operadriver().setup();
            threadLocalDriver.set(new OperaDriver());
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            threadLocalDriver.set(new EdgeDriver());
        } else if (browser.equals("ie")) {
            WebDriverManager.iedriver().setup();
            threadLocalDriver.set(new InternetExplorerDriver());
        } else if (browser.equals("androidApp")) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("platformVersion", "9");
            capabilities.setCapability("deviceName", "emulator-5554");
            capabilities.setCapability("appPackage", "com.modanisa.debug");
            capabilities.setCapability("appActivity", "com.modanisa.activity.SplashActivity");
            threadLocalDriver.set(new RemoteWebDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities));
        } else if (browser.equals("androidBrowser")) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("browser", "chrome");
            capabilities.setCapability("platformVersion", "9");
            capabilities.setCapability("deviceName", "emulator-5554");
            threadLocalDriver.set(new RemoteWebDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities));
        } else if (browser.equals("iosBrowser")) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("browser", "safari");
            capabilities.setCapability("platformVersion", "9.0");
            capabilities.setCapability("udid", "D13B777C-E0A4-4E8F-87CF-2EDEB45A44D5");
            threadLocalDriver.set(new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities));
        } else if (browser.equals("iosApp")) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("platformVersion", "9.0");
            capabilities.setCapability("udid", "D13B777C-E0A4-4E8F-87CF-2EDEB45A44D5");
            capabilities.setCapability("appPackage", "com.modanisa.iPhone.debug");
            capabilities.setCapability("appActivity", "com.modanisa.activity.SplashActivity");
            threadLocalDriver.set(new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities));
        } else {
            System.out.println("Please pass the correct browser value: " + browser);
        }
        if (!browser.equals("androidApp") && !browser.equals("androidBrowser")&& !browser.equals("iosApp")&& !browser.equals("iosBrowser")) {
            getDriver().manage().deleteAllCookies();
            getDriver().manage().window().maximize();
            getDriver().manage().timeouts().pageLoadTimeout(10000, TimeUnit.SECONDS);
        }
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return getDriver();
    }

    /**
     * @return
     */
    public static synchronized WebDriver getDriver() {
        return threadLocalDriver.get();
    }
}
