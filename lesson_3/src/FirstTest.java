import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {
    private AppiumDriver driver;
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","and80");
        capabilities.setCapability("platformVersion","8.0.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:\\Users\\User\\Documents\\GitHub\\Mobile_App_Automation\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    }

    @After
    public void tearDown ()
    {
        driver.quit();
    }

    @Test
    public void firstTest() {
        //Поиск и клик
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wiki input",
                5);

        //Поиск и ввод
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        // Проверка наличия строки в поиске
        // от id искать текст ниже
        waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by Java ",
                15);
    }

    @Test
    public void testCancelSearch() {    //Тест на отмену поиска. По id

        //Дожидаемся эл-та и кликаем
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wiki input",
                5
        );

        //Поиск и ввод
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        // Очищаем введенный элемент
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        //Поиск и клик
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        //Проверка отсутсвия эл-та на странице
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still on the page",
                5
        );
    }

    @Test
    //Падение тестов - сравнение заголовков
    public void testCompareArticleTitle() {

        waitForElementAndClick( //Поиск и клик
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wiki input",
                5);

        waitForElementAndSendKeys(  //Поиск и ввод
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick( //Поиск и клик
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find Search Wiki input",
                25);

        // ожидаем элемент и сохраняем
        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        // запишем текст из заголовка по атрибуту text
        String article_title = title_element.getAttribute("text");

        //Сравнение заголовков
        Assert.assertEquals(
                 "We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    // Фреймворк
    // Поиск элемента и ожадание его появления
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds); // время ожидания
    wait.withMessage(error_message + "\n"); // текст ошибки
    return wait.until (ExpectedConditions.presenceOfElementLocated(by) ); // ожидаем элемент
    }

    //Переопределим Фреймворк, чтобы не вводить время каждый раз
    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by,  error_message, 5);
    }

    //Ожидание элемента и клик
    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    //Ожидание элемента и ввод
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    //Проверка отсутсвия эл-та на странице по ID
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    //Очитска элемента
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.clear();
    return element;
    }

}