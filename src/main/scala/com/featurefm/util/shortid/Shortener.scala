package com.featurefm.util.shortid

import java.lang.Integer.{MIN_VALUE, highestOneBit}
import java.nio.ByteBuffer
import java.util.{Base64, UUID}

import org.hashids._

import scala.util.hashing.MurmurHash3

/**
  * Created by yardena on 3/16/16.
  */
trait Shortener {

  lazy val hashIds = Hashids.reference(salt = "FEATURE.FM rulez 7he w0rld")
  val offset = highestOneBit(MIN_VALUE).toLong.abs

  def base64encode(uuid: UUID): String = {
    val bb = ByteBuffer.allocate(16)
    bb.putLong(uuid.getMostSignificantBits)
    bb.putLong(uuid.getLeastSignificantBits)
    Base64.getEncoder.encodeToString(bb.array)
  }

  def base64decode(str: String): UUID = {
    val bb = Base64.getDecoder.decode(str)
    UUID.nameUUIDFromBytes(bb)
  }

  def uniqueString(uuid: UUID): String = {
    val h = uuid.getMostSignificantBits
    val l = uuid.getLeastSignificantBits
    val end = "@-_~"
    val signs = (h < 0,l < 0) match {
      case (false, false) => end.charAt(0)
      case (false, true)  => end.charAt(1)
      case (true, false)  => end.charAt(2)
      case (true, true)   => end.charAt(3)
    }
    hashIds.encode(h.abs, l.abs) + signs
  }

  def restoreFromString(str: String): UUID = {
    val signs = str.last match {
      case '@' => ( 1, 1)
      case '-' => (-1, 1)
      case '_' => ( 1,-1)
      case '~' => (-1,-1)
    }
    val List(h, l) = hashIds.decode(str.substring(0, str.length - 1)).take(2)
    val bb = ByteBuffer.allocate(16)
    bb.putLong(h)
    bb.putLong(l)
    UUID.nameUUIDFromBytes(bb.array())
  }

  def shortStringHash(i: Int): String = {
    var hash = i.toLong
    if (hash < 0) { if (hash == MIN_VALUE) hash = hash.abs else hash = offset + hash.abs }
    hashIds.encode(hash)
  }

  def shortString(uuid: UUID): String = {
    shortStringHash(uuid.hashCode())
  }

  def matchesShortString(uuid: UUID, str: String): Boolean = {
    shortString(uuid) == str
  }

  private def byteArray2Long(arr: Array[Byte]): Long = {
    require(arr.length < 8, s"error in unique hash generator, array is too long (${arr.length})")
    val zeroPadded = Array.fill[Byte](8 - arr.length)(0) ++ arr
    ByteBuffer.wrap(zeroPadded).getLong
  }

  def shortUniqueUrlString(b: Array[Byte]): String = {
    hashIds.encode((if (b.length == 12) b.grouped(6) else b.grouped(7)).map(byteArray2Long).toSeq:_*)
  }

  def shortUrlString(b: Array[Byte]): String = {
    shortStringHash(MurmurHash3.bytesHash(b))
  }

  private def long2ByteArray(x: Long): Array[Byte] = {
    val buffer = ByteBuffer.allocate(java.lang.Long.BYTES)
    buffer.putLong(x)
    buffer.array()
  }

  def restore12BytesFromUrlString(str: String): Array[Byte] = {
    val List(a,b) = hashIds.decode(str)
    long2ByteArray(a).takeRight(6) ++ long2ByteArray(b).takeRight(6)
  }

}

object Shortener extends Shortener