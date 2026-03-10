package main.java.com.example.demo.metier;

import com.example.demo.data.IData;

public class MetierImpl implements IMetier {

  private IData datacall;

  public double calcule() {
    double data = datacall.getData();
    return data * .5;
  }

  public void setDatacall(IData datacall) {
    this.datacall = datacall;
  }
}