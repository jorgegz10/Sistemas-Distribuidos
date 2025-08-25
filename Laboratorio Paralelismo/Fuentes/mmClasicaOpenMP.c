/*#######################################################################################
 #* Fecha: 15-Agosto-2025
 #* Autor: Daniela Medina
 #* Tema: Laboratorio de Paralelismo
 #* 	- Programa Multiplicación de Matrices algoritmo clásico
 #* 	- Paralelismo con OpenMP
######################################################################################*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <sys/time.h>
#include <omp.h>

// Variables globales para medir el tiempo de ejecucion 
struct timeval inicio, fin; 

// Funcion que marca el inicio de la meedicion del tiempo

void InicioMuestra(){
	// Captura el tiempo actual en inicio
	gettimeofday(&inicio, (void *)0);
}

// Funcion que marca el final de la medicion y calcula el tiempo en microsegundos y lo imprime

void FinMuestra(){
	// Captura el tiempo actual en fin
	gettimeofday(&fin, (void *)0);
	fin.tv_usec -= inicio.tv_usec;
	fin.tv_sec  -= inicio.tv_sec;
	// Convierte tiempo a microsegundos
	double tiempo = (double)(fin.tv_sec * 1000000 + fin.tv_usec); 
	printf("%9.0f \n", tiempo);
}

// Funcion que recibe un puntero a la matriz a imprimir y la dimension de la misma
// Imprime la matriz si su dimension es menor a 9

void impMatrix(double *matrix, int D){
	printf("\n");
	if(D < 9){
		//Recorre los elementos de la matriz
		for(int i = 0; i < D*D; i++){
			if(i % D == 0) printf("\n");
			printf("%.2f ", matrix[i]);
		}
		printf("\n**-----------------------------**\n");
	}
}

// Funcion que recibe un puntero a la 1era matriz, un puntero a la 2da matrz y la dimension de las mismas
// Inicializa las dos matrices con valores entre 0-99

void iniMatrix(double *m1, double *m2, int D){
	for(int i = 0; i < D*D; i++, m1++, m2++){
		// Asigna valores aleatorios a cada matriz
		*m1 = (double)(rand() % 100);	
		*m2 = (double)(rand() % 100);	
	}
}

// Funcion que recibe una matriz A, una matriz B y la dimension de las mismas
// Realiza la multiplicacion de matrices, empleando paralelismo con OpenMP

void multiMatrix(double *mA, double *mB, double *mC, int D){
	double suma, *pA, *pB;
	// Inicia una region paralela (donde se crean los hilos)
	#pragma omp parallel
	{
		// Divide las iteraciones del siguiente for, entre todos los hilos disponibles, cada hilo tiene variables privadas 
		#pragma omp for private(pA, pB, suma)
		// Itera filas de A y C
		for(int i = 0; i < D; i++){
			// Itera columnas de B
			for(int j = 0; j < D; j++){
				// Apunta al inicio de la fila i de A
				pA = mA + i*D;	
				// Apunta al inicio de la columna j de B
				pB = mB + j;	
				// Reinicia acumulador para C
				suma = 0.0;
				// Recorre elementos de fila i y columna j
				for(int k = 0; k < D; k++, pA++, pB += D){
					// Acumula producto A[i,k] * B[k,j]
					suma += (*pA) * (*pB);
				}
				// Escribe resultado en C
				mC[i*D + j] = suma;
			}
		}
	}
}

// Funcion principal
int main(int argc, char *argv[]){
	if(argc < 3){
		printf("\n Use: $./clasicaOpenMP SIZE Hilos \n\n");
		exit(0);
	}

	// Dimension de las matrices 
	int N = atoi(argv[1]);
	// Numero de hilos a usar
	int TH = atoi(argv[2]);

	// Reservar matrices con tipo double
	double *matrixA = (double *)calloc(N*N, sizeof(double));
	double *matrixB = (double *)calloc(N*N, sizeof(double));
	double *matrixC = (double *)calloc(N*N, sizeof(double));
	
	// Verifica asignacion correcta
	if(!matrixA || !matrixB || !matrixC){
		fprintf(stderr, "Error al asignar memoria.\n");
		exit(1);
	}

	//Inicializacion de entorno paralelo
	srand(time(NULL));
	// Configura cantidad de hilos que usara OpenMP
	omp_set_num_threads(TH);

	// Inicializar matrices A y B con números aleatorios de 0-99
	iniMatrix(matrixA, matrixB, N);

	// Mostrar matrices si tienen dimensiones <9
	impMatrix(matrixA, N);
	impMatrix(matrixB, N);

	// Inicio de medicion de tiempo
	InicioMuestra();
	// Ejecuta la multiplicación 
	multiMatrix(matrixA, matrixB, matrixC, N);
	// Fin de la medicion de tiempo
	FinMuestra();

	// Mostrar resultado si tiene dimension <9
	impMatrix(matrixC, N);

	// Liberación de Memoria
	free(matrixA);
	free(matrixB);
	free(matrixC);
	
	return 0;
}
