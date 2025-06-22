package page;

import base.BaseTest;
import org.openqa.selenium.By;

public class HomePage extends BaseTest {
    //Localizadores
    public By optionForm = By.cssSelector("div.card.mt-4.top-card");

    //Metodos
    public boolean isOptionFormVisible() {
        return driver.findElement(optionForm).isDisplayed();
    }

    public void clicOptionForm(String option, By component) throws InterruptedException {
        getOpctionMenu(option, component);
    }

}
