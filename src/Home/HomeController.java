/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

/**
 *
 * @author Lenovo
 */
import Login.LoginView;
import Pembayaran.PembayaranView;
import Pembayaran.RaportView;
import static java.awt.Frame.MAXIMIZED_BOTH;
import javax.swing.JOptionPane;
import Pendaftaran.PendaftaranView;
import Pendaftaran.Rapot;

public class HomeController {
    
    private Object JoptionPane;
    
    public void maximaze(HomeView hv){
        hv.setExtendedState(MAXIMIZED_BOTH);
    }
    
    public void keluar(HomeView hv){
        int confir = JOptionPane.showConfirmDialog(hv, "Ingin Keluar dari Aplikasi?", "keluar Aplikasi?", JOptionPane.YES_NO_OPTION);
        if (confir == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
    
    public void logout (HomeView hv){
        int conf = JOptionPane.showConfirmDialog(hv, "Log Out?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION){
            
            LoginView l = new LoginView();
            l.setVisible(true);
            hv.dispose();
            
        }
    }
    
    public void menu_pendaftaran (HomeView hv){
        
        PendaftaranView pv = new PendaftaranView();
        hv.dHome.add(pv);  
        pv.setVisible(true);
        
    }
    
    public void menu_pembayaran (HomeView hv){
        
        PembayaranView pv = new PembayaranView();
        hv.dHome.add(pv);
        pv.setVisible(true);
    }
    
    public void menu_Laporan (HomeView hv){
        
        Rapot vui = new Rapot();
        hv.dHome.add(vui);
        vui.setVisible(true);
    }
    
    public void menu_Laporan_Pembayaran (HomeView hv){
        
        RaportView v = new RaportView();
        hv.dHome.add(v);
        v.setVisible(true);
    }
}
