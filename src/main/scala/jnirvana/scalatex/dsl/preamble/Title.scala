package jnirvana.scalatex.dsl.preamble

case class Title(title: String) extends Preamble {
  override val content: List[String] = List(s"\\title{$title}\n")
}