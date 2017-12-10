package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class operaciones {

    private conexion con;

    public operaciones() {
    }

    public void setCon(conexion con) {
        this.con = con;
    }

    public conexion getConexion() {
        return con;
    }

    public void mostrarConsola() {
        con.cons.setVisible(true);
    }

    public Vector<String> getTablesDataBase(String dataBase) throws SQLException {
        Vector<String> aux = new Vector<>();
        con.SelectDataBase(dataBase);
        ResultSet res2 = con.GetTables();
        while (res2.next()) {
            String h2 = res2.getString("Tables_in_" + dataBase);
            aux.add(h2);
        }
        return aux;
    }

    public void llenarJtable(String tabla , DefaultTableModel modeloJtable, 
            Vector<String> columnas) throws SQLException {
        
        ResultSet res = con.GetDatos(tabla);
        while (res.next()) {
            Vector<String> datos = new Vector<>();
            for (int i = 0; i < columnas.size(); i++) {
                datos.add(res.getString(columnas.get(i)));
            }
            modeloJtable.addRow(datos);
        }
    }
}
