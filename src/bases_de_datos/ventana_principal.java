package bases_de_datos;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class ventana_principal extends javax.swing.JFrame implements KeyListener {

    inicio ini;
    ventana_tabla vbd;
    private final conexion x3;
    private DefaultTreeModel modelo_arbol;
    private DefaultMutableTreeNode raiz;
    private DefaultTableModel model_bd;
    private JPopupMenu menu2, menu1, menu3;
    private Vector<DefaultMutableTreeNode> nodos;

    public ventana_principal(conexion x3, inicio ini) {
        this.ini = ini;
        this.x3 = x3;
        initComponents();
        cargar();

        menu1 = new JPopupMenu();
        menu2 = new JPopupMenu();
        menu3 = new JPopupMenu();
        JMenuItem crearBD = new JMenuItem("Crear Base de Datos");
        crearBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String h = JOptionPane.showInputDialog(null, "Inserte nombre");
                if (h == null) {
                    return;
                }
                try {
                    x3.CrearDataBase(h);
                    cargar();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear\nla base de datos", "Error", 0);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                }
            }
        });
        JMenuItem agregar = new JMenuItem("crear tabla");
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreePath path = jTree1.getSelectionPath();
                if (path.getPathCount() == 2) {
                    String h = JOptionPane.showInputDialog(null, "nombre de la tabla");
                    if (h == null) {
                        return;
                    }

                    try {
                        String h2 = path.getPathComponent(1).toString();
                        x3.SelectDataBase(h2);
                        x3.CrearTabla(h);
                        for (DefaultMutableTreeNode nodo : nodos) {
                            if (nodo.toString().equals(h2)) {
                                modelo_arbol.insertNodeInto(new DefaultMutableTreeNode(h), nodo, 0);
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al crear tabla", "Error", 0);
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                    }
                }
            }
        });
        JMenuItem borrar = new JMenuItem("borrar tabla");
        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {

                TreePath path = jTree1.getSelectionPath();
                if (path.getPathCount() == 3) {
                    if (JOptionPane.showConfirmDialog(null, "Seguro que quiere\nborrar la tabla")
                            != JOptionPane.YES_OPTION) {
                        return;
                    }
                    try {
                        System.out.println("base de datos " + path.getPathComponent(1).toString());
                        System.out.println("tabla " + path.getPathComponent(2).toString());
                        x3.SelectDataBase(path.getPathComponent(1).toString());
                        x3.BorrarTabla(path.getPathComponent(2).toString());
                        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (path.getLastPathComponent());
                        modelo_arbol.removeNodeFromParent(currentNode);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al borrar tabla", "Error", 0);
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un\nerror inesperado", "Error", 0);
                    }
                }
            }
        });
        JMenuItem borrarBD = new JMenuItem("borrar Base de Datos");
        borrarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreePath path = jTree1.getSelectionPath();
                String bd = path.getLastPathComponent().toString();
                int q = JOptionPane.showConfirmDialog(null, "Seguro que la desea borrar");

                if (q == JOptionPane.YES_OPTION) {
                    int q2 = JOptionPane.showConfirmDialog(null, "Segurisimo que desea borrar\nla base de datos\n" + bd);
                    if (q2 == JOptionPane.YES_OPTION) {
                        try {
                            x3.BorrarDataBase(bd);
                            cargar();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al borrar\nbase de datos", "Error", 0);
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
                        }
                    }
                }
            }
        });
        menu1.add(crearBD);
        menu2.add(agregar);
        menu2.add(borrarBD);
        menu3.add(borrar);

        eventojtree();
        jButton1.addKeyListener(this);
        jButton2.addKeyListener(this);
        jTree1.addKeyListener(this);

        setTitle("Bases de datos");
        setLocationRelativeTo(null);
    }

    public void cargar() {
        try {
            raiz = new DefaultMutableTreeNode("Bases de Datos");
            modelo_arbol = new DefaultTreeModel(raiz);
            jTree1.setModel(modelo_arbol);

            nodos = new Vector<>();
            ResultSet res = x3.GetDataBases();
            String h;
            int pos = 0, pos2;

            while (res.next()) {
                h = res.getString(1);
                DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(h);
                modelo_arbol.insertNodeInto(nuevo, raiz, pos);
                nodos.add(nuevo);
                pos++;
            }

            for (int i = 0; i < nodos.size(); i++) {
                x3.SelectDataBase(nodos.get(i).toString());
                ResultSet res2 = x3.GetTables();
                pos2 = 0;
                System.out.println("tabla " + nodos.get(i).toString());
                while (res2.next()) {
                    String h2 = res2.getString("Tables_in_" + nodos.get(i).toString());
                    modelo_arbol.insertNodeInto(new DefaultMutableTreeNode(h2), nodos.get(i), pos2);
                    pos2++;
                }
            }

            model_bd = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model_bd.addColumn("Bases de datos");
            model_bd.addColumn("Longitud en MB");
            model_bd.addColumn("Numero de tablas");
            ResultSet res2 = x3.TamanioDataBases();
            while (res2.next()) {
                Vector<String> aux = new Vector<>();
                aux.add(res2.getString(1));
                aux.add(res2.getString(2));
                aux.add(res2.getString(3));
                model_bd.addRow(aux);
            }
            Tabla_BD.setModel(model_bd);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos", "Error", 0);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        }
    }

    private void eventojtree() {
        jTree1.addMouseListener(new java.awt.event.MouseListener() {
            public void mouseClicked(MouseEvent me) {
                TreePath tp = jTree1.getPathForLocation(me.getX(), me.getY());
                if (tp == null) {
                    return;
                }

                if (me.getButton() == MouseEvent.BUTTON3) {
                    switch (tp.getPathCount()) {
                        case 1:
                            menu1.show(me.getComponent(), me.getX(), me.getY());
                            break;
                        case 2:
                            menu2.show(me.getComponent(), me.getX(), me.getY());
                            break;
                        case 3:
                            menu3.show(me.getComponent(), me.getX(), me.getY());
                            break;
                    }
                    return;
                }

                if (me.getClickCount() == 1 || me.getClickCount() > 2) {
                    return;
                }

                if (tp.getPathCount() == 3) {
                    llamar_ventana_tabla(tp);
                }
            }

            public void mousePressed(MouseEvent me) {
            }

            public void mouseReleased(MouseEvent me) {
            }

            public void mouseEntered(MouseEvent me) {
            }

            public void mouseExited(MouseEvent me) {
            }
        });
    }

    private void llamar_ventana_tabla(TreePath treepath) {
        try {
            String q = treepath.getPathComponent(1).toString();
            Vector<String> aux = new Vector<>();

            x3.SelectDataBase(q);
            ResultSet res2 = x3.GetTables();
            while (res2.next()) {
                String h2 = res2.getString("Tables_in_" + q);
                aux.add(h2);
            }

            vbd = new ventana_tabla(x3, ventana_principal.this, treepath.getLastPathComponent().toString(),
                    aux);
            setVisible(false);
            vbd.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos", "Error", 0);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }
    }

    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla_BD = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton2.setText("salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jTree1);

        Tabla_BD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(Tabla_BD);

        jLabel2.setText("Informacion");

        jButton1.setText("Recargar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("consola");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 355, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(101, 101, 101)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1)
                            .addComponent(jButton3)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
        x3.desconectar();
        ini.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setCursor(Cursor.WAIT_CURSOR);
        cargar();
        setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        x3.cons.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(ventana_principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventana_principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventana_principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventana_principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new ventana_bd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla_BD;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getComponent() == jButton1) {
                jButton1ActionPerformed(null);
                return;
            }
            if (e.getComponent() == jButton2) {
                jButton2ActionPerformed(null);
                return;
            }
            if (e.getComponent() == jTree1) {
                TreePath p = jTree1.getSelectionPath();
                if (p.getPathCount() == 3) {
                    llamar_ventana_tabla(p);
                } else {
                    if (jTree1.isExpanded(p)) {
                        jTree1.collapsePath(p);
                    } else {
                        jTree1.expandPath(p);
                    }
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_F2) {
            x3.cons.setVisible(true);
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
