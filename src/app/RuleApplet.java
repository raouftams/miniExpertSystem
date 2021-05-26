package app;
import java.awt.*;
import java.applet.*;
import java.util.* ;

public class RuleApplet extends Applet {

    // user selected a rule base
    void choice1_Clicked() {
        String rbName = choice1.getSelectedItem() ;
        ///modif
        choice2.removeAll();



        if (rbName.equals("Vendeur")) currentRuleBase = vendeur;
        if (rbName.equals("Acheteur")) currentRuleBase = acheteur;
        if (rbName.equals("smartphone")) currentRuleBase = smartphone;


        currentRuleBase.reset() ;  // reset the rule base
        Enumeration vars = currentRuleBase.variableList.elements() ;
        while (vars.hasMoreElements()) {
            choice2.addItem(((RuleVariable)vars.nextElement()).name) ;
        }
        currentRuleBase.displayVariables(textArea3) ;

    }









    // user selected a value for a variable
    void choice3_Clicked(Event event) {
        String varName = choice2.getSelectedItem() ;
        String varValue = choice3.getSelectedItem() ;

        RuleVariable rvar = (RuleVariable)currentRuleBase.variableList.get(varName);
        rvar.setValue(varValue) ;
        textArea3.appendText("\n" + rvar.name + " set to " + varValue) ;

    }

    // user pressed Find button -- do an infernece cycle
    void button1_Clicked(Event event) {
        String goal = textField1.getText() ;
        //manylmodif


        currentRuleBase.displayVariables(textArea2) ;
        if (radioButton1.getState() == true) currentRuleBase.forwardChain();
        if (radioButton2.getState() == true) currentRuleBase.backwardChain(goal);

        currentRuleBase.displayVariables(textArea2) ;

    }

    // user pressed Demo button -- do inference with pre-set values


    void button3_Clicked(Event event) {

        //{{CONNECTION
        // Clear the text for TextArea
        textArea1.setText("");
        textArea2.setText("");
        textArea3.setText("");
        //}}

        currentRuleBase.reset() ;
        currentRuleBase.displayRules(textArea1);
        currentRuleBase.displayVariables(textArea3) ;
    }

    // display dialog to get user value for a variable
    static public String waitForAnswer(String prompt, String labels) {

        // position dialog over parent dialog
        Point p = frame.getLocation() ;
        dlg = new RuleVarDialog(frame, true) ;
        dlg.label1.setText("   " + prompt + " (" + labels + ")");
        dlg.setLocation(400, 250) ;
        dlg.show() ;
        String ans = dlg.getText() ;
        return ans ;

    }


    public void init() {
        super.init();


        //{{INIT_CONTROLS
        setLayout(null);
        addNotify();
        resize(624,527);
        button1 = new java.awt.Button("Find Goal");
        button1.reshape(228,468,108,30);
        add(button1);
        button3 = new java.awt.Button("Reset");
        button3.reshape(444,468,108,30);
        add(button3);
        textArea1 = new java.awt.TextArea();
        textArea1.reshape(12,48,312,144);
        add(textArea1);
        textArea2 = new java.awt.TextArea();
        textArea2.reshape(12,216,600,168);
        add(textArea2);
        label2 = new java.awt.Label("Trace Log");
        label2.reshape(24,192,168,24);
        add(label2);
        label1 = new java.awt.Label("Rule Base");
        label1.reshape(24,12,96,24);
        add(label1);
        choice1 = new java.awt.Choice();
        add(choice1);
        choice1.reshape(132,12,192,24);
        Group1 = new CheckboxGroup();
        radioButton1 = new java.awt.Checkbox("Forward Chain", Group1, false);
        radioButton1.reshape(36,396,156,21);
        add(radioButton1);
        choice3 = new java.awt.Choice();
        add(choice3);
        choice3.reshape(480,36,135,24);
        label5 = new java.awt.Label("Value");
        label5.reshape(480,12,95,24);
        add(label5);
        choice2 = new java.awt.Choice();
        add(choice2);
        choice2.reshape(336,36,137,24);
        textArea3 = new java.awt.TextArea();
        textArea3.reshape(336,72,276,122);
        add(textArea3);
        label4 = new java.awt.Label("Variable");
        label4.reshape(336,12,109,24);
        add(label4);
        radioButton2 = new java.awt.Checkbox("Backward Chain", Group1, false);
        radioButton2.reshape(36,420,156,24);
        add(radioButton2);

        textField1 = new java.awt.TextField();
        textField1.reshape(324,420,142,27);
        add(textField1);
        label3 = new java.awt.Label("Goal");
        label3.reshape(324,384,80,30);
        add(label3);
        //}}

        frame = new Frame("Ask User") ;
        frame.resize(50,50) ;
        frame.setLocation(100,100) ;


        choice1.addItem("Vendeur");
        choice1.addItem("Acheteur");
        choice1.addItem("smartphone");



        vendeur = new RuleBase("Vendeur") ;
        vendeur.setDisplay(textArea2);
        initVendeurRuleBase(vendeur);
        currentRuleBase =vendeur;

        acheteur = new RuleBase("acheteur") ;
        acheteur.setDisplay(textArea2);
        initAchteurRuleBase(acheteur);

        smartphone = new RuleBase("smarphone");
        smartphone.setDisplay(textArea2);
        initSmartphoneRuleBase(smartphone);






        // initialize textAreas and list controls
        currentRuleBase.displayRules(textArea1) ;

        currentRuleBase.displayVariables(textArea3) ;
        radioButton1.setState(true) ;
        choice1_Clicked() ; // fill variable list
    }

    public void initSmartphoneRuleBase(RuleBase rb) {
        rb.goalClauseStack = new Stack();
        rb.variableList = new Hashtable();
        rb.ruleList = new Vector() ;
        RuleBase currentRuleBase = rb;

        //Define variables
        RuleVariable brand = new RuleVariable("Brand");
        brand.setLabels("apple google samsung xiaomi asus");
        rb.variableList.put(brand.name, brand);

        RuleVariable os = new RuleVariable("OS");
        os.setLabels("android ios");
        rb.variableList.put(os.name, os);

        RuleVariable ui = new RuleVariable("UI");
        ui.setLabels("none miui oneui ");
        rb.variableList.put(ui.name, ui);

        RuleVariable securityLevel = new RuleVariable("Security level");
        securityLevel.setLabels("high medium");
        rb.variableList.put(securityLevel.name, securityLevel);

        RuleVariable phoneRange = new RuleVariable("Phone range");
        phoneRange.setLabels("flagship midrange budget");
        rb.variableList.put(phoneRange.name, phoneRange);

        RuleVariable utility = new RuleVariable("Utility");
        utility.setLabels("business communication gaming");
        rb.variableList.put(utility.name, utility);

        RuleVariable userBudget = new RuleVariable("User budget");
        userBudget.setLabels("200 300 400 500 600 700 800 900 1000 1100 1200");
        rb.variableList.put(userBudget.name, userBudget);

        Condition cEquals = new Condition("=") ;
        Condition cNotEquals = new Condition("!=") ;
        Condition cLessThan = new Condition("<") ;
        Condition cMoreThan = new Condition(">") ;
        Condition cLessThanOrEquals = new Condition("<=");
        Condition cMoreThanOrEquals = new Condition(">=");


        //Define rules
        Rule flagshipRange = new Rule(rb, "check flagship",
                new Clause(userBudget, cMoreThanOrEquals, "700"),
                new Clause(phoneRange, cEquals, "flagship")
        );

        Rule midRange = new Rule(rb, "check midrange",
                new Clause(userBudget, cLessThan, "700"),
                new Clause(userBudget, cMoreThanOrEquals, "300"),
                new Clause(phoneRange, cEquals, "midrange")
        );

        Rule budgetRange = new Rule(rb, "check budget",
                new Clause(userBudget, cLessThan, "300"),
                new Clause(phoneRange, cEquals, "budget")
        );

        Rule highSecurity = new Rule(rb, "high security",
                new Clause(securityLevel, cEquals, "high"),
                new Clause(os, cEquals, "ios")
        );

        Rule midSecurity = new Rule(rb, "medium security",
                new Clause(securityLevel, cEquals, "medium"),
                new Clause(os, cEquals, "android")
        );

        Rule businessUtility = new Rule(rb, "business utility",
                new Clause(utility, cEquals, "business"),
                new Clause(securityLevel, cEquals, "high")
        );

        Rule gamingUtility = new Rule(rb, "gaming utility",
                new Clause(utility, cEquals, "gaming"),
                new Clause(securityLevel, cEquals, "medium")
        );

        Rule communicationUtility = new Rule(rb, "communication utility",
                new Clause(utility, cEquals, "communication"),
                new Clause(securityLevel, cEquals, "medium")
        );

        Rule appleUi = new Rule(rb, "apple os",
                new Clause(os, cEquals, "ios"),
                new Clause(brand, cEquals, "apple")
        );

        Rule googleUi = new Rule(rb, "google os",
                new Clause(os, cEquals, "android"),
                new Clause(ui, cEquals, "none"),
                new Clause(brand, cEquals, "google")
        );

        Rule samsungUi = new Rule(rb, "samsung ui",
                new Clause(os, cEquals, "android"),
                new Clause(ui, cEquals, "oneui"),
                new Clause(brand, cEquals, "samsung")
        );

        Rule xiaomiUi = new Rule(rb, "xiaomi ui",
                new Clause(os, cEquals, "android"),
                new Clause(ui, cEquals, "miui"),
                new Clause(brand, cEquals, "xiaomi")
        );

        Rule gamingPhone = new Rule(rb, "gaming phone",
                new Clause(os, cEquals, "android"),
                new Clause(utility, cEquals, "gaming"),
                new Clause(brand, cEquals, "asus")
        );
    }


    public boolean handleEvent(Event event) {
        if (event.target == button1 && event.id == Event.ACTION_EVENT) {
            button1_Clicked(event);
            return true;
        }
        if (event.target == button3 && event.id == Event.ACTION_EVENT) {
            button3_Clicked(event);
            return true;
        }
        if (event.target == dlg && event.id == Event.ACTION_EVENT) {
            return dlg.handleEvent(event);
        }
        if (event.target == choice1 && event.id == Event.ACTION_EVENT) {
            choice1_Clicked();
            return true;
        }
        if (event.target == choice3 && event.id == Event.ACTION_EVENT) {
            choice3_Clicked(event);
            return true;
        }
        return super.handleEvent(event);
    }

    //{{DECLARE_CONTROLS
    java.awt.Button button1;
    //java.awt.Button button2;
    java.awt.Button button3;
    java.awt.TextArea textArea1;
    java.awt.TextArea textArea2;
    java.awt.Label label2;
    java.awt.Label label1;
    java.awt.Choice choice1;
    java.awt.Checkbox radioButton1;
    CheckboxGroup Group1;
    java.awt.Choice choice3;
    java.awt.Label label5;
    java.awt.Choice choice2;
    java.awt.TextArea textArea3;
    java.awt.Label label4;
    java.awt.Checkbox radioButton2;
    ///manyl modif
    //java.awt.Checkbox radioButton3;
    java.awt.TextField textField1;
    java.awt.Label label3;
    //}}

    static Frame frame ;
    static RuleVarDialog dlg ;

    static RuleBase smartphone ;
    static RuleBase vendeur;
    static RuleBase currentRuleBase ;
    static RuleBase acheteur;




    // initialize the Bugs rule base

// initialize the Plants rule base

    //////vendeur
    public void initVendeurRuleBase(RuleBase rb) {
        rb.goalClauseStack = new Stack() ;
        rb.variableList = new Hashtable() ;




        RuleVariable marque = new RuleVariable("marque") ;
        marque.setLabels("BMW AUDI FORD");

        rb.variableList.put(marque.name,marque) ;
        RuleVariable article_disponible = new RuleVariable("article_disponible") ;
        article_disponible.setLabels("non oui");

        rb.variableList.put(article_disponible.name,article_disponible) ;

        RuleVariable type = new RuleVariable("type") ;
        type.setLabels("2-port 4-port");

        rb.variableList.put(type.name,type) ;
        RuleVariable color = new RuleVariable("color") ;
        color.setLabels("noir rouge blanc vert jeune");

        rb.variableList.put(color.name,color) ;
        RuleVariable vitess = new RuleVariable("vitess") ;
        vitess.setLabels("5 6");

        rb.variableList.put(vitess.name,vitess) ;



        RuleVariable prix_vente = new RuleVariable("prix_vente") ;
        prix_vente.setLabels("3500 4000 5000 7000 8000 9000 1200 1400");

        rb.variableList.put(prix_vente.name,prix_vente) ;







        RuleVariable vendue = new RuleVariable("resultat") ;
        vendue.setLabels("");

        rb.variableList.put(vendue.name,vendue) ;









        // Note: at this point all variables values are NULL

        Condition cEquals = new Condition("=") ;
        Condition cNotEquals = new Condition("!=") ;
        Condition cLessThan = new Condition("<") ;
        Condition cMoreThan = new Condition(">") ;
        rb.ruleList = new Vector() ;


        // define rules
        Rule vent_voiturea = new Rule(rb, "regle1:verifie la marque: BMW" ,

                new Clause(marque,cEquals,"BMW")  ,
                new Clause(vendue, cEquals, "verifier la disponibilité"));

        Rule vente_automobileeeez = new Rule(rb, "AUDI",

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(vendue, cEquals, "verifier la disponibilité"));
        Rule vente_automobileeeeEr = new Rule(rb, "FORD",

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(vendue, cEquals, "verifier la disponibilité"));
        Rule vent_voitureet = new Rule(rb, "regle2:verifier la disponibilité BMW" ,


                new Clause(marque,cEquals,"BMW")  ,

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(vendue, cEquals, "verifier la color"));

        Rule vente_automobileeeeey = new Rule(rb, "AUDI",

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(vendue, cEquals, "verifier la color"));

        Rule vente_automobileeeeEeu = new Rule(rb, "FORD",

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(vendue, cEquals, "verifier la color"));
        Rule vent_voitureeei = new Rule(rb, "regle3:verifier la color BMW" ,

                new Clause(marque,cEquals,"BMW")  ,

                new Clause(color,cEquals, "rouge"),
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(vendue, cEquals, "verifier le type"));
        Rule vent_voitureeeeo = new Rule(rb, "FORD" ,

                new Clause(marque,cEquals,"FORD")  ,

                new Clause(color,cEquals, "vert"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(vendue, cEquals, "verifier le type"));

        Rule vent_voitureeeeep = new Rule(rb, "AUDI" ,

                new Clause(marque,cEquals,"AUDI")  ,

                new Clause(color,cEquals, "blanc"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(vendue, cEquals, "verifier le type"));
        Rule vent_voiturerrq = new Rule(rb, "regle4:verifier le type: BMW" ,

                new Clause(marque,cEquals,"BMW")  ,

                new Clause(color,cEquals, "rouge"),
                new Clause(article_disponible, cEquals, "oui"),
                new Clause(type,cEquals, "2-port"),
                new Clause(vendue, cEquals, "verifier la vitess"));

        Rule vente_automobileeeeees = new Rule(rb, "AUDI",

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(color,cEquals,"blanc"),
                new Clause(type,cEquals, "4-port"),

                new Clause(vendue, cEquals, "verifier la vitess"));
        Rule vente_automobileeeeEeed = new Rule(rb, "FORD",

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(color,cEquals,"vert"),
                new Clause(type,cEquals, "4-port"),
                new Clause(vendue, cEquals, "verifier la vitess"));
        Rule vent_voitureefj = new Rule(rb, "regle5:verifier la vitess BMW" ,

                new Clause(marque,cEquals,"BMW")  ,

                new Clause(color,cEquals, "rouge"),
                new Clause(type,cEquals, "2-port"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(vitess, cEquals, "6"),

                new Clause(vendue, cEquals, "verifier le prix"));

        Rule vente_automobileeeeeog = new Rule(rb, "AUDI",

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(color,cEquals,"blanc"),
                new Clause(vitess, cEquals, "5"),
                new Clause(type,cEquals, "4-port"),
                new Clause(vendue, cEquals, "verifier le prix"));

        Rule vente_automobileeeeEeph = new Rule(rb, "FORD",

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(color,cEquals,"vert"),
                new Clause(vitess, cEquals, "5"),
                new Clause(type,cEquals, "4-port"),
                new Clause(vendue, cEquals, "verifier le prix"));
        Rule vent_voitureeemj = new Rule(rb, "regle6:verifier le prix BMW" ,

                new Clause(marque,cEquals,"BMW")  ,
                new Clause(prix_vente, cEquals, "7000"),
                new Clause(color,cEquals, "rouge"),
                new Clause(vitess, cEquals, "6"),
                new Clause(type,cEquals, "2-port"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(vendue, cEquals, "vendeur:je vends l'automobile"));
        Rule vent_voitureeeenk = new Rule(rb, "FORD" ,

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(prix_vente, cEquals, "8000"),
                new Clause(color,cEquals, "vert"),
                new Clause(vitess, cEquals, "5"),
                new Clause(type,cEquals, "4-port"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(vendue, cEquals, "vendeur:je vends l'automobile"));

        Rule vent_voitureeeeedl = new Rule(rb, "AUDI" ,

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(prix_vente, cEquals, "4000"),
                new Clause(color,cEquals, "blanc"),
                new Clause(vitess, cEquals, "5"),
                new Clause(type,cEquals, "4-port"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(vendue, cEquals, "vendeur:je vends l'automobile"));




    }
    //////////acheteur
    public void initAchteurRuleBase(RuleBase rb)
    {
        rb.goalClauseStack = new Stack() ;
        rb.variableList = new Hashtable() ;




        RuleVariable marque = new RuleVariable("marque") ;
        marque.setLabels("BMW AUDI FORD");

        rb.variableList.put(marque.name,marque) ;

        RuleVariable type = new RuleVariable("type") ;
        type.setLabels("2-port 4-port");

        rb.variableList.put(type.name,type) ;

        RuleVariable vitess = new RuleVariable("vitess") ;
        vitess.setLabels("5 6");

        rb.variableList.put(vitess.name,vitess) ;


        RuleVariable prix_achat = new RuleVariable("prix_achat") ;
        prix_achat.setLabels("3500 4000 5000 7000 8000 9000 1200 1400");

        rb.variableList.put(prix_achat.name,prix_achat) ;

        RuleVariable colors = new RuleVariable("color") ;
        colors.setLabels("noir rouge blanc vert jeune");

        rb.variableList.put(colors.name,colors) ;




        RuleVariable article_disponible = new RuleVariable("article_disponible") ;
        article_disponible.setLabels(" non oui");

        rb.variableList.put(article_disponible.name,article_disponible) ;

        RuleVariable achat = new RuleVariable("resultat") ;
        achat.setLabels("");

        rb.variableList.put(achat.name,achat) ;
        // Note: at this point all variables values are NULL

        Condition cEquals = new Condition("=") ;
        Condition cNotEquals = new Condition("!=") ;
        Condition cLessThan = new Condition("<") ;
        Condition cMoreThan = new Condition(">") ;

        // define rules
        rb.ruleList = new Vector() ;

        Rule achat_voiture = new Rule(rb, "regle1:chercher marque: BMW" ,

                new Clause(marque,cEquals,"BMW")  ,
                new Clause(achat, cEquals, "verifier la disponibilité"));

        Rule vente_automobileeee = new Rule(rb, "AUDI",

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(achat, cEquals, "verifier la disponibilité"));
        Rule vente_automobileeeeE = new Rule(rb, "FORD",

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(achat, cEquals, "verifier la disponibilité"));
        Rule achat_voituree = new Rule(rb, "regle2:verifier la disponibilité BMW" ,


                new Clause(marque,cEquals,"BMW")  ,

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(achat, cEquals, "chercher color"));

        Rule vente_automobileeeee = new Rule(rb, "AUDI",

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(achat, cEquals, "chercher color"));

        Rule vente_automobileeeeEe = new Rule(rb, "FORD",

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(achat, cEquals, "chercher color"));
        Rule achat_voitureee = new Rule(rb, "regle3:chercher color BMW" ,

                new Clause(marque,cEquals,"BMW")  ,

                new Clause(colors,cEquals, "rouge"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(achat, cEquals, "chercher type"));
        Rule achat_voitureeee = new Rule(rb, "FORD" ,

                new Clause(marque,cEquals,"FORD")  ,

                new Clause(colors,cEquals, "vert"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(achat, cEquals, "chercher type"));

        Rule achat_voitureeeee = new Rule(rb, "AUDI" ,

                new Clause(marque,cEquals,"AUDI")  ,

                new Clause(colors,cEquals, "blanc"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(achat, cEquals, "chercher type"));
        Rule achat_voiturerr = new Rule(rb, "regle4:chercher type: BMW" ,

                new Clause(marque,cEquals,"BMW")  ,

                new Clause(colors,cEquals, "rouge"),
                new Clause(article_disponible, cEquals, "oui"),
                new Clause(type,cEquals, "2-port"),
                new Clause(achat, cEquals, "chercher vitess"));

        Rule vente_automobileeeeee = new Rule(rb, "AUDI",

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(colors,cEquals,"blanc"),
                new Clause(type,cEquals, "4-port"),

                new Clause(achat, cEquals, "chercher vitess"));
        Rule vente_automobileeeeEee = new Rule(rb, "FORD",

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(colors,cEquals,"vert"),
                new Clause(type,cEquals, "4-port"),
                new Clause(achat, cEquals, "chercher vitess"));
        Rule achat_voitureej = new Rule(rb, "regle5:chercher vitess BMW" ,

                new Clause(marque,cEquals,"BMW")  ,

                new Clause(colors,cEquals, "rouge"),
                new Clause(type,cEquals, "2-port"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(vitess, cEquals, "6"),

                new Clause(achat, cEquals, "chercher prix"));

        Rule vente_automobileeeeeo = new Rule(rb, "AUDI",

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(colors,cEquals,"blanc"),
                new Clause(vitess, cEquals, "5"),
                new Clause(type,cEquals, "4-port"),
                new Clause(achat, cEquals, "chercher prix"));

        Rule vente_automobileeeeEep = new Rule(rb, "FORD",

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(article_disponible, cEquals, "oui"),


                new Clause(colors,cEquals,"vert"),
                new Clause(vitess, cEquals, "5"),
                new Clause(type,cEquals, "4-port"),
                new Clause(achat, cEquals, "chercher prix"));
        Rule achat_voitureeem = new Rule(rb, "regle6:chercher prix BMW" ,

                new Clause(marque,cEquals,"BMW")  ,
                new Clause(prix_achat, cEquals, "7000"),
                new Clause(colors,cEquals, "rouge"),
                new Clause(vitess, cEquals, "6"),
                new Clause(type,cEquals, "2-port"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(achat, cEquals, "acheteur:j'achete l'automobile"));
        Rule achat_voitureeeen = new Rule(rb, "FORD" ,

                new Clause(marque,cEquals,"FORD")  ,
                new Clause(prix_achat, cEquals, "8000"),
                new Clause(colors,cEquals, "vert"),
                new Clause(vitess, cEquals, "5"),
                new Clause(type,cEquals, "4-port"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(achat, cEquals, "acheteur:j'achete l'automobile"));

        Rule achat_voitureeeeed = new Rule(rb, "AUDI" ,

                new Clause(marque,cEquals,"AUDI")  ,
                new Clause(prix_achat, cEquals, "4000"),
                new Clause(colors,cEquals, "blanc"),
                new Clause(vitess, cEquals, "5"),
                new Clause(type,cEquals, "4-port"),

                new Clause(article_disponible, cEquals, "oui"),
                new Clause(achat, cEquals, "acheteur:j'achete l'automobile"));


    }

}
