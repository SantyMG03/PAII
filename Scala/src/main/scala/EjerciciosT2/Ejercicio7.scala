package EjerciciosT2

class Fraction(val n: Int, val d: Int) {
  require(d != 0, "El denominador no puede ser 0")

  private val gcd = BigInt(n).gcd(BigInt(d)).toInt
  val numerador: Int = n / gcd
  val denominador: Int = d / gcd

  def +(that: Fraction): Fraction =
    new Fraction(
      this.numerador * that.denominador + this.denominador * that.numerador,
      this.denominador * that.denominador
    )

  def -(that: Fraction): Fraction =
    new Fraction(
      this.numerador * that.denominador - this.denominador * that.numerador,
      this.denominador * that.denominador
    )

  def *(that: Fraction): Fraction =
    new Fraction(
      this.numerador * that.numerador,
      this.denominador * that.denominador
    )

  def /(that: Fraction): Fraction =
    new Fraction(
      this.numerador * that.denominador,
      this.denominador * that.numerador
    )

  override def toString: String = s"$numerador/$denominador"
}

@main
def testFraction():Unit =
  val f1 = new Fraction(2, 3)
  val f2 = new Fraction(4, 5)

  println(s"Suma: $f1 + $f2 = ${f1 + f2}") // 22/15
  println(s"Resta: $f1 - $f2 = ${f1 - f2}") // -2/15
  println(s"Multiplicación: $f1 * $f2 = ${f1 * f2}") // 8/15
  println(s"División: $f1 / $f2 = ${f1 / f2}") // 10/12 → 5/6 (simplificado)
