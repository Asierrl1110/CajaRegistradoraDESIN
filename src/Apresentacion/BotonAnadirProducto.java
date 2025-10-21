
package Apresentacion;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * @author Asier Rodríguez Lamas
 */
public class BotonAnadirProducto extends JComponent {
    private Color colorFondo = Color.LIGHT_GRAY;
    private Color colorHover = Color.LIGHT_GRAY;
    private Color colorPresionado = Color.LIGHT_GRAY;
    private boolean estaPresionado = false;

    private ActionListener actionListener;

    public BotonAnadirProducto() {
        setPreferredSize(new Dimension(50, 50)); // Tamaño del componente

        
        // Agregar MouseListener para manejar los clics y los eventos de hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                estaPresionado = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                estaPresionado = false;
                repaint();
                // Disparar la acción cuando el mouse se libera
                if (actionListener != null && contains(e.getPoint())) {
                    actionListener.actionPerformed(new ActionEvent(BotonAnadirProducto.this, ActionEvent.ACTION_PERFORMED, "click"));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                colorFondo = colorHover;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                colorFondo = Color.GRAY;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ajustar el objeto Graphics
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el fondo
        g2.setColor(estaPresionado ? colorPresionado : colorFondo);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Dibujar el símbolo "+"
        int margen = 10; // Margen para el símbolo "+"
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int longitud = Math.min(getWidth(), getHeight()) / 3;

        g2.setColor(Color.WHITE);
        g2.fillRect(centroX - longitud / 2, centroY - margen / 2, longitud, margen); // Línea horizontal
        g2.fillRect(centroX - margen / 2, centroY - longitud / 2, margen, longitud); // Línea vertical
    }

    // Método para agregar ActionListener
    public void addActionListener(ActionListener listener) {
        this.actionListener = listener;
    }

    // Método para eliminar ActionListener
    public void removeActionListener() {
        this.actionListener = null;
    }
}
