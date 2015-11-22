package com.featurefm.util.urls

import com.typesafe.config.ConfigFactory

import scala.util.matching.Regex

/**
 * Created by yardena on 11/19/15.
 */
object UrlValidator {

  val config = ConfigFactory.load()

  trait UrlPattern {
    def pattern: Regex
    def unapply(url: String): Option[String] = {
      pattern.unapplySeq(url).flatMap(_.headOption)
    }
  }

  object YoutubeVideo extends UrlPattern {
    override val pattern = config.getString("pattern.youtube.video").r
    def apply(id: String) = s"https://www.youtube.com/watch?v=$id"
  }

  object YoutubeChannel extends UrlPattern {
    override val pattern = config.getString("pattern.youtube.channel").r
  }

  object Facebook extends UrlPattern {
    override val pattern = config.getString("pattern.facebook").r
  }

  object TwitterHandle extends UrlPattern {
    override val pattern = config.getString("pattern.twitter.handle").r
  }

  object TwitterUrl extends UrlPattern {
    override val pattern = config.getString("pattern.twitter.url").r
  }

  object AnyUrl extends UrlPattern {
    override val pattern = config.getString("pattern.url").r
  }

  def validFacebookUrl_?(url: String): Boolean = url match { case Facebook(_) => true; case _ => false }

  def validYouTubeVideoUrl_?(url: String): Boolean = url match { case YoutubeVideo(_) => true; case _ => false }

  def validYouTubeUrl_?(url: String): Boolean = url match {
    case YoutubeVideo(_) | YoutubeChannel(_) => true
    case _ => false
  }

  def validTwitterHandle_?(str: String): Boolean = str match {
    case TwitterHandle(_) | TwitterUrl(_) => true
    case _ => false
  }

  def validUrl_?(url: String): Boolean = url match { case AnyUrl(_) => true; case _ => false }

}
