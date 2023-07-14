package com.epam.rd.autotasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadUnionImpl implements ThreadUnion {

	private final String threadUnionName;	
	private final List<FinishedThreadResult> threadResults; 
	private final AtomicInteger threadCounter;//<threadCounter> является уникальным числом для каждого созданного потока.
	private volatile boolean shutdownRequested;
	private final List<Thread> threads;
	
	public ThreadUnionImpl(String threadUnionName) {
		super();
		this.threadUnionName = threadUnionName;
		this.threadResults = Collections.synchronizedList(new ArrayList<>());// будут храниться результаты выполнения потоков.
		this.threadCounter = new AtomicInteger(0);//инициализируются счетчик потоков
		this.shutdownRequested = false;
		this.threads = Collections.synchronizedList(new ArrayList<>());//будут храниться созданные потоки.
	}
	
	public static ThreadUnion getInstance(String name) {
        return new ThreadUnionImpl(name);//для получения экземпляра ThreadUnion.
    }	

	@Override
	public synchronized Thread newThread(Runnable r) {	 //создает и возвращает новый поток,	
		if (shutdownRequested) {
			/*
			 * Если флаг shutdownRequested имеет значение true, то выбрасывается исключение IllegalStateException, 
			 * так как нельзя создавать новые потоки после вызова метода shutdown.
			 */
            throw new IllegalStateException("Cannot create new threads after shutdown");
        }
		
		Thread thread = new Thread(r) {// создается новый поток на основе переданного объекта Runnable. 

			@Override
			public void run() {	
				//При запуске потока вызывается метод run(), который добавляет результат выполнения потока в список threadResults.	
				super.run();
				threadResults.add(new FinishedThreadResult(this.getName()));
			}
			
		}; 
		
		/*
		 * Установлен обработчик необработанных исключений для каждого созданного потока. 
		 * Если в потоке возникает необработанное исключение, 
		 * то создается объект FinishedThreadResult с информацией о потоке и исключении, и он добавляется в список threadResults.
		 */
		thread.setUncaughtExceptionHandler((threadProc, throwable) -> {
			//перехватить исключение, которое возникло в другой запущенной нити
			FinishedThreadResult result = new FinishedThreadResult(threadProc.getName(), throwable);			
			threadResults.add(result);
		});
		
		String threadName = threadUnionName + "-worker-" + threadCounter.getAndIncrement(); 
		thread.setName(threadName);		
		threads.add(thread);//поток добавляется в список threads
		return thread;
	}

	@Override
	public int totalSize() {
		//Возвращает общее количество потоков, созданных в рамках ThreadUnion,  путем получения значения счетчика threadCounter.
		return threadCounter.get();
	}

	@Override
	public int activeSize() {
		// Показывает общее количество активных в данный момент потоков, созданных в рамках ThreadUnion
		return (int) threads.stream().filter(Thread::isAlive).count();
		//фильтрует список threads по условию, что поток является живым (alive), 
		//и возвращает количество потоков, удовлетворяющих этому условию.
	}

	@Override
	public void shutdown() {		
		//выполнить итерацию по списку потоков и вызвать interrupt() в каждом потоке, чтобы запросить их прерывание
		for (Thread thread : threads) {
            thread.interrupt();
        }
		
		//true, означает завершение работы и предотвращает создание новых потоков.	
		shutdownRequested = true;
	}

	@Override
	public boolean isShutdown() {
		// true, если было вызвано завершение работы
		return shutdownRequested;
	}

	@Override
	public void awaitTermination() {// ожидает завершения всех потоков в списке threads
		 for (Thread thread : threads) {
		        while (thread.isAlive()) {//перебирает каждый поток и проверяет, жив ли он с помощью метода isAlive()	        	
		            try {//Если поток все еще жив, вызывается метод join(), чтобы текущий поток ожидал завершения данного потока.
		                thread.join();//для ожидания завершения каждого потока в списке потоков.
		            } catch (InterruptedException e) {		            	
		                Thread.currentThread().interrupt();//устанавливается флаг прерывания для текущего потока.
		                System.out.println("Error: "+ Thread.currentThread());
		            }
		        }
		    }
	}

	@Override
	public boolean isFinished() {
		// проверяет, было ли вызвано завершение работы (shutdownRequested)
		// и все ли созданные потоки завершились (activeSize() == 0)
		return shutdownRequested && activeSize() == 0;//Если это условие выполняется, метод возвращает true.
	}

	@Override
	public List<FinishedThreadResult> results() {		
		return new ArrayList<>(threadResults);//возвращается новый объект ArrayList, чтобы предотвратить изменение списка извне.
	}

}
