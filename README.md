[COJ](http://coj.uci.cu/) - Caribbean Online Judge
==================================================

El Juez en Línea Caribeño tiene un motor de evaluación completamente desacoplado hasta el punto de convertirlo en un componente separado. El proyecto UEngine persigue el perfeccionamiento de este motor con vistas a mejorar las funcionalidades brindadas al Juez en Línea Caribeño.

## Manual de Instalación

Guia de instalacion y configuracion del Uengine

Introduccion
Uengine es el motor de evaluacion del Caribbean Online Judge.

Requerimientos
Uengine esta probado en `Ubuntu 14.04 y 12.04`, tanto server como escritorio, 32 y 64 bits. Uengine usa [libsandbox](https://openjudge.net/Solution/Sandbox) para ejecutar los envios de los usuarios en un ambiente seguro y controlado (un sandbox o jail)

Requiere los compiladores de los lenguajes que se quieran adicionar al Juez en Lineah. Actualmente estos son:....uyeap

### Registrar como servicio

    - hacer un fichero en /etc/init.d/uengine con el siguiente texto:
    #!/bin/bash

    case $1 in
    start)
    nohup java -jar /opt/chjudgeEngine.jar & echo $! > /var/run/chjudge.pid
    msg=`cat /var/run/chjudge.pid`
    echo "starting engine with pid ${msg}"

    ;;
    stop)
    kill -9 `cat /var/run/chjudge.pid`
    rm /var/run/chjudge.pid
    ;;
    restart)
    echo "stoping engine"
    /etc/init.d/chjudgeengine stop
    sleep 1
    /etc/init.d/chjudgeengine start
    ;;
    ?)
    echo 'invalid parameter';
    ;;
    esac

### Configuraciones extras

* Recordar configurar el LibSandbox
* También configurar los properties según el entorno


### Compruebe los compiladores. ej:

	/usr/bin/gcc

## Contáctenos

Preguntas o comentarios a través del mecanismo establecido en el Juez en Línea Caribeño, [Contáctenos](http://coj.uci.cu/general/contact.xhtml)