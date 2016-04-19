/**
*@author tyl
*@date Apr 17, 2016 8:32:59 PM
*@email buaatyl@qq.com
**/
package com.tyl.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Const {
	public static String cnDataPath = "F:\\stoneforest\\work\\workspace\\lucence_github\\src\\test\\resources\\mydata";
	public static String imageFilePath = "";
	public static String imageIndexPath = "";
	public static String textIndexPath = "";
	public static String textFilePath = "";
	public Const(){
		Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(("path.properties")));

        } catch (FileNotFoundException e) {
            System.out.println("1-FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("2-IOException");
            e.printStackTrace();
        }
        cnDataPath = pro.getProperty("cnDataPath");
        imageFilePath = pro.getProperty("imageFilePath");
        imageIndexPath = pro.getProperty("imageIndexPath");
        textIndexPath = pro.getProperty("textIndexPath");
        textFilePath= pro.getProperty("textFilePath");
	}
	public static void main(String[] a){
		System.out.println(new Const().imageIndexPath);
	}
}
