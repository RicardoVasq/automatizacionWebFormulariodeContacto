package test;

import base.BaseTest;
import data.DataForm;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.FormPage;
import page.HomePage;
import utils.Logs;

public class FormTest extends BaseTest {
    //instancias
    HomePage hp = new HomePage();
    FormPage fp = new FormPage();


    @Severity(SeverityLevel.CRITICAL)
    @Story("El usuario llena todos los campos correctamente")
    @Description("TC01-Envío exitoso del formulario con todos los campos completos-Positiva")
    @Test(dataProvider = "formData", dataProviderClass = DataForm.class, groups = {"positivo", "regression"})
    public void testTC01(
            String firstName, String lastName,
            String email, String gender,
            String mobile, String dateOfBirth,
            String state, String city) {
        Logs.debug("El usuario esta en la pantalla del Formulario ");
        Logs.debug("Llenamos correctamente el formulario");
        fp.senKeysInputText(firstName, fp.inputFirstName);
        fp.senKeysInputText(lastName, fp.inputLastName);
        fp.senKeysInputText(email, fp.inputEmail);
        fp.selectGender(gender);
        fp.senKeysInputText(mobile, fp.inputMobile);
        fp.setDateOfBirth(dateOfBirth);
        scrollToElement(fp.inputState); // Se realiza un Scroll al elemento dado que un anuncion publicitario bloquea el siguiente elemento
        fp.selectDropdown(fp.inputState, state);
        fp.selectDropdown(fp.inputCity, city);
        fp.clickSubmit();

        Logs.debug("Validamos que se haya enviado correctamente el formulario");
        Assert.assertTrue(fp.isFormSubmittedSuccessfully(), "El modal de confirmación no se mostró.");
    }

}
