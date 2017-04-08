package bases_de_datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class conexion {

    private Connection con;
    private Statement sta;
    private final String driver;
    consola cons;

    public conexion() {
        driver = "com.mysql.jdbc.Driver";
        cons = new consola(this);
    }

    public boolean conectar(String user, String password, String url) {
        con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            sta = con.createStatement();
            if (con != null) {
                cons.agregar("se ha conectado!!!");
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
        sta = null;
        cons.agregar("desconectado");
    }
    
    public void EjecutarUpdate(String comando) throws SQLException{
        if (sta == null) {
            return;
        }
        sta.executeUpdate(comando);
        cons.agregar(comando);
    }
    
    public ResultSet EjecutarConsulta(String comando) throws SQLException{
        if (sta == null) {
            return null;
        }
        ResultSet r = sta.executeQuery(comando);
        cons.agregar(comando);
        return r;
    }

    //metodos ventana princiapl
    public ResultSet GetDataBases() throws SQLException {
        String z = "SHOW DATABASES;";
        cons.agregar(z);
        return sta.executeQuery(z);
    }

    public void SelectDataBase(String bd) throws SQLException {
        String z = "USE " + bd + ";";
        cons.agregar(z);
        sta.executeUpdate(z);
    }

    public void CrearDataBase(String nombre) throws SQLException {
        String z = "CREATE DATABASE " + nombre + ";";
        cons.agregar(z);
        sta.executeUpdate(z);
    }

    public void BorrarDataBase(String nombre) throws SQLException {
        String z = "DROP DATABASE " + nombre + ";";
        cons.agregar(z);
        sta.executeUpdate(z);
    }

    public ResultSet TamanioDataBases() throws SQLException {
        String z = "SELECT table_schema \"database_name\", sum( data_length + index_length ) / 1024 /1024 \"Data Base Size in MB\", \n"
                + "COUNT(*) \"numero_de_tablas\" FROM information_schema.TABLES GROUP BY table_schema;";
//        cons.agregar(z);
        return sta.executeQuery(z);
    }

    public ResultSet GetTables() throws SQLException {
        String z = "SHOW TABLES;";
        cons.agregar(z);
        return sta.executeQuery(z);
    }

    public ResultSet GetColumnas(String table) throws SQLException {
        String z = "DESCRIBE " + table + ";";
        cons.agregar(z);
        return sta.executeQuery(z);
    }

    public void CrearTabla(String nombre) throws SQLException {
        String z = "Create Table " + nombre + " (ID int NOT NULL);";
        cons.agregar(z);
        sta.executeUpdate(z);
    }

    public void BorrarTabla(String nombre) throws SQLException {
        String z = "DROP TABLE IF EXISTS " + nombre + ";";
        cons.agregar(z);
        sta.executeUpdate(z);
    }

    //metodos ventaba tablas
    public int agregar(String datos, String table) throws SQLException {
        String z = "INSERT INTO " + table + " values(" + datos + ");";
        cons.agregar(z);
        return sta.executeUpdate(z);
    }

    public ResultSet GetDatos(String table) throws SQLException {
        String z = "SELECT * FROM " + table + ";";
        cons.agregar(z);
        return sta.executeQuery(z);
    }

    public void borrar(String dato, String valor, String table) throws SQLException {
        String z = "DELETE FROM " + table + " WHERE " + dato + " = '" + valor + "';";
        cons.agregar(z);
        sta.executeUpdate(z);
    }

    public void BorrarFila(String datos, String table) throws SQLException {
        String z = "DELETE FROM " + table + " WHERE" + datos + ";";
        cons.agregar(z);
        sta.executeUpdate(z);
    }

    public int Actualizar(String tabla, String datos) throws SQLException {
        String z = "UPDATE " + tabla + " " + datos;
        cons.agregar(z);
        return sta.executeUpdate(z);
    }

    public int BorrarTodo(String tabla) throws SQLException {
        String z = "DELETE FROM " + tabla + ";";
        cons.agregar(z);
        return sta.executeUpdate(z);
    }

    public int AgregarColumna(String datos, String tabla) throws SQLException {
        String z = "ALTER TABLE " + tabla + " ADD(" + datos + ");";
        cons.agregar(z);
        return sta.executeUpdate(z);
    }

    public int BorrarColumna(String tabla, String columna) throws SQLException {
        String z = "ALTER TABLE " + tabla + " DROP " + columna + ";";
        cons.agregar(z);
        return sta.executeUpdate(z);
    }

    public int CrearLlavePrimaria(String tabla, String columna) throws SQLException {
        String z = "ALTER TABLE " + tabla + " ADD PRIMARY KEY (" + columna + ");";
        cons.agregar(z);
        return sta.executeUpdate(z);
    }

    public int CrearLlaveForanea(String tabla, String atri, String tabla_ref, String atri_ref) throws SQLException {
        String z = "ALTER TABLE " + tabla + " ADD FOREIGN KEY(" + atri
                + ") REFERENCES " + tabla_ref + "(" + atri_ref + ");";
//        cons.agregar(z);
        return sta.executeUpdate(z);
    }
}
