package Practica3

// ------ Ejercicio 11 ------

@main
def ex11(): Unit = {
  val logs = List(
    "ERROR: Null pointer exception",
    "INFO: User logged in",
    "ERROR: Out of memory",
    "WARNING: Disk space low",
    "INFO: File uploaded",
    "ERROR: Database connection failed"
  )

  val count = logs.groupBy(_.split(":")(0)).view.mapValues(_.size).toMap
  val err = logs.filter(_.startsWith("ERROR"))

  println(count)
  println(err)
}

// ------ Ejercicio 12 ------

@main
def ex12(): Unit = {
  val sales = List(
    ("Laptop", 2, 1000.0),
    ("Mouse", 10, 15.0),
    ("Keyboard", 5, 50.0),
    ("Monitor", 3, 200.0),
    ("USB Drive", 20, 5.0)
  )

  val gain = sales.foldRight(0.0)((t, acc) => acc + (t._3 * t._2))
  println(gain)

  val report = sales.foldRight(List.empty[(String, Double)])((t, acc) => (t._1, (t._2 * t._3)) :: acc).
    sortBy(_._2).reverse
  println(report)
}

// ------ Ejercicio 13 ------

@main
def ex13(): Unit = {
  val sentences = Set(
    "Scala is a functional language",
    "The power of functional programming is great",
    "Functional programming is elegant"
  )
  val stopWords = Set("a", "the", "is", "of")

  val words = sentences.flatMap(_.toLowerCase.split("\\s+")).diff(stopWords).toSet
  println(words)
}

// ------ Ejercicio 14 ------

@main
def ex14(): Unit = {
  val words = List("scala", "is", "awesome", "scala", "functional", "scala", "is", "great")

  val freq = words.groupBy(_.toLowerCase).view.mapValues(_.size).toMap
  println(freq)
}

// ------ Ejercicio 14 ------

@main
def ex15(): Unit = {
  val warehouse1 = Map("laptop" -> 5, "mouse" -> 20, "keyboard" -> 10)
  val warehouse2 = Map("laptop" -> 3, "mouse" -> 15, "monitor" -> 8)

  // val res = (warehouse1.toSeq ++ warehouse2.toSeq).map((k,v) => k ->(v + warehouse1.getOrElse(k, 0)))
  val combined = (warehouse1 ++ warehouse2).groupBy(_._1).map{ case (k,v) => k -> v.values.sum}
  println(combined)
}