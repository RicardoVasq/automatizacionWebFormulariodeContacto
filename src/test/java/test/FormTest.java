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

import java.util.List;

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

    @Severity(SeverityLevel.CRITICAL)
    @Story("El usuario intenta enviar el formulario sin colocar informacion en los campos obligatorios")
    @Description("TC02 - Validación de campos obligatorios vacíos - Negativa")
    @Test(groups = {"negativa", "regression"})
    public void testTC02() {
        Logs.debug("Intentamos enviar el formulario sin llenar los campos requeridos.");
        scrollToElement(fp.buttonSubmit);
        fp.clickSubmit();

        softAssertl.assertTrue(fp.isFieldInvalid(fp.inputFirstName), "First Name no está en estado de error.");
        softAssertl.assertTrue(fp.isFieldInvalid(fp.inputLastName), "Last Name no está en estado de error.");
        softAssertl.assertTrue(fp.isFieldInvalid(fp.inputEmail), "Email no está en estado de error.");
        softAssertl.assertTrue(fp.isFieldInvalid(fp.inputMobile), "Mobile no está en estado de error.");
        softAssertl.assertTrue(fp.isFieldInvalid(fp.inputDate), "Date of Birth no está en estado de error.");
        softAssertl.assertAll();

    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("El usuario ingresa un correo con formato incorrecto")
    @Description("TC03 - Validación de formato incorrecto de email - Negativa")
    @Test(groups = {"negativa", "regression"})
    public void testTC03() {
        fp.senKeysInputText("Ricardo", fp.inputFirstName);
        fp.senKeysInputText("Vasquez", fp.inputLastName);
        fp.senKeysInputText("correoInvalidoSinArroba.com", fp.inputEmail); // Email inválido
        fp.senKeysInputText("7732508799", fp.inputMobile);
        fp.setDateOfBirth("01 Jan 1990");
        scrollToElement(fp.buttonSubmit);
        fp.clickSubmit();

        Assert.assertTrue(fp.isFieldInvalid(fp.inputEmail), "El campo email no está resaltado como inválido.");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("El usuario digita el numero de telefono con menos de 10 digitos")
    @Description("TC04 - Ingreso de número telefónico con menos de 10 dígitos - Negativa")
    @Test(groups = {"negativa", "regression"})
    public void testTC04_telefonoCorto() {
        fp.senKeysInputText("Ricardo", fp.inputFirstName);
        fp.senKeysInputText("Vasquez", fp.inputLastName);
        fp.senKeysInputText("ricardo.v@example.com", fp.inputEmail);
        fp.senKeysInputText("12345", fp.inputMobile); // Teléfono inválido
        fp.setDateOfBirth("01 Jan 1990");
        scrollToElement(fp.buttonSubmit);
        fp.clickSubmit();

        Assert.assertTrue(fp.isFieldInvalid(fp.inputMobile), "El campo teléfono no está resaltado como inválido.");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("El usuario desea ingresar sus hobbies y sus subject en los campos")
    @Description("TC05 - Selección de múltiples hobbies y subjects - Funcional")
    @Test(dataProvider = "formData", dataProviderClass = DataForm.class, groups = {"funcional", "regression"})
    public void testTC05_hobbiesYSubjectsMultiples(
            String firstName, String lastName,
            String email, String gender,
            String mobile, String dateOfBirth,
            String state, String city) {

        Logs.debug("Llenamos los campos básicos");
        fp.senKeysInputText(firstName, fp.inputFirstName);
        fp.senKeysInputText(lastName, fp.inputLastName);
        fp.senKeysInputText(email, fp.inputEmail);
        fp.selectGender(gender);
        fp.senKeysInputText(mobile, fp.inputMobile);
        fp.setDateOfBirth(dateOfBirth);


        Logs.debug("Seleccionamos múltiples materias");
        fp.addSubject("Maths");
        fp.addSubject("English");

        Logs.debug("Seleccionamos múltiples hobbies");
        fp.selectHobby("Sports");
        fp.selectHobby("Reading");

        scrollToElement(fp.inputState);
        fp.selectDropdown(fp.inputState, state);
        fp.selectDropdown(fp.inputCity, city);
        scrollToElement(fp.buttonSubmit);
        fp.clickSubmit();

        Logs.debug("Validamos que los valores se reflejan correctamente en el modal");
        Assert.assertTrue(fp.isFormSubmittedSuccessfully(), "El formulario no se envió correctamente.");

        Assert.assertTrue(fp.isValueInModal("Subjects", "Maths, English"), "Las materias seleccionadas no coinciden.");
        Assert.assertTrue(fp.isValueInModal("Hobbies", "Sports, Reading"), "Los hobbies seleccionados no coinciden.");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("El usuario desea subir una imagen valida")
    @Description("TC06 - Carga de imagen válida - Funcional")
    @Test(dataProvider = "formData", dataProviderClass = DataForm.class, groups = {"funcional", "regression"})
    public void testTC06_cargaImagenValida(
            String firstName, String lastName,
            String email, String gender,
            String mobile, String dateOfBirth,
            String state, String city
    ) {

        fp.senKeysInputText(firstName, fp.inputFirstName);
        fp.senKeysInputText(lastName, fp.inputLastName);
        fp.senKeysInputText(email, fp.inputEmail);
        fp.selectGender(gender);
        fp.senKeysInputText(mobile, fp.inputMobile);
        fp.setDateOfBirth(dateOfBirth);

        Logs.debug("Subimos una imagen válida");
        fp.uploadPicture();

        scrollToElement(fp.inputState);
        fp.selectDropdown(fp.inputState, state);
        fp.selectDropdown(fp.inputCity, city);

        scrollToElement(fp.buttonSubmit);
        fp.clickSubmit();

        Assert.assertTrue(fp.isFormSubmittedSuccessfully(), "El formulario no se envió correctamente.");

        Assert.assertTrue(fp.isValueInModal("Picture", "descargar.jpg"), "La imagen no fue reflejada correctamente en el resumen.");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("El usuario desea seleccionar una ciudad correcta segun su estado")
    @Description("TC07 - Validación dinámica de Cities según State - Funcional")
    @Test(
            dataProvider = "citiesByStateData",
            dataProviderClass = DataForm.class,
            groups = {"funcional", "regression"})
    public void testTC07(String state, List<String> expectedCities) {
        // Paso 1: Asegurarse de que el campo City esté visible
        scrollToElement(fp.inputCity);

        // Paso 2: Obtener las ciudades reales según el estado
        List<String> actualCities = fp.getAllCitie(fp.inputCity, state);

        // Paso 3: Validar
        Assert.assertEquals(actualCities, expectedCities,
                "Las ciudades mostradas no coinciden con las esperadas para el estado: " + state);
    }

}
