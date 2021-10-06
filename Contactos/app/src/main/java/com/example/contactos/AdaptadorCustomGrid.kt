package com.example.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

/**
 * Adaptador encargado de manejar la grilla de contactos
 *
 * Fijate que ahora, como extiendo del AdaptadorCustom, y como este se encarga de
 * agregar, actualizar y buscar los items del array, este adapter solamente se va a concentrar
 * en inflar lo vista. Código más límpio y modularizado
 */
class AdaptadorCustomGrid(var contexto:Context): AdaptadorCustom() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var viewHolder:ViewHolder? = null
        var vista:View? = p1
        if (vista==null){
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto_grid,null)
            viewHolder=ViewHolder(vista)
            vista.tag = viewHolder

        }else{
            viewHolder = vista.tag as? ViewHolder
        }
        val item = getItem(p0) as Contacto

        //Asignacion de valores a elementos graficos
        viewHolder?.nombre?.text = item.nombre + " " + item.apellidos
        viewHolder?.foto?.setImageResource(item.foto)
        return vista!!
    }

    private class ViewHolder(vista:View){
        var nombre: TextView? = null
        var foto: ImageView? = null

        init {
            nombre = vista.findViewById(R.id.tvNombre_grid)
            foto = vista.findViewById(R.id.ivFoto_grid)
        }
    }

}