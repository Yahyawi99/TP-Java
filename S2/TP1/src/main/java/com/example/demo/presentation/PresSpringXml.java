
package main.java.com.example.demo.presentation;

public class PresSpringXml {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");
    IMetier metier = (IMetier) applicationContext.getBean("metier");
    System.out.println(metier.calcule());
  }
}