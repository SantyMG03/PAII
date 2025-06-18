package PracticaGUI

import java.awt.event.{ActionEvent, ActionListener}

class Controller(panel: IPanel) extends ActionListener {

  private var currentWorker: Option[Worker] = None

  override def actionPerformed(e: ActionEvent): Unit = {
    e.getActionCommand match {
      case panel.FIELD =>
        panel.clearTextArea()
        val n = panel.getNumber // Numero introducido
        if (n > 0) {
          val tipo = panel.getSelectedOption // Tipo seleccionado
          panel.setStatusMessage(s"Opción seleccionada: $tipo")
          val worker = new Worker(n, tipo, panel)
          currentWorker = Some(worker)
          worker.addPropertyChangeListener(evt =>
            if evt.getPropertyName == "progress" then
              panel.updateProgress(evt.getNewValue.asInstanceOf[Int])
          )
          worker.execute()
        }

      case panel.COMBO =>
        val tipo = panel.getSelectedOption
        panel.setStatusMessage(s"Opción seleccionada: $tipo")

      case panel.CANCEL_BUTTON =>
        currentWorker.foreach(_.cancel(true))
        panel.setStatusMessage("Tarea interrumpida")
    }
  }
}