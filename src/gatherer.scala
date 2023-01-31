package koodi

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._


import java.util.Calendar
import java.text.SimpleDateFormat

import scala.collection.mutable





object gatherer {

  def day(): Int = {
    val now = Calendar.getInstance.getTime
    val dInt = new SimpleDateFormat("u")

    val d = dInt.format(now).toInt

    // returns the number of the day, for example Monday = 1, Sunday = 7

    d
  }

  def getSode(): (mutable.Buffer[String], mutable.Buffer[String]) = {

    // Sodexo Alppikortteli
    // uses scala-scraper library for extracting the info from restaurant's web site. Same library used as well in other
    // "get..." functions.

    val browser = JsoupBrowser()
    val doc = browser.get("https://www.sodexo.fi/ravintolat/ravintola-hdl-alppikortteli#nextweeklink")
    val items: Iterable[String] = doc >> extractor(".meal-name", texts)
    val allergies: Iterable[String] = doc >> extractor(".mealdietcodes", texts)

    var lista = mutable.Buffer[String]()
    var all = mutable.Buffer[String]()

    // ignores the unwanted info
    for (item <- items) {
      if (!item.contains("HENKILÖKUNTA") && !item.contains("Ravintola")) {
        lista += item
      }
    }
    for(a <- allergies) {
      a.split(" ").foreach(all += _)

    }
    val d = day()
    var res1 = mutable.Buffer[String]()
    var res2 = mutable.Buffer[String]()

     // checks today's "number" and adds dishes and allergies to separate lists
    if (d == 2) {
      res1 += lista(2)
      res1 += lista(3)
      res2 += all(2)
      res2 += all(3)
    } else if(d == 3) {
      res1 += lista(4)
      res1 += lista(5)
      res2 += all(4)
      res2 += all(5)
    } else if(d == 4) {
      res1 += lista(6)
      res1 += lista(7)
      res2 += all(6)
      res2 += all(7)
    } else if(d == 5) {
      res1 += lista(8)
      res1 += lista(9)
      res2 += all(8)
      res2 += all(9)
    } else {
      res1 += lista(0)
      res1 += lista(1)
      res2 += all(0)
      res2 += all(1)
    }

    // returns a tuple (dishes, allergies)
    (res1, res2)


  }

  def getPalmia(): mutable.Buffer[String] = {
    // Palmia Kaupungintalo
    val browser = JsoupBrowser()
    val doc = browser.get("https://ruoka.palmia.fi/fi/ravintola/ravintola/kaupungintalon-ravintola/")
    val items: Iterable[String] = doc >> extractor(".meal-name.invidual-restaurant-meal-name", texts)

    var lista = mutable.Buffer[String]()
    for(item <- items) {
        lista += item

    }
    val d = day()
    val res = mutable.Buffer[String]()

    // adds dishes + allergies to a list depending on the day's number

    if (d == 2) {
      res += lista(6)
      res += lista(7)
      res += lista(8)
      res += lista(9)
      res += lista(10)
      res += lista(11)
    } else if(d == 3) {
      res += lista(12)
      res += lista(13)
      res += lista(14)
      res += lista(15)
      res += lista(16)
      res += lista(17)
    } else if(d == 4) {
      res += lista(18)
      res += lista(19)
      res += lista(20)
      res += lista(21)
      res += lista(22)
      res += lista(23)
    } else if(d == 5) {
      res += lista(24)
      res += lista(25)
      res += lista(26)
      res += lista(27)
      res += lista(28)
      res += lista(29)
    } else {
      res += lista(0)
      res += lista(1)
      res += lista(2)
      res += lista(3)
      res += lista(4)
      res += lista(5)
    }
    // returns single list which contains dishes + allergies
    res
  }

  def getMaukas(): mutable.Buffer[String] = {
    // Ravintola Mau-Kas
    val browser = JsoupBrowser()
    val doc = browser.get("https://www.mau-kas.fi/ravintola.html")
    val items: Iterable[String] = doc >> extractor(".restaurant_menuitemname", texts)

    // there are only "today's" dishes

    var lista = mutable.Buffer[String]()
    for (item <- items) {
      lista += item
    }



    lista
  }

  def getValimo(): (mutable.Buffer[String], mutable.Buffer[String]) = {
    // Sodexo Valimo
    // almost exactly the same as Sodexo Alppikortteli

    val browser = JsoupBrowser()
    val doc = browser.get("https://www.sodexo.fi/ravintolat/ravintola-aalto-valimo")
    val items: Iterable[String] = doc >> extractor(".meal-name", texts)
    val allergies: Iterable[String] = doc >> extractor(".mealdietcodes", texts)

    var lista = mutable.Buffer[String]()
    var all = mutable.Buffer[String]()


    for (item <- items) {
      if (!item.contains("HENKILÖKUNTA") && !item.contains("Ravintola")) {
        lista += item
      }
    }
    for(a <- allergies) {
      all += a
    }
    val d = day()
    var res1 = mutable.Buffer[String]()
    var res2 = mutable.Buffer[String]()


    if (d == 2) {
      res1 += lista(2)
      res1 += lista(3)
      res2 += all(2)
      res2 += all(3)
    } else if(d == 3) {
      res1 += lista(4)
      res1 += lista(5)
      res2 += all(4)
      res2 += all(5)
    } else if(d == 4) {
      res1 += lista(6)
      res1 += lista(7)
      res2 += all(6)
      res2 += all(7)
    } else if(d == 5) {
      res1 += lista(8)
      res1 += lista(9)
      res2 += all(8)
      res2 += all(9)
    } else {
      res1 += lista(0)
      res1 += lista(1)
      res2 += all(0)
      res2 += all(1)
    }
    (res1, res2)

  }


}