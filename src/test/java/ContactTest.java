import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactTest extends BaseTest {

    @Test
    public void testContactFormSubmit() {
        driver.get("https://practicesoftwaretesting.com/contact");

        ContactPage contactPage = new ContactPage(driver);

        WebElement heading = wait.until(d -> driver.findElement(contactPage.headingLoc));
        wait.until(d -> heading.isDisplayed());

        // Fill form
        contactPage.fillForm(
                "John", "Smith", "john.smith@example.com", "webmaster",
                "Hello My name is John Smith, Please make sure we have got 50 characters in this message."
        );

        // Submit
        contactPage.submitForm();

        // Add explicit wait for the success alert to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(contactPage.alertLoc)
        );
        String alertText = alert.getText().trim();

        assertEquals("Thanks for your message! We will contact you shortly.", alertText);
    }
}
