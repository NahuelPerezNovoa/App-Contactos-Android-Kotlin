package com.example.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Adaptador encargado de manejar la lista de contactos
 *
 * Fijate que ahora, como extiendo del AdaptadorCustom, y como este se encarga de
 * agregar, actualizar y buscar los items del array, este adapter solamente se va a concentrar
 * en inflar lo vista. Código más límpio y modularizado
 */
class AdaptadorCustomLista(var contexto:Context): AdaptadorCustom() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var viewHolderList:ViewHolder? = null
        var vista:View? = p1


        if (vista == null) {
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto, null)
            viewHolderList = ViewHolder(vista!!)
            vista.tag = viewHolderList

        } else {
            viewHolderList = vista.tag as? ViewHolder
        }
        val item = getItem(p0) as Contacto

        //Asignacion de valores a elementos graficos
        viewHolderList?.nombre?.text = item.nombre + " " + item.apellidos
        viewHolderList?.empresa?.text = item.empresa
        viewHolderList?.foto?.setImageResource(item.foto)


        return vista!!
    }

    private class ViewHolder(vista:View){
        var nombre:TextView? = null
        var foto:ImageView? = null
        var empresa:TextView? = null

        init {
            nombre = vista.findViewById(R.id.tvNombre)
            empresa = vista.findViewById(R.id.tvEmpresa)
            foto = vista.findViewById(R.id.ivFoto_grid)
        }
    }


}