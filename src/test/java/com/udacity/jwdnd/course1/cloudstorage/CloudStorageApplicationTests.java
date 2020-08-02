package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.spo.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.spo.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.spo.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    public static WebDriver driver;
    public String baseURL;
    @LocalServerPort
    private Integer port;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterAll
    static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void invalidLogin() {
        String username = "rahul";
        String password = "0987654";

        driver.get(baseURL + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        Assertions.assertEquals("Invalid username or password", loginPage.getErrorMessage());
    }

    @Test
    public void getLoginPage() throws InterruptedException {
        driver.get(baseURL + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void getSignupPage() {
        driver.get(baseURL + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    void unauthorizedAccessRestrictions() throws InterruptedException {
        driver.get(baseURL + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get(baseURL + "/result");
        Assertions.assertNotEquals("Result", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    void signUpLoginLogout() throws InterruptedException {
        String username = "john";
        String password = "12345678";
        String firstName = "john";
        String lastName = "doe";
        driver.get(baseURL + "/signup");

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp(firstName, lastName, username, password);
        Thread.sleep(2000);
        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        Thread.sleep(2000);
        Assertions.assertEquals("Home", driver.getTitle());
        HomePage homePage = new HomePage(driver);
        Thread.sleep(2000);
        homePage.logout();
        Thread.sleep(2000);
        Assertions.assertNotEquals("Home", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    void noteCreate() throws InterruptedException {

        String username = "john1";
        String password = "123456";
        String firstName = "john";
        String lastName = "doe";

        String noteTitle = "Reminder";
        String noteDescription = "Aman's bithday";

        driver.get(baseURL + "/signup");
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp(firstName, lastName, username, password);
        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewNote"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSaveSubmit"))).click();
        driver.get(baseURL + "/home");
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
        HomePage homePage = new HomePage(driver);

        String resultNoteTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("noteTitleAfterSubmit"))).getText();
        String resultNoteDescription = wait.until(ExpectedConditions.elementToBeClickable(By.id("noteDescriptionAfterSubmit"))).getText();
        Assertions.assertEquals(noteTitle, resultNoteTitle);
        Assertions.assertEquals(noteDescription, resultNoteDescription);
    }

    @Test
    void noteUpdate() throws InterruptedException {
        String username = "john2";
        String password = "123456";
        String firstName = "john";
        String lastName = "doe";

        String noteTitle = "Reminder";
        String noteDescription = "Aman's bithday";

        driver.get(baseURL + "/signup");

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp(firstName, lastName, username, password);

        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        driver.get(baseURL + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 20);

        Thread.sleep(2000);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewNote"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSaveSubmit"))).click();

        driver.get(baseURL + "/home");
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("editNoteButton"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")))
                .clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")))
                .sendKeys("new title");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")))
                .clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")))
                .sendKeys("new description");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSaveSubmit"))).click();

        driver.get(baseURL + "/home");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();

        String resultNoteTitle =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("noteTitleAfterSubmit"))).getText();
        String resultNoteDescription =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("noteDescriptionAfterSubmit"))).getText();

        Assertions.assertEquals("new title", resultNoteTitle);
        Assertions.assertEquals("new description", resultNoteDescription);
    }

    @Test
    void noteDelete() throws InterruptedException {
        String username = "john3";
        String password = "12345678";
        String firstName = "john";
        String lastName = "doe";

        String noteTitle = "Reminder";
        String noteDescription = "Aman's bithday";

        driver.get(baseURL + "/signup");

        SignUpPage signupControllerPageTest = new SignUpPage(driver);
        signupControllerPageTest.signUp(firstName, lastName, username, password);
        Thread.sleep(2000);
        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        Thread.sleep(2000);
        loginPage.login(username, password);
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewNote"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("noteSaveSubmit"))).click();

        driver.get(baseURL + "/home");
        Thread.sleep(2000);
        driver.findElement(By.id("nav-notes-tab")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteNoteButton"))).click();


        HomePage homePage = new HomePage(driver);

        Assertions.assertThrows(NoSuchElementException.class, homePage::getNoteDescription);
        Assertions.assertThrows(NoSuchElementException.class, homePage::getNoteTitle);

    }

    @Test
    void credentialCreate() throws InterruptedException {

        String username = "john4";
        String password = "12345678";
        String firstName = "john";
        String lastName = "doe";

        String credentialURL = "john credentials";
        String credentialUsername = "john username";
        String credentialPassword = "john password";

        driver.get(baseURL + "/signup");

        SignUpPage signupControllerPageTest = new SignUpPage(driver);
        signupControllerPageTest.signUp(firstName, lastName, username, password);

        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("newCredentialButton"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(credentialUsername);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(credentialPassword);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialSave"))).click();

        driver.get(baseURL + "/home");
        Thread.sleep(2000);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();

        HomePage homePage = new HomePage(driver);

        String resultCredentialUrl = wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUrlAfterSubmit"))).getText();
        String resultCredentialUsername = wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUsernameAfterSubmit"))).getText();
        String resultCredentialPassword =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialPasswordAfterSubmit"))).getText();

        Assertions.assertEquals(credentialURL, resultCredentialUrl);
        Assertions.assertEquals(credentialUsername, resultCredentialUsername);
        Assertions.assertNotEquals(credentialPassword, resultCredentialPassword);
    }

    @Test
    void credentialUpdate() throws InterruptedException {
        String username = "john5";
        String password = "12345678";
        String firstName = "john";
        String lastName = "doe";

        String credentialURL = "john credentials";
        String credentialUsername = "john username";
        String credentialPassword = "john password";

        String credentialURL2 = "john credentials 2";
        String credentialUsername2 = "john username 2";
        String credentialPassword2 = "john password 2";

        driver.get(baseURL + "/signup");

        SignUpPage signupControllerPageTest = new SignUpPage(driver);
        signupControllerPageTest.signUp(firstName, lastName, username, password);

        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("newCredentialButton"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(credentialUsername);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(credentialPassword);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialSave"))).click();

        driver.get(baseURL + "/home");
        Thread.sleep(2000);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();

        String resultCredentialUrl =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUrlAfterSubmit"))).getText();
        String resultCredentialUsername =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUsernameAfterSubmit"))).getText();
        String resultCredentialPassword =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialPasswordAfterSubmit"))).getText();

        Assertions.assertEquals(credentialURL, resultCredentialUrl);
        Assertions.assertEquals(credentialUsername, resultCredentialUsername);
        Assertions.assertNotEquals(credentialPassword, resultCredentialPassword);

        driver.get(baseURL + "/home");
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredentialButton"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL2);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(credentialUsername2);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).clear();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(credentialPassword2);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialSave"))).click();
        driver.get(baseURL + "/home");
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();

        String resultCredentialUrl2 =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUrlAfterSubmit"))).getText();
        String resultCredentialUsername2 =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUsernameAfterSubmit"))).getText();
        String resultCredentialPassword2 =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialPasswordAfterSubmit"))).getText();

        Assertions.assertEquals(credentialURL2, resultCredentialUrl2);
        Assertions.assertEquals(credentialUsername2, resultCredentialUsername2);
        Assertions.assertNotEquals(credentialPassword2, resultCredentialPassword2);
    }

    @Test
    void credentialDelete() throws InterruptedException {

        String username = "john6";
        String password = "12345678";
        String firstName = "john";
        String lastName = "doe";

        String credentialURL = "john credentials";
        String credentialUsername = "john username";
        String credentialPassword = "john password";

        driver.get(baseURL + "/signup");

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp(firstName, lastName, username, password);

        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();


        wait.until(ExpectedConditions.elementToBeClickable(By.id("newCredentialButton"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(credentialUsername);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(credentialPassword);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialSave"))).click();

        driver.get(baseURL + "/home");
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();

        String resultCredentialUrl = wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUrlAfterSubmit"))).getText();
        String resultCredentialUsername = wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialUsernameAfterSubmit"))).getText();
        String resultCredentialPassword =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialPasswordAfterSubmit"))).getText();

        Assertions.assertEquals(credentialURL, resultCredentialUrl);
        Assertions.assertEquals(credentialUsername, resultCredentialUsername);
        Assertions.assertNotEquals(credentialPassword, resultCredentialPassword);

        driver.get(baseURL + "/home");
        Thread.sleep(2000);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteCredentialButton"))).click();

        driver.get(baseURL + "/home");
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();


        HomePage homePage = new HomePage(driver);

        Assertions.assertThrows(NoSuchElementException.class, homePage::getCredentialUrlSubmitted);
        Assertions.assertThrows(NoSuchElementException.class, homePage::getCredentialUsernameSubmitted);
        Assertions.assertThrows(NoSuchElementException.class, homePage::getCredentialPasswordSubmitted);

    }
}
