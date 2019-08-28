package com.aitoulghazi.loginhelpdesk;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class Intervention_Activity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://192.168.1.7:3306/sys";
    private static final String user = "admin";
    private static final String pass = "juazizomar";
    Button btnFetch;
    TextView txtData;
    ListView lstData;
    SimpleAdapter ADAhere;
    ArrayAdapter<String> mAdapter;

    public static Integer itemVal;
    List<Map<String, String>> data = null;
    Map<String, String> datanum;

    MainActivity mnactivity = new MainActivity();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interv_list);
        txtData = (TextView) this.findViewById(R.id.txtData);
        btnFetch = (Button) findViewById(R.id.btnFetch);

        btnFetch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");
            }
        });

        lstData = (ListView) findViewById(R.id.lstData);
        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HashMap<String, String> mymap = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                String itemValbef = mymap.get("A");
                String itemValaft = itemValbef.substring(14);
                itemVal = Integer.parseInt(itemValaft);

                Intent intervItemAct = new Intent(getApplicationContext(),InterventionItem.class);
                startActivity(intervItemAct);
            }
        });




    }

    private class ConnectMySql extends AsyncTask<String, String, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Intervention_Activity.this, "Please wait...", Toast.LENGTH_SHORT)
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
                String query = "select interventionID from intervention where username = "+"'"+MainActivity.uname+"'"+";";
                ResultSet rs = st.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();

               // List<Map<String, String>> data = null;
              data = new ArrayList<Map<String, String>>();



                while (rs.next()) {


                   // Map<String, String> datanum = new HashMap<String, String>();
                    datanum = new HashMap<String, String>();

                    datanum.put("A","Intervention #"+ rs.getString(1).toString());
                    data.add(datanum);
                }

                String[] fromwhere = { "A" };
                int[] viewswhere = { R.id.lblintrvid};
                ADAhere = new SimpleAdapter(Intervention_Activity.this, data,
                        R.layout.intervlist_bg, fromwhere, viewswhere);




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
            txtData.setText(result);
            lstData.setAdapter(ADAhere);



        }
    }

}