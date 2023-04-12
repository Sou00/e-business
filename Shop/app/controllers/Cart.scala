package controllers

import com.google.common.collect.ImmutableList

import scala.collection.mutable.ListBuffer

case class Cart(
                 id: Int,
                 products: ListBuffer[Product]
               )
