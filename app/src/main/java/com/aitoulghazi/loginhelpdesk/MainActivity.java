package com.aitoulghazi.loginhelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://192.168.1.7:3306/sys";
    private static final String user = "admin";
    private static final String pass = "juazizomar";
    EditText username;
    EditText password;
    Button logBtn;
    ProgressBar progressBar;


    public static String uname;
   public static String pww;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          username = (EditText) findViewById(R.id.userEdit);
          password = (EditText) findViewById(R.id.passEdit);
         logBtn = (Button) findViewById(R.id.loginBtn);
         progressBar = (ProgressBar) findViewById(R.id.progressBar);




        progressBar.setVisibility(View.GONE);
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");

            }
        });


    }

    private class ConnectMySql extends AsyncTask<String, String, String>{
            String msg = "";
            Boolean isSuccess = false;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            uname = username.getText().toString();
            pww = password.getText().toString();

            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        protected String doInBackground(String... params) {




            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Database connection success");

                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
                String query = "select username, passwordd from intervener where username = '"+uname+"' and passwordd = '"+pww+"'"+";";
                ResultSet rs = st.executeQuery(query);


                if (rs.next()) {
                    msg = "Login Successful";
                    isSuccess = true;
                }
                else{
                    msg = "Invalid Credentials";
                    isSuccess = false;
                }
                //msg = result;
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
                msg = e.toString();

            }



            return msg;

        }

        @Override
        protected void onPostExecute(String s) {
            //txtLol.setText(s);


            if(isSuccess == true){
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT)
                        .show();
                Intent intervAct = new Intent(MainActivity.this,Intervention_Activity.class);
               startActivity(intervAct);
            }
            else{
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT)
                        .show();
            }


            progressBar.setVisibility(View.GONE);
            super.onPostExecute(s);
        }
    }
}
