package Apresentacion;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import Blogica.LUsuario;
import javax.swing.ImageIcon;

/**
 *
 * @author Asier Rodríguez Lamas
 */
public class PantallaInicio extends JFrame {

    // Paneles de las tres zonas de la interfaz
    JPanel jp1, jp2, jp3;

    // Labels de usuario y clave
    JLabel lblUsuario, lblClave;

    // Espacio donde escribir el usuario y su clave
    JTextField jtfUsuario, jtfClave;

    // Boton que permite iniciar sesión al usuario
    BotonIniciarSesion btnIniciarSesion;

    // Capa de lógica
    LUsuario logica = new LUsuario();

    public PantallaInicio() {
        super("Caja Compra Masiva");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLookAndFeel();
        
        ImageIcon icono = new ImageIcon("Imagenes/icono.png");
        setIconImage(icono.getImage());

        // Creamos un layout de 3 filas y 1 columna
        GridLayout gl = new GridLayout(3, 1);
        setLayout(gl);

        // Llamamos a los métodos donde establecemos cada fila de la interfaz
        fila1();
        fila2();
        fila3();

        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Método que crea la fila 1 de la interfaz, donde se establece el usuario
     */
    private void fila1() {
        jp1 = new JPanel();

        GridLayout gl = new GridLayout(2, 1);
        jp1.setLayout(gl);

        lblUsuario = new JLabel("Usuario:");
        jtfUsuario = new JTextField(20);

        jp1.add(lblUsuario);
        jp1.add(jtfUsuario);

        add(jp1);
    }

    /**
     * Método que crea la fila 2 de la interfaz, donde se establece la clave
     */
    private void fila2() {
        jp2 = new JPanel();

        GridLayout gl = new GridLayout(2, 1);
        jp2.setLayout(gl);

        lblClave = new JLabel("Contraseña:");
        jtfClave = new JTextField(20);

        jp2.add(lblClave);
        jp2.add(jtfClave);

        add(jp2);
    }

    /**
     * Método que crea la fila 3 de la intefaz, donde están los botones de
     * inicio de sesión y de registro
     */
    private void fila3() {
        jp3 = new JPanel();

        btnIniciarSesion = new BotonIniciarSesion(jtfUsuario, jtfClave, this);

        // btnInicioSesion = new JButton("Iniciar Sesión");
        // btnInicioSesion.addActionListener(this);
        jp3.add(btnIniciarSesion);

        add(jp3);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            /* Ignoramos el error. Si no tenemos instalado Nimbus
               se mostrará el Look & Feel por defecto
             */
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PantallaInicio p = new PantallaInicio();
    }
}
