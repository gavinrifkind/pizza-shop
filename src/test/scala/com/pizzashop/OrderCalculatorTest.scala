package com.pizzashop

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

import scala.concurrent.duration.{Duration, DurationInt}

class OrderCalculatorTest extends Specification
  with Mockito
  with OrderMatchers
  with PizzaMakers {

  "OrderCalculator" should {

    "Calculate the baking time according to the base" in new Context {

      givenBakingTime(thin, 10.minutes)

      orderCalculator.calculate(aPizza(withBase = thin)) must bakeFor(10.minutes)
    }

    "Calculate the price according to base and toppings" in new Context {

      givenBasePrice(thin, 20.00)
      givenToppingPrices(olives -> 3.00, mushrooms -> 2.00)

      orderCalculator.calculate(
        aPizza(withBase = thin, withToppings = List(olives, mushrooms))) must cost(25.00)
    }

    "Not give a free drink when price is 25 or less" in new Context {

      givenBasePrice(thin, 20.00)
      givenToppingPrices(olives -> 2.00, mushrooms -> 3.00)

      orderCalculator.calculate(
        aPizza(withBase = thin, withToppings = List(olives, mushrooms))) must getAFreeDrink(false)
    }

    "Give a free drink when price is more than 25" in new Context {

      givenBasePrice(thin, 20.00)
      givenToppingPrices(olives -> 2.00, mushrooms -> 3.50)

      orderCalculator.calculate(
        aPizza(withBase = thin, withToppings = List(olives, mushrooms))) must getAFreeDrink(true)
    }
  }

  abstract class Context extends Scope {

    val ingredientsStore = mock[IngredientsStore]
    val orderCalculator = new OrderCalculator(ingredientsStore)
    val thin = "thin"
    val olives = "olives"
    val mushrooms = "mushrooms"

    def givenBakingTime(base: String, bakingTime: Duration) =
      ingredientsStore.getBase(base) returns aBase(withBakingTime = bakingTime)

    def givenBasePrice(base: String, price: BigDecimal) =
      ingredientsStore.getBase(base) returns aBase(withPrice = price)

    def givenToppingPrices(toppingPrices: (String, BigDecimal)*) =
      toppingPrices.collect{
        case (topping, price) => ingredientsStore.getTopping(topping) returns aTopping(topping, price)
      }
  }
}
