/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Pembayaran;

import inc.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

            /**
             *
             * @author Lenovo
             */
public class PembayaranController {

    private final Connection conn = new config().Conn();
        PreparedStatement pst;
        ResultSet rs;
        DefaultTableModel tabMode;

    private String no_trans_oto (){

        String no = "";
            try {
            int nolama;
            pst = conn.prepareStatement("select no from pembayaran order by no desc limit 1");
            rs = pst.executeQuery();
                if (!rs.next()){
                    no = "NOTA-0001";
                } else {
                    nolama = Integer.parseInt(rs.getString(1).substring(5))+1;
                        if (nolama < 10){
                        no = "NOTA-000" +nolama;
                        } else if (nolama >= 10 && nolama > 100){
                             no = "NOTA-00" +nolama;
                        } else if (nolama >=100 && nolama > 1000){
                             no = "NOTA-0" +nolama;
                        } else {
                            no = "NOTA" +nolama;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error Di id oto :" +e.toString());
            }
        return no;
    }

    public String validasi (PembayaranView pv){

        String ket = null;
        Date tgl = pv.tanggal.getDate();

            if (tgl == null){
                JOptionPane.showMessageDialog(null, "Tanggal Pembayaran tidak boleh kosong", null, JOptionPane.ERROR_MESSAGE);       

            } else if (pv.tNO.getText().equals("") || 
                PembayaranView.tId.getText().equals("") ||
                PembayaranView.tNama.getText().equals("") ||
                PembayaranView.tMateri.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Data Mahasiswa Tidak Boleh Kosong", null, JOptionPane.ERROR_MESSAGE);

            } else if (pv.tBayar.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Biaya Pendaftaran Tidak Boleh Kosong", null, JOptionPane.ERROR_MESSAGE);

            } else {
                ket = "Sukses";
            }
        return ket;
    }

    public void cek (PembayaranView pv){
        try {
        double harga;

        // Untuk menanpilkan varian harga ms offline
        if (PembayaranView.tMateri.getText().equals("MS Offline") && PembayaranView.tKelasdata.getText().equals("Umum (Min 5 Orang)")){

            harga = 120000;
            pv.tTotal.setText(Double.toString(harga));
            
        } else if (PembayaranView.tMateri.getText().equals("MS Offline") && PembayaranView.tKelasdata.getText().equals("Umum (Max 4 Orang)")){
            
            harga = 200000;
            pv.tTotal.setText(Double.toString(harga));
        
        } else if (PembayaranView.tMateri.getText().equals("MS Offline") && PembayaranView.tKelasdata.getText().equals("Private (Max 3 Orang)")){

            harga = 250000;
            pv.tTotal.setText(Double.toString(harga));
        
        } else if (PembayaranView.tMateri.getText().equals("MS Offline") && PembayaranView.tKelasdata.getText().equals("Private (Max 2 Orang)")){
            
            harga = 350000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("MS Offline") && PembayaranView.tKelasdata.getText().equals("Private (Max 1 Orang)")){

            harga = 600000;
            pv.tTotal.setText(Double.toString(harga));

        } 

//            Untuk menampilkan varian harga komputer akutasi
          else if (PembayaranView.tMateri.getText().equals("Komputer Akutansi") && PembayaranView.tKelasdata.getText().equals("Umum (Min 5 Orang)")){

            harga = 150000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Komputer Akutansi") && PembayaranView.tKelasdata.getText().equals("Umum (Min 4 Orang)")){

            harga = 200000;
            pv.tTotal.setText(Double.toString(harga));
            
        } else if (PembayaranView.tMateri.getText().equals("Komputer Akutansi") && PembayaranView.tKelasdata.getText().equals("Private (Max 3 Orang)")){

            harga = 250000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Komputer Akutansi") && PembayaranView.tKelasdata.getText().equals("Private (Max 2 Orang)")){

            harga = 350000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Komputer Akutansi") && PembayaranView.tKelasdata.getText().equals("Private (Max 1 Orang)")){

            harga = 650000;
            pv.tTotal.setText(Double.toString(harga));

        } 
//            Untuk menampikan varian harga komputer statistik
          else if (PembayaranView.tMateri.getText().equals("Komputer Statistik") && PembayaranView.tKelasdata.getText().equals("Umum (Min 5 Orang)")){

            harga = 150000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Komputer Statistik") && PembayaranView.tKelasdata.getText().equals("Umum (Min 4 Orang)")){

            harga = 200000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Komputer Statistik") && PembayaranView.tKelasdata.getText().equals("Private (Max 3 Orang)")){
            
            harga = 250000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Komputer Statistik") && PembayaranView.tKelasdata.getText().equals("Private (Max 2 Orang)")){
            
            harga = 350000;
            pv.tTotal.setText(Double.toString(harga));
        
        } else if (PembayaranView.tMateri.getText().equals("Komputer Statistik") && PembayaranView.tKelasdata.getText().equals("Private (Max 1 Orang)")){

            harga = 650000;
            pv.tTotal.setText(Double.toString(harga));

                        }
//            Untuk menampilkan varian harga desain grafis                  
          else if (PembayaranView.tMateri.getText().equals("Desain Grafis") && PembayaranView.tKelasdata.getText().equals("Umum (Min 5 Orang)")){

            harga = 150000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Desain Grafis") && PembayaranView.tKelasdata.getText().equals("Umum (Min 4 Orang)")){

            harga = 200000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Desain Grafis") && PembayaranView.tKelasdata.getText().equals("Private (Max 3 Orang)")){

            harga = 250000;            
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Desain Grafis") && PembayaranView.tKelasdata.getText().equals("Private (Max 2 Orang)")){
            
            harga = 350000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Desain Grafis") && PembayaranView.tKelasdata.getText().equals("Private (Max 1 Orang)")){

            harga = 650000;
            pv.tTotal.setText(Double.toString(harga));

        } 
//        menampilkan yg web  
        else if (PembayaranView.tMateri.getText().equals("Web Design") && PembayaranView.tKelasdata.getText().equals("Umum (Min 5 Orang)")){
            
            harga = 200000;
            pv.tTotal.setText(Double.toString(harga));
            
        } else if (PembayaranView.tMateri.getText().equals("Web Design") && PembayaranView.tKelasdata.getText().equals("Umum (Min 4 Orang)")){

            harga = 250000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Web Design") && PembayaranView.tKelasdata.getText().equals("Private (Max 3 Orang)")){

            harga = 275000;            
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Web Design") && PembayaranView.tKelasdata.getText().equals("Private (Max 2 Orang)")){
            
            harga = 350000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Web Design") && PembayaranView.tKelasdata.getText().equals("Private (Max 1 Orang)")){

            harga = 700000;
            pv.tTotal.setText(Double.toString(harga));

        } 
//        menampilkan yg teknis komouter
        else if (PembayaranView.tMateri.getText().equals("Teknis Komputer") && PembayaranView.tKelasdata.getText().equals("Umum (Min 5 Orang)")){
            
            harga = 200000;
            pv.tTotal.setText(Double.toString(harga));
            
        } else if (PembayaranView.tMateri.getText().equals("Teknis Komputer") && PembayaranView.tKelasdata.getText().equals("Umum (Min 4 Orang)")){

            harga = 250000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Teknis Komputer") && PembayaranView.tKelasdata.getText().equals("Private (Max 3 Orang)")){

            harga = 275000;            
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Teknis Komputer") && PembayaranView.tKelasdata.getText().equals("Private (Max 2 Orang)")){
            
            harga = 350000;
            pv.tTotal.setText(Double.toString(harga));

        } else if (PembayaranView.tMateri.getText().equals("Teknis Komputer") && PembayaranView.tKelasdata.getText().equals("Private (Max 1 Orang)")){

            harga = 700000;
            pv.tTotal.setText(Double.toString(harga));

        } 

        } catch (Exception e) {
    }
    }
                
    public void hitung (PembayaranView pv){

                    
        double grandtotal;        
        double admin;      
        double uangmasuk;        
        double kembalian;
        
        admin = Double.parseDouble(PembayaranView.tAdmin.getText());       
        grandtotal = Double.parseDouble(pv.tGrandTotal.getText());       
        uangmasuk  = Double.parseDouble(pv.tBayar.getText());       
        kembalian  = uangmasuk - (admin+ grandtotal);
        
        pv.tKembalian.setText(Double.toString(kembalian));
        
        }
               
    public void cek_grand (PembayaranView pv){
        
            double tadmin;
            double tmateri;
            double grnd;
            
            tadmin = Double.parseDouble(PembayaranView.tAdmin.getText());
            tmateri = Double.parseDouble(PembayaranView.tTotal.getText());
            grnd = tadmin + tmateri;
            
            PembayaranView.tGrandTotal.setText(Double.toString(grnd));
    }
                               
    public void buy (PembayaranView pv){

                    
        if (validasi(pv).equals("Sukses")){
        
            try {
            
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String tgl = format.format(pv.tanggal.getDate());                
                String sql = "INSERT pembayaran VALUES ('"+pv.tNO.getText()+"','"+tgl.toString()+"',"               
                        + "'"+PembayaranView.tId.getText()+"', '"+PembayaranView.tNama.getText()+"',"                       
                        + "'"+PembayaranView.tMateri.getText()+"', '"+pv.tGrandTotal.getText()+"',"                       
                        + "'"+pv.tBayar.getText()+"', '"+pv.tKembalian.getText()+"')";
                        
                java.sql.Connection conn = new config().Conn();                
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);               
                pst.execute();                
                JOptionPane.showMessageDialog(null, "Sukses");                
            } catch (Exception e) {
                System.out.println("gagal" +e.toString());
            
            }
        }

    }

                
    public void reset (PembayaranView pv){

    
        pv.tNO.setText(no_trans_oto());      
        pv.tanggal.setDate(null);
        PembayaranView.tId.setText(null);
        PembayaranView.tTamggal.setText(null);
        PembayaranView.tNama.setText(null);
        PembayaranView.tInstasi.setText(null);
        PembayaranView.tNoTelp.setText(null);
        PembayaranView.tEmail.setText(null);
  
        PembayaranView.tAdmin.setText("10000");       
        PembayaranView.tTotal.setText(null);       
        PembayaranView.tGrandTotal.setText(null);        
        pv.tBayar.setText(null);
        pv.tKembalian.setText(null);  
        
        pv.tNO.setEnabled(false);                           
        PembayaranView.tAdmin.setEnabled(false);           
        PembayaranView.tTotal.setEnabled(false);               
        PembayaranView.tGrandTotal.setEnabled(false);
        
        
        pv.tKembalian.setEnabled(false);
        
    }
}