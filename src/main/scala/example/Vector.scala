package example

case class Vect(val x: Double, val y: Double) {

  def normalize: Vect = {
    val m = this.magnitude
    new Vect(x / m, y / m)
  }

  def magnitude: Double =
    math.sqrt((x * x) + (y * y))

  def dot(that: Vect): Double =
    this.x * that.x + this.y * that.y

  def +(that: Vect): Vect =
    new Vect(that.x + x, that.y + y)

  def -(that: Vect): Vect =
    new Vect(x - that.x, y - that.y)

  def *(that: Vect): Vect =
    new Vect(x * that.x, y * that.y)

  def multiplyScalar(w: Double): Vect =
    new Vect(x * w, y * w)

  override def toString =
    s"Vect [$x, $y]"
}
