## Objetivo: ##

Diseñar un modelo de objetos que permita soportar la funcionalidad básica de una herramienta para el control de ítems (pedidos de cambios y requerimientos) de un proyecto de software.

## Introducción: ##

El propósito de esta herramienta es el de ser utilizada en proyectos de desarrollo de software para formalizar la manera en la que se informan y manipulan los diferentes ítems que se van creado a lo largo de la vida de un proyecto. Ejemplos de estas herramientas son:

Bugzilla, Jira y Mantis Bug Tracker.

Los ítems se clasifican utilizando un _tipo_ (reporte de bug, ampliación, mejora, nuevo requerimiento, etc.) para poder asignarle el equipo correcto. Por ejemplo, en caso de ser un reporte de bug, seguramente se deberá asignar un equipo que haya participado en el proceso de desarrollo. Los _tipos_ son dinámicos, en otras palabras, la herramienta debe permitir la creación de nuevos tipos de requerimientos. Estos tipos varían de proyecto en proyecto.

El ciclo de vida de un ítem está determinado por la secuencia de _estados_ por los que puede pasar, por ejemplo, Creado, Análisis, En Desarrollo, Testing, Evaluación Usuario, Aceptado. Los estados deben ser configurados por el líder de proyecto teniendo en cuenta los
diferentes tipos de ítems y los canales más eficientes para su resolución.

De cada tipo de ítem se conocen las posibles secuencias que puede seguir un ítem clasificado con este tipo. Por ejemplo el siguiente gráfico describe las posibles secuencias de estados que puede seguir un ítem clasificado como _reporte de bug_.