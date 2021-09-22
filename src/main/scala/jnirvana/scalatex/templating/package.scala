package jnirvana.scalatex

import jnirvana.scalatex.dsl.template.Template

package object templating {
  // Template source:
  // src/main/twirl/LaTeXTemplate.scala.txt
  def render(template: Template, problems: List[Problem]): String =
    txt.LaTeXTemplate(
      template,
      problems
    ).toString

  // Parsing rule:
  // Everything between lines
  // %! <problem name=A1.1>
  // and
  // %! </problem>
  // is regarded as a problem;
  // TODO figure out hot to find the name of the problem

  sealed trait Token

  case class NormalLine(content: String) extends Token

  case class ProblemStartingLine(problemName: String) extends Token

  case class ProblemEndingLine() extends Token

  private val problemStartingLinePattern = ".*%! <problem name=(.*)>".r
  private val problemEndingLinePattern = ".*%! </problem>".r

  def tokenize: String => Token = {
    case problemStartingLinePattern(name) => ProblemStartingLine(name)
    case problemEndingLinePattern() => ProblemEndingLine()
    case l => NormalLine(l)
  }

  case class Problem(name: String, content: List[String])

  // FIXME line number report of some error message is not correct
  def parse(tokenized: List[Token]): List[Problem] = {
    type NumberedLines = List[(Token, Int)]
    def collect(restLines: NumberedLines): List[String] =
      restLines match {
        case Nil => parseError("Unexpected end-of-file", -1)
        case (headLine, lineNumber) :: tail =>
          headLine match {
            case ProblemStartingLine(name) => parseError("unpaired opening", lineNumber + 1)
            case ProblemEndingLine() => Nil
            case NormalLine(content) => content :: collect(tail)
          }
      }
    def skim(restLines: NumberedLines): List[Problem] =
      restLines match {
        case Nil => Nil
        case (headLine, lineNumber) :: tail => headLine match {
            case ProblemStartingLine(name) =>
              val collected = collect(tail)
              val collectedCount = collected.length
              Problem(name, collected) :: skim(tail.slice(collectedCount + 1, tail.length))
            case ProblemEndingLine() => parseError("unpaired closing", lineNumber + 1)
            case NormalLine(_) => skim(tail)
          }
      }
    def parseError(message: String, lineNumber: Int) =
      throw new IllegalStateException(s"line $lineNumber: $message")

    val numberedLines = tokenized.zipWithIndex
    skim(numberedLines)
  }
}
