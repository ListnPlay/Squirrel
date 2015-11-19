package com.featurefm.util.slug

/**
 * Created by yardena on 10/13/15.
 */
class BetterSlugGenerator extends SlugGenerator {
    lazy val slugify = Slugify.default

    override def apply(name: String): String = slugify(name)
}
