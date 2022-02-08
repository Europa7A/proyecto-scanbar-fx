package servidor.caja;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Registrar extends Caja {

    private boolean registrado = false;

    public Registrar(String id) {
        super(id);
    }

    public void registrarCaja() {

        basedatos.caja.Registrar _registrar_caja;

        _registrar_caja = new basedatos.caja.Registrar(id);

        _registrar_caja.setId("0");
        _registrar_caja.setUsuario(usuario);
        _registrar_caja.setB200(b200);
        _registrar_caja.setB100(b100);
        _registrar_caja.setB50(b50);
        _registrar_caja.setB20(b20);
        _registrar_caja.setB10(b10);
        _registrar_caja.setM5(m5);
        _registrar_caja.setM2(m2);
        _registrar_caja.setM1(m1);
        _registrar_caja.setM050(m050);
        _registrar_caja.setM020(m020);
        _registrar_caja.setTotal_dinero(total_dinero);
        _registrar_caja.setTotal_ventas(total_ventas);
        _registrar_caja.setDescuadre(descuadre);
        _registrar_caja.setFecha_fin_sesion(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        _registrar_caja.setHora_fin_sesion(new SimpleDateFormat("hh:mm:ss").format(new Date()));

        if (_registrar_caja.registrarCaja()) {
            registrado = true;
        }

    }

    public boolean estaRegistrado() {
        return registrado;
    }

}
