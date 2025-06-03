package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Alerta {
  private final Clima clima;
  private final String mensaje;
  private final LocalDateTime fechaGeneracion;

  public Alerta(Clima clima) {
    this.clima = clima;
    this.fechaGeneracion = LocalDateTime.now();
    this.mensaje = generarMensaje(clima);
  }

  private String generarMensaje(Clima clima) {
    return String.format(
        "ALERTA: Condiciones climáticas extremas detectadas en %s\n\n" +
            "Temperatura: %.1f°C\n" +
            "Humedad: %d%%\n" +
            "Condición: %s\n" +
            "Velocidad del viento: %.1f km/h\n\n" +
            "Se recomienda tomar precauciones.",
        clima.getCiudad(),
        clima.getTemperaturaCelsius(),
        clima.getHumedad(),
        clima.getCondicion(),
        clima.getVelocidadVientoKmh()
    );
  }

  public Email generarEmail(String remitente, String destinatario) {
    String asunto = "Alerta de Clima - Condiciones Extremas";
    return new Email(destinatario, remitente, asunto, mensaje);
  }
}
