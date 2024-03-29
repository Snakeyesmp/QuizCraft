package com.example.proyectofinaltrivial

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "PartidasDB"
        private const val TABLE_PARTIDAS = "partidas"

        private const val KEY_ID = "id"
        private const val KEY_NOMBRE_PARTIDA = "nombre_partida"
        private const val KEY_TURNO = "turno"
        private const val KEY_POS_JUGADOR1 = "jugador1"
        private const val KEY_POS_JUGADOR2 = "jugador2"
        private const val KEY_TIPOS_PREGUNTAS = "preguntas"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTablePartidas =
            ("CREATE TABLE $TABLE_PARTIDAS($KEY_ID INTEGER PRIMARY KEY, $KEY_NOMBRE_PARTIDA TEXT, $KEY_TURNO TEXT, $KEY_POS_JUGADOR1 INTEGER, $KEY_POS_JUGADOR2 INTEGER, $KEY_TIPOS_PREGUNTAS TEXT)")
        db?.execSQL(createTablePartidas)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PARTIDAS")
        onCreate(db)
    }

    /**
     * Método para añadir una nueva partida a la base de datos.
     *
     * @param nombrePartida Nombre de la partida.
     * @param datosPartida Lista de datos de la partida.
     */
    fun addPartida(nombrePartida: String, datosPartida: ArrayList<String>) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NOMBRE_PARTIDA, nombrePartida)
        values.put(KEY_POS_JUGADOR1, datosPartida[0])
        values.put(KEY_POS_JUGADOR2, datosPartida[1])
        values.put(KEY_TURNO, datosPartida[2])
        values.put(KEY_TIPOS_PREGUNTAS, datosPartida[3])
        db.insert(TABLE_PARTIDAS, null, values)
        db.close()
    }

    /**
     * Método para obtener todas las partidas almacenadas en la base de datos.
     *
     * @return Lista de objetos Partida.
     */
    fun getAllPartidas(): List<Partida> {
        // Lista para almacenar y retornar los discos.
        val partidasList = ArrayList<Partida>()
        // Consulta SQL para seleccionar todos los discos.
        val selectQuery = "SELECT  * FROM $TABLE_PARTIDAS"

        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            // Ejecuta la consulta SQL.
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // Maneja la excepción en caso de error al ejecutar la consulta.
            db.execSQL(selectQuery)
            return ArrayList()
        }

        // Variables para almacenar los valores de las columnas.
        var id: Int
        var nombre: String
        var posJugador1: Int
        var posJugador2: Int
        var turno: String
        var tiposPreguntas: String

        // Itera a través del cursor para leer los datos de la base de datos.
        if (cursor.moveToFirst()) {
            do {
                // Obtiene los índices de las columnas.
                val idIndex = cursor.getColumnIndex(KEY_ID)
                val nombreIndex = cursor.getColumnIndex(KEY_NOMBRE_PARTIDA)
                val posJugador1Index = cursor.getColumnIndex(KEY_POS_JUGADOR1)
                val posJugador2Index = cursor.getColumnIndex(KEY_POS_JUGADOR2)
                val turnoIndex = cursor.getColumnIndex(KEY_TURNO)
                val tiposPreguntasIndex = cursor.getColumnIndex(KEY_TIPOS_PREGUNTAS)

                // Verifica que los índices sean válidos.
                if (idIndex >= 0 && nombreIndex >= 0 && posJugador1Index >= 0 && posJugador2Index >= 0 && turnoIndex >= 0 && tiposPreguntasIndex >= 0) {
                    // Lee los valores y los añade a la lista de discos.
                    id = cursor.getInt(idIndex)
                    nombre = cursor.getString(nombreIndex)
                    posJugador1 = cursor.getInt(posJugador1Index)
                    posJugador2 = cursor.getInt(posJugador2Index)
                    turno = cursor.getString(turnoIndex)
                    tiposPreguntas = cursor.getString(tiposPreguntasIndex)

                    val partida =
                        Partida(id, nombre, posJugador1, posJugador2, turno, tiposPreguntas)
                    partidasList.add(partida)
                }
            } while (cursor.moveToNext())
        }

        // Cierra el cursor para liberar recursos.
        cursor.close()
        return partidasList
    }


    /**
     * Método para eliminar una partida de la base de datos.
     *
     * @param partidaSeleccionada Objeto Partida a ser eliminado.
     */
    fun deletePartida(partidaSeleccionada: Partida) {
        val db = this.writableDatabase
        db.delete(TABLE_PARTIDAS, "$KEY_ID=?", arrayOf(partidaSeleccionada.id.toString()))
        db.close()
    }


}

/**
 * Clase de datos que representa una partida.
 *
 * @property id Identificador único de la partida.
 * @property nombrePartida Nombre de la partida.
 * @property posJugador1 Posición del jugador 1.
 * @property posJugador2 Posición del jugador 2.
 * @property turno Turno actual.
 * @property tiposPreguntas Tipos de preguntas seleccionadas.
 */
data class Partida(
    val id: Int,
    val nombrePartida: String,
    val posJugador1: Int,
    val posJugador2: Int,
    val turno: String,
    val tiposPreguntas: String
)
