package com.featurefm.util.slug

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Future

/**
 * Created by yardena on 10/15/15.
 */
class UniqueSlugifySpec extends FlatSpec with Matchers with ScalaFutures {

  private implicit val context = scala.concurrent.ExecutionContext.global

  val factory = (f: String => Boolean) => UniqueSlugGeneratorAsync(new FastSlugGenerator, "artist")({ s: String =>
    Future successful f(s) })

  "UniqueSlugGenerator" should "handle Beatles" in {
    whenReady(factory(_ => true)("Beatles")) (_ shouldEqual "beatles" )
  }

  it should "handle Beatles if another exists" in {
    whenReady(factory(!_.equals("beatles"))("Beatles")) (_ shouldEqual "beatles-2")
  }

  it should "handle Beatles if another 2 exist" in {
    whenReady(factory(!Set("beatles", "beatles-2").contains(_))("Beatles"))(_ shouldEqual "beatles-3")
  }

  it should "handle 'Beatles 2' if another 2 exist" in {
    whenReady(factory(!Set("beatles", "beatles-2").contains(_))("Beatles 2"))(_ shouldEqual "beatles-2-2")
  }

  it should "handle John Lennon" in {
    whenReady(factory(_ => true)("John Lennon"))(_ shouldEqual "john-lennon")
  }

  it should "handle John Lennon if another exists" in {
    whenReady(factory(!_.equals("john-lennon"))("John Lennon")) (_ shouldEqual "john-lennon-2")
  }

  it should "handle empty string" in {
    whenReady(factory(_ => true)(""))(_ shouldEqual "artist")
  }

  it should "handle multiple empty strings" in {
    whenReady(factory(!Set("artist", "artist-2", "artist-3").contains(_))("-"))(_ shouldEqual "artist-4")
  }

}
