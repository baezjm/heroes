# Prueba técnica spring boot: W2m

## Consigna

Desarrollar, utilizando Spring Boot 2 y Java 11, una API que permita hacer un mantenimiento de súper
héroes.
Este mantenimiento debe permitir:
* Consultar todos los súper héroes.
* Consultar un único súper héroe por id.
* Consultar todos los súper héroes que contienen, en su nombre, el valor de un parámetro
  enviado en la petición. Por ejemplo, si enviamos “man” devolverá “Spiderman”, “Superman”,
  “Manolito el fuerte”, etc.
* Modificar un súper héroe.
* Eliminar un súper héroe.
* Test unitarios de algún servicio.


## Tecnologías utilizadas para implementar la solución.

* Lenguaje: java versión 11
* Framework: spring boot.
* Librerias: mockito para los tests, jacoco para la cobertura, springdoc-openapi-ui para documentar los servicios rests, lombok, flyway
* Docker


## Instalación  del proyecto
1. **Descargar código fuente**

```console
git clone https://github.com/baezjm/heroes.git
```

2. **Compilar**

```console
mvn clean install
```

3. **Correr el proyecto localmente**

```console
mvn spring-boot:run
```

4. **Generar imagen de docker**

```console
docker build -t w2m/heroes .
```

5. **Levantar imagen de docker**

```console
docker run -p 8080:8080 w2m/heroes
```

6. **Urls**

   [Documentación de la api: Swagger](http://localhost:8080/swagger-ui.html)

![](/documentation.png)

7. **Invocación a servicios**

[Collection de postman con tests](https://go.postman.co/workspace/My-Workspace~aec8abf7-c487-43f7-8761-05dea536ee94/collection/4233576-4f991fd8-bf1e-49dc-9311-5868510c6dbd)

* Login

```console
curl --location --request POST 'localhost:8080/api/auth/signin'
--data-raw '{
    "username":"admin",
    "password":"admin"
}'
```

* Crear un Heroes

```console
curl --location --request POST 'localhost:8080/api/hero' \
--header 'Authorization: Bearer {token}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"spidermanasd"
}'
```

* Listar todos los súper heroes

```console
curl --location --request GET 'localhost:8080/api/hero/all' \
--header 'Authorization: Bearer {token} \
```
* Consultar súper héroe por id

```console
curl --location --request GET 'localhost:8080/api/hero/1' \
--header 'Authorization: Bearer {token} \ 
```
* Consultar súper héroe por nombre

```console
curl --location --request GET 'localhost:8080/api/hero?name=man' \
--header 'Authorization: Bearer {token} \ 
```

* Actualizar súper héroe

```console
curl --location --request PUT 'localhost:8080/api/hero/4' \
--header 'Authorization: Bearer {token}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"test"
}'
```
* Borrar súper héroe

```console
curl --location --request DELETE 'localhost:8080/api/hero/3' \
--header 'Authorization: Bearer {token} \ 
```



## Tests

**Ejecución de tests: el mismo corre jacoco para medir cobertura**
```console
 mvn clean test
```

![](/jacoco.png)

El resultado queda en:

```console
 ../target/site/jacoco/index.html
```
