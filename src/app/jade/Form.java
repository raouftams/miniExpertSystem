package app.jade;

public class Form {
    private String depart;
    private String destination;
    private String dateDepart;
    private String dateRetour;
    private String nbBillets;
    private String aeroport;
    private String nbAdulte;
    private String nbEnfant;
    private String nbPerAgee;

    public Form(String depart, String destination, String dateDepart, String dateRetour, String aeroport, String nbAdulte, String nbEnfant, String nbPerAgee) {
        this.depart = depart;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
        this.aeroport = aeroport;
        this.nbAdulte = nbAdulte;
        this.nbEnfant = nbEnfant;
        this.nbPerAgee = nbPerAgee;
        this.nbBillets = String.valueOf(Integer.parseInt(nbAdulte) + Integer.parseInt(nbEnfant) + Integer.parseInt(nbPerAgee));
    }

    public String toString(){
        return "{\"depart\":" + "\"" + this.depart + "\"" + "," +
                "\"destination\":" + "\"" + this.destination + "\"," +
                "\"dateD\":" + "\"" + this.dateDepart + "\"," +
                "\"dateR\":" + "\"" + this.dateRetour + "\"," +
                "\"nbBillets\":" + "\"" + this.nbBillets + "\"," +
                "\"nbEnfants\":" + "\"" + this.nbEnfant + "\"," +
                "\"nbAdultes\":" + "\"" + this.nbAdulte + "\"," +
                "\"nbPerAgee\":" + "\"" + this.nbPerAgee + "\"," +
                "\"aeroport\":" + "\"" + this.aeroport + "\"" +
                "}";
    }
}
