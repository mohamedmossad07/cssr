package org.exalt.cssr.location;

import org.exalt.cssr.exceptions.ApiRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

public class ReservationThreadFactory implements ThreadFactory {

    public static final int THREAD_LIMIT = 1000;
    private static int THREAD_COUNTER = 0;
    private static ReservationThreadFactory threadFactory = null;

    @Override
    public Thread newThread(Runnable r) {
        if (THREAD_COUNTER<THREAD_LIMIT)
            return new ReservationThread(r);
        return null;
    }

    public static ReservationThreadFactory getInstance(){
        if (threadFactory == null) {
            threadFactory = new ReservationThreadFactory();
            THREAD_COUNTER++;
        }
        return threadFactory;
    }

    private static class ReservationThread extends Thread{
        public ReservationThread(Runnable task) {
            super(task);
        }
        @Override
        public void interrupt() {
            THREAD_COUNTER--;
            super.interrupt();
        }
    }
}
