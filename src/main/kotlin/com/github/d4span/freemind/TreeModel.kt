package com.github.d4span.freemind

interface TreeModel {
    val root: Any?

    fun getIndexOfChild(o: Any?, o1: Any?): Int
}