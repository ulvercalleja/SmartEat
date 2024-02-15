<?php

include "conexion.php"
    // Obtener los datos enviados desde la aplicación Android
    $nombre = $_POST["nombre"];
    $pass = $_POST["pass"];
    $email = $_POST["email"];

    // Insertar los datos en la tabla SE_users
    $sql = "INSERT INTO SE_users (nombre, pass, email) VALUES ('$nombre', '$pass', '$email')";

    if ($conexion->query($sql) === TRUE) {
        echo "Registro exitoso";
    } else {
        echo "Error: " . $sql . "<br>" . $conexion->error;
    }

    // Cerrar la conexión
    $conexion->close();
?>