package com.featurefm.util.shortid

import java.util
import java.util.UUID

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, FunSpec, Matchers}

import scala.util.Random

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

  it should "generate url-friendly id" in {
    val someBytes = Array.fill[Byte](12)(0)
    Random.nextBytes(someBytes)
    val str = Shortener.shortUniqueUrlString(someBytes)
    println(str)
    val out = Shortener.restore12BytesFromUrlString(str)
    assert(util.Arrays.equals(someBytes, out))
    val shortHash = Shortener.shortUrlString(someBytes)
    println(shortHash)
  }

}
