package koodi

import scala.collection.mutable

case class Menu(dishes: mutable.Buffer[String], allergies: mutable.Buffer[String], restaurant: String, order: Int) {

  // a class for menus, def p() prints the wanted info for example different dishes

  def p(): Unit = {
    if(this.order == -10000) {
      println(this.restaurant + " FAVOURITE")
    } else {
      println(this.restaurant)
    }
    if (this.restaurant == "Sodexo Alppikortteli" || this.restaurant == "Sodexo Valimo") {
      // sodexo restaurants doesn't have diet codes included into the menu name so some extra for loop needed
      for (d <- dishes) {
        println(d + " " + this.allergies(dishes.indexOf(d)))
      }
    } else {

        for (d <- dishes) {

          println(d)
        }

      }
      println("\n")
    }



}
