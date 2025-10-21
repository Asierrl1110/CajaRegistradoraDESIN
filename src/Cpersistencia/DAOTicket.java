/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cpersistencia;

import Dentidades.DTOTicket;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asier
 */
public class DAOTicket {
    
    public void writeTXT(DTOTicket ticket, String fichero){
        
        FileWriter fw = null;
        PrintWriter pw = null;
        
        try {
            fw = new FileWriter(fichero);
            
            pw = new PrintWriter(fw);
            
            pw.print(ticket.getTicket());
            
        } catch (IOException ex) {
            Logger.getLogger(DAOTicket.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                pw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(DAOTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void writeCSV(DTOTicket ticket, String fichero){
        FileWriter fw = null;
        PrintWriter pw = null;
        
        try {
            fw = new FileWriter(fichero);
            
            pw = new PrintWriter(fw);
            
            pw.print(ticket.getTicketCSV());
            
        } catch (IOException ex) {
            Logger.getLogger(DAOTicket.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                pw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(DAOTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
