/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

/**
 *
 * @author virajee
 */
public class LoginInfo {

    private String username;  //variable to store user id
    private String password;  //variable to store password
    private String userLevel; //variable to store user level

    public LoginInfo(String username, String password, String userLevel) {
        this.username = username;
        this.password = password;
        this.userLevel = userLevel;
    }

    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public String getUsername() {
        return username;
    }
}
