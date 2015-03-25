### Tecnologias utilizadas: ###

Persistencia de objectos: Hibernate 3.6.7
Interfaz Web: Apache Wicket 1.5.4
Integracion: Maven 2.2.1 y Spring 3.1.0
Seguridad: Spring Security 3.1.0

### Instalación: ###

svn checkout https://seguimiento-items.googlecode.com/svn/trunk/seguimiento

cd seguimiento

mvn install

cd web

mvn jetty:run