package stepdefs

import java.util.concurrent.TimeUnit

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.firefox.MarionetteDriver
import org.openqa.selenium.{By, Keys, WebDriver, WebElement}
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}
import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.scalatest.Matchers
import org.scalatest.selenium.WebBrowser
/**
  * Created by mchambers on 09/08/2016.
  */

//C:\Users\mchambers\IdeaProjects\CucumberScala\lib\geckodriver
class MozillaCucumberTest extends ScalaDsl with EN with Matchers with WebBrowser{
  // using geckodriver v0.9.0
  // v0.10.0 did not work and is described as being suitable for Selenium 3 beta releases only here: https://github.com/mozilla/geckodriver/releases
  System.setProperty("webdriver.gecko.driver", "../../lib/marionette/geckodriver.exe")
  val capabilities:DesiredCapabilities = DesiredCapabilities.firefox()
  capabilities.setCapability("marionette", true) // this line doesn't actually seem to be required although it was included in the example I have seen
  val driver:WebDriver = new MarionetteDriver()
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)



  Given("""^I have navigated to Bing using Mozilla$"""){ () =>
    driver.navigate().to("http://www.bing.com")
  }

  When("""^I type "(.*?)" in the search box using Mozilla$"""){ (searchTerm:String) =>
    driver.findElement(By.id("sb_form_q")).sendKeys(searchTerm)
    driver.findElement(By.id("sb_form_q")).click();
  }

  And("""Press <Enter> using Mozilla$""") {
    driver.findElement(By.id("sb_form_q")).sendKeys(Keys.RETURN)
  }

  Then("""^the page should contain more than (\d+) occurences of the text "(.*?)" using Mozilla$"""){ (occurences:Int, searchTerm:String) =>

    val elementWait = new WebDriverWait(driver, 10).until(
      new ExpectedCondition[WebElement] {
        override def apply(d:WebDriver) = d.findElement(By.className("sb_count"))
     }
    )
    def countSubstring(str: String, substr: String) = substr.r.findAllMatchIn(str).length
    val html = driver.getPageSource()
    assert(countSubstring(html, searchTerm) > occurences)

  }


}

