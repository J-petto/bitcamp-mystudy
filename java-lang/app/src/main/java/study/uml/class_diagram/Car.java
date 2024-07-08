package study.uml.class_diagram;

public class Car {

  Insurance insurance;
  Navigation navigation;
  Engine engine;
  // GasStation gasStation; 일시적인 관계는 전역으로 인스턴스를 안 만듬

  public Car(Engine engine) {
    // 자동차와 엔진은 생명을 같이함 교체 안됨.
    this.engine = engine;
  }

  public Insurance getInsurance() {
    return insurance;
  }

  public void setInsurance(Insurance insurance) {
    this.insurance = insurance;
  }

  public Navigation getNavigation() {
    return navigation;
  }

  public void setNavigation(Navigation navigation) {
    this.navigation = navigation;
  }

  public Engine getEngine() {
    // 엔진교체 불가로 get만 있으면 됨
    return engine;
  }

  public void fuelUp(GasStation gasStation) {
    gasStation.inject();
  }

}
