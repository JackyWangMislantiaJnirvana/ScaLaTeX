abstract class Task {
  def preambleItems: List[String]
}

class SplittingTask extends Target {
  override def preambleItems: List[String] = List(
    """\begin{document}
      |\maketitle
      |<slot>
      |\end{document}""".stripMargin
  )
  def printPreamble(): Unit = preambleItems.foreach(println(_))
}

trait AMS extends Target {
  private val content =
    "\\usepackage{amsmath}\n" + "\\usepackage{amsthm}\n" + "\\usepackage{amssymb}\n"
  abstract override def preambleItems: List[String] =
    content :: super.preambleItems
}
trait Geometry extends Target {
  val paper: String = "a4paper"
  val left: String = "2cm"
  val right: String = "2cm"
  val top: String = "1.5cm"
  val bottom: String = "1.5cm"

  private val content =
    s"\\usepackage[$paper, left=$left, right=$right, top=$top, bottom=$bottom]{geometry}"
  abstract override def preambleItems: List[String] =
    content :: super.preambleItems
}
trait Title extends Target {
  val author: String
  val title: String

  private val content =
    s"\\title{$title}\n" + s"\\author{$author}\n"

  abstract override def preambleItems: List[String] =
    content :: super.preambleItems
}

trait MyTitle extends Title {
  override val author = "Jacky Wang"
  override val title = "Test"
}

class Configured extends SplittingTask with AMS with Geometry with MyTitle
(new Configured).printPreamble()

//trait AA extends AMS {
//  override val AValue: String = "AA"
//}
//class AnotherConfigured extends SplittingTask with AA with Geometry with Title
//(new AnotherConfigured).printPreamble()