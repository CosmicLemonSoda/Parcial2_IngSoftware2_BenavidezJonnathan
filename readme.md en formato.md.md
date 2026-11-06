# Microservicio saberpro-question-service - Parcial Práctico (Corte 2)

**Repositorio oficial**: [https://github.com/CosmicLemonSoda/Parcial2_IngSoftware2_BenavidezJonnathan](https://github.com/CosmicLemonSoda/Parcial2_IngSoftware2_BenavidezJonnathan)  
**Estudiante**: Jonnathan Benavidez  
**Asignatura**: Ingeniería de Software II  

Este repositorio contiene la implementación del microservicio de gestión de preguntas del sistema SaberPro correspondiente al Segundo Parcial Práctico de Laboratorio (Corte 2). La solución está desarrollada bajo los principios de Arquitectura Limpia, integrando persistencia relacional con Spring Data JPA, validación REST y los patrones de diseño GoF Builder y State.

---

## 1. Arquitectura y Patrones de Diseño

El código fuente está estructurado de forma modular para garantizar la separación de responsabilidades entre las reglas del negocio y la infraestructura tecnológica:

* **Arquitectura Limpia**:
  * `domain.model`: Contiene las entidades puras del dominio (`Question`, `AnswerOption`), los contratos/puertos (`QuestionRepositoryPort`), los estados concretos del patrón State y la interfaz del patrón Builder.
  * `infrastructure.rest`: Contiene los controladores REST (`QuestionController`) y los DTOs de transferencia para la recepción y respuesta de peticiones HTTP.
  * `infrastructure.jpa`: Contiene la implementación de la persistencia, incluyendo entidades JPA (`QuestionRecord`, `AnswerOptionRecord`), repositorios Spring Data y mapeadores de entidad a dominio.
  * `infrastructure.messaging`: Estructura para la gestión de mensajes asíncronos AMQP (`LifecycleListener`, `SubmitQuestionEvent`).
  * `infrastructure.config`: Definición de componentes del sistema, cliente RestTemplate y constantes de RabbitMQ.

* **Patrón Builder (`QuestionBuilder`)**:
  Encapsula la creación del objeto complejo `Question`. Aplica las validaciones del dominio antes de la construcción: exige exactamente 4 opciones de respuesta y valida que una sola de ellas esté marcada como correcta (`correct = true`).

* **Patrón State (`QuestionState`, `DraftState`, `PendingReviewState`)**:
  Modela el ciclo de vida de la pregunta. Permite efectuar transiciones de estado (de `DRAFT` a `PENDING_REVIEW`) mediante comportamiento polimórfico en lugar de utilizar estructuras condicionales avanzadas (`if` / `switch`) en la capa de aplicación.

---

## 2. Precisiones de Entorno y Plan de Contingencia

Durante el proceso de desarrollo y ejecución en las estaciones de trabajo del laboratorio de sistemas se identificaron y solucionaron las siguientes restricciones de entorno:

1. **Incapacidad de conexión a RabbitMQ Server**:
   Los equipos del laboratorio no cuentan con una instancia activa de RabbitMQ Server en los puertos 5672/15672 ni con permisos de administración para la instalación local del broker.
   * **Solución técnica**: Se excluyó la autoconfiguración automática de AMQP en `application.properties` mediante `spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration`. Para garantizar el funcionamiento del flujo, el endpoint `POST /questions/{id}/submit` procesa el cambio de estado invocando directamente los métodos del patrón State sobre la entidad de dominio. No obstante, las clases de configuración (`RabbitMQConfig.java`), eventos (`SubmitQuestionEvent.java`) y consumidor (`LifecycleListener.java`) permanecen dentro del proyecto como evidencia de la implementación asíncrona.

2. **Codificación de Archivos (UTF-8 con BOM en Windows)**:
   Al trabajar sobre la consola de PowerShell en Windows, los archivos de código fuente generados incluyeron la marca de orden de bytes (`\ufeff`), ocasionando fallos de compilación en el compilador `javac`.
   * **Solución técnica**: Se ejecutó la conversión masiva de la totalidad de archivos `.java` a codificación UTF-8 pura sin BOM dentro del directorio `src/main/java`.

3. **Disponibilidad del Comando Maven en PATH**:
   El comando global `mvn` no se encontraba registrado en las variables de entorno del sistema operativo.
   * **Solución técnica**: Se habilitó dinámicamente la ruta de ejecución de Maven (`mvn.cmd`) dentro de la sesión activa de PowerShell para permitir los comandos de compilación y despliegue local.

---

## 3. Requisitos del Sistema

* **Java Development Kit (JDK)**: Versión 17 o superior.
* **Gestor de Construcción**: Apache Maven 3.8+.
* **Base de Datos**: H2 Database (en memoria).
* **Cliente HTTP**: Postman o Thunder Client.

---

## 4. Guía de Compilación y Ejecución

1. Clonar el repositorio desde GitHub:
   ```powershell
   git clone https://github.com/CosmicLemonSoda/Parcial2_IngSoftware2_BenavidezJonnathan.git
   cd Parcial2_IngSoftware2_BenavidezJonnathan
   ```

2. Abrir la terminal de PowerShell en la raíz del proyecto.

3. En caso de presentar problemas con procesos de Java previos o falta del comando `mvn` en la consola, ejecutar el siguiente bloque de preparación:
   ```powershell
   Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue
   $mvnFile = Get-ChildItem -Path "C:\" -Filter "mvn.cmd" -Recurse -ErrorAction SilentlyContinue | Select-Object -First 1
   if ($mvnFile) { $env:PATH += ";$($mvnFile.Directory.FullName)" }
   ```

4. Compilar y levantar la aplicación Spring Boot:
   ```powershell
   mvn clean spring-boot:run
   ```

La aplicación quedará disponible y escuchando en el puerto **`8083`**.

---

## 5. Matriz de Pruebas de Endpoints

### 5.1. Prueba de Validación REST (Justificación $\le 15$ caracteres)

* **Método**: `POST`
* **URL**: `http://localhost:8083/questions`
* **Headers**: `Content-Type: application/json`
* **Body (JSON)**:
  ```json
  {
    "statement": "¿Cuál es el patrón GoF para construir objetos complejos paso a paso?",
    "justification": "Es muy corta",
    "opt1": "Builder",
    "opt1Correct": true,
    "opt2": "Factory",
    "opt2Correct": false,
    "opt3": "State",
    "opt3Correct": false,
    "opt4": "Adapter",
    "opt4Correct": false
  }
  ```
* **Resultado Esperado**:
  * **HTTP Status**: `400 Bad Request`
  * **Cuerpo**: Mensaje notificando que la justificación no supera los 15 caracteres mínimos. El registro no se persiste en la base de datos.

---

### 5.2. Creación de Pregunta (Builder Pattern + Estado DRAFT)

* **Método**: `POST`
* **URL**: `http://localhost:8083/questions`
* **Headers**: `Content-Type: application/json`
* **Body (JSON)**:
  ```json
  {
    "statement": "¿Qué patrón de diseño permite que un objeto altere su comportamiento cuando su estado interno cambia?",
    "justification": "El patrón State encapsula los estados en clases independientes y permite la transición de comportamiento sin estructuras condicionales.",
    "opt1": "State",
    "opt1Correct": true,
    "opt2": "Strategy",
    "opt2Correct": false,
    "opt3": "Observer",
    "opt3Correct": false,
    "opt4": "Decorator",
    "opt4Correct": false
  }
  ```
* **Resultado Esperado**:
  * **HTTP Status**: `201 Created`
  * **Cuerpo**: Retorna la entidad creada en formato JSON conteniendo un identificador UUID y el campo `"state": "DRAFT"`.

---

### 5.3. Transición de Estado (State Pattern: DRAFT -> PENDING_REVIEW)

* **Método**: `POST`
* **URL**: `http://localhost:8083/questions/{ID_GENERADO}/submit`
* **Resultado Esperado**:
  * **HTTP Status**: `202 Accepted`
  * **Cuerpo**:
    ```json
    {
      "operationId": "...",
      "status": "SUBMITTED_FOR_REVIEW"
    }
    ```

---

## 6. Verificación de Persistencia en Consola H2

1. Abrir en el navegador de internet la dirección `http://localhost:8083/h2-console`.
2. Completar los datos de conexión:
   * **JDBC URL**: `jdbc:h2:mem:saberprodb`
   * **User Name**: `sa`
   * **Password**: *(dejar en blanco)*
3. Presionar **Connect** y ejecutar las consultas SQL de validación:

```sql
-- Verificar que la pregunta guardada haya actualizado su estado a PENDING_REVIEW
SELECT id, statement, justification, state FROM questions;

-- Verificar la persistencia de las 4 opciones de respuesta asociadas
SELECT id, text, correct, question_id FROM answer_options;
```