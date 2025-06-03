package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ciudad {
  private String nombre;
  private String region;
  private String pais;

  public Ciudad(String nombre, String region, String pais) {
    this.nombre = nombre;
    this.region = region;
    this.pais = pais;
  }
}
