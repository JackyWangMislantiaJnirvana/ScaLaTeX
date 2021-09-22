package jnirvana.scalatex.dsl.preamble

case class TColorBoxProblem() extends Preamble() {
  override val content: List[String] = List(
    "\\usepackage{tcolorbox}",
    """\newenvironment{tcbproblem}[1]{
      |    \begin{tcolorbox}[title = {Problem #1}]
      |}{
      |    \end{tcolorbox}
      |}""".stripMargin
  )
}
