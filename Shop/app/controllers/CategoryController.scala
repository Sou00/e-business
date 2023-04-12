package controllers

import controllers.global.categories
import play.api.libs.json.{Json, Writes}
import play.api.mvc._

import javax.inject._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class CategoryController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val categoryWrites = new Writes[Category] {
    def writes(category: Category) = Json.obj(
      "Id"  -> category.id,
      "Name" -> category.name
    )
  }
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def getAll = Action {
    val result = Json.toJson(categories)
    Ok(result)
  }
  def getSingle(id: Int) = Action {
    val result = Json.toJson(categories.filter(_.id == id))
    Ok(result)
  }
  def post(id: Int, name: String) = Action {
    val p = Category(id,name)
    categories.addOne(p)
    Ok("Added!")
  }
  def update(id: Int, name: String) = Action {
    val index = categories.indexWhere(_.id == id)
    val removed = categories.remove(index)
    val p = Category(id,name)
    categories.addOne(p)
    Ok("Updated!")
  }
  def delete(id: Int) = Action {
    val index = categories.indexWhere(_.id == id)
    val removed = categories.remove(index)
    Ok("Deleted!")
  }

}
