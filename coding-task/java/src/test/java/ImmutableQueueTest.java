import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ImmutableQueueTest {

    private Queue<Integer> emptyQueue = new ImmutableQueue<>(new ArrayList<>());

    @Test
    public void testIsEmpty() {
        assertTrue(emptyQueue.isEmpty());

        Queue<Integer> integerQueue = new ImmutableQueue<>(Arrays.asList(1, 2));
        assertFalse(integerQueue.isEmpty());
    }

    @Test
    public void testEnqueue() {
        Queue<Integer> integerQueue = emptyQueue.enQueue(1);
        assertEquals(integerQueue, new ImmutableQueue<>(Arrays.asList(1)));
        assertNotSame(integerQueue, new ImmutableQueue<>(Arrays.asList(1)));
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueToEmptyQueue() {
        emptyQueue.deQueue();
    }

    @Test
    public void testDequeue() {
        Queue<Integer> integerQueue = new ImmutableQueue<>(Arrays.asList(1, 2, 3));
        assertEquals(integerQueue.deQueue(), new ImmutableQueue<>(Arrays.asList(2, 3)));
        assertNotSame(integerQueue.deQueue(), new ImmutableQueue<>(Arrays.asList(2, 3)));
    }

    @Test
    public void testHead() {
        assertNull(emptyQueue.head());
        Queue<Integer> integerQueue = new ImmutableQueue<>(Arrays.asList(1, 2, 3));
        assertEquals(integerQueue.head(), Integer.valueOf(1));
    }

}
