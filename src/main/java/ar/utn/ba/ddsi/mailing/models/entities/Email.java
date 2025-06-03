package ar.utn.ba.ddsi.mailing.models.entities;

import ar.utn.ba.ddsi.mailing.models.adapters.IEmailAdapter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Email {
    private Long id;
    private String destinatario;
    private String remitente;
    private String asunto;
    private String contenido;
    private boolean enviado;

    private IEmailAdapter emailAdapter;

    public Email(String destinatario, String remitente, String asunto, String contenido) {
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.asunto = asunto;
        this.contenido = contenido;
        this.enviado = false;
    }

    public void enviar() {

        if (emailAdapter == null) {
            throw new IllegalStateException("No se configuró el adaptador del email");
        }
        emailAdapter.enviar(this);
        this.enviado = true;
    }
}

