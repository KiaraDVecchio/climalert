package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.CondicionAlerta;
import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.models.repositories.IClimaRepository;
import ar.utn.ba.ddsi.mailing.services.IAlertasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

@Service
public class AlertasService implements IAlertasService {
    private static final Logger logger = LoggerFactory.getLogger(AlertasService.class);

    private final IClimaRepository climaRepository;
    private final EmailService emailService;
    private final String remitente;
    private final List<String> destinatarios;
    private final CondicionAlerta condicionAlerta;

    // double TEMPERATURA_ALERTA = 35.0;
    // private static final int HUMEDAD_ALERTA = 60;

    public AlertasService(
        IClimaRepository climaRepository,
        EmailService emailService,
        @Value("${email.alertas.remitente}") String remitente,
        @Value("${email.alertas.destinatarios}") String destinatarios,
        @Value("${alerta.temperatura.minima}") double tempMin,
        @Value("${alerta.humedad.minima}") int humedadMin)
    {
        this.climaRepository = climaRepository;
        this.emailService = emailService;
        this.remitente = remitente;
        this.destinatarios = Arrays.asList(destinatarios.split(","));
        this.condicionAlerta =  new CondicionAlerta(tempMin, humedadMin);
    }

    @Override
    public Mono<Void> generarAlertasYAvisar() {
        return Mono.fromCallable(() -> climaRepository.findByProcesado(false))
            .flatMap(climas -> {
                logger.info("Procesando {} registros de clima no procesados", climas.size());
                return Mono.just(climas);
            })
            .flatMap(climas -> {
                climas.stream()
                    .filter(condicionAlerta::cumple)
                    .map(Alerta::new)
                    .forEach(this::enviarAlertaPorEmail);

                // Marcar todos como procesados
                climas.forEach(clima -> {
                    clima.setProcesado(true);
                    climaRepository.save(clima);
                });

                return Mono.empty();
            })
            .onErrorResume(e -> {
                logger.error("Error al procesar alertas: {}", e.getMessage());
                return Mono.empty();
            })
            .then();
    }

    private void enviarAlertaPorEmail(Alerta alerta) {
        for (String destinatario : destinatarios) {
            Email email = alerta.generarEmail(remitente, destinatario);
            emailService.crearEmail(email);
        }

        logger.info("Alerta generada para {} - Emails creados para {} destinatarios",
            alerta.getClima().getCiudad(), destinatarios.size());
    }
}