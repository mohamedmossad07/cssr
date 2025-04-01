package org.exalt.cssr.location;

import java.util.concurrent.ThreadFactory;

/**
 * Custom Thread factory for CachedExecutor
 * with limiting threads count to 1000
 */
public class ReservationThreadFactory implements ThreadFactory {

    public static final int THREAD_LIMIT = 1000;//max thread count
    private static int THREAD_COUNTER = 0;//current thread count
    private static ReservationThreadFactory threadFactory = null;//instance

    /**
     * Retrieving a Singleton instance
     * @return instance of ReservationThreadFactory
     */
    public static ReservationThreadFactory getInstance() {
        if (threadFactory == null) {
            threadFactory = new ReservationThreadFactory();
            THREAD_COUNTER++;
        }
        return threadFactory;
    }

    @Override
    public Thread newThread(Runnable r) {
        if (THREAD_COUNTER < THREAD_LIMIT)
            return new ReservationThread(r);
        return null;
    }

    //custom thread impl
    private static class ReservationThread extends Thread {
        public ReservationThread(Runnable task) {
            super(task);
        }

        /**
         * Decrement the thread counter with one when interrupted
         */
        @Override
        public void interrupt() {
            THREAD_COUNTER--;
            super.interrupt();
        }
    }
}
