
package Blogica;

import Dentidades.DTOProducto;
import java.util.Set;
import Cpersistencia.DAOProducto;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author asier
 */
public class LProducto {
    
    DAOProducto persistencia = new DAOProducto();
    
    public String[] getFiltroCategoriasProductos(){

        Set<String> coleccion = persistencia.readCategorias();
        String[] categorias = new String[coleccion.size()+1];
        int contador = 1;
        
        categorias[0] = "Todas las categorias";
        for(String c : coleccion){
            categorias[contador] = c;
            contador++;
        }
        
        return categorias;
    }
    
    public String[] getEscogerCategoriaProducto(){
        Set<String> coleccion = persistencia.readCategorias();
        String[] categorias = new String[coleccion.size()+1];
        int contador = 0;
        
        
        for(String c : coleccion){
            categorias[contador] = c;
            contador++;
        }
        categorias[contador] = "Otra";
        
        return categorias;
    }

    
    public Set<DTOProducto> getProductos(){
        
        Set<DTOProducto> productos = persistencia.readProductos();
        
        return productos;
    }
    
    public Set<DTOProducto> getProductosbyCategoria(String categoria){
        
        Set<DTOProducto> productos = persistencia.readProductosByCategoria(categoria);
        
        return productos;
    }
    
    public boolean existeProducto(String producto){
        return persistencia.existeProducto(producto);
    }
    
    public void anadirProducto(DTOProducto producto, JFrame context){
        if(persistencia.writeProducto(producto)){
            JOptionPane.showMessageDialog(context, "Producto añadido correctamente", "Producto añadido", JOptionPane.OK_OPTION);
        }else{
            JOptionPane.showMessageDialog(context, "No se pudo añadir el producto", "Error al añadir el producto", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
