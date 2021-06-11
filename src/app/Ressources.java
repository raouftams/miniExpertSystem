package app;

import app.rule.*;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

public abstract class Ressources {

    private static Condition cEquals = new Condition("=") ;
    private static Condition cNotEquals = new Condition("!=") ;
    private static Condition cLessThan = new Condition("<") ;
    private static Condition cMoreThan = new Condition(">") ;
    private static Condition cLessThanOrEquals = new Condition("<=");
    private static Condition cMoreThanOrEquals = new Condition(">=");

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
                new Clause(userBudget, cMoreThanOrEquals, "700"),
                new Clause(phoneRange, cEquals, "flagship")
        );

        Rule midRange = new Rule(rb, "check_midrange",
                new Clause(userBudget, cLessThan, "700"),
                new Clause(userBudget, cMoreThanOrEquals, "300"),
                new Clause(phoneRange, cEquals, "midrange")
        );

        Rule budgetRange = new Rule(rb, "check_budget",
                new Clause(userBudget, cLessThan, "300"),
                new Clause(phoneRange, cEquals, "budget")
        );

        Rule highSecurity = new Rule(rb, "high_security",
                new Clause(securityLevel, cEquals, "high"),
                new Clause(os, cEquals, "ios")
        );

        Rule midSecurity = new Rule(rb, "medium_security",
                new Clause(securityLevel, cEquals, "medium"),
                new Clause(os, cEquals, "android")
        );

        Rule businessUtility = new Rule(rb, "business_utility",
                new Clause(utility, cEquals, "business"),
                new Clause(securityLevel, cEquals, "high")
        );

        Rule gamingUtility = new Rule(rb, "gaming_utility",
                new Clause(utility, cEquals, "gaming"),
                new Clause(securityLevel, cEquals, "medium")
        );

        Rule communicationUtility = new Rule(rb, "communication_utility",
                new Clause(utility, cEquals, "communication"),
                new Clause(securityLevel, cEquals, "medium")
        );

        Rule appleUi = new Rule(rb, "apple_os",
                new Clause(os, cEquals, "ios"),
                new Clause(brand, cEquals, "apple")
        );

        Rule googleUi = new Rule(rb, "google_os",
                new Clause(os, cEquals, "android"),
                new Clause(ui, cEquals, "none"),
                new Clause(brand, cEquals, "google")
        );

        Rule samsungUi = new Rule(rb, "samsung_ui",
                new Clause(os, cEquals, "android"),
                new Clause(ui, cEquals, "oneui"),
                new Clause(brand, cEquals, "samsung")
        );

        Rule xiaomiUi = new Rule(rb, "xiaomi_ui",
                new Clause(os, cEquals, "android"),
                new Clause(ui, cEquals, "miui"),
                new Clause(brand, cEquals, "xiaomi")
        );

        Rule gamingPhone = new Rule(rb, "gaming_phone",
                new Clause(os, cEquals, "android"),
                new Clause(utility, cEquals, "gaming"),
                new Clause(brand, cEquals, "asus")
        );

        return rb;
    }

    public static RuleBase AirAlgerieRuleBase(){
        RuleBase rb = new RuleBase("Air algerie");

        rb.goalClauseStack = new Stack() ;
        rb.variableList = new Hashtable() ;
        rb.ruleList = new Vector() ;

        RuleVariable check = new RuleVariable("check");
        check.setLabels("checkDepart checkDestination checkDate checkNbrBillets");
        rb.variableList.put((String) check.name, check);

        RuleVariable destination = new RuleVariable("Destination");
        destination.setLabels("Paris Montreal Londre Alger");
        rb.variableList.put((String) destination.name, destination);

        RuleVariable depart = new RuleVariable("Depart");
        depart.setLabels("Paris Montreal Londre Alger");
        rb.variableList.put((String) depart.name, depart);

        RuleVariable age = new RuleVariable("Age");
        age.setLabels("2 4 6 8 10 12 14 16 20 25 30 55 70 76 80");
        rb.variableList.put((String) age.name, age);

        RuleVariable aeroport = new RuleVariable("Aeroports");
        aeroport.setLabels("Charles_De_Gaulle(FRA) Lyon_Saint_Exupéry(FRA) " +
                "Montréal_Mirabel(CAN) Muskoka(CAN) " +
                "Oran-Es_Senia(DZA) Houari_Boumédiène(DZA) " +
                "Heathrow(GBA) Birmingham(GBA) " +
                "Shanghai-Pudong(CHN) Canton-Baiyun(CHN)");
        rb.variableList.put((String) aeroport.name, aeroport);

        RuleVariable nbbillets = new RuleVariable("NbBillets");
        nbbillets.setLabels("1 2 3 4 5 6 7 8 9");
        rb.variableList.put((String) nbbillets.name, nbbillets);

        RuleVariable remiseBillet = new RuleVariable("RemiseSurBillets");
        remiseBillet.setLabels("Avec Sans");
        rb.variableList.put((String) remiseBillet.name, remiseBillet);

        RuleVariable remiseEscale = new RuleVariable("RemiseSurEscale");
        remiseEscale.setLabels("Avec Sans");
        rb.variableList.put((String) remiseEscale.name, remiseEscale);

        RuleVariable saison = new RuleVariable("Saison");
        saison.setLabels("Saison_Estivale Hors_Saison_Estivale");
        rb.variableList.put((String) saison.name, saison);

        RuleVariable agereduction = new RuleVariable("AgeReduction");
        agereduction.setLabels("10% 15% 20%");
        rb.variableList.put((String) agereduction.name, agereduction);

        RuleVariable date = new RuleVariable("Date");
        agereduction.setLabels("22/09/2021 07/08/2021 11/06/2021 24/10/2021 21/11/2021 26/11/2021");
        rb.variableList.put((String) agereduction.name, agereduction);

        RuleVariable voyage = new RuleVariable("Voyage");
        voyage.setLabels("Escale Direct");
        rb.variableList.put((String) voyage.name, voyage);

        //Define rules pt2
        Rule departAlger = new Rule(rb, "Depart Alger",
                new Clause(check, cEquals, "checkDepart"),
                new Clause(depart, cEquals, "Alger"),
                new Clause(check, cEquals, "checkDestination")
        );
        Rule departParis = new Rule(rb, "Depart Paris",
                new Clause(check, cEquals, "checkDepart"),
                new Clause(depart, cEquals, "Paris"),
                new Clause(check, cEquals, "checkDestination")
        );

        Rule departLondre = new Rule(rb, "Depart Londre",
                new Clause(check, cEquals, "checkDepart"),
                new Clause(depart, cEquals, "Londre"),
                new Clause(check, cEquals, "checkDestination")
        );

        Rule departMontreal = new Rule(rb, "Depart Montreal",
                new Clause(check, cEquals, "checkDepart"),
                new Clause(depart, cEquals, "Montreal"),
                new Clause(check, cEquals, "checkDestination")
        );

        Rule destinationAlger = new Rule(rb, "Destination Alger",
                new Clause(check, cEquals, "checkDestination"),
                new Clause(destination, cEquals, "Alger"),
                new Clause(check, cEquals, "checkDate")
        );

        Rule destinationParis = new Rule(rb, "Destination Paris",
                new Clause(check, cEquals, "checkDestination"),
                new Clause(destination, cEquals, "Paris"),
                new Clause(depart, cEquals, "Alger"),
                new Clause(check, cEquals, "checkDate")
        );

        Rule destinationLondre = new Rule(rb, "Destination Londre",
                new Clause(check, cEquals, "checkDestination"),
                new Clause(destination, cEquals, "Londre"),
                new Clause(depart, cEquals, "Alger"),
                new Clause(check, cEquals, "checkDate")
        );

        Rule destinationMontreal = new Rule(rb, "Destination Montreal",
                new Clause(check, cEquals, "checkDestination"),
                new Clause(destination, cEquals, "Montreal"),
                new Clause(depart, cEquals, "Alger"),
                new Clause(check, cEquals, "checkDate")
        );


        Rule AgeePriceReduction = new Rule(rb, "reduction for elders",
                new Clause(age, cMoreThanOrEquals, "75"),
                new Clause(agereduction, cEquals, "10%")
        );

        Rule EnfPriceReduction = new Rule(rb, "reduction for children",
                new Clause(age, cLessThan, "7"),
                new Clause(agereduction, cEquals, "20%")
        );


        Rule reductionBillet = new Rule(rb, "Reduction sur billets",
                new Clause(nbbillets, cMoreThanOrEquals, "4"),
                new Clause(remiseBillet, cEquals, "Avec")
        );

        Rule reductionEscale = new Rule(rb, "Reduction sur Escale",
                new Clause(voyage, cEquals, "Escale"),
                new Clause(remiseEscale, cEquals, "Avec")
        );

        return rb;

    }


}
