package com.pizzashop

import scala.concurrent.duration.Duration

class OrderCalculator(ingredientsStore: IngredientsStore) {

  def calculate(pizza: Pizza): Order = {
    val base = ingredientsStore.getBase(pizza.base)
    val price = base.price + pizza.toppings.foldLeft(BigDecimal(0.00))(addToppingPrice)
    Order(
      price = price,
      bakingTime = base.bakingTime,
      freeDrink =  price > 25.00)
  }

  private def addToppingPrice(total: BigDecimal, topping: String) =
    if (topping.isEmpty) total else total + ingredientsStore.getTopping(topping).price
}

case class Base(name: String, price: BigDecimal, bakingTime: Duration)
case class Topping(name: String, price: BigDecimal)
case class Pizza(base: String, toppings: List[String])
case class Order(price: BigDecimal, bakingTime: Duration, freeDrink: Boolean)
