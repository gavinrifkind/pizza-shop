package com.pizzashop

import scala.concurrent.duration.Duration

case class UseCase(pizzaName: String,
                   base: String,
                   toppings: List[String],
                   expectedPrice: BigDecimal,
                   expectedBakingTime: Duration,
                   expectFreeDrink: Boolean)

object UseCase {
  def apply(fields: Array[String]): UseCase = {
    val Array(name, base, toppings, price, bakingTime, freeDrink) = fields
    UseCase(
      name,
      base,
      toppings.split(";").toList,
      BigDecimal(price),
      Duration(s"$bakingTime.minutes"),
      freeDrink == "Y")
  }
}
