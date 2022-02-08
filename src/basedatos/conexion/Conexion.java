package basedatos.conexion;

import controladores.ErrorConexionDBController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Conexion {

    private Connection conexion;
    public static String ERROR_CONEXION_MENSAJE;
    private static Stage dialog;

    public Connection establecerConexion() throws IOException {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/bd_sb", "root", "");

        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "No se pudo establecer conexion con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "Establecer conexion", ex);
            ERROR_CONEXION_MENSAJE = ex.getMessage();
            mostrarDialogError();

        }

        return conexion;
    }
    
    public static Stage getDialog(){
        return dialog;
    }

    private void mostrarDialogError() throws IOException {
        
        dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("/ventanas/ErrorConexionDB.fxml"));
        Scene scene = new Scene(root);

        dialog.setScene(scene);
        dialog.showAndWait();

    }

}
