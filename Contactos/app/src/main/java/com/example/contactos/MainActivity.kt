package com.example.contactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar

/**
 * Fijate que ahora implemento la interfaz (AdaptadorCustom.OnDataSetChange) ↓
 */
class MainActivity : AppCompatActivity(), AdaptadorCustom.OnDataSetChange {

    var lista:ListView? = null
    var grid:GridView? = null
    var viewSwitcher:ViewSwitcher? = null

    companion object{
        var contactos:ArrayList<Contacto>? = null
        var adaptador:AdaptadorCustomLista? = null
        var adaptadorGrid:AdaptadorCustomGrid? = null

        fun agregarContacto(contacto: Contacto){
            adaptador?.addItem(contacto)
        }

        fun obtenerContacto(index:Int):Contacto{
            return adaptador?.getItem(index) as Contacto
        }

        fun eliminarContacto(index: Int){
            adaptador?.removeItem(index)
        }

        fun actualizarContacto(index:Int,nuevoContacto: Contacto){
            adaptador?.updateItem(index,nuevoContacto)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Marcos","Rivas","Contoso",25,70.0F,"Tamaulimpas 215","55 1789245","marcos@contoso.com",R.drawable.foto_01))
        contactos?.add(Contacto("Juan","Perez","Fravega",40,83.2F,"Lacarra 1224","66 5429245","Juan@Fravega.com",R.drawable.foto_02))
        contactos?.add(Contacto("Hector","Gonzalez","Garbarino",33,68.4F,"Lituania 2032","11 5468245","Hector@Garbarino.com",R.drawable.foto_03))
        contactos?.add(Contacto("Dario","Gomez","Samsung",22,70.5F,"Anchori 123","44 3289245","Dario@Samsung.com",R.drawable.foto_04))
        contactos?.add(Contacto("Nahuel","Rodriguez","LG",55,76.6F,"Peron 666","33 556789245","Nahuel@LG.com",R.drawable.foto_05))
        contactos?.add(Contacto("Sergio","Gil","CompuMundo",60,90.0F,"Malabia 1002","22 659","Sergio@CompuMundo.com",R.drawable.foto_06))

        lista = findViewById<ListView>(R.id.lista)
        grid = findViewById<GridView>(R.id.grid)
        adaptador = AdaptadorCustomLista(this)
        adaptadorGrid = AdaptadorCustomGrid(this)

        /**Asigno al MainActivity como "listener" de los cambios realizados en las listas
         Esto se puede hacer porque esta clase implementa AdaptadorCustom.OnDataSetChange**/
        adaptador?.setOnDataChangeListener(this)
        adaptadorGrid?.setOnDataChangeListener(this)

        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        grid?.adapter = adaptadorGrid

        lista?.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this,Detalle::class.java)
            intent.putExtra("ID",i.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as androidx.appcompat.widget.SearchView

        val itemSwitch = menu.findItem(R.id.switchView)
        itemSwitch.setActionView(R.layout.switch_item)
        val switchview = itemSwitch.actionView.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint="Buscar contacto..."

        searchView.setOnQueryTextFocusChangeListener { view, b ->
            //preparar datos
        }
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                //filtrar
                adaptador?.filtrar(p0!!)
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                //filtrar
                adaptador?.filtrar(p0!!)
                return true
            }
        })

        switchview.setOnCheckedChangeListener { compoundButton, b ->
            viewSwitcher?.showNext()
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.INuevo->{
                val intent = Intent(this,Nuevo::class.java)
                startActivity(intent)
                return true
            }
            else->{return super.onOptionsItemSelected(item) }
        }
    }


    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }

    /**
     * Escucho los cambios en el array de Contactos
     * y actualizo ambas listas para reflejar los cambios
     */
    override fun onDataChanged() {
        adaptadorGrid?.notifyDataSetChanged()
        adaptador?.notifyDataSetChanged()
    }

}