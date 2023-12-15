# Ejercicio BCI
## Desarrollo de Microservicio para BCI
## Tabla de contenido
* [Información General](#general-info)
* [Tecnologías](#technologies)
* [Instalación](#setup)
* [Ejecución](#ejecucion)
## Información General
El objetivo de este proyecto es desarrollar un microservicio para la creación y consulta de usuarios.

## Tecnologías
El proyecto ha sido creado con las siguientes herramientas y versiones:
* Java 11
* Spring Framework 5.3.20
* SpringBoot 2.5.14
* Gradle 7.4
* Lombok 1.8.30
* JUnit 5
* Mockito
* Docker

## Instalación
* Clonar repositorio master: https://github.com/rebolloomare/ejercicioBCI.git

## Ejecución
Para ejecutar este proyecto desde la linea de comandos seguir los siguientes pasos:
Click en la opción "Run DemoEjercicioBciApplication"
Ingresar por postman mediante POST:
* Endpoint para crear usuarios: http://localhost:8080/bci/users/sign-up
* Endpoint para consultar usuarios: http://localhost:8080/bci/users/auth/login
* Conectarse a la consola de H2: http://localhost:8080/bci/users/h2-console
  * user: sa
  * password: sa
  * Database: bcidb
