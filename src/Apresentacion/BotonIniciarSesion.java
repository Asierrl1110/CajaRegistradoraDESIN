
package Apresentacion;

import Blogica.LUsuario;
import Dentidades.DTOUsuario;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Asier Rodríguez Lamas
 */
public class BotonIniciarSesion extends JButton implements ActionListener {

    JTextField jtfUsuario, jtfClave;
    
    JFrame context;

    LUsuario logica = new LUsuario();

    public BotonIniciarSesion(JTextField jtfUsuario, JTextField jtfClave, JFrame context) {
        super("Iniciar sesión");
        this.jtfUsuario = jtfUsuario;
        this.jtfClave = jtfClave;
        this.context = context;
        
        setPreferredSize(new Dimension(100,40));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        // Coleccion de par clave valor donde almacenamos el nombre y contraseña de los usuarios
        Map<String, String> users = new HashMap<String, String>();
        // Creamos un objeto usuario con los valores de los JTextField
        DTOUsuario user = new DTOUsuario(jtfUsuario.getText(), jtfClave.getText());

        if (!user.getNombre().isEmpty() && !user.getNombre().isBlank() && !user.getClave().isEmpty() && !user.getClave().isBlank()) {
            // Comprobamos si el método se activo por el boton de inicio de sesión o de registrarse

            // Comprobamos si el usuario existe
            if (logica.usuarioExistente(user)) {
                // Comprobamos si la contraseña que introdujo es correcta
                if (logica.claveCorrecta(user, this)) {
                    JOptionPane.showMessageDialog(this, "Inicio de sesión correctamente", "Inicio", JOptionPane.INFORMATION_MESSAGE);
                    PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(user.getNombre());
                    context.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Contraseña incorrecta", "Fallo al iniciar sesión", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no existente", "No existe", JOptionPane.ERROR_MESSAGE);

            }
            
        }
    }
}

