package app.jade;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Offer {

    String companie;
    String escale;
    String dateD;
    String dateR;
    String prix;

    public Offer(String companie, String escale, String dateD, String dateR, String prix) {
        this.companie = companie;
        this.escale = escale;
        this.dateD = dateD;
        this.dateR = dateR;
        this.prix = prix;
    }

    public Offer(String jsonData) throws ParseException {
        System.out.println(jsonData);
        JSONObject jsonObj = (JSONObject) new JSONParser().parse(jsonData);
        System.out.println(String.valueOf(jsonObj.get("companie")));
        this.companie = String.valueOf(jsonObj.get("companie"));
        this.dateR = String.valueOf(jsonObj.get("dateR"));
        this.dateD = String.valueOf(jsonObj.get("dateD"));
        this.prix = String.valueOf(jsonObj.get("prix"));
        this.escale = String.valueOf(jsonObj.get("escale"));
    }

    public String toString(){
        return "{\"dateD\":" + "\"" + this.dateD + "\"" + "," +
                "\"dateR\":" + "\"" + this.dateR + "\"," +
                "\"escale\":" + "\"" + this.escale + "\"," +
                "\"prix\":" + "\"" + this.prix + "\"," +
                "\"companie\":" + "\"" + this.companie + "\"," +
                "}";
    }

    public String getCompanie() {
        return companie;
    }

    public String getEscale() {
        return escale;
    }

    public String getDateD() {
        return dateD;
    }

    public String getDateR() {
        return dateR;
    }

    public String getPrix() {
        return prix;
    }
}
