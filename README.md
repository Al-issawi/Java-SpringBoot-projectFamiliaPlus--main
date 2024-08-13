Proyecto DAW 2021/2023
Familia+
Equipo 06

**Nota Importante:**  
Este proyecto ya no está disponible en el servidor. A continuación, así como instrucciones alternativas para aquellos interesados en trabajar con el código localmente.

## Razones por la Retirada del Proyecto del Servidor
## Asistente técnico:En El Archivo **DOC** se encuentra :
-          Clasifica usuarios
-          Gestiona perfiles
-          Gestiona base de datos
-          Administra el sistema

# Bbdd: MySQL

### Paso 1: Las entidades pasarán a ser tablas:
•   	Usuario
•       Residente
•       Cuidados
•       Personal
•       Centro.
•       PARTENCE
•       Cuidad.

### Paso 2: Los atributos de cada entidad pasarán a ser columnas de dichas tablas.

1.      USARIO (código, Nombre, Apellidos, Dirección, Email, móvil, fecha de alta.contrato,).
2.      RESIDENTE (código. nombre, edad, habitación, fecha de alta).
3.      CUIDADOS DIARIOS: (código. descripción, fecha)
4.      Personal del centro: (cod-personal. nombre. Puesto).
5.      Centro: cod.centro, nombre, dirección.teff.)
6.      Cuidad:cp,provencia,nom_cuidad,
7.      partencia:centro,personal.

### Paso 3: Los identificadores principales :
1.      Usario: codigo-usario.                              	
2.      Residente: codigo-resedente.                              	
3.      CUIDADOS DIARIOS: código-cuidados.            	
4.      Personal del centro: código-personal
5.      Centro:código.
6.      cuidad:cp

### Paso 4:  modelo relacional :
1.      USARIO (código, Nombre, Apellidos, Dirección, Email, móvil, fecha de alta, contrato, relación-Familiar).
2.      RESIDENTE ( codigo usario,código centro).,
3.      Pertence: código personal,código centro.
4.      Centro :cp cuidad,
5.      Cuidado:código residente,código personal.
6.      perentence:codigo centro, codigo personal.

## BBDD corregido

    USUARIO (cod_usuario, nombre, apellidos, email, fecha_alta, contraseña).
    RESIDENTE (n_resi, nombre, apellidos, edad, n_hab, cod_usuario, código).
    CUIDADO (n_resi, ID, descripción, fecha).
    PERSONAL (ID, nombre, apellidos, puesto, contraseña).
    PERTENECE (ID, código)
    CENTRO (código, nombre, dirección, CP, teléfono).
    CIUDAD (CP, ciudad, provincia).

## c. Casos de uso 

En el primer diagrama de casos de uso están 3 actores principales. 
Con el desarrollo y la expansión del programa, aquí se pueden agregar más actores y otros servicios,

lo que ampliará las capacidades del programa. 
A continuación se muestra una descripción más detallada de cada caso de uso:

## Diagramas:
#### diagrama
![Diragrama.png](./diagrama.png)

#### BaseRelacionadoDiagrama
![BaseRelacionadoDiagram.png](./BaseRelacionadoDiagrama.png)

#### DDBB Diagrama. 
![DiagramaDDBB.png](./DiagramaDDBB.png)

#### DiagramaDeClases
![DiagramaDeClases.jpg](./DiagramaDeClases.jpg)

#### diagramaDeFlujo
![diagramaDeFlujo.jpg](./diagramaDeFlujo.jpg)

#### diagramaDeTecnologia
![diagramaDeTecnologia.jpg](./diagramaDeTecnologia.jpg)



## Conexion BBDD en Servidor Heroku

![herukoconeccion3.jpg](./herukoconeccion3.jpg)

![complexional2.jpg](./herukoconeccion2.jpg)

![serverHerokuConMYSQL.png](./serverHerokuConMYSQL.png)

![pruebaHeroku1.png](./pruebaHeroku1.png)

![prueba14.jpg](./prueba14.jpg)

![MVCDiagrama.jpg](./MVCDiagrama.jpg)
