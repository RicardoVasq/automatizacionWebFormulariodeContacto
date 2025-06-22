package utils;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    private static final String screenshotPath = "src/test/resources/screenshots";
    private static String pageStructurePath = "src/test/resources/pageStructure";


    public static void getScreenshot(String screenshotName) {
        Logs.debug("Tomando Screenshot");

        final var screenshotFile = ((TakesScreenshot) new WebDriverProvider().get())
                .getScreenshotAs(OutputType.FILE);

        final var path = String.format("%s/%s.png", screenshotPath, screenshotName);

        try {
            FileUtils.copyFile(screenshotFile, new File(path));
        } catch (IOException ioException) {
            Logs.error("Error tomar screenshot: %s", ioException.getLocalizedMessage());
        }

    }

    public static void getPageSource(String fileName) {
        Logs.debug("Tomando el Page Source de la pagina");

        final var path = String.format("%s/page-source-%s.html", pageStructurePath, fileName);

        try {
            final var file = new File(path);
            Logs.debug("Preguntando si los directorios padres si es que no existe");
            if (file.getParentFile().mkdirs()) {
                final var fileWriter = new FileWriter(file);
                final var pageSource = new WebDriverProvider().get().getPageSource();
                fileWriter.write(Jsoup.parse(pageSource).toString());
                fileWriter.close();
            }
        } catch (IOException ioException) {
            Logs.error("Error en la toma de page source %s", ioException.getLocalizedMessage());
        }
    }

    public static void deletePreviousEvidence() {
        Logs.debug("Borrando la evidencia Anterior");
        try {
            FileUtils.deleteDirectory(new File(screenshotPath));
            FileUtils.deleteDirectory((new File(pageStructurePath)));
        } catch (IOException ioException) {
            Logs.error("Error al borrar la evidencia anterior  %s",
                    ioException.getLocalizedMessage());
        }
    }

    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] getScreenshot() {
        return ((TakesScreenshot) new WebDriverProvider().get()).
                getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "pageSource", type = "text/html", fileExtension = "txt")
    public static String getPageSource() {
        return Jsoup.parse(new WebDriverProvider().get().getPageSource()).toString();
    }
}
