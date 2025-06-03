package ar.utn.ba.ddsi.mailing.models.entities;

public class CondicionAlerta {
  private double temperaturaAlerta;
  private int humedadAlerta;

  public CondicionAlerta(double temperaturaAlerta, int humedadAlerta) {
    this.temperaturaAlerta = temperaturaAlerta;
    this.humedadAlerta = humedadAlerta;
  }

  public boolean cumple(Clima clima) {
    return clima.getTemperaturaCelsius() > temperaturaAlerta &&
           clima.getHumedad() > humedadAlerta;
  }
}
