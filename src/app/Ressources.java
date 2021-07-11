package app;

import app.jade.Vol;
import app.rule.*;

import java.util.*;

public abstract class Ressources {

    private static final Condition cEquals = new Condition("=") ;
    private static final Condition cNotEquals = new Condition("!=") ;
    private static final Condition cLessThan = new Condition("<") ;
    private static final Condition cMoreThan = new Condition(">") ;
    private static final Condition cLessThanOrEquals = new Condition("<=");
    private static final Condition cMoreThanOrEquals = new Condition(">=");
    private static final Condition cDateLessThan = new Condition("<=D");

    //Air algerie data
    private static final String[] airAlgDates = {"14/06/2021", "22/06/2021", "07/07/2021", "24/10/2021", "21/11/2021", "26/11/2021"};
    public static final String[] airAlgVilles = {"Alger", "Paris", "Montreal", "Londre"};
    public static final String[] airAlgAirports = {"Charles_De_Gaulle(FRA)", "Montréal_Mirabel(CAN)", "Houari_Boumédiène(DZA)", "Heathrow(GBA)"};
    public static ArrayList<Vol> airAlgvols = generateVols(airAlgDates, airAlgVilles);

    //Air France data
    private static final String[] airFRDates = {"07/07/2021", "24/10/2021", "21/11/2021", "26/11/2021"};
    public static final String[] airFRVilles = {"Alger", "Paris", "Berlin", "Stockholm"};
    public static final String[] airFRAirports = {"Charles_De_Gaulle(FRA)", "Willy Brandt (BER)", "Houari_Boumédiène(DZA)", "Arlanda Airport(Suède)"};
    public static ArrayList<Vol> airFRvols = generateVols(airFRDates, airFRVilles);

    //Air british data
    private static final String[] airGBADates = {"07/07/2021", "24/10/2021", "21/11/2021", "26/11/2021"};
    public static final String[] airGBAVilles = {"Londre", "Paris", "Amsterdam", "Madrid"};
    public static final String[] airGBAAirports = {"Charles_De_Gaulle(FRA)", "Willy Brandt (BER)", "Houari_Boumédiène(DZA)", "Arlanda Airport(Suède)"};
    public static ArrayList<Vol> airGBAvols = generateVols(airGBADates, airGBAVilles);

    //air france data

    public static RuleBase SmartphooneRuleBase(){
        RuleBase rb = new RuleBase("Smartphones");

        rb.goalClauseStack = new Stack() ;
        rb.variableList = new Hashtable() ;
        rb.ruleList = new Vector() ;

        RuleVariable brand = new RuleVariable("Brand");
        brand.setLabels("apple google samsung xiaomi asus");
        rb.variableList.put((String) brand.name, brand);

        RuleVariable os = new RuleVariable("OS");
        os.setLabels("android ios");
        rb.variableList.put(os.name, os);

        RuleVariable ui = new RuleVariable("UI");
        ui.setLabels("none miui oneui");
        rb.variableList.put(ui.name, ui);

        RuleVariable securityLevel = new RuleVariable("SecurityLevel");
        securityLevel.setLabels("high medium");
        rb.variableList.put(securityLevel.name, securityLevel);

        RuleVariable phoneRange = new RuleVariable("PhoneRange");
        phoneRange.setLabels("flagship midrange budget");
        rb.variableList.put(phoneRange.name, phoneRange);

        RuleVariable utility = new RuleVariable("Utility");
        utility.setLabels("business communication gaming");
        rb.variableList.put(utility.name, utility);

        RuleVariable userBudget = new RuleVariable("UserBudget");
        userBudget.setLabels("200 300 400 500 600 700 800 900 1000 1100 1200");
        rb.variableList.put(userBudget.name, userBudget);

        //Define rules
        Rule flagshipRange = new Rule(rb, "check_flagship",
                new Clause[]{new Clause(userBudget, cMoreThanOrEquals, "700")},
                new Clause[]{new Clause(phoneRange, cEquals, "flagship")}
        );

        Rule midRange = new Rule(rb, "check_midrange",
                new Clause[]{
                        new Clause(userBudget, cLessThan, "700"),
                        new Clause(userBudget, cMoreThanOrEquals, "300")
                },
                new Clause[]{new Clause(phoneRange, cEquals, "midrange")}
        );

        Rule budgetRange = new Rule(rb, "check_budget",
                new Clause[]{new Clause(userBudget, cLessThan, "300")},
                new Clause[]{new Clause(phoneRange, cEquals, "budget")}
        );

        Rule highSecurity = new Rule(rb, "high_security",
                new Clause[]{new Clause(securityLevel, cEquals, "high")},
                new Clause[]{new Clause(os, cEquals, "ios")}
        );

        Rule midSecurity = new Rule(rb, "medium_security",
                new Clause[]{new Clause(securityLevel, cEquals, "medium")},
                new Clause[]{new Clause(os, cEquals, "android")}
        );

        Rule businessUtility = new Rule(rb, "business_utility",
                new Clause[]{new Clause(utility, cEquals, "business")},
                new Clause[]{new Clause(securityLevel, cEquals, "high")}
        );

        Rule gamingUtility = new Rule(rb, "gaming_utility",
                new Clause[]{new Clause(utility, cEquals, "gaming")},
                new Clause[]{new Clause(securityLevel, cEquals, "medium")}
        );

        Rule communicationUtility = new Rule(rb, "communication_utility",
                new Clause[]{new Clause(utility, cEquals, "communication")},
                new Clause[]{new Clause(securityLevel, cEquals, "medium")}
        );

        Rule appleUi = new Rule(rb, "apple_os",
                new Clause[]{new Clause(os, cEquals, "ios")},
                new Clause[]{new Clause(brand, cEquals, "apple")}
        );

        Rule googleUi = new Rule(rb, "google_os",
                new Clause[]{
                        new Clause(os, cEquals, "android"),
                        new Clause(ui, cEquals, "none")
                },
                new Clause[]{new Clause(brand, cEquals, "google")}
        );

        Rule samsungUi = new Rule(rb, "samsung_ui",
                new Clause[]{
                        new Clause(os, cEquals, "android"),
                        new Clause(ui, cEquals, "oneui")
                },
                new Clause[]{new Clause(brand, cEquals, "samsung")}
        );

        Rule xiaomiUi = new Rule(rb, "xiaomi_ui",
                new Clause[]{
                        new Clause(os, cEquals, "android"),
                        new Clause(ui, cEquals, "miui")
                },
                new Clause[]{new Clause(brand, cEquals, "xiaomi")}
        );

        Rule gamingPhone = new Rule(rb, "gaming_phone",
                new Clause[]{
                        new Clause(os, cEquals, "android"),
                        new Clause(utility, cEquals, "gaming")
                },
                new Clause[]{new Clause(brand, cEquals, "asus")}
        );

        return rb;
    }

    public static ArrayList<Vol> generateVols(String[] dates, String[] villes){
        ArrayList<Vol> vols = new ArrayList<>();
        for (int i = 0; i < dates.length; i++) {
            for (int j = 0; j < villes.length; j++) {
                for (int k = 0; k < villes.length; k++) {
                    vols.add(
                            new Vol(
                                    villes[j],
                                    villes[k],
                                    dates[i],
                                    100
                            )
                    );
                }
            }
        }
        return vols;
    }

    public static RuleBase AirAlgerieRuleBase(){
        RuleBase rb = new RuleBase("Air algerie");

        rb.goalClauseStack = new Stack() ;
        rb.variableList = new Hashtable() ;
        rb.ruleList = new Vector() ;

        RuleVariable check = new RuleVariable("check");
        check.setLabels("checkDepart checkDestination checkDate checkNbrBillets");
        rb.variableList.put(check.name, check);

        RuleVariable destination = new RuleVariable("Destination");
        destination.setLabels("Paris Montreal Londre Alger");
        rb.variableList.put(destination.name, destination);

        RuleVariable depart = new RuleVariable("Depart");
        depart.setLabels("Paris Montreal Londre Alger");
        rb.variableList.put(depart.name, depart);

        RuleVariable age = new RuleVariable("Age");
        age.setLabels("2 4 6 8 10 12 14 16 20 25 30 55 70 76 80");
        rb.variableList.put(age.name, age);

        RuleVariable aeroport = new RuleVariable("Aeroports");
        aeroport.setLabels("Charles_De_Gaulle(FRA) Lyon_Saint_Exupéry(FRA) " +
                "Montréal_Mirabel(CAN) Muskoka(CAN) " +
                "Oran-Es_Senia(DZA) Houari_Boumédiène(DZA) " +
                "Heathrow(GBA) Birmingham(GBA) " +
                "Shanghai-Pudong(CHN) Canton-Baiyun(CHN)");
        rb.variableList.put(aeroport.name, aeroport);

        RuleVariable nbbillets = new RuleVariable("NbBillets");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbbillets.name, nbbillets);

        RuleVariable nbEnfants = new RuleVariable("NbEnfants");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbEnfants.name, nbEnfants);

        RuleVariable nbAdulte = new RuleVariable("NbAdultes");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbAdulte.name, nbAdulte);

        RuleVariable nbAdolescents = new RuleVariable("NbAdolescents");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbAdolescents.name, nbAdolescents);

        RuleVariable nbAged = new RuleVariable("NbAged");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbAged.name, nbAged);

        RuleVariable remiseBillet = new RuleVariable("RemiseSurBillets");
        remiseBillet.setLabels("Avec Sans");
        rb.variableList.put(remiseBillet.name, remiseBillet);

        RuleVariable remiseEscale = new RuleVariable("RemiseSurEscale");
        remiseEscale.setLabels("Avec Sans");
        rb.variableList.put(remiseEscale.name, remiseEscale);

        RuleVariable saison = new RuleVariable("Saison");
        saison.setLabels("Saison_Estivale Hors_Saison_Estivale");
        rb.variableList.put(saison.name, saison);

        RuleVariable agereduction = new RuleVariable("AgeReduction");
        agereduction.setLabels("10% 15% 20%");
        rb.variableList.put(agereduction.name, agereduction);

        RuleVariable date = new RuleVariable("Date");
        date.setLabels("22/09/2021 07/08/2021 11/06/2021 24/10/2021 21/11/2021 26/11/2021");
        rb.variableList.put(date.name, date);

        RuleVariable dateR = new RuleVariable("DateR");
        dateR.setLabels("22/09/2021 07/08/2021 11/06/2021 24/10/2021 21/11/2021 26/11/2021");
        rb.variableList.put(dateR.name, dateR);

        RuleVariable voyage = new RuleVariable("Voyage");
        voyage.setLabels("Escale Direct");
        rb.variableList.put(voyage.name, voyage);

        RuleVariable price = new RuleVariable("Prix");
        price.setLabels("200");
        rb.variableList.put(price.name, price);

        //Define rules pt2
        Rule departAlger = new Rule(rb, "Depart Alger",
                new Clause[]{
                    new Clause(check, cEquals, "checkDepart"),
                    new Clause(depart, cEquals, "Alger")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );
        Rule departParis = new Rule(rb, "Depart Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Paris")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );

        Rule departLondre = new Rule(rb, "Depart Londre",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Londre")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );

        Rule departMontreal = new Rule(rb, "Depart Montreal",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Montreal")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );

        Rule destinationAlger = new Rule(rb, "Destination Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Alger")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        Rule destinationParis = new Rule(rb, "Destination Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Paris")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        Rule destinationLondre = new Rule(rb, "Destination Londre",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Londre")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        Rule destinationMontreal = new Rule(rb, "Destination Montreal",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Montreal"),
                        new Clause(depart, cEquals, "Alger")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        //Vérifier la date de départ
        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkDateDepart = new Rule(rb, "Check_equal_date_depart" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDate"),
                            new Clause(date, cEquals, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(price, cEquals, "200")
                    }
            );
        }


        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkNextDateDepart = new Rule(rb, "Check_next_date_depart" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDate"),
                            new Clause(date, cDateLessThan, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(date, cEquals, airAlgDates[i]),
                            new Clause(price, cEquals, "200")
                    }
            );
        }

        //Vérifier la date de retour si demandé
        Rule checkDateRetourNull = new Rule(rb, "check_date_retour_equals_null",
                new Clause[]{
                        new Clause(check, cEquals, "checkDateRetour"),
                        new Clause(dateR, cEquals, "null")
                },
                new Clause[]{
                        new Clause(check, cEquals, "checkPlaces")
                }
        );

        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkDateRetour = new Rule(rb, "Check_equal_date_retour" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(dateR, cEquals, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkPlaces"),
                            new Clause(price, cEquals, "400")
                    }
            );
        }

        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkNextDateRetour = new Rule(rb, "Check_next_date_retour" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(dateR, cDateLessThan, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(dateR, cEquals, airAlgDates[i]),
                            new Clause(price, cEquals, "400"),
                            new Clause(check, cEquals, "checkPlaces")
                    }
            );
        }

        //regles de vérification des places
        for (Vol v: airAlgvols) {
            Rule checkNbPlaces = new Rule(rb, "check_nb_places",
                    new Clause[]{
                            new Clause(check, cEquals, "checkPlaces"),
                            new Clause(depart, cEquals, v.depart),
                            new Clause(destination, cEquals, v.destination),
                            new Clause(date, cEquals, v.dateDepart),
                            new Clause(nbbillets, cLessThan, String.valueOf(v.nbPlaces))
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkEscale")
                    }
            );
        }


        //les regles d'escales

        //Alger vs Londre
        Rule voleAlgerLondre = new Rule(rb, "Vole de Alger vers Londre",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Alger"),
                        new Clause(destination,cEquals, "Londre")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        Rule voleLondreAlger = new Rule(rb, "Vole de Londre vers Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Londre"),
                        new Clause(destination,cEquals, "Alger")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        //Alger vs Paris

        Rule voleAlgerParis = new Rule(rb, "Vole de Alger vers Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Alger"),
                        new Clause(destination,cEquals, "Paris")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });

        Rule voleParisAlger = new Rule(rb, "Vole de Paris vers Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Paris"),
                        new Clause(destination,cEquals, "Alger")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });

        //Alger vs Montreal

        Rule voleAlgerMontreal = new Rule(rb, "Vole de Alger vers Montreal",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Alger"),
                        new Clause(destination,cEquals, "Montreal")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });
        Rule voleMontrealAlger = new Rule(rb, "Vole de Montreal vers Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Montreal"),
                        new Clause(destination,cEquals, "Alger")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        //Londre vs Pris
        Rule voleLondreParis = new Rule(rb, "Vole de Londre vers Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Londre"),
                        new Clause(destination,cEquals, "Paris")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });
        Rule voleParisLondre = new Rule(rb, "Vole de Paris vers Londre",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Paris"),
                        new Clause(destination,cEquals, "Londre")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });

        //Londre vs Montreal
        Rule voleLondreMontreal = new Rule(rb, "Vole de Londre vers Montreal",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Londre"),
                        new Clause(destination,cEquals, "Montreal")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });
        Rule voleMontrealLondre = new Rule(rb, "Vole de Montreal vers Londre",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Montreal"),
                        new Clause(destination,cEquals, "Londre")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        //Paris vs Montreal
        Rule voleParisMontreal = new Rule(rb, "Vole de Paris vers Montreal",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Paris"),
                        new Clause(destination,cEquals, "Montreal")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        Rule voleMontrealParis = new Rule(rb, "Vole de Montreal vers Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Montreal"),
                        new Clause(destination,cEquals, "Paris")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        Rule reductionEscaleDouble = new Rule(rb, "reduction escale",
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale"),
                        new Clause(dateR, cNotEquals, "null")
                },
                new Clause[]{
                        new Clause(price, cEquals, "350")
                }
        );

        Rule reductionEscale = new Rule(rb, "reduction escale",
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale"),
                        new Clause(dateR, cEquals, "null")
                },
                new Clause[]{
                        new Clause(price, cEquals, "150")
                }
        );

        return rb;

    }

    public static RuleBase AirFranceRuleBase(){
        RuleBase rb = new RuleBase("AirFrance");

        rb.goalClauseStack = new Stack() ;
        rb.variableList = new Hashtable() ;
        rb.ruleList = new Vector() ;

        RuleVariable check = new RuleVariable("check");
        check.setLabels("checkDepart checkDestination checkDate checkNbrBillets");
        rb.variableList.put(check.name, check);

        RuleVariable destination = new RuleVariable("Destination");
        destination.setLabels("Paris Stockholm Berlin Alger");
        rb.variableList.put(destination.name, destination);

        RuleVariable depart = new RuleVariable("Depart");
        depart.setLabels("Paris Stockholm Berlin Alger");
        rb.variableList.put(depart.name, depart);

        RuleVariable age = new RuleVariable("Age");
        age.setLabels("2 4 6 8 10 12 14 16 20 25 30 55 70 76 80");
        rb.variableList.put(age.name, age);

        RuleVariable aeroport = new RuleVariable("Aeroports");
        aeroport.setLabels("");
        rb.variableList.put(aeroport.name, aeroport);

        RuleVariable nbbillets = new RuleVariable("NbBillets");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbbillets.name, nbbillets);

        RuleVariable nbEnfants = new RuleVariable("NbEnfants");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbEnfants.name, nbEnfants);

        RuleVariable nbAdulte = new RuleVariable("NbAdultes");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbAdulte.name, nbAdulte);

        RuleVariable nbAdolescents = new RuleVariable("NbAdolescents");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbAdolescents.name, nbAdolescents);

        RuleVariable nbAged = new RuleVariable("NbAged");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbAged.name, nbAged);

        RuleVariable remiseBillet = new RuleVariable("RemiseSurBillets");
        remiseBillet.setLabels("Avec Sans");
        rb.variableList.put(remiseBillet.name, remiseBillet);

        RuleVariable remiseEscale = new RuleVariable("RemiseSurEscale");
        remiseEscale.setLabels("Avec Sans");
        rb.variableList.put(remiseEscale.name, remiseEscale);

        RuleVariable saison = new RuleVariable("Saison");
        saison.setLabels("Saison_Estivale Hors_Saison_Estivale");
        rb.variableList.put(saison.name, saison);

        RuleVariable agereduction = new RuleVariable("AgeReduction");
        agereduction.setLabels("10% 15% 20%");
        rb.variableList.put(agereduction.name, agereduction);

        RuleVariable date = new RuleVariable("Date");
        date.setLabels("22/09/2021 07/08/2021 11/06/2021 24/10/2021 21/11/2021 26/11/2021");
        rb.variableList.put(date.name, date);

        RuleVariable dateR = new RuleVariable("DateR");
        dateR.setLabels("22/09/2021 07/08/2021 11/06/2021 24/10/2021 21/11/2021 26/11/2021");
        rb.variableList.put(dateR.name, dateR);

        RuleVariable voyage = new RuleVariable("Voyage");
        voyage.setLabels("Escale Direct");
        rb.variableList.put(voyage.name, voyage);

        RuleVariable price = new RuleVariable("Prix");
        price.setLabels("200");
        rb.variableList.put(price.name, price);

        //Define rules pt2
        Rule departAlger = new Rule(rb, "Depart Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Alger")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );
        Rule departParis = new Rule(rb, "Depart Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Paris")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );

        Rule departLondre = new Rule(rb, "Depart Berlin",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Berlin")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );

        Rule departMontreal = new Rule(rb, "Depart Stockholm",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Stockholm")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );

        Rule destinationAlger = new Rule(rb, "Destination Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Alger")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        Rule destinationParis = new Rule(rb, "Destination Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Paris")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        Rule destinationBerlin = new Rule(rb, "Destination Berlin",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Berlin")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        Rule destinationMontreal = new Rule(rb, "Destination Stockholm",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Stockholm"),
                        new Clause(depart, cEquals, "Alger")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        //Vérifier la date de départ
        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkDateDepart = new Rule(rb, "Check_equal_date_depart" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDate"),
                            new Clause(date, cEquals, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(price, cEquals, "200")
                    }
            );
        }


        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkNextDateDepart = new Rule(rb, "Check_next_date_depart" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDate"),
                            new Clause(date, cDateLessThan, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(date, cEquals, airAlgDates[i]),
                            new Clause(price, cEquals, "200")
                    }
            );
        }

        //Vérifier la date de retour si demandé
        Rule checkDateRetourNull = new Rule(rb, "check_date_retour_equals_null",
                new Clause[]{
                        new Clause(check, cEquals, "checkDateRetour"),
                        new Clause(dateR, cEquals, "null")
                },
                new Clause[]{
                        new Clause(check, cEquals, "checkPlaces")
                }
        );

        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkDateRetour = new Rule(rb, "Check_equal_date_retour" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(dateR, cEquals, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkPlaces"),
                            new Clause(price, cEquals, "500")
                    }
            );
        }

        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkNextDateRetour = new Rule(rb, "Check_next_date_retour" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(dateR, cDateLessThan, airAlgDates[i]),
                            new Clause(dateR, cNotEquals, String.valueOf(date.value))
                    },
                    new Clause[]{
                            new Clause(dateR, cEquals, airAlgDates[i]),
                            new Clause(price, cEquals, "500"),
                            new Clause(check, cEquals, "checkPlaces")
                    }
            );
        }

        //regles de vérification des places
        for (Vol v: airFRvols) {
            Rule checkNbPlaces = new Rule(rb, "check_nb_places",
                    new Clause[]{
                            new Clause(check, cEquals, "checkPlaces"),
                            new Clause(depart, cEquals, v.depart),
                            new Clause(destination, cEquals, v.destination),
                            new Clause(date, cEquals, v.dateDepart),
                            new Clause(nbbillets, cLessThan, String.valueOf(v.nbPlaces))
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkEscale")
                    }
            );
        }


        //les regles d'escales

        //Alger vs Londre
        Rule voleAlgerLondre = new Rule(rb, "Vole de Alger vers Stockholm",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Alger"),
                        new Clause(destination,cEquals, "Berlin")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        Rule voleLondreAlger = new Rule(rb, "Vole de Stockholm vers Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Stockholm"),
                        new Clause(destination,cEquals, "Alger")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        //Alger vs Paris

        Rule voleAlgerParis = new Rule(rb, "Vole de Alger vers Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Alger"),
                        new Clause(destination,cEquals, "Paris")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });

        Rule voleParisAlger = new Rule(rb, "Vole de Paris vers Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Paris"),
                        new Clause(destination,cEquals, "Alger")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });

        //Alger vs Montreal

        Rule voleAlgerMontreal = new Rule(rb, "Vole de Alger vers Berlin",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Alger"),
                        new Clause(destination,cEquals, "Berlin")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });
        Rule voleMontrealAlger = new Rule(rb, "Vole de Stockholm vers Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Stockholm"),
                        new Clause(destination,cEquals, "Alger")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        //Londre vs Pris
        Rule voleLondreParis = new Rule(rb, "Vole de Berlin vers Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Berlin"),
                        new Clause(destination,cEquals, "Paris")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });
        Rule voleParisLondre = new Rule(rb, "Vole de Paris vers Stockholm",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Paris"),
                        new Clause(destination,cEquals, "Stockholm")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });

        Rule reductionEscaleDouble = new Rule(rb, "reduction escale",
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale"),
                        new Clause(dateR, cNotEquals, "null")
                },
                new Clause[]{
                        new Clause(price, cEquals, "350")
                }
        );

        Rule reductionEscale = new Rule(rb, "reduction escale",
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale"),
                        new Clause(dateR, cEquals, "null")
                },
                new Clause[]{
                        new Clause(price, cEquals, "150")
                }
        );

        return rb;

    }

    public static RuleBase AirGBARuleBase(){
        RuleBase rb = new RuleBase("British airline");

        rb.goalClauseStack = new Stack() ;
        rb.variableList = new Hashtable() ;
        rb.ruleList = new Vector() ;

        RuleVariable check = new RuleVariable("check");
        check.setLabels("checkDepart checkDestination checkDate checkNbrBillets");
        rb.variableList.put(check.name, check);

        RuleVariable destination = new RuleVariable("Destination");
        destination.setLabels("Paris Stockholm Berlin Alger");
        rb.variableList.put(destination.name, destination);

        RuleVariable depart = new RuleVariable("Depart");
        depart.setLabels("Paris Stockholm Berlin Alger");
        rb.variableList.put(depart.name, depart);

        RuleVariable age = new RuleVariable("Age");
        age.setLabels("2 4 6 8 10 12 14 16 20 25 30 55 70 76 80");
        rb.variableList.put(age.name, age);

        RuleVariable aeroport = new RuleVariable("Aeroports");
        aeroport.setLabels("");
        rb.variableList.put(aeroport.name, aeroport);

        RuleVariable nbbillets = new RuleVariable("NbBillets");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbbillets.name, nbbillets);

        RuleVariable nbEnfants = new RuleVariable("NbEnfants");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbEnfants.name, nbEnfants);

        RuleVariable nbAdulte = new RuleVariable("NbAdultes");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbAdulte.name, nbAdulte);

        RuleVariable nbAdolescents = new RuleVariable("NbAdolescents");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbAdolescents.name, nbAdolescents);

        RuleVariable nbAged = new RuleVariable("NbAged");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put(nbAged.name, nbAged);

        RuleVariable remiseBillet = new RuleVariable("RemiseSurBillets");
        remiseBillet.setLabels("Avec Sans");
        rb.variableList.put(remiseBillet.name, remiseBillet);

        RuleVariable remiseEscale = new RuleVariable("RemiseSurEscale");
        remiseEscale.setLabels("Avec Sans");
        rb.variableList.put(remiseEscale.name, remiseEscale);

        RuleVariable saison = new RuleVariable("Saison");
        saison.setLabels("Saison_Estivale Hors_Saison_Estivale");
        rb.variableList.put(saison.name, saison);

        RuleVariable agereduction = new RuleVariable("AgeReduction");
        agereduction.setLabels("10% 15% 20%");
        rb.variableList.put(agereduction.name, agereduction);

        RuleVariable date = new RuleVariable("Date");
        date.setLabels("22/09/2021 07/08/2021 11/06/2021 24/10/2021 21/11/2021 26/11/2021");
        rb.variableList.put(date.name, date);

        RuleVariable dateR = new RuleVariable("DateR");
        dateR.setLabels("22/09/2021 07/08/2021 11/06/2021 24/10/2021 21/11/2021 26/11/2021");
        rb.variableList.put(dateR.name, dateR);

        RuleVariable voyage = new RuleVariable("Voyage");
        voyage.setLabels("Escale Direct");
        rb.variableList.put(voyage.name, voyage);

        RuleVariable price = new RuleVariable("Prix");
        price.setLabels("200");
        rb.variableList.put(price.name, price);

        //Define rules pt2
        Rule departAlger = new Rule(rb, "Depart Londre",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Londre")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );
        Rule departParis = new Rule(rb, "Depart Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Paris")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );

        Rule departLondre = new Rule(rb, "Depart Madrid",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Madrid")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );

        Rule departMontreal = new Rule(rb, "Depart Amsterdam",
                new Clause[]{
                        new Clause(check, cEquals, "checkDepart"),
                        new Clause(depart, cEquals, "Amsterdam")
                },
                new Clause[]{new Clause(check, cEquals, "checkDestination")}
        );

        Rule destinationAlger = new Rule(rb, "Destination Londre",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Londre")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        Rule destinationParis = new Rule(rb, "Destination Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Paris")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        Rule destinationBerlin = new Rule(rb, "Destination Madrid",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Madrid")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        Rule destinationMontreal = new Rule(rb, "Destination Amsterdam",
                new Clause[]{
                        new Clause(check, cEquals, "checkDestination"),
                        new Clause(destination, cEquals, "Amsterdam"),
                        new Clause(depart, cEquals, "Londre")
                },
                new Clause[]{new Clause(check, cEquals, "checkDate")}
        );

        //Vérifier la date de départ
        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkDateDepart = new Rule(rb, "Check_equal_date_depart" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDate"),
                            new Clause(date, cEquals, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(price, cEquals, "200")
                    }
            );
        }


        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkNextDateDepart = new Rule(rb, "Check_next_date_depart" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDate"),
                            new Clause(date, cDateLessThan, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(date, cEquals, airAlgDates[i]),
                            new Clause(price, cEquals, "200")
                    }
            );
        }

        //Vérifier la date de retour si demandé
        Rule checkDateRetourNull = new Rule(rb, "check_date_retour_equals_null",
                new Clause[]{
                        new Clause(check, cEquals, "checkDateRetour"),
                        new Clause(dateR, cEquals, "null")
                },
                new Clause[]{
                        new Clause(check, cEquals, "checkPlaces")
                }
        );

        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkDateRetour = new Rule(rb, "Check_equal_date_retour" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(dateR, cEquals, airAlgDates[i])
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkPlaces"),
                            new Clause(price, cEquals, "500")
                    }
            );
        }

        for (int i = 0; i < airAlgDates.length; i++) {
            Rule checkNextDateRetour = new Rule(rb, "Check_next_date_retour" + i,
                    new Clause[]{
                            new Clause(check, cEquals, "checkDateRetour"),
                            new Clause(dateR, cDateLessThan, airAlgDates[i]),
                            new Clause(dateR, cNotEquals, String.valueOf(date.value))
                    },
                    new Clause[]{
                            new Clause(dateR, cEquals, airAlgDates[i]),
                            new Clause(price, cEquals, "500"),
                            new Clause(check, cEquals, "checkPlaces")
                    }
            );
        }

        //regles de vérification des places
        for (Vol v: airFRvols) {
            Rule checkNbPlaces = new Rule(rb, "check_nb_places",
                    new Clause[]{
                            new Clause(check, cEquals, "checkPlaces"),
                            new Clause(depart, cEquals, v.depart),
                            new Clause(destination, cEquals, v.destination),
                            new Clause(date, cEquals, v.dateDepart),
                            new Clause(nbbillets, cLessThan, String.valueOf(v.nbPlaces))
                    },
                    new Clause[]{
                            new Clause(check, cEquals, "checkEscale")
                    }
            );
        }


        //les regles d'escales

        //Alger vs Londre
        Rule voleAlgerLondre = new Rule(rb, "Vole de Alger vers Stockholm",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Madrid"),
                        new Clause(destination,cEquals, "Amsterdam")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        Rule voleLondreAlger = new Rule(rb, "Vole de Stockholm vers Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Amsterdam"),
                        new Clause(destination,cEquals, "Madrid")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        //Alger vs Paris

        Rule voleAlgerParis = new Rule(rb, "Vole de Alger vers Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Londre"),
                        new Clause(destination,cEquals, "Paris")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });

        Rule voleParisAlger = new Rule(rb, "Vole de Paris vers Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Paris"),
                        new Clause(destination,cEquals, "Londre")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });

        //Alger vs Montreal

        Rule voleAlgerMontreal = new Rule(rb, "Vole de Alger vers Berlin",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Londre"),
                        new Clause(destination,cEquals, "Berlin")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });
        Rule voleMontrealAlger = new Rule(rb, "Vole de Stockholm vers Alger",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Stockholm"),
                        new Clause(destination,cEquals, "Alger")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale")
                });

        //Londre vs Pris
        Rule voleLondreParis = new Rule(rb, "Vole de Berlin vers Paris",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Berlin"),
                        new Clause(destination,cEquals, "Paris")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });
        Rule voleParisLondre = new Rule(rb, "Vole de Paris vers Stockholm",
                new Clause[]{
                        new Clause(check, cEquals, "checkEscale"),
                        new Clause(depart, cEquals, "Paris"),
                        new Clause(destination,cEquals, "Stockholm")
                },
                new Clause[]{
                        new Clause(voyage, cEquals, "Direct")
                });

        Rule reductionEscaleDouble = new Rule(rb, "reduction escale",
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale"),
                        new Clause(dateR, cNotEquals, "null")
                },
                new Clause[]{
                        new Clause(price, cEquals, "350")
                }
        );

        Rule reductionEscale = new Rule(rb, "reduction escale",
                new Clause[]{
                        new Clause(voyage, cEquals, "Escale"),
                        new Clause(dateR, cEquals, "null")
                },
                new Clause[]{
                        new Clause(price, cEquals, "150")
                }
        );

        return rb;

    }

}
