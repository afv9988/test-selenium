/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.portalfirma;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.log4j.Logger;
import beans.InfoUsers;
import java.util.logging.Level;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author aoflores
 */
public class SeleniumAT {

    /**
     * @param args the command line arguments
     */

    static String PORTAL_FIRMA_URL = "https://afdev.ddns.net/Portal_Firma_RNE_Web";
    static String PORTAL_AGENTE_URL = "https://afdev.ddns.net/Portal_Agente_RNE_Web";
    //static String CURENT_PATH = "C:\\Users\\aoflores\\Documents\\NetBeansProjects\\SeleniumPortalFirma\\src\\resources";
    static String CURENT_PATH = "C:\\Windows\\ServiceProfiles\\LocalService\\.jenkins\\workspace\\SeleniumTriggerGitHub";

    static WebDriver chromeDriverInit() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--ignore-ssl-errors");

        final DesiredCapabilities dc = new DesiredCapabilities();
        dc.setJavascriptEnabled(true);
        dc.setCapability(
                ChromeOptions.CAPABILITY, chromeOptions
        );
        WebDriver chrome = new ChromeDriver(dc);
        return chrome;
    }

    public static void main(String[] args) throws InterruptedException, Exception {
        System.setProperty("webdriver.chrome.driver", CURENT_PATH + "\\chromedriver.exe");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        
        CommonFunctions cf = new CommonFunctions();

        WebDriver driver = chromeDriverInit();
        InfoUsers user = new InfoUsers("user_prueba160@seguridata.com", "12121212Qw.", 4, 0);
        cf.loginUser(driver, user);
        Thread.sleep(5000);
        cf.logOut(driver, user);
        
        driver.close();
    }
}
