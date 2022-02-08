package servidor.caja;

public class Consultar extends Caja {

    private boolean encontrado = false;

    public Consultar(String id) {
        super(id);
    }

    public void consultarProducto() {

        basedatos.caja.Consultar _consultar_caja;

        _consultar_caja = new basedatos.caja.Consultar(id);

        if (_consultar_caja.consultarCaja()) {
            usuario = _consultar_caja.getUsuario();
            b200 = _consultar_caja.getB200();
            b100 = _consultar_caja.getB100();
            b50 = _consultar_caja.getB50();
            b20 = _consultar_caja.getB20();
            b10 = _consultar_caja.getB10();
            m5 = _consultar_caja.getM5();
            m2 = _consultar_caja.getM2();
            m1 = _consultar_caja.getM1();
            m050 = _consultar_caja.getM050();
            m020 = _consultar_caja.getM020();
            total_dinero = _consultar_caja.getTotal_dinero();
            total_ventas = _consultar_caja.getTotal_ventas();
            descuadre = _consultar_caja.getDescuadre();
            fecha_fin_sesion = _consultar_caja.getFecha_fin_sesion();
            hora_fin_sesion = _consultar_caja.getHora_fin_sesion();
            encontrado = true;
        }
    }
     public void consultarUltimo() {

        basedatos.caja.Consultar _consultar_caja;

        _consultar_caja = new basedatos.caja.Consultar(id);

        if (_consultar_caja.consultarUltimo()) {
            usuario = _consultar_caja.getUsuario();
            b200 = _consultar_caja.getB200();
            b100 = _consultar_caja.getB100();
            b50 = _consultar_caja.getB50();
            b20 = _consultar_caja.getB20();
            b10 = _consultar_caja.getB10();
            m5 = _consultar_caja.getM5();
            m2 = _consultar_caja.getM2();
            m1 = _consultar_caja.getM1();
            m050 = _consultar_caja.getM050();
            m020 = _consultar_caja.getM020();
            total_dinero = _consultar_caja.getTotal_dinero();
            total_ventas = _consultar_caja.getTotal_ventas();
            descuadre = _consultar_caja.getDescuadre();
            fecha_fin_sesion = _consultar_caja.getFecha_fin_sesion();
            hora_fin_sesion = _consultar_caja.getHora_fin_sesion();
            encontrado = true;
        }
    }

    public boolean productoEncontrado() {
        return encontrado;
    }

}
