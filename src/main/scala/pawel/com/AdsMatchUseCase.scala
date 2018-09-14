package pawel.com

import akka.japi.Option.Some
import pawel.com.domain.{Ad, Rule}
import pawel.com.domain.Rule.{AgeRange, Gender}
import scala.concurrent.{ExecutionContext, Future}

class AdsMatchUseCase(adStore: AdStore)
                     (implicit executionContext: ExecutionContext) {

  def getMatchingAd(age: Int, gender: Gender): Future[Option[Ad]] = {
    for {
      ads <- adStore.getAll
      ad <- findMatchingAd(age, gender, ads)
    } yield ad
  }

  private def findMatchingAd(age: Int,
                             gender: Gender,
                             ads: List[Ad]): Future[Option[Ad]] = Future.successful {
    val matchingAds =
      ads.filter { ad =>
        val rules = ad.provider.rules
        rules
          .forall { rule => ruleVerify(rule, age, gender) }
      }

    if (matchingAds.isEmpty) None
    else {
      Some(
        matchingAds.foldLeft(matchingAds.head) {
          (acc, next) =>
            if (acc.provider.priority < next.provider.priority) acc
            else next
        }
      )
    }
  }

  private def ruleVerify(rule: Rule,
                         age: Int,
                         gender: Gender): Boolean = {
    rule match {
      case g: Gender => g == gender
      case AgeRange(min, max) => min < age && max > age
    }
  }

}