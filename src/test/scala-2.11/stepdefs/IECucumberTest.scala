package stepdefs

import java.util.concurrent.TimeUnit

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually
import org.scalatest.selenium.WebBrowser
import org.scalatest.selenium.WebBrowser.{click, go}

/**
  * Created by mchambers on 18/08/2016.
  */
class IECucumberTest extends ScalaDsl with EN with Matchers with WebBrowser with Eventually {
  System.setProperty("webdriver.ie.driver", "../../lib/iedriver/IEDriverServer.exe")
  val capabilities:DesiredCapabilities = DesiredCapabilities.internetExplorer()
  capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.bing.com")
  implicit val driver:WebDriver = new InternetExplorerDriver(capabilities)
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
  val url:String = "http://www.bing.com"


  Given("""^I have navigated to Bing using IE$"""){ () =>
    go to url
  }

  When("""^I type "(.*?)" in the search box using IE$"""){ (searchTerm:String) =>
    click on "sb_form_q"
    enter(searchTerm)
  }

  And("""Press <Enter> using IE$""") {
    submit()
  }

  Then("""^the page should contain more than (\d+) occurrences of the text "(.*?)" using IE$"""){ (occurrences:Int, searchTerm:String) =>

    def countSubstring(str: String, substr: String) = substr.r.findAllMatchIn(str).length
    eventually {
      val html = driver.getPageSource()
      assert(countSubstring(html, searchTerm) > occurrences)
    }
    close()
  }


}
