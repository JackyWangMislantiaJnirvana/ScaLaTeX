package jnirvana.scalatex.dsl

import java.io.File

case class Target(title: String,
                  problems: List[String],
                  resources: List[String]) {
  def compositePhase(inputFile: File): Unit = println("Inside composite phase!")

  def compilePhase(): Unit = println("Inside compile phase!")

  def deployPhase(): Unit = println("Inside deploy phase!")
}