# Tofu App v0.3 - (Alpha Final proyecto)

## Descripcion

TofuApp tiene la intencion de querer ser una red social con la que compartir recetas con gente, lo que comes cada dia y organizar tus comidas de la semana

Actualmente esta en early development y esta entrega final del proyecto integrado de la FP de desarrollo de aplicaciones multiplataforma que he realizado en el colegio de Los Salesianos de Triana.

## Requerimientos

- Maven

- Docker

- Node.JS

- Android Studio 

## Contenido del repositorio principal

- Archivo Excel (.xlsx) y .ods con informacion sobre los endpoints de la API y sus DTOs

- Memoria del Proyecto

- Archivo figma (.fig) con el diseño que se establecio al principio para las aplicaciones web y movil

- Coleccion de Postman (.json) con todos los endpoints de la aplicacion listos para testear

- APK del proyecto

- 2 scripts (.bat) para facilitar el correr la API y la aplicacion web

- Todo el codigo del proyecto

- Carpeta con los Assets que se han usado durante el proyecto

## Primeros pasos

### Iniciar la API

1. Clonar el repositorio en tu sistema

2. Ejecutar ```docker-compose up``` en la carpeta *./api_tofu_app*

3. Ejecutar el script *run_server.bat* o por el contrario ejecutar el comando ```mvn spring-boot:run``` en la carpeta *./api_tofu_app*

4. *Opcional:* Tambien se puede ejecutar la API desde el IDE IDEA IntelliJ abriendo la carpeta ya mencionada y configurando para que ejecute `mvn spring-boot:run`

### Iniciar la aplicacion Web

1. Asegurarse de tener docker y la api ejecutandose

2. Ejecutar el script *run_web.bat* o por el contrario en la carpeta *./web_tofu_app* ejecutar los siguientes comandos:
   
   ```
   npm i
   
   ng serve -o
   ```

### Iniciar la aplicacion Movil

Dado que la API corre en local, y no es el mismo local que donde se corre el movil (Ya sea por emulador, por APK o por conexion directa en movil con Android Studio) no se recomienda usar la APK ya que habra que configurar primero la URL de la API segun cada caso.

#### Con conexion directa desde Android Studio (recomendado)

1. Abrir un terminal y ejecutar el comando `ipconfig`

2. Buscar la direccion *IPv4* que empieza por `192.168.x.xxx`  *EJ: 192.168.1.131*

3. En android studio, en el archivo `.\mobile_tofu_app\app\src\main\...\util\Constants.kt` cambiar la variable constante *networkIP* por la IPv4 que se busco antes

4. En el archivo `./mobile_tofu_app/app/src/main/res/xml/network_security_config.xml` cambiar la direccion IPv4 por la que hemos obtenido antes

5. En el dispositivo movil habilitar las opciones de desarrollador

6. Conectar el movil via usb al PC, y ejecutar la aplicacion desde Android Studio

#### Via emulador de Android Studio

1. En android studio, en el archivo `.\mobile_tofu_app\app\src\main\...\util\Constants.kt` descomentar la variable constante *API_BASE_URL* y comentar la que esta actualmente sin comentar

2. Ejecutar la aplicacion desde el emulador de Android Studio

## Funcionalidades añadidas desde la ultima entrega

### API

- Fix de un problema con el borrado de recetas

- Añadido UTF-8

- Fix menores

### WEB

- Añadir recetas

- Borrado de recetas

- Añadir ingredientes a recetas

- Añadir Pasos a recetas

- Borrado de Pasos

- Edicion de Pasos

### Movil

- Rehacer la aplicacion de Flutter en Android con kotlin

- Autenticacion con token, login y registro

- Listado de recetas y vista detalle de estas

- Creacion de recetas

- Borrado de recetas

- Listado de ingredientes de una receta 

- Listado de pasos de una receta

- Navegacion entre fragments

## Guia visual de las aplicaciones

Guia visual para poder ver donde esta cada funcionalidad de las aplicaciones, y tener una idea visual de como son sin necesidad de ejecutar

### Web

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_web_1.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_web_2.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_web_3.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_web_4.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_web_5.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_web_6.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_web_7.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_web_8.png)

### Movil

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_mobile_1.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_mobile_2.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_mobile_3.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_mobile_4.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_mobile_5.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_mobile_6.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_mobile_7.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_mobile_8.png)

![](https://github.com/SirMowglo/TofuApp/blob/main/assets/screenshots/Screenshot_mobile_9.png)
