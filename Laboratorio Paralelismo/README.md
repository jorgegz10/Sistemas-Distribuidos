# Laboratorio de Paralelismo

El programa hace uso del algoritmo **mmClasicaOpenMP**, el cual es una implementación en **C** de la multiplicación clásica de matrices cuadradas, optimizada con **OpenMP** para ejecutarse en paralelo y reducir el tiempo de cómputo.

---

### 1. Documentación del Código

#### 1.1 `mmClasicaOpenMP.c`
Este programa implementa la multiplicación de matrices cuadradas haciendo uso de **paralelismo con OpenMP**.  
El usuario indica el **tamaño de la matriz** y el **número de hilos** a utilizar.  

- Las matrices **A** y **B** se inicializan con valores aleatorios.  
- El resultado de la multiplicación se almacena en la matriz **C**.  
- Para medir el rendimiento, el programa toma el **tiempo de ejecución en microsegundos** antes y después de realizar la multiplicación.  
- Si la matriz posee una dimensión menor a 9, el programa la imprime en pantalla para facilitar la validación del cálculo.  

---

#### 1.2 `lanzador.pl`
Este script automatiza la ejecución por conjuntos de **mmClasicaOpenMP**.  

- Su propósito es **probar diferentes tamaños de matrices y número de hilos** para evaluar el rendimiento del programa.  
- Para cada combinación, el script ejecuta el programa **30 veces** y guarda los resultados en un archivo `.dat`.  
- Los archivos se nombran siguiendo un formato específico.

De esta manera, se genera un conjunto de resultados que reflejan el comportamiento del algoritmo bajo diferentes cargas computacionales.  

---

#### 1.3 `Makefile`
El **Makefile** automatiza la compilación de **mmClasicaOpenMP**.  
Facilita la compilación repetitiva del proyecto con un solo comando, evitando tener que escribir manualmente la línea de compilación.  

---

### 2. Diseño del Experimento

- Se seleccionaron **cinco cantidades de hilos**: `1, 4, 8, 16 y 20`.  
- Se definieron **siete tamaños de matrices diferentes**: `240, 880, 1520, 3040, 4960, 7200, 9280`.
- Cada tamaño fue elegido como múltiplo de **80**, lo cual facilita la distribución de hilos y mejora la estructura interna de los cálculos.  


**Nota:**  
En las condiciones iniciales del taller se establecía el uso de **12** matrices de hasta un tamaño de **20.000**, pero este se redujo posteriormente a **14.000**.  
Sin embargo, la ejecución del programa se realizó con el tamaño original de **20.000**.  

Algunas matrices no se procesaron debido al tiempo prolongado requerido para su ejecución, por ello, se obtuvo siete tamaños diferentes en lugar de doce. 

---

### 3. Análisis Estadístico

<img width="425" height="215" alt="image" src="https://github.com/user-attachments/assets/f823d48a-4969-4788-8ceb-63820939f1cd" />

