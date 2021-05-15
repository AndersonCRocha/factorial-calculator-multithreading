package br.edu.ifba.view;

import br.edu.ifba.task.impl.FactorialTask;
import br.edu.ifba.task.impl.FactorialTaskExecutor;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class CalculatorFrame  extends JFrame {

  private static final NumberFormat CIENTIFIC_FORMATTER = new DecimalFormat("0.######E0",
      DecimalFormatSymbols.getInstance(Locale.ROOT));
  private static final NumberFormat INTEGER_FORMATTER = NumberFormat.getIntegerInstance();

  private JTextField textField;
  private JButton button;

  public CalculatorFrame() {
    super("Calculadora de fatorial - Multithreading");
    JFrame.setDefaultLookAndFeelDecorated(true);
  }

  public void initialize() {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());
    panel.add(createTitle());

    createTextField();
    createButton();
    createPoweredBy();

    this.add(panel);
    this.setSize(450, 180);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setVisible(true);
  }

  private JLabel createTitle() {
    JLabel title = new JLabel("Insira o valor que deseja calcular o fatorial: ");
    title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

    return title;
  }

  private void createTextField() {
    NumberFormatter formatter = new NumberFormatter(INTEGER_FORMATTER);
    formatter.setValueClass(Integer.class);
    formatter.setMinimum(0);
    formatter.setMaximum(999999);
    formatter.setAllowsInvalid(false);
    formatter.setCommitsOnValidEdit(true);

    this.textField = new JFormattedTextField(formatter);
    this.textField.setBounds(150, 32, 120, 32);
    this.textField.setHorizontalAlignment(SwingConstants.CENTER);
    this.textField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    this.textField.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) calculate(null);
      }

      @Override
      public void keyReleased(KeyEvent e) {}
    });
    this.add(this.textField);
  }

  private void createButton() {
    this.button = new JButton();
    this.button.setText("CALCULAR");
    this.button.setBounds(110, 80, 200, 30);
    this.button.addActionListener(this::calculate);
    this.add(this.button);
  }

  private void createPoweredBy() {
    JLabel poweredBy = new JLabel("Por: Anderson Rocha");
    Font fontPoweredBy = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
    poweredBy.setBounds(320, 120, 100, 10);
    poweredBy.setFont(fontPoweredBy);
    this.add(poweredBy);
  }

  private void calculate(ActionEvent actionEvent) {
    int number = Integer.parseInt(this.textField.getText().replaceAll("\\.", ""));
    this.textField.setEnabled(false);
    this.button.setText("Calculando...");
    this.button.setEnabled(false);

    try {
      FactorialTask factorialTask = new FactorialTask(number, this::callback);
      new FactorialTaskExecutor().execute(factorialTask);
    } catch (InterruptedException exception) {
      String message = String.format("Erro ao calcular fatorial: %s", exception.getMessage());
      JOptionPane.showMessageDialog(this, message, "Erro!", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void callback(BigInteger result) {
    String resultAsString = result.compareTo(new BigInteger("100000000000")) > 0
        ? CIENTIFIC_FORMATTER.format(result).toLowerCase()
        : INTEGER_FORMATTER.format(result);
    String message = String.format("O fatorial de %s é %s", this.textField.getText(), resultAsString);
    JOptionPane.showMessageDialog(this, message, "Resultado", JOptionPane.INFORMATION_MESSAGE);

    this.textField.setEnabled(true);
    this.textField.setText("0");
    this.button.setText("CALCULAR");
    this.button.setEnabled(true);
  }
}
