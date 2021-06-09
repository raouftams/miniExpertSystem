package app.jade;

public class Main {
    public static void main(String[] args) {
        //probably gonna change it, we dont need the fuckin jade GUI
        String[] jadearg= new String[2];
        StringBuffer SbAgent=new StringBuffer();
        SbAgent.append("AC:app.jade.CentralAgent(arguments,META,drias);");
        SbAgent.append("AN1:app.jade.Company1;");
        SbAgent.append("AN2:app.jade.Company2;");
        SbAgent.append("AN3:app.jade.Company3;");
        SbAgent.append("AN4:app.jade.Company4;");
        jadearg[0]="-gui";
        jadearg[1]=SbAgent.toString();
        jade.Boot.main(jadearg);
    }
}
