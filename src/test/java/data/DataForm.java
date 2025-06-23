package data;

import base.BaseTest;
import org.testng.annotations.DataProvider;

import java.util.Arrays;

public class DataForm extends BaseTest {

    @DataProvider(name = "formData")
    public static Object[][] getFormData() {
        return new Object[][]{
                // {firstName, lastName, email, gender, mobile, dateOfBirth, state, city}
                {"Ricardo", "Vasquez", "ricardo.v@example.com", "Male", "7732508799", "01 Jan 1990", "NCR", "Delhi"},
                {"Ana", "Lopez", "ana.lopez@example.com", "Female", "7123456799", "15 May 1985", "Uttar Pradesh", "Lucknow"},
                {"Carlos", "Perez", "carlos.perez@example.com", "Male", "7999888899", "20 Aug 1992", "Haryana", "Karnal"},
                {"Laura", "Gomez", "laura.gomez@example.com", "Female", "7888777799", "30 Dec 1995", "Rajasthan", "Jaipur"}
        };
    }

    @DataProvider(name = "negativeFormData")
    public Object[][] getNegativeFormData() {
        return new Object[][]{
                // {firstName, lastName, email, gender, mobile, dob, state, city, expectedErrorField}
                {"", "Perez", "correo@valido.com", "Male", "77325087", "01 Jan 1990", "NCR", "Delhi", "firstName"},
                {"Ana", "", "ana.lopez@example.com", "Female", "71234567", "15 May 1985", "Uttar Pradesh", "Lucknow", "lastName"},
                {"Carlos", "Perez", "correoInvalido", "Male", "79998888", "20 Aug 1992", "Haryana", "Karnal", "email"},
                {"Laura", "Gomez", "laura.gomez@example.com", "Female", "abc123", "30 Dec 1995", "Rajasthan", "Jaipur", "mobile"},
                {"Ricardo", "Vasquez", "ricardo.v@example.com", "Male", "77325087", "invalidDate", "NCR", "Delhi", "dateOfBirth"},
                // Puedes agregar más casos negativos
        };
    }

    @DataProvider(name = "citiesByStateData")
    public Object[][] provideCitiesByState() {
        return new Object[][]{
                {
                        "NCR",
                        Arrays.asList("Delhi", "Gurgaon", "Noida")
                },
                {
                        "Uttar Pradesh",
                        Arrays.asList("Agra", "Lucknow", "Merrut")
                },
                {
                        "Haryana",
                        Arrays.asList("Karnal", "Panipat")
                },
                {
                        "Rajasthan",
                        Arrays.asList("Jaipur", "Jaiselmer")
                }
        };
    }

}
