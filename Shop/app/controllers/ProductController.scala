package controllers
import play.api.libs.json._

import global.products
import play.api.mvc._

import javax.inject._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val productWrites = new Writes[Product] {
    def writes(product: Product) = Json.obj(
      "Id"  -> product.id,
      "Name" -> product.name,
      "Category" -> product.category
    )
  }
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def getAll = Action {
    val result = Json.toJson(products)
    Ok(result)
  }
  def getSingle(id: Int) = Action {
    val result = Json.toJson(products.filter(_.id == id))
    Ok(result)
  }
  def post(id: Int, name: String, category: String) = Action {
    val p = Product(id,name,category)
    products.addOne(p)
    Ok("Added!")
  }
  def update(id: Int, name: String) = Action {
    val index = products.indexWhere(_.id == id)
    val removed = products.remove(index)
    val p = Product(id,name, removed.category)
    products.addOne(p)
    Ok("Updated!")
  }
  def delete(id: Int) = Action {
    val index = products.indexWhere(_.id == id)
    val removed = products.remove(index)
    Ok("Deleted!")
  }

}
