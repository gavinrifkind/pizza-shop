package com.pizzashop

import scala.io.Source

object CsvLoader {
  def loadDataFromCsv[T](fileName: String, converter: (Array[String]) => T): List[T] =
    Source.fromInputStream(getClass.getResourceAsStream(fileName))
      .getLines
      .drop(1)
      .map(_.split(","))
      .map(converter)
      .toList
}