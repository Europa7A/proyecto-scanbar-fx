
package servidor.usuario;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Registrar extends Usuario{
    
    private boolean registrado;
    
    public void registrarUsuario() throws IOException{
        basedatos.usuarios.Registrar registrar = new basedatos.usuarios.Registrar();
        registrar.setNombres(nombres);
        registrar.setUsuario(usuario);
        registrar.setPassword(password);
        registrar.setPrivilegio(privilegio);
        registrar.setEstado(USUARIO_ACTIVO);
        registrar.setFechaIngreso(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        
        if(registrar.registrarUsuario()){
            registrado = true;
        }
    }
    public boolean usuarioRegistrado(){
        return registrado;
    }
}
