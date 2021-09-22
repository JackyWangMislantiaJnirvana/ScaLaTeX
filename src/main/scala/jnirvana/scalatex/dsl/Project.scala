package jnirvana.scalatex.dsl

import jnirvana.scalatex.dsl.template.Template
import scopt.OParser

import java.io.File

trait Project {
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

  val mkCLI: collection.Seq[String] => Unit = {
    OParser.parse(parser, _, Config()) match {
      case Some(config) =>
        config match {
          case Config("compose", t) =>
            targets.filter(x => t.contains(x.title))
              .foreach(x => {
                println(s"📦Compositing target ${x.title}...")
                x.compositePhase(new File(sourceFilename), template)
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
  }
  // ---End of commandline stuff---
}