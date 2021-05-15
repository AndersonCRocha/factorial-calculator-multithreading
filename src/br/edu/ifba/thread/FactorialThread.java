package br.edu.ifba.thread;

import java.math.BigInteger;

public class FactorialThread extends Thread {

  private final int initialNumber;
  private final int finalNumber;
  private BigInteger result = BigInteger.ONE;

  public FactorialThread(int initialNumber, int finalNumber) {
    this.initialNumber = initialNumber;
    this.finalNumber = finalNumber;
  }

  @Override
  public void run() {
    for (int count = initialNumber; count <= finalNumber; count++) {
      this.result = this.result.multiply(new BigInteger(String.valueOf(count)));
    }
  }

  public BigInteger getResult() {
    return result;
  }
}
