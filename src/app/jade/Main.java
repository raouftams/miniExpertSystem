package app.jade;

import app.Ressources;
import app.rule.RuleBase;
import app.rule.RuleVariable;
import javafx.scene.control.TextArea;

public class Main {
    public static TextArea display;
    public static void main(String[] args) {
        //Initializing rulebase
        RuleBase rb = Ressources.AirAlgerieRuleBase();
        //affecting values to rulabase variables
        RuleVariable rvar = (RuleVariable)rb.getVariableList().get("Depart");
        rvar.setValue("Alger");
        System.out.println("dagi");
        rvar = (RuleVariable)rb.getVariableList().get("Destination");
        rvar.setValue("Paris");
        System.out.println("dagi1");
        rvar = (RuleVariable)rb.getVariableList().get("Date");
        String date = "22/06/2021";
        rvar.setValue(date);


        rvar = (RuleVariable)rb.getVariableList().get("check");
        rvar.setValue("checkDepart");
        System.out.println("dagi3");
        rb.forwardChain(display);
        System.out.println(rb.displayVariables());
    }
}
