package org.iftm.gerenciadorveterinarios.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

/**
 * Classe base para testes Selenium WebDriver
 * Configura o ambiente de teste com Spring Boot e Selenium
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseSeleniumTest {

    protected WebDriver driver;

    @LocalServerPort
    protected int port;

    protected String baseUrl;

    @BeforeEach
    public void setUp() {
        // Configura o WebDriverManager para gerenciar automaticamente o ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Configura opções do Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        baseUrl = "http://localhost:" + port;

        // Maximiza a janela do navegador
        driver.manage().window().maximize();

        // Define timeout implícito
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Navega para a página inicial da aplicação
     */
    protected void navegarParaHome() {
        driver.get(baseUrl + "/home");
    }
}
