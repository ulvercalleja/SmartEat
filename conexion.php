<?php
$hostname = "mysql-8001.dinaserver.com";
$database = "tesla";
$username = "sge";
$password = "InstaladorDAM2!";

// Crear conexión
$conexion = new mysqli($hostname, $database, $username, $password);

// Verificar la conexión
if ($conexion->connect_error) {
    die("Error de conexión: " . $conexion->connect_error);
}


?>