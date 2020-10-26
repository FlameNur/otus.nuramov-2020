package com.nuramov.hw05_GC_Work_Monitoring;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import javax.management.InstanceNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;


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
    public static void main(String[] args) throws InterruptedException, InstanceNotFoundException {
        long StartTime = System.nanoTime();
        MyTestClass myTestClass = new MyTestClass();

        Monitoring();
        try {
            myTestClass.run();
        } catch (OutOfMemoryError e) {
            System.out.println("Ошибка: OutOfMemoryError");
        } finally {
            System.out.println("Общее время работы приложения: " + (System.nanoTime() - StartTime) / 1000_000_000 + " секунд");
        }


    }


    private static void Monitoring() throws InstanceNotFoundException {
        NotificationListener listener = (notification, handback) -> {
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                CompositeData cd = (CompositeData) notification.getUserData();
                GarbageCollectionNotificationInfo gcNotificationInfo = GarbageCollectionNotificationInfo.from(cd);
                GcInfo gcInfo = gcNotificationInfo.getGcInfo();

                long startTime = gcNotificationInfo.getGcInfo().getStartTime(); //Время старта каждой сборки


                System.out.println("GarbageCollection: " + gcNotificationInfo.getGcAction() + " " +
                        gcNotificationInfo.getGcName() + ", duration: " + gcInfo.getDuration() + "ms");
            }
        };

        for (GarbageCollectorMXBean gcMbean : ManagementFactory.getGarbageCollectorMXBeans()) {
            ManagementFactory.getPlatformMBeanServer().
                    addNotificationListener(gcMbean.getObjectName(), listener, null,null);
        }
    }
}
