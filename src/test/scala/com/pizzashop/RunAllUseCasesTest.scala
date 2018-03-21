package com.pizzashop

import org.specs2.mutable.Specification

import com.pizzashop.CsvLoader.loadDataFromCsv
import org.specs2.specification.core.Fragments
import com.pizzashop.UseCase.apply

class RunAllUseCasesTest extends Specification
  with PizzaMakers
  with OrderMatchers {

  val ingredientsStore = new CsvIngredientsStore
  val orderCalculator = new OrderCalculator(ingredientsStore)

  val useCases = loadDataFromCsv[UseCase]("/UseCases.csv", apply)

  Fragments.foreach(useCases)(execute)

  private def execute(useCase: UseCase) = {
    import useCase._
    val drinkMessage = if (expectFreeDrink) "with a free drink 🍺" else "sorry no drink 🙁"

    s"$pizzaName 🍕 $base costs $expectedPrice💲, bakes for $expectedBakingTime, $drinkMessage" >> {

      orderCalculator.calculate(
        aPizza(withBase = base, withToppings = toppings)) must
        cost(expectedPrice) and
        bakeFor(expectedBakingTime) and
        getAFreeDrink(expectFreeDrink)
    }
  }
}
