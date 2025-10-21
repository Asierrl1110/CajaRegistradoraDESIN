package Apresentacion;



import Dentidades.DTOProducto;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import Blogica.LProducto;
import Blogica.LTicket;
import Blogica.LUsuario;
import Dentidades.DTOTicket;
import Dentidades.DTOUsuario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.Box;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author asier
 */
public class PantallaPrincipal extends JFrame implements ActionListener, ItemListener {

    LUsuario logicaUsuario = new LUsuario();

    LProducto logicaProducto = new LProducto();

    LTicket logicaTicket = new LTicket();

    String cajero;

    DTOTicket ticket;

    JMenuBar barraMenu;

    JMenu menuUsuario, menuFormato, menuAdmin, menuAyuda;

    JCheckBoxMenuItem cbmiPermisosAdmin;

    JMenuItem miCerrarSesion, miSalir, miColorFondoTicket, miColorTextoTicket, miExportarCSV, miExportarTxt, miAcercaDe, miAyuda;

    JRadioButton rbEfectivo, rbTarjeta;

    JRadioButtonMenuItem rbmiModoClaro, rbmiModoOscuro;

    ButtonGroup grupoPago, grupoColor;

    JCheckBox cbCantidad;

    JComboBox categorias;

    JList<DTOProducto> productos;

    JScrollPane scrollProductos, scrollTicket;

    DefaultListModel<DTOProducto> listModel;

    JTextArea jtaTicket;

    JTextField jtfCantidad;

    JButton btnExportarTicket, btnMas, btnMenos, btnVaciarCarrito, btnPagar, btnRegistrarUsuario, btnCrearProducto;

    BotonAnadirProducto botonPersonalizado;

    JToolBar barraHerramientas;

    JPopupMenu menuPopup;

    JPanel jp1, jp2, jp13, jp21, jp23;

    JPanel jpBarraHerramientas, jpInterfaz;

    public PantallaPrincipal(String usuario) {
        super("Cajero: " + usuario);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLookAndFeel();
        cajero = usuario;
        ticket = new DTOTicket(cajero);
        
        ImageIcon icono = new ImageIcon("Imagenes/icono.png");
        setIconImage(icono.getImage());

        GridLayout gl = new GridLayout(1, 2);
        BorderLayout bl = new BorderLayout();
        
        setLayout(bl);

        // Creamos la barra de herramientas y el menú
        setBarraHerramientas();
        setBarraMenu();

        // Creamos el panel de la interfaz con el gridlayout de 1 fila y 2 columnas
        jpInterfaz = new JPanel();
        jpInterfaz.setLayout(gl);
        setPanel1();
        setPanel2();
        // Lo añadimos al centro del frame
        add(jpInterfaz, BorderLayout.CENTER);

        // Ponemos el background en gris
        jp1.setBackground(Color.GRAY);
        jp2.setBackground(Color.GRAY);
        jp13.setBackground(Color.GRAY);
        jp21.setBackground(Color.GRAY);
        jp23.setBackground(Color.GRAY);

        // Visualizamos la ayuda nada más abrir el frame para que ya este cargado
        verAyuda();
        pack();
        // Centramos el panel
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Método que crea la barra de herramientas
     */
    private void setBarraHerramientas() {
        ImageIcon imgUser = new ImageIcon("Imagenes/agregarUser.png");
        ImageIcon imgProducto = new ImageIcon("Imagenes/agregarProducto.png");

        barraHerramientas = new JToolBar(SwingConstants.HORIZONTAL);
        barraHerramientas.setVisible(false);

        btnRegistrarUsuario = new JButton(imgUser);
        btnCrearProducto = new JButton(imgProducto);
        btnRegistrarUsuario.addActionListener(this);
        btnCrearProducto.addActionListener(this);

        barraHerramientas.add(btnRegistrarUsuario);
        barraHerramientas.add(btnCrearProducto);

        add(barraHerramientas, BorderLayout.NORTH);
    }

    /**
     * Método que crea la barra del Menu
     */
    private void setBarraMenu() {
        barraMenu = new JMenuBar();

        setMenuUsuario();
        setMenuFormato();

        // En caso de que el usuario que haya iniciado sesión sea el admin, crea el menu admin
        if (cajero.equals("admin")) {
            setMenuAdmin();
        }

        // Ponemos un espacio en la barra de menu
        barraMenu.add(Box.createHorizontalGlue());

        // Creamos el menu de ayuda
        setMenuAyuda();

        // Asignamos la barra de menu
        setJMenuBar(barraMenu);
    }

    private void setMenuAyuda() {
        menuAyuda = new JMenu("Ayuda");
        menuAyuda.setMnemonic(KeyEvent.VK_A);

        ImageIcon imgAcercaDe = new ImageIcon("Imagenes/acercade.png");
        ImageIcon imgAyuda = new ImageIcon("Imagenes/ayuda.png");

        miAcercaDe = new JMenuItem("Acerca de", imgAcercaDe);
        miAyuda = new JMenuItem("Ayuda", imgAyuda);
        miAyuda.setAccelerator(KeyStroke.getKeyStroke("F1"));

        miAcercaDe.addActionListener(this);
        miAyuda.addActionListener(this);

        menuAyuda.add(miAcercaDe);
        menuAyuda.add(miAyuda);

        barraMenu.add(menuAyuda);
    }

    /**
     * Método que crea el menu del administrador
     */
    private void setMenuAdmin() {
        menuAdmin = new JMenu("Permisos");
        menuAdmin.setMnemonic(KeyEvent.VK_P);

        cbmiPermisosAdmin = new JCheckBoxMenuItem("Activar permisos administrador");
        cbmiPermisosAdmin.addItemListener(this);

        menuAdmin.add(cbmiPermisosAdmin);

        barraMenu.add(menuAdmin);
    }

    /**
     * Método que crea la opción del usuario en la barra de menú
     */
    private void setMenuUsuario() {
        menuUsuario = new JMenu("Usuario");
        miCerrarSesion = new JMenuItem("Cerrar Sesión");
        miSalir = new JMenuItem("Salir");

        miCerrarSesion.addActionListener(this);
        miSalir.addActionListener(this);

        menuUsuario.setMnemonic(KeyEvent.VK_U);
        miCerrarSesion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        miSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

        menuUsuario.add(miCerrarSesion);
        menuUsuario.add(miSalir);

        barraMenu.add(menuUsuario);
    }

    /**
     * Método que crea la opción del formato en la barra de menú
     */
    private void setMenuFormato() {
        menuFormato = new JMenu("Formato");
        ImageIcon imgColor = new ImageIcon("Imagenes/colores.png");
        menuFormato.setMnemonic(KeyEvent.VK_F);

        miColorFondoTicket = new JMenuItem("Color Fondo Ticket", imgColor);
        miColorTextoTicket = new JMenuItem("Color texto ticket", imgColor);
        grupoColor = new ButtonGroup();
        rbmiModoClaro = new JRadioButtonMenuItem("Modo Claro", true);
        rbmiModoOscuro = new JRadioButtonMenuItem("Modo Oscuro");

        grupoColor.add(rbmiModoClaro);
        grupoColor.add(rbmiModoOscuro);

        miColorFondoTicket.addActionListener(this);
        miColorTextoTicket.addActionListener(this);
        rbmiModoClaro.addItemListener(this);
        rbmiModoOscuro.addItemListener(this);

        menuFormato.add(rbmiModoClaro);
        menuFormato.add(rbmiModoOscuro);
        menuFormato.addSeparator();
        menuFormato.add(miColorFondoTicket);
        menuFormato.add(miColorTextoTicket);

        barraMenu.add(menuFormato);
    }

    /**
     * Método que crea el combo box con las categorias de los productos
     */
    private void setComboBox() {
        categorias = new JComboBox(logicaProducto.getFiltroCategoriasProductos());
        categorias.addItemListener(this);

        jp1.add(categorias);
    }

    /**
     * Método que crea la lista de productos distintos
     */
    private void setListaProductos() {
        scrollProductos = new JScrollPane();
        productos = new JList<>();
        listModel = new DefaultListModel<>();
        listModel.addAll(logicaProducto.getProductos());
        productos.setVisibleRowCount(4);

        productos.setModel(listModel);

        scrollProductos = new JScrollPane(productos);

        jp1.add(scrollProductos);
    }

    /**
     * Método que crea la tercera fila del panel 1
     */
    private void setFila3Panel1() {
        jp13 = new JPanel();
        GridLayout gl = new GridLayout(1, 5);
        jp13.setLayout(gl);

        ImageIcon imgMas = new ImageIcon("Imagenes/mas.png");
        ImageIcon imgMenos = new ImageIcon("Imagenes/menos.png");

        cbCantidad = new JCheckBox("+1");
        cbCantidad.addItemListener(this);
        botonPersonalizado = new BotonAnadirProducto();
        botonPersonalizado.addActionListener(this);
        jtfCantidad = new JTextField("1", 4);
        jtfCantidad.setEditable(false);
        btnMas = new JButton(imgMas);
        btnMenos = new JButton(imgMenos);
        btnMas.setEnabled(false);
        btnMenos.setEnabled(false);

        btnMas.addActionListener(this);
        btnMenos.addActionListener(this);

        jp13.add(cbCantidad);
        jp13.add(botonPersonalizado);
        jp13.add(jtfCantidad);
        jp13.add(btnMas);
        jp13.add(btnMenos);

        jp1.add(jp13);

    }

    /**
     * Método que crea el panel 1 del frame
     */
    private void setPanel1() {
        jp1 = new JPanel();
        jp1.setPreferredSize(new Dimension(400, 600));
        GridLayout gl = new GridLayout(3, 1);
        jp1.setLayout(gl);

        setComboBox();
        setListaProductos();
        setFila3Panel1();

        jpInterfaz.add(jp1);
    }

    /**
     * Método que crea el panel 2 del frame
     */
    private void setPanel2() {
        jp2 = new JPanel();
        jp2.setPreferredSize(new Dimension(400, 600));
        GridLayout gl = new GridLayout(3, 1);
        jp2.setLayout(gl);

        jp21 = new JPanel();
        jp21.setLayout(new GridLayout(1, 2));

        ImageIcon imgEfectivo = new ImageIcon("Imagenes/efectivo.png");
        ImageIcon imgTarjeta = new ImageIcon("Imagenes/tarjeta.png");

        grupoPago = new ButtonGroup();
        rbEfectivo = new JRadioButton("Efectivo", imgEfectivo);
        rbTarjeta = new JRadioButton("Tarjeta", imgTarjeta);
        rbEfectivo.addItemListener(this);
        rbTarjeta.addItemListener(this);

        rbEfectivo.setForeground(Color.RED);
        rbTarjeta.setForeground(Color.RED);

        grupoPago.add(rbEfectivo);
        grupoPago.add(rbTarjeta);

        jp21.add(rbEfectivo);
        jp21.add(rbTarjeta);

        jp2.add(jp21);

        jtaTicket = new JTextArea(20, 10);

        scrollTicket = new JScrollPane(jtaTicket);
        jp2.add(scrollTicket);

        actualizarTicket();

        jp23 = new JPanel();
        jp23.setLayout(new GridLayout(1, 3));

        btnExportarTicket = new JButton("Exportar ticket");
        btnVaciarCarrito = new JButton("Vaciar productos");
        btnPagar = new JButton("Pagar");
        btnExportarTicket.addActionListener(this);
        btnVaciarCarrito.addActionListener(this);
        btnPagar.addActionListener(this);

        jp23.add(btnExportarTicket);
        jp23.add(btnVaciarCarrito);
        jp23.add(btnPagar);

        jp2.add(jp23);

        jpInterfaz.add(jp2);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        // Comprobamos el objeto que ha provocado el evento
        
        if (ae.getSource() == miCerrarSesion) {
            // Cerramos la pantalla y mostramos la pantalla de inicio
            PantallaInicio pantallaInicio = new PantallaInicio();
            dispose();
        } else if (ae.getSource() == miSalir) {
            // Cerramos la pantalla y se cierra la aplicación
            dispose();
        } else if (ae.getSource() == btnExportarTicket) {
            // Creamos el menupopup y lo mostramos en el boton de exportar ticket
            setMenuPopup();
            menuPopup.show(btnExportarTicket, btnExportarTicket.getWidth() / 2, btnExportarTicket.getHeight());
        } else if (ae.getSource() == miExportarTxt) {
            // Abre un file chooser para escoger donde guardar el ticket en formato txt
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.txt", "txt");
            fileChooser.setFileFilter(filtro);

            // Guardamos el resultado de mostrar el filechooser
            int opcion = fileChooser.showSaveDialog(this);

            // Si se ha pulsado afirmativo, entonces lo guarda
            // En caso de que el fichero ya exista, entonces lo guarda normal
            // Sino entonces le agrega la extensión txt
            if (opcion == JFileChooser.APPROVE_OPTION) {
                if(!fileChooser.getSelectedFile().exists()){
                    logicaTicket.guardarTicket(ticket, fileChooser.getSelectedFile().toString() + ".txt", "txt");
                }else{
                    logicaTicket.guardarTicket(ticket, fileChooser.getSelectedFile().toString(), "txt");
                }
           }
        } else if (ae.getSource() == miExportarCSV) {
            // Abre un file chooser para escoger donde guardar el ticket en formato csv
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.csv", "csv");
            fileChooser.setFileFilter(filtro);

            // Guardamos el resultado de mostrar el filechooser
            int opcion = fileChooser.showSaveDialog(this);

            // Si se ha pulsado afirmativo, entonces lo guarda
            // En caso de que el fichero ya exista, entonces lo guarda normal
            // Sino entonces le agrega la extensión txt
            if (opcion == JFileChooser.APPROVE_OPTION) {
                if(!fileChooser.getSelectedFile().exists()){
                    logicaTicket.guardarTicket(ticket, fileChooser.getSelectedFile().toString() + ".csv", "csv");
                }else{
                    logicaTicket.guardarTicket(ticket, fileChooser.getSelectedFile().toString(), "csv");
                }
            }
        } else if (ae.getSource() == btnMas) {
            // Incrementamos el valor del jtextfield de la cantidad de productos
            int cantidad = Integer.parseInt(jtfCantidad.getText());
            cantidad++;
            jtfCantidad.setText(String.valueOf(cantidad));
        } else if (ae.getSource() == btnMenos) {
            // Decrementamos el valor del jtextfield de la cantidad de productos
            int cantidad = Integer.parseInt(jtfCantidad.getText());
            // Si la cantidad es 1, entonces muestra un error de que no puede ser inferior a 1
            if (cantidad > 1) {
                cantidad--;
                jtfCantidad.setText(String.valueOf(cantidad));
            } else {
                JOptionPane.showMessageDialog(this, "ERROR, la cantidad no puede ser inferior a 1", "No 0", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == botonPersonalizado) {
            // Almacenamos en la variable el producto escogido de la JList
            DTOProducto productoEscogido = productos.getSelectedValue();

            // Le asignamos la cantidad y el precio
            productoEscogido.setCantidad(Integer.parseInt(jtfCantidad.getText()));
            productoEscogido.setPrecio(productoEscogido.getCantidad() * productoEscogido.getPrecio());

            // Añadimos el producto al ticket
            ticket.anadirProducto(productoEscogido, this);

            // Actualizamos la vista del ticket
            actualizarTicket();
        } else if (ae.getSource() == btnVaciarCarrito) {
            // Vaciamos el ticket y actualizamos la vista
            ticket.vaciarTicket();
            actualizarTicket();
        } else if (ae.getSource() == btnPagar) {
            // Comprobamos el método de pago seleccionado, si no hay ninguno muestra un error
            if (rbEfectivo.isSelected() || rbTarjeta.isSelected()) {
                // Si es efectivo mostramos un input dialog con la cantidad a pagar
                if (rbEfectivo.isSelected()) {
                    try {
                        double cantidad = Double.parseDouble(JOptionPane.showInputDialog(this, "Selecciona la cantidad a pagar", ticket.getTotal()));

                        // Si la cantidad es igual o superior lo agregamos, sino muestra un error
                        if (cantidad >= ticket.getTotal()) {
                            ticket.setPago(cantidad);
                            actualizarTicket();
                        } else {
                            JOptionPane.showMessageDialog(this, "Falta dinero para llegar al necesario.", "Falta dinero", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "ERROR. Hay que agregar un número", "No número", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    ticket.setPago(ticket.getTotal());
                    actualizarTicket();
                }
            } else {
                    JOptionPane.showMessageDialog(this, "Agrega un método de pago", "Agrega pago", JOptionPane.WARNING_MESSAGE);
            }
        } else if (ae.getSource() == miColorFondoTicket) {
            // Mostramos un color chooser para escoger el color
            Color color = JColorChooser.showDialog(this, "Color de fondo del ticket", Color.GREEN);

            // Si el color escogido es distinto de nulo, cambia el color
            if (color != null) {
                jtaTicket.setBackground(color);
            }
        } else if (ae.getSource() == miColorTextoTicket) {
            Color color = JColorChooser.showDialog(this, "Color del texto del ticket", Color.GREEN);

            if (color != null) {
                jtaTicket.setForeground(color);
            }
        } else if (ae.getSource() == btnCrearProducto) {
            PantallaAnadirProducto p = new PantallaAnadirProducto(this);
            setVisible(false);

        } else if (ae.getSource() == btnRegistrarUsuario) {

            String usuario = JOptionPane.showInputDialog(this, "Introduce el nombre de usuario", "Introduce usuario", JOptionPane.OK_CANCEL_OPTION);
            String clave = null;
            if (!logicaUsuario.usuarioExistente(new DTOUsuario(usuario, clave)) && !usuario.isEmpty() && !usuario.isBlank()) {
                clave = JOptionPane.showInputDialog(this, "Introduce la contraseña del usuario", "Introduce contraseña", JOptionPane.OK_CANCEL_OPTION);
            } else {
                if (usuario.isEmpty() || usuario.isBlank()) {
                    JOptionPane.showMessageDialog(this, "El usuario no puede ser nulo", "Usuario nulo", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "El usuario ya existe", "Usuario existente", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (!clave.isEmpty() && !clave.isBlank()) {
                logicaUsuario.anadirUsuario(new DTOUsuario(usuario, clave), this);
            } else {
                JOptionPane.showMessageDialog(this, "ERROR. El usuario debe tener contraseña", "Contraseña nula", JOptionPane.ERROR_MESSAGE);
            }

        } else if (ae.getSource() == miAcercaDe) {
            JOptionPane.showMessageDialog(this, "Esta aplicación ha sido creada para aprobar la asignatura de DESIN", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
        } else if (ae.getSource() == miAyuda) {
            verAyuda();
        }

    }

    private void verAyuda() {
        File fichero = new File("help/help_set.hs");
        try {
            URL hsURL = fichero.toURI().toURL();
            
            HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
            HelpBroker hb = helpset.createHelpBroker();
            
            hb.enableHelpOnButton(miAyuda, "inicio", helpset);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HelpSetException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        if (ie.getSource() == categorias) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                listModel.clear();
                if (!categorias.getSelectedItem().toString().equals("Todas las categorias")) {
                    listModel.addAll(logicaProducto.getProductosbyCategoria(categorias.getSelectedItem().toString()));
                } else {
                    listModel.addAll(logicaProducto.getProductos());
                }
            }
        } else if (ie.getSource() == cbCantidad) {
            if (cbCantidad.isSelected()) {
                btnMas.setEnabled(true);
                btnMenos.setEnabled(true);
            } else {
                btnMas.setEnabled(false);
                btnMenos.setEnabled(false);
                jtfCantidad.setText("1");
            }
        } else if (ie.getSource() == rbEfectivo) {
            if (rbEfectivo.isSelected()) {
                rbEfectivo.setForeground(Color.GREEN);
                ticket.setFormaPago("Efectivo");
                actualizarTicket();
            } else {
                rbEfectivo.setForeground(Color.RED);
            }
        } else if (ie.getSource() == rbTarjeta) {
            if (rbTarjeta.isSelected()) {
                rbTarjeta.setForeground(Color.GREEN);
                ticket.setFormaPago("Tarjeta");
                actualizarTicket();
            } else {
                rbTarjeta.setForeground(Color.RED);
            }
        } else if (ie.getSource() == rbmiModoClaro) {

            if (rbmiModoClaro.isSelected()) {
                jp1.setBackground(Color.GRAY);
                jp2.setBackground(Color.GRAY);
                jp13.setBackground(Color.GRAY);
                jp21.setBackground(Color.GRAY);
                jp23.setBackground(Color.GRAY);
            }
        } else if (ie.getSource() == rbmiModoOscuro) {
            if (rbmiModoOscuro.isSelected()) {
                jp1.setBackground(Color.BLACK);
                jp2.setBackground(Color.BLACK);
                jp13.setBackground(Color.BLACK);
                jp21.setBackground(Color.BLACK);
                jp23.setBackground(Color.BLACK);
            }
        } else if (ie.getSource() == cbmiPermisosAdmin) {
            barraHerramientas.setVisible(cbmiPermisosAdmin.isSelected());
        }
    }

    private void setMenuPopup() {
        menuPopup = new JPopupMenu();

        miExportarTxt = new JMenuItem("Formato TXT");
        miExportarCSV = new JMenuItem("Formato CSV");

        miExportarTxt.addActionListener(this);
        miExportarCSV.addActionListener(this);

        menuPopup.add(miExportarTxt);
        menuPopup.add(miExportarCSV);
    }

    private void actualizarTicket() {
        jtaTicket.setText(ticket.getTicket());
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
