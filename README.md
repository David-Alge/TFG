TFG - Aplicación de Ecommerce en Android

Aplicación móvil de comercio electrónico desarrollada en Android como Proyecto de Fin de Grado (Grado Superior en Desarrollo de Aplicaciones Multiplataforma, U-TAD).

Descripción

La aplicación permite a los usuarios registrarse, explorar un catálogo de productos, filtrarlos por categoría, añadirlos a un carrito de compra y gestionar su perfil. Incluye además un panel de administrador para la gestión completa del catálogo de productos y de los usuarios registrados.

El proyecto está orientado principalmente a productos de tecnología, aunque el modelo de datos permite adaptarlo a cualquier tipo de producto (ropa, muebles, etc.).

Stack tecnológico
Lenguaje: Kotlin
IDE: Android Studio
Backend / Base de datos: Firebase
Firebase Authentication (registro e inicio de sesión de usuarios)
Cloud Firestore (base de datos de productos y usuarios)
Funcionalidades
Registro e inicio de sesión de usuarios con validación de errores (contraseñas incorrectas, emails inválidos, campos vacíos) mediante mensajes tipo toast.
Pantalla de inicio (Home): listado de productos obtenidos en tiempo real desde Firestore, con sistema de filtros por categoría (Components, Computers, Phones, Appliance, Complements) accesible desde un menú lateral (navigation drawer).
Vista de detalle de producto: imagen, nombre, precio, características y descripción del producto.
Carrito de compra: vinculado al usuario, con opción de añadir productos desde el listado o el detalle, eliminar productos mediante deslizamiento, ver el total y vaciar el carrito.
Perfil de usuario: edición de nombre de usuario, email, contraseña y dirección.
Panel de administrador: alta, baja y modificación de productos y de usuarios.
Navegación: barra de navegación inferior (bottom navigation) entre Home, Carrito y Perfil.
Decisiones técnicas destacadas
Se evaluó inicialmente el uso de una API externa (commercejs.com) para la gestión de datos, mostrando finalmente Firebase por su mayor flexibilidad, respaldo y posibilidades de aprendizaje a futuro más allá de aplicaciones de comercio electrónico.
Toda la lógica de conexión con la base de datos se implementó con métodos personalizados sobre el SDK de Firebase, sin depender de librerías adicionales.
Autor

David Alge Balaguer
