# CookFlow

[Enlace al repositorio GitHub](https://github.com/a24miguelod/CookFlow/tree/main)

La App es una pequeña lista de recetas, con una gestión básica
de "despensa" y "lista de la compra".

Se muestran una serie de recetas del repositorio, permitiendo
acceder al detalle. Desde el detalle se pueden ver los elementos
que ya tenemos en nuestra despensa y/o si están en la lista de
la compra. Desde aquí también se puede gestionar la despensa
y la lista

Las otras dos screens son funcionalmente parecidas: muestran
los ingredientes de la despensa, permitiendo eliminarlos. En
la lista de compra, al eliminar elementos pasan a la despensa.

En el modo "flow" (???) se
van mostrando sucesivamente los pasos con un timer que se agota
con el tiempo especificado en cada paso. La idea es poder seguir
la receta sin necesidad de manipular el móvil con las manos llenas
de harina. Sonaba mejor en mi cabeza

## Características

* UI con Jetpack Compose
* Navigation Component con tipos seguros y BottomNavigationBar
* Single Activity

## Librerias

* **Firebase**: Almacena dos colecciones "recetas" e "ingredientes"
* **Ktor**: Para hacer consultas http (usada en el repositorio de github)
* **Coil**: Para mostrar imagenes
* **Room**: Para la persistencia de la despensa/lista compra y cachear ingredientes en el repo de firebase
