package EjerciciosBasicos

def isLeapYear(year: Int): Boolean =
  (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

object LeapYearChecker:
  def main(args: Array[String]): Unit =
    val year = 2024
    if isLeapYear(year) then
      println(s"$year es un año bisiesto.")
    else
      println(s"$year no es un año bisiesto.")