package lib.ui;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

// Здесь методы

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject (AppiumDriver driver){ // конструктор
        this.driver = driver;
    }

    // Фреймворк
    // Ожидание элемента
    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds); // время ожидания
        wait.withMessage(error_message + "\n"); // текст ошибки
        return wait.until (ExpectedConditions.presenceOfElementLocated(by) ); // ожидаем элемент
    }

    //Переопределим Фреймворк, чтобы не вводить время каждый раз
    public WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by,  error_message, 15);
    }

    //Ожидание элемента и клик
    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    //Ожидание элемента и ввод
    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    //Проверка отсутсвия эл-та на странице по ID
    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    //Очитска элемента
    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    // EX 1
    //Проверка наличие ожидаемого текста у элемента
    public WebElement assertElementHasText (By by, String expected_text, String error_message,  long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        String search_title = element.getAttribute("text");
        Assert.assertEquals(error_message, expected_text, search_title);
        return element;
    }

    // Свайп вверх
    public void swipeUp(int timeOfSwipe) { // передаем время между нажатиями
        TouchAction action = new TouchAction(driver); //импорт из appium
        //для создания отн-ых координат
        Dimension size = driver.manage().window().getSize(); // получаем параметры девайса

        int x = size.width / 2;//нач. переменная по Х = конеч. переменная по Х
        int start_y = (int) (size.height * 0.8); //нач. переменная по У - на 80% экрана т.е. снизу
        int end_y = (int) (size.height * 0.2); //конеч. переменная по У

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    // Быстрй свайп
    public void swipeUpQuick(){
        swipeUp(200);
    }

    //Свайп пока не найдем эл-т с ограничением по свайпам
    public void swipeUpToFindElement(By by, String error_message, int max_swipes){
        // driver.findElements(by); - находит все эл-ты что есть
        // driver.findElements(by).size(); - число найденных эл-ов
        int already_swipes = 0;
        while (driver.findElements(by).size() == 0){

            // проверяем что эл-та все еще нет
            if (already_swipes > max_swipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swipes;
        }
    }

    // Свай влево
    public void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                20);
        int left_x = element.getLocation().getX(); // получаем самую левую точку
        int right_x = element.getSize().getWidth(); // прибавляем ширину элемента

        // Получаем середину элемента
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver); // инициализируем драйвер
        action
                .press(right_x, middle_y)
                .waitAction(200)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    //Число элементов на сранице
    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    // Проверка что не нашлось ни одной строки
    public void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by); // число эл-ов
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    // Получить заголовок статьи
    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeInSeconds);
        return element.getAttribute(attribute);
    }


}
