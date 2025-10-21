/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Blogica;

import Cpersistencia.DAOTicket;
import Dentidades.DTOTicket;

/**
 *
 * @author asier
 */
public class LTicket {
    
    DAOTicket persistencia = new DAOTicket();
    
    public void guardarTicket(DTOTicket ticket, String fichero, String forma){
        if(forma.equals("txt")){
            persistencia.writeTXT(ticket, fichero);
        }else if(forma.equals("csv")){
            persistencia.writeCSV(ticket, fichero);
        }
    }
    
}
