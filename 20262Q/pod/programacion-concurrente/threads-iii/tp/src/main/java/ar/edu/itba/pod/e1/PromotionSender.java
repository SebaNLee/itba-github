package ar.edu.itba.pod.e1;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PromotionSender {

    public static void main(String[] args) {
        List<String> promotions = Arrays.asList("Descuento en Cafe: ",
                "Descuento en Refrescos:",
                "Descuento en Congelados:");
        // notifyPromotions(promotions);
        notifyPromotionsParallel(promotions);
        System.out.println("Se realizaron todas las notificaciones de la promoción.");

        //
        System.out.println("Ahora con CompleatableFuture:");
        notifiyPromotionsCompletable(promotions);
    }

    private static void notifyCustomers(String promotion) {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Cliente: " + promotion);
        } catch (InterruptedException e) {
            //
        }
    }

    private static void notifyMarketing(String promotion) {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Marketing: " + promotion);
        } catch (InterruptedException e) {
            //
        }
    }

    private static void notifyPromotions(List<String> promotions) {
        for (String promotion : promotions) {
            promotion = promotion + "30%";

            promotion = promotion + " Solo por hoy";
            notifyCustomers(promotion);
        }
        notifyMarketing("Hoy se publicito un descuento del 30%");
    }

    private static void notifyPromotionsParallel(List<String> promotions) {
        // con ParallelStream
        promotions.parallelStream().forEach(promotion -> notifyCustomers(promotion + "30%" + " Solo por hoy" + " | thread: " + Thread.currentThread().getName()));

        notifyMarketing("Hoy se publicito un descuento del 30%");
    }

    // copiado de soluciones
    private static void notifiyPromotionsCompletable(List<String> promotions) {
        CompletableFuture<Void> customerNotification = CompletableFuture.runAsync(() -> {
            for (String promotion : promotions) {
                promotion = promotion + "30%";
                promotion = promotion + " Sólo por hoy";
                notifyCustomers(promotion);
            }
        });
        CompletableFuture<Void> marketingNotification = customerNotification.thenRunAsync(() -> notifyMarketing("Hoy se publicitó un descuento del 30%"));

        CompletableFuture<Void> combinedNotification = CompletableFuture.allOf(customerNotification, marketingNotification);
        combinedNotification.join();
    }
}

// mvn compile && mvn exec:java -Dexec.mainClass="ar.edu.itba.pod.e1.PromotionSender"
