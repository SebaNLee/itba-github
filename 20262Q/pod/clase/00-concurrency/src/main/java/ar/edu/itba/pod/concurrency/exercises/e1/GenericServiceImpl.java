package ar.edu.itba.pod.concurrency.exercises.e1;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Basic implementation of {@link GenericService}.
 */
public  class GenericServiceImpl implements GenericService {

    private int visitCount = 0;
    private Queue<String> queue = new LinkedList<>();

    @Override
    public String echo(String message) {
        return message;
    }

    @Override
    public String toUpper(String message) {
        return Optional.ofNullable(message).map(m -> m.toUpperCase()).orElseGet(() -> null);
    }

    @Override
    public void addVisit() {
        visitCount++;
    }

    @Override
    public int getVisitCount() {
        return visitCount;
    }

    @Override
    public boolean isServiceQueueEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void addToServiceQueue(String name) {
        if (name == null) {
            throw new NullPointerException();
        }

        queue.add(name);
    }

    @Override
    public String getFirstInServiceQueue() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("No one in queue");    
        }

        return queue.poll();
    }
}
