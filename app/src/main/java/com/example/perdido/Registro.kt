package com.example.perdido


data class MyUsers(val nombre: String,
                   val apellido : String,
                   val correo: String,
                   val contrasena: String,
                   val telefono: String)

class Registro {
    companion object{
        // Lista de datos
        val itemList = mutableListOf(
            MyUsers("Camilo","Pulido","juacampv@hotmail.com","password","3028546174"),
        )
        // Método para agregar más registros
        fun agregarRegistro(nombre: String, apellido: String,correo: String, contrasena: String,telefono: String) {
            itemList.add(MyUsers(nombre,apellido,correo,contrasena,telefono ))
        }

        // Método para verificar el inicio de sesión
        fun iniciarSesion(correo: String, contrasena: String): Boolean {
            return itemList.any { it.correo == correo && it.contrasena == contrasena }
        }
    }
}