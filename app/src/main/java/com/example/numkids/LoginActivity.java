package com.example.numkids;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.numkids.connetion.ConnetionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    EditText usuario, clave;
    TextView lblRegistro;
    Button btnLogin;
    Connection con;


    public LoginActivity(){
        ConnetionDB instanceConnection = new ConnetionDB();
        con=instanceConnection.Connect();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usuario = (EditText) findViewById(R.id.txtUser);
        clave = (EditText) findViewById(R.id.txtPassword);
        lblRegistro = (TextView) findViewById(R.id.lblRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new LoginActivity();

                new LoginActivity.Login().execute("");
            }
        });

        lblRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(getApplicationContext(), RegistrarActivity.class);
                startActivity(reg);
            }
        });

    }

    public class Login extends AsyncTask<String,String,String>{

        String z = null;
        boolean exito = false;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
        }


        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {

            if (con == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"Verifique su Conexión",Toast.LENGTH_SHORT).show();
                    }
                });
                z= "En Conexion";
            }
            else{
                try{

                    String sql = "EXEC p_Get_Login '" +usuario.getText()+ "', '" +clave.getText()+ "'" ;

                    Statement stm = con.createStatement();
                    ResultSet rs = stm.executeQuery(sql);

                    if (rs.next()){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"Acceso Exitoso",Toast.LENGTH_SHORT).show();
                                Intent menu = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(menu);
                            }
                        });
                        usuario.setText("");
                        clave.setText("");
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"Error en el usuario y/o Contraseña",Toast.LENGTH_SHORT).show();
                            }
                        });
                        usuario.setText("");
                        clave.setText("");
                    }


                }catch(Exception e){
                    exito=false;
                    Log.e("ERROR DE CONEXION : ", e.getMessage());
                }
            }

            return z;
        }
    }



}