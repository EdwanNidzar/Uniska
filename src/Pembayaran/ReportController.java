/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pembayaran;

import Pendaftaran.Rapot;
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
public class ReportController {
    
    private final Connection conn = new config().Conn();
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel tabMode;
    
    public void tampil(RaportView r){
        
        Object [] baris = {"NO Transaksi", "Tanggal" ,"Id Mahasiswa",  "Nama Mahasiswa", "Materi", "Total", "Bayar", "Kembalian"};
        tabMode = new DefaultTableModel(null, baris);
        r.table.setModel(tabMode);        
        String sql = "select * from pembayaran";
        
        try {
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while (rs.next()){
                
                String no = rs.getString("no");
                String tanggal = rs.getString("tanggal");
                String id = rs.getString("id_mahasiswa");
                String nama = rs.getString("nama_mahasiswa");
                String materi = rs.getString("materi");
                String total = rs.getString("total");
                String bayar = rs.getString("bayar");
                String kembalian = rs.getString("kembali");
                String [] data = {no, tanggal, id, nama, materi, total, bayar, kembalian};
                tabMode.addRow(data);
                
            }
        } catch (Exception e) {
        }
    }
    
    public void laporan (RaportView r){
        
        String reportSource = null;
        String reportDest = null;
        
        try{
            java.sql.Connection conn =(java.sql.Connection)config.Conn();    
            reportSource = System.getProperty("user.dir") + "/src/Laporan/LaporanPembayaran.jrxml";
            reportDest = System.getProperty("user.dir") + "/src/Laporan/LaporanPembayaran.jasper";
            
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
