/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sewa_kos;
import koneksi.db_koneksi;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author HP
 */
public class form_kamar extends javax.swing.JFrame {
//membuat clas DefaultTableModel
    private DefaultTableModel model;
    String no_kamar, type_kamar, fasilitas;
    int harga_kamar;
    
     public void loadData(){
        no_kamar = txtno.getText();
        type_kamar = (String) jComboBox1.getSelectedItem();
        harga_kamar = Integer.parseInt(txthrg.getText());  
        fasilitas = txtf.getText();
     }
     
     public void loadHarga(){
        type_kamar =""+ jComboBox1.getSelectedItem();
        switch(type_kamar) {
            case "Royal":
                harga_kamar = 400000;
                fasilitas = "Non Ac, Luas Kamar 2x2,5m , Lemari,Free Wifi";
            break;
            case "Premium":
                harga_kamar = 650000;
                fasilitas = " AC, Luas Kamar 4x3m, Lemari, Kamar Mandi Dalam, Free Wifi";
            break;
            case "Deluxe":
                harga_kamar = 800000;
                fasilitas = "AC, Luas Kamar 4x5m, Lemari, Kamar Mandi Dalam, Free Wifi";
        }
        txthrg.setText(""+harga_kamar);
        txtf.setText(""+fasilitas); 
     } 
             
    /**
     * Creates new form form_kamar
     */
    public form_kamar() {
        initComponents();
        
        //Memberi penamaan pada judul kolom kamar
        model = new DefaultTableModel();
       kamar.setModel(model);
        model.addColumn("no_kamar");
        model.addColumn("type_kamar");
        model.addColumn("harga_kamar");
        model.addColumn("fasilitas");
        getData();
    }
    
    public void getData(){
        //menghapus isi table kamar
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            //membuat pemanggilan data pada table table kamar dari database
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select *from kamar";
            ResultSet res = stat.executeQuery(sql);
            
            // penelusuran baris pada table penyewa dari database
            while(res.next()) {
                Object[] obj = new Object[4];
                obj[0] = res.getString("no_kamar");
                obj[1] = res.getString("type_kamar");
                obj[2] = res.getString("harga_kamar");
                obj[3] = res.getString("fasilitas");
                model.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());   
        }
    }
    
     public void saveData() {
            loadData();
        try{
             Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
             String sql = "Insert into kamar(no_kamar, type_kamar,harga_kamar,fasilitas)" +"values ('"+no_kamar+"','"+type_kamar+"','"+harga_kamar+"','"+fasilitas+"')";
           db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
           getData();
        }catch(SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

      public void Reset() {
            no_kamar = "";
            type_kamar = "";
            harga_kamar = 0;
            fasilitas ="";
            txtno.setText(no_kamar);
            txthrg.setText("");
            txtf.setText("");
        }
      
        public void dataSelect(){
        //deklarasi variabel
        int i = kamar.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        txtno.setText(""+model.getValueAt(i,0));
        jComboBox1.setSelectedItem(""+model.getValueAt(i,1));
        txthrg.setText(""+model.getValueAt(i,2));
        txtf.setText(""+model.getValueAt(i,3));
    }
       
         public void updateData(){
          //panggil fungsi load data
        loadData();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String sql  =   "UPDATE kamar SET type_kamar = '"+ type_kamar  +"',"
                            + "harga_kamar = '"+ harga_kamar +"',"
                            + "fasilitas = '"+fasilitas+ "' WHERE no_kamar = '" + no_kamar +"'";
           db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
            
            //ambil data
            getData();
            
            //memanggil class Reset()agar setelah update berhasil data yang terdapat pada komponen- komponen langsung dikosongkan
            Reset();
            JOptionPane.showMessageDialog(null, "Update berhasil...");
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, er.getMessage());
        }
    }
         
         public void deleteData(){
        //panggil fungsi ambil data
        loadData(); 
        
        //Beri peringatan sebelum melakukan penghapusan data
        int pesan = JOptionPane.showConfirmDialog(null, "Hapus data dengan  no_kamar "+ no_kamar+"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji koneksi
            try{
                //buka koneksi ke database
                Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM kamar WHERE no_kamar='"+ no_kamar +"'";
                 db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
                 
                //fungsi ambil data
                getData();
                
                //fungsi reset data
                Reset();
                JOptionPane.showMessageDialog(null, "Delete berhasil horee...");
            }catch(SQLException er){
                JOptionPane.showMessageDialog(null, er.getMessage());
            }
        }
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtno = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        txthrg = new javax.swing.JTextField();
        txtf = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        kamar = new javax.swing.JTable();
        btncl = new javax.swing.JButton();
        btnsv = new javax.swing.JButton();
        btnw = new javax.swing.JButton();
        btnup = new javax.swing.JButton();
        btndlt = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data kamar Kos");
        setBackground(new java.awt.Color(255, 204, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setFont(new java.awt.Font("Perpetua", 3, 36)); // NOI18N
        jLabel1.setText("SEWA KOS CINDERELLA");

        jLabel2.setFont(new java.awt.Font("Perpetua", 3, 24)); // NOI18N
        jLabel2.setText("Jl. Itaewon no.05 Seoul");

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel3.setText("DATA KAMAR KOS");

        jLabel5.setFont(new java.awt.Font("Rockwell", 3, 18)); // NOI18N
        jLabel5.setText("NO. KAMAR");

        jLabel7.setFont(new java.awt.Font("Rockwell", 3, 18)); // NOI18N
        jLabel7.setText("TIPE KAMAR");

        jLabel6.setFont(new java.awt.Font("Rockwell", 3, 18)); // NOI18N
        jLabel6.setText("HARGA KAMAR");

        jLabel8.setFont(new java.awt.Font("Rockwell", 3, 18)); // NOI18N
        jLabel8.setText("FASILITAS");

        txtno.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Royal", "Premium", "Deluxe" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        txthrg.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N

        txtf.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N

        kamar.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        kamar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        kamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(kamar);

        btncl.setBackground(new java.awt.Color(255, 102, 102));
        btncl.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btncl.setText("CLOSE");
        btncl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclActionPerformed(evt);
            }
        });

        btnsv.setBackground(new java.awt.Color(255, 102, 102));
        btnsv.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btnsv.setText("SAVE");
        btnsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsvActionPerformed(evt);
            }
        });

        btnw.setBackground(new java.awt.Color(255, 102, 102));
        btnw.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btnw.setText("RESET");
        btnw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnwActionPerformed(evt);
            }
        });

        btnup.setBackground(new java.awt.Color(255, 102, 102));
        btnup.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btnup.setText("UPDATE");
        btnup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupActionPerformed(evt);
            }
        });

        btndlt.setBackground(new java.awt.Color(255, 102, 102));
        btndlt.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btndlt.setText("DELETE");
        btndlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndltActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 102, 102));
        jButton1.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        jButton1.setText("CETAK LAPORAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txthrg, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtno, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(154, 154, 154))
                    .addComponent(txtf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btncl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btndlt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnup, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnsv, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1))
                .addGap(103, 103, 103))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(268, 268, 268)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txthrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(btnsv)
                        .addGap(18, 18, 18)
                        .addComponent(btnw)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnup)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btndlt)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11)
                        .addComponent(btncl)
                        .addGap(36, 36, 36)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclActionPerformed
this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnclActionPerformed

    private void btnwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnwActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_btnwActionPerformed

    private void kamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamarMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_kamarMouseClicked

    private void btnupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupActionPerformed
        // TODO add your handling code here:
        updateData();
    }//GEN-LAST:event_btnupActionPerformed

    private void btnsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsvActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_btnsvActionPerformed

    private void btndltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndltActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_btndltActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        loadHarga();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("laporan_kamar.jasper"), null,db_koneksi.getKoneksi());
            JasperViewer.viewReport(jp, false);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(form_kamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_kamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_kamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_kamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_kamar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncl;
    private javax.swing.JButton btndlt;
    private javax.swing.JButton btnsv;
    private javax.swing.JButton btnup;
    private javax.swing.JButton btnw;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable kamar;
    private javax.swing.JTextField txtf;
    private javax.swing.JTextField txthrg;
    private javax.swing.JTextField txtno;
    // End of variables declaration//GEN-END:variables
}
