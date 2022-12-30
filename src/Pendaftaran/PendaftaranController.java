 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pendaftaran;

import inc.config;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Lenovo
 */
public class PendaftaranController {
    
    private final Connection conn = new config().Conn();
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel tabMode;
    
    private String kode_oto(){
        String kode = "";
        try {
            int kodelama;
            pst = conn.prepareStatement("select id from pendaftaran order by id desc limit 1");
            rs = pst.executeQuery();
            if (!rs.next()){
                kode = "MHS-0001";
            } else {
                kodelama = Integer.parseInt(rs.getString(1).substring(4))+1;
                if (kodelama < 10 ){
                    kode = "MHS-000" +kodelama;
                } else if (kodelama >= 10 && kodelama < 100){
                    kode = "MHS-00" +kodelama;
                } else if (kodelama >= 100 && kodelama < 1000){
                    kode = "MHS-0" +kodelama;
                } else {
                    kode = "MHS" +kodelama;
                }
            }
        } catch (Exception e) {
            System.out.println("Error Di id oto :" +e.toString());
        }
        return kode;
    }

    public String validasi (PendaftaranView v){
        
        String ket = null;
        Date tgl = v.tanggal.getDate();
        
        if ( tgl == null){
            ket = "Tanggal Belum Diisi";
            
        } else if (v.tNrp.getText().equals("") || v.tNama.getText().equals("") || v.tno.getText().equals("") || v.tEmail.getText().equals("")
                || v.tFakultas.getText().equals("")){
            ket = "Data Tidak Boleh Kosong";
            
        } else if (v.cMateri.getSelectedItem().equals("") || v.cKelas.getSelectedItem().equals("")){
            
            ket = "Data Tidak Boleh Kosong";
            
        } else {           
            ket = "Sukses";
        }
        return ket;
        
    }
    
    public void tampil (PendaftaranView v){
        
        Object [] baris = {"Id Mahasiswa", "Tanggal", "Nama Mahasiswa", "No Telephone", "Email", "Instasi", "Materi", "Kelas"};
        tabMode = new DefaultTableModel(null, baris);
        v.table.setModel(tabMode);        
        String sql = "select * from pendaftaran";
        
        try {
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while (rs.next()){
                
                String id = rs.getString("id");
                String tanggal = rs.getString("tanggal");
                String nama = rs.getString("nama");
                String notel = rs.getString("no_telp");
                String email = rs.getString("email");
                String fakultas = rs.getString("fakultas");
                String materi = rs.getString("materi");
                String kelas = rs.getString("kelas");
                String [] data = {id, tanggal, nama, notel, email, fakultas, materi,kelas};
                tabMode.addRow(data);
                
            }
        } catch (Exception e) {
        }
    }
    
    public void reset(PendaftaranView v){
        
        v.tNrp.setText(kode_oto());
        v.tanggal.setDate(null);
        v.tNama.setText(null);
        v.tno.setText(null);
        v.tEmail.setText(null);
        v.tFakultas.setText(null);
        v.cMateri.setSelectedItem(null);
        v.cKelas.setSelectedItem(null);
        v.tNrp.setEnabled(false);
        
    }

    public void simpan (PendaftaranView v){
        
        if (validasi(v).equals("Sukses")){
            
            try {
                
                int sim;
                Date d = v.tanggal.getDate();
                java.sql.Date tgl = new java.sql.Date(d.getTime());
                String sql = "insert into pendaftaran (id, tanggal, nama, no_telp, email, fakultas, materi, kelas) values (?, ?, ?, ?, ?, ?, ?, ?)";
                
                pst = conn.prepareStatement(sql);
                pst.setString(1, v.tNrp.getText());
                pst.setString(2, tgl.toString());
                pst.setString(3, v.tNama.getText());
                pst.setString(4, v.tno.getText());
                pst.setString(5, v.tEmail.getText());
                pst.setString(6, v.tFakultas.getText());
                pst.setString(7, v.cMateri.getSelectedItem().toString());
                pst.setString(8, v.cKelas.getSelectedItem().toString());
                
                sim = pst.executeUpdate();
                if ( sim == 1){
                    
                        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan", "Informasi",JOptionPane.INFORMATION_MESSAGE);
                        reset(v);
                        tampil(v);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Simpan Data", "Informasi",JOptionPane.ERROR_MESSAGE);
                    }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Di proses Simpan Data \nDetail:"+e.toString());
                Logger.getLogger(PendaftaranController.class.getName()).log(Level.SEVERE,null,e);
            }
        } else {
            JOptionPane.showMessageDialog(null, validasi(v), "Kesalahan input data", JOptionPane.ERROR_MESSAGE);
        }
                
            
    }
    

    public void hapus (PendaftaranView v){
        
        int hapus = JOptionPane.showConfirmDialog(null, "Yakin Ingin Menghapus Data?" +v.tNrp.getText()+ "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if ( hapus == JOptionPane.YES_OPTION){
            try {               
                int hap;
                
                String sql = "delete from pendaftaran where id='"+v.tNrp.getText()+"'";
                pst = conn.prepareStatement(sql);
                hap = pst.executeUpdate();
                
                if (hap == 1){
                    
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    reset(v);
                    tampil(v);
                    
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Hapus Data \nDetail:"+e.toString());
                Logger.getLogger(PendaftaranController.class.getName()).log(Level.SEVERE,null,e);
            }
        }
    }
    
    public void ubah (PendaftaranView v){
        
        int ubah = JOptionPane.showConfirmDialog(null, "Yakin Ingin Mengubah Data" + v.tNrp.getText() + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ubah == JOptionPane.YES_OPTION){
            if (validasi(v).equals("Sukses")){
                try {
                    
                    int ub;
                    
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String jdate = format.format(v.tanggal.getDate());
                    
                    pst.setString(1, jdate);
                    pst.setString(2, v.tNama.getText());
                    pst.setString(3, v.tno.getText());
                    pst.setString(4, v.tEmail.getText());
                    pst.setString(5, v.tFakultas.getText());
                    pst.setString(6, v.cMateri.getSelectedItem().toString());
                    pst.setString(7, v.cKelas.getSelectedItem().toString());
                    pst.setString(8, v.tNrp.getText());
                    ub = pst.executeUpdate();
                    
                    if ( ub == 1 ){
                        JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                        reset(v);
                        tampil(v);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Hapus Data \nDetail:"+e.toString());
                    Logger.getLogger(PendaftaranController.class.getName()).log(Level.SEVERE,null,e);
                }
            } else {
                JOptionPane.showMessageDialog(null, validasi(v), "Kesalahan input data", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    public void clik (PendaftaranView v){
        
        tabMode = (DefaultTableModel)v.table.getModel();
        int baris = v.table.getSelectedRow();
        
        try {
            Date tgl = new SimpleDateFormat("yyyy-MM-dd").parse((String)tabMode.getValueAt(baris, 1));
            
            v.tNrp.setText(v.table.getValueAt(baris, 0).toString());
            v.tanggal.setDate(tgl);
            v.tNama.setText(v.table.getValueAt(baris, 2).toString());
            v.tno.setText(v.table.getValueAt(baris, 3).toString());
            v.tEmail.setText(v.table.getValueAt(baris, 4).toString());
            v.tFakultas.setText(v.table.getValueAt(baris, 5).toString());
            v.cMateri.setSelectedItem(v.table.getValueAt(baris, 6).toString());
            v.cKelas.setSelectedItem(v.table.getValueAt(baris, 7).toString());
            
            v.tNrp.setEnabled(false);
            v.bDaftar.setEnabled(false);
            v.bUbah.setEnabled(true);
            v.bHapus.setEnabled(true);
        } catch (ParseException ex) {
            System.out.println("detail :" +ex.toString());
            Logger.getLogger(PendaftaranController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cari (PendaftaranView v ){
        
        Object [] baris = {"Id Mahasiswa", "Tanggal", "Nama Mahasiswa", "No Telephone", "Email", "Instasi", "Materi", "Kelas"};
        tabMode = new DefaultTableModel(null, baris);
        v.table.setModel(tabMode);        
        String sql = "select * from pendaftaran where "+v.cCari.getSelectedItem()+" like '%"+v.tCari.getText()+"%' ";
        
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                
                String id = rs.getString("id");
                String tanggal = rs.getString("tanggal");
                String nama = rs.getString("nama");
                String notel = rs.getString("no_telp");
                String email = rs.getString("email");
                String fakultas = rs.getString("fakultas");
                String materi = rs.getString("materi");
                String [] data = {id, tanggal, nama, notel, email, fakultas, materi};
                tabMode.addRow(data);
            }
        } catch (Exception e) {
            System.out.println("Tejadi Kesalahan Pada Tampil Data Barang. \nDetail:"+e.toString());
        }
    }
    
    public void laporan (){
        
        String reportSource = null;
        String reportDest = null;
        
        try{
            java.sql.Connection conn =(java.sql.Connection)config.Conn();    
            reportSource = System.getProperty("user.dir") + "/src/Pendaftaran/LaporanPendaftaran.jrxml";
            reportDest = System.getProperty("user.dir") + "/src/Pendaftaran/LaporanPendaftaran.jasper";
            
            HashMap hash=new HashMap(2);
//            hash.put("tgl1",cbtgl1.getDate());
//            hash.put("tgl2",cbtgl2.getDate());
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash,conn);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);
            JasperViewer.viewReport(jasperPrint,false);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
