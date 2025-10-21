package Apresentacion;

import Blogica.LProducto;
import Blogica.LTicket;
import Blogica.LUsuario;
import Dentidades.DTOProducto;
import Dentidades.DTOTicket;
import Dentidades.DTOUsuario;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * 
 * @author Asier Rodríguez Lamas
 */
public class PantallaPrincipal extends JFrame implements ActionListener, ItemListener {

    // Capa de lógica
    LUsuario logicaUsuario = new LUsuario();
    LProducto logicaProducto = new LProducto();
    LTicket logicaTicket = new LTicket();
    // Variable para almacenar el nombre del cajero que esta usando la aplicación
    String cajero;
    // Objeto para almacenar el ticket
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
    JButton btnExportarTicket, btnMas, btnMenos, btnVaciarCarrito, btnPagar, btnRegistrarUsuario, btnCrearProducto, btnAyuda;
    BotonAnadirProducto botonPersonalizado;
    JToolBar barraHerramientas;
    JPopupMenu menuPopup;
    JPanel jp1, jp2, jp13, jp21, jp23, jpBarraHerramientas, jpInterfaz;

    /**
     * 
     * @param usuario nombre del cajero que esta utilizando la aplicación 
     */
    public PantallaPrincipal(String usuario) {
        super("Cajero: " + usuario);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLookAndFeel();
        
        // Establecemos el icono de la aplicación
        ImageIcon icono = new ImageIcon("Imagenes/icono.png");
        setIconImage(icono.getImage());

        // Asignamos el nombre del usuario y declaramos el ticket
        cajero = usuario;
        ticket = new DTOTicket(cajero);

        // Configuración principal de la interfaz
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        setBarraHerramientas();
        setBarraMenu();

        // Configuración del panel principal
        jpInterfaz = new JPanel();
        jpInterfaz.setLayout(new FlowLayout());
        jpInterfaz.setBackground(new Color(240, 240, 240));
        setPanel1();
        setPanel2();

        add(jpInterfaz, BorderLayout.CENTER);

        verAyuda();
        actualizarTicket();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setBarraHerramientas() {
        barraHerramientas = new JToolBar(SwingConstants.HORIZONTAL);
        barraHerramientas.setFloatable(false);
        barraHerramientas.setBackground(new Color(200, 220, 240));

        btnRegistrarUsuario = crearBotonConEstilo("Registrar Usuario", "Imagenes/agregarUser.png");
        btnCrearProducto = crearBotonConEstilo("Crear Producto", "Imagenes/agregarProducto.png");
        btnAyuda = crearBotonConEstilo("Ayuda", "Imagenes/ayuda.png");
        btnRegistrarUsuario.addActionListener(this);
        btnCrearProducto.addActionListener(this);
        btnAyuda.addActionListener(this);
        
        barraHerramientas.add(btnRegistrarUsuario);
        barraHerramientas.add(btnCrearProducto);
        barraHerramientas.add(btnAyuda);
        
        barraHerramientas.setVisible(false);

        add(barraHerramientas, BorderLayout.NORTH);
    }

    private void setBarraMenu() {
        barraMenu = new JMenuBar();

        // Menú Usuario
        menuUsuario = new JMenu("Usuario");
        menuUsuario.setMnemonic(KeyEvent.VK_U);
        miCerrarSesion = crearMenuItemConEstilo("Cerrar Sesión", null);
        miSalir = crearMenuItemConEstilo("Salir", null);
        miCerrarSesion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        miSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        miCerrarSesion.addActionListener(this);
        miSalir.addActionListener(this);

        menuUsuario.add(miCerrarSesion);
        menuUsuario.add(miSalir);
        barraMenu.add(menuUsuario);

        // Menú Formato
        menuFormato = new JMenu("Formato");
        menuFormato.setMnemonic(KeyEvent.VK_F);
        miColorFondoTicket = crearMenuItemConEstilo("Color Fondo Ticket", "Imagenes/colores.png");
        miColorTextoTicket = crearMenuItemConEstilo("Color Texto Ticket", "Imagenes/colores.png");
        miColorFondoTicket.addActionListener(this);
        miColorTextoTicket.addActionListener(this);

        rbmiModoClaro = new JRadioButtonMenuItem("Modo Claro", true);
        rbmiModoOscuro = new JRadioButtonMenuItem("Modo Oscuro");
        rbmiModoClaro.addItemListener(this);
        rbmiModoOscuro.addItemListener(this);
        
        grupoColor = new ButtonGroup();
        grupoColor.add(rbmiModoClaro);
        grupoColor.add(rbmiModoOscuro);

        menuFormato.add(rbmiModoClaro);
        menuFormato.add(rbmiModoOscuro);
        menuFormato.addSeparator();
        menuFormato.add(miColorFondoTicket);
        menuFormato.add(miColorTextoTicket);

        barraMenu.add(menuFormato);

        // Menú Admin (opcional)
        if ("admin".equals(cajero)) {
            menuAdmin = new JMenu("Permisos");
            menuAdmin.setMnemonic(KeyEvent.VK_P);
            cbmiPermisosAdmin = new JCheckBoxMenuItem("Activar Permisos Administrador");
            cbmiPermisosAdmin.addItemListener(this);
            menuAdmin.add(cbmiPermisosAdmin);
            barraMenu.add(menuAdmin);
        }

        // Menú Ayuda
        menuAyuda = new JMenu("Ayuda");
        menuAyuda.setMnemonic(KeyEvent.VK_A);
        miAcercaDe = crearMenuItemConEstilo("Acerca De", "Imagenes/acercade.png");
        miAyuda = crearMenuItemConEstilo("Ayuda", "Imagenes/ayuda.png");
        
        miAyuda.addActionListener(this);
        miAcercaDe.addActionListener(this);

        menuAyuda.add(miAcercaDe);
        menuAyuda.add(miAyuda);
        barraMenu.add(Box.createHorizontalGlue());
        barraMenu.add(menuAyuda);

        setJMenuBar(barraMenu);
    }

    private void setPanel1() {
        jp1 = new JPanel();
        jp1.setLayout(new BorderLayout());
        jp1.setBackground(new Color(255, 255, 255));
        jp1.setBorder(BorderFactory.createTitledBorder("Productos"));

        // Categorías
        categorias = new JComboBox(logicaProducto.getFiltroCategoriasProductos());
        categorias.addItemListener(this);
        jp1.add(categorias, BorderLayout.NORTH);

        // Lista de productos
        listModel = new DefaultListModel<>();
        listModel.addAll(logicaProducto.getProductos());
        productos = new JList<>(listModel);
        productos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollProductos = new JScrollPane(productos);
        jp1.add(scrollProductos, BorderLayout.CENTER);

        // Controles de cantidad
        jp13 = new JPanel(new GridLayout(1, 5, 5, 5));
        cbCantidad = new JCheckBox("+1");
        cbCantidad.addItemListener(this);

        botonPersonalizado = new BotonAnadirProducto();
        botonPersonalizado.addActionListener(this);

        jtfCantidad = new JTextField("1", 4);
        jtfCantidad.setEditable(false);

        btnMas = new JButton(new ImageIcon("Imagenes/mas.png"));
        btnMenos = new JButton(new ImageIcon("Imagenes/menos.png"));
        btnMas.addActionListener(this);
        btnMenos.addActionListener(this);
        btnMas.setEnabled(false);
        btnMenos.setEnabled(false);

        jp13.add(cbCantidad);
        jp13.add(botonPersonalizado);
        jp13.add(jtfCantidad);
        jp13.add(btnMas);
        jp13.add(btnMenos);

        jp1.add(jp13,BorderLayout.SOUTH);

        jpInterfaz.add(jp1);
    }

    private void setPanel2() {
        jp2 = new JPanel();
        jp2.setLayout(new BorderLayout());
        jp2.setBackground(new Color(250, 250, 250));
        jp2.setBorder(BorderFactory.createTitledBorder("Ticket"));

        // Opciones de pago
        jp21 = new JPanel(new GridLayout(1, 2, 5, 5));
        rbEfectivo = new JRadioButton("Efectivo");
        rbTarjeta = new JRadioButton("Tarjeta");
        rbEfectivo.addItemListener(this);
        rbTarjeta.addItemListener(this);

        grupoPago = new ButtonGroup();
        grupoPago.add(rbEfectivo);
        grupoPago.add(rbTarjeta);

        jp21.add(rbEfectivo);
        jp21.add(rbTarjeta);
        jp2.add(jp21,BorderLayout.NORTH);

        // Vista del ticket
        jtaTicket = new JTextArea(20,10);
        scrollTicket = new JScrollPane(jtaTicket);
        jp2.add(scrollTicket,BorderLayout.CENTER);

        // Botones inferiores
        jp23 = new JPanel(new GridLayout(1, 3, 5, 5));
        btnExportarTicket = new JButton("Exportar Ticket");
        btnVaciarCarrito = new JButton("Vaciar Carrito");
        btnPagar = new JButton("Pagar");
        btnExportarTicket.addActionListener(this);
        btnVaciarCarrito.addActionListener(this);
        btnPagar.addActionListener(this);

        jp23.add(btnExportarTicket);
        jp23.add(btnVaciarCarrito);
        jp23.add(btnPagar);
        jp2.add(jp23, BorderLayout.SOUTH);

        jpInterfaz.add(jp2);
    }

    private JMenuItem crearMenuItemConEstilo(String texto, String iconPath) {
        JMenuItem item = new JMenuItem(texto);
        if (iconPath != null) {
            item.setIcon(new ImageIcon(iconPath));
        }
        return item;
    }

    private JButton crearBotonConEstilo(String texto, String iconPath) {
        JButton boton = new JButton(texto);
        if (iconPath != null) {
            boton.setIcon(new ImageIcon(iconPath));
        }
        boton.setFocusPainted(false);
        boton.setBackground(new Color(230, 230, 250));
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        return boton;
    }

    private void verAyuda() {
        File fichero = new File("help/help_set.hs");
        try {
            URL hsURL = fichero.toURI().toURL();
            
            HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
            HelpBroker hb = helpset.createHelpBroker();
            
            hb.enableHelpOnButton(miAyuda, "inicio", helpset);
            hb.enableHelpOnButton(btnAyuda, "inicio", helpset);
        } catch (MalformedURLException ex) {
            
        } catch (HelpSetException ex) {
            
        }
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Ignorar errores
        }
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
            try{
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
            }catch(NullPointerException npe){
                
            }

        } else if (ae.getSource() == miAcercaDe) {
            JOptionPane.showMessageDialog(this, "Esta aplicación ha sido creada para aprobar la asignatura de DESIN", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
        } else if (ae.getSource() == miAyuda) {
            verAyuda();
        }else if(ae.getSource() == btnAyuda){
             verAyuda();
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
                ticket.setFormaPago("Efectivo");
                actualizarTicket();
            }
        } else if (ie.getSource() == rbTarjeta) {
            if (rbTarjeta.isSelected()) {
                ticket.setFormaPago("Tarjeta");
                actualizarTicket();
            }
        } else if (ie.getSource() == rbmiModoClaro) {

            if (rbmiModoClaro.isSelected()) {
                jpInterfaz.setBackground(Color.WHITE);
            }
        } else if (ie.getSource() == rbmiModoOscuro) {
            if (rbmiModoOscuro.isSelected()) {
                jpInterfaz.setBackground(Color.BLACK);
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
}


