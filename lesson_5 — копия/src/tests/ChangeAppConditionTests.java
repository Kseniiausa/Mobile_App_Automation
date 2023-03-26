package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

// Тесты на ориентацию и сворачивание МП
public class ChangeAppConditionTests extends CoreTestCase {
    @Test // Проверка что заголовок не меняется при повороте экрана
    public void testChangeScreenOrientationOnSearchResults(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language"); // By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String title_before_rotation = ArticlePageObject.getArticleTitle(); // Сохранить по атрибуту название статьи

        this.rotateScreenLandscape(); // Поворот экрана
        String title_after_rotation = ArticlePageObject.getArticleTitle(); // Сохранить по атрибуту название статьи после
        assertEquals( // сравнение результатов
                "Article title have been changed after screen rotation",
                title_after_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait(); //Вернем ориентацию
        String title_after2_rotation = ArticlePageObject.getArticleTitle();
        assertEquals( // сравнение результатов 2
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after2_rotation
        );
    }

    @Test // Проверка что заголовок в поиске не меняется при сворачивании МП
    public void testCheckSearchArticleInBackground() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language"); // By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),
        this.backgroundApp(2); // Свернуть МП
        //проверяем элемент
        SearchPageObject.waitForSearchResult("Object-oriented programming language"); // By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Object-oriented programming language']"),

    }
}
