package example

class Vector(val x: Double, val y: Double) {

  def normalize: Vector = {
    val m = this.magnitude
    new Vector(x / m, y / m)
  }

  def magnitude: Double =
    math.sqrt((x * x) + (y * y))

  def dot(that: Vector): Double =
    this.x * that.x + this.y * that.y

  def +(that: Vector): Vector =
    new Vector(that.x + x, that.y + y)

  def -(that: Vector): Vector =
    new Vector(x - that.x, y - that.y)

  def *(that: Vector): Vector =
    new Vector(x * that.x, y * that.y)

  def multiplyScalar(w: Double): Vector =
    new Vector(x * w, y * w)

  override def toString =
    s"Vector [$x, $y]"
}
