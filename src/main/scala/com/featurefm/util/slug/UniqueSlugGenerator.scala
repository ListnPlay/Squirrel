package com.featurefm.util.slug

import scala.concurrent.ExecutionContextExecutor

/**
 * Created by yardena on 11/19/15.
 */
class UniqueSlugGenerator(slugify: SlugGenerator, defaultPrefix: String, check: (String) => Boolean)
                              (implicit context: ExecutionContextExecutor)
  extends ((String) => String) {

  val inc = (suffix: Option[Int]) => if (suffix.isEmpty) Some(2) else suffix.map(_ + 1)

  override def apply(from: String): String = {
    var stage1 = slugify(from)
    if (stage1.isEmpty) stage1 = defaultPrefix
    def generate(s: String, suffix: Option[Int]): String = {
      val input = if (suffix.isEmpty) s else s"$s-${suffix.get.toString}"
      if (check(input)) input else generate(s, inc(suffix))
    }
    generate(stage1, None)
  }

}

object UniqueSlugGenerator {

  def apply(slugify: SlugGenerator, defaultPrefix: String)
           (check: (String) => Boolean)
           (implicit context: ExecutionContextExecutor): UniqueSlugGenerator =
    new UniqueSlugGenerator(slugify, defaultPrefix, check)
}
