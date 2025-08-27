# Laboratorio de Paralelismo

El programa hace uso del algoritmo **mmClasicaOpenMP**, el cual es una implementación en **C** de la multiplicación clásica de matrices cuadradas, optimizada con **OpenMP** para ejecutarse en paralelo y reducir el tiempo de cómputo.

---

### 1. Documentación del Código

#### 1.1 `mmClasicaOpenMP.c`
Este programa implementa la multiplicación de matrices cuadradas haciendo uso de **paralelismo con OpenMP**.  
El usuario indica el tamaño de la matriz y el número de hilos a utilizar.  

- Las matrices **A** y **B** se inicializan con valores aleatorios.  
- El resultado de la multiplicación se almacena en la matriz **C**.  
- Para medir el rendimiento, el programa toma el tiempo de ejecución en microsegundos antes y después de realizar la multiplicación.  
- Si la matriz posee una dimensión menor a 9, el programa la imprime en pantalla para facilitar la validación del cálculo.  

---

#### 1.2 `lanzador.pl`
Este script automatiza la ejecución por conjuntos de **mmClasicaOpenMP**.  

- Su propósito es probar diferentes tamaños de matrices y número de hilos para evaluar el rendimiento del programa.  
- Para cada combinación, el script ejecuta el programa 30 veces y guarda los resultados en un archivo `.dat`.  
- Los archivos se nombran siguiendo un formato específico.

De esta manera, se genera un conjunto de resultados que reflejan el comportamiento del algoritmo bajo diferentes cargas computacionales.  

---

#### 1.3 `Makefile`
El **Makefile** automatiza la compilación de **mmClasicaOpenMP**.  
Facilita la compilación repetitiva del proyecto con un solo comando, evitando tener que escribir manualmente la línea de compilación.  

---

### 2. Diseño del Experimento

- Se seleccionaron cinco cantidades de hilos: `1, 4, 8, 16 y 20`.  
- Se definieron siete tamaños de matrices diferentes: `240, 880, 1520, 3040, 4960, 7200, 9280`.
- Cada tamaño fue elegido como múltiplo de 80, lo cual facilita la distribución de hilos y mejora la estructura interna de los cálculos.  


**Nota:**  
En las condiciones iniciales del taller se establecía el uso de 12 matrices de hasta un tamaño de 20.000, pero este se redujo posteriormente a 14.000.  
Sin embargo, la ejecución del programa se realizó con el tamaño original de 20.000.  

Algunas matrices no se procesaron debido al tiempo prolongado requerido para su ejecución, por ello, se obtuvo siete tamaños diferentes en lugar de doce. 

---

### 3. Análisis Estadístico

#### `Matriz de tamaño 240`

En esta matriz, los resultados muestran de los efectos de la paralelización. Con un solo hilo, el tiempo promedio de ejecución fue ede 6769,87 ms, con una desviación estándar de 2068.74 ms, lo que refleja un tiempo muy alto y con bastantane variacion entre cada corrida. Al aumentar a 4 hilos, el tiempo promedio se reduce a 1983,07 ms, con una desviación mucho menor de 331,46, logrando un speedup (razón entre el tiempo de ejecución secuencial y el tiempo paralelo) de aproximadamente 3,4x, lo que muestra que la paralelizacion no solo acelera la ejecución, sino aporta mayor consistencia. Sin embargo, al aumentar a 8 hilos, tanto el tiempo promedio como la desviación estándar suben a 2331,43 ms y 675,97 ms respectivamente,esto expone un ejemplo de sobrecarga de sincronización, que se refiere a que aunque hay más hilos trabajando en paralelo, el costo de mantenerlos coordinados empieza a disminuir la ganancia en rendimiento. El mejor desempeño se alcanza con 16 hilos, donde el tiempo promedio baja a 1622,77 ms con desviación de 259,36 ms, aqui el speedup es de 4,17x, lo que da a entender que representa la mayor eficiencia, ya que el trabajo es más de cuatro veces más rápido que la ejecución secuencial y ademas tiene buena estabilidad en las mediciones. Finalmente, al incrementar a 20 hilos, el tiempo vuelve a aumentar a 1877,63 ms, con desviación de 527,81 ms, lo que refuerza la idea que añadir hilos no siempre garantiza mejoras. Introducir cada vez más hilos, se traduce a mayoe sobrecarga de sincronización, ya que cda hilo requiere recursos del sistema para coordinarse e intercambiar datos. 

En conclusión, para la matriz de 240, el óptimo número de hilos es 16 ya que ofrecen mejor balance entre rendimiento y estabilidad. 

<div align="center">
<img width="315" height="60" alt="image" src="https://github.com/user-attachments/assets/23505f78-6f5c-47d6-baeb-64d3cf696563" />
</div>
<div align="center">
  <img width="315" height="180" alt="image" src="https://github.com/user-attachments/assets/03003b62-d69d-4255-baaa-e90bea615029" />
</div>

#### `Matriz de tamaño 880`

En este caso, el tiempo promedio de ejecución con 1 hilo fue de 415.925,93 ms con una desviación estandar de 9.107,27 ms, lo que refleja una ejecución consistente. Al aumentar el número de hilos se observa una mejora en el rendimiento: con 4 hilos, el tiempo promedio se reduce a 117.212,20 ms, y con 8 hilos baja aún más a 98.768,30 ms. Aunque en este último punto la desviación estándar aumenta a 12.878,55 ms, lo que muestra mayor variabilidad en las mediciones, el beneficio en rendimiento sigue siendo bueno. Con 16 hilos, el tiempo promedio baja a 85.205,00 ms, con una desviación estándar menor de 7.776,57 ms, lo que representa el mejor equilibrio entre tiempo de ejecución y consistencia en los resultados. Finalmente, con 20 hilos se obtiene el mejor tiempo promedio 73.975,60 ms y la menor variabilidad 3.292,65 ms, lo cual aclara que, para este tamaño de matriz, la escalabilidad es muy buena y constante incluso con un número mayor de hilos.

Al calcular el speedup respecto a la ejecución secuencial, se observa una aceleración de aproximadamente 3,55× con 4 hilos, 4,21× con 8 hilos, 4,88× con 16 hilos y hasta 5,62× con 20 hilos. Esto indica que, a diferencia de matrices más pequeñas (donde la sobrecarga de sincronización puede deteriorar el rendimiento al incrementar el número de hilos), en este tamaño de problema el paralelismo se aprovecha de manera más eficiente.

<div align="center">
<img width="315" height="60" alt="image" src="https://github.com/user-attachments/assets/6dce1ec2-6f45-4366-8057-d96a27567096" />
</div>
<div align="center">
  <img  width="315" height="180" alt="image" src="https://github.com/user-attachments/assets/46735b52-0efc-4adc-b97e-c1bb807bbcbb" />
</div>

#### `Matriz de tamaño 1520`

Con un solo hilo, el tiempo promedio fue de 2.414.296,53 ms, con una desviación estándar de 96807,51 ms. Al incrementar a 4 hilos, el tiempo cae a 749.104,33, evidenciando un buen speedup debido a que el trabajo se reparte de manera más efectiva y se aprovechan mejor los recursos disponibles. Con 8 hilos, hay una mejora adicional hasta 574.055,70 ms, aunque se ve una menor ganancia en comparación con el salto de 1 a 4 hilos, lo puede indicar el inicio de sobrecarga de sincronización. Cuando se incrementa a 16 hilos, el tiempo se reduce aún más a 509.085,57 ms, lo que representa el mejor rendimiento, ya que en este punto la carga de trabajo se distribuye de manera muy eficiente y la sobrecarga aún esta en un rango normal. Sin embargo, al pasar a 20 hilos, hay disminución del rendimiento, ya que el tiempo promedio aumenta a 687.454,40 ms, lo que indica que los costos asociados a la comunicación y sincronización entre los hilos superan los beneficios de paralelizar más allá de 16. La desviación estándar observa una disminución continua a mediada que se incrementa el numero de hilos hasta llegar a 16, despues de ellos, la desviación crece, lo que siginifica pérdida de eficiencia. 

En conclusión, para esta matriz el punto óptimo se alcanzó con 16 hilos, donde se obtuvo el mejor balance entre reducción de tiempo, speedup y estabilidad en la ejecución.

<div align="center">
<img width="315" height="60" alt="image" src="https://github.com/user-attachments/assets/743aa2f2-db04-4763-b4d3-1edaac287273" />
</div>
<div align="center">
  <img width="315" height="180" alt="image" src="https://github.com/user-attachments/assets/eb87916b-d9d8-4e60-8eb5-f2b985ab6b2f" />
</div>

#### `Matriz de tamaño 3040`

Se observa un comportamiento de mejora en los tiempos promedio a medida que aumenta el número de hilos. Con 1 hilo, el promedio es de 79.748.162,83 ms, mientras que al utilizar 4 hilos este valor disminuye a 25.563.157,73 ms, lo que refleja un aprovechamiento inicial muy fuerte del paralelismo. Al incrementar a 8 hilos, el tiempo sigue reduciéndose hasta 21.492.425,47 ms, y con 16 hilos la mejora es más evidente, alcanzando los 16.471.654,57 ms, lo que que representa una disminución de casi cinco veces respecto a la ejecución secuencial. Finalmente, con 20 hilos se obtiene un promedio de 15.424.520,93 ms, lo que confirma la continua optimización, aunque la ganancia adicional frente a los 16 hilos es menor, evidenciando el punto en el que las mejoras comienzan a decaer debido a los costos de sincronización y gestión de este.

En cuanto a la desviación estándar, se observa que se mantiene baja en todos los casos, lo que indica estabilidad en la ejecución. Con 1 hilo la desviación es de 1.031.554,02 ms, este disminuye con el aumento de hilos. Esto muestra que, además de reducir los tiempos, el aumento de hilos también ayuda a una mayor consistencia en los resultados corridos.

<div align="center">
<img width="315" height="60" alt="image" src="https://github.com/user-attachments/assets/ae97d9e3-bb5b-47e8-8e0d-937764232d97" />
</div>
<div align="center">
  <img width="315" height="180" alt="image" src="https://github.com/user-attachments/assets/4d71aec2-5498-4fd2-9449-341d7e13d5db" />
</div>

#### `Matriz de tamaño 4960`

Se observa una reducción en el tiempo promedio a medida que aumenta el paralelismo. Con 1 hilo, el tiempo promedio de ejecución fue de 503.664.128,47 ms, mientras que con 4 hilos el tiempo cae a 134.027.263,83 ms, mostrando un beneficio de la paralelización. Al incrementar los hilos a 8, el promedio disminuye aún más hasta 110.932.713,63 ms, aunque la mejora ya no es tan grande como en el salto de 1 a 4 hilos. A partir de 16 hilos el promedio llega a 86.207.664,00 ms, y con 20 hilos baja a 84.190.239,50 ms, indicando que la ganancia empieza a estabilizarse.

En cuanto a la desviación estándar, se aprecia que la variabilidad de los resultados también disminuye al usar más hilos. Con un solo hilo, la desviación estándar es alta 23.689.195,05 ms, lo que refleja no muy buena consistencia en la ejecución. Sin embargo, al aumentar el número de hilos, la dispersión de los datos se reduce: con 4 hilos baja a 1.126.476,52 ms, con 8 hilos a 5.254.963,10 ms, con 16 hilos a 2.167.974,06 ms, y con 20 hilos a 1.358.592,18 ms. Esto indica que el paralelismo no solo acelera la ejecución, sino que también aporta mayor estabilidad en los tiempos medidos.

En general, los resultados muestran que la paralelización mejora bastante la eficiencia, aunque la ganancia decrece a medida que se agregan más hilos, que puede ser por la sobrecarga de sincronización y a la saturación del hardware. 

<div align="center">
<img width="315" height="60" alt="image" src="https://github.com/user-attachments/assets/716c29e8-4e0b-4f73-921a-c7e2e7cea474" />
</div>
<div align="center">
  <img width="315" height="180" alt="image" src="https://github.com/user-attachments/assets/aca89488-d2dc-45b8-a874-2e0dcc0e5c8f" />
</div>

#### `Matriz de tamaño 7200`

Los resultados obtenidos muestran una mejora en el rendimiento al aumentar el número de hilos, aunque también se evidencian ciertos efectos de sincronización. Con 1 hilo, el tiempo promedio de ejecución fue de 1.685.044.463,60 ms. Al incrementar a 4 hilos, el promedio disminuye a 430.851.478,57 ms, lo que representa una aceleración de aproximadamente 3,9 veces más rápido. Posteriormente, con 8 hilos, el tiempo baja a 352.911.529,17 ns, lo que implica un speedup de aproximadamente 4,8x frente a la ejecución secuencial. Aunque hay una mejora respecto a 4 hilos, el incremento ya no es lineal, lo cual refleja el inicio de los efectos de la sobrecarga de sincronización y el reparto desigual de tareas entre los hilos. Con 16 hilos, el tiempo promedio cae a 296.445.421,97 ms, alcanzando un speedup de de 5,7x. Finalmente, al llegar a 20 hilos, se observa un aumento del tiempo a 303.519.335,93 ms, mostrando que el mucha concurrencia puede dar como resultado costos de coordinación y comunicación que superan los beneficios de dividir más el trabajo. En conclusión, el paralelismo funciona de manera muy eficiente hasta los 8 hilos y de forma moderada hasta los 16, pero con 20 hilos la sincronización y la sobrecarga por la gestión de múltiples hilos empiezan a reducir el rendimiento, lo que crea un límite de escalabilidad para este tamaño de matriz.

<div align="center">
<img width="315" height="60" alt="image" src="https://github.com/user-attachments/assets/e3e3324c-add1-41e1-9957-45335d8ce374" />
</div>
<div align="center">
  <img width="315" height="180" alt="image" src="https://github.com/user-attachments/assets/7ab690f0-f26c-4560-af66-3cb9886c0024" />
</div>

#### `Matriz de tamaño 9280`

El comportamiento mejora con el aumento de hilos hasta un punto óptimo y luego se degrada. Con 1 hilo, el tiempo promedio fue 4.329.650.200,97 ms  con una desviación estándar de 133.817.050,98 ms. Al usar 4 hilos, el promedio cae a 956.118.461,10 ms, con un speedup de 4,53×; con 8 hilos baja a 757.614.355,97 ms, su speedup de 5,71×, mostrando una ganancia adicional aunque ya menos marcada que el salto de 1 a 4. El mejor resultado aparece con 16 hilos, donde el promedio alcanza 699.935.259,90 ms y el speedup llega a 6,19×, lo que combina un buen promedio de tiempo y variabilidad, ya que posee una desviación estándar 29.762.232,63 ms. En cambio, al aumentar a 20 hilos el promedio aumenta hasta 878.622.959,47 ms, con speedup de 4,93×. En conjunto, se concluye que para este tamaño de matriz, 16 hilos ofrece el mejor equilibrio entre reducción de tiempo y estabilidad, mientras que agregar más hilos introduce más costos de coordinación que anulan la ventaja de paralelizar más.

<div align="center">
<img width="315" height="60" alt="image" src="https://github.com/user-attachments/assets/c43cb02d-840a-4990-bd7d-95fc98d298a7" />
</div>
<div align="center">
  <img width="315" height="180" alt="image" src="https://github.com/user-attachments/assets/70c642cb-d9c0-4161-a049-eeab1e5cc4be" />
</div>

---
#### Comparación del rendimiento de las matrices

Al analizar en conjunto los resultados obtenidos para los distintos tamaños de matrices, se observa que el beneficio de la paralelización depende del tamaño de la matriz y de la relación entre el costo de cómputo y la sobrecarga de sincronización.

En conclusión, los resultados finales muestran que la eficiencia del paralelismo escala mejor a medida que aumenta el tamaño de la matriz, ya que el trabajo es lo suficientemente grande como para justificar el uso de múltiples hilos. Sin embargo, en todos los casos analizados el número óptimo de hilos se ubica alrededor de 16, punto en el que se logra la mayor reducción de tiempo sin que la sobrecarga de sincronización anule los beneficios. Esto confirma que el rendimiento en entornos paralelos no depende únicamente de más hilos, sino de encontrar el balance adecuado entre carga de trabajo, recursos disponibles y costos de coordinación.

<div align="center">
  <img width="315" height="180" alt="image" src="https://github.com/user-attachments/assets/6f7e4a1f-f377-47b6-a666-49d8cf98ef86" />
</div>




