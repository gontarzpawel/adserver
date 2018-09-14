package pawel.com

import io.restassured.RestAssured._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers.equalTo
import org.scalatest.{BeforeAndAfter, WordSpec}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AdsApiTest
  extends WordSpec with BeforeAndAfter {

  "An api" when {

    Future {
      Boot.run()
    }

    //hack to let webapp get online
    Thread.sleep(2000)


    "asked for advertisement with matching rules" should {
      "return the only advertisement that matches rules" in {
        when()
          .get("http://localhost:8080/ad?age=10&gender=Female").
          Then()
          .statusCode(200)
          .body(equalTo("""{"id":"2"}"""))
      }


      "return the advertisement that comes from more important advertiser" in {
        when()
          .get("http://localhost:8080/ad?age=20&gender=male").
          Then()
          .statusCode(200)
          .body(equalTo("""{"id":"3"}"""))
      }
    }

    "asked for advertisement with non-matching rules" should {
      "return empty ad id" in {
        when()
          .get("http://localhost:8080/ad?age=50&gender=Female").
          Then()
          .statusCode(200)
          .body(equalTo("""{"id":null}"""))
      }
    }
  }
}
