package com.pizzashop

trait IngredientsStore {
  def getBase(base: String): Base
  def getTopping(topping: String): Topping
}
