package bases_de_datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class conexion {

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

    public void desconectar() {
        con = null;
        System.out.println("desconectado");
    }

    //metodos ventana princiapl
    public ResultSet GetDataBases() throws SQLException {
        return sta.executeQuery("SHOW DATABASES;");
    }

    public void SelectDataBase(String bd) throws SQLException {
        sta.executeUpdate("USE " + bd + ";");
    }

    public void CrearDataBase(String nombre) throws SQLException {
        sta.executeUpdate("CREATE DATABASE " + nombre + ";");
    }

    public ResultSet TamanioDataBases() throws SQLException {
        return sta.executeQuery("SELECT\n"
                + "  table_schema \"Bases de Datos\",\n"
                + "  sum( data_length + index_length ) / 1024 / 1024 \"Longitud en MB\"\n"
                + "  FROM\n"
                + "  information_schema.TABLES GROUP BY table_schema;");
    }

    public ResultSet GetTables() throws SQLException {
        return sta.executeQuery("SHOW TABLES;");
    }

    public ResultSet GetColumnas(String table) throws SQLException {
        return sta.executeQuery("DESCRIBE " + table + ";");
    }
    
    public void CrearTabla(String nombre) throws SQLException{
        sta.executeUpdate("Create Table " + nombre + " (ID int);");
    }
    
    public void BorrarTabla(String nombre) throws SQLException{
        sta.executeUpdate("DROP " + nombre + ";");
    }

    //metodos ventaba tablas
    public int agregar(String datos, String table) throws SQLException {
        String z = "INSERT INTO " + table + " values(" + datos + ");";
//        System.out.println(z);
        return sta.executeUpdate(z);
    }

    public ResultSet GetDatos(String table) throws SQLException {
        String z = "SELECT * FROM " + table + ";";
//        System.out.println(z);
        return sta.executeQuery(z);
    }

    public void borrar(String dato, String valor, String table) throws SQLException {
        String z = "DELETE FROM " + table + " WHERE " + dato + " = '" + valor + "';";
        System.out.println(z);
        sta.executeUpdate(z);
    }

    public void BorrarFila(String datos, String table) throws SQLException {
        String z = "DELETE FROM " + table + " WHERE" + datos + ";";
        System.out.println(z);
        sta.executeUpdate(z);
    }

    public int Actualizar(String tabla, String datos) throws SQLException {
        String z = "UPDATE " + tabla + " " + datos;
//        System.out.println(z);
        return sta.executeUpdate(z);
    }

    public int BorrarTodo(String tabla) throws SQLException {
        return sta.executeUpdate("DELETE FROM " + tabla + ";");
    }

    public int AgregarColumna(String datos, String tabla) throws SQLException {
        String z = "ALTER TABLE " + tabla + " ADD(" + datos + ");";
//        System.out.println(z);
        return sta.executeUpdate(z);
    }

    public int BorrarColumna(String tabla, String columna) throws SQLException {
        String z = "ALTER TABLE " + tabla + " DROP " + columna + ";";
//        System.out.println(z);
        return sta.executeUpdate(z);
    }

    public int CrearLlavePrimaria(String tabla, String columna) throws SQLException {
        String z = "ALTER TABLE " + tabla + " ADD PRIMARY KEY (" + columna + ");";
//        System.out.println(z);
        return sta.executeUpdate(z);
    }

    public int CrearLlaveForanea(String tabla, String atri, String tabla_ref, String atri_ref) throws SQLException {
        String z = "ALTER TABLE " + tabla + " ADD FOREIGN KEY(" + atri
                + ") REFERENCES " + tabla_ref + "(" + atri_ref + ");";
//        System.out.println(z);
        return sta.executeUpdate(z);
    }
}
