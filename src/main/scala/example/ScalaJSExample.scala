package example

import scala.collection.immutable.NumericRange
import scala.collection.immutable.NumericRange.Inclusive
import scala.collection.mutable
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom._

import scala.util.Random

@JSExport
object ScalaJSExample {
  @JSExport
  def main(canvas: html.Canvas): Unit = {
    resizeCanvas(canvas)

    trait GameState {
      var alive = true
      val frameTime: Double = 16.0 / 1000.0
      val playerSize: Double = 50
      var player = Vect(100, canvas.height / 2 - playerSize / 2)
      val acceleration = Vect(0, 0.3)
      var speed = Vect(0, 0)
      val enemyEvery: Double = Math.min(canvas.width / 3, 600)
      var enemySpeed: Double = 5
      var frame: Long = 0
      var score: Long = 0
      var enemies: Seq[(Double, Double)] = (for (i <- 2 to 20) yield (i * enemyEvery, genEnemy(canvas.height))).toSeq
    }

    var gameState = new GameState {}

    window.onresize = (e: UIEvent) => {
      resizeCanvas(canvas)
      gameState = new GameState {}
    }

    var jump = false
    window.onkeydown = (ke: KeyboardEvent) => {
      if (ke.keyCode == ext.KeyCode.space) jump = true
    }
    window.onkeyup = (ke: KeyboardEvent) => {
      if (ke.keyCode == ext.KeyCode.space) jump = false
      if (ke.keyCode == ext.KeyCode.r) gameState = new GameState {}
    }
    window.onmousedown = (me:MouseEvent) => {
      jump = true
    }
    window.onmouseup = (me:MouseEvent) => {
      jump = false
    }

    val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, canvas.width, canvas.height)
    }

    def update(): Unit = {
      gameState.frame += 1

      gameState.speed = gameState.speed + gameState.acceleration
      gameState.player = gameState.player + gameState.speed

      if (jump) {
        gameState.speed = Vect(0, -7)
      }

      val maxX = gameState.enemies.map(_._1).max
      gameState.enemies = gameState.enemies.map {
        case (x, y) => if (x > -gameState.playerSize)
          (x - gameState.enemySpeed, y)
        else {
          gameState.score += 1
          gameState.enemySpeed = 5 * Math.pow(1.03, gameState.score)
          (maxX + gameState.enemyEvery, genEnemy(canvas.height))
        }
      }

      if (gameState.player.y + gameState.playerSize > canvas.height || gameState.player.y < 0) gameState.alive = false

      gameState.enemies.foreach {
        case (x, y) =>
          if (gameState.player.x > x && gameState.player.x < x + gameState.playerSize || gameState.player.x + gameState.playerSize > x && gameState.player.x + gameState.playerSize < x + gameState.playerSize) {
            if (gameState.player.y > y + gameState.playerSize * 2 || gameState.player.y < y - gameState.playerSize * 2)
              gameState.alive = false
          }
      }
    }


    def run = {
      if (gameState.alive)
        update()

      clear()

      ctx.fillStyle = if (gameState.alive) "white" else "gray"
      ctx.fillRect(gameState.player.x, gameState.player.y, gameState.playerSize, gameState.playerSize)

      ctx.fillStyle = "red"
      gameState.enemies.foreach {
        case (x, y) =>
          ctx.fillRect(x, y - 2 * gameState.playerSize, gameState.playerSize, -canvas.height)
          ctx.fillRect(x, y + 2 * gameState.playerSize, gameState.playerSize, canvas.height)
      }

      ctx.font = "20px Helvetica"
      ctx.fillStyle = "green"
      ctx.fillText("To jump press <space>, to restart <r> or reload page.", 10, canvas.height - 10)

      ctx.font = "40px Helvetica"
      ctx.fillStyle = "green"
      ctx.fillText(gameState.score.toString, 10, 40)


    }

    dom.setInterval(() => run, 16)
  }


  def resizeCanvas(canvas: html.Canvas): Unit = {
    canvas.width = window.innerWidth
    canvas.height = window.innerHeight
  }

  def genEnemy(maxY: Double): Double = maxY / 2 - Random.nextInt((maxY / 2 * 0.8).toInt)

}
