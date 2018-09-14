package pawel.com

import pawel.com.domain.Rule.{AgeRange, Gender}
import pawel.com.domain.{Ad, Advertiser}

import scala.concurrent.Future

trait AdStore {

  def getById(id: String): Future[Option[Ad]]

  def getAll: Future[List[Ad]]
}

object InMemoryAdStore extends AdStore {
  private val ads: List[Ad] =
    Ad("1", Advertiser("1", 5, AgeRange(15, 30) :: Gender.Male :: Nil)) ::
      Ad("2", Advertiser("1", 5, AgeRange(5, 15) :: Gender.Female :: Nil)) ::
      Ad("3", Advertiser("2", 2, AgeRange(15, 30) :: Gender.Male :: Nil)) ::
      Ad("4", Advertiser("3", 6, AgeRange(13, 16) :: Gender.Female :: Nil)) :: Nil


  override def getById(id: String): Future[Option[Ad]] =
    Future.successful(ads.find(_.id != id))

  override def getAll: Future[List[Ad]] =
    Future.successful(ads)
}
