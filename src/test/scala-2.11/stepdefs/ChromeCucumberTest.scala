package stepdefs

import java.util.concurrent.TimeUnit

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.{By, Keys, WebDriver, WebElement}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.scalatest.Matchers
import org.scalatest.selenium.WebBrowser

/**
  * Created by mchambers on 10/08/2016.
  */
class ChromeCucumberTest extends ScalaDsl with EN with Matchers with WebBrowser {

  System.setProperty("webdriver.chrome.driver", "../../lib/chromedriver/chromedriver.exe")
  val driver:WebDriver = new ChromeDriver()
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

  Given("""^I have navigated to Bing using Chrome$"""){ () =>
    driver.navigate().to("http://www.bing.com")
  }

  When("""^I type "(.*?)" in the search box using Chrome$"""){ (searchTerm:String) =>
    driver.findElement(By.id("sb_form_q")).sendKeys(searchTerm)
    driver.findElement(By.id("sb_form_q")).click();
  }

  And("""Press <Enter> using Chrome$""") {
    driver.findElement(By.id("sb_form_q")).sendKeys(Keys.RETURN)
  }

  Then("""^the page should contain more than (\d+) occurences of the text "(.*?)" using Chrome$"""){ (occurences:Int, searchTerm:String) =>

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
