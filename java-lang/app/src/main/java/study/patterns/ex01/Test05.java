package study.patterns.ex01;

import study.patterns.ex01.step5.Car;
import study.patterns.ex01.step5.CarFactory;
import study.patterns.ex01.step5.K7Factory;
import study.patterns.ex01.step5.SonataFactory;

public class Test05 {
  public static void main(String[] args) {
    SonataFactory sonataFactory = SonataFactory.getInstance();
    K7Factory k7Factory = K7Factory.getInstance();
    K7Factory k7Factory2 = K7Factory.getInstance();

    printCar(sonataFactory);
    printCar(k7Factory);
    printCar(k7Factory2);
    System.out.println(k7Factory.equals(k7Factory2));
  }

  static void printCar(CarFactory carFactory) {
    Car car = carFactory.createCar();
    System.out.println(car);
  }

}
