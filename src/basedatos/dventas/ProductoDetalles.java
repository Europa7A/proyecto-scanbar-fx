package basedatos.dventas;

public class ProductoDetalles {

    protected String id, codigo_barra, nombre, registrado_por, modificado_por, devolucion, precio, cantidad, total, fecha_registro, hora_registro, sesion;

    protected ProductoDetalles() {
        id = "";
        codigo_barra = "";
        nombre = "";
        registrado_por = "";
        modificado_por = "";
        devolucion = "";
        precio = "";
        cantidad = "";
        fecha_registro = "";
        hora_registro = "";
    }

    public String getId() {
        return id;
    }

    public String getCodigo_barra() {
        return codigo_barra;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRegistrado_por() {
        return registrado_por;
    }

    public String getModificado_por() {
        return modificado_por;
    }

    public String getDevolucion() {
        return devolucion;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getTotal() {
        return total;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public String getHora_registro() {
        return hora_registro;
    }
    public String getSesion() {
        return sesion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCodigo_barra(String codigo_barra) {
        this.codigo_barra = codigo_barra;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRegistrado_por(String registrado_por) {
        this.registrado_por = registrado_por;
    }

    public void setModificado_por(String modificado_por) {
        this.modificado_por = modificado_por;
    }

    public void setDevolucion(String devolucion) {
        this.devolucion = devolucion;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public void setHora_registro(String hora_registro) {
        this.hora_registro = hora_registro;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    public void setSesion(String sesion) {
        this.sesion = sesion;
    }
}
