package com.featurefm.util.slug

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSpec, Matchers}

class SlugifySpec extends FunSpec with Matchers with PropertyChecks {
  val slugify: SlugGenerator = new FastSlugGenerator()

  info("based on https://github.com/osinka/slugify/blob/master/src/test/scala/slugifySpec.scala")

//  import com.ibm.icu.text.Transliterator
//  import scala.collection.JavaConversions._
//  val x = Transliterator.getAvailableIDs
//  while(x.hasMoreElements) {
//    println(x.nextElement())
//  }

  describe("Slugify") {
    it("builds correct alias for big chi chi") {
      slugify("Big Chi Chi") should equal("big-chi-chi")
    }
    it("builds correct alias for daily basis") {
      slugify("Daily Basis") should equal("daily-basis")
    }
    it("builds correct alias for Jackson 5") {
      slugify("Jackson 5") should equal("jackson-5")
    }
    it("builds correct alias for Destiny's Child") {
      slugify("Destiny's Child") should equal("destinys-child")
    }
    it("builds correct alias for wikipedia example") {
      slugify("This, That & the Other! Various Outré Considerations") should equal("this-that-the-other-various-outre-considerations")
    }
//    it("transliterate Hebrew") {
//      slugify("שלום") should equal("slwm")
//    }
    it("converts deutsch ß to ss") {
      slugify("ß") should equal("ss")
    }
    it("keeps _") {
      slugify("* --__- *") should equal("__")
    }
    it("replaces any number of whitespaces with minus") {
      slugify("1  3  6") should equal("1-3-6")
    }
    it("drops heading or ending spaces") {
      slugify("   6    ") should equal("6")
    }
    it("transliterates cyrillic characters w/ BGN romanization") {
//      slugify("а б в г д е ж з и й к л м н о п р с т у ф х ц ч ш щ э ю я ь ъ") should equal("a-b-v-g-d-e-zh-z-i-i-k-l-m-n-o-p-r-s-t-u-f-kh-ts-ch-sh-sht-e-yu-ya-u")
      slugify("Ольга") should equal("olga")
      slugify("Сергей Гончар") should equal("sergej-gonchar")
    }
    it("drops accents") {
      slugify("Ȳá") should equal("ya")
    }
    it("drops non-characters") {
      // there is a lot of characters that cannot be translated into latin
      // mostly greek, math, etc.
      slugify("ҥѶ") should equal("") //Ƈ
    }
    it("replaces select punctuation with dashes") {
      slugify("""A+l[on]g-l(in)e@{wit}h "a'hu'as"bol&s""") should equal("a-l-on-g-l-in-e-wit-h-ahuasbol-s")
    }
    it("lowercases") {
      slugify("""ФЫВАЯYAUSL""") should equal("""fyvayayausl""")
    }
    it("meets the `slug` laws") {
      forAll { (str: String) =>
        val slug = slugify(str)
        slug should not(startWith("-") and endWith("-"))
        slug.count(_.isUpper) should equal(0)
        slug.count(_.isSpaceChar) should equal(0)
        slug should not(include("--"))
        slug should not(include("""[^-_\p{ASCII}\p{Digit}]"""))
        slug should not(include("""[^-_\p{Latin}\p{Digit}]"""))
      }
    }
  }
}