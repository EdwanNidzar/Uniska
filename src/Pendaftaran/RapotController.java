/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pendaftaran;

import inc.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
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
public class RapotController {
    
    private final Connection conn = new config().Conn();
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel tabMode;
    
    public void tampil (Rapot v){
        
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
    public void laporan (Rapot r){
        
        String reportSource = null;
        String reportDest = null;
        
        try{
            java.sql.Connection conn =(java.sql.Connection)config.Conn();    
            reportSource = System.getProperty("user.dir") + "/src/Laporan/LaporanPendaftaran.jrxml";
            reportDest = System.getProperty("user.dir") + "/src/Laporan/LaporanPendaftaran.jasper";
            
            HashMap hash=new HashMap(2);
            hash.put("Dari",r.dDari.getDate());
            hash.put("Sampai",r.dSampai.getDate());
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash,conn);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);
            JasperViewer.viewReport(jasperPrint,false);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
