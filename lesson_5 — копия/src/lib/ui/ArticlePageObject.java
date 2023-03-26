package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text = 'View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView",
            ADD_TO_MY_LIST_OVERlLAY = "org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";


    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE),
                "Cannot find article title",
                15);
    }

    // Получаем название строки
    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    // Свайп
    public void swipeToFooter(){
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20);
    }

    // ДОБАВЛЯЕМ СТАТЬЮ В СВОЙ ЛИСТ
    public void addArticleToMyList(String name_of_folder){
        // Переходим в меню (...) статьи
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),  //android.widget.ImageView[@content-desc='More options'
                "Cannot find button to open article options",
                15);

        // Добавляем в чтение
        this.waitForElementAndClick(
                //By.xpath("//*[@text='Add to reading list']"),
                By.xpath(OPTIONS_ADD_TO_MY_LIST),
                "Cannot find option to add article to reading list",
                15);

        // Кликаем кнопку добавить - подверждение
        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERlLAY),
                "Cannot find 'Got it' tip overlay",
                5);

        // После всплывает окно с введением названия списка для чтения

        // Очищаем дефолтное значение
        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of articles folder",
                5        );

        // Вводим новое имя
        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into articles folder input",
                5        );

        // Подтверждаем
        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                5
        );
    }
    // Закрываем статю
    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article",
                5
        );
    }
}
