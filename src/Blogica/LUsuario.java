/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Blogica;

import Dentidades.DTOUsuario;
import java.awt.Component;
import java.util.Map;
import javax.swing.JOptionPane;
import Cpersistencia.DAOUsuario;

/**
 *
 * @author asier
 */
public class LUsuario {
    
    DAOUsuario persistencia;
    
    public LUsuario(){
        persistencia = new DAOUsuario();
    }
    
    public boolean usuarioExistente(DTOUsuario user){
        
        Map<String, String> users = persistencia.readAllUsers();
        
        boolean existeUsuario = false;
        
        String usuario = users.get(user.getNombre());
        
        if(usuario!=null){
            existeUsuario=true;
        }
        
        return existeUsuario;
        
    }
    
    public void anadirUsuario(DTOUsuario user, Component context){
        if(persistencia.writeUser(user)){
            JOptionPane.showMessageDialog(context, "Usuario añadido correctamente","Usuario añadido",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(context, "No se pudo añadir al usuario","Error al añadir un usuario",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean claveCorrecta(DTOUsuario user,Component context){
        
        boolean claveCorrecta=false;
        
        Map<String, String> users = persistencia.readAllUsers();
        claveCorrecta = users.get(user.getNombre()).equals(user.getClave());
        return claveCorrecta;
    }
    
}
