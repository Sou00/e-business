package controllers

import controllers.global.{carts, products}
import play.api.libs.json._
import play.api.mvc._

import javax.inject._
import scala.collection.mutable.ListBuffer

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class CartController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val productWrites = new Writes[Product] {
    def writes(product: Product) = Json.obj(
      "Id"  -> product.id,
      "Name" -> product.name,
      "Category" -> product.category
    )
  }
  implicit val cartWrites = new Writes[Cart] {
    def writes(cart: Cart) = Json.obj(
      "Id"  -> cart.id,
      "Products" -> cart.products
    )
  }
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def getAll = Action {
    val result = Json.toJson(carts)
    Ok(result)
  }
  def getSingle(id: Int) = Action {
    val result = Json.toJson(carts.filter(_.id == id))
    Ok(result)
  }
  def post(id: Int) = Action {
    val p = Cart(id,new ListBuffer[Product])
    carts.addOne(p)
    Ok("Added!")
  }
  def update(id: Int, itemId: Int) = Action {
    val index = carts.indexWhere(_.id == id)
    val itemIndex = products.indexWhere(_.id == itemId)
    carts(index).products.addOne(products(itemIndex))
    Ok("Item added!")
  }

  def delete(id: Int) = Action {
    val index = carts.indexWhere(_.id == id)
    val removed = carts.remove(index)
    Ok("Deleted!")
  }

  def deleteItem(id: Int,itemId: Int) = Action {
    val index = carts.indexWhere(_.id == id)
    val itemIndex = carts(index).products.indexWhere(_.id == itemId)
    val removed = carts(index).products.remove(itemIndex)
    Ok("Item deleted!")
  }

}
