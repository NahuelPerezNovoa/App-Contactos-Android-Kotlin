package com.example.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class AdaptadorCustomGrid(var contexto:Context,items:ArrayList<Contacto>):BaseAdapter() {
    //Almacenar los elementos que se van a mostrar en el listview
    var items:ArrayList<Contacto>? = null
    var copiaItems:ArrayList<Contacto>? = null

    init {
        this.items= ArrayList(items)
        this.copiaItems=items
    }

    override fun getCount(): Int {
        return this.items?.count()!!
    }

    override fun getItem(p0: Int): Any {
        return this.items?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

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

    fun addItem(item:Contacto){
        copiaItems?.add(item)
        items=ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        copiaItems?.removeAt(index)
        items=ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun updateItem(index:Int, newItem:Contacto){
        copiaItems?.set(index,newItem)
        items=ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun filtrar(str:String){
        items?.clear()

        if(str.isEmpty()){
            items=ArrayList(copiaItems)
            notifyDataSetChanged()
            return
        }else{

        }
        var busqueda = str
        busqueda=busqueda.lowercase()

        for (item in copiaItems!!){
            val nombre = item.nombre.lowercase()
            if(nombre.contains(busqueda)){
                items?.add(item)
            }
        }

        notifyDataSetChanged()
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