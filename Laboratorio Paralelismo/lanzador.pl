#!/usr/bin/perl
#**************************************************************
#         		Pontificia Universidad Javeriana
#     Autor: Daniela Medina
#     Fecha: 15 AGosto 2025
#     Materia: Sistemas Distribuidos
#     Tema: Laboratorio de Paralelismo
#     Fichero: script automatización ejecución por lotes 
#****************************************************************/

$Path = `pwd`;
chomp($Path);

# Nombre del programa a ejecutar
$Nombre_Ejecutable = "mmClasicaOpenMP";
# Lista de tamaños de matrices a probar 
@Size_Matriz = ("240","880","1520","3040","4960","7200","9280","12800","14400","16080","18400","19920");
# Lista de cantidades de hilos 
@Num_Hilos = (1,2,4,8,16,20);
$Repeticiones = 30;

# Bucle que recorre cada tamaño de matriz
foreach $size (@Size_Matriz){
	# Dentro de cada tamaño de matriz, recorre los distintos números de hilos
	foreach $hilo (@Num_Hilos) {
		# Nombre de archivo donde se guardan los resultados
		$file = "$Path/$Nombre_Ejecutable-".$size."-Hilos-".$hilo.".dat";
		# Ejecuta el programa 30 veces
		for ($i=0; $i<$Repeticiones; $i++) {
			# Llama al programa ./mmClasicaOpenMP con argumentos: tamaño de matriz y número de hilos
			system("$Path/$Nombre_Ejecutable $size $hilo  >> $file");
			#printf("$Path/$Nombre_Ejecutable $size $hilo \n");
		}
		close($file);
	$p=$p+1;
	}
}
