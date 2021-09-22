package jnirvana.scalatex.dsl

import jnirvana.scalatex.dsl.template.Template
import jnirvana.scalatex.dsl.utils._
import jnirvana.scalatex.templating

import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source

case class Target(title: String,
                  problemNames: List[String],
                  resources: List[String]) {
  def compositePhase(sourceFileName: String, template: Template): Unit = {
    println("Inside composite phase!")

    println("Generating necessary directory scheme for the build...")
    mkDirScheme(title)

    import sys.process._
    println("Copying resources...")
    for (name <- resources) {
      val targetPath = new File(s"./resource/$name").getAbsolutePath
      val linkPath = new File(s"./build/$title/$name").getAbsolutePath
      s"ln -s '$targetPath' '$linkPath'".!
    }

    println("Parsing the source document into problems...")
    val sourceDocument = Source.fromFile(s"./$sourceFileName")
    val tokenized = sourceDocument
      .getLines()
      .map(templating.tokenize)
      .toList
    val problems = templating
      .parse(tokenized)
      .filter(x => problemNames.contains(x.name))

    println("Composing target document from template...")
    val renderedText = templating.render(template, problems)
    val renderedFile = new File(s"./build/$title/$title.tex")
    val bw = new BufferedWriter(new FileWriter(renderedFile))
    bw.write(renderedText)
    bw.close()
  }

  def compilePhase(): Unit = {
    println("Inside compile phase!")
    import sys.process._
    val result = (s"xelatex" +
      s" -interaction=nonstopmode" +
      s" -file-line-error" +
      s" -output-directory=./build/$title/" +
      s" ./build/$title/$title.tex").!
    println(s"Compiler exited with status code $result.")
  }

  def deployPhase(): Unit = println("Inside deploy phase!")
}