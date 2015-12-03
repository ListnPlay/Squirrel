package com.featurefm.util.urls

import com.typesafe.config.ConfigFactory

import scala.util.matching.Regex

/**
 * Created by yardena on 11/19/15.
 */
trait UrlValidator {

  protected val config = ConfigFactory.load()

  trait UrlPattern {
    def pattern: Regex
    def unapply(url: String): Option[String] = pattern.unapplySeq(url).flatMap(_.headOption)
  }

  object YoutubeVideo extends UrlPattern {
    override val pattern = config.getString("pattern.youtube.video").r
    def apply(id: String) = s"https://www.youtube.com/watch?v=$id"
  }

  object YoutubeChannel extends UrlPattern {
    override val pattern = config.getString("pattern.youtube.channel").r
    def apply(id: String) = s"https://www.youtube.com/channel/$id"
  }

  object Facebook extends UrlPattern {
    override val pattern = config.getString("pattern.facebook").r
  }

  object TwitterHandle extends UrlPattern {
    override val pattern = config.getString("pattern.twitter.handle").r
  }

  object TwitterUrl extends UrlPattern {
    override val pattern = config.getString("pattern.twitter.url").r
    def apply(username: String): String = s"https://twitter.com/$username"
  }

  object AnyUrl extends UrlPattern {
    override val pattern = config.getString("pattern.url").r
    override def unapply(url: String): Option[String] = pattern.findFirstIn(url)
  }

  def allowEmptyString: Boolean

  def validFacebookUrl_?(url: String): Boolean = (url.isEmpty && allowEmptyString) || (url match {
    case Facebook(_) => true
    case _ => false
  })

  def validYouTubeVideoUrl_?(url: String): Boolean = (url.isEmpty && allowEmptyString) || (url match {
    case YoutubeVideo(_) => true
    case _ => false
  })

  def validYouTubeChannelUrl_?(url: String): Boolean = (url.isEmpty && allowEmptyString) || (url match {
    case YoutubeChannel(_) => true
    case _ => false
  })

  def validYouTubeUrl_?(url: String): Boolean = (url.isEmpty && allowEmptyString) || (url match {
    case YoutubeVideo(_) | YoutubeChannel(_) => true
    case _ => false
  })

  def validTwitterHandle_?(str: String): Boolean = (str.isEmpty && allowEmptyString) || (str match {
    case TwitterHandle(_) | TwitterUrl(_) => true
    case _ => false
  })

  def validUrl_?(url: String): Boolean = (url.isEmpty && allowEmptyString) || (url match {
    case AnyUrl(_) => true
    case _ => false
  })

}

object UrlValidator extends UrlValidator {
  override val allowEmptyString = config.getBoolean("pattern.allowEmptyString")
}
