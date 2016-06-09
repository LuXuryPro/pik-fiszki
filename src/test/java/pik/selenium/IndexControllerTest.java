package pik.selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.mongodb.Mongo;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.AssertJUnit;
import pik.Application;
import de.flapdoodle.embed.mongo.config.ArtifactStoreBuilder;


import java.io.IOException;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:9000")
public class IndexControllerTest {
    private HtmlUnitDriver webDriver;
    private static final String DATABASE_NAME = "embedded";
    private static final MongodStarter starter = MongodStarter.getDefaultInstance();

    private MongodExecutable mongodExe;
    private MongodProcess mongod;


    @Before
    public void setup() throws IOException {
        int port = 27017;
        this.mongodExe = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(12345, Network.localhostIsIPv6()))
                .build());
        this.mongod = this.mongodExe.start();

        this.webDriver = new HtmlUnitDriver(BrowserVersion.CHROME);
        this.webDriver.setJavascriptEnabled(true);
    }

    @After
    public void tearDown() {
        this.webDriver.close();
        this.mongodExe.stop();
        this.mongod.stop();
    }

    @Test
    public void startTest() {
        this.webDriver.get("http://localhost:9000/");
        WebElement header = this.webDriver.findElement(By.tagName("h1"));
        assertEquals(header.getText(), "Fiszki");
    }
}
