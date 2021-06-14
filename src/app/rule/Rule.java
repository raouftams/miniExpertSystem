package app.rule;

import java.util.Enumeration;
import java.util.Vector;

public class Rule {
    RuleBase rb ;
    String name ;
    Clause antecedents[] ;  // allow up to 3 antecedents for now
    Clause consequent[] ;     //only 1 consequent clause allowed
    Boolean truth;       // states = (null=unknown, true, or false)
    boolean fired=false;

    public Rule(RuleBase rb, String name, Clause[] lhs, Clause[] rhs){
        this.rb = rb ;
        this.name = name ;
        antecedents = new Clause[lhs.length];
        for (int i = 0; i < lhs.length; i++) {
            antecedents[i] = lhs[i];
            lhs[i].addRuleRef(this);
        }
        consequent = new Clause[rhs.length];
        for (int i = 0; i < rhs.length; i++) {
            consequent[i] = rhs[i];
            rhs[i].addRuleRef(this);
            rhs[i].isConsequent();
        }
        rb.ruleList.addElement(this) ;  // add self to rule list
        truth = null ;
    }

    long numAntecedents() { return antecedents.length; }

    public static void checkRules(Vector clauseRefs) {
        Enumeration enum87 = clauseRefs.elements();
        while(enum87.hasMoreElements()) {
            Clause temp = (Clause)enum87.nextElement();
            Enumeration enum2 = temp.ruleRefs.elements() ;
            while(enum2.hasMoreElements()) {
                ((Rule)enum2.nextElement()).check() ; // retest the rule
            }
        }
    }


    // used by forward chaining only !
    Boolean check() {  // if antecedent is true and rule has not fired
        /*
            check if all the antecedents are correct so we can say that the
            rule is true!!!
         */
        //RuleBase.appendText("\nTesting rule " + name ) ;
        for (int i=0 ; i < antecedents.length ; i++ ) {
            if (antecedents[i].truth == null) return null ;
            if (antecedents[i].truth == true) {
                continue ;
            } else {
                return truth = false ; //don't fire this rule
            }
        } // endfor
        return truth = true ;  // could fire this rule
    }


    // used by forward chaining only !
    // fire this rule -- perform the consequent clause
    // if a variable is changes, update all clauses where
    //  it is references, and then all rules which contain
    //  those clauses

    void fire() {
        RuleBase.appendText("\nFiring rule " + name ) ;
        truth = true ;
        fired = true ;
        // set the variable value and update clauses
        for (int i = 0; i < consequent.length; i++) {
            consequent[i].lhs.setValue(consequent[i].rhs);
            // now retest any rules whose clauses just changed
            checkRules(consequent[i].lhs.clauseRefs);
        }
    }

    // determine is a rule is true or false
    // by recursively trying to prove its antecedent clauses are true
    // if any are false, the rule is false

    Boolean backChain()
    {
        /*
        Will not be used in our project normalement
         */

        System.out.println("\nEvaluating rule " + name);
        for (int i=0 ; i < antecedents.length ; i++) { // test each clause
            if (antecedents[i].truth == null) rb.backwardChain(antecedents[i].lhs.name);
            if (antecedents[i].truth == null) { // we couldn't prove true or false
                truth = antecedents[i].check() ; // redundant?
            } // endif
            if (antecedents[i].truth.booleanValue() == true) {
                continue ;    // test the next antecedent (if any)
            } else {
                return truth = false ; // exit, if any are false
            }
        } // endfor
        return truth = true ; // all antecedents are true
    }

    // display the rule in text format
    @SuppressWarnings("deprecation")
    void display() {
        System.out.println(name +": IF ");
        for(int i=0 ; i < antecedents.length ; i++) {
            Clause nextClause = antecedents[i] ;

            System.out.println(nextClause.lhs.name +
                    nextClause.cond.asString() +
                    nextClause.rhs + " ") ;
            //TODO: find out why this part is never executed.
            if ((i+1) < antecedents.length) System.out.println(" AND ") ;
        }
        System.out.println("\n     THEN ") ;
        for (int i = 0; i < consequent.length; i++) {
            System.out.println(consequent[i].lhs.name +
                    consequent[i].cond.asString() +
                    consequent[i].rhs + "\n") ;
        }
    }
}