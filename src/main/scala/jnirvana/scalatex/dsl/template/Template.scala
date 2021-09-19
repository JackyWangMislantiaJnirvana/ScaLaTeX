package jnirvana.scalatex.dsl.template

import jnirvana.scalatex.dsl.preamble.Preamble

import scala.+:

trait Template {
  val documentClass: String

  def preambleItems: List[String] = Nil

  def and(preamble: Preamble): Template = {
    val p = preambleItems
    val d = documentClass
    new Template {
      override val documentClass: String = d
      override val preambleItems: List[String] = p ++ preamble.content
    }
  }
}
