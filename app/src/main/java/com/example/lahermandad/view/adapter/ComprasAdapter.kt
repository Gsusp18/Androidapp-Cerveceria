package com.example.lahermandad.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lahermandad.R
import com.example.lahermandad.model.compras
import com.squareup.picasso.Picasso

class ComprasAdapter(private val context: Context, var clickListener: OnCompraItemClickListener): RecyclerView.Adapter<ComprasAdapter.ViewHolder>(){
    private var beerlista = mutableListOf<compras>()

    fun setListData(data:MutableList<compras>){
        beerlista = data
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(ViewGroup: ViewGroup, i:Int): ViewHolder{
        val v = LayoutInflater.from(ViewGroup.context).inflate(R.layout.card_view_compras,ViewGroup, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun binWeb(cerveza: compras, action:OnCompraItemClickListener){
            itemView.findViewById<TextView>(R.id.title).text=cerveza.titulo
            itemView.findViewById<TextView>(R.id.precio).text=cerveza.precio
            //itemView.findViewById<TextView>(R.id.preciosubtotal).text=cerveza.subtotal
            //itemView.findViewById<TextView>(R.id.ivatotal).text=cerveza.iva
            //itemView.findViewById<TextView>(R.id.grado).text=cerveza.grado
            Picasso.with(context).load(cerveza.image).into(itemView.findViewById<ImageView>(R.id.image1))
            val btneliminar=itemView.findViewById<ImageButton>(R.id.eliminar)
            btneliminar.setOnClickListener{
                action.onItemclick(cerveza, adapterPosition)
            }
        }
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val cerveza=beerlista[i]
        viewHolder.binWeb(cerveza, clickListener)
    }

    override fun getItemCount(): Int {
        return if(beerlista.size > 0){
            beerlista.size
        }else{
            0
        }
    }
}
interface OnCompraItemClickListener{
    fun onItemclick(cerveza: compras, position: Int)
}