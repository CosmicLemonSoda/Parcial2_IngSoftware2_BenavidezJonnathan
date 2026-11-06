package infrastructure.config;

// Configuración desactivada temporalmente para modo de emergencia sin servidor RabbitMQ
public class RabbitMQConfig {
    public static final String EXCHANGE = "saberpro.direct.exchange";
    public static final String QUEUE = "question.submit.review.queue";
    public static final String ROUTING_KEY = "question.submit";
}
