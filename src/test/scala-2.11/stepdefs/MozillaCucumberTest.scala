package stepdefs

import java.util.concurrent.TimeUnit

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.firefox.MarionetteDriver
import org.openqa.selenium.{By, Keys, WebDriver, WebElement}
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}
import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.WebBrowser


/**
  * Created by mchambers on 18/08/2016.
  */
class MozillaCucumberTest extends ScalaDsl with EN with Matchers with WebBrowser{
  // using geckodriver v0.9.0
  // v0.10.0 did not work and is described as being suitable for Selenium 3 beta releases only here: https://github.com/mozilla/geckodriver/releases
  // renamed to 'wires.exe' in accordance with advice (some selenium libraries specifically look for that name)
  System.setProperty("webdriver.gecko.driver", "../../lib/marionette/wires.exe")
  val capabilities:DesiredCapabilities = DesiredCapabilities.firefox()
  // this line is required when using RemoteWebDriver() - which doesn't actually seem to work yet though is listed as the preferred option on Github
  // https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/firefox/package-summary.html
  capabilities.setCapability("marionette", true) // not actually required with MarionetteDriver
  // MarionetteDriver is deprecated, but I couldn't get the non-deprecated option to work
  implicit val driver:WebDriver = new MarionetteDriver()
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
  val url:String = "http://www.bing.com"

  Given("""^I have navigated to Bing using Mozilla$"""){ () =>
    go to url
  }

  When("""^I type "(.*?)" in the search box using Mozilla$"""){ (searchTerm:String) =>
    driver.findElement(By.id("sb_form_q")).sendKeys(searchTerm)
  }

  And("""Press <Enter> using Mozilla$""") {
    driver.findElement(By.id("sb_form_q")).sendKeys(Keys.RETURN)
  }

  Then("""^the page should contain more than (\d+) occurrences of the text "(.*?)" using Mozilla$"""){ (occurrences:Int, searchTerm:String) =>
    val elementWait = new WebDriverWait(driver, 10).until(
      new ExpectedCondition[WebElement] {
        override def apply(d:WebDriver) = d.findElement(By.className("sb_count"))
      }
    )
    def countSubstring(str: String, substr: String) = substr.r.findAllMatchIn(str).length
    val html = driver.getPageSource()
    assert(countSubstring(html, searchTerm) > occurrences)
    driver.quit()
  }
}
