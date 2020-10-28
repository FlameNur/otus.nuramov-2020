package com.nuramov.hw05_GC_Work_Monitoring;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import javax.management.InstanceNotFoundException;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

/*
 Выбор GC:
-XX:+UseSerialGC
-XX:+UseParallelGC
-XX:+UseConcMarkSweepGC
-XX:+UseG1GC
 */

/*
 Настройка: вывод информации о работе GC
-verbose:gc -XX:+PrintGCDetails
 */

public class MyGcWorkMonitor {

    private static long countOfYoungGC;
    private static long countOfOldGC;
    private static long fullDurationOfYoungGC;
    private static long fullDurationOfOldGC;

    public static void main(String[] args) throws InterruptedException, InstanceNotFoundException {
        long StartTime = System.nanoTime();
        MyTestClass myTestClass = new MyTestClass();

        try {
            Monitoring();
            myTestClass.run();
        } catch (OutOfMemoryError e) {
            System.out.println("Ошибка: OutOfMemoryError");
        } finally {
            System.out.println("Общее время работы приложения: " + (System.nanoTime() - StartTime) / 1000_000_000 + " секунд");
            System.out.println("Общее количество сборок young: " + countOfYoungGC);
            System.out.println("Общее количество сборок old: " + countOfOldGC);
            System.out.println("Общее время, потраченное на сборки young: " + fullDurationOfYoungGC + " ms");
            System.out.println("Общее время, потраченное на сборки old: " + fullDurationOfOldGC + " ms");
        }
    }

    private static void Monitoring() throws InstanceNotFoundException {
        NotificationListener listener = (notification, handback) -> {
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                CompositeData cd = (CompositeData) notification.getUserData();
                GarbageCollectionNotificationInfo gcNotificationInfo = GarbageCollectionNotificationInfo.from(cd);
                GcInfo gcInfo = gcNotificationInfo.getGcInfo();

                if(gcNotificationInfo.getGcName().contains("Copy") || gcNotificationInfo.getGcName().contains("Young") ||
                        gcNotificationInfo.getGcName().contains("Scavenge") || gcNotificationInfo.getGcName().contains("ParNew")) {
                    countOfYoungGC++;
                    fullDurationOfYoungGC += gcInfo.getDuration();
                }

                if(gcNotificationInfo.getGcName().contains("MarkSweepCompact") || gcNotificationInfo.getGcName().contains("Old") ||
                        gcNotificationInfo.getGcName().contains("MarkSweep") || gcNotificationInfo.getGcName().contains("ConcurrentMarkSweep")) {
                    countOfOldGC++;
                    fullDurationOfOldGC += gcInfo.getDuration();
                }

                System.out.println("GarbageCollection: " + gcNotificationInfo.getGcAction() + " " +
                        gcNotificationInfo.getGcName() + ", duration: " + gcInfo.getDuration() + " ms");
            }
        };

        for (GarbageCollectorMXBean gcMbean : ManagementFactory.getGarbageCollectorMXBeans()) {
            ManagementFactory.getPlatformMBeanServer().
                    addNotificationListener(gcMbean.getObjectName(), listener, null,null);
        }
    }
}
