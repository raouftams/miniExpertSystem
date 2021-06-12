package app.jade;

public class Form {
    private String depart;
    private String destination;
    private String dateDepart;
    private String dateRetour;
    private String nbBillets;
    private String aeroport;

    public Form(String depart, String destination, String dateDepart, String dateRetour, String nbBillets, String aeroport) {
        this.depart = depart;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
        this.nbBillets = nbBillets;
        this.aeroport = aeroport;
    }

    public String toString(){
        return "{\"depart\":" + "\"" + this.depart + "\"" + "," +
                "\"dateD\":" + "\"" + this.dateDepart + "\"," +
                "\"destination\":" + "\"" + this.destination + "\"," +
                "\"nbBillets\":" + "\"" + this.nbBillets + "\"" +
                "}";
    }
}
