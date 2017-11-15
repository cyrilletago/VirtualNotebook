package com.example.cyrille.virtualnotebook;

/**
 * Created by cyrille on 14/11/17.
 */

public class Validators {

    public boolean isAlpha(String s){
        String pattern= "^[a-zA-Zéèàç]*$";
        return s.matches(pattern);
    }

}
