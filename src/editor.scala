package koodi

import koodi.gatherer._
import koodi.info._

import scala.collection.mutable

object editor {

  var menus: mutable.Buffer[Menu] = mutable.Buffer[Menu]() // a list for menus
  var ud: (String, mutable.Buffer[String], mutable.Buffer[String]) = getInfo() // gets user info from the text file
  var fave: String = ud._1 // favourite restaurant
  var uAller: mutable.Buffer[String] = ud._3 // user allergies/diets
  var uPref: mutable.Buffer[String] = ud._2 // preferred food items

  def update(): Unit = { // clears the "temporary memory"
    menus = mutable.Buffer[Menu]()
    ud = getInfo()
    fave = ud._1
    uAller = ud._3
    uPref = ud._2
  }

  def compare(uAll: mutable.Buffer[String], dAller: mutable.Buffer[String], uPref: mutable.Buffer[String], dItems: mutable.Buffer[String]): Int = {
    // compares user preferences with dishes. really brute force :D solution but it works
    // returns a number which is calculated comparing the attributes mentioned above
    // the smaller the number, the more likely the restaurant's menu fits for users preferences and diets
    // used for every restaurant
    var res = 0
    var counter1 = 0
    var counter2 = 0
    while(counter1 < uAll.size) {
      for(a <- dAller) {
        if(uAll(counter1).trim.contains(a) || uAll(counter1).trim.contains(a + ",")) {
          res -= 1
        }
      }
      counter1 += 1
    }

    while(counter2 < uPref.size) {
      for(a <- dItems) {
        if(uPref(counter2).trim.contains(a) || uPref(counter2).trim.contains(a + ",")) {
          res -= 1
        }
      }
      counter2 += 1
    }


    res
  }


  def createSode(): Unit = {
    // sodexo alppikortteli

    val sMenu: mutable.Buffer[String] = getSode()._1 // gets the menus from web
    val sAller: mutable.Buffer[String] = getSode()._2 // allergies from web
    var menuItems = mutable.Buffer[String]()

    for(m <- sMenu) { // for comparing
      m.split(" ").foreach(menuItems += _)

    }

    var sOrder = 0

    if(fave != "Sodexo Alppikortteli") {
      sOrder = compare(uAller, sAller, uPref, menuItems) // generates the number for ordering the menus
    } else {
      sOrder = -10000 // favourite restaurant will always be the first menu in order
    }

      menus += Menu(sMenu, sAller, "Sodexo Alppikortteli", sOrder) // adds the menu to the list as a new Menu
    }

  def createMaukas(): Unit = {
    // Mau-Kas

    val mMenu: mutable.Buffer[String] = getMaukas()
    var mAller = mutable.Buffer[String]()
    var menuItems = mutable.Buffer[String]()

    for(m <- mMenu) {
      m.split(" ").foreach(menuItems += _)

    }

    for(m <- mMenu) {
      m.split("").drop(1).foreach(mAller += _)

    }
    var order = 0

    if(fave != "Mau-Kas") {
      order = compare(uAller, mAller, uPref, menuItems)
    } else {
      order = -10000
    }
    menus += Menu(mMenu, mAller, "Mau-Kas", order)
  }

  def createPalmia(): Unit = {
    // Palmia Kaupungintalo
    val mMenu: mutable.Buffer[String] = getPalmia()
    var mAller = mutable.Buffer[String]()
    var menuItems = mutable.Buffer[String]()

    for(m <- mMenu) {
      m.split(" ").foreach(menuItems += _)

    }

    for(m <- mMenu) {
      m.split("").drop(1).foreach(mAller += _)

    }
    var order = 0

    if(fave != "Palmia Kaupungintalo") {
      order = compare(uAller, mAller, uPref, menuItems)
    } else {
      order = -10000
    }
    menus += Menu(mMenu, mAller, "Palmia Kaupungintalo", order)
  }

  def createValimo(): Unit = {
    // Sodexo Valimo
    val sMenu: mutable.Buffer[String] = getValimo()._1
    val sAller: mutable.Buffer[String] = getValimo()._2
    var menuItems = mutable.Buffer[String]()

    for(m <- sMenu) {
      m.split(" ").foreach(menuItems += _)

    }

    var sOrder = 0

    if(fave != "Sodexo Valimo") {
      sOrder = compare(uAller, sAller, uPref, menuItems)
    } else {
      sOrder = -10000
    }

    menus += Menu(sMenu, sAller, "Sodexo Valimo", sOrder)
  }

  def printer(): Unit = { // prints the menus to the screen
    println("Loading menus...\n")
    update()
    createSode()
    createPalmia()
    createMaukas()
    createValimo()
     val m = menus.sortBy(_.order) // checks the order numbers, the smaller the number is the higher the menu goes in order
      m.foreach(_.p())
  }

}
