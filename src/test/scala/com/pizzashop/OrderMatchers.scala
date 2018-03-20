package com.pizzashop

import org.specs2.matcher.Matchers

import scala.concurrent.duration.Duration

trait OrderMatchers {
  self: Matchers =>

  def bakeFor(bakingTime: Duration) = be_===(bakingTime) ^^ ((_: Order).bakingTime aka "Baking Time")

  def cost(price: BigDecimal) = be_===(price) ^^ ((_: Order).price aka "Price")

  def getAFreeDrink(freeDrink: Boolean) = be_===(freeDrink) ^^ ((_: Order).freeDrink aka "Free Drink")
}