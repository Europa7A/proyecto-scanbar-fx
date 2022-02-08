package controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import servidor.usuario.Consultar;
import servidor.usuario.Usuario;
import util.BaseDatosUtilidades;
import util.DefaultApp;
import util.FxDialogs;

public class LoginAdminController implements Initializable {

    @FXML
    private Button btnConfirmar;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private ComboBox cbAdministrador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbAdministrador.getItems().addAll(getAdministradores());
        cbAdministrador.setValue(cbAdministrador.getItems().get(0));
        btnConfirmar.setOnAction(event -> {
            try {
                ingresar(cbAdministrador.getValue().toString(), pfPassword.getText(), contexto);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(LoginAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnConfirmar.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                try {
                    ingresar(cbAdministrador.getValue().toString(), pfPassword.getText(), contexto);
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(LoginAdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void ingresar(String user, String password, String _contexto) throws SQLException, IOException {

        if(DefaultApp.USER.equals(user)){
            if (DefaultApp.PASSWORD.equals(password)) {

                switch (_contexto) {
                    case "backup":
                        if (BaseDatosUtilidades.crearBackupBaseDeDatos()) {
                            FxDialogs.showInformation("", "Copia de seguridad realizada exitosamente.");
                            stageDialog.close();
                        } else {
                            FxDialogs.showError("", "Fallo en la copia de seguridad.");
                            stageDialog.close();
                        }

                        break;
                    case "restore":
                        if (BaseDatosUtilidades.restaurarBaseDeDatos()) {
                            FxDialogs.showInformation("", "Restauracion realizada exitosamente.");
                            stageDialog.close();
                        } else {
                            FxDialogs.showError("", "Fallo en la restauracion.");
                            stageDialog.close();
                        }

                        break;
                    default:
                        break;

                }

            } else {
                FxDialogs.showError("", "Contraseña incorrecta");
            }
        }
        

    }

    private Object[] getAdministradores() {

        Object[] _administradores;
        Consultar _consultar_usuarios;
        Usuario[] _usuarios;
        int _cant_admins;

        _cant_admins = 0;
        _consultar_usuarios = new Consultar();

        /*
        try {

            _usuarios = _consultar_usuarios.consultarTodosLosUsuarios();

            for (Usuario _usuario : _usuarios) {
                if (_usuario.getPrivilegio().equals("Administrador")) {
                    _cant_admins++;
                }
            }

            _administradores = new Object[_cant_admins];

            for (int i = 0; i < _usuarios.length; i++) {
                if (_usuarios[i].getPrivilegio().equals("Administrador")) {
                    _administradores[i] = _usuarios[i].getUsuario();
                }
            }

            return _administradores;
        } catch (IOException | SQLException ex) {
            Logger.getLogger(LoginAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
         */
        _administradores = new Object[1];
        _administradores[0] = "admin";
        return  _administradores;
    }

    public static String nombre_administrador, contexto;

    public Stage stageDialog;

}
