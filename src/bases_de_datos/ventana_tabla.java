package bases_de_datos;

//import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class ventana_tabla extends javax.swing.JFrame {

    private final String tabla;
    private final conexion x;
    private DefaultTableModel modelo, model_col;
    ventana_bd ven_bd;
    private Vector<String> col;
    private Vector<Vector<String>> datos;
    private boolean inserta;

    public ventana_tabla(conexion x2, ventana_bd vbd, String t) {
        x = x2;
        ven_bd = vbd;
        tabla = t;
        inserta = false;
        initComponents();
        setResizable(false);
        modelo = new DefaultTableModel();
        model_col = new DefaultTableModel();
        datos = new Vector<>();
        cargar();
        EventoJtable(modelo);
        setLocationRelativeTo(null);
    }

    public void cargar() {
        try {
            ResultSet rs = x.GetColumnas(tabla);
            col = new Vector<>();
            Vector<String> aux;
            model_col.addColumn("Field");
            model_col.addColumn("Type");
            model_col.addColumn("Null");
            model_col.addColumn("Key");
            model_col.addColumn("Extra");

            while (rs.next()) {
                aux = new Vector<>();
                String field = rs.getString("Field");
                aux.add(field);
                aux.add(rs.getString("type"));
                aux.add(rs.getString("null"));
                aux.add(rs.getString("key"));
                aux.add(rs.getString("extra"));

                model_col.addRow(aux);
                col.add(field);
                modelo.addColumn(field);
                jcombobox_columnas.addItem(field);
            }
            jTable1.setModel(modelo);
            tabla_columnas.setModel(model_col);

            ResultSet res = x.GetDatos(tabla);
            int q = 0;

            while (res.next()) {
                datos.add(new Vector<>());
                for (int i = 0; i < col.size(); i++) {
                    datos.get(q).add(res.getString(i + 1));
                }
                modelo.addRow(datos.get(q));
                q++;
            }

//            datos.stream().forEach((dato) -> {
//                System.out.println(dato.toString());
//            });
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            JOptionPane.showMessageDialog(null, "Error al cargar datos", "Error", 0);
        }
    }

    private void EventoJtable(DefaultTableModel m) {
        m.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent tme) {
                if (inserta) {
                    return;
                }
                try {
                    int fila = tme.getFirstRow(), columna = tme.getColumn();
                    System.out.println("columana modificada " + modelo.getColumnName(columna));
                    String comando = "SET " + modelo.getColumnName(columna) + " = '"
                            + modelo.getValueAt(fila, columna) + "' WHERE ";

                    for (int i = 0; i < modelo.getColumnCount(); i++) {
                        if (i != columna) {
                            comando += modelo.getColumnName(i) + " = '" + modelo.getValueAt(fila, i) + "' AND ";
                        }
                    }
                    comando = comando.substring(0, comando.length() - 5) + ";";

                    x.Actualizar(tabla, comando);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar los datos", "Error", 0);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un\nerror inesperado", "Error", 0);
                    m.setValueAt(datos.get(tme.getFirstRow()).get(tme.getColumn()), tme.getFirstRow(), tme.getColumn());
                }
            }
        });
    }

//    private void setEventoMouseClicked(JTable tbl) {
//        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                editar_tabla(e);
//            }
//        });
//    }
//    private void editar_tabla(java.awt.event.MouseEvent evt) {
//        String cadena = "";
//
//        if (evt.getClickCount() == 1) {
//            return;
//        }
//        System.out.println("doble click");
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_columnas = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        registro = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jcombobox_columnas = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        borrar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabla_columnas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabla_columnas);

        jButton4.setText("Crear columna");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jButton4)))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Editar", jPanel2);

        jLabel1.setText("agregar nueva tupla");

        jButton1.setText("registrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("ingrese los datos separados por comas");

        registro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registroActionPerformed(evt);
            }
        });
        registro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                registroKeyTyped(evt);
            }
        });

        jcombobox_columnas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---------------" }));

        jButton2.setText("borrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Borrar por:");

        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarActionPerformed(evt);
            }
        });

        jLabel4.setText("ingrese el dato");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jButton3.setText("borrar todo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(registro, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jcombobox_columnas, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(borrar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(202, 202, 202))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(registro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(50, 50, 50)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jcombobox_columnas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(borrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 108, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Datos", jPanel1);

        jButton5.setText("salir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registroActionPerformed

    }//GEN-LAST:event_registroActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        inserta = true;
        String h[] = registro.getText().split(",");
        StringBuilder str = new StringBuilder("'");
        for (int i = 0; i < h.length - 1; i++) {
            h[i] = h[i].trim();
            str.append(h[i]).append("','");
        }
        h[h.length - 1] = h[h.length - 1].trim();
        str.append(h[h.length - 1]).append("'");
        try {
            x.agregar(str.toString(), tabla);
            modelo.addRow(h);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar\nverifique los datos", "Error", 0);
        }
        inserta = false;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void registroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_registroKeyTyped
//        char tecla = evt.getKeyChar();
//        if (tecla < 0 || tecla > 9) {
//            evt.consume();
//        }
    }//GEN-LAST:event_registroKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int index = jcombobox_columnas.getSelectedIndex();
        if (index == 0) {
            return;
        }
        inserta = true;

        try {
            String h = col.get(index - 1);
            x.borrar(h, borrar.getText(), tabla);

            for (int i = 0; i < modelo.getColumnCount(); i++) {
                if (h.equals(modelo.getColumnName(i))) {

                    for (int j = 0; j < modelo.getRowCount(); j++) {
                        if (modelo.getValueAt(j, i).toString().equals(borrar.getText())) {
                            modelo.removeRow(j);
                            j--;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar\nverifique los datos", "Error", 0);
        }
        inserta = false;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int opcion = JOptionPane.showConfirmDialog(null, "Seguro que sea borrar la\ninformacion de la tabla");
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        
        inserta = true;
        try {
            x.borrarTodo(tabla);
            while (modelo.getRowCount() > 0) {
                modelo.removeRow(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ventana_tabla.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error borrar todo", "Error", 0);
        }
        inserta = false;
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ven_bd.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JOptionPane.showMessageDialog(null, "aun no disponible");
    }//GEN-LAST:event_jButton4ActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventana_tabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventana_tabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventana_tabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventana_tabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField borrar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox jcombobox_columnas;
    private javax.swing.JTextField registro;
    private javax.swing.JTable tabla_columnas;
    // End of variables declaration//GEN-END:variables
}
