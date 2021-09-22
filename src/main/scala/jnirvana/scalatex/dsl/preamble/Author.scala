package jnirvana.scalatex.dsl.preamble

case class Author(author: String) extends Preamble {
  override val content: List[String] = List(s"\\author{$author}\n")
}