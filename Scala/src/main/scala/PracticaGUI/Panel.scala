package PracticaGUI

import java.awt.*
import java.awt.event.*
import javax.swing.*
import scala.jdk.CollectionConverters.*

trait IPanel:
  // Commands
  val FIELD = "FIELD" // for action events coming from the text field 
  val CANCEL_BUTTON = "CANCEL" // for action events coming from the cancel button
  val COMBO = "COMBO" // for action events coming from the combo box

  def setController(controller: ActionListener): Unit // Controller hookup
  def getSelectedOption: String // Get the selected option from the combo box
  def setStatusMessage(msg: String): Unit // Set status messages
  def getNumber: Int // Read input value
  def writePrimes(primes: scala.List[PrimesPair]): Unit // Write results to text area
  def updateProgress(value: Int): Unit // Update progress bars
  def clearTextArea(): Unit // Clear text area

class Panel extends JPanel with IPanel:
  // Labels and fields
  private val tipoLabel = JLabel("Tipo de parejas de primos a generar")
  tipoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)) // Add padding
  private val primesLabel = JLabel("Número de números primos a generar")
  primesLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)) // Add padding
  private val primesField = JTextField(3)
  private val statusMessage = JLabel("GUI creada")
  statusMessage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)) // Add padding
  private val primesArea = JTextArea(10, 40)
  private val primesScroll = JScrollPane(primesArea)
  private val primesMsg = JLabel("Números primos generados")
  private val cancelButton = JButton("Cancel")
  private val progress = JProgressBar(0, 100)
  private val optionsComboBox = JComboBox(Array("Gemelos", "Primos", "Sexies"))

  init()

  private def init(): Unit =
    setLayout(BorderLayout())

    val west = JPanel()
    west.setLayout(GridLayout(5,1))
    west.add(tipoLabel)
    west.add(optionsComboBox)
    west.add(primesLabel)
    west.add(primesField)
    west.add(cancelButton)

    progress.setValue(0)
    progress.setStringPainted(true)
    add(progress, BorderLayout.NORTH)

    add(west, BorderLayout.WEST)
    add(primesScroll, BorderLayout.CENTER)

    add(statusMessage, BorderLayout.SOUTH)

  // Controller hookup
  def setController(controller: ActionListener): Unit =
    primesField.addActionListener(controller)
    primesField.setActionCommand(FIELD)
    optionsComboBox.addActionListener(controller)
    optionsComboBox.setActionCommand(COMBO)
    cancelButton.addActionListener(controller)
    cancelButton.setActionCommand(CANCEL_BUTTON)

  // Get the selected option from the combo box
  def getSelectedOption: String =
    optionsComboBox.getSelectedItem.toString

  // Read input values
  def getNumber: Int =
    try
      primesField.getText.toInt
    catch
      case _: NumberFormatException =>
        JOptionPane.showMessageDialog(this, "Número no válido. Por favor, inténtalo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE)
        0 // Return 0 or some default value if input is invalid
  
  // Write results to text area
  def writePrimes(primes: scala.List[PrimesPair]): Unit =
    for (p, i) <- primes.zipWithIndex do
      primesArea.append(s"${p.toString}   ")
      if ((i + 1) % 5 == 0) primesArea.append("\n")

  // Clear text area
  def clearTextArea(): Unit = primesArea.setText("")

  // Set status messages
  def setStatusMessage(msg: String): Unit = statusMessage.setText(msg)

  // Update progress bars
  def updateProgress(value: Int): Unit = progress.setValue(value)
