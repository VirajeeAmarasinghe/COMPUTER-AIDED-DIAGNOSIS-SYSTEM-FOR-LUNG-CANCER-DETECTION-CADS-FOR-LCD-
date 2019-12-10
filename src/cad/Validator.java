/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author virajee
 */
public class Validator {
    //this method is for mandatory field validation
    public boolean isPresent(String fieldValue) {
        if (fieldValue.length() == 0 || fieldValue == null) {
            return false;
        }
        return true;
    }
    
    public boolean isValidWithPasswordPolicy(String fieldValue) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher matcher = pattern.matcher(fieldValue);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    //this method is for implementing encryption technique
    public String reverseuser_pass(String user_pass) {
        String reverseword="";
        if (user_pass.length() == 1) {
            return user_pass;
        } else {
            reverseword += user_pass.charAt(user_pass.length() - 1) + reverseuser_pass(user_pass.substring(0, user_pass.length() - 1));
            return reverseword;
        }
    }
}
