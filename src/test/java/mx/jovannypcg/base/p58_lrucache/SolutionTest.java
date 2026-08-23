package mx.jovannypcg.base.p58_lrucache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    @Test
    @DisplayName("example from problem statement: capacity 2 with mixed get/put")
    void exampleFromProblemStatement() {
        Solution cache = new Solution(2);

        cache.put(1, 1);
        cache.put(2, 2);

        assertThat(cache.get(1)).isEqualTo(1);

        cache.put(3, 3);

        assertThat(cache.get(2)).isEqualTo(-1);

        cache.put(4, 4);

        assertThat(cache.get(1)).isEqualTo(-1);
        assertThat(cache.get(3)).isEqualTo(3);
        assertThat(cache.get(4)).isEqualTo(4);
    }

    @Test
    @DisplayName("capacity 1 → every put on a new key evicts the current entry")
    void capacityOneEvictsOnEveryNewKey() {
        Solution cache = new Solution(1);

        cache.put(2, 1);

        assertThat(cache.get(2)).isEqualTo(1);

        cache.put(3, 2);

        assertThat(cache.get(2)).isEqualTo(-1);
        assertThat(cache.get(3)).isEqualTo(2);
    }

    @Test
    @DisplayName("put on existing key updates value without evicting")
    void putOnExistingKeyUpdatesValue() {
        Solution cache = new Solution(2);

        cache.put(1, 1);
        cache.put(1, 2);

        assertThat(cache.get(1)).isEqualTo(2);

        cache.put(2, 2);

        assertThat(cache.get(1)).isEqualTo(2);
        assertThat(cache.get(2)).isEqualTo(2);
    }

    @Test
    @DisplayName("get on missing key returns -1")
    void getOnMissingKeyReturnsMinusOne() {
        Solution cache = new Solution(2);

        cache.put(1, 1);

        assertThat(cache.get(99)).isEqualTo(-1);
    }

    @Test
    @DisplayName("get marks key as recently used, protecting it from eviction")
    void getRefreshesRecency() {
        Solution cache = new Solution(2);

        cache.put(1, 1);
        cache.put(2, 2);

        assertThat(cache.get(1)).isEqualTo(1);

        cache.put(3, 3);

        assertThat(cache.get(2)).isEqualTo(-1);
        assertThat(cache.get(1)).isEqualTo(1);
        assertThat(cache.get(3)).isEqualTo(3);
    }

    @Test
    @DisplayName("put on existing key also refreshes recency")
    void putOnExistingKeyRefreshesRecency() {
        Solution cache = new Solution(2);

        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(1, 10);
        cache.put(3, 3);

        assertThat(cache.get(2)).isEqualTo(-1);
        assertThat(cache.get(1)).isEqualTo(10);
        assertThat(cache.get(3)).isEqualTo(3);
    }

    @Test
    @DisplayName("boundary key and value at upper constraint limits")
    void boundaryKeyAndValue() {
        Solution cache = new Solution(1);

        cache.put(10000, 100000);

        assertThat(cache.get(10000)).isEqualTo(100000);
    }

    @Test
    @DisplayName("key and value at zero, the lower constraint bound")
    void zeroKeyAndValue() {
        Solution cache = new Solution(1);

        cache.put(0, 0);

        assertThat(cache.get(0)).isEqualTo(0);
    }

    @Test
    @DisplayName("large capacity with many sequential inserts evicts oldest first")
    void largeCapacitySequentialInserts() {
        int capacity = 3000;
        Solution cache = new Solution(capacity);

        for (int i = 0; i < capacity + 100; i++) {
            cache.put(i, i * 10);
        }

        assertThat(cache.get(0)).isEqualTo(-1);
        assertThat(cache.get(99)).isEqualTo(-1);
        assertThat(cache.get(100)).isEqualTo(1000);
        assertThat(cache.get(capacity + 99)).isEqualTo((capacity + 99) * 10);
    }

    @Test
    @DisplayName("repeated get calls on the same key do not corrupt the cache")
    void repeatedGetOnSameKey() {
        Solution cache = new Solution(2);

        cache.put(1, 1);

        assertThat(cache.get(1)).isEqualTo(1);
        assertThat(cache.get(1)).isEqualTo(1);
        assertThat(cache.get(1)).isEqualTo(1);
    }
}
