package com.extrcproject.core.entailment;

import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.extrcproject.core.syntax.DefeasibleImplication;
import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.syntax.QueryResult;

/**
 * Defines cache for adding and removing items from cache.
 *
 * @author Thabo Vincent Moloi
 */
public class Cache {

    /**
     * Represent item to add to cache with the timestamp it was added.
     */
    private class CacheItem {

        private final QueryResult value;
        private final Instant timestamp;

        /**
         * Create new cache item.
         *
         * @param value The query result
         */
        public CacheItem(QueryResult value) {
            this.value = value;
            this.timestamp = Instant.now();
        }
    }

    private final Map<String, CacheItem> cache = new HashMap<>();

    /**
     * Check if result for formula and knowledge base exists.
     *
     * @param formula
     * @param knowledgeBase
     * @return true if the query result exists, else false.
     */
    public synchronized boolean contains(DefeasibleImplication formula, KnowledgeBase knowledgeBase) {
        return cache.containsKey(generateKey(formula, knowledgeBase));
    }

    /**
     * Adds new cache item.
     *
     * @param formula
     * @param knowledgeBase
     * @param queryResult
     */
    public synchronized void put(DefeasibleImplication formula, KnowledgeBase knowledgeBase, QueryResult queryResult) {
        cache.put(generateKey(formula, knowledgeBase), new CacheItem(queryResult));
    }

    /**
     * Retreives query result corresponing to formula and knowledge base
     *
     * @param formula
     * @param knowledgeBase
     * @return QueryResult
     */
    public synchronized QueryResult get(DefeasibleImplication formula, KnowledgeBase knowledgeBase) {
        String key = generateKey(formula, knowledgeBase);
        CacheItem item = cache.get(key);
        if (item == null) {
            return null;
        }
        return item.value;
    }

    /**
     * Removes all items older than provided duration.
     *
     * @param expirySeconds Expiration duration.
     */
    public synchronized void expire(long expirySeconds) {
        Instant now = Instant.now();
        Iterator<Map.Entry<String, CacheItem>> iterator = cache.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, CacheItem> entry = iterator.next();
            CacheItem cacheItem = entry.getValue();
            long ageInSeconds = now.getEpochSecond() - cacheItem.timestamp.getEpochSecond();

            if (ageInSeconds > expirySeconds) {
                iterator.remove(); // Remove expired entry
            }
        }
    }

    /**
     * Clears the cache.
     */
    public synchronized void clear() {
        cache.clear();
    }

    private String generateKey(DefeasibleImplication formula, KnowledgeBase knowledgeBase) {
        return formula + "::" + knowledgeBase;
    }

}
