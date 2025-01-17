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
     * Creates a new defeasible implication a~>b with the two given formulas
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

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFormulas() == null) ? 0 : getFormulas().hashCode());
        return result;
    }

    
    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DefeasibleImplication other = (DefeasibleImplication) obj;
        if (this.getFormulas() == null) {
            if (other.getFormulas() != null) {
                return false;
            }
        } else if (!this.getFormulas().equals(other.getFormulas())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString().replaceAll(Symbol.IMPLICATION(), Symbol.DEFEASIBLE_IMPLICATION());
    }

}
