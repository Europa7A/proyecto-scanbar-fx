package controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import servidor.caja.Registrar;
import servidor.dventas.ProductoDetalles;
import util.FxDialogs;
import util.ReportesPDF;

public class TerminarSesionController implements Initializable {

    @FXML
    private TextField tf200, tf100, tf50, tf20, tf10, tf5, tf2, tf1, tf050, tf020, tfTotalCaja, tfDineroVentas, tfDescuadre, tfTotalBilletes, tfTotalMonedas;

    @FXML
    private Label lbl200, lbl100, lbl50, lbl20, lbl10, lbl5, lbl2, lbl1, lbl050, lbl020;

    @FXML
    private Button btnFinalizarSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        inicializarSoloNumerosTextFieldActions();
        iniciarlizarCalcularMontoTextFieldsActions();
        btnFinalizarSesion.setOnAction(event -> actionBtnFinalizarSesion());

    }

    private void actionBtnFinalizarSesion() {

        final String _a = FxDialogs.showConfirm("", "¿Estás seguro?", "Si, deseo terminar sesión", "No, por dios no");

        if (_a.equalsIgnoreCase("Si, deseo terminar sesión")) {
            
            registrarCaja();
            ReportesPDF.crearReporte(new SimpleDateFormat("dd-MM-yyyy").format(new Date()), LoginController.nombre_usuario, "abierta");
            registrarSesion();
            
        } 

    }

    private void registrarSesion() {
        servidor.usuario.Consultar _consultar_usuario;
        servidor.usuario.Modificar _modificar_usuario;
        servidor.dventas.Modificar _modificar_detalles_venta;
        servidor.dventas.Consultar _consultar_detalles_venta;

        _consultar_usuario = new servidor.usuario.Consultar();

        try {
            _consultar_usuario.consultarUsuarioPorUsuario(LoginController.nombre_usuario);

            if (_consultar_usuario.usuarioEntontrado()) {

                _modificar_usuario = new servidor.usuario.Modificar(_consultar_usuario.getId());

                _modificar_usuario.setNombres(_consultar_usuario.getNombres());
                _modificar_usuario.setApellidos(_consultar_usuario.getApellidos());
                _modificar_usuario.setTelefono(_consultar_usuario.getTelefono());
                _modificar_usuario.setDireccion(_consultar_usuario.getDireccion());
                _modificar_usuario.setPassword(_consultar_usuario.getPassword());
                _modificar_usuario.setEstado(_consultar_usuario.getEstado());
                _modificar_usuario.setPrivilegio(_consultar_usuario.getPrivilegio());
                _modificar_usuario.setUsuario(_consultar_usuario.getUsuario());
                _modificar_usuario.setEstadoSesion(PrincipalController.ESTADO_FINALIZADO);
                _modificar_usuario.setFechaFinSesion(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                _modificar_usuario.setFechaInicioSesion(_consultar_usuario.getFechaInicioSesion());
                _modificar_usuario.moficiarUsuario();

                if (_modificar_usuario.usuarioModificado()) {

                    ProductoDetalles[] _producto_detalles;

                    _consultar_detalles_venta = new servidor.dventas.Consultar("");

                    _producto_detalles = _consultar_detalles_venta.consultarTodoPorFechaUsuarioYSesion(_consultar_usuario.getFechaInicioSesion(), _consultar_usuario.getUsuario(), "abierta");

                    if (_producto_detalles != null) {
                        for (int i = 0; i < _producto_detalles.length; i++) {
                            _modificar_detalles_venta = new servidor.dventas.Modificar(_producto_detalles[i].getId());
                            _modificar_detalles_venta.modificarConValor(servidor.dventas.Modificar.SM.sesion, "cerrada");
                            if (_modificar_detalles_venta.estaModificado()) {
                                System.out.println("Producto " + _producto_detalles[i].getNombre() + " : modificado");
                            }

                        }
                    }
                    FxDialogs.showInformation("", "Has cerrado sesión");
                    stageDialog.close();
                    stageDialogPrincipal.close();
                } else {
                    
                }

            } else {
                
            }

        } catch (SQLException | IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registrarCaja() {
        Registrar _registrar_caja;

        _registrar_caja = new Registrar("");

        _registrar_caja.setId("0");
        _registrar_caja.setUsuario(LoginController.nombre_usuario);
        _registrar_caja.setB200(tf200.getText());
        _registrar_caja.setB100(tf100.getText());
        _registrar_caja.setB50(tf50.getText());
        _registrar_caja.setB20(tf20.getText());
        _registrar_caja.setB10(tf10.getText());
        _registrar_caja.setM5(tf5.getText());
        _registrar_caja.setM2(tf2.getText());
        _registrar_caja.setM1(tf1.getText());
        _registrar_caja.setM050(tf050.getText());
        _registrar_caja.setM020(tf020.getText());
        _registrar_caja.setTotal_dinero(tfTotalCaja.getText());
        _registrar_caja.setTotal_ventas(tfDineroVentas.getText());
        _registrar_caja.setDescuadre(tfDescuadre.getText());
        _registrar_caja.setFecha_fin_sesion("");
        _registrar_caja.setHora_fin_sesion("");

        _registrar_caja.registrarCaja();

        if (_registrar_caja.estaRegistrado()) {
            
        }
    }

    private void inicializarSoloNumerosTextFieldActions() {

        final TextField[] _text_fields_inputs = {tf200, tf100, tf50, tf20, tf10, tf5, tf2, tf1, tf050, tf020};

        for (TextField _text_field_input : _text_fields_inputs) {
            _text_field_input.setOnKeyTyped(keyEvent -> {

                if (!Character.isDigit(keyEvent.getCharacter().charAt(0))) {
                    keyEvent.consume();
                }
            });
        }

    }

    public void inicializarTextFieldDineroEnVentas(String _total) {

        tfDineroVentas.setText(_total);
        DESCUADRE = SALDO_TOTAL - DINERO_VENTAS;
        tfDescuadre.setText(String.valueOf(Math.round(DESCUADRE * 100d) / 100d));
    }

    private void iniciarlizarCalcularMontoTextFieldsActions() {

        //double _total = 0d;int f;                     
        final TextField[] _text_fields_inputs = {tf200, tf100, tf50, tf20, tf10, tf5, tf2, tf1, tf050, tf020};
        final Label[] _lbls_totals = {lbl200, lbl100, lbl50, lbl20, lbl10, lbl5, lbl2, lbl1, lbl050, lbl020};

        _text_fields_inputs[0].setOnKeyReleased(keyEvent -> {
            try {

                double _total = Integer.parseInt(_text_fields_inputs[0].getText()) * MONTOS[0];
                _lbls_totals[0].setText(String.valueOf(_total));
                actualizarSaldoTotal(_lbls_totals);

            } catch (NumberFormatException e) {
                if (_text_fields_inputs[0].getText().isEmpty()) {
                    _lbls_totals[0].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }

        });
        _text_fields_inputs[1].setOnKeyReleased(keyEvent -> {
            try {
                double _total = Integer.parseInt(_text_fields_inputs[1].getText()) * MONTOS[1];
                _lbls_totals[1].setText(String.valueOf(_total));
                actualizarSaldoTotal(_lbls_totals);
            } catch (NumberFormatException e) {
                if (_text_fields_inputs[1].getText().isEmpty()) {
                    _lbls_totals[1].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }

        });
        _text_fields_inputs[2].setOnKeyReleased(keyEvent -> {

            try {
                double _total = Integer.parseInt(_text_fields_inputs[2].getText()) * MONTOS[2];
                _lbls_totals[2].setText(String.valueOf(_total));
                actualizarSaldoTotal(_lbls_totals);
            } catch (NumberFormatException e) {
                if (_text_fields_inputs[2].getText().isEmpty()) {
                    _lbls_totals[2].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }
        });
        _text_fields_inputs[3].setOnKeyReleased(keyEvent -> {

            try {
                double _total = Integer.parseInt(_text_fields_inputs[3].getText()) * MONTOS[3];
                _lbls_totals[3].setText(String.valueOf(_total));
                actualizarSaldoTotal(_lbls_totals);
            } catch (NumberFormatException e) {
                if (_text_fields_inputs[3].getText().isEmpty()) {
                    _lbls_totals[3].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }
        });
        _text_fields_inputs[4].setOnKeyReleased(keyEvent -> {

            try {
                double _total = Integer.parseInt(_text_fields_inputs[4].getText()) * MONTOS[4];
                _lbls_totals[4].setText(String.valueOf(_total));
                actualizarSaldoTotal(_lbls_totals);
            } catch (NumberFormatException e) {
                if (_text_fields_inputs[4].getText().isEmpty()) {
                    _lbls_totals[4].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }
        });
        _text_fields_inputs[5].setOnKeyReleased(keyEvent -> {

            try {
                double _total = Integer.parseInt(_text_fields_inputs[5].getText()) * MONTOS[5];
                _lbls_totals[5].setText(String.valueOf(_total));
                actualizarSaldoTotal(_lbls_totals);
            } catch (NumberFormatException e) {
                if (_text_fields_inputs[5].getText().isEmpty()) {
                    _lbls_totals[5].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }
        });
        _text_fields_inputs[6].setOnKeyReleased(keyEvent -> {

            try {
                double _total = Integer.parseInt(_text_fields_inputs[6].getText()) * MONTOS[6];
                _lbls_totals[6].setText(String.valueOf(_total));
                actualizarSaldoTotal(_lbls_totals);
            } catch (NumberFormatException e) {
                if (_text_fields_inputs[6].getText().isEmpty()) {
                    _lbls_totals[6].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }
        });
        _text_fields_inputs[7].setOnKeyReleased(keyEvent -> {

            try {
                double _total = Integer.parseInt(_text_fields_inputs[7].getText()) * MONTOS[7];
                _lbls_totals[7].setText(String.valueOf(_total));
                actualizarSaldoTotal(_lbls_totals);
            } catch (NumberFormatException e) {
                if (_text_fields_inputs[7].getText().isEmpty()) {
                    _lbls_totals[7].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }
        });
        _text_fields_inputs[8].setOnKeyReleased(keyEvent -> {

            try {
                double _total = Integer.parseInt(_text_fields_inputs[8].getText()) * MONTOS[8];
                _lbls_totals[8].setText(String.valueOf(_total));
                actualizarSaldoTotal(_lbls_totals);
            } catch (NumberFormatException e) {
                if (_text_fields_inputs[8].getText().isEmpty()) {
                    _lbls_totals[8].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }
        });
        _text_fields_inputs[9].setOnKeyReleased(keyEvent -> {

            try {
                double _total = Integer.parseInt(_text_fields_inputs[9].getText()) * MONTOS[9];
                _lbls_totals[9].setText(String.valueOf(Math.round(_total * 100d) / 100d));
                actualizarSaldoTotal(_lbls_totals);
            } catch (NumberFormatException e) {
                if (_text_fields_inputs[9].getText().isEmpty()) {

                    _lbls_totals[9].setText("Sin valor");
                    actualizarSaldoTotal(_lbls_totals);
                }
            }
        });
    }

    private void actualizarSaldoTotal(Label[] _labels) {

        double _total_aux = 0d;

        try {

            for (int i = 0; i < _labels.length; i++) {
                if (i <= 4) {
                    if (!_labels[i].getText().equals("Sin valor")) {
                        _total_aux += Double.parseDouble(_labels[i].getText());
                        SALDO_BILLETES = _total_aux;

                        if (SALDO_BILLETES != 0d) {
                            tfTotalBilletes.setText(String.valueOf(SALDO_BILLETES));
                        }
                        //System.out.println(_total_aux + ": billetes");
                    } else {
                        if (_total_aux == 0d) {
                            SALDO_BILLETES = 0d;
                            tfTotalBilletes.setText("");
                        }
                    }
                }
            }
            _total_aux = 0d;
            for (int i = 0; i < _labels.length; i++) {
                if (i >= 5) {
                    if (!_labels[i].getText().equals("Sin valor")) {
                        _total_aux += Double.parseDouble(_labels[i].getText());
                        SALDO_MONEDAS = _total_aux;

                        if (SALDO_MONEDAS != 0d) {
                            tfTotalMonedas.setText(String.valueOf(SALDO_MONEDAS));
                        }
                        //System.out.println(_total_aux + ": billetes");
                    } else {
                        if (_total_aux == 0d) {
                            SALDO_MONEDAS = 0d;
                            tfTotalMonedas.setText("");
                        }
                    }
                }
            }
            SALDO_TOTAL = SALDO_BILLETES + SALDO_MONEDAS;

            if (SALDO_TOTAL != 0d) {
                tfTotalCaja.setText(String.valueOf(SALDO_TOTAL));
            } else {
                tfTotalCaja.setText("");
            }
            DESCUADRE = SALDO_TOTAL - DINERO_VENTAS;
            tfDescuadre.setText(String.valueOf(Math.round(DESCUADRE * 100d) / 100d));
        } catch (NumberFormatException e) {
            System.out.println("ACA");
        }

    }

    public void setStageDialogPrincipal(Stage stageDialog) {
        stageDialogPrincipal = stageDialog;
    }

    public void setStage(Stage stageDialog) {
        this.stageDialog = stageDialog;
    }

    public Stage getStage() {
        return stageDialog;
    }

    private final static double[] MONTOS = {200d, 100d, 50d, 20d, 10d, 5d, 2d, 1d, 0.5d, 0.2d};
    private static double SALDO_BILLETES = 0d, SALDO_MONEDAS = 0d, SALDO_TOTAL = 0d, DESCUADRE = 0d;
    public double DINERO_VENTAS = 0d;
    private Stage stageDialog, stageDialogPrincipal;

}
