package ar.edu.itba.pod.concurrency.threads.e4;

public interface IBranchClientQueueService {
    void receiveClient(Client client);
    
    Client clientForPriority(ClientPriority priority);
}