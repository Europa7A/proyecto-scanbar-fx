package controladores;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servidor.producto.Consultar;
import servidor.producto.Modificar;
import util.FxDialogs;
import util.Producto;
import util.UtilStage;

public class ModificarProductosController implements Initializable {

    @FXML
    private TextField tfID, tfNombre, tfCantidad, tfPrecio, tfDescripcion, tfFechaRegistro, tfRegistradoPor, tfModificadoPor, tfRutaImagen, tfCantidadSuma;
    @FXML
    private DatePicker dpFechaVencimiento;
    @FXML
    private Button btnModificar, btnElegir;
    @FXML
    private CheckBox chkSumarCantidad;
    @FXML
    private TableView<Producto> tableProductosRegistrados;

    private Stage stageDialog;
    private TableColumn tcID, tcDescripcion, tcNombre, tcPrecio, tcCantidad;

    private int index_producto = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tcID = new TableColumn<Producto, String>("Código de barra");
        tcNombre = new TableColumn<Producto, String>("Nombre");
        tcDescripcion = new TableColumn<Producto, String>("Descripcion");
        tcPrecio = new TableColumn<Producto, String>("Precio");
        tcCantidad = new TableColumn<Producto, String>("Cantidad");

        tcID.setEditable(true);
        tcID.setCellValueFactory(new PropertyValueFactory("codigo_de_barra"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));        
        tcDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));

        tcPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        tcCantidad.setCellValueFactory(new PropertyValueFactory("unidades_disponibles"));

        tableProductosRegistrados.getColumns().addAll(tcID, tcNombre, tcDescripcion, tcPrecio, tcCantidad);
        tableProductosRegistrados.getItems().addAll(Producto.obtenerDatosParaTableProductosRegistrados());

        tableProductosRegistrados.setOnMouseClicked(event -> {
            try {
                final int index = tableProductosRegistrados.getSelectionModel().getSelectedIndex();
                final String id = tableProductosRegistrados.getItems().get(index).getCodigo_de_barra();
                index_producto = index;
                tfID.setText(id);
                colocarDatosEnTextFields();
            } catch (IOException ex) {
                Logger.getLogger(ModificarProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tfNombre.setTooltip(new Tooltip("Nombre"));
        tfCantidad.setTooltip(new Tooltip("Cantidad"));
        tfPrecio.setTooltip(new Tooltip("Precio"));
        tfDescripcion.setTooltip(new Tooltip("Descripcion"));
        dpFechaVencimiento.setTooltip(new Tooltip("Fecha de vencimiento"));
        tfRutaImagen.setTooltip(new Tooltip("Ruta de la imágen"));
        btnModificar.setOnAction(event -> {
            try {
                actionBtnModificarProducto();
            } catch (IOException ex) {
                Logger.getLogger(ModificarProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnElegir.setOnAction(event -> mostrarElegirArchivo());
        chkSumarCantidad.setOnAction(event -> {
            if (chkSumarCantidad.isSelected()) {
                tfCantidadSuma.setDisable(false);
            } else {
                tfCantidadSuma.setDisable(true);
            }
        });

    }

    public void setTextID(String id) {
        tfID.setText(id);
    }

    private void limpiarCampos() {
        final TextField[] _text_fields = {tfID, tfNombre, tfCantidad, tfPrecio, tfDescripcion, tfFechaRegistro, tfRegistradoPor, tfModificadoPor, tfRutaImagen, tfCantidadSuma};
        for (TextField _text_field : _text_fields) {
            _text_field.clear();
        }
        chkSumarCantidad.setSelected(false);
        tfCantidadSuma.setDisable(true);
    }

    /*Consulta el id del producto para posteriormente colocarlos en los textFields*/
    public void colocarDatosEnTextFields() throws IOException {
        Consultar consultar = new Consultar(tfID.getText());
        consultar.consultarProducto();

        if (consultar.productoEncontrado()) {
            System.out.println("nombre: " + consultar.getNombre());
            tfNombre.setText(consultar.getNombre());
            tfCantidad.setText(consultar.getCantidad());
            tfPrecio.setText(consultar.getPrecio());
            tfDescripcion.setText(consultar.getDescripcion());
            tfFechaRegistro.setText(consultar.getFechaRegistro());
            dpFechaVencimiento.getEditor().setText(consultar.getFechaVencimiento());
            tfRegistradoPor.setText(consultar.getRegistradoPor());
            tfModificadoPor.setText(consultar.getModificadoPor());
            tfRutaImagen.setText(consultar.getRutaImagen());
        } else {

        }
    }

    private void mostrarElegirArchivo() {
        FileChooser fc;
        File ruta_archivo;

        fc = new FileChooser();
        ruta_archivo = fc.showOpenDialog(stageDialog);

        if (ruta_archivo != null) {
            tfRutaImagen.setText(ruta_archivo.getPath());
        }

    }

    public void setStageDialog(Stage stageDialog) {
        this.stageDialog = stageDialog;
    }

    private void actionBtnModificarProducto() throws IOException {
        try {
            if (!tfID.getText().isEmpty()) {
                String id, nombre, descripcion, precio, cantidad, cantidad2, fecha_vencimiento, modificado_por, imagen;
                int _cantidad = 0;

                id = tfID.getText();
                nombre = tfNombre.getText();
                descripcion = tfDescripcion.getText();
                precio = tfPrecio.getText();
                cantidad = tfCantidad.getText();
                cantidad2 = tfCantidadSuma.getText();
                fecha_vencimiento = dpFechaVencimiento.getEditor().getText();
                modificado_por = LoginController.nombre_administrador;
                imagen = tfRutaImagen.getText();

                if (chkSumarCantidad.isSelected()) {
                    if (!cantidad2.isEmpty()) {
                        try {
                            _cantidad = Integer.parseInt(cantidad) + Integer.parseInt(cantidad2);
                        } catch (NumberFormatException e) {
                            _cantidad = Integer.parseInt(cantidad);
                        }
                    }
                } else {
                    _cantidad = Integer.parseInt(cantidad);
                }

                Modificar modificar = new Modificar(id);

                modificar.setModificadoPor(modificado_por);
                modificar.setNombre(nombre);
                modificar.setDescripcion(descripcion);
                modificar.setPrecio(precio);
                modificar.setCantidad(String.valueOf(_cantidad));
                modificar.setFechaVencimiento(fecha_vencimiento);
                modificar.setRutaImagen(imagen);
                modificar.modificar();

                if (modificar.estaModificado()) {
                    tableProductosRegistrados.getItems().get(index_producto).setNombre(nombre);
                    tableProductosRegistrados.getItems().get(index_producto).setPrecio(precio);
                    tableProductosRegistrados.getItems().get(index_producto).setUnidades_disponibles(String.valueOf(_cantidad));
                    tableProductosRegistrados.refresh();
                    PrincipalController.getController().actualizarTableProductosRegistrados();
                    FxDialogs.showInformation("", "Operacion realizada exitosamente");
                    limpiarCampos();
                } else {
                    FxDialogs.showError("", "Modificación no realizada");
                }

            }
        } catch (Exception e) {
            FxDialogs.showError("", "Ocurrio un error al intentar modificar el producto\n"+e);
        }
        
    }

}
