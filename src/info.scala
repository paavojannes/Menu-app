package koodi

import java.io._

import koodi.readerWriter._
import koodi.editor._

import scala.collection.mutable
import scala.io.StdIn._



object info {

  // variables for saving temporary info
  var allergies: mutable.Buffer[String] = mutable.Buffer[String]()
  var favourite = ""
  var preferences: mutable.Buffer[String] = mutable.Buffer[String]()

  def getInfo(): (String, mutable.Buffer[String], mutable.Buffer[String])  = {
    // gets the info from the text file
    erase()

    val info = reader("userData.txt")
    if(info.nonEmpty) {
      val splitted = info.head.split(";")
      if(splitted.nonEmpty && splitted.size < 4) {
        val alle = splitted(2).split(",")
        val pref = splitted(1).split(",")
        favourite = splitted(0)
        alle.foreach(allergies += _)
        pref.foreach(preferences += _)
      } else {
        println("Error in typing, please try again\n")
      }

    }

    // returns a triple
    new Triple(favourite, preferences, allergies)


  }
  def addInfo(): Unit = {
    // adds info to the text file by using variables
    val alle = allergies.mkString(",")
    val pref = preferences.mkString(",")
    val res = favourite + ";" + pref + ";" + alle

    val pw = new PrintWriter("userData.txt") // Clears the text file
    pw.write("")
    pw.close()

    writer("userData.txt", res) // Writes to the text file


  }

  def addUserInfo(): Unit = {
    // gets the user info from the user and adds it to the text file using the addInfo() function
    erase()
    println("Tell your preferences by answering the following questions.\n" +
      "The app will save the given information so you don't need to do this every time you use the app :)\n" +
      "Information is used for ordering the menus so the app will show you first the menus which fits your preferences the most.\n" +
      "Favourite restaurant will always be the first restaurant in the listing\n" +
      "Information has to be given in a correct form.\n")
    val fave = readLine("What's your favourite restaurant?: (Sodexo Alppikortteli, Sodexo Valimo, Palmia Kaupungintalo, Mau-Kas) ")
    val pref = readLine("Which food items do you prefer?: (separarate the items with a comma, for example kana, kanaa, riisiä) ")
    val aller = readLine("Do you have allergies or some special diet?: (separarate the items with a comma and use diet codes 'G', 'V', 'L' etc., for example G, L, V) ")

    println("\nSaving...\n")

    favourite = fave
    val p = pref.split(", ")
    val a = aller.split(", ")

    p.foreach(preferences += _)
    a.foreach(allergies += _)

    addInfo()

    erase()

    println("Saved successfully!\n")
    printer() // "refreshes" the app after updating the user info

  }
  def erase(): Unit = {
    // clears the "temporary memory" aka variables
    allergies = mutable.Buffer[String]()
    favourite = ""
    preferences = mutable.Buffer[String]()
  }


}
