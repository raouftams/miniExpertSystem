package app;

import java.util.Vector;

public class Clause {
    Vector ruleRefs ;
    RuleVariable lhs ;
    String rhs ;
    Condition  cond ;
    Boolean consequent ;  // true or false
    Boolean truth ;   // states = null(unknown), true or false

    Clause(RuleVariable Lhs, Condition Cond, String Rhs) {
        lhs = Lhs; cond = Cond; rhs = Rhs;
        lhs.addClauseRef(this);
        ruleRefs = new Vector();
        truth = null;
        consequent = Boolean.FALSE;
    }

    void addRuleRef(Rule ref) { ruleRefs.addElement(ref); }

    Boolean check() {
        if (consequent) return truth = null ;
        if (lhs.value == null) return truth = null ;  // can't check if variable value is undefined
        if(checkInt(rhs)){

            switch(cond.index) {
                case 1:
                    truth = (Integer.parseInt(lhs.value)==Integer.parseInt(rhs));

                    //        RuleBase.appendText("\nTesting Clause " + lhs.name + " = " + rhs + " " + truth);
                    break ;
                case 2:
                    truth = (Integer.parseInt(lhs.value)>(Integer.parseInt(rhs)));
                    //        RuleBase.appendText("\nTesting Clause " + lhs.name + " > " + rhs + " " + truth);
                    break ;
                case 3: truth = (Integer.parseInt(lhs.value)<(Integer.parseInt(rhs)));
                    //        RuleBase.appendText("\nTesting Clause " + lhs.name + " < " + rhs + " " + truth);
                    break ;
                case 4: truth = (Integer.parseInt(lhs.value)!=(Integer.parseInt(rhs)));
                    //        RuleBase.appendText("\nTesting Clause " + lhs.name + " != " + rhs + " " + truth);
                    break ;
            }

        }
        return truth ;
    }

    void isConsequent() { consequent = Boolean.TRUE; }
    public Boolean checkInt(String s){
        try {
            Integer.parseInt(s);
            return true;

        }catch (Exception e){
            return  false;
        }
    }

    Rule getRule() {
        if (consequent) return (Rule)ruleRefs.firstElement() ;
        return null ;
    }
}
