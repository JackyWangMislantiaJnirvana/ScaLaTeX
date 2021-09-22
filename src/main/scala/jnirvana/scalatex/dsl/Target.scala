package jnirvana.scalatex.dsl

import jnirvana.scalatex.dsl.template.Template
import jnirvana.scalatex.dsl.utils._
import jnirvana.scalatex.templating

import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source

case class Target(title: String,
                  problemNames: List[String],
                  resources: List[String]) {
  def compositePhase(inputFile: File, template: Template): Unit = {
    println("Inside composite phase!")

    println("Generating necessary directory scheme for the build...")
    mkDirScheme(title)

    println("Parsing the source document into problems...")
    val sourceDocument = Source.fromFile("./source.tex")
    val tokenized = sourceDocument
      .getLines()
      .map(templating.tokenize)
      .toList
    val problems = templating
      .parse(tokenized)
      .filter(x => problemNames.contains(x.name))

    println("Composing target from template...")
    val renderedText = templating.render(template, problems)
    val renderedFile = new File(s"./build/$title/$title.tex")
    val bw = new BufferedWriter(new FileWriter(renderedFile))
    bw.write(renderedText)
    bw.close()
  }

  def compilePhase(): Unit = println("Inside compile phase!")

  def deployPhase(): Unit = println("Inside deploy phase!")
}