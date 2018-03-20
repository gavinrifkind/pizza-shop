package com.pizzashop

import scala.concurrent.duration.{Duration, DurationInt}

trait PizzaBuilders {
  private val defaultBase = "default"

  def aBase(withName: String = defaultBase, withPrice: BigDecimal = 20.00, withBakingTime: Duration = 10.minutes) =
    Base(withName, withPrice, withBakingTime)

  def aTopping(withName: String = "", withPrice: BigDecimal = 3.00) = Topping(withName, withPrice)

  def aPizza(withBase: String = defaultBase, withToppings: List[String] = List.empty) =
    Pizza(withBase, withToppings)
}
