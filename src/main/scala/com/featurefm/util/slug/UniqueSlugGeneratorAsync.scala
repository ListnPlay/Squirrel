package com.featurefm.util.slug

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
 * Created by yardena on 10/15/15.
 */
class UniqueSlugGeneratorAsync(slugify: SlugGenerator, defaultPrefix: String, check: (String) => Future[Boolean])
                         (implicit context: ExecutionContextExecutor)
      extends ((String) => Future[String]) {

  val inc = (suffix: Option[Int]) => if (suffix.isEmpty) Some(2) else suffix.map(_ + 1)

  override def apply(from: String): Future[String] = {
    var stage1 = slugify(from)
    if (stage1.isEmpty) stage1 = defaultPrefix
    def generate(s: String, suffix: Option[Int]): Future[String] = {
      val input = if (suffix.isEmpty) s else s"$s-${suffix.get.toString}"
      check(input).flatMap(if (_) Future.successful(input) else generate(s, inc(suffix)))
    }
    generate(stage1, None)
  }
  
}

object UniqueSlugGeneratorAsync {

  def apply(slugify: SlugGenerator, defaultPrefix: String)
           (check: (String) => Future[Boolean])
           (implicit context: ExecutionContextExecutor): UniqueSlugGeneratorAsync =
  new UniqueSlugGeneratorAsync(slugify, defaultPrefix, check)
}
