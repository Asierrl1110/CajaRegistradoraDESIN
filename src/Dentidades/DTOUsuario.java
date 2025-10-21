
package Dentidades;

/**
 *
 * @author asier
 */
public class DTOUsuario {
    
    private String nombre;
    
    private String clave;

    public DTOUsuario(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String getRegistroCSV(){
        return nombre + ";" + clave;
    }
    
}
