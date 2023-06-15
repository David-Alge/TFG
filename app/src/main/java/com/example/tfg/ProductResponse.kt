package com.example.tfg

data class ProductResponse(
    val Productos: List<Producto>
) {
    data class Producto(
        val Categoria: String,
        val Id: Int,
        val Img: String,
        val Nombre: String,
        val Precio: Int
    )
}