package page;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FormPage extends BaseTest {
    //Localizadores
    public By inputFirstName = By.id("firstName");
    public By inputLastName = By.id("lastName");
    public By inputEmail = By.id("userEmail");
    public By inputGenderMale = By.id("gender-radio-1");
    public By labelGenderMale = By.cssSelector("label[for='gender-radio-1']");
    public By inputGenderFemale = By.id("gender-radio-2");
    public By labelGenderFemale = By.cssSelector("label[for='gender-radio-2']");
    public By inputGenderOther = By.id("gender-radio-3");
    public By labelGenderOther = By.cssSelector("label[for='gender-radio-3']");
    public By inputMobile = By.id("userNumber");
    public By inputDate = By.id("dateOfBirthInput");
    public By inputHobbiesSports = By.id("hobbies-checkbox-1");
    public By inputHobbiesReading = By.id("hobbies-checkbox-2");
    public By inputHobbiesMusic = By.id("hobbies-checkbox-3");
    public By inputPicture = By.id("uploadPicture");
    public By inputAddres = By.id("currentAddress");
    public By inputState = By.id("state");
    public By inputCity = By.id("city");
    public By buttonSubmit = By.id("submit");


    //Metodos
    public void senKeysInputText(String information, By component) {
        driver.findElement(component).sendKeys(information);
    }

    public boolean isEmailValid() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement emailInput = driver.findElement(inputEmail);
        return (Boolean) js.executeScript("return arguments[0].checkValidity();", emailInput);
    }

    public void selectGender(String gender) {
        switch (gender.toLowerCase()) {
            case "male":
                driver.findElement(labelGenderMale).click();
                break;
            case "female":
                driver.findElement(labelGenderFemale).click();
                break;
            case "other":
                driver.findElement(labelGenderOther).click();
                break;
        }
    }

    public boolean isGenderSelected(By locator) {
        return driver.findElement(locator).isSelected();
    }

    public void setDateOfBirth(String date) {
        WebElement dateInput = driver.findElement(inputDate);
        // 1. Limpiar y escribir la fecha
        dateInput.sendKeys(Keys.CONTROL + "a");
        dateInput.sendKeys(date);

        // 2. Forzar el cierre del calendario con JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('.react-datepicker').style.display='none';");
    }

    public void selectDropdown(By dropdownLocator, String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Click en el dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        dropdown.click();

        // 2. Localizar opción por texto visible (sin depender de clases dinámicas)
        By optionLocator = By.xpath("//div[contains(@class, '-menu')]//div[text()='" + optionText + "']");
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));

        // 3. Click en la opción deseada
        option.click();
    }

    public void selectHobbies(String forAttributeValue) {
        driver.findElement(By.cssSelector("label[for='" + forAttributeValue + "']")).click();
    }

    public void clickSubmit() {
        driver.findElement(buttonSubmit).click();
    }

    public boolean isValueInModal(String label, String expectedValue) {
        String xpath = String.format("//td[text()='%s']/following-sibling::td", label);
        WebElement cell = driver.findElement(By.xpath(xpath));
        return cell.getText().trim().equals(expectedValue);
    }

    public boolean isFormSubmittedSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement modalTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("example-modal-sizes-title-lg")));
        return modalTitle.getText().equals("Thanks for submitting the form");
    }


}
