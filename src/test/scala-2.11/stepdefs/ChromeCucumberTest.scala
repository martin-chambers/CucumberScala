package stepdefs

import java.util.concurrent.TimeUnit

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.{By, Keys, WebDriver, WebElement}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.WebBrowser

/**
  * Created by mchambers on 10/08/2016.
  */
class ChromeCucumberTest extends ScalaDsl with EN with Matchers with WebBrowser with Eventually {

  System.setProperty("webdriver.chrome.driver", "../../lib/chromedriver/chromedriver.exe")
  implicit val driver:WebDriver = new ChromeDriver()
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
  val url:String = "http://www.bing.com"

  Given("""^I have navigated to Bing using Chrome$"""){ () =>
    go to url
  }

  When("""^I type "(.*?)" in the search box using Chrome$"""){ (searchTerm:String) =>
    click on "sb_form_q"
    enter(searchTerm)
  }

  And("""Press <Enter> using Chrome$""") {
    submit()
  }

  Then("""^the page should contain more than (\d+) occurrences of the text "(.*?)" using Chrome$"""){ (occurrences:Int, searchTerm:String) =>

    def countSubstring(str: String, substr: String) = substr.r.findAllMatchIn(str).length
    eventually {
      val html = driver.getPageSource()
      assert(countSubstring(html, searchTerm) > occurrences)
    }
    close()
  }
}
