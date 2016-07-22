package tps03_J_CSUEH;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

final class PoolService {
	
	private static final ThreadFactory factory = new ExceptionThreadFactory(new MyExceptionHandler()); 
	private static final ExecutorService pool =Executors.newFixedThreadPool(10, factory);
	
	public void doSomething() { 
		pool.execute(new Task());
	} 
	
	public static class ExceptionThreadFactory implements ThreadFactory { 
		private static final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
		private final Thread.UncaughtExceptionHandler handler;

		public ExceptionThreadFactory( Thread.UncaughtExceptionHandler handler) {
			this.handler = handler;
		}

		@Override 
		public Thread newThread(Runnable run) { 
			Thread thread = defaultFactory.newThread(run); 
			thread.setUncaughtExceptionHandler(handler); 
			return thread;
		} 
	}
	
	public static class MyExceptionHandler extends Exception implements Thread.UncaughtExceptionHandler {
		// ...
		@Override public void uncaughtException(Thread thread, Throwable t) { 
			// Recovery or logging code
		} 
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		PoolService service = new PoolService();
		service.doSomething();
	}
}
