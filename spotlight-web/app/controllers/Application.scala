package controllers

import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(chartIds = List("1")))
  }
}
