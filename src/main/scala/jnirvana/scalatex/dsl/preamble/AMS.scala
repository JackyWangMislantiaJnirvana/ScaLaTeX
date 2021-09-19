package jnirvana.scalatex.dsl.preamble

case class AMS() extends Preamble {
  override val content: List[String] = List(
    "\\usepackage{amsmath}",
    "\\usepackage{amsthm}",
    "\\usepackage{amssymb}",
    "\\theoremstyle{definition} \\newtheorem{definition}{Definition}[section]",
    "\\theoremstyle{plain} \\newtheorem{proposition}{Proposition}[section]",
    "\\theoremstyle{remark} \\newtheorem{example}{Example}[section]",
    "\\theoremstyle{plain} \\newtheorem{theorem}{Theorem}[section]"
  )
}