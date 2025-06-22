package utils.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import utils.FileManager;
import utils.Logs;

public class SuiteListeners implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        Logs.info("Suite iniciada %s", suite.getName());
        FileManager.deletePreviousEvidence();
    }

    @Override
    public void onFinish(ISuite suite) {
        Logs.info("Suite finalizada %s", suite.getName());
    }
}
