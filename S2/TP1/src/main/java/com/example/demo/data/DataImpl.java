package main.java.com.example.demo.data;

public class DataImpl implements IData {
  public double getData() {
    System.out.println("Recuperation de la Base de donnees");
    return 10;
  }
}