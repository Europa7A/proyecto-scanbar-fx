package servidor.dventas;

public class Eliminar extends ProductoDetalles {

    private boolean eliminado = false;

    public Eliminar(String id) {
        super(id);
    }

    public void elimnarProducto() {
        basedatos.dventas.Eliminar _eliminar_producto;

        _eliminar_producto = new basedatos.dventas.Eliminar(id);

        if (_eliminar_producto.eliminarProducto()) {
            eliminado = true;
        }
    }

    public boolean productoEliminado() {
        return eliminado;
    }

}
