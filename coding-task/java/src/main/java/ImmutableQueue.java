import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ImmutableQueue<T> implements Queue<T> {

    private List<T> elements;

    ImmutableQueue(List<T> elements) {
        this.elements = elements;
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public Queue<T> enQueue(T t) {
        elements.add(t);
        return new ImmutableQueue<>(elements);
    }

    @Override
    public Queue<T> deQueue() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("dequeue on empty queue");
        }
        return new ImmutableQueue<>(elements.subList(1, elements.size()));
    }

    @Override
    public T head() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(0);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImmutableQueue && (this.hashCode() == obj.hashCode())) {
            return true;
        }
        return false;
    }
}
