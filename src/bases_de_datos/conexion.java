package bases_de_datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class conexion {
    
//    private final String driver = "com.mysql.jdbc.Driver";
//    private final String user = "root";
//    private final String password = "MYSQL";
//    private final String url = "jdbc:mysql://localhost/nombre_base_de_datos";
    private Connection con;
    private Statement sta;
    private final String driver = "com.mysql.jdbc.Driver";
    private final String user;
    private final String password;
    private final String url;

    public conexion(String user, String password, String url) {
        this.user = user;
        this.password = password;
        this.url = url;
    }

    public boolean conectar() {
        con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            sta = con.createStatement();
            if (con != null) {
                System.out.println("se ha conectado!!!\n");
            } else {
                System.out.println("no se ha conectado\n");
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ha ocurrido un error inesperado\n");
            return false;
        }
        return true;
    }

    public Connection Getconeccion() {
        return con;
    }
    
    //metodos ventana princiapl
    
    public ResultSet GetDtataBases() throws SQLException{
        return sta.executeQuery("SHOW DATABASES;");
    }
    
    public void SelectDataBase(String bd) throws SQLException{
        sta.executeUpdate("USE " + bd + ";");
    }
    
    //metodos ventana bases de datos
    
    public ResultSet GetTables(String bd) throws SQLException{
        return sta.executeQuery("SHOW TABLES;");
    }
    
    public ResultSet GetColumnas(String table) throws SQLException{
        return sta.executeQuery("DESCRIBE " + table + ";");
    }

    //metodos ventaba tablas
    
    public int agregar(String datos, String table) throws SQLException {
        return sta.executeUpdate("INSERT INTO " + table + " values(" + datos + ");");
    }

    public ResultSet GetDatos(String table) throws SQLException {
        String z = "SELECT * FROM " + table + ";";
//        System.out.println(z);
        return sta.executeQuery(z);
    }
    
    public void borrar(String dato, String valor, String table) throws SQLException {
        String z = "DELETE FROM " + table + " WHERE " + dato + " = '" + valor + "';";
//        System.out.println(z);
        sta.executeUpdate(z);
    }
    
    public int Actualizar(String tabla, String datos) throws SQLException{
        String z = "UPDATE " + tabla + " " + datos;
//        System.out.println(z);
        return sta.executeUpdate(z);
    }
    
    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    
    public ResultSet GetDatosId(String id) throws SQLException {
        return sta.executeQuery("SELECT nombre, pasword FROM tabla1 WHERE Id = '" + id + "'");
    }

    public ResultSet GetDatosNombre(String nombre) throws SQLException {
        return sta.executeQuery("SELECT pasword FROM tabla1 WHERE nombre = '" + nombre + "'");
    }

    public ResultSet GetDatosAdmin(String nombre) throws SQLException { //xxx
        return sta.executeQuery("SELECT pasword FROM administradores WHERE nombre = '" + nombre + "'");
    }

    public void actualizar_pass(String id, String newpass) throws SQLException {
//        System.out.println("UPDATE tabla1 SET pasword = " + newpass + " where Id = " + id);
        sta.executeUpdate("UPDATE tabla1 SET pasword = '" + newpass + "' where Id = " + id);
    }

    public void borrarTodo(String table) throws SQLException {
        sta.executeUpdate("DELETE FROM " + table + ";");
    }

    public void desconectar() {
        con = null;
        System.out.println("desconectado");
    }
    
    public int CrearLlavePrimaria(String tabla, String columna) throws SQLException{
        String z = "ALTER TABLE " + tabla + " ADD PRIMARY KEY (" + columna+ ");";
//        System.out.println(z);
        return sta.executeUpdate(z);
    }
    
    public int CrearLlaveForanea(String tabla, String atri, String tabla_ref, String atri_ref) throws SQLException{
        String z = "ALTER TABLE " + tabla + " ADD FOREIGN KEY(" + atri + 
                ") REFERENCES " + tabla_ref + "(" + atri_ref + ");";
//        System.out.println(z);
        return sta.executeUpdate(z);
    }
}
