package ar.utn.ba.ddsi.mailing.models.adapters;

import ar.utn.ba.ddsi.mailing.models.entities.Email;

public interface IEmailAdapter {
  public void enviar(Email email);
}
