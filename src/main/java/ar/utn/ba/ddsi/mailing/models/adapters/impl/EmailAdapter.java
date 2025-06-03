package ar.utn.ba.ddsi.mailing.models.adapters.impl;

import ar.utn.ba.ddsi.mailing.models.adapters.IEmailAdapter;
import ar.utn.ba.ddsi.mailing.models.entities.Email;

public class EmailAdapter implements IEmailAdapter {

  @Override
  public void enviar(Email email) {
    // Simulación de envío
    System.out.printf("Email enviado a %s con asunto '%s'%n",
        email.getDestinatario(), email.getAsunto());
  }
}
