package itmo.localpiper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BinomialHeapTest {
    BinomialHeap heap;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        heap = new BinomialHeap();
    }

    @Test
    @DisplayName("Insert a single element and remove it")
    void testInsertSingle() {
        heap.insert(42);
        assertEquals(42, heap.removeSmallest(), "Should remove the only element in heap.");
    }

    @Test
    @DisplayName("Insert multiple elements and remove in order")
    void testInsertMultiple() {
        int[] values = {10, 30, 20, 5, 15};
        for (int v : values) heap.insert(v);

        int[] expectedOrder = {5, 10, 15, 20, 30};
        for (int expected : expectedOrder) {
            int actual = heap.removeSmallest();
            assertEquals(expected, actual, "Elements should be removed in order.");
        }
    }

    @Test
    @DisplayName("Clear heap and check if empty")
    void testClearHeap() {
        heap.insert(10);
        heap.insert(20);
        heap.clearHeap();
        assertEquals(heap.removeSmallest(), -1);
    }

    @ParameterizedTest
    @CsvSource({
        "100, 200, 300, 100",
        "500, 400, 300, 300",
        "10, 50, 5, 5"
    })
    @DisplayName("Parameterized test for inserting and removing smallest")
    void testInsertAndRemoveParameterized(int a, int b, int c, int expectedMin) {
        heap.insert(a);
        heap.insert(b);
        heap.insert(c);
        assertEquals(expectedMin, heap.removeSmallest(), "Smallest element should be removed first.");
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, 1, 1",
        "100000, 100000, 99999, 99999",
        "5, 5, 5, 5"
    })
    void testInsertDuplicates(int a, int b, int c, int exp) {
        heap.insert(a);
        heap.insert(b);
        heap.insert(c);
        assertEquals(exp, heap.removeSmallest(), "Smallest element should be removed first.");
        assertEquals(2, heap.getSize(), "There should be 2 elements left in the heap.");
    }
}
