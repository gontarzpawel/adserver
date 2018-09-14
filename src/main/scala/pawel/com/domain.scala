package pawel.com

import enumeratum._

object domain {

  final case class Advertiser(id: String, priority: Int, rules: List[Rule])

  final case class Ad(id: String, provider: Advertiser)

  trait Rule
  object Rule {

    final case class AgeRange(min: Int, max: Int) extends Rule

    sealed trait Gender extends EnumEntry with Rule
    object Gender extends Enum[Gender] {
      override def values = findValues

      case object Male extends Gender
      case object Female extends Gender
    }

  }
}
