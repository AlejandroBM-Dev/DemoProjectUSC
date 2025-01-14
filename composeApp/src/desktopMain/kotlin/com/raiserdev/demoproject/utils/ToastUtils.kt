package com.raiserdev.demoproject.utils

import java.awt.Color
import java.awt.Font
import java.awt.Toolkit
import java.awt.geom.RoundRectangle2D
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.Timer

actual fun showToast(message: String) {
    // Crear una ventana temporal sin bordes
    val toastWindow = JFrame()
    toastWindow.isUndecorated = true
    //toastWindow.type = JFrame.
    //toastWindow.type = JFrame.Type.UTILITY
    toastWindow.isAlwaysOnTop = true

    // Configurar el contenido del Toast
    val panel = JPanel()
    panel.background = Color(0, 0, 0, 170) // Fondo negro semitransparente
    panel.border = javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)

    val label = JLabel(message)
    label.foreground = Color.WHITE
    label.font = Font("Dialog", Font.PLAIN, 14)
    panel.add(label)

    toastWindow.add(panel)
    toastWindow.pack()

    // Ubicar el Toast en la parte inferior central de la pantalla
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val x = (screenSize.width - toastWindow.width) / 2
    val y = screenSize.height - toastWindow.height - 50
    toastWindow.setLocation(x, y)

    // Opcional: redondear bordes
    toastWindow.shape = RoundRectangle2D.Double(0.0, 0.0, toastWindow.width.toDouble(), toastWindow.height.toDouble(), 20.0, 20.0)

    toastWindow.isVisible = true

    // Cerrar el Toast después de un cierto tiempo (por ejemplo, 2 segundos)
    Timer(2000) {
        toastWindow.isVisible = false
        toastWindow.dispose()
    }.start()
}