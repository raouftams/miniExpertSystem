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
        if (consequent) return null ;
        if (lhs.value == null) return truth = null ;  // can't check if variable value is undefined

        switch(cond.index) {
            case 1: truth = lhs.value.equals(rhs);
                //        RuleBase.appendText("\nTesting Clause " + lhs.name + " = " + rhs + " " + truth);
                break ;
            case 2: truth = lhs.value.compareTo(rhs) > 0;
                //        RuleBase.appendText("\nTesting Clause " + lhs.name + " > " + rhs + " " + truth);
                break ;
            case 3: truth = lhs.value.compareTo(rhs) < 0;
                //        RuleBase.appendText("\nTesting Clause " + lhs.name + " < " + rhs + " " + truth);
                break ;
            case 4: truth = lhs.value.compareTo(rhs) != 0;
                //        RuleBase.appendText("\nTesting Clause " + lhs.name + " != " + rhs + " " + truth);
                break ;
        }

        return truth ;
    }

    void isConsequent() { consequent = Boolean.TRUE; }

    Rule getRule() {
        if (consequent) return (Rule)ruleRefs.firstElement() ;
        return null ;
    }
}
