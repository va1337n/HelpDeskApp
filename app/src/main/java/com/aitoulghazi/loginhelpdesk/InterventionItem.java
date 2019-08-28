package com.aitoulghazi.loginhelpdesk;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterventionItem extends AppCompatActivity {

    private static final String url = "jdbc:mysql://192.168.1.7:3306/sys";
    private static final String user = "admin";
    private static final String pass = "juazizomar";

    TextView txtIntvId;
    TextView txtIntervener;
    TextView txtDesc;
    TextView txtRequester;
    Button btnedit;
    Integer intvId;
    String intvIdstr;
    String intvdesc;
    String intvuname;
    String intvreq ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interv_info);

        txtIntvId = (TextView) findViewById(R.id.intervInfoIDinp);
        txtIntervener = (TextView) findViewById(R.id.intervInfoIntervenerInp);
        txtDesc = (TextView) findViewById(R.id.intervInfoDescInp);
        txtRequester = (TextView) findViewById(R.id.intervInfoReqInp);



        ConnectMySql connectMySql = new ConnectMySql();
        connectMySql.execute("");

        btnedit = (Button) findViewById(R.id.btnEdit);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intervFinAct = new Intent(getApplicationContext(),Intervention_FinishForm.class);
                startActivity(intervFinAct);
            }
        });





    }

    private class ConnectMySql extends AsyncTask<String, String, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(InterventionItem.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");

                String result = "Database Connection Successful\n"+"Welcome "+MainActivity.uname+"\n";
                Statement st = con.createStatement();
                //+" and intervenionID = "+Intervention_Activity.itemVal
                String query = "select interventionID, intvDescription, username, Requester from intervention where interventionID = "+Intervention_Activity.itemVal+";";
                ResultSet rs = st.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();





                while (rs.next()) {
                     intvId = rs.getInt("interventionID");
                     intvdesc = rs.getString("intvDescription");
                     intvuname = rs.getString("username");
                     intvreq = rs.getString("Requester");
                     intvIdstr = Integer.toString(intvId);



                }





                while (rs.next()) {
                    result +=rs.getString(1).toString() + "\n";
                }
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            txtIntvId.setText(intvIdstr);
            txtDesc.setText(intvdesc);
            txtIntervener.setText(intvuname);
            txtRequester.setText(intvreq);






        }
    }
}
