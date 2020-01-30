package com.github.taylort7147.minigames;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AccumulatorMap<K, V>
{
    private int maxSize;
    private Map<K, Set<V>> accumulators;

    public AccumulatorMap(int maxSize)
    {
        this.maxSize = maxSize;
        this.accumulators = new HashMap<K, Set<V>>();
    }

    /**
     * Add a value to the specified key. Optionally return a complete set if the
     * maxSize has been added.
     * 
     * @param key   the key to add to
     * @param value the value to add
     * @return An Optional that, if complete returns the completed set. Otherwise
     *         returns empty.
     */
    public Optional<Set<V>> add(K key, V value)
    {
        if(!this.accumulators.containsKey(key))
        {
            this.accumulators.put(key, new HashSet<V>());
        }
        Set<V> accumulator = this.accumulators.get(key);

        accumulator.add(value);

        if (accumulator.size() == this.maxSize)
        {
            this.accumulators.remove(key);
            return Optional.of(accumulator);
        }
        return Optional.empty();
    }

    public void reset()
    {
        this.accumulators.clear();
    }
}
