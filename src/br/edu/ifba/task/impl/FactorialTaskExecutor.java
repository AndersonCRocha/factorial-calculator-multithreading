package br.edu.ifba.task.impl;

import br.edu.ifba.task.ITaskExecutor;

public class FactorialTaskExecutor implements ITaskExecutor<FactorialTask> {
  @Override
  public void execute(FactorialTask task) throws InterruptedException {
    new Thread(() -> {
      try {
        task.run();
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      }
    })
    .start();
  }
}
