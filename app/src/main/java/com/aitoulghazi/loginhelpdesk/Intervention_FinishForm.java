package com.aitoulghazi.loginhelpdesk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Intervention_FinishForm extends AppCompatActivity {

    private static final String url = "jdbc:mysql://192.168.1.7:3306/sys";
    private static final String user = "admin";
    private static final String pass = "juazizomar";


    //Description input
    EditText txtAction;
    EditText txtLibre;
    EditText txtPrprty1;
    EditText txtPrprty2;
    String txtActionstr;
    String txtLibrestr;
    String txtPrprty1str;
    String txtPrprty2str;

    //Qualification input
    Spinner priority;
    Spinner type;
    Spinner ctgry;
    Spinner state;
    String prioritystr;
    String typestr;
    String ctgrystr;
    String statestr;


    //Time input
    EditText predictedDebut;
    EditText realDebut;
    EditText predictedEnd;
    EditText realEnd;
    EditText predictedTotalDuration;
    EditText totalDuration;
    EditText alarme;
    EditText realisation;

    String predictedDebutstr;
    String realDebutstr;
    String predictedEndstr;
    String realEndstr;
    String predictedTotalDurationstr;
    String totalDurationstr;
    String alarmestr;
    String realisationstr;

    Button complete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interv_item);

        txtAction = (EditText) findViewById(R.id.actionInp);
        txtLibre =(EditText) findViewById(R.id.libreInp);
        txtPrprty1 =(EditText) findViewById(R.id.prprtyfirstInp);
        txtPrprty2 =(EditText) findViewById(R.id.prprtyscndInp);

        txtActionstr = txtAction.getText().toString();
        txtLibrestr = txtLibre.getText().toString();
        txtPrprty1str = txtPrprty1.getText().toString();
        txtPrprty2str = txtPrprty2.getText().toString();

        priority = (Spinner) findViewById(R.id.prioritySpinner);
        type = (Spinner) findViewById(R.id.typeSpinner);
        ctgry = (Spinner) findViewById(R.id.categorySpinner);
        state = (Spinner) findViewById(R.id.etatSpinner);

        prioritystr = priority.getSelectedItem().toString();
        typestr = type.getSelectedItem().toString();
        ctgrystr = ctgry.getSelectedItem().toString();
        statestr = state.getSelectedItem().toString();



        predictedDebut =(EditText) findViewById(R.id.debutInput);
        realDebut =(EditText) findViewById(R.id.debutRealInput);
        predictedEnd =(EditText) findViewById(R.id.finInput);
        realEnd = (EditText) findViewById(R.id.finRealInput);
        predictedTotalDuration =(EditText) findViewById(R.id.durationInput);
        totalDuration = (EditText) findViewById(R.id.totalDurationInput);
        alarme =(EditText) findViewById(R.id.alarmeInput);
        realisation =(EditText) findViewById(R.id.realisationInput);

        predictedDebutstr = predictedDebut.getText().toString();
        realDebutstr = realDebut.getText().toString();
        predictedEndstr = predictedEnd.getText().toString();
        realEndstr = realEnd.getText().toString();
        predictedTotalDurationstr = predictedTotalDuration.getText().toString();
        totalDurationstr = totalDuration.getText().toString();
        alarmestr = alarme.getText().toString();
        realisationstr = realisation.getText().toString();

        complete = (Button) findViewById(R.id.btnComplete);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");
            }
        });






    }

    private class ConnectMySql extends AsyncTask<String, String, String> {
        String res = "";
        boolean flag = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Intervention_FinishForm.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");


                Statement st = con.createStatement();
                Statement st2 = con.createStatement();
                //+" and intervenionID = "+Intervention_Activity.itemVal
                String query = "insert into completedIntervention values ("+"'"+txtActionstr+"',"+"'"+txtLibrestr+"',"+"'"+txtPrprty1str+"',"+"'"+txtPrprty2str+"',"+
                        "'"+prioritystr+"',"+"'"+typestr+"',"+"'"+ctgrystr+"',"+"'"+statestr+"',"+"'"+predictedDebutstr+"',"+"'"+realDebutstr+"',"+
                        "'"+predictedEndstr+"',"+"'"+realEndstr+"',"+"'"+predictedTotalDurationstr+"',"+"'"+totalDurationstr+"',"+"'"+alarmestr+"',"+"'"+realisationstr+"',"+Intervention_Activity.itemVal+",'"+MainActivity.uname+"'"+")"+";";

                String query2 = "delete from intervention where interventionID = "+Intervention_Activity.itemVal+";";
                     st.executeUpdate(query);
                     st2.executeUpdate(query2);
             //   ResultSetMetaData rsmd = rs.getMetaData();

                flag = true;






            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
                flag = false;
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            if (flag == true) {
                Toast.makeText(Intervention_FinishForm.this, "Intervention completed.", Toast.LENGTH_SHORT)
                        .show();

                Intent backIntervAct = new Intent(Intervention_FinishForm.this,Intervention_Activity.class);
                startActivity(backIntervAct);
            }
            else{
                Toast.makeText(Intervention_FinishForm.this, result, Toast.LENGTH_SHORT)
                        .show();
            }





        }
    }

}
