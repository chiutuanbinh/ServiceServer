/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.zalotraing.server;

/**
 *
 * @author root
 */
public class Util {
    public static String ProfileInfoToString (ProfileInfo obj){
	String result = "";
	result = result + obj.id + "," + obj.userName + "," + obj.email + "," + obj.phoneNumber
		+ "," + obj.birthday.date + "," + obj.birthday.month + "," + obj.birthday.year;
	return result;
    }
    
    public static ProfileInfo StringToProfileInfo(String str){
	String[] fraction = str.split(",");
	return new ProfileInfo(fraction[1], fraction[2], fraction[3], 
		new Day(Integer.parseInt(fraction[4]), Integer.parseInt(fraction[5]), Integer.parseInt(fraction[6])), 
		fraction[0]);
    }
}
