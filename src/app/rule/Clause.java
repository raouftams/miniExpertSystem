package app.rule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Clause {
    Vector ruleRefs ;
    RuleVariable lhs ;
    String rhs ;
    Condition  cond ;
    Boolean consequent ;  // true or false
    Boolean truth ;   // states = null(unknown), true or false

    public Clause(RuleVariable Lhs, Condition Cond, String Rhs)
    {
        lhs = Lhs ; cond = Cond ; rhs = Rhs ;
        lhs.addClauseRef(this) ;
        ruleRefs = new Vector() ;
        truth = null ;
        consequent = new Boolean(false) ;
    }
    // TOdo: creer des tableaux de vols

    void addRuleRef(Rule ref) { ruleRefs.addElement(ref) ; }

    Boolean check(){
        if (consequent.booleanValue() == true) return null ;
        if (lhs.value == null) {
            return truth = null ;  // can't check if variable value is undefined
        } else {

            switch(cond.index) {
                case 1: truth = new Boolean(lhs.value.equals(rhs)) ;
//        RuleBase.appendText("\nTesting Clause " + lhs.name + " = " + rhs + " " + truth);
                    break ;
                case 2: truth = new Boolean(lhs.value.compareTo(rhs) > 0) ;
//        RuleBase.appendText("\nTesting Clause " + lhs.name + " > " + rhs + " " + truth);
                    break ;
                case 3: truth = new Boolean(lhs.value.compareTo(rhs) < 0) ;
//        RuleBase.appendText("\nTesting Clause " + lhs.name + " < " + rhs + " " + truth);
                    break ;
                case 4: truth = new Boolean(lhs.value.compareTo(rhs) != 0) ;
//        RuleBase.appendText("\nTesting Clause " + lhs.name + " != " + rhs + " " + truth);
                    break ;
                case 5: truth = new Boolean(lhs.value.compareTo(rhs) <= 0);
                    break;
                case 6: truth = new Boolean(lhs.value.compareTo(rhs) >= 0);
                    break;
                case 7:
                    try{
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        truth = sdf.parse(lhs.value).before(sdf.parse(rhs)) == true;
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    break;
            }

            return truth ;
        }
    }
    void isConsequent() { consequent = new Boolean(true); }
    Rule getRule() { if (consequent.booleanValue() == true)
        return (Rule)ruleRefs.firstElement() ;
    else return null ;}
}
