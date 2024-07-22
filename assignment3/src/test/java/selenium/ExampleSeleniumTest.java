package selenium;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

class ExampleSeleniumTest {

  static Process server;
  private WebDriver driver;

  @BeforeAll
  public static void setUpBeforeClass() throws Exception {
    ProcessBuilder pb = new ProcessBuilder("java", "-jar", "bookstore5.jar");
    server = pb.start();
  }

  @BeforeEach
  void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();

    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.get("http://localhost:8080/");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
  }

  @AfterEach
  public void tearDown() {
    driver.close();
  }

  @AfterAll
  public static void tearDownAfterClass() throws Exception {
    server.destroy();
  }

  @Test
  void test1() {
    WebElement element = driver.findElement(By.id("title"));
    String expected = "YAMAZONE BookStore";
    String actual = element.getText();
    assertEquals(expected, actual);
  }

  @Test
  public void test2() {
    WebElement welcome = driver.findElement(By.cssSelector("p"));
    String expected = "Welcome";
    String actual = welcome.getText();
    assertEquals(expected, getWords(actual)[0]);
    WebElement langSelector = driver.findElement(By.id("locales"));
    langSelector.click();
    WebElement frSelector = driver.findElement(By.cssSelector("option:nth-child(3)"));
    frSelector.click();
    welcome = driver.findElement(By.cssSelector("p"));
    expected = "Bienvenu";
    actual = welcome.getText();
    assertEquals(expected, getWords(actual)[0]);
  }

  private String[] getWords(String s) {
    return s.split("\\s+");
  }

  // Additional test cases start here

  // Test for adding a book with valid data
  @Test
  public void testAddBookValid() {
    loginAsAdmin();
    WebElement category = driver.findElement(By.name("category"));
    category.sendKeys("Fiction");
    WebElement bookId = driver.findElement(By.name("book_id"));
    bookId.sendKeys("B12345");
    WebElement title = driver.findElement(By.name("title"));
    title.sendKeys("The Great Adventure");
    WebElement author = driver.findElement(By.name("author"));
    author.sendKeys("John Doe");
    WebElement description = driver.findElement(By.name("description"));
    description.sendKeys("An epic tale of adventure");
    WebElement cost = driver.findElement(By.name("cost"));
    cost.sendKeys("15.99");
    WebElement addButton = driver.findElement(By.name("add"));
    addButton.click();

    WebElement successMessage = driver.findElement(By.id("success_message"));
    assertEquals("Book successfully added", successMessage.getText());
  }

  // Test for adding a book with invalid data
  @Test
  public void testAddBookInvalid() {
    loginAsAdmin();
    WebElement category = driver.findElement(By.name("category"));
    category.sendKeys("");
    WebElement bookId = driver.findElement(By.name("book_id"));
    bookId.sendKeys("123");
    WebElement title = driver.findElement(By.name("title"));
    title.sendKeys("");
    WebElement author = driver.findElement(By.name("author"));
    author.sendKeys("");
    WebElement description = driver.findElement(By.name("description"));
    description.sendKeys("");
    WebElement cost = driver.findElement(By.name("cost"));
    cost.sendKeys("-5.00");
    WebElement addButton = driver.findElement(By.name("add"));
    addButton.click();

    WebElement errorMessage = driver.findElement(By.id("error_message"));
    assertTrue(errorMessage.getText().contains("Error"));
  }

  // Test for removing an existing book
  @Test
  public void testRemoveExistingBook() {
    loginAsAdmin();
    WebElement bookId = driver.findElement(By.name("book_id"));
    bookId.sendKeys("B12345");
    WebElement removeButton = driver.findElement(By.name("remove"));
    removeButton.click();

    WebElement successMessage = driver.findElement(By.id("success_message"));
    assertEquals("Book successfully removed", successMessage.getText());
  }

  // Test for ordering a new book
  @Test
  public void testOrderNewBook() {
    searchForBook("Fiction");
    WebElement bookId = driver.findElement(By.name("book_id"));
    bookId.sendKeys("B12345");
    WebElement orderButton = driver.findElement(By.name("order"));
    orderButton.click();

    WebElement successMessage = driver.findElement(By.id("success_message"));
    assertEquals("Book successfully added to order", successMessage.getText());
  }

  // Test for viewing books order with items
  @Test
  public void testViewBooksOrderWithItems() {
    addBookToOrder("B12345");
    WebElement viewOrderButton = driver.findElement(By.name("view_order"));
    viewOrderButton.click();

    WebElement orderInfo = driver.findElement(By.id("order_info"));
    assertTrue(orderInfo.getText().contains("B12345"));
  }

  // Test for viewing books order without items
  @Test
  public void testViewBooksOrderWithoutItems() {
    WebElement viewOrderButton = driver.findElement(By.name("view_order"));
    viewOrderButton.click();

    WebElement emptyOrderMessage = driver.findElement(By.id("empty_order_message"));
    assertEquals("No items in the order", emptyOrderMessage.getText());
  }

  // Test for updating book order with valid copies
  @Test
  public void testUpdateBookOrderValid() {
    addBookToOrder("B12345");
    WebElement updateCopies = driver.findElement(By.name("update_copies"));
    updateCopies.sendKeys("3");
    WebElement updateButton = driver.findElement(By.name("update"));
    updateButton.click();

    WebElement updatedCost = driver.findElement(By.id("updated_cost"));
    assertEquals("45.00", updatedCost.getText());
  }

  // Test for updating book order with invalid copies
  @Test
  public void testUpdateBookOrderInvalid() {
    addBookToOrder("B12345");
    WebElement updateCopies = driver.findElement(By.name("update_copies"));
    updateCopies.sendKeys("0");
    WebElement updateButton = driver.findElement(By.name("update"));
    updateButton.click();

    WebElement removedMessage = driver.findElement(By.id("removed_message"));
    assertEquals("Book removed from order", removedMessage.getText());
  }

  // Test for successful checkout
  @Test
  public void testCheckoutSuccessful() {
    addBookToOrder("B12345");
    WebElement checkoutButton = driver.findElement(By.name("checkout"));
    checkoutButton.click();

    WebElement checkoutInfo = driver.findElement(By.id("checkout_info"));
    assertTrue(checkoutInfo.getText().contains("Total Amount"));
  }

  // Test for checkout with empty order
  @Test
  public void testCheckoutEmptyOrder() {
    WebElement checkoutButton = driver.findElement(By.name("checkout"));
    checkoutButton.click();

    WebElement errorMessage = driver.findElement(By.id("error_message"));
    assertEquals("No items to checkout", errorMessage.getText());
  }

  // Test for selecting a valid language
  @Test
  public void testSelectValidLanguage() {
    WebElement langSelector = driver.findElement(By.id("locales"));
    langSelector.click();
    WebElement frSelector = driver.findElement(By.cssSelector("option:nth-child(3)"));
    frSelector.click();

    WebElement welcome = driver.findElement(By.cssSelector("p"));
    assertEquals("Bienvenu", getWords(welcome.getText())[0]);
  }

  // Helper methods

  private void loginAsAdmin() {
    driver.get("http://localhost:8080/admin");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("password");
    driver.findElement(By.name("login")).click();
  }

  private void searchForBook(String category) {
    driver.findElement(By.name("category")).sendKeys(category);
    driver.findElement(By.name("search")).click();
  }

  private void addBookToOrder(String bookId) {
    searchForBook("Fiction");
    WebElement bookElement = driver.findElement(By.name("book_id"));
    bookElement.sendKeys(bookId);
    WebElement orderButton = driver.findElement(By.name("order"));
    orderButton.click();
  }
}
