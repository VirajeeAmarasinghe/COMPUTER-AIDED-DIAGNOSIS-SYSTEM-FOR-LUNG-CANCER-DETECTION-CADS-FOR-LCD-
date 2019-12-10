/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author virajee
 */
public class LoginDB {
    private Connection con;
    
    public LoginDB() {
       try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cad", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Internal Error!Contact the System Administrator");
            System.exit(0);
        }
    }
    
    //this method is for retrieving login details according to username
    public LoginInfo getLogin(String username) {
        LoginInfo l = null;

        try {
            String select = "select * from login where username=?";
            PreparedStatement ps = con.prepareStatement(select);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String password = rs.getString("password");
                String userlevel = rs.getString("user_level");
                l = new LoginInfo(username, password, userlevel);
            }
            rs.close();
            ps.close();
            return l;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Internal Error!Contact the System Administrator");
            System.exit(0);
            return null;
        }
    }
    
    /*
     this method is for updating passwords.
     first it checks whether eneterd username is available in the database or not.
     If available updating is done. If not error message will come.
     */
    public int updatePassword(LoginInfo d) {
        try {
            String selectSt = "select * from login where username=?";
            PreparedStatement ps = con.prepareStatement(selectSt);
            ps.setString(1, d.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String updateSt = "update login set password=? where username=?";
                PreparedStatement ps2 = con.prepareStatement(updateSt);
                ps2.setString(1, d.getPassword());
                ps2.setString(2, d.getUsername());
                int result = ps2.executeUpdate();
                ps.close();
                ps2.close();
                return result;
            } else {
                JOptionPane.showMessageDialog(null, "No Such User Exits in the DB.");
                return 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Internal Error!Contact the System Administrator");
            System.exit(0);
            return 0;
        }
    }
    
    /*
     this method is for deleting user.
     first it checks whether eneterd username is available in the database or not.
     If available deleting is done. If not error message will come.
     */
    public int deleteUser(String userID) {
        try {
            String select = "select * from login where username=?";
            PreparedStatement p = con.prepareStatement(select);
            p.setString(1, userID);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                String deleteSt = "delete from login where username=?";
                PreparedStatement ps = con.prepareStatement(deleteSt);
                ps.setString(1, userID);
                int result = ps.executeUpdate();
                ps.close();
                return result;
            } else {
                JOptionPane.showMessageDialog(null, "No Such User Exits in the DB.");
                return 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Internal Error!Contact the System Administrator");
            System.exit(0);           
            return 0;
        }
    }
    
    /*
     this method is for inserting new user login details to the Database.
     first it checks whether eneterd username is available in the database or not.
     If available error message will come. If not insertion is done.
     */
    public int addLoginData(LoginInfo lin) {
        try {
            String select = "select * from login where username=?";
            PreparedStatement p = con.prepareStatement(select);
            p.setString(1, lin.getUsername());
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Username is Already Taken.");
                return 0;
            } else {
                String insertSt = "insert into login(username,password,user_level)values(?,?,?)";
                PreparedStatement ps = con.prepareStatement(insertSt);

                ps.setString(1, lin.getUsername());
                ps.setString(2, lin.getPassword());
                ps.setString(3, lin.getUserLevel());

                int result = ps.executeUpdate();
                ps.close();
                return result;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Internal Error!Contact the System Administrator");
            System.exit(0);
            return 0;
        }
    }
}
