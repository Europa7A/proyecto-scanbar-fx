package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import servidor.dventas.Registrar;
import servidor.producto.Consultar;
import servidor.producto.Modificar;
import util.FxDialogs;
import util.Producto;

public class ConfirmarController implements Initializable {

    private Stage stageDialog;

    @FXML
    private Button btnConfirmar;
    @FXML
    private TextField tfCobrar, tfRecibido, tfEntregar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfCobrar.setText(String.valueOf(PrincipalController.getController().obtenerMontoTotalProductos()));

        btnConfirmar.setOnAction(event -> actionBtnConfirmar(PrincipalController.getController().getTableProductos(), PrincipalController.getController().getTableDetalles()));
        tfRecibido.setOnKeyTyped(event -> {

            if (!event.getCharacter().equals(".")) {
                if (!Character.isDigit(event.getCharacter().charAt(0))) {
                    event.consume();
                } 
            }
            

        });
        tfRecibido.setOnKeyReleased(event -> {
            try {
                if (!tfRecibido.getText().isEmpty()) {
                    final double _recibido = Double.parseDouble(tfRecibido.getText());
                    final double _cobrar = Double.parseDouble(tfCobrar.getText());
                    final double _entregar = _recibido - _cobrar;
                    tfEntregar.setText(String.valueOf(_entregar));
                } else {
                    tfEntregar.setText("");
                }

            } catch (NumberFormatException e) {
                
                FxDialogs.showError("", "Error al intentar calcular el cambio\n" + e);
            }
        });
    }

    private void actionBtnConfirmar(TableView<Producto> tableProductos, TableView<Producto> tableDetalles) {

        ObservableList<Producto> _list_registrados, _list_no_registrados, _datos_registrados_table_detalles, _datos_no_registrados_table_detalles;
        Producto[] _productos_registrados, _producto_no_registrados;
        Modificar _modificar_producto;
        Registrar _registrar_producto_dv;
        servidor.dventas.Consultar _consultar_detalles_venta;

        Consultar _consultar_producto;
        int _temp1, _temp2, _temp3;
        int _aux, _aux2;//conocer el tamaño del array

        _aux = 0;
        _aux2 = 0;
        for (Producto item : tableProductos.getItems()) {
            if (!item.getId().isEmpty()) {
                _aux++;
            } else {
                _aux2++;
            }
        }

        _productos_registrados = new Producto[_aux];
        _producto_no_registrados = new Producto[_aux2];

        _aux = 0;
        _aux2 = 0;
        for (int i = 0; i < tableProductos.getItems().toArray().length; i++) {
            if (!tableProductos.getItems().get(i).getId().isEmpty()) {
                _productos_registrados[_aux] = tableProductos.getItems().get(i);
                _aux++;
            } else {
                _producto_no_registrados[_aux2] = tableProductos.getItems().get(i);
                _aux2++;
            }
        }

        _list_registrados = FXCollections.observableArrayList(_productos_registrados);
        _datos_registrados_table_detalles = FXCollections.observableArrayList(new Producto("", "", "", null).obtenerDatosRegistradosParaTablaDetallesVenta(_list_registrados));

        _list_no_registrados = FXCollections.observableArrayList(_producto_no_registrados);
        _datos_no_registrados_table_detalles = FXCollections.observableArrayList(new Producto("", "", "", null).obtenerDatosNoRegistradosParaTablaDetallesVenta(_list_no_registrados));

        _registrar_producto_dv = new Registrar("0");

        for (int i = 0; i < _list_registrados.toArray().length; i++) {
            try {

                _temp3 = Integer.parseInt(_list_registrados.get(i).getSpinner().getValue().toString());
                _consultar_producto = new Consultar(_list_registrados.get(i).getId());
                _modificar_producto = new Modificar(_list_registrados.get(i).getId());

                _consultar_producto.consultarProducto();

                if (_consultar_producto.productoEncontrado()) {

                    _temp1 = Integer.parseInt(_consultar_producto.getCantidad());
                    _temp2 = _temp1 - _temp3;

                    _modificar_producto.setNombre(_consultar_producto.getNombre());
                    _modificar_producto.setDescripcion(_consultar_producto.getDescripcion());
                    _modificar_producto.setPrecio(_consultar_producto.getPrecio());
                    _modificar_producto.setFechaVencimiento(_consultar_producto.getFechaVencimiento());
                    _modificar_producto.setModificadoPor(_consultar_producto.getModificadoPor());
                    _modificar_producto.setRutaImagen(_consultar_producto.getRutaImagen());
                    _modificar_producto.setCantidad(String.valueOf(_temp2));

                }

                _modificar_producto.modificar();

            } catch (IOException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (int i = 0; i < _datos_registrados_table_detalles.toArray().length; i++) {
            _registrar_producto_dv.setId("0");
            _registrar_producto_dv.setCodigo_barra(_datos_registrados_table_detalles.get(i).getId());
            _registrar_producto_dv.setNombre(_datos_registrados_table_detalles.get(i).getNombre());
            _registrar_producto_dv.setRegistrado_por(LoginController.nombre_usuario);
            _registrar_producto_dv.setPrecio(_datos_registrados_table_detalles.get(i).getPrecio());
            _registrar_producto_dv.setCantidad(String.valueOf(_datos_registrados_table_detalles.get(i).getCantidad_int()));
            _registrar_producto_dv.setTotal(String.valueOf(_datos_registrados_table_detalles.get(i).getTotal_dbl()));
            _registrar_producto_dv.registrar();
            _datos_registrados_table_detalles.get(i).setId_base_datos(_registrar_producto_dv.getId());

        }
        for (int i = 0; i < _datos_no_registrados_table_detalles.toArray().length; i++) {
            _registrar_producto_dv.setId("0");
            _registrar_producto_dv.setCodigo_barra("");
            _registrar_producto_dv.setNombre(_datos_no_registrados_table_detalles.get(i).getNombre());
            _registrar_producto_dv.setRegistrado_por(LoginController.nombre_usuario);
            _registrar_producto_dv.setPrecio(_datos_no_registrados_table_detalles.get(i).getPrecio());
            _registrar_producto_dv.setCantidad("");
            _registrar_producto_dv.setTotal(String.valueOf(_datos_no_registrados_table_detalles.get(i).getTotal_dbl()));
            _registrar_producto_dv.registrar();
            _datos_no_registrados_table_detalles.get(i).setId_base_datos(_registrar_producto_dv.getId());

        }

        tableProductos.getItems().clear();
        tableDetalles.getItems().addAll(_datos_registrados_table_detalles);
        tableDetalles.getItems().addAll(_datos_no_registrados_table_detalles);
        PrincipalController.getController().actualizarSaldoTotal(tableDetalles.getItems());
        PrincipalController.getController().actualizarTableProductosRegistrados();
        stageDialog.close();

    }

    public void setStage(Stage _stageDialog) {
        this.stageDialog = _stageDialog;
    }
}
