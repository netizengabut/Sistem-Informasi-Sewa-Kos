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
public class form_tagihan extends javax.swing.JFrame {
private DefaultTableModel model;
    String no_transaksi, id_penyewa, nama_penyewa, no_kamar, type_kamar, bulan, tgl_bayar;
    double harga_kamar;
    
     public void loadData(){
        no_transaksi = tno.getText();
        id_penyewa = tip.getText();
        nama_penyewa = tnm.getText();
        no_kamar = tkmr.getText();
        type_kamar = ttype.getText();
        harga_kamar = Double.parseDouble(thrg.getText());
        bulan = (String) cbln.getSelectedItem();
        tgl_bayar = jtgl.getDateFormatString();
     }
    /**
     * Creates new form form_tagihan
     */
    public form_tagihan() {
        initComponents();
        
        
          //Memberi penamaan pada judul kolom transaksi
        model = new DefaultTableModel();
       tagihan.setModel(model);
        model.addColumn("no_transaksi");
        model.addColumn("id_penyewa");
        model.addColumn("nama_penyewa");
        model.addColumn("no_kamar");
        model.addColumn("type_kamar");
        model.addColumn("harga_kamar");
        model.addColumn("bulan");
        model.addColumn("tgl_bayar");
        getData();
    }
    
       public void getData(){
        //menghapus isi table transaksi
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            //membuat pemanggilan data pada table table transaksi dari database
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select *from tagihan";
            ResultSet res = stat.executeQuery(sql);
            
            // penelusuran baris pada table penyewa dari database
            while(res.next()) {
                Object[] obj = new Object[8];
                obj[0] = res.getString("no_transaksi");
                obj[1] = res.getString("id_penyewa");
                obj[2] = res.getString("nama_penyewa");
                obj[3] = res.getString("no_kamar");
                obj[4] = res.getString("type_kamar");
                obj[5] = res.getString("harga_kamar");
                obj[6] = res.getString("bulan");
                obj[7] = res.getString("tgl_bayar");
                model.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());   
        }
       }
       
        public void datapenyewa(){   
        try{
            //tes koneksi
            java.sql.Statement stat = (java.sql.Statement) db_koneksi.getKoneksi().createStatement();
           
            //perintah sql untuk membaca data dari tabel produk
            String sql = "SELECT * FROM penyewa WHERE id_penyewa = '"+ tip.getText() +"'";
            ResultSet res = stat.executeQuery(sql);
                        
            //baca data dan tampilkan pada text produk dan harga
            while(res.next()){
                //membuat obyek berjenis array
               tnm.setText(res.getString("nama_penyewa"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    } 
        
        public void datakamar(){   
        try{
            //tes koneksi
            java.sql.Statement stat = (java.sql.Statement) db_koneksi.getKoneksi().createStatement();
           
            //perintah sql untuk membaca data dari tabel produk
            String sql = "SELECT * FROM kamar WHERE no_kamar  = '"+ tkmr.getText() +"'";
            ResultSet res = stat.executeQuery(sql);
                        
            //baca data dan tampilkan pada text produk dan harga
            while(res.next()){
                //membuat obyek berjenis array
               ttype.setText(res.getString("type_kamar"));
               thrg.setText(res.getString("harga_kamar"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    } 
        
         public void saveData() {
            loadData();
            String tampilan = "yyyy-MM-dd";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tgl_bayar = String.valueOf(fm.format(jtgl.getDate()));
        try{
             Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
             String sql = "Insert into tagihan (no_transaksi, id_penyewa, nama_penyewa, no_kamar, type_kamar, harga_kamar, bulan, tgl_bayar)" +"values ('"+no_transaksi+"','"+id_penyewa+"','"+nama_penyewa+"','"+no_kamar+"','"+type_kamar+"','"+harga_kamar+"','"+bulan+"','"+tgl_bayar+"')";
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
            tgl_bayar ="";
            tno.setText(no_transaksi);
            tip.setText("");
            tnm.setText("");
            tkmr.setText("");
            ttype.setText("");
            thrg.setText("");
            jtgl.setDateFormatString(tgl_bayar);  
        }
         
         public void dataSelect(){
        //deklarasi variabel
        int i = tagihan.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        tno.setText(""+model.getValueAt(i,0));
        tip.setText(""+model.getValueAt(i,1));
        tnm.setText(""+model.getValueAt(i,2));
        tkmr.setText(""+model.getValueAt(i,3));
        ttype.setText(""+model.getValueAt(i,4));
        thrg.setText(""+model.getValueAt(i,5));
        cbln.setSelectedItem(""+model.getValueAt(i,6));
        jtgl.setDateFormatString(""+model.getValueAt(i,7));
    }
         
         public void updateData(){
          //panggil fungsi load data
        loadData();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String sql  =   "UPDATE tagihan SET no_transaksi = '"+ no_transaksi  +"',"
                            + "id_penyewa  = '"+ id_penyewa +"',"
                            + "nama_penyewa  = '"+ nama_penyewa +"',"
                            + "no_kamar  = '"+ no_kamar +"',"
                            + "type_kamar  = '"+ type_kamar +"',"
                            + "harga_kamar  = '"+ harga_kamar +"'"
                            + "bulan  = '"+ bulan +"'"
                            + "tgl_bayar  = '"+ tgl_bayar +"' WHERE no_transaksi = '" + no_transaksi +"'";
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
                String sql = "DELETE FROM tagihan WHERE no_transaksi ='"+ no_transaksi +"'";
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
        tno = new javax.swing.JTextField();
        tip = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tnm = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tkmr = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        ttype = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        thrg = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbln = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jtgl = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tagihan = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setFont(new java.awt.Font("Perpetua", 3, 24)); // NOI18N
        jLabel1.setText("SEWA KOS CINDERELLA");

        jLabel2.setFont(new java.awt.Font("Perpetua", 3, 18)); // NOI18N
        jLabel2.setText("Jl. Itaewon no.05 Seoul");

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel3.setText("FORM TAGIHAN BULANAN KOS\n");

        jLabel4.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel4.setText("NO. TRANSAKSI");

        jLabel5.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel5.setText("ID PENYEWA");

        tno.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N

        tip.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        tip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tipKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel6.setText("NAMA PENYEWA");

        tnm.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel11.setText("NO KAMAR");

        tkmr.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        tkmr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tkmrKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel10.setText("TYPE KAMAR");

        ttype.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel8.setText("HARGA KAMAR");

        thrg.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel7.setText("BULAN");

        cbln.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        cbln.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-pilih-", "JANUARI", "FEBRUARI", "MARET", "APRIL", "MEI", "JUNI", "JULI", "AGUSTUS", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DESEMBER" }));

        jLabel9.setFont(new java.awt.Font("Rockwell", 3, 14)); // NOI18N
        jLabel9.setText("TANGGAL BAYAR");

        jButton1.setBackground(new java.awt.Color(255, 102, 102));
        jButton1.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 102, 102));
        jButton3.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        jButton3.setText("UPDATE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 102, 102));
        jButton2.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        jButton2.setText("RESET");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 102, 102));
        jButton5.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        jButton5.setText("CLOSE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 102, 102));
        jButton4.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        jButton4.setText("DELETE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tagihan.setModel(new javax.swing.table.DefaultTableModel(
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
        tagihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tagihanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tagihan);

        jButton6.setBackground(new java.awt.Color(255, 102, 102));
        jButton6.setFont(new java.awt.Font("Poor Richard", 3, 14)); // NOI18N
        jButton6.setText("CETAK LAPORAN");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(360, 360, 360))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jtgl, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel11)
                                                    .addComponent(jLabel5))
                                                .addGap(26, 26, 26))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(31, 31, 31)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tno, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ttype, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                            .addComponent(tkmr)
                                            .addComponent(tnm)
                                            .addComponent(tip)))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbln, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(thrg, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(106, 106, 106)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton6)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 215, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(tnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jButton2)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(tkmr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(ttype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(thrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(cbln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
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
                .addGap(0, 65, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        updateData();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tagihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tagihanMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_tagihanMouseClicked

    private void tipKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipKeyReleased
        // TODO add your handling code here:
        datapenyewa();
    }//GEN-LAST:event_tipKeyReleased

    private void tkmrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tkmrKeyReleased
        // TODO add your handling code here:
        datakamar();
    }//GEN-LAST:event_tkmrKeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try{
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("laporan_tagihan.jasper"), null,db_koneksi.getKoneksi());
            JasperViewer.viewReport(jp, false);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(form_tagihan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_tagihan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_tagihan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_tagihan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_tagihan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbln;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTable tagihan;
    private javax.swing.JTextField thrg;
    private javax.swing.JTextField tip;
    private javax.swing.JTextField tkmr;
    private javax.swing.JTextField tnm;
    private javax.swing.JTextField tno;
    private javax.swing.JTextField ttype;
    // End of variables declaration//GEN-END:variables
}
