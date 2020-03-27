package com.thinkcore.kotlin

import android.widget.SearchView

fun SearchView.onQueryChange(query: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(q: String): Boolean {
            query(q)
            return false
        }

        override fun onQueryTextSubmit(q: String): Boolean {
            return false
        }
    })
}

fun SearchView.onQuerySubmit(query: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(q: String): Boolean {
            return false
        }

        override fun onQueryTextSubmit(q: String): Boolean {
            query(q)
            return false
        }
    })
}

//fun SupportSearchView.onQueryChange(query: (String) -> Unit) {
//    setOnQueryTextListener(object : SupportSearchView.OnQueryTextListener {
//        override fun onQueryTextChange(q: String): Boolean {
//            query(q)
//            return false
//        }
//
//        override fun onQueryTextSubmit(q: String): Boolean {
//            return false
//        }
//    })
//}

//fun SupportSearchView.onQuerySubmit(query: (String) -> Unit) {
//    setOnQueryTextListener(object : SupportSearchView.OnQueryTextListener {
//        override fun onQueryTextChange(q: String): Boolean {
//            return false
//        }
//
//        override fun onQueryTextSubmit(q: String): Boolean {
//            query(q)
//            return false
//        }
//    })
//}