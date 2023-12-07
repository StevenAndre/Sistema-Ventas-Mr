import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuario {

    private static final String ALL_USUARIOS = "select * from usuarios";

    private Connection conn = null;
    public static Usuario usuarioActual;

    public RepositorioUsuario(){
        conn=ConexionBD.getConection();
    }

    public List<Usuario> filtrarUsuarios(String argument) {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario;
        String query = "select * from usuarios where nombre like '%" + argument + "%' or username like '%" + argument + "%'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public boolean login(String username,String password){
        Usuario usuario= null;
        String query="select * from usuarios where username = '"+username+"' ";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
            usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("username"),
                    rs.getString("password")
            );
            }
            if(usuario.getPassword().equals(password)){
                usuarioActual=usuario;
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    public void updateUsuario(Usuario u) {
        int id = u.getId();

        String updateQuery = "update usuarios set nombre='" + u.getNombre() +
                "', username='" + u.getUsername() +
                "', password='" + u.getPassword() +
                "' where id=" + id;

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Usuario obtenerUsuarioPorId(int id) {
        Usuario usuario = null;
        String query = "select * from usuarios where id = " + id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    public void deleteUsuario(int id) {
        String deleteQuery = "delete from usuarios where id=" + id;
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUsuario(Usuario u) {
        String insertQuery = "insert into usuarios(nombre, username, password) values('" + u.getNombre() + "','" + u.getUsername() + "','" + u.getPassword() + "')";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_USUARIOS);

            while (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuarios;
    }




}
