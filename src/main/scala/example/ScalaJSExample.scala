package example

import scala.collection.mutable
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom._

@JSExport
object ScalaJSExample {
  @JSExport
  def main(canvas: html.Canvas): Unit = {
    resizeCanvas(canvas)
    window.onresize = (e: UIEvent) => {
      resizeCanvas(canvas)
    }

    val keys = mutable.Set[Int]()

    window.onkeydown = (ke:KeyboardEvent)=>{
      keys.add(ke.keyCode)
    }

    window.onkeyup = (ke:KeyboardEvent)=>{
      keys.remove(ke.keyCode)
    }

    val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]


    val frameTime:Double = 16.0/1000.0
    val playerSize = 50
    var player = Vect(100, 0)
    val acceleration = Vect(0,50 0)
    var speed = Vect(0,0)

    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, canvas.width, canvas.height)
    }

    def update(): Unit = {
      speed = speed + acceleration * frameTime
      player = player + speed * frameTime
      
    }


    def run = {
      update()
      clear()

      ctx.fillStyle = "white"
      ctx.fillRect(player.x, player.y, playerSize, playerSize)

    }

    dom.setInterval(() => run, 16)
  }


  def resizeCanvas(canvas: html.Canvas): Unit = {
    canvas.width = window.innerWidth
    canvas.height = window.innerHeight
  }

}
