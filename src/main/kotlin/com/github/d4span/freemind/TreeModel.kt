package com.github.d4span.freemind

interface TreeModel {
    val root: Any?

    // fun getChild(o: Any?, i: Int): Any?

    // fun getChildCount(o: Any?): Int

    fun isLeaf(o: Any?): Boolean

    fun valueForPathChanged(treePath: TreePath, o: Any?)

    fun getIndexOfChild(o: Any?, o1: Any?): Int

    fun addTreeModelListener(treeModelListener: TreeModelListener?)

    fun removeTreeModelListener(treeModelListener: TreeModelListener?)
}