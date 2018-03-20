package com.pizzashop

import com.pizzashop.CsvLoader.loadDataFromCsv

import scala.concurrent.duration.Duration

class CsvIngredientsStore extends IngredientsStore {

  val bases = loadDataFromCsv[Base]("/Bases.csv", createBase)
  val toppings = loadDataFromCsv[Topping]("/Toppings.csv", createTopping)

  override def getBase(base: String) = bases.find(_.name == base).get
  override def getTopping(topping: String) = toppings.find(_.name == topping).get

  private def createBase(fields: Array[String]): Base = {
    val Array(name, price, bakingTime) = fields
    Base(name, BigDecimal(price), Duration(s"$bakingTime.minutes"))
  }

  private def createTopping(fields: Array[String]): Topping = {
    val Array(name, price) = fields
    Topping(name, BigDecimal(price))
  }
}
