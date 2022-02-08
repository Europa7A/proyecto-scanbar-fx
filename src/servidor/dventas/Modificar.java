
package servidor.dventas;

public class Modificar extends ProductoDetalles{
    
    public static basedatos.dventas.Modificar.StringsModificacion SM;
    
    private boolean modificado = false;
    
    public Modificar(String id) {
        super(id);
    }
    
    public void modificarConValor(basedatos.dventas.Modificar.StringsModificacion _sm, String _valor){
        basedatos.dventas.Modificar _modificar_producto;
        
        _modificar_producto = new basedatos.dventas.Modificar(id);
        
        final boolean _modificado = _modificar_producto.modificarProductoDetallesConValor(_sm, _valor);
        
        if(_modificado){
            this.modificado = true;
        }   
    }
    
    public boolean estaModificado(){
        return modificado;
    }
    
    
}
