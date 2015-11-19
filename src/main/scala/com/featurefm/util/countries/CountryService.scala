package com.featurefm.util.countries

import org.json4s.JsonAST._
import org.json4s.StringInput
import org.json4s.jackson.JsonMethods._

import scala.io.Source
import scalaj.http._


/**
 * Created by ymeymann on 06/07/15.
 */
class CountryService(contents: => String) {

  private def parseContent(content: String): Seq[(String,String)] = for {
    JObject(child) <- parse(StringInput(content))
    JField("name", JString(name)) <- child
    JField("altSpellings", altSpellings) <- child
    JArray(codes) <- altSpellings
    JString(code) <- codes.take(1)
  } yield (name.toString, code.toString.toLowerCase)

  private lazy val src = parseContent(contents)

  lazy val countries: Map[String, String] = Map[String, String](src.map(t => t.copy(_1 = t._1.toLowerCase)): _*)

  lazy val countryCodes: Map[String, String] = Map[String, String](src.map(_.swap): _*)

}

object CountryService {
  def getResource(name: String) = Source.fromURL(getClass.getResource(name)).mkString

  def localJson = getResource("/countries.json")
  def originalJson = Http("http://restcountries.eu/rest/v1/all").asString.body
}

import com.featurefm.util.countries.CountryService._

class LocalCountryService extends CountryService(localJson)

class OriginalCountryService extends CountryService(originalJson)
