//package jnirvana.scalatex.dsl
//
//import java.io.File
//
//class SplittingTarget(
//                            title: String,
//                            problems: List[String],
//                            resources: List[String]
//                          ) extends Target {
//  assert(
//    resources.forall(new File(_).exists()),
//    s"Cannot find resource(s): ${resources.filter(!new File(_).exists())}"
//  )
//
//  override val inputFilename = "source.tex"
//
//  override val outputFilename: String = s"${title}.tex"
//
//  override def compositePhase(): Unit = {
//    val workingDirectory = new File(".")
//    val sourceFile = new File(s"./${title}")
//
//    val buildDirectory = new File(s"./build/${title}")
//    if (!buildDirectory.exists()) {
//      println("No build directory. Creating one.")
//      buildDirectory.mkdir()
//    }
//
//    val resourceDirectory = new File(s"./build/${title}/resource")
//    if (!resourceDirectory.exists()) {
//      println("No resource directory. Creating one.")
//      resourceDirectory.mkdir()
//    }
//  }
//
//  override def compilePhase(): Unit = {
//    import sys.process._
//    // TODO
//    val result = s"xelatex" +
//      s" -interaction=nonstopmode" +
//      s" -file-line-error" +
//      s" -output-directory=./build/${outputFilename}" +
//      s" ./build/${title}/${outputFilename}".!
//  }
//
//  override def deployPhase(): Unit = {
//    println("Nothing to deploy so far.")
//  }
//}
