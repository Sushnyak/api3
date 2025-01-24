import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSuccessForm() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Захаров Даниил");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79154230549");
        // driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.cssSelector("[class='button__text']")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",result.getText().trim());
    }

    @Test
    void shouldMessageEnterName() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        driver.findElement(By.cssSelector("[class='button__text']")).click();
        WebElement message = form.findElement(By.cssSelector("[data-test-id='name'] [class='input__sub']"));
        assertTrue(message.isDisplayed());
        assertEquals("Поле обязательно для заполнения",message.getText().trim());
    }

    @Test
    void shouldMessageInvalidName() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Zaharov Daniil");
        driver.findElement(By.cssSelector("[class='button__text']")).click();
        WebElement message = form.findElement(By.cssSelector("[data-test-id='name'] [class='input__sub']"));
        assertTrue(message.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",message.getText().trim());
    }

    @Test
    void shouldMessageEnterPhone() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Захаров Даниил");
        driver.findElement(By.cssSelector("[class='button__text']")).click();
        WebElement message = form.findElement(By.cssSelector("[data-test-id='phone'] [class='input__sub']"));
        assertTrue(message.isDisplayed());
        assertEquals("Поле обязательно для заполнения",message.getText().trim());
    }

    @Test
    void shouldMessageInvalidPhone() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Захаров Даниил");
        form.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("111");
        driver.findElement(By.cssSelector("[class='button__text']")).click();
        WebElement message = form.findElement(By.cssSelector("[data-test-id='phone'] [class='input__sub']"));
        assertTrue(message.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",message.getText().trim());
    }

    @Test
    void shouldAskMarkCheckbox() {
            WebElement form = driver.findElement(By.cssSelector("form"));
            form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Захаров Даниил");
            form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79154230549");
            // driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
            driver.findElement(By.cssSelector("[class='button__text']")).click();
            WebElement ask = form.findElement(By.cssSelector("[class='checkbox checkbox_size_m checkbox_theme_alfa-on-white input_invalid']"));
            assertTrue(ask.isDisplayed());
    }
}
