package com.helpet.books

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.w3c.dom.Text


//class BooksAdapter(private val items: MutableList<SampleData>): BaseAdapter() {
//
//    override fun getCount(): Int = items.size
//
//    override fun getItem(position: Int): SampleData = items[position]
//
//    override fun getItemId(position: Int): Long = position.toLong()
//
//    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
//        var convertView = view
//        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_vect_sub_dog_list, parent, false)
//
//        val item: SampleData = items[position]
//        convertView.booksSubTitle.text = item.booksSubTitle
//
//
//        return convertView
//    }
//}