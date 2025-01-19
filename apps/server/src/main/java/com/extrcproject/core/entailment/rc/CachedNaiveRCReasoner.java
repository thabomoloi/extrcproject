package com.extrcproject.core.entailment.rc;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;

import com.extrcproject.core.entailment.Cache;
import com.extrcproject.core.syntax.Algorithm;

/**
 * Represent a cached reasoner using naive rational closure algorithm.
 *
 * @author Maqhobosheane Mohlerepe
 * @author Thabo Vincent Moloi
 */
public class CachedNaiveRCReasoner extends NaiveRCReasoner {

    protected final Cache cache;

    /**
     * Create new cached reasoner using naive rational closure algorithm.
     */
    public CachedNaiveRCReasoner(SatReasoner reasoner, Cache cache) {
        super(reasoner);
        this.cache = cache;
        algorithmType = Algorithm.Type.CACHED_NAIVE;
    }

}
