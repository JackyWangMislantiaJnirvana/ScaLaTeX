import scopt.OParser

import java.io.File

// How to run in the final situation
// scala -classpath ScalaTeX\ RC3.jar usingDep.scala

trait Template {
  val documentClass: String

  def preambleItems: List[Preamble] = Nil

  def and(preamble: Preamble): Template = {
    val p = preambleItems
    val d = documentClass
    new Template {
      override val documentClass: String = d
      override val preambleItems: List[Preamble] = p.appended(preamble)
    }
  }
}

class Preamble

case class Target(title: String,
                  problems: List[String],
                  resources: List[String]) {
  def compositePhase(inputFile: File): Unit = println("Inside composite phase!")

  def compilePhase(): Unit = println("Inside compile phase!")

  def deployPhase(): Unit = println("Inside deploy phase!")
}

case class AMS() extends Preamble {
  override def toString: String =
    "\\usepackage{amsmath}\n" + "\\usepackage{amsthm}\n" + "\\usepackage{amssymb}\n" +
      """\theoremstyle{definition} \newtheorem{definition}{Definition}[section]
        |\theoremstyle{plain} \newtheorem{proposition}{Proposition}[section]
        |\theoremstyle{remark} \newtheorem{example}{Example}[section]
        |\theoremstyle{plain} \newtheorem{theorem}{Theorem}[section]""".stripMargin
}

case class Geometry(paper: String = "a4paper",
                    left: String = "2cm",
                    right: String = "2cm",
                    top: String = "1.5cm",
                    bottom: String = "1.5cm"
                   ) extends Preamble {
  override def toString: String =
    s"\\usepackage[$paper, left=$left, right=$right, top=$top, bottom=$bottom]{geometry}"
}

case class Title(author: String, title: String) extends Preamble {
  override def toString: String =
    s"\\title{$title}\n" + s"\\author{$author}\n"
}

case class Article() extends Template {
  override val documentClass: String = "article"
}


trait Project extends App {
  val sourceFilename: String
  val template: Template
  val targets: List[Target]

  // ---Start of commandline stuff---
  case class Config(
                     command: String = "",
                     tasks: List[String] = Nil
                   )
  val builder = OParser.builder[Config]
  val parser = {
    import builder._
    OParser.sequence(
      programName("<prefix>"),
      head("ScaLaTeX Builder", "rev3 indev"),
      cmd("compose")
        .action((_, c) => c.copy(command = "compose"))
        .children(
          arg[String]("<target to run>...")
            .unbounded()
            .action((x, c) => c.copy(tasks = c.tasks :+ x))
        ),
      cmd("compile")
        .action((_, c) => c.copy(command = "compile"))
        .children(
          arg[String]("<target to run>...")
            .unbounded()
            .action((x, c) => c.copy(tasks = c.tasks :+ x))
        ),
      cmd("deploy")
        .action((_, c) => c.copy(command = "deploy"))
        .children(
          arg[String]("<target to run>...")
            .unbounded()
            .action((x, c) => c.copy(tasks = c.tasks :+ x))
        ),
      help("help")
    )
  }
  val mkCLI: collection.Seq[String] => Unit = OParser.parse(parser, _, Config()) match {
    case Some(config) =>
      config match {
        case Config("compose", t) =>
          targets.filter(x => t.contains(x.title))
            .foreach(x => {
              println(s"📦Compositing target ${x.title}...")
              x.compositePhase(new File(sourceFilename))
            })
        case Config("compile", t) =>
          targets.filter(x => t.contains(x.title))
            .foreach(x => {
              println(s"🔨Compiling target ${x.title}...")
              x.compilePhase()
            })
        case Config("deploy", t) =>
          targets.filter(x => t.contains(x.title))
            .foreach(x => {
              println(s"🚚Deploying task ${x.title}...")
              x.deployPhase()
            })
        case _ =>
      }
    case _ =>
  }
  // ---End of commandline stuff---
}


object EndConfig extends Project {
  override val sourceFilename: String = "source.tex"

  override val template: Template =
    Article() and Geometry() and Title(author = "Jacky", title = "Test")

  override val targets: List[Target] = List(
    Target(
      title = "Assignment1",
      problems = List(
        "A1.1",
        "A1.2"
      ),
      resources = List(
        "figA1.1.pdf",
        "figA1.2.pdf"
      )
    ),
    Target(
      title = "Assignment2",
      problems = List(
        "A2.1",
        "A2.2"
      ),
      resources = List(
        "figA1.1.pdf",
        "figA1.2.pdf"
      )
    ),
    Target(
      title = "Assignment3",
      problems = List(
        "A3.1"
      ),
      resources = Nil
    )
  )

  mkCLI(args)
}

object Playground {
  println("hello!")
}