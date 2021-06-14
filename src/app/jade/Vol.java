package app.jade;

public class Vol {
    public String depart;
    public String destination;
    public String dateDepart;
    public int nbPlaces;

    public Vol(String depart, String destination, String dateDepart, int nbPlaces) {
        this.depart = depart;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.nbPlaces = nbPlaces;
    }
}
