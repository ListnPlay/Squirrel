package com.featurefm.util.shortid

import java.util.UUID

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers, FunSpec}

/**
  * Created by yardena on 3/16/16.
  */
class ShortIdSpec extends FlatSpec with Matchers with PropertyChecks{

  "Shortener" should "generate id" in {
    val ids = (1 to 10).map( _ => UUID.randomUUID())
    ids.foreach { id =>
      val x = Shortener.shortString(id)
      println(x)
      x.length should be > 0
      x.length should be < 10
      assert(Shortener.matchesShortString(id, x))
    }
  }

}
