package com.example.contactos

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * Clase base con métodos en común entre las dos listas
 * La logica compartida de la grilla y la lista las maneja
 *  este adaptador base. De esta manera esta todo en un solo lugar
 */
open abstract class AdaptadorCustom(): BaseAdapter() {

    protected var tempContactos: ArrayList<Contacto>?
    private var listener: OnDataSetChange? = null

    init {
        this.tempContactos = MainActivity.contactos
    }

    /** Métodos del BaseAdapter() **/

    override fun getCount(): Int {
        return this.tempContactos?.size ?: 0;
    }

    override fun getItem(p0: Int): Any {
        return this.tempContactos?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return p1!!;
    }

    /** Métodos del adaptador **/

    /**
     * Asigna una instancia de la interfaz
     * @param listener: Nueva instancia de la interfaz encargada de notificar los cambios
     */
    fun setOnDataChangeListener(listener: OnDataSetChange) {
        this.listener = listener;
    }

    fun addItem(item:Contacto){
        MainActivity.contactos?.add(item)
        this.reloadData()
    }

    fun removeItem(index:Int){
        MainActivity.contactos?.removeAt(index)
        this.reloadData()
    }

    fun updateItem(index:Int, newItem:Contacto){
        MainActivity.contactos?.set(index,newItem)
        this.reloadData()
    }

    /**
     * Vuelve a copiar el array de Contactos
     * y notifica a la interfaz que hubieron cambios
     */
    private fun reloadData() {
        // Copio el array de Contactos nuevamente
        this.tempContactos = ArrayList(MainActivity.contactos)

        // Le notifico a la interfaz que se hicieron cambios
        // Para que refresque desde afuera
        this.listener?.onDataChanged()
    }

    fun filtrar(str:String){
        this.tempContactos?.clear()

        // En caso de no buscar nada, vuelvo a cargar los datos
        if(str.isEmpty()){
            this.tempContactos = ArrayList(MainActivity.contactos)
            this.listener?.onDataChanged()
            return
        }

        var busqueda = str
        busqueda=busqueda.lowercase()

        for (item in MainActivity.contactos!!){
            val nombre = item.nombre.lowercase()
            if(nombre.contains(busqueda)){
                this.tempContactos?.add(item)
            }
        }

        // Le notifico a la interfaz que se hicieron cambios
        // Para que refresque desde afuera
        this.listener?.onDataChanged()
    }

    /**
     * Interfaz desarrollada para escuchar cambios de estado y manejarlos desde
     * un activity o fragment a gusto
     */
    interface OnDataSetChange {
        fun onDataChanged()
    }

}