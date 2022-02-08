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

public class UsuariosController implements Initializable {

    @FXML
    private Button btnRegistrar, btnModificar;

    private Stage stageUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegistrar.setOnAction(event -> {
            try {
                mostrarVentanaRegistrar();
            } catch (IOException ex) {
                Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnModificar.setOnAction(event -> {
            try {
                mostrarVentanaModificar();
            } catch (IOException ex) {
                Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void setStageUsuario(Stage stage) {
        this.stageUsuario = stage;
    }

    private void mostrarVentanaRegistrar() throws IOException {
        Stage dialogStage;
        Parent root;
        Scene scene;
        FXMLLoader loader;
        RegistrarUsuariosController controller;

        loader = new FXMLLoader(getClass().getResource("/ventanas/RegistrarUsuarios.fxml"));
        root = loader.load();
        scene = new Scene(root);
        dialogStage = new Stage();
        controller = loader.getController();

        controller.stageDialog = dialogStage;
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.getIcons().add(UtilStage.getIconStage());
        dialogStage.show();
        stageUsuario.close();
    }

    private void mostrarVentanaModificar() throws IOException {
        Stage dialogStage;
        Parent root;
        Scene scene;
        FXMLLoader loader;

        loader = new FXMLLoader(getClass().getResource("/ventanas/ListaUsuarios.fxml"));
        root = loader.load();
        scene = new Scene(root);
        dialogStage = new Stage();

        dialogStage.getIcons().add(UtilStage.getIconStage());
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.show();
        stageUsuario.close();
    }

}
