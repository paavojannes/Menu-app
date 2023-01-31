package koodi

import java.io._

import scala.collection.mutable
import scala.io.Source

object readerWriter {

  // Functions for reading and writing the text file

  def reader(filename: String): mutable.Buffer[String] = {
    var data = mutable.Buffer[String]()
    val source = Source.fromFile(filename)
    for(rivi <- source.getLines) {
      data += rivi
    }
    source.close()
    data // returns a buffer

  }

  def writer(filename: String, input: String): Unit = {
    val fw = new FileWriter(filename, true)
    fw.write(input)
    fw.close()
  }
}
