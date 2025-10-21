/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dentidades;

/**
 *
 * @author asier
 */
public class DTOProducto {
    
    private String nombre;
    
    private String categoria;
    
    private double precio;
    
    private int cantidad;

    public DTOProducto(String nombre, String categoria, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    public DTOProducto(String nombre, String categoria, double precio, int cantidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public DTOProducto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombre + " - " + categoria + " - " + precio;
    }
    
    public String getRegistroCSV(){
        return nombre + ";" + categoria + ";" + precio + ";" + cantidad;
    }
    
    public String getRegistroFichero(){
        return nombre + ";" + categoria + ";" + precio;
    }
    
    public String getTicket(){
        return nombre + "    " + cantidad + "x" + precio/cantidad + "     " + precio;
    }
    
}
