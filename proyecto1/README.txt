Proyecto 1

Deben implementar un ordenador lexicográfico que funcione con uno o más archivos 
de texto o la entrada estándar, y que imprima su salida en la salida estándar.

Ejemplo entrada:
En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo
que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y
galgo corredor. Una olla de algo más vaca que carnero, salpicón las más noches,
duelos y quebrantos los sábados, lentejas los viernes, algún palomino de
añadidura los domingos, consumían las tres partes de su hacienda. El resto della
concluían sayo de velarte, calzas de velludo para las fiestas con sus pantuflos
de lo mismo, los días de entre semana se honraba con su vellori de lo más
fino. Tenía en su casa una ama que pasaba de los cuarenta, y una sobrina que no
llegaba a los veinte, y un mozo de campo y plaza, que así ensillaba el rocín
como tomaba la podadera. Frisaba la edad de nuestro hidalgo con los cincuenta
años, era de complexión recia, seco de carnes, enjuto de rostro; gran madrugador
y amigo de la caza. Quieren decir que tenía el sobrenombre de Quijada o Quesada
(que en esto hay alguna diferencia en los autores que deste caso escriben),
aunque por conjeturas verosímiles se deja entender que se llama Quijana; pero
esto importa poco a nuestro cuento; basta que en la narración dél no se salga un
punto de la verdad.

Ejemplo salida en base a la entrada anterior:
añadidura los domingos, consumían las tres partes de su hacienda. El resto della
años, era de complexión recia, seco de carnes, enjuto de rostro; gran madrugador
aunque por conjeturas verosímiles se deja entender que se llama Quijana; pero
como tomaba la podadera. Frisaba la edad de nuestro hidalgo con los cincuenta
concluían sayo de velarte, calzas de velludo para las fiestas con sus pantuflos
de lo mismo, los días de entre semana se honraba con su vellori de lo más
duelos y quebrantos los sábados, lentejas los viernes, algún palomino de
En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo
esto importa poco a nuestro cuento; basta que en la narración dél no se salga un
fino. Tenía en su casa una ama que pasaba de los cuarenta, y una sobrina que no
galgo corredor. Una olla de algo más vaca que carnero, salpicón las más noches,
llegaba a los veinte, y un mozo de campo y plaza, que así ensillaba el rocín
punto de la verdad.
(que en esto hay alguna diferencia en los autores que deste caso escriben),
que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y
y amigo de la caza. Quieren decir que tenía el sobrenombre de Quijada o Quesada

El programa debe poder recibir varios archivos, ya sea en la línea de comandos o por la entrada estándar, en cuyo caso los trata a 
todos como un único archivo grande. 

Reglas: 
  •No pueden usar ninguna clase de Java de java.util, excepto por las pocas interfaces usadas durante 
    el curso (java.util.Iterator, etc.) y excepciones.
  •Pueden (y deben) usar las clases vistas durante el curso.
  •No pueden modificar ninguna de las interfaces públicas de las clases vistas durante el curso. 
    Esto incluye los nombres de los paquetes. 
  •El paréntesis al inicio de la línea (que en esto... en el ejemplo de arriba no es considerado, 
    es como si no existiera al ordenar. No es obligatorio que lo hagan igual, pero de ser así se dará un punto extra.
  •Las clases de su proyecto (no las de las prácticas) deben usar el paquete mx.unam.ciencias.edd.proyecto1.
  •Los archivos de entrada deben poder estar en cualquier lugar en el sistema de archivos, no tienen por qué estar en 
    el mismo directorio del archivo Jar.
  •Si su programa detecta el parámetro -r e invierte el orden (de la Z a la A en lugar de la A a la Z), 
    ganarán un punto extra. La bandera debe poder aparecer en cualquier lugar de la lista de parámetros, 
    y debe funcionar también con la entrada estándar.
  •La complejidad en tiempo del programa debe ser O(n log n), siendo n el número de líneas.

Ejemplo ejecucion del programa:
cat quijote.txt | java -jar proyecto1.jar

Compilacion:
Usar en la terminal el comando "ant" dentro de la carpeta "proyecto1" (Instalar apache ant)
