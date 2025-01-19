package com.extrcproject.core.baserank;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.syntax.KnowledgeBase;

/**
 * Defines methods for computing ranking using base rank algorithm with
 * threading (Fork/Join Framework).
 *
 * @author Maqhobosheane Mohlerepe
 * @author Mamodike Sadiki
 * @author Thabo Vincent Moloi
 */
// JAN2025 - Vincent - Modified to include additional syntax
public class ThreadedBaseRank extends BaseRankAlgorithm {

    private class ExceptionalsTask extends RecursiveTask<KnowledgeBase> {

        private final int start;
        private final int end;
        private final int threshold;
        private final KnowledgeBase knowledgeBase;
        private final List<PlFormula> antecedents;

        public ExceptionalsTask(KnowledgeBase knowledgeBase, int start, int end, int threshold, List<PlFormula> antecedants) {
            this.start = start;
            this.end = end;
            this.threshold = threshold;
            this.antecedents = antecedants;
            this.knowledgeBase = knowledgeBase;
        }

        @Override
        protected KnowledgeBase compute() {
            if ((end - start) <= threshold) {
                KnowledgeBase exceptionals = new KnowledgeBase();
                for (int i = start; i <= end; i++) {
                    PlFormula antecedent = antecedents.get(i);
                    if (reasoner.query(knowledgeBase, new Negation(antecedent))) {
                        exceptionals.add(antecedent);
                    }
                }
                return exceptionals;
            } else {
                int mid = start + (end - start) / 2;
                ExceptionalsTask lower = new ExceptionalsTask(knowledgeBase, start, mid, threshold, antecedents);
                ExceptionalsTask upper = new ExceptionalsTask(knowledgeBase, mid + 1, end, threshold, antecedents);
                upper.fork();
                KnowledgeBase exceptionals = lower.compute();
                exceptionals.addAll(upper.join());
                return exceptionals;
            }
        }

    }

    /**
     * Create naive base rank algorithm.
     *
     * @param reasoner SAT Reasoner
     */
    public ThreadedBaseRank(SatReasoner reasoner) {
        super(reasoner);
    }

    @Override
    protected KnowledgeBase getExceptionals(KnowledgeBase defeasible, KnowledgeBase classical) {
        KnowledgeBase all = defeasible.union(classical);
        List<PlFormula> antecedents = defeasible.antecedents().getCanonicalOrdering();
        ExceptionalsTask exceptionalsTask = new ExceptionalsTask(all, 0, antecedents.size() - 1, Math.max(antecedents.size() / 6, 1), antecedents);
        try (ForkJoinPool pool = new ForkJoinPool()) {
            return pool.invoke(exceptionalsTask);
        }
    }

}
