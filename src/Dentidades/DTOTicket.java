/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dentidades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author asier
 */
public class DTOTicket {

    private String cajero;

    private LocalDate fecha;

    private LocalTime hora;

    private HashSet<DTOProducto> productosCompra;

    private double subtotal = 0;

    private double iva = 0;

    private double total = 0;

    private String formaPago=null;

    private double pago = 0;

    private double cambio = 0;

    public DTOTicket(String cajero) {
        this.cajero = cajero;

        fecha = LocalDate.now();
        hora = LocalTime.now();

        productosCompra = new HashSet<DTOProducto>();
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getCajero() {
        return cajero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public HashSet<DTOProducto> getProductosCompra() {
        return productosCompra;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getIva() {
        return iva;
    }

    public double getTotal() {
        return total;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public double getPago() {
        return pago;
    }

    public double getCambio() {
        return cambio;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setPago(double pago) {
        this.pago = pago;
        cambio = pago-total;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }
    
    public void anadirProducto(DTOProducto producto, JFrame context){
        if(!existeProducto(producto)){
        productosCompra.add(producto);
        subtotal = formateaDosDecimales(subtotal + producto.getPrecio());
        iva = formateaDosDecimales(iva + producto.getPrecio()*0.21);
        total = subtotal + iva;
        }else{
            JOptionPane.showMessageDialog(context, "ERROR. Este producto ya ha sido añadido al carrito", "Producto ya existe",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean existeProducto(DTOProducto producto){
        for(DTOProducto p : productosCompra){
            if(p.getNombre().equals(producto.getNombre())){
                return true;
            }
        }
        return false;
    }
    
    public String getTicket(){
        String todosProductos="";
        
        for(DTOProducto producto : productosCompra){
            todosProductos = todosProductos + producto.getTicket() + "\n";
        }
        
        return """
        ****************************************************
                       SUPERMERCADO "Compra Masiva"
                     Tu compra compulsiva me paga la nómina
                         Tel: 123-456-789
        **************************************************** 
        """ +
        "Cajero: " + getCajero() + "\n" +
        "Fecha: " + getFecha() + "\n" +
        "Hora: " + getHora() + "\n" +
        """
        Productos comprados:
        ----------------------------------------------------
        """ +
        todosProductos +
        "----------------------------------------------------" + "\n" +
        "Subtotal: " + getSubtotal() + "\n" +
        "IVA (21%): " + getIva() + "\n" +
        "----------------------------------------------------" + "\n" +
        "Total: " + getTotal() + "\n\n\n" +
        "Forma de pago: " + getFormaPago() + "\n" +
        "Monto recibido: " + getPago() + "\n" +
        "Cambio: " + getCambio() + "\n\n" +
        """
        ****************************************************
        "¡Gracias por tu compra! Recuerda: no hay límite para llenar el carrito, 
        ni para vaciar tu billetera. ¡Vuelve pronto, que tu obsesión es nuestra misión!"
        ****************************************************
        """;
    }
    
    public String getTicketCSV(){
        String todosProductos="";
        
        for(DTOProducto producto : productosCompra){
            todosProductos = todosProductos + producto.getNombre() + ";" + producto.getCantidad() + ";" 
                    + producto.getPrecio()/producto.getCantidad() + ";" + producto.getPrecio() + "\n";
        }
        return getCajero() + ";" + getFecha() + ";" + getHora() + "\n" +
                todosProductos +
                getSubtotal() + ";" + getIva() + ";" + getTotal() + "\n" +
                getFormaPago() + ";" + getPago() + ";" + getCambio();
    }
    
    public void vaciarTicket(){
        productosCompra.clear();
        subtotal = 0;
        iva = 0;
        total = 0;
        pago = 0;
        cambio = 0;
    }
    
    private double formateaDosDecimales(double numeroSinFormatear){
        double formateado = Math.floor(numeroSinFormatear * 100) / 100;
        return formateado;
    }

}
