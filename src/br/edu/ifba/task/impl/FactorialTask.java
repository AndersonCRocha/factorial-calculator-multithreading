package br.edu.ifba.task.impl;

import br.edu.ifba.thread.FactorialThread;
import br.edu.ifba.task.ITask;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FactorialTask implements ITask {

  private static final int INTERVAL_BY_THREAD = 100;
  private final List<FactorialThread> threads = new ArrayList<>();

  private final int number;
  private final Consumer<BigInteger> callback;

  public FactorialTask(int number, Consumer<BigInteger> callback) {
    this.number = number;
    this.callback = callback;
  }

  @Override
  public void run() throws InterruptedException {
    for (int count = 2; count <= number; count += INTERVAL_BY_THREAD + 1) {
      int finalNumber = count + INTERVAL_BY_THREAD;

      if (finalNumber > number) finalNumber = number;

      FactorialThread factorialThread = new FactorialThread(count, finalNumber);
      factorialThread.start();

      this.threads.add(factorialThread);
    }

    for (FactorialThread thread : this.threads) {
      thread.join();
    }

    BigInteger result = this.threads.parallelStream()
        .map(FactorialThread::getResult)
        .reduce(BigInteger.ONE, BigInteger::multiply);

    this.threads.forEach(Thread::interrupt);
    this.threads.clear();

    this.callback.accept(result);
  }

}
