package EjerciciosT2

// Todo -> Probar a hacerlor con un foldLeft
def caracterMasFrecuente(s: String): Option[(Char, Int)] = {
  s.groupBy(identity)          // Paso 1: Agrupar caracteres en listas
    .view.mapValues(_.length)   // Paso 2: Contar la frecuencia de cada carácter
    .toMap                      // Paso 3: Convertir a un Map[Char, Int]
    .maxByOption(_._2)          // Paso 4: Obtener el carácter con la frecuencia máxima (Char -> Int)
}

@main
def test(): Unit =
  println(caracterMasFrecuente("Scaala"))
