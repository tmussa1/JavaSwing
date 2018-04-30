/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udc;

//import java.awt.List;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author TOSHIBAPC
 */
public class ImageServer {
    
public ImageServer(){}

private Connection connection = null; 

public void connect(String dbName){
    if(connection != null)
        return;
    try 
    {
        String dbUrl = "jdbc:derby:"+dbName;
        connection = DriverManager.getConnection(dbUrl);
        if(connection == null)
            System.out.println("conn null");
    }
    catch(SQLException sqe)
    {
        System.out.println("connect Error " + sqe);
    }
}

public List<pojo> getDBItem(){
    Statement st = null;
    ResultSet rs = null;
    
    List<pojo> pojoList = new LinkedList<pojo>();
    
    String sql = "Select cc_acct_num, holder, holder_pic from holder";
    try
    {
        st = connection.createStatement();
        rs = st.executeQuery(sql);
      
      byte [] image = null;
      while(rs.next()){
        pojo pj = new pojo();
        pj.cc_acct_num = rs.getString("cc_acct_num");
        pj.holder = rs.getString("holder");
        pj.holder_pic = rs.getBlob("holder_pic");
        
        pojoList.add(pj);
      }
      
      
    }
    catch(SQLException sqe){
        System.out.println("Error " + sqe);
    }
    return pojoList;
}
public Image showImage(String acct_num){
    Statement st = null;
    ResultSet rs = null;
    
    String sql = "select holder_pic from holder where cc_acct_num = " + "'" +acct_num + "'";
    Image img = null;
  
    
    try
    {
        st = connection.createStatement();
        rs = st.executeQuery(sql);
        
        
        while(rs.next()){
           byte [] byteImage = rs.getBytes("holder_pic");
           img = Toolkit.getDefaultToolkit().createImage(byteImage);
        }
    
    }
    catch(SQLException sqe)
    {
        System.out.println("Image error" + sqe);
    }  
    
    return img;
}
}
