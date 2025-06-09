package parte_2025_4

import java.awt.{BorderLayout, Panel => AWTPanel}
import javax.swing.{JFrame, SwingUtilities, WindowConstants}

object Main {
  def main(args: Array[String]): Unit = {
    SwingUtilities.invokeLater(() => {
      val frame = new JFrame("Números primos")
      val panel = new Panel()
      val controller = new Controller(panel)

      panel.setStatusMessage("GUI creada")

      panel.setController(controller)

      frame.setContentPane(panel)
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
      frame.pack()
      frame.setLocationRelativeTo(null) // Centra la ventana
      frame.setVisible(true)
    })
  }
}
