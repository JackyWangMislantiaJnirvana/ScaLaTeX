package jnirvana.scalatex.dsl.preamble

case class Title(author: String, title: String) extends Preamble {
  override val content: List[String] = List(
    s"\\title{$title}\n" + s"\\author{$author}\n"
  )
}