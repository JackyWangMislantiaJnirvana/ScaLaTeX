#!/bin/sh
exec scala -savecompiled -classpath "/Users/jacky/Documents/Workspace/ScalaTeX RC3/target/scala-2.13/ScalaTeX RC3-assembly-0.1.jar" "$0" "$@"
!#

import jnirvana.scalatex.dsl._
import jnirvana.scalatex.dsl.preamble._
import jnirvana.scalatex.dsl.template._

object Build extends Project {
  override val sourceFilename: String = "source.tex"

  override val template: Template =
    Article() and
      Geometry() and
      Title("Assignment of Real Analysis") and
      AMS() and
      TColorBoxProblem()

  override val targets: List[Target] = List(
    Target(
      title = "Assignment1",
      problemNames = List(
        "A1.1",
        "A1.2",
      ),
      resources = Nil
    ),
    Target(
      title = "Assignment2",
      problemNames = List(
        "A2.1"
      ),
      resources = Nil
    )
  )
}

// Don't touch this
Build.mkCLI(args)
