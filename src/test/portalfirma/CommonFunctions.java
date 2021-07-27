/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.portalfirma;

import beans.InfoUsers;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static test.portalfirma.SeleniumAT.PORTAL_FIRMA_URL;

/**
 *
 * @author aoflores
 */
public class CommonFunctions {
    
    private static final Logger logger = Logger.getLogger(CommonFunctions.class);
    private InfoUsers user;
    
    public void loginUser(WebDriver driver, InfoUsers user) throws InterruptedException{
        try {
            logger.info("getLogin " + user.getLogin());
            driver.get(PORTAL_FIRMA_URL);
            JavascriptExecutor js = (JavascriptExecutor) driver;  
            js.executeScript("changeLenguage(arguments[0], arguments[1])", "/Portal_Firma_RNE_Web", "es");
            Thread.sleep(500);
            WebElement username = driver.findElement(By.id("loginForm_user_login"));
            username.clear();
            username.sendKeys(user.getLogin());
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.findElement(By.id("btnLogin")).click();
            WebElement password = driver.findElement(By.id("loginForm_user_password"));
            password.clear();
            password.sendKeys(user.getPassword());
            driver.findElement(By.id("btnLogin")).click();
            //Thread.sleep(1000);
            WebElement element = driver.findElement(By.xpath("//h1[@style=\"margin-left:15px\"]"));
            assert element.getText().equals("Documentos pendientes");
        } catch (Exception e) {
            logger.error("Error en getLogin " + user.getLogin() + " " + e.getMessage());
            //publishIssue("getLogin", e.getMessage().substring(0, 20));
            logOut(driver, user);   
            driver.close();
        }
    }
    
    public void logOut(WebDriver driver, InfoUsers user) throws InterruptedException{
        try {
            logger.info("logOut " + user.getLogin());
            JavascriptExecutor js = (JavascriptExecutor) driver;  
            js.executeScript("goTo(arguments[0])", "/Portal_Firma_RNE_Web/login/loginAction_logOut.action");
        } catch (Exception e) {
            logger.error("Error en logOut " + user.getLogin());
            //publishIssue("logOut", e.getMessage().substring(0, 20));
        }
    }
    
    public void publishIssue(String function, String issue) throws InterruptedException{
        
        String data = "{\\\"fields\\\":{\\\"project\\\":{\\\"key\\\":\\\"TSTSEL\\\"},\\\"summary\\\":\\\"Error en funcion ##01##.\\\",\\\"description\\\":\\\"Se encontro un error con descripcion ##02##\\\",\\\"issuetype\\\":{\\\"name\\\":\\\"Error\\\"}}}";
        data = data.replace("##01##", function);        
        data = data.replace("##02##", issue);
        
        logger.info("data " + data);
        String command = "curl -D- -u aoflores@seguridata.com:3IjPpzGKUypFWLaYxPh8B63F -X POST --data \"##03##\" -H \"Content-Type: application/json\" https://seguridata.atlassian.net/rest/api/2/issue";
               
        command = command.replace("##03##", data);
        
        logger.info("publishIssue " + command);
        try {
            Process process = Runtime.getRuntime().exec(command);
            logger.info("\n\n------------------------------------------------------");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(
                                    process.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
                System.out.println(inputLine);
            in.close();
        
            logger.info("\n------------------------------------------------------\n\n");
        
        } catch (Exception e) {
            logger.error("error " + e);
        }
    }
    
}
