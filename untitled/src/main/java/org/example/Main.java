package org.example;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    String input;
    do {
      System.out.println("Yes or no?");
      input = new Scanner(System.in).nextLine();
    }
    while (!input.equalsIgnoreCase("no"));
  }
}