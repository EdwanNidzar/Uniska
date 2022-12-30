/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pembayaran.Cari;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import inc.config;
import Pembayaran.PembayaranView;
/**
 *
 * @author Lenovo
 */
public class CariController {
    
    private final Connection conn = new config().Conn();
    PreparedStatement pst ;
    ResultSet rs ;
    DefaultTableModel tabMode;
    
    public void tampil (CariView cv){
        
        Object [] baris = {"Id Mahasiswa", "Tanggal", "Nama Mahasiswa", "No Telephone", "Email", "Instasi", "Materi", "Kelas"};
        tabMode = new DefaultTableModel(null, baris);
        cv.table.setModel(tabMode);        
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
                String [] data = {id, tanggal, nama, notel, email, fakultas, materi, kelas};
                tabMode.addRow(data);
                
            }
        } catch (Exception e) {
            System.out.println("Tejadi Kesalahan Pada Tampil Data Pelaggan. \nDetail:"+e.toString());
        }
    }
    
    public void cari (CariView cv){
        
        Object [] baris = {"Id Mahasiswa", "Tanggal", "Nama Mahasiswa", "No Telephone", "Email", "Instasi", "Materi", "Kelas"};
        tabMode = new DefaultTableModel(null, baris);
        cv.table.setModel(tabMode);        
        String sql = "select * from pendaftaran where nama like '%"+cv.tCari.getText()+"%' ";
        
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
    
    public void clik (CariView cv){
        
       int row = cv.table.getSelectedRow();
       
       PembayaranView.tId.setText(cv.table.getValueAt(row, 0).toString());
       PembayaranView.tTamggal.setText(cv.table.getValueAt(row, 1).toString());
       PembayaranView.tNama.setText(cv.table.getValueAt(row, 2).toString());
       PembayaranView.tNoTelp.setText(cv.table.getValueAt(row, 3).toString());
       PembayaranView.tEmail.setText(cv.table.getValueAt(row, 4).toString());
       PembayaranView.tInstasi.setText(cv.table.getValueAt(row, 5).toString());
       PembayaranView.tMateri.setText(cv.table.getValueAt(row, 6).toString());
       PembayaranView.tKelasdata.setText(cv.table.getValueAt(row, 7).toString());
       
       PembayaranView.tId.setEnabled(false);
       PembayaranView.tTamggal.setEnabled(false);
       PembayaranView.tNama.setEnabled(false);
       PembayaranView.tNoTelp.setEnabled(false);
       PembayaranView.tEmail.setEnabled(false);
       PembayaranView.tInstasi.setEnabled(false);
       PembayaranView.tMateri.setEnabled(false);
       PembayaranView.tKelasdata.setEnabled(false);
       
       PembayaranView.tGrandTotal.setEnabled(false);
       cv.dispose();
    }
}
