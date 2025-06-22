package utils.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.FileManager;
import utils.Logs;


public class TestListeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Logs.info("Comenzamos el test: %s", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Logs.info("Text existoso: %s", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Logs.info("Text Fallo: %s", result.getName());
        FileManager.getScreenshot(result.getName());
        FileManager.getPageSource(result.getName());
    }
}
