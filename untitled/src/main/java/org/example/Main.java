package org.example;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Yes or no?");
    String input = sc.nextLine();
    while (input.equalsIgnoreCase("yes")) {
      System.out.println("Yes or no?");
      input = sc.nextLine();
      if (input.equalsIgnoreCase("no")) {
        break;
      }
    }
  }
}