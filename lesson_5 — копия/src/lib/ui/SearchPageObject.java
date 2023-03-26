package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = '{SUBSTRING}']",
                                         //= "//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']"; // логотип путой строки

    //частично текст будет заменяться
//    "//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    //метод для замены текста
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    //находит поиск и тапает и прверяет инпут
    public void initSearchInput() {
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search input after clicking search init element");
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
    }

    // Ввод значения
    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                By.xpath(SEARCH_INPUT),
                search_line,
                "Cannot find and type into search input",
                5);
    }

    // Поиск Крестик - очистка поля
    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button",5);
    }

    // Проверка отсутсвия эл-та
    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still on the page",5);
    }

    // Нажатие на Крестик - очистка поля
    public void clickCancelSearch(){
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button",5);
    }

    // Проверка наличия строки в поиске
    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
       // String a = "//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']";
        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "Cannot find search result with substring " + substring);
    }

    // Клик
    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "Cannot find and click search result with substring " + substring, 15);
    }


    // число элементов
    public int getAmountOfFoundArticles() {
        // локатор всех результатов поиска
                this.waitForElementPresent( // ожидаем элемент
                        By.xpath(SEARCH_RESULT_ELEMENT),
                //By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find anything by the request ",
                20);
                return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT)); // число элементов

    }

    public void waitForEmptyResultsLabel(){
        this.waitForElementPresent( // ожидаем элемент
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result element ",
                15);
    }

    // Проверка что не нашлось ни одной строки
    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent (
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any results " );
    }


}


