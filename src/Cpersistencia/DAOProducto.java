
package Cpersistencia;

import Dentidades.DTOProducto;
import Dentidades.DTOUsuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asier
 */
public class DAOProducto {
    
    public Set<String> readCategorias(){
        Set<String> categorias = new HashSet<>();
        
        Set<DTOProducto> productos = readProductos();
        
        for(DTOProducto producto : productos){
            if(!categorias.contains(producto.getCategoria())){
                categorias.add(producto.getCategoria());
            }
        }
        
        return categorias;
    }
    
    public Set<DTOProducto> readProductos(){
        Set<DTOProducto> productos = new HashSet<>();
        DTOProducto producto = null;
        String registro;
        
        File fichero = new File("productos.csv");
        FileReader fr = null;
        BufferedReader br = null;
        
        try {
            fr = new FileReader(fichero);
            
            br = new BufferedReader(fr);
            
            try {
                while((registro = br.readLine()) != null){
                    
                    StringTokenizer st = new StringTokenizer(registro, ";");
                    producto = new DTOProducto(st.nextToken(), st.nextToken(),Double.parseDouble(st.nextToken()));
                    
                    productos.add(producto);
                }
            } catch (IOException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return productos;
    }
    
    public Set<DTOProducto> readProductosByCategoria(String categoria){
        Set<DTOProducto> productos = new HashSet<>();
        DTOProducto producto = null;
        String registro;
        
        File fichero = new File("productos.csv");
        FileReader fr = null;
        BufferedReader br = null;
        
        try {
            fr = new FileReader(fichero);
            
            br = new BufferedReader(fr);
            
            try {
                while((registro = br.readLine()) != null){
                    
                    StringTokenizer st = new StringTokenizer(registro, ";");
                    producto = new DTOProducto(st.nextToken(), st.nextToken(),Double.parseDouble(st.nextToken()));
                    
                    if(producto.getCategoria().equals(categoria)){
                    productos.add(producto);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return productos;
    }
    
    public boolean writeProducto(DTOProducto producto){

        FileWriter fw;
        PrintWriter pw;
        boolean anadido = true;
        try {
            fw = new FileWriter("productos.csv",true);
            
            pw = new PrintWriter(fw);
            
            pw.println(producto.getRegistroFichero());
                
            pw.close();
                
            fw.close();
        } catch (IOException ex) {
            anadido = false;
        }
        
        return anadido;
    
    }
    
    public boolean existeProducto(String producto){
        boolean existe = false;
        
        Set<DTOProducto> productos = new HashSet<>();
        productos = readProductos();
        
        for(DTOProducto p : productos){
            if(p.getNombre().equals(producto)){
                existe = true;
            }
        }
        
        return existe;
    }
    
}
