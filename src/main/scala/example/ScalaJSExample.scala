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

    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, canvas.width, canvas.height)
    }

    def run = {
      clear()

      ctx.fillStyle = "white"
      ctx.fillRect(100, 100, 200, 200)

    }

    dom.setInterval(() => run, 16)
  }


  def resizeCanvas(canvas: html.Canvas): Unit = {
    canvas.width = window.innerWidth
    canvas.height = window.innerHeight
  }

}
