package com.aitoulghazi.loginhelpdesk;

public class interventionList {

    public String intrvId;
    public String intrvDesc;



    public interventionList(String intrvId, String intrvDesc){

        intrvId = this.intrvId;
        intrvDesc = this.intrvDesc;


    }

    public String getId (){ return intrvId;}
    public String getIntrvDesc (){ return intrvDesc;}


}
