package es.fplumara.dam2.rinconessecretos.navegation

object Routes {
    const val LISTA = "lista"
    const val CREAR = "crear"
    const val DETALLE ="detalle/{rinconId}"
    const val EDITAR ="edit"

    fun detalle(rinconId: Long) = "detalle/$rinconId"



}