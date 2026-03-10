package main.java.com.example.demo.data;

public class DataImplExt implements IData {
  public double getData() {
    System.out.println("Recuperation de la Base de donnees");
    return 20;
  }
}