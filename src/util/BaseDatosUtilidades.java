package util;

import basedatos.conexion.Conexion;
import java.awt.Dialog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class BaseDatosUtilidades {

    public static boolean crearBackupBaseDeDatos() {
        try {

            FileChooser _file_chooser;
            File _file, _file_backup_bat;
            FileWriter _file_writer;
            String _file_path, _path_mysqldump, _password, _user, _base_datos, _sentencia;

            _file_chooser = new FileChooser();
            _file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de base de datos", "*.sql"));

            _file = _file_chooser.showSaveDialog(null);

            final String _temp = System.getProperty("user.home");

            if (_file != null) {
                _file_path = _file.getPath();
                _path_mysqldump = "C:\\wamp64\\bin\\mysql\\mysql5.7.14\\bin\\mysqldump.exe";
                _user = "root";
                _password = "";
                _base_datos = "bd_sb";
                _sentencia = "\"" + _path_mysqldump + "\" --opt --password=" + _password + " --user=" + _user + " " + _base_datos + " > \"" + _file_path + "\"\n";

                _file_backup_bat = new File(_temp + "/Desktop/eliminar_esto.bat");
                _file_writer = new FileWriter(_file_backup_bat);

                _file_writer.write(_sentencia, 0, _sentencia.length());
                _file_writer.close();

                Runtime.getRuntime().exec(_temp + "/Desktop/eliminar_esto.bat");
                return true;
            }

        } catch (IOException e) {
            FxDialogs.showError("", "Error en la ejecucion para crear copia de seguridad\n"+e);
        }
        return false;
    }

    public static boolean restaurarBaseDeDatos() {
        FileChooser _file_chooser;
        File _file, _file_backup_bat;
        FileWriter _file_writer;
        String _file_path, _path_mysqldump, _password, _user, _base_datos, _sentencia;

        _file_backup_bat = null;

        try {
            PreparedStatement _pst;
            String _sql;
            Connection _cn;
            
            _sql = "DROP TABLE `caja`, `detalles_venta`, `productos`, `registros_productos`, `usuarios`";
            _cn = new Conexion().establecerConexion();
            _pst = _cn.prepareStatement(_sql);
            
            _pst.executeUpdate();
            
            
        } catch (IOException | SQLException e) {
            FxDialogs.showError("", "Error al eliminar tablas\n" + e);
        }
        try {

            _file_chooser = new FileChooser();
            _file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de base de datos", "*.sql"));

            _file = _file_chooser.showOpenDialog(null);
            final String _temp = System.getProperty("user.home");
            if (_file != null) {
                _file_path = _file.getPath();
                _path_mysqldump = "C:\\wamp64\\bin\\mysql\\mysql5.7.14\\bin\\mysql.exe";
                _user = "root";
                _password = "";
                _base_datos = "bd_sb";
                _sentencia = "\"" + _path_mysqldump + "\" --password=" + _password + " --user=" + _user + " " + _base_datos + " < \"" + _file_path + "\"\n";

                _file_backup_bat = new File(_temp + "/Desktop/eliminar_esto.bat");
                _file_writer = new FileWriter(_file_backup_bat);

                _file_writer.write(_sentencia, 0, _sentencia.length());
                _file_writer.close();

                Runtime.getRuntime().exec(_temp + "/Desktop/eliminar_esto.bat");

                return true;
            }

        } catch (IOException e) {
            FxDialogs.showError("", "Error durante la ejecucion para restaurar datos\n" + e);
        }
        return false;
    }
}
