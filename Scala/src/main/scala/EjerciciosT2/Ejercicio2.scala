package EjerciciosT2

def compress(s: String): String =
  if (s.isEmpty) return ""

  s.foldLeft(new StringBuilder)((acc, char) => {
    if (acc.isEmpty || acc.last != char) {
      acc.append(char).append(1)
    } else {
      val lastCount = acc.last - '0'
      acc.deleteCharAt(acc.length - 1).append(lastCount + 1)
    }
    acc
  }).toString()
