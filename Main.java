package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {
    WebDriver driver;

    @BeforeEach
    public void init(){
        System.setProperty("webdriver.edge.driver", "D:\\college\\Testing\\edgedriver_win64\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.navigate().to("https://www.imdb.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void testSearchMovie() {

        String movieName = "The Shawshank Redemption";

        WebElement searchInput = driver.findElement(By.name("q"));
        searchInput.sendKeys(movieName);

        WebElement searchButton = driver.findElement(By.id("suggestion-search-button"));
        searchButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement a = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[3]/section/div/div[1]/section[2]/div[2]/ul/li[1]/div[2]/div/a"));
        Assertions.assertEquals(movieName, a.getText().trim());
        driver.close();


    }
    @Test
    public void test_top250() {
        String movieName = "1. The Shawshank Redemption";
        String Header = "IMDb Top 250 Movies";

        WebElement dropdownmenue = driver.findElement(By.xpath("//*[@id=\"imdbHeader-navDrawerOpen\"]"));
        dropdownmenue.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement top250 = driver.findElement(By.xpath("//*[@id=\"imdbHeader\"]/div[2]/aside[1]/div/div[2]/div/div[1]/span/div/div/ul/a[2]\n"));
        top250.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement header = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[3]/section/div/div[1]/div/div[2]/hgroup/h1\n"));
        Assertions.assertEquals(Header, header.getText().trim());
        WebElement firstmovie = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[3]/section/div/div[2]/div/ul/li[1]/div[2]/div/div/div[1]/a/h3"));
        Assertions.assertEquals(movieName,firstmovie.getText().trim());
        driver.close();

    }


    @Test
    public void Advanced_Search() throws InterruptedException {
        String Header = "Advanced title search";
        String start = "2010";
        String end = "2020";
        String sort = "Popularity";

        WebElement dropdownmenue = driver.findElement(By.xpath("//*[@id=\"nav-search-form\"]/div[1]/div/label"));
        dropdownmenue.click();
        WebElement advancedSearchLink = driver.findElement(By.xpath("//*[@id=\"navbar-search-category-select-contents\"]/ul/a/span[2]"));
        advancedSearchLink.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement header = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[3]/section/section/div/section/div/hgroup/h1"));
        Assertions.assertEquals(Header, header.getText().trim());
        WebElement Title = driver.findElement(By.xpath("//*[@id=\"titleTypeAccordion\"]/div[1]/label"));
        Title.click();
        WebElement movie = driver.findElement(By.xpath("//*[@id=\"accordion-item-titleTypeAccordion\"]/div/section/button[1]"));
        movie.click();
        Title.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement clickableGenre = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"genreAccordion\"]/div[1]/label")));
        clickableGenre.click();
        WebElement element = driver.findElement(By.xpath("//*[@id=\"accordion-item-genreAccordion\"]/div/section/button[1]"));
        scrollToElementInView(element);
        Thread.sleep(500);
        WebElement action = driver.findElement(By.xpath("//*[@id=\"accordion-item-genreAccordion\"]/div/section/button[1]"));
        scrollToElementInView(action); // Ensure the element is in view
        wait.until(ExpectedConditions.elementToBeClickable(action)).click(); // Wait until clickable and then click

        clickableGenre.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement release = driver.findElement(By.xpath("//*[@id=\"releaseDateAccordion\"]/div[1]/label\n"));
        release.click();
        WebElement starttime = driver.findElement(By.name("release-yearmonth-start-input"));
        starttime.sendKeys(start);
        WebElement endtime = driver.findElement(By.name("release-yearmonth-end-input"));
        endtime.sendKeys(end);
        release.click();

        WebElement seeresult = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[1]/button\n"));
        seeresult.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement popularity = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[2]/div[3]/section/section/div/section/section/div[2]/div/section/div[2]/div[2]/div[1]/div[2]/div/span/span/label"));
        Assertions.assertEquals(sort, popularity.getText().trim());
        driver.close();
    }

    private void scrollToElementInView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
    }

}
