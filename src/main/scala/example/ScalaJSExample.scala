package example

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

    val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    val playerSize = 50
    val player = Vect(20, canvas.height / 2 - playerSize / 2)


    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, canvas.width, canvas.height)
    }

    def run = {
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
