package app;

import javafx.scene.control.TextArea;

import java.util.*;

public class RuleBase {
    String name ;
    Hashtable<String, RuleVariable> variableList = new Hashtable<String, RuleVariable>();    // all variables in the rulebase
    Clause clauseVarList[];
    Vector<Rule> ruleList = new Vector<Rule>() ;           // list of all rules
    Vector conclusionVarList ;  // queue of variables
    Rule rulePtr ;              // working pointer to current rule
    Clause clausePtr ;          // working pointer to current clause
    Stack goalClauseStack;      // for goals (cons clauses) and subgoals


    public RuleBase(String Name) {
        this.goalClauseStack = new Stack();
        this.variableList = new Hashtable();
        this.ruleList = new Vector() ;
        this.name = Name;
        //Define variables
        RuleVariable brand = new RuleVariable("Brand");
        brand.setLabels("apple google samsung xiaomi asus");
        this.variableList.put((String) brand.name, brand);

        RuleVariable os = new RuleVariable("OS");
        os.setLabels("android ios");
        this.variableList.put(os.name, os);

        RuleVariable ui = new RuleVariable("UI");
        ui.setLabels("none miui oneui");
        this.variableList.put(ui.name, ui);

        RuleVariable securityLevel = new RuleVariable("SecurityLevel");
        securityLevel.setLabels("high medium");
        this.variableList.put(securityLevel.name, securityLevel);

        RuleVariable phoneRange = new RuleVariable("PhoneRange");
        phoneRange.setLabels("flagship midrange budget");
        this.variableList.put(phoneRange.name, phoneRange);

        RuleVariable utility = new RuleVariable("Utility");
        utility.setLabels("business communication gaming");
        this.variableList.put(utility.name, utility);

        RuleVariable userBudget = new RuleVariable("UserBudget");
        userBudget.setLabels("200 300 400 500 600 700 800 900 1000 1100 1200");
        this.variableList.put(userBudget.name, userBudget);

        Condition cEquals = new Condition("=") ;
        Condition cNotEquals = new Condition("!=") ;
        Condition cLessThan = new Condition("<") ;
        Condition cMoreThan = new Condition(">") ;
        Condition cLessThanOrEquals = new Condition("<=");
        Condition cMoreThanOrEquals = new Condition(">=");


        //Define rules
        Rule flagshipRange = new Rule(this, "check_flagship",
                new Clause(userBudget, cMoreThanOrEquals, "700"),
                new Clause(phoneRange, cEquals, "flagship")
        );

        Rule midRange = new Rule(this, "check_midrange",
                new Clause(userBudget, cLessThan, "700"),
                new Clause(userBudget, cMoreThanOrEquals, "300"),
                new Clause(phoneRange, cEquals, "midrange")
        );

        Rule budgetRange = new Rule(this, "check_budget",
                new Clause(userBudget, cLessThan, "300"),
                new Clause(phoneRange, cEquals, "budget")
        );

        Rule highSecurity = new Rule(this, "high_security",
                new Clause(securityLevel, cEquals, "high"),
                new Clause(os, cEquals, "ios")
        );

        Rule midSecurity = new Rule(this, "medium_security",
                new Clause(securityLevel, cEquals, "medium"),
                new Clause(os, cEquals, "android")
        );

        Rule businessUtility = new Rule(this, "business_utility",
                new Clause(utility, cEquals, "business"),
                new Clause(securityLevel, cEquals, "high")
        );

        Rule gamingUtility = new Rule(this, "gaming_utility",
                new Clause(utility, cEquals, "gaming"),
                new Clause(securityLevel, cEquals, "medium")
        );

        Rule communicationUtility = new Rule(this, "communication_utility",
                new Clause(utility, cEquals, "communication"),
                new Clause(securityLevel, cEquals, "medium")
        );

        Rule appleUi = new Rule(this, "apple_os",
                new Clause(os, cEquals, "ios"),
                new Clause(brand, cEquals, "apple")
        );

        Rule googleUi = new Rule(this, "google_os",
                new Clause(os, cEquals, "android"),
                new Clause(ui, cEquals, "none"),
                new Clause(brand, cEquals, "google")
        );

        Rule samsungUi = new Rule(this, "samsung_ui",
                new Clause(os, cEquals, "android"),
                new Clause(ui, cEquals, "oneui"),
                new Clause(brand, cEquals, "samsung")
        );

        Rule xiaomiUi = new Rule(this, "xiaomi_ui",
                new Clause(os, cEquals, "android"),
                new Clause(ui, cEquals, "miui"),
                new Clause(brand, cEquals, "xiaomi")
        );

        Rule gamingPhone = new Rule(this, "gaming_phone",
                new Clause(os, cEquals, "android"),
                new Clause(utility, cEquals, "gaming"),
                new Clause(brand, cEquals, "asus")
        );
    }

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public Vector getRuleList() {
        return ruleList;
    }

    public void setRuleList(Vector ruleList) {
        this.ruleList = ruleList;
    }

    public Hashtable getVariableList() {
        return variableList;
    }

    public void setVariableList(Hashtable variableList) {
        this.variableList = variableList;
    }

    public Stack getGoalClauseStack() {
        return goalClauseStack;
    }

    public void setGoalClauseStack(Stack goalClauseStack) {
        this.goalClauseStack = goalClauseStack;
    }

    public static void appendText(String text) {
        System.out.println(text); }

    // for trace purposes - display all variables and their value
    public ArrayList<String> displayVariables() {
        ArrayList<String> text = new ArrayList<>();
        Enumeration enum87 = variableList.elements() ;
        while(enum87.hasMoreElements()) {
            RuleVariable temp = (RuleVariable)enum87.nextElement() ;
            text.add("\n" + temp.name + " value = " + temp.value); ;
        }
        return text;
    }

    // for trace purposes - display all rules in text format
    public void displayRules() {
        System.out.println("\n" + name + " Rule Base: " + "\n");
        Enumeration enum87 = ruleList.elements() ;
        while(enum87.hasMoreElements()) {
            Rule temp = (Rule)enum87.nextElement() ;
            temp.display() ;
        }
    }

    // for trace purposes - display all rules in the conflict set
    public void displayConflictSet(Vector ruleSet, TextArea displayArea) {
        displayArea.appendText("\n" + " -- Rules in conflict set:\n");
        Enumeration enum87 = ruleSet.elements() ;
        while(enum87.hasMoreElements()) {
            Rule temp = (Rule)enum87.nextElement() ;
            displayArea.appendText(temp.name + "(" + temp.numAntecedents()+ "), " );
        }
    }


    // reset the rule base for another round of inferencing
    // by setting all variable values to null
    public String reset() {
        Enumeration enum87 = variableList.elements() ;
        while(enum87.hasMoreElements()) {
            RuleVariable temp = (RuleVariable)enum87.nextElement() ;
            temp.setValue(null) ;
        }
        return ("\n --- Setting all " + name + " variables to null");
    }

    // for all consequent clauses which refer to this goalVar
    // try to find goalVar value via a rule being true
    //     if rule is true then pop, assign value, re-eval rule
    //     if rule is false then pop, continue
    //     if rule is null then we couldnt find a value (same as false?)
    //
    public void backwardChain(String goalVarName)
    {

        RuleVariable goalVar = (RuleVariable)variableList.get(goalVarName);
        Enumeration goalClauses = goalVar.clauseRefs.elements() ;

        while (goalClauses.hasMoreElements()) {
            Clause goalClause = (Clause)goalClauses.nextElement() ;
            if (goalClause.consequent.booleanValue() == false) continue ;

            goalClauseStack.push(goalClause) ;

            Rule goalRule = goalClause.getRule();
            Boolean ruleTruth = goalRule.backChain() ; // find rule truth value
            if (ruleTruth == null) {
                System.out.println("\nRule " + goalRule.name +
                        " is null, can't determine truth value.");
            } else if (ruleTruth.booleanValue() == true) {
                // rule is OK, assign consequent value to variable
                goalVar.setValue(goalClause.rhs) ;
                goalVar.setRuleName(goalRule.name) ;
                goalClauseStack.pop() ;  // clear item from subgoal stack
                System.out.println("\nRule " + goalRule.name + " is true, setting " + goalVar.name + ": = " + goalVar.value);
                if (goalClauseStack.empty() == true) {
                    System.out.println("\n +++ Found Solution for goal: " + goalVar.name);
                    break ; // for now, only find first solution, then stop
                }
            } else {
                goalClauseStack.pop() ; // clear item from subgoal stack
                System.out.println("\nRule " + goalRule.name + " is false, can't set " + goalVar.name);
            }

            // displayVariables("Backward Chaining") ;  // display variable bindings
        } // endwhile

        if (goalVar.value == null) {
            System.out.println("\n +++ Could Not Find Solution for goal: " + goalVar.name);
        }
    }

    // used for forward chaining only
    // determine which rules can fire, return a Vector
    public Vector match(boolean test, TextArea displayArea) {
        Vector matchList = new Vector() ;
        Enumeration enum87 = ruleList.elements() ;
        while (enum87.hasMoreElements()) {
            Rule testRule = (Rule)enum87.nextElement() ;
            if (test) testRule.check() ; // test the rule antecedents
            if (testRule.truth == null) continue;
            // fire the rule only once for now
            if ((testRule.truth) && (!testRule.fired))
                matchList.addElement(testRule) ;
        }
        displayConflictSet(matchList, displayArea) ;
        return matchList ;
    }

    // used for forward chaining only
    // select a rule to fire based on specificity
    public Rule selectRule(Vector ruleSet) {
        Enumeration enum87 = ruleSet.elements() ;
        long numClauses ;
        Rule nextRule ;

        Rule bestRule = (Rule)enum87.nextElement() ;
        long max = bestRule.numAntecedents() ;
        while (enum87.hasMoreElements()) {
            nextRule = (Rule)enum87.nextElement() ;
            if ((numClauses = nextRule.numAntecedents()) > max) {
                max = numClauses ;
                bestRule = nextRule ;
            }
        }
        return bestRule ;
    }

    public void forwardChain(TextArea displayArea) {
        Vector conflictRuleSet = new Vector() ;

        // first test all rules, based on initial data
        conflictRuleSet = match(true, displayArea); // see which rules can fire

        while(conflictRuleSet.size() > 0) {

            Rule selected = selectRule(conflictRuleSet); // select the "best" rule
            selected.fire() ; // fire the rule
            // do the consequent action/assignment
            // update all clauses and rules

            conflictRuleSet = match(false, displayArea); // see which rules can fire

            // displayVariables("Forward Chaining") ; // display variable bindings
        }
    }
}
