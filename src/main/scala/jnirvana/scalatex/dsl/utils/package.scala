package jnirvana.scalatex.dsl

import java.io.File

package object utils {
  def pmsDeploy(): Unit = {}

  def mkDirScheme(targetName: String): Unit = {
    val sourceResourceDirectory = new File("./resource")
    if (!sourceResourceDirectory.exists()) {
      println("No source resource directory. Creating one.")
      sourceResourceDirectory.mkdir()
    }

    val rootBuildDirectory = new File("./build")
    if (!rootBuildDirectory.exists()) {
      println("No root build directory. Creating one.")
      rootBuildDirectory.mkdir()
    }

    val targetBuildDirectory = new File(s"./build/$targetName")
    if (!targetBuildDirectory.exists()) {
      println("No target build directory. Creating one.")
      targetBuildDirectory.mkdir()
    }
  }
}
