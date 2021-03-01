# README #

El sistema esta conectado al servicio postal mexicano, cada que se consulte un cp, seran persistidos los datos y se guardaran en nuetra base de datos local, esto pensado para una v2 , donde se realizaria la consulta en la db si es que llegara a fallar la pagina oficial de servicio postal mexicano.

Este sistema funciona con una base de datos en MySQL con los siguientes parametros:
password: zip_code
username: root
url: jdbc:mysql://localhost:3306/codigos_postales?useSSL=false&serverTimezone=America/Mexico_City&useLegacyDatetimeCode=false
nombre de la bs: codigos_postales

Con la direccion local:
http://localhost:8080/zip-codes/50780

Tambien integracion de swangger para mejor integracion y control:
http://localhost:8080/swagger-ui.html


Las vesiones para el entorno de desarrollo son:
Apache Maven 3.6.3
git version 2.25.1
java version "1.8.0_281"
Spring tools suite 4


archivo en git:
git clone https://jesusnava@bitbucket.org/jesusnava/apicodigopostal.git