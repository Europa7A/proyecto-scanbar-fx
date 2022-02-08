package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.UtilStage;

public class AdminController implements Initializable {

    @FXML
    private Button btnProductos, btnUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnProductos.setOnAction(event -> {
            try {
                mostrarVentanaProductos();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnUsuarios.setOnAction(event -> {
            try {
                mostrarVentanaUsuarios();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void mostrarVentanaProductos() throws IOException {
        Stage dialogStage;
        Scene scene;
        FXMLLoader loader;
        Parent root;
        ProductosController controller;

        loader = new FXMLLoader(getClass().getResource("/ventanas/Productos.fxml"));
        root = loader.load();
        scene = new Scene(root);
        dialogStage = new Stage();
        controller = loader.getController();

        dialogStage.getIcons().add(UtilStage.getIconStage());
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.show();

        controller.setStageProducto(dialogStage);
    }

    private void mostrarVentanaUsuarios() throws IOException {
        Stage dialogStage;
        Scene scene;
        FXMLLoader loader;
        Parent root;
        UsuariosController controller;

        loader = new FXMLLoader(getClass().getResource("/ventanas/Usuarios.fxml"));
        root = loader.load();
        scene = new Scene(root);
        dialogStage = new Stage();
        controller = loader.getController();
        dialogStage.getIcons().add(UtilStage.getIconStage());
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.show();

        controller.setStageUsuario(dialogStage);
    }

}
