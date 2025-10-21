
package Cpersistencia;

import Dentidades.DTOUsuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asier
 */
public class DAOUsuario {

    public Map<String, String> readAllUsers() {

        Map<String, String> users = new HashMap<String, String>();
        String registro;

        File fichero = new File("usuarios.csv");
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(fichero);

            br = new BufferedReader(fr);

            try {
                while ((registro = br.readLine()) != null) {

                    StringTokenizer st = new StringTokenizer(registro, ";");

                    users.put(st.nextToken(), st.nextToken());
                }
            } catch (IOException ex) {
                Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);

        }
        return users;
    }
    
    public boolean writeUser(DTOUsuario user){
        FileWriter fw;
        PrintWriter pw;
        boolean anadido = true;
        try {
            fw = new FileWriter("usuarios.csv",true);
            
            pw = new PrintWriter(fw);
            
            pw.println(user.getRegistroCSV());
                
            pw.close();
                
            fw.close();
        } catch (IOException ex) {
            anadido = false;
        }
        
        return anadido;
    }
}
