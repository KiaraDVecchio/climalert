package ar.utn.ba.ddsi.mailing.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailOutputDTO {
  private Long id;
  private String destinatario;
  private String remitente;
  private String asunto;
  private String contenido;
  private boolean enviado;

}
