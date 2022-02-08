package servidor.producto;

import java.io.IOException;

public class Producto {

    /*
    * @param usuarios = registrado_por
     */
    protected String nombre, descripcion, id, precio, cantidad, fecha_registro, registrado_por, fecha_vencimiento, modificado_por, ruta_img;
    protected basedatos.productos.Registrar registrar;
    protected boolean registrado;

    public Producto(String id) {
        this.id = id;
        nombre = "";
        descripcion = "";
        precio = "";
        cantidad = "";
        fecha_registro = "";
        registrado_por = "";
        fecha_vencimiento = "";
        modificado_por = "";
        ruta_img = "";
    }

    /* Setter methods */
    public void setID(String id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaVencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public void setFechaRegistro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public void setModificadoPor(String modificado_por) {
        this.modificado_por = modificado_por;
    }
    public void setRegistradoPor(String registrado_por) {
        this.registrado_por= registrado_por;
    }

    public void setRutaImagen(String ruta_img) {
        this.ruta_img = ruta_img;
    }

    /* Getter methods */
    public String getID() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getFechaVencimiento() {
        return fecha_vencimiento;
    }

    public String getFechaRegistro() {
        return fecha_registro;
    }

    public String getModificadoPor() {
        return modificado_por;
    }
    public String getRegistradoPor() {
        return registrado_por;
    }

    public String getRutaImagen() {
        return ruta_img;
    }

}
