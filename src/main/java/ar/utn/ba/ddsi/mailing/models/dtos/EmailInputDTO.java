package ar.utn.ba.ddsi.mailing.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailInputDTO {
  private String destinatario;
  private String asunto;
  private String contenido;
}

