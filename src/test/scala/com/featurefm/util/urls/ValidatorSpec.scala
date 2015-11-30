package com.featurefm.util.urls

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by yardena on 11/19/15.
 */
class ValidatorSpec extends FlatSpec with Matchers {

  import UrlValidator._

  UrlValidator.getClass.getSimpleName should "accept twitter handle @EricPClapton" in {
    validTwitterHandle_?("@EricPClapton") shouldBe true
  }

  it should "accept twitter url https://twitter.com/EricPClapton" in {
    validTwitterHandle_?("https://twitter.com/EricPClapton") shouldBe true
  }

  it should "accept facebook url https://www.facebook.com/ericclapton" in {
    validFacebookUrl_?("https://www.facebook.com/ericclapton") shouldBe true
  }

  it should "accept facebook url with '-' in the page" in {
    validFacebookUrl_?("https://www.facebook.com/Featurefm-497931363607317?ref=aymt_homepage_panel") shouldBe true
  }

  it should "accept various flavors of youtube urls" in {

    validYouTubeUrl_?("https://www.youtube.com/user/ericclapton") shouldBe true
    validYouTubeUrl_?("https://www.youtube.com/channel/ericclapton") shouldBe true
    validYouTubeUrl_?("https://www.youtube.com/c/ericclapton") shouldBe true
    validYouTubeUrl_?("https://www.youtube.com/ericclapton") shouldBe true
  }

  it should "accept a url 'http://www.ericclapton.com'" in {
    validUrl_?("http://www.ericclapton.com") shouldBe true
  }

  it should "accept urls with trailing /" in {
    validTwitterHandle_?("https://twitter.com/EricPClapton/") shouldBe true

    validFacebookUrl_?("https://www.facebook.com/ericclapton/") shouldBe true

    validYouTubeUrl_?("https://www.youtube.com/user/ericclapton/") shouldBe true
    validYouTubeUrl_?("https://www.youtube.com/ericclapton/") shouldBe true

    validUrl_?("http://www.ericclapton.com/") shouldBe true
  }

  it should "accept underscore in twitter handle" in {
    validTwitterHandle_?("@I_Has_Under") shouldBe true
    validTwitterHandle_?("https://twitter.com/I_Has_Under") shouldBe true
  }

  it should "succeed to update artist with empty string url" in {
    validTwitterHandle_?("") shouldBe true

    validFacebookUrl_?("") shouldBe true

    validYouTubeUrl_?("") shouldBe true

    validUrl_?("") shouldBe true
  }

  it should "fail twitter with 'ssasasdad'" in {
    validTwitterHandle_?("ssasasdad") shouldBe false
  }
  it should "fail twitter with 'ssas@asdad'" in {
    validTwitterHandle_?("ssas@asdad") shouldBe false
  }

  it should "fail youtube with 'ssasasdad'" in {
    validYouTubeUrl_?("ssasasdad") shouldBe false
  }

  it should "fail facebook with 'ssasasdad'" in {
    validFacebookUrl_?("ssasasdad") shouldBe false
  }

  it should "fail website with 'ssasasdad'" in {
    validUrl_?("ssasasdad") shouldBe false
  }

  it should "extract correct facebook id" in new TableDrivenPropertyChecks {

    val data = Table(
      ("url",                                                                        "id"                 ),
//     ------------------------------------------------------------------            ----------------------
      ("https://www.facebook.com/ericclapton",                                       "ericclapton"        ),
      ("https://www.facebook.com/Featurefm-497931363607317/?fref=ts",                "497931363607317"    ),
      ("http://www.facebook.com/#!/my_page_id",                                      "my_page_id"         ),
      ("http://www.facebook.com/pages/Paris-France/Vanity-Url/123456?v=app_555",     "123456"             ),
      ("http://www.facebook.com/pages/Vanity-Url/45678",                             "45678"              ),
      ("http://www.facebook.com/#!/page_with_1_number",                              "page_with_1_number" ),
      ("http://www.facebook.com/bounce_page#!/pages/Vanity-Url/45678",               "45678"              ),
      ("http://www.facebook.com/bounce_page#!/my_page_id?v=app_166292090072334",     "my_page_id"         ),
      ("https://www.facebook.com/Featurefm-497931363607317?ref=aymt_homepage_panel", "497931363607317"    ),
      ("http://www.facebook.com/my.page.is.great",                                   "my.page.is.great"   )
    )

    forAll(data) { (url: String, id: String) =>
      val Facebook(extract) = url
      extract shouldBe id
    }

  }

}
