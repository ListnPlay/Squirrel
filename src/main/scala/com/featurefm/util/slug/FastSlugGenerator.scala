package com.featurefm.util.slug

import com.github.slugify.Slugify

/**
 * Created by yardena on 10/13/15.
 */
class FastSlugGenerator(private val replacements: Map[String,String] = FastSlugGenerator.customMappings) extends SlugGenerator {

  lazy val slugify = new Slugify() {
    def initialize(): Slugify = {
      setLowerCase(true)
      import scala.collection.JavaConverters._
      this.setCustomReplacements(replacements.asJava)
      this
    }
  }.initialize()

  def apply(name: String): String = {
    slugify.slugify(name)
  }
}

object FastSlugGenerator {
  val customMappings = Map("+" -> "-", "'" -> "", "\"" -> "", "ь" -> "", "ъ" -> "")
}
