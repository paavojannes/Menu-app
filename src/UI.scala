package koodi

import java.text.SimpleDateFormat
import java.util.Calendar

import scala.io.StdIn._
import koodi.info._
import koodi.editor._

object UI extends App {
  // very simple UI
  // basic idea is that function run has a while loop and during the loop the function asks commands from the user
  // the loop ends when var on changes to 1 and then the app will close
  private var on = 0

  def date(): String = { // gives today's date in form dd-mm-yyyy
    val d = new SimpleDateFormat("d-M-y")
    val now = Calendar.getInstance().getTime()
    val res = d.format(now)
    res
  }
  this.run()

  private def run(): Unit = {
    val d = date() // today
    println("Hello, " + d + " menus are:\n")
    printer() // prints the menus
    println("Type 'help' for list of commands")
    while(on != 1) {

      getCommand() // gets the commands
    }
    println("Bye!")
  }

   def getCommand() = {
    val command = readLine("Command: ") // gets the command word

     val commandText = command.trim.toLowerCase // some trimming
     val verb = commandText.takeWhile( _ != ' ' )

    verb match { // matches the given command, idea stolen from ihan oma tekstipeli :D
    case "close" => Some(on += 1)
    case "edit" => Some(addUserInfo())
    case "update" => Some(printer())
    case "help" => Some(println("Command 'edit' -> edit preferences, diets and the favourite restaurant\n" +
      "Command 'update' -> refreshes the view\n" +
      "Command 'close' -> closes the app\n" +
      "Command 'info' -> information about the restaurants\n"))
    case "info" => Some(println("Sodexo Alppikortteli, Alppikatu 2 00530 Helsinki, Lunch 10:15-13:30 Mon-Fri\n" +
      "Sodexo Valimo, Metallimiehenkuja 2 02150 Espoo, Lunch 10:30-13:30 Mon-Fri\n" +
      "Mau-Kas, Vuorimiehentie 5 02150 Espoo, Lunch 10:30-14:00 Mon-Fri\n" +
      "Palmia Kaupungintalo, Sofiankatu 1-3 00170 Helsinki, Lunch 10:30-13:30 Mon-Fri\n"))
    case _ => Some(println("Command '" + verb + "' not found. Please try again."))
  }
  
   
  }
}