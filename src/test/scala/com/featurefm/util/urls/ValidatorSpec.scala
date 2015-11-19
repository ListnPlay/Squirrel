package com.featurefm.util.urls

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by yardena on 11/19/15.
 */
class ValidatorSpec extends FlatSpec with Matchers {

  import UrlValidator._

  UrlValidator.getClass.getSimpleName should "succeed to update artist with legal url" in {
    validTwitterHandle_?("@EricPClapton") shouldBe true
    validTwitterHandle_?("https://twitter.com/EricPClapton") shouldBe true

    validFacebookUrl_?("https://www.facebook.com/ericclapton") shouldBe true
    validFacebookUrl_?("https://www.facebook.com/Featurefm-497931363607317?ref=aymt_homepage_panel") shouldBe true

    validYouTubeUrl_?("https://www.youtube.com/user/ericclapton") shouldBe true
    validYouTubeUrl_?("https://www.youtube.com/channel/ericclapton") shouldBe true
    validYouTubeUrl_?("https://www.youtube.com/c/ericclapton") shouldBe true
    validYouTubeUrl_?("https://www.youtube.com/ericclapton") shouldBe true

    validUrl_?("http://www.ericclapton.com") shouldBe true
  }

  it should "succeed to update artist with legal url with trailing /" in {
    validTwitterHandle_?("https://twitter.com/EricPClapton/") shouldBe true

    validFacebookUrl_?("https://www.facebook.com/ericclapton/") shouldBe true
    validFacebookUrl_?("https://www.facebook.com/Featurefm-497931363607317/?ref=aymt_homepage_panel") shouldBe true

    validYouTubeUrl_?("https://www.youtube.com/user/ericclapton/") shouldBe true
    validYouTubeUrl_?("https://www.youtube.com/ericclapton/") shouldBe true

    validUrl_?("http://www.ericclapton.com/") shouldBe true
  }

  it should "succeed to update artist with underscore in twitter handle" in {
    validTwitterHandle_?("@I_Has_Under") shouldBe true
    validTwitterHandle_?("https://twitter.com/I_Has_Under") shouldBe true
  }

  it should "succeed to update artist with empty string url" in {
    validTwitterHandle_?("") shouldBe true

    validFacebookUrl_?("") shouldBe true

    validYouTubeUrl_?("") shouldBe true

    validUrl_?("") shouldBe true
  }

  it should "fail to update artist with illegal twitter" in {
    validTwitterHandle_?("ssasasdad") shouldBe false
  }
  it should "fail to update artist with illegal twitter 2" in {
    validTwitterHandle_?("ssas@asdad") shouldBe false
  }

  it should "fail to update artist with illegal youtube" in {
    validYouTubeUrl_?("ssasasdad") shouldBe false
  }

  it should "fail to update artist with illegal facebook" in {
    validFacebookUrl_?("ssasasdad") shouldBe false
  }

  it should "fail to update artist with illegal website" in {
    validUrl_?("ssasasdad") shouldBe false
  }

}
