package controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.plaf.OptionPaneUI;
import servidor.usuario.Consultar;
import servidor.usuario.Registrar;
import servidor.usuario.Usuario;
import util.FxDialogs;

public class RegistrarUsuariosController implements Initializable {

    public Stage stageDialog;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField tfNombre, tfUsuario;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private ComboBox cbPrivilegios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegistrar.setOnAction(event -> {
            try {
                acitonBtnRegistrar();
            } catch (IOException ex) {
                Logger.getLogger(RegistrarUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        cbPrivilegios.getItems().add("Administrador");
        cbPrivilegios.getItems().add("Usuario");
    }

    private void acitonBtnRegistrar() throws IOException {
        Consultar _consultar_usuario;

        _consultar_usuario = new Consultar();

        try {
            _consultar_usuario.consultarUsuarioPorUsuario(tfUsuario.getText());
            if (!_consultar_usuario.usuarioEntontrado()) {
                Registrar registrar = new Registrar();
                registrar.setNombres(tfNombre.getText());

                registrar.setUsuario(tfUsuario.getText());
                registrar.setPassword(pfPassword.getText());
                registrar.setPrivilegio(cbPrivilegios.getValue().toString());
                registrar.registrarUsuario();
                if (registrar.usuarioRegistrado()) {
                    FxDialogs.showInformation("", "Registro exitoso");
                    stageDialog.close();
                } else {
                    FxDialogs.showError("", "Registro fallido");
                }
            } else {
                FxDialogs.showError("", "Este nombre de usuario ya existe.\nPorfavor elija otro nombre de usuario");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegistrarUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            FxDialogs.showError("", "Error al intentar consultar el nombre de usuario");
        }

    }

}
