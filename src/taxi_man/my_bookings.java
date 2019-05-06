/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxi_man;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lenovo
 */
public class my_bookings extends javax.swing.JFrame {

    /**
     * Creates new form my_bookings
     */
    String c_name;
    public my_bookings(String u) {
        initComponents();
        c_name=u;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("show bookings");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Driver Name", "Vehicle Name", "Vehicle Type", "From", "To", "Driver Contact", "Date", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable2);

        jButton2.setText("back");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(396, 396, 396)
                .addComponent(jButton2)
                .addGap(195, 195, 195)
                .addComponent(jButton1)
                .addContainerGap(363, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        try
        {
            Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","dbms_project","iamharsh");
            String sql="select * from cabs natural join booking where booking.customer='"+c_name+"'";
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            
            DefaultTableModel tm=(DefaultTableModel)jTable2.getModel();
            
            while(rs.next())
            {
                String sql2="select * from vehicle where vehicle.vehicle_name='"+rs.getString("vehicle_name")+"'";
                PreparedStatement ps2=conn.prepareStatement(sql2);
                ResultSet rs2=ps2.executeQuery();
                Object o[]=new Object[8];
                o[0]=rs.getString("driver_name");
                o[1]=rs.getString("vehicle_name");
                while(rs2.next())
                {
                    o[2]=rs2.getString("vehicle_type");
                }
                o[3]=rs.getString("pick_from");
                o[4]=rs.getString("drop_to");
                o[5]=rs.getString("contact");
                o[6]=rs.getString("jdate");
                o[7]=rs.getString("time");
                //Object o[]={rs.getString("driver_name"),rs.getString("vehicle_name"),rs.getString("vehicle_type"),rs.getString("pick_from"),rs.getString("drop_to"),rs.getLong("contact"),rs.getString("jdate"),rs.getString("time")};
                tm.addRow(o);
            }
            conn.close();
            ps.close();
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        Home_Client p=new Home_Client(c_name);
        setVisible(false);
        p.setVisible(true);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        try
        {
            int row=jTable2.getSelectedRow();
            String table_click0=(jTable2.getModel().getValueAt(row, 0).toString());
            String table_click6=(jTable2.getModel().getValueAt(row, 6).toString());
            String table_click7=(jTable2.getModel().getValueAt(row, 7).toString());
            if(JOptionPane.showConfirmDialog(null, "cancel this booking")==0)
            {
                Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","dbms_project","iamharsh");
                String sql1="insert into cancelled_bookings values('"+table_click0+"','"+table_click6+"','"+table_click7+"','"+c_name+"')";
                PreparedStatement ps1=conn.prepareStatement(sql1);
                ps1.executeQuery();
                ps1.close();
                String sql="delete from booking where driver_name='"+table_click0+"'and jdate='"+table_click6+"'and time='"+table_click7+"' and customer='"+c_name+"'";
                PreparedStatement ps=conn.prepareStatement(sql);
                ps.executeQuery();
                conn.close();
                ps.close();
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_jTable2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(my_bookings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(my_bookings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(my_bookings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(my_bookings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new my_bookings(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
