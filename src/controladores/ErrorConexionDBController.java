/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import basedatos.conexion.Conexion;
import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


/**
 * FXML Controller class
 *
 * @author Alonso
 */
public class ErrorConexionDBController implements Initializable {

    @FXML
    private TextArea txtErrorMensaje;
    @FXML
    private Button btnSalir, btnComunicarse;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        txtErrorMensaje.setText(Conexion.ERROR_CONEXION_MENSAJE);
        btnSalir.setOnAction(event -> salir());
        btnComunicarse.setOnAction(event -> {
            try {
                comunicarse();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ErrorConexionDBController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    //Metodo para salid de la ventana dialog
    private void salir(){
        Conexion.getDialog().close();
        //System.exit(0);
    }
    //Metodo para comunicarse con el desarrollador
    private void comunicarse() throws MalformedURLException{
        URL comunicarseA = new URL("https://www.facebook.com/alonsosebastian.flores");
        try {
            Desktop.getDesktop().browse(comunicarseA.toURI());
        } catch (IOException | URISyntaxException e) {
        }
    }
      
    
}
