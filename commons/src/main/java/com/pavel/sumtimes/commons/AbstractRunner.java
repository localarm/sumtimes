package com.pavel.sumtimes.commons;

import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractRunner implements SmartLifecycle {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final AtomicBoolean closed = new AtomicBoolean(false);
    private volatile Thread thread;
    private volatile boolean running;

    @Override
    public void start(){
        loggingStart();
        thread = new Thread(() -> {
           try {
               while (!closed.get()) {
                   runIteration();
               }
           } catch (InterruptedException | WakeupException | InterruptException e) {
               if (!closed.get()) {
                   logger.warn("Interrupt iteration", e);
               }
           } catch (Exception e) {
               logger.warn("Unexpected error", e);
           } finally {
               try {
                   specFinally();
               } catch (InterruptException ie) {
                   if (!closed.get()) {
                       logger.warn("Unexpected interrupt in finally block", ie);
                   }
               } catch (Exception e) {
                   logger.error("Error in finally block", e);
               }
               running = false;
           }
        });
        thread.start();
        running = true;
    }

    public abstract void runIteration() throws InterruptedException;

    public abstract void loggingStart();

    @Override
    public void stop(){
        closed.set(true);
        interrupt();
        try {
            thread.join(2000);
        } catch (InterruptedException ignore) {}
        running = false;
        logger.info("service has been stopped");
    }

    public abstract void interrupt();

    public abstract void specFinally();

    @Override
    public boolean isRunning() {
        return running;
    }
}
