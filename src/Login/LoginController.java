/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

/**
 *
 * @author Lenovo
 */
import inc.config;
import Home.HomeView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class LoginController {
    
    Connection conn;
    Statement st;
    ResultSet rs;
    
    public void login(LoginView lv){
        try {
            String user = lv.t_User.getText();
            String pass = lv.t_Pass.getText();
            String sql = "select * from akun where username='"+user+"' and password='"+pass+"'";
            
            conn= config.Conn();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            if(rs.next()){
                if(user.equals(rs.getString("username")) && pass.equals(rs.getString("password"))){
                    JOptionPane.showMessageDialog(null, "Berhasil Login");
                    new HomeView().setVisible(true);
                   
                }
            } else {
                JOptionPane.showMessageDialog(null, "Login Gagal!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
