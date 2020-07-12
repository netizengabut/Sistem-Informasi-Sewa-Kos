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
public class form_penyewa extends javax.swing.JFrame {
//membuat clas DefaultTableModel
    private DefaultTableModel model;
    String id_penyewa, nama_penyewa, jenis_kelamin,pekerjaan,no_telp;
    
    public void loadData(){
        id_penyewa = tid.getText();
        nama_penyewa = tnama.getText();
        jenis_kelamin = (String) jComboBox1.getSelectedItem();
        pekerjaan = txtp.getText();
        no_telp = telp.getText();  
    }
    

    /**
     * Creates new form form_penyewa
     */
    public form_penyewa() {
        initComponents();
        
        //Memberi penamaan pada judul kolom penyewa
        model = new DefaultTableModel();
       penyewa.setModel(model);
        model.addColumn("id_penyewa");
        model.addColumn("nama_penyewa");
        model.addColumn("jenis_kelamin");
        model.addColumn("pekerjaan");
        model.addColumn("no_telp");
        
        getData();

    }
    
    public void getData(){
        //menghapus isi table penyewa
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            //membuat pemanggilan data pada table table penyewa dari database
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select *from penyewa";
            ResultSet res = stat.executeQuery(sql);
            
            // penelusuran baris pada table penyewa dari database
            while(res.next()) {
                Object[] obj = new Object[5];
                obj[0] = res.getString("id_penyewa");
                obj[1] = res.getString("nama_penyewa");
                obj[2] = res.getString("jenis_kelamin");
                obj[3] = res.getString("pekerjaan");
                obj[4] = res.getString("no_telp");
                
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
             String sql = "Insert into penyewa(id_penyewa, nama_penyewa,jenis_kelamin,pekerjaan, no_telp)" +"values ('"+id_penyewa+"','"+nama_penyewa+"','"+jenis_kelamin+"','"+pekerjaan+"','"+no_telp+"')";
           db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
           getData();
        }catch(SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
        public void Reset() {
            id_penyewa = "";
            nama_penyewa = "";
            jenis_kelamin = "";
            pekerjaan = "";
            no_telp = "";
            tid.setText(id_penyewa);
            tnama.setText(nama_penyewa);
            txtp.setText(pekerjaan);
            telp.setText(no_telp);
        }
    
         public void dataSelect(){
        //deklarasi variabel
        int i = penyewa.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        tid.setText(""+model.getValueAt(i,0));
        tnama.setText(""+model.getValueAt(i,1));
        jComboBox1.setSelectedItem(""+model.getValueAt(i,2));
        txtp.setText(""+model.getValueAt(i,3));
        telp.setText(""+model.getValueAt(i,4));
    }
         
         public void updateData(){
          //panggil fungsi load data
        loadData();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            //perintah sql untuk simpan data
            String sql  =   "UPDATE penyewa SET nama_penyewa = '"+ nama_penyewa  +"',"
                            + "pekerjaan  = '"+ pekerjaan +"',"
                            + "no_telp  = '"+ no_telp +"' WHERE id_penyewa = '" + id_penyewa +"'";
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
        int pesan = JOptionPane.showConfirmDialog(null, "Hapus Data Dengan id_penyewa "+ id_penyewa +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji koneksi
            try{
                //buka koneksi ke database
                Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM penyewa WHERE id_penyewa='"+ id_penyewa +"'";
                 db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
                 
                //fungsi ambil data
                getData();
                
                //fungsi reset data
                Reset();
                JOptionPane.showMessageDialog(null, "Delete Berhasil Horee yey...");
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tid = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        tnama = new javax.swing.JTextField();
        txtp = new javax.swing.JTextField();
        telp = new javax.swing.JTextField();
        btnsv = new javax.swing.JButton();
        btnb = new javax.swing.JButton();
        btnrbh = new javax.swing.JButton();
        btnhps = new javax.swing.JButton();
        btnkl = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        penyewa = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Penyewa Kos");
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setFont(new java.awt.Font("Poor Richard", 3, 27)); // NOI18N
        jLabel1.setText("SEWA KOS CINDERELLA");

        jLabel2.setFont(new java.awt.Font("Perpetua", 3, 20)); // NOI18N
        jLabel2.setText("Jl. Itaewon no.05 Seoul");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("DATA PENYEWA ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel4.setText("ID PENYEWA");

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel5.setText("NAMA PENYEWA");

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel6.setText("JENIS KELAMIN");

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel8.setText("PEKERJAAN");

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel9.setText("NO. TELP");

        tid.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laki - Laki", "Perempuan" }));

        tnama.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N

        txtp.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N

        telp.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N

        btnsv.setBackground(new java.awt.Color(255, 102, 102));
        btnsv.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btnsv.setText("SAVE");
        btnsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsvActionPerformed(evt);
            }
        });

        btnb.setBackground(new java.awt.Color(255, 102, 102));
        btnb.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btnb.setText("RESET");
        btnb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbActionPerformed(evt);
            }
        });

        btnrbh.setBackground(new java.awt.Color(255, 102, 102));
        btnrbh.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btnrbh.setText("UPDATE");
        btnrbh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrbhActionPerformed(evt);
            }
        });

        btnhps.setBackground(new java.awt.Color(255, 102, 102));
        btnhps.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btnhps.setText("DELETE");
        btnhps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhpsActionPerformed(evt);
            }
        });

        btnkl.setBackground(new java.awt.Color(255, 102, 102));
        btnkl.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        btnkl.setText("CLOSE");
        btnkl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnklActionPerformed(evt);
            }
        });

        penyewa.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        penyewa.setModel(new javax.swing.table.DefaultTableModel(
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
        penyewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                penyewaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(penyewa);

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(telp, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtp, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, 186, Short.MAX_VALUE)
                                                .addComponent(tid, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(tnama, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(83, 83, 83)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton1)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(btnsv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnrbh, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                                .addComponent(btnhps, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnkl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(257, 257, 257)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel6))
                            .addComponent(jLabel5)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsv, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnb, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnrbh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnhps, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnkl, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnklActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnklActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnklActionPerformed

    private void btnhpsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhpsActionPerformed
        // TODO add your handling code here:
        deleteData();
        
    }//GEN-LAST:event_btnhpsActionPerformed

    private void btnsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsvActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_btnsvActionPerformed

    private void btnbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_btnbActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void penyewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_penyewaMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_penyewaMouseClicked

    private void btnrbhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrbhActionPerformed
        // TODO add your handling code here:
        updateData();
    }//GEN-LAST:event_btnrbhActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("laporan_penyewa.jasper"), null,db_koneksi.getKoneksi());
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
            java.util.logging.Logger.getLogger(form_penyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_penyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_penyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_penyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_penyewa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnb;
    private javax.swing.JButton btnhps;
    private javax.swing.JButton btnkl;
    private javax.swing.JButton btnrbh;
    private javax.swing.JButton btnsv;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable penyewa;
    private javax.swing.JTextField telp;
    private javax.swing.JTextField tid;
    private javax.swing.JTextField tnama;
    private javax.swing.JTextField txtp;
    // End of variables declaration//GEN-END:variables
}
