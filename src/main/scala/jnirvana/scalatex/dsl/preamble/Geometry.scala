package jnirvana.scalatex.dsl.preamble

case class Geometry(paper: String = "a4paper",
                    left: String = "2cm",
                    right: String = "2cm",
                    top: String = "1.5cm",
                    bottom: String = "1.5cm"
                   ) extends Preamble {
  override val content: List[String] = List(
    s"\\usepackage[$paper, left=$left, right=$right, top=$top, bottom=$bottom]{geometry}"
  )
}
