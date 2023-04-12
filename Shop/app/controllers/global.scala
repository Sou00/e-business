package controllers

import scala.collection.mutable.ListBuffer

object global {
  var products = new ListBuffer[Product]
  var categories = new ListBuffer[Category]
  var carts = new ListBuffer[Cart]
}
