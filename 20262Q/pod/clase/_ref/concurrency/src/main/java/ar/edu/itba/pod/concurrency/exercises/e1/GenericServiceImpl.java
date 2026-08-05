package ar.edu.itba.pod.concurrency.exercises.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * Basic implementation of {@link GenericService}.
 */
public  class GenericServiceImpl implements GenericService {

    private int visitCount = 0;
    private LinkedList<String> serviceQueue = new LinkedList<>();

    @Override
    public String echo(String message) {
        return message;
    }

    @Override
    public String toUpper(String message) {
        return Optional.ofNullable(message).map(x->x.toUpperCase()).orElseGet(()->null);
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
        return false;
    }

    @Override
    public void addToServiceQueue(String name) {
        serviceQueue.add(name);
    }

    @Override
    public String getFirstInServiceQueue() {
        if (serviceQueue.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return serviceQueue.get(0);
    }
}
