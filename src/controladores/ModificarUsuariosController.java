package controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import servidor.usuario.Consultar;
import servidor.usuario.Modificar;
import util.FxDialogs;

public class ModificarUsuariosController implements Initializable {

    /**
     * @return the tfID
     */
    public TextField getTfID() {
        return tfID;
    }

    /**
     * @return the tfNombre
     */
    public TextField getTfNombre() {
        return tfNombre;
    }

    /**
     * @return the tfDirecccion
     */
    public TextField getTfDireccion() {
        return tfDireccion;
    }

    /**
     * @return the tfTelefono
     */
    public TextField getTfTelefono() {
        return tfTelefono;
    }

    /**
     * @return the tfFechaRegistro
     */
    public TextField getTfFechaRegistro() {
        return tfFechaRegistro;
    }

    /**
     * @return the cbPrivilegios
     */
    public ComboBox getCbPrivilegios() {
        return cbPrivilegios;
    }

    /**
     * @return the cbEstado
     */
    public ComboBox getCbEstado() {
        return cbEstado;
    }

    /**
     * @return the pfPassword
     */
    public TextField getTfPassword() {
        return tfPassword;
    }

    /**
     * @return the tfUsuario
     */
    public TextField getTfUsuario() {
        return tfUsuario;
    }

    @FXML
    private TextField tfID, tfNombre, tfDireccion, tfTelefono, tfFechaRegistro, tfUsuario, tfPassword;
    @FXML
    private ComboBox cbPrivilegios, cbEstado;

    @FXML
    private Button btnModificar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPrivilegios.getItems().add("Administrador");
        cbPrivilegios.getItems().add("Usuario");
        cbEstado.getItems().add("Activo");
        cbEstado.getItems().add("Inactivo");
        btnModificar.setOnAction(event -> actionBtnModificar());
    }

    private void actionBtnModificar() {

        Modificar modificar;

        Consultar _consultar_usuario;

        _consultar_usuario = new Consultar();

        try {
            _consultar_usuario.consultarUsuarioPorUsuario(tfUsuario.getText());
            if (!_consultar_usuario.usuarioEntontrado()) {
                modificar = new Modificar(tfID.getText());

                modificar.setNombres(tfNombre.getText());
                modificar.setUsuario(tfUsuario.getText());
                modificar.setPrivilegio(cbPrivilegios.getValue().toString());
                modificar.setPassword(tfPassword.getText());
                modificar.setDireccion(tfDireccion.getText());
                modificar.setEstado(cbEstado.getValue().toString());
                modificar.setTelefono(tfTelefono.getText());
                modificar.moficiarUsuario();

                if (modificar.usuarioModificado()) {
                    FxDialogs.showInformation("", "Modificacion exitosa");
                } else {
                    FxDialogs.showError("", "Modificacion fallida");
                }
            } else {
                FxDialogs.showError("", "Este nombre de usuario ya existe.\nPorfavor elija otro nombre de usuario");
            }
        } catch (IOException | SQLException e) {

        }

    }

}
