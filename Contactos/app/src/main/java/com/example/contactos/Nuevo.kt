package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class Nuevo : AppCompatActivity() {

    var fotoIndex:Int = 0
    val fotos = arrayOf(R.drawable.foto_01,R.drawable.foto_02,R.drawable.foto_03,R.drawable.foto_04,R.drawable.foto_05,R.drawable.foto_06)
    var foto:ImageView? = null
    var index:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById<ImageView>(R.id.ivFoto_Detalle)
        foto?.setOnClickListener{
            seleccionarFoto()
        }

        //reconocer accion de nuevo vs editar
        if(intent.hasExtra("ID")){
            index = intent.getStringExtra("ID")!!.toInt()
            rellenarDatos(index)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo,menu)
        if(intent.hasExtra("ID")){
            menu?.getItem(0)?.setIcon(ContextCompat.getDrawable(this,R.drawable.icono_listo))
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){

            android.R.id.home->{
                finish()
                return true
            }


            R.id.iCrearNuevo->{//crear un nuevo elemento de tipo contacto
                //val intent = Intent(this,Nuevo::class.java)
                //startActivity(intent)



                val nombre = findViewById<EditText>(R.id.tvNombre_Detalle)
                val apellidos = findViewById<EditText>(R.id.tvApellido_Detalle)
                val empresa = findViewById<EditText>(R.id.tvEmpresa_Detalle)
                val edad = findViewById<EditText>(R.id.tvEdad_Detalle)
                val peso = findViewById<EditText>(R.id.tvPeso_Detalle)
                val telefono = findViewById<EditText>(R.id.tvTelefono_Detalle)
                val email = findViewById<EditText>(R.id.tvEmail_Detalle)
                val direccion = findViewById<EditText>(R.id.tvDireccion_Detalle)

                //validar campos
                var campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellidos.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(direccion.text.toString())
                campos.add(telefono.text.toString())
                campos.add(email.text.toString())
                var flag = 0
                for (campo in campos){
                    if(campo.isNullOrEmpty())
                        flag++
                }
                if(flag>0){
                    Toast.makeText(this,"Rellena todos los campos",Toast.LENGTH_LONG).show()
                }else{
                    if(index>-1) {
                        MainActivity.actualizarContacto(
                            index,
                            Contacto(
                                campos.get(0),
                                campos.get(1),
                                campos.get(2),
                                campos.get(3).toInt(),
                                campos.get(4).toFloat(),
                                campos.get(5),
                                campos.get(6),
                                campos.get(7),
                                obternerFoto(fotoIndex)
                            )
                        )
                    }else {
                        MainActivity.agregarContacto(
                            Contacto(
                                campos.get(0),
                                campos.get(1),
                                campos.get(2),
                                campos.get(3).toInt(),
                                campos.get(4).toFloat(),
                                campos.get(5),
                                campos.get(6),
                                campos.get(7),
                                obternerFoto(fotoIndex)
                            )
                        )
                    }
                    finish()
                    Log.d("NUMERO ELEMENTOS", MainActivity.contactos?.count().toString())
                }


                return true
            }
            else->{ return super.onOptionsItemSelected(item) }
        }

    }

    fun seleccionarFoto(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona imagen de perfil")

        val adapatorDialogo = ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item)
        adapatorDialogo.add("Foto 01")
        adapatorDialogo.add("Foto 02")
        adapatorDialogo.add("Foto 03")
        adapatorDialogo.add("Foto 04")
        adapatorDialogo.add("Foto 05")
        adapatorDialogo.add("Foto 06")

        builder.setAdapter(adapatorDialogo){
            dialog,which ->

            fotoIndex=which
            foto?.setImageResource(obternerFoto(fotoIndex))
        }

        builder.setNegativeButton("Cancelar"){
            dialog, which->
            dialog.dismiss()
        }
        builder.show()
    }

    fun obternerFoto(index:Int):Int{
        return fotos.get(index)
    }

    fun rellenarDatos(index:Int){
        val contacto = MainActivity.obtenerContacto(index)

        val tvNombre = findViewById<EditText>(R.id.tvNombre_Detalle)
        val tvApellido = findViewById<EditText>(R.id.tvApellido_Detalle)
        val tvEmpresa = findViewById<EditText>(R.id.tvEmpresa_Detalle)
        val tvEdad = findViewById<EditText>(R.id.tvEdad_Detalle)
        val tvPeso = findViewById<EditText>(R.id.tvPeso_Detalle)
        val tvTelefono = findViewById<EditText>(R.id.tvTelefono_Detalle)
        val tvEmail = findViewById<EditText>(R.id.tvEmail_Detalle)
        val tvDireccion = findViewById<EditText>(R.id.tvDireccion_Detalle)
        val ivFoto = findViewById<ImageView>(R.id.ivFoto_Detalle)

        tvNombre.setText(contacto.nombre,TextView.BufferType.EDITABLE)
        tvApellido.setText(contacto.apellidos,TextView.BufferType.EDITABLE)
        tvEmpresa.setText(contacto.empresa,TextView.BufferType.EDITABLE)
        tvEdad.setText(contacto.edad.toString(),TextView.BufferType.EDITABLE)
        tvPeso.setText(contacto.peso.toString(),TextView.BufferType.EDITABLE)
        tvTelefono.setText(contacto.telefono,TextView.BufferType.EDITABLE)
        tvEmail.setText(contacto.email,TextView.BufferType.EDITABLE)
        tvDireccion.setText(contacto.direccion,TextView.BufferType.EDITABLE)
        ivFoto.setImageResource(contacto.foto)

        var posicion = 0
        for (foto in fotos){
            if(contacto.foto==foto){
                fotoIndex=posicion
            }
            posicion++
        }

    }
}