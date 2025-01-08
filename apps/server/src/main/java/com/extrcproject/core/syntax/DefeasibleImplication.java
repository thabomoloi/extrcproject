package com.extrcproject.core.syntax;

import org.tweetyproject.commons.util.Pair;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.util.Symbol;

/**
 * This class models defeasible implication of propositonal logic.
 *
 * @author Thabo Vincent Moloi
 */
public class DefeasibleImplication extends Implication {

    /**
     * Creates a new implication a~>b with the two given formulas
     *
     * @param a A propositional formula.
     * @param b A propositional formula.
     */
    public DefeasibleImplication(PlFormula a, PlFormula b) {
        super(a, b);
    }

    /**
     * Creates new defeasible implication with given pair of formulas
     *
     * @param formulas A pair of formulas.
     */
    public DefeasibleImplication(Pair<PlFormula, PlFormula> formulas) {
        super(formulas);
    }

    @Override
    public String toString() {
        return super.toString().replaceAll(Symbol.IMPLICATION(), Symbol.DEFEASIBLE_IMPLICATION());
    }

}
