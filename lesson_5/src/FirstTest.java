import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

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
        capabilities.setCapability("app","C:\\Users\\User\\Documents\\GitHub\\Mobile_App_Automation\\lesson_3_HW\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    }

    @After
    public void tearDown ()
    {
        driver.quit();
    }

    // Проверка соблюдения условий - что в поиске 1 строка
    @Test
    public void test_Amount_Of_Not_Empty_Search(){
        waitForElementAndClick( //Поиск и клик
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wiki input",
                5);

        String search_line = "Linkin park discography"; // что ищем
        waitForElementAndSendKeys(  //Поиск и ввод
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5);

        // локатор всех результатов поиска
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent( // ожидаем элемент
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find anything by the request " + search_line,
                20);

        // число эементов
        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
                );

        // Проверка что элементов больше 0
        Assert.assertTrue("We found too few results",
                amount_of_search_results > 0);
    }

    // Проверка - что в поиске нет строк
    @Test
    public void test_Amount_Of_Empty_Search() {
        waitForElementAndClick( //Поиск и клик
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wiki input",
                5);

        String search_line = "6546Linkin park discography"; // что ищем
        waitForElementAndSendKeys(  //Поиск и ввод
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5);

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']"; // эл-т в поиске
        String empty_result_label = "//*[@text='No results found']"; // логотип путой строки

        waitForElementPresent( // ожидаем элемент
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                15);

        // Проверка что не нашлось ни одной строки
        assertElementNotPresent (
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line );
    }

    // Проверка что заголовок не меняется при повороте экрана
    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        waitForElementAndClick( //Поиск и клик
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wiki input",
                5);

        String search_line = "Java"; // что ищем
        waitForElementAndSendKeys(  //Поиск и ввод
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5);

        waitForElementAndClick( //Поиск и клик
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                25);

        // Запишем атрибут (название строки статьи) в строку
        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        // Поворот экрана
        driver.rotate(ScreenOrientation.LANDSCAPE);

        // Запишем атрибут (название строки статьи) в строку
        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        // сравнение результатов
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_after_rotation,
                title_after_rotation
        );

        //Вернем ориентацию
        driver.rotate(ScreenOrientation.PORTRAIT);

        // Запишем атрибут (название строки статьи) в строку
        String title_after2_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        // сравнение результатов
        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after2_rotation
        );
    }


    // Проверка что заголовок в поиске не меняется при сворачивании МП
    @Test
    public void testCheckSearchArticleInBackground() {
        waitForElementAndClick( //Поиск и клик
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wiki input",
                5);

        String search_line = "Java"; // что ищем
        waitForElementAndSendKeys(  //Поиск и ввод
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5);

        waitForElementPresent( //проверяем элемент
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                25);

        // Свернуть МП
        driver.runAppInBackground(5);

        waitForElementPresent( //проверяем элемент
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
                "Cannot find article after returning from background",
                25);


    }

        //Сохранить статью в избранное и удалить
    @Test
    public void saveFirstArticleToMyList(){

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

        waitForElementPresent( // ожидаем элемент
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        // Переходим в меню (...) статьи
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),  //android.widget.ImageView[@content-desc='More options'
                "Cannot find button to open article options",
                15);

        // Добавляем в чтение
        waitForElementAndClick(
                //By.xpath("//*[@text='Add to reading list']"),
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView"),
                "Cannot find option to add article to reading list",
                15);

        // Кликаем кнопку добавить - подверждение
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5);

        // После всплывает окно с введением названия списка для чтения

        // Очищаем дефолтное значение
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5        );

        // Вводим новое
        String name_of_folder = "Learning programming"; // название папки с избранным
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5        );

        // Подтверждаем
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        // Закрываем статю
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article",
                5
        );

        // Добавляем в избранное
        waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                5
        );

        // Находим ее в избранном
        waitForElementAndClick(
                By.xpath("//*[@text='Learning programming']"),
                //By.xpath("//*[@text='" + name_of_folder + "']"), // name_of_folder  - переменная
                "Cannot find created folder",
                5
        );

        // Свайпаем влево для удаления
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        // Проверяем что статьи нет
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                10);
    }



    @Test
    //Свайп до конца страницы
    public void testSwipeArticle() {

        waitForElementAndClick( //Поиск и клик
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wiki input",
                5);

        waitForElementAndSendKeys(  //Поиск и ввод для Appium
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5);

        waitForElementAndClick( //Поиск и клик поиск среди заголовков по тексту
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = 'Appium']"),
                "Cannot find Search Appium input",
                25);

        waitForElementPresent( // ожидаем элемент
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                35
        );

        //вызов свайпа: swipeUp(2000);


        //Свайп до футера (конца страницы) с ограничением до 6 свайпов
        swipeUpToFindElement(
                By.xpath("//*[@text = 'View page in browser']"),
                "Cannot find article title",
                6

        );
    }


    //***

    // EX 1
    //Функция, которая проверяет наличие ожидаемого текста у элемента - assertElementHasText.
    // На вход эта функция должна принимать локатор элемент, ожидаемый текст и текст ошибки,
    // который будет написан в случае, если элемент по этому локатору не содержит текст, который мы ожидаем.
    //
    //И тест, который проверяет, что поле ввода для поиска статьи содержит текст
    //(в разных версиях приложения это могут быть тексты "Search..." или "Search Wikipedia",
    // правильным вариантом следует считать тот, которые есть сейчас).
    @Test
    public void testElementHasText() {

        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[@text='Search Wikipedia']"),
                "Search Wikipedia",
                "Couldn't find 'Search Wikipedia' text in Search field",
                10
        );
    }

    // EX 2
    //Написать тест, который: Ищет какое-то слово
    //Убеждается, что найдено несколько статей
    //Отменяет поиск
    //Убеждается, что результат поиска пропал
    @Test
    public void SearchForManySearchResultsAndCancel() {

        waitForElementAndClick(    //Поиск и клик
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wiki input",
                5);

        waitForElementAndSendKeys(     //Поиск и ввод
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementPresent( // ожидаем элемент и сохраняем
            By.id("org.wikipedia:id/search_results_list"),
            "Couldn't Search Results area",
            15);

    // Сохраняем список найднных заголовков у полей
    By mySelector = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']");

    List<WebElement> myElements = driver.findElements(mySelector);
    System.out.println("Number of elements:" + myElements.size());
        Assert.assertTrue(
                "Several articles were not found in Search Results area",
                myElements.size() >= 2);

    waitForElementAndClick( //Поиск и клик по Х
            By.id("org.wikipedia:id/search_close_btn"),
            "Couldn't find 'X' button to cancel search",
            5);

    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_results_list"),
            "Search results area is still present",
            5
                        );
}

    //Тест, который делает поиск по какому-то слову. Например, JAVA. Затем убеждается, что в каждом результате поиска есть это слово.
    //Работать только с теми результатами поиска, который видны сразу, без прокрутки.

    @Test
    public void ValidateSearchByJavaWord() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Couldn't find Search field",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Java",
                "Couldn't find Search field",
                5
        );

        WebElement search_area = waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Couldn't Search Results area",
                15
        );
        By mySelector = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']");

        List<WebElement> myElements = driver.findElements(mySelector);
        System.out.println("Number of elements:" +myElements.size());
        int count = 0;
        for(WebElement e : myElements) {
            System.out.println(e.getText());
            String string = e.getText();
            String substring = "Java";
            if (string.contains(substring))
            {
                count = count+1;
            }
        }
        Assert.assertTrue("There are no 'Java' word in at least one search result",
                count == 3);
    }



//***
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
    // Ожидание элемента
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

    // EX 1
    //Проверка наличие ожидаемого текста у элемента
    private WebElement assertElementHasText (By by, String expected_text, String error_message,  long timeoutInSeconds){
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    String search_title = element.getAttribute("text");
    Assert.assertEquals(error_message, expected_text, search_title);
    return element;
    }

    // Свайп вверх
    protected void swipeUp(int timeOfSwipe) { // передаем время между нажатиями
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
    protected void swipeUpQuick(){
        swipeUp(200);
    }

    //Свайп пока не найдем эл-т с ограничением по свайпам
    protected void swipeUpToFindElement(By by, String error_message, int max_swipes){
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
    protected void swipeElementToLeft(By by, String error_message) {
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
    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    // Проверка что не нашлось ни одной строки
    private void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by); // число эл-ов
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    // Получить заголовок статьи
    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeInSeconds);
        return element.getAttribute(attribute);
    }

}