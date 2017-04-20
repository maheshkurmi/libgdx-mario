package com.shikhar.mario.core.sound;

import java.util.LinkedList;

/**
    A thread pool is a group of a limited number of threads that
    are used to execute tasks.
    
    Forrest's comments: still a little unclear about how this whole thing works,
    basically my understanding is that it is a group of threads that run through a
    linked list of tasks sent to the object, the threads execute each task as the threads
    finish up with other tasks. 
*/
public class ThreadPool extends ThreadGroup {

    private boolean isAlive; // tracks if this ThreadPool is still running
    private LinkedList<Runnable> taskQueue;
    private int threadID; // give a number to each thread in a ThreadPool
    private static int threadPoolID = 0; // give a number to seperate ThreadPool
    /**
        Creates a new ThreadPool.
        @param numThreads The number of threads in the pool.
    */
    public ThreadPool(int numThreads) {
        super("ThreadPool-" + (threadPoolID++));
        this.setDaemon(true); // ??

        isAlive = true;

        taskQueue = new LinkedList<Runnable>();
        for (int i=0; i< numThreads; i++) {
            new PooledThread().start();
        }
    }

    /**
        Requests a new task to run. This method returns
        immediately, and the task executes on the next available
        idle thread in this ThreadPool.
        <p>Tasks start execution in the order they are received.
        @param task The task to run. If null, no action is taken.
        @throws IllegalStateException if this ThreadPool is
        already closed.
    */
    public synchronized void runTask(Runnable task) {
        if (!isAlive) {
            throw new IllegalStateException();
        }
        if (task != null) {
            taskQueue.add(task);
            notify();
        }

    }

    protected synchronized Runnable getTask()
        throws InterruptedException
    {
    	// if there are no tasks to run, just wait
        while (taskQueue.size() == 0) {
            if (!isAlive) {
                return null;
            }
            wait();
        }
        return (Runnable)taskQueue.removeFirst();
    }


    /**
        Closes this ThreadPool and returns immediately. All
        threads are stopped, and any waiting tasks are not
        executed. Once a ThreadPool is closed, no more tasks can
        be run on this ThreadPool.
    */
    public synchronized void close() {
        if (isAlive) {
            isAlive = false;
            taskQueue.clear();
            interrupt();
        }
    }

    /**
        Closes this ThreadPool and waits for all running threads
        to finish. Any waiting tasks are executed.
    */
    public void join() {
        // notify all waiting threads that this ThreadPool is no longer alive
        synchronized (this) {
            isAlive = false;
            notifyAll();
        }

        // wait for all threads to finish
        Thread[] threads = new Thread[activeCount()];
        int count = enumerate(threads);
        for (int i=0; i<count; i++) {
            try {
                threads[i].join();
            }
            catch (InterruptedException ex) { }
        }
    }
    
	/**
	 * Signals that a PooledThread has started. This method does nothing by
	 * default; subclasses should override to do any thread-specific startup
	 * tasks.
	 */
	protected void threadStarted() {
		// do nothing
	}

	/**
	 * Signals that a PooledThread has stopped. This method does nothing by
	 * default; subclasses should override to do any thread-specific cleanup
	 * tasks.
	 */
	protected void threadStopped() {
		// do nothing
	}

    /**
        A PooledThread is a Thread in a ThreadPool group, designed
        to run tasks (Runnables).
    */
    private class PooledThread extends Thread {

        public PooledThread() {
            super(ThreadPool.this,
                "PooledThread-" + (threadID++));
        }


        public void run() {
        	ThreadPool.this.threadStarted();
            while (!isInterrupted()) {

                // get a task to run
                Runnable task = null;
                try {
                    task = getTask();
                }
                catch (InterruptedException ex) { }

                // if getTask() returned null or was interrupted,
                // close this thread by returning.
                // if null, there are no tasks to run, so kill this thread.
                if (task == null) {
                    return;
                }

                // run the task, and eat any exceptions it throws
                try {
                    task.run();
                }
                catch (Throwable t) {
                    uncaughtException(this, t);
                }
            }
            ThreadPool.this.threadStopped();
        }
    }
}