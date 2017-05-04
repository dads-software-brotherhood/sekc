# The Software Engineering Knowledge Composer

Repositorio oficial del **_Softwar Engineering Knowledge Composer_**

El proyecto está dividio en dos componentes, el primero es el `essence-core` y contiene todas las interfaces del meta-modelo de essence y el segundo es el `sekc` que representa al proyecto general. Los repositorios se encuentra en las siguientes url's:

1. [Essence Meta-Model](https://github.com/danimaniarqsoft/essence-metamodel)
2. [Software Engineering Knowledge Composer](https://github.com/dads-software-brotherhood/sekc)



# Pre-requisitos

1. Instalar Mongodb
2. Instalar Java 8
3. Instalar Maven
4. Instalar Eclipse última versión web.


# Configuración del proyecto

## Clonar el proyecto del prepositorio de git
```bash
git clone -b develop git@github.com:dads-software-brotherhood/sekc.git 
```

## Levantar el proyecto
```bash
mvn spring-boot:run
```

el proyecto estárá disponible en la url:
```bash
http://localhost:8080
```

# Estructura del proyecto

webapp
├── app                               - Your application
│   ├── account                       - User account management UI
│   ├── admin                         - Administration UI
│   ├── blocks                        - Common building blocks like configuration and interceptors
│   ├── components                    - Common components like alerting and form validation
│   ├── entities                      - Generated entities (more information below)
│   ├── home                          - Home page
│   ├── layouts                       - Common page layouts like navigation bar and error pages
│   ├── services                      - Common services like authentication and user management
│   ├── app.constants.js              - Application constants
│   ├── app.module.js                 - Application modules configuration
│   ├── app.state.js                  - Main application router
├── bower_components                  - Dependencies installed by Bower
├── content                           - Static content
│   ├── images                        - Images
│   ├── styles                        - CSS stylesheets
│   ├── fonts                         - Font files will be copied here
├── i18n                              - Translation files
├── scss                              - Sass style sheet files will be here if you choose the option
├── swagger-ui                        - Swagger UI front-end
├── 404.html                          - 404 page
├── favicon.ico                       - Fav icon
├── index.html                        - Index page
├── robots.txt                        - Configuration for bots and Web crawlers
