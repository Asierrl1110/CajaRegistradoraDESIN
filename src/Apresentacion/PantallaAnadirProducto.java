
package Apresentacion;

import Blogica.LProducto;
import Dentidades.DTOProducto;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Asier Rodríguez Lamas
 */
public class PantallaAnadirProducto extends JFrame implements ActionListener, ItemListener {

    PantallaPrincipal p;
    
    LProducto logica = new LProducto();
    
    JLabel lblNombre, lblCategoria, lblPrecio;
    
    JTextField jtfNombre, jtfPrecio;
    
    JComboBox<String> jcbCategorias;
    
    JButton btnAnadirProducto;
    
    JPanel jp1, jp2, jp3;
    
    public PantallaAnadirProducto(PantallaPrincipal p){
        super("Nuevo producto");
        this.p = p;
        // Asignamos a este frame que al cerrarse que haga dispose (que se elimine sin cerrar la aplicacion)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLookAndFeel();
        ImageIcon icono = new ImageIcon("Imagenes/icono.png");
        setIconImage(icono.getImage());
        
        // Creamos un layout de 4 filas y 1 columna
        GridLayout gl = new GridLayout(4, 1);
        setLayout(gl);
        
        // Creamos los tres paneles en los que vamos a tener los elementos para
        // poner los datos del producto a añadir
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        
        jp1.setLayout(new GridLayout(2,1));
        jp2.setLayout(new GridLayout(2,1));
        jp3.setLayout(new GridLayout(2,1));
        
        lblNombre = new JLabel("Nombre:");
        jtfNombre = new JTextField(20);
        
        lblCategoria = new JLabel("Categoria: ");
        jcbCategorias = new JComboBox<>(logica.getEscogerCategoriaProducto());
        jcbCategorias.addItemListener(this);
        
        lblPrecio = new JLabel("Precio:");
        jtfPrecio = new JTextField(10);
        
        btnAnadirProducto = new JButton("Añadir producto");
        btnAnadirProducto.addActionListener(this);
        
        jp1.add(lblNombre);
        jp1.add(jtfNombre);
        
        jp2.add(lblCategoria);
        jp2.add(jcbCategorias);
        
        jp3.add(lblPrecio);
        jp3.add(jtfPrecio);
        
        // Añadimos los paneles al frame
        add(jp1);
        add(jp2);
        add(jp3);
        add(btnAnadirProducto);
        
        // Le asignamos un window listener con el método windowClosing
        // Al cerrarse la aplicacion, hace visible la pantalla principal
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                p.setVisible(true);
            }
        });
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==btnAnadirProducto){
            // Si no existe el producto intentamos añadir
            if(!logica.existeProducto(jtfNombre.getText())){
                try{
                    String nombre = jtfNombre.getText();
                    String categoria = jcbCategorias.getSelectedItem().toString();
                    double precio = Double.parseDouble(jtfPrecio.getText());
                    DTOProducto producto = new DTOProducto(nombre, categoria, precio);
                    
                    logica.anadirProducto(producto, this);
                    p.setVisible(true);
                    dispose();
                    
                }catch(NumberFormatException nfe){
                    // Si el precio que introducimos no es un número, salta la excepción y muestra el error
                    JOptionPane.showMessageDialog(this, "ERROR. EL precio debe ser un valor numérico", "Error numérico", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Ya existe el producto", "Producto existente", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie) {
        if(ie.getSource()==jcbCategorias){
            // Si el item que esta seleccionado en las categorias es otra, entonces permite editar el combobox
            if(jcbCategorias.getSelectedItem().equals("Otra")){
                jcbCategorias.setEditable(true);
            }else{
                jcbCategorias.setEditable(false);
            }
        }
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

}
