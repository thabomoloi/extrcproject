package com.extrcproject;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;

import com.extrcproject.core.baserank.BaseRank;
import com.extrcproject.core.baserank.NaiveBaseRank;
import com.extrcproject.core.entailment.rc.TernaryRCReasoner;
import com.extrcproject.core.syntax.DefeasibleImplication;
import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.util.DefeasibleParser;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Exception {
        SatReasoner satReasoner = new SatReasoner();
        DefeasibleParser parser = new DefeasibleParser();
        BaseRank baseRank = new NaiveBaseRank(satReasoner);

        TernaryRCReasoner tRcReasoner = new TernaryRCReasoner(satReasoner);

        KnowledgeBase kb = parser.parseFormulas("p => b, b~>w. b~>f, p~>!f");
        var formula = (DefeasibleImplication) parser.parseFormula("p ~>!f");
        System.out.println("Result\n" + tRcReasoner.query(formula, baseRank.computeBaseRank(kb).getRanking()));

    }
}
