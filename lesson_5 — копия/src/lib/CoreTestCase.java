package lib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

// Здесь инициализация

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","and80");
        capabilities.setCapability("platformVersion","8.0.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:\\Users\\User\\Documents\\GitHub\\Mobile_App_Automation\\lesson_3_HW\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL(AppiumURL),capabilities);

        // Венруть МП в обычную ориентацию!!!!!!!!!!!!!!!!
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown () throws Exception
    {
        driver.quit();
        super.tearDown();
    }

    // Поворот экрана
    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }
    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    // Свернуть МП
    protected void backgroundApp (int seconds) {
        driver.runAppInBackground(seconds);
    }

}
