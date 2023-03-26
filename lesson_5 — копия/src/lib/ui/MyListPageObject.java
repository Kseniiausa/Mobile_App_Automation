package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListPageObject extends MainPageObject{
    public MyListPageObject(AppiumDriver driver) {
        super(driver);
    }

    public static final String
            FOLDER_BY_NAME_TPL =  "//*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    // Метод по замене константы в FOLDER_BY_NAME_TPL
    private static String getFolderXpathByName (String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle (String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }


    // Находим папку в избранном
    public void openFolderByName(String name_of_folder)  {

        // Получаем новую строку
        String folder_name_xpath = getFolderXpathByName(name_of_folder);

        this.waitForElementAndClick(
                //By.xpath("//*[@text='Learning programming']"),
                //By.xpath("//*[@text='" + name_of_folder + "']"), // name_of_folder  - переменная
                By.xpath(folder_name_xpath),
                "Cannot find folder by name: " + name_of_folder,
                5
        );
    }

    // Свайпаем влево для удаления
    public void swipeByArticleToDelete (String article_title) {

        // Получаем новую строку
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForArticleToAppearByTitle(article_title); // проверяем что статья есть
        this.swipeElementToLeft(
                By.xpath(article_xpath),
                //By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );
        this.waitForArticleToDisappearByTitle(article_title);
    }

    // Проверяем что статьи нет. Передадим этот метод в метод выше
    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementNotPresent(
                By.xpath(article_xpath), //By.xpath("//*[@text='Java (programming language)']"),
                "Saved article still present with title " + article_title,
                10);
    }

    // Метод для ожидания появления статьи для свайпа
    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementPresent(
               // By.xpath(article_xpath), //
                 By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article by title " + article_title + "xpath " + article_xpath,
                10);
    }
}
