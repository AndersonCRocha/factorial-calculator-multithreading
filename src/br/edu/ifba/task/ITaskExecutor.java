package br.edu.ifba.task;

public interface ITaskExecutor<T extends ITask> {

  void execute(T task) throws InterruptedException;

}
