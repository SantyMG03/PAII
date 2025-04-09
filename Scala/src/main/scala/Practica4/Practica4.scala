import scala.util.Random

package object Practica4 {

  var turn = 0

  def log(msg: String): Unit =
    println(s"${Thread.currentThread().getName}: $msg")

  def thread(body: =>Unit): Thread = {
    val t = new Thread {
      override def run() = body
    }
    t.start()
    t
  }

  class miThread(t: Int, c: Char, id: Int) extends Thread {
    override def run(): Unit = {
      for (i <- 0 to t) {
        while (id != turn) Thread.sleep(0)
        for (j <- 0 to id) print(c)
        turn = (turn + 1) % 3
      }
    }
  }

  def periodico(t: Long)(b: =>Unit): Thread = {
    thread {
      while(true) {
        b
        Thread.sleep(t)
      }
    }
  }

  def parallel[A,B](a: =>A, b: =>B):(A,B) = {
    var resA: A = null.asInstanceOf[A]
    var resB: B = null.asInstanceOf[B]

    val t1 = new Thread(() => resA = a)
    val t2 = new Thread(() => resB = b)

    t1.start()
    t2.start()

    t1.join()
    t2.join()

    (resA, resB)
  }

  def todosTrue(l: List[Boolean]): Boolean = {
    l.foldRight(true)((e, acc) => acc && e)
  }

  def fibonacci(n: Int): (Int, Int) = {
    if n == 1 then (1, 0)
    else{
      var v = (0, 0)
      thread {
        v = fibonacci(n - 1)
      }.join()
      (v._1 + v._2, v._1)
    }
  }

  def mezclar(l1: List[Int], l2: List[Int]): List[Int] = {
    (l1, l2) match
      case (x :: xs, y :: ys) => if x < y then x :: mezclar(xs , l2) else y :: mezclar(l1, ys)
      case (Nil, l) => l
      case (l, Nil) => l
  }

  def ord(l: List[Int]): List[Int] = {
    if l.size <= 1 then l else {
      val (l1, l2) = l.splitAt(l.size / 2)
      val (l10, l20) = parallel(ord(l1), ord(l2))
      mezclar(l10, l20)
    }
  }

  @main
  def ejercicio1(): Unit = {
    val t1 = miThread(10, 'a', 0)
    val t2 = miThread(20, 'b', 1)
    val t3 = miThread(30, 'c', 2)

    t1.start()
    t2.start()
    t3.start()
  }

  @main
  def ejercicio2(): Unit = {
    periodico(1000){log("Soy la hebra1")}
    periodico(3000){log("Soy la hebra2")}
  }

  @main
  def ejercicio3(): Unit = {
    val l = List.fill(Random.nextInt(10))(Random.nextBoolean())
    log(s"$l")
    val splited = l.splitAt(l.size / 2)
    val (e1,e2) = parallel(todosTrue(splited._1), todosTrue(splited._2))
    log(s"{$e1 && $e2}")
  }

  @main
  def ejercicio4(): Unit = {
    val f = fibonacci(5)
    log(s"$f")
  }

  @main
  def ejercicio5(): Unit = {
    val l = List.fill(5)(Random.nextInt(10))
    log(s"$l")
    val sol = ord(l)
    log(s"$sol")
  }

}