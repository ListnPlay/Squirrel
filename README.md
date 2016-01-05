# Squirrel
Utilities for dealing with URLs in Scala, like url validation and translating country code to name and vice versa.
This project consists of 3 utilities:
* URLs - contains a set of regular expressions to recognize facebook, twitter and youtube urls
* Slugify - generates SEO-friendly "alias" from a string using https://github.com/slugify/slugify, but in addition supports pluggable uniqueness test and returns a unique alias
* Country code converter - maps country name to two-character code and vice versa based on http://restcountries.eu/

To use, include this in your sbt: `"com.featurefm" %% "squirrel" % "0.1.7"`

Supports Scala 2.11 only.