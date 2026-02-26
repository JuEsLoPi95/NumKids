package com.example.numkids.connetion;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnetionDB {

    private String Server = "(localdb)//MSSQLLocalDB";
    private String User = "PruebasWA";
    private String Password = "PruebasWA123*";
    private String DataBase = "App_NumKids";

    @SuppressLint("NewApi")
    public Connection Connect(){
        Connection connection = null;
        String ConnectionURL = null;

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + this.Server + "/" + this.DataBase + ";user="
                                + this.User + ";password=" + this.Password + ";";
            connection = DriverManager.getConnection(ConnectionURL);


        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error de Conexion SQL ", e.getMessage());
        }

        return connection;
    }

}
