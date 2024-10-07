package ar.edu.itba.poo.soltp7.ej9;

import java.util.*;

public class HelpDesk {

    private final Map<ClientType, Queue<HelpTicket>> queuesByClientType = new EnumMap<>(ClientType.class);

    public HelpDesk openTicket(ClientType clientType, String name, String question) {
        queuesByClientType.putIfAbsent(clientType, new LinkedList<>());
        queuesByClientType.get(clientType).offer(new HelpTicket(name, question));
        return this;
    }

    public HelpTicket getNextTicket(ClientType clientType) {
        return queuesByClientType.getOrDefault(clientType, new LinkedList<>()).remove();
    }

    public HelpTicket getNextTicket() {
        for(Queue<HelpTicket> value : queuesByClientType.values()) {
            if(!value.isEmpty()) {
                return value.poll();
            }
        }
        throw new NoSuchElementException();
    }

    public boolean hasTickets() {
        for(Queue<HelpTicket> value : queuesByClientType.values()) {
            if(!value.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    static class HelpTicket {
        private final String name, question;

        public HelpTicket(String name, String question) {
            this.name = name;
            this.question = question;
        }

        @Override
        public String toString() {
            return "HelpTicket for %s asking %s".formatted(name, question);
        }
    }

}
