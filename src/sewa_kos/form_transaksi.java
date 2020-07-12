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
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author HP
 */
public class form_transaksi extends javax.swing.JFrame {
//membuat clas DefaultTableModel
    private DefaultTableModel model;
    String no_transaksi, id_penyewa, nama_penyewa, no_kamar, type_kamar,tgl_masuk;
    int harga_kamar;
    
     public void loadData(){
        no_transaksi = txtr.getText();
        id_penyewa = (String) jComboBox1.getSelectedItem();
        nama_penyewa = txtnm.getText();
        no_kamar = (String) jComboBox2.getSelectedItem();
        type_kamar = txtt.getText();
        harga_kamar = Integer.parseInt(txthrg1.getText());  
        tgl_masuk = jtgl.getDateFormatString();
     }
    /**
     * Creates new form form_transaksi
     */
    public form_transaksi() {
        initComponents();
        comboboxPenyewa();
        comboboxKamar();
        
         //Memberi penamaan pada judul kolom transaksi
        model = new DefaultTableModel();
       transaksi.setModel(model);
        model.addColumn("no_transaksi");
        model.addColumn("id_penyewa");
        model.addColumn("nama_penyewa");
        model.addColumn("no_kamar");
        model.addColumn("type_kamar");
        model.addColumn("harga_kamar");
        model.addColumn("tgl_masuk");
        getData();
    }

     public void getData(){
        //menghapus isi table transaksi
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            //membuat pemanggilan data pada table table transaksi dari database
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select *from transaksi";
            ResultSet res = stat.executeQuery(sql);
            
            // penelusuran baris pada table penyewa dari database
            while(res.next()) {
                Object[] obj = new Object[7];
                obj[0] = res.getString("no_transaksi");
                obj[1] = res.getString("id_penyewa");
                obj[2] = res.getString("nama_penyewa");
                obj[3] = res.getString("no_kamar");
                obj[4] = res.getString("type_kamar");
                obj[5] = res.getString("harga_kamar");
                obj[6] = res.getString("tgl_masuk");
                model.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());   
        }
    }
     
     
     public void comboboxPenyewa() {
         jComboBox1.removeAllItems();
         try{
             Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "select *from penyewa";
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()) {
                Object[] obj = new Object[1];
                obj[0] = res.getString("id_penyewa");
                jComboBox1.addItem(obj[0].toString());
         }
             }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());   
        }
     }
   
     public void comboboxKamar() {
         jComboBox2.removeAllItems();
         try{
             Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "select *from kamar";
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()) {
                Object[] obj = new Object[1];
                obj[0] = res.getString("no_kamar");
                jComboBox2.addItem(obj[0].toString());
         }
             }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());   
        }
     }
     
       public void saveData() {
            loadData();
            String tampilan = "yyyy-MM-dd";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tgl_masuk = String.valueOf(fm.format(jtgl.getDate()));
        try{
             Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
             String sql = "Insert into transaksi (no_transaksi, id_penyewa, nama_penyewa,no_kamar, type_kamar,harga_kamar,tgl_masuk)" +"values ('"+no_transaksi+"','"+id_penyewa+"','"+nama_penyewa+"','"+no_kamar+"','"+type_kamar+"','"+harga_kamar+"','"+tgl_masuk+"')";
           db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
           getData();
        }catch(SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
       
        public void Reset() {
            no_transaksi = "";
            id_penyewa = "";
            nama_penyewa = "";
            no_kamar = "";
            type_kamar = "";
            harga_kamar = 0;
            tgl_masuk ="";
            txtr.setText(no_transaksi);
            txtnm.setText("");
            txtt.setText("");
            txthrg1.setText("");
            jtgl.setDateFormatString(tgl_masuk);  
        }
        
          public void dataSelect(){
        //deklarasi variabel
        int i = transaksi.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        txtr.setText(""+model.getValueAt(i,0));
        jComboBox1.setSelectedItem(""+model.getValueAt(i,1));
        txtnm.setText(""+model.getValueAt(i,2));
        jComboBox2.setSelectedItem(""+model.getValueAt(i,3));
        txtt.setText(""+model.getValueAt(i,4));
        txthrg1.setText(""+model.getValueAt(i,5));
        jtgl.setDateFormatString(""+model.getValueAt(i,6));
    }
          
           public void updateData(){
          //panggil fungsi load data
        loadData();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String sql  =   "UPDATE transaksi SET no_transaksi = '"+ no_transaksi  +"',"
                            + "id_penyewa  = '"+ id_penyewa +"',"
                            + "nama_penyewa  = '"+ nama_penyewa +"',"
                            + "no_kamar  = '"+ no_kamar +"',"
                            + "type_kamar  = '"+ type_kamar +"',"
                            + "harga_kamar  = '"+ harga_kamar +"'"
                            + "tgl_masuk  = '"+ tgl_masuk +"' WHERE no_transaksi = '" + no_transaksi +"'";
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
        int pesan = JOptionPane.showConfirmDialog(null, "Hapus Data Dengan no_transaksi "+ no_transaksi +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji koneksi
            try{
                //buka koneksi ke database
                Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM transaksi WHERE no_transaksi ='"+ no_transaksi +"'";
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
        txtr = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtnm = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        txtt = new javax.swing.JTextField();
        txthrg1 = new javax.swing.JTextField();
        jtgl = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btncl = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        transaksi = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setFont(new java.awt.Font("Perpetua", 3, 30)); // NOI18N
        jLabel1.setText("SEWA KOS CINDERELLA");

        jLabel2.setFont(new java.awt.Font("Perpetua", 3, 18)); // NOI18N
        jLabel2.setText("Jl. Itaewon no.05 Seoul");

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel3.setText("FORM TRANSAKSI PEMBAYARAN KOS");

        jLabel4.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel4.setText("NO. TRANSAKSI");

        txtr.setFont(new java.awt.Font("Rockwell", 3, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel6.setText("NAMA PENYEWA");

        jLabel5.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel5.setText("ID PENYEWA");

        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        txtnm.setFont(new java.awt.Font("Rockwell", 3, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel7.setText("NO. KAMAR");

        jLabel10.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel10.setText("TYPE KAMAR");

        jLabel8.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel8.setText("HARGA KAMAR");

        jLabel9.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel9.setText("TANGGAL MASUK");

        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });

        txtt.setFont(new java.awt.Font("Rockwell", 3, 12)); // NOI18N

        txthrg1.setFont(new java.awt.Font("Rockwell", 3, 12)); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 102, 102));
        jButton1.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 102, 102));
        jButton2.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jButton2.setText("RESET");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 102, 102));
        jButton5.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jButton5.setText("UPDATE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 102, 102));
        jButton3.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jButton3.setText("DELETE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btncl.setBackground(new java.awt.Color(255, 102, 102));
        btncl.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        btncl.setText("CLOSE");
        btncl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclActionPerformed(evt);
            }
        });

        transaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(transaksi);

        jButton4.setBackground(new java.awt.Color(255, 102, 102));
        jButton4.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        jButton4.setText("CETAK LAPORAN");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtnm)
                            .addComponent(txtr)
                            .addComponent(jComboBox1, 0, 183, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txthrg1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jtgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(77, 77, 77))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(238, 238, 238)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(309, 309, 309)
                                .addComponent(jLabel2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jButton4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton5)
                                    .addComponent(jLabel6))
                                .addGap(38, 38, 38)
                                .addComponent(jButton3)
                                .addGap(75, 75, 75)
                                .addComponent(btncl, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(210, 210, 210))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(txthrg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel9))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)))))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btncl)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnclActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        // TODO add your handling code here:
        try{
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "select *from penyewa where id_penyewa = '"+jComboBox1.getSelectedItem().toString()+"'";
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()) {
                Object[] obj = new Object[1];
                obj[0] = res.getString("nama_penyewa");
                txtnm.setText(obj[0].toString());
             }
              }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());   
        }
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        updateData();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_transaksiMouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        // TODO add your handling code here:
        try{
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "select *from kamar where no_kamar = '"+jComboBox2.getSelectedItem().toString()+"'";
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()) {
                Object[] obj = new Object[2];
                obj[0] = res.getString("type_kamar");
                obj[1] = res.getString("harga_kamar");
                txtt.setText(obj[0].toString());
                txthrg1.setText(obj[1].toString());
             }
              }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());   
        }
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try{
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("laporan_transaksi.jasper"), null,db_koneksi.getKoneksi());
            JasperViewer.viewReport(jp, false);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jtgl;
    private javax.swing.JTable transaksi;
    private javax.swing.JTextField txthrg1;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtr;
    private javax.swing.JTextField txtt;
    // End of variables declaration//GEN-END:variables
}
