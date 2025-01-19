/*
 * Copyright (c) 1997, 2023, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.github.d4span.freemind

import java.beans.ConstructorProperties
import java.io.*
import java.util.*
import javax.swing.event.EventListenerList

/**
 * A simple tree data model that uses TreeNodes.
 * For further information and examples that use DefaultTreeModel,
 * see [How to Use Trees](https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html)
 * in *The Java Tutorial.*
 *
 *
 * **Warning:**
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans
 * has been added to the `java.beans` package.
 * Please see [java.beans.XMLEncoder].
 *
 * @author Rob Davis
 * @author Ray Ryan
 * @author Scott Violet
 */
// Same-version serialization only
open class DefaultTreeModel
/**
 * Creates a tree specifying whether any node can have children,
 * or whether only certain nodes can have children.
 *
 * @param root a TreeNode object that is the root of the tree
 * @param asksAllowsChildren a boolean, false if any node can
 * have children, true if each node is asked to see if
 * it can have children
 * @see .asksAllowsChildren
 */(
    /** Root of the tree.  */
    private var theRoot: TreeNode?,

    protected var asksAllowsChildren: Boolean
) : Serializable, TreeModel {
    /** Listeners.  */
    protected var listenerList: EventListenerList? = EventListenerList()

    override var root: TreeNode?
        get() = theRoot
        set(root) {
            val oldRoot = this.theRoot
            this.theRoot = root
            if (root == null && oldRoot != null || root != null && root !== oldRoot) {
                fireTreeStructureChanged(this, null)
            }
        }


    /**
     * Creates a tree in which any node can have children.
     *
     * @param root a TreeNode object that is the root of the tree
     * @see .DefaultTreeModel
     */
    @ConstructorProperties("root")
    constructor(root: TreeNode?) : this(root, false)

    /**
     * Returns the index of child in parent.
     * If either the parent or child is `null`, returns -1.
     * @param parent a note in the tree, obtained from this data source
     * @param child the node we are interested in
     * @return the index of the child in the parent, or -1
     * if either the parent or the child is `null`
     */
    override fun getIndexOfChild(parent: Any?, child: Any?): Int {
        if (parent == null || child == null) return -1
        return (parent as TreeNode).getIndex(child as TreeNode)
    }

    /**
     * Invoked this to insert newChild at location index in parents children.
     * This will then message nodesWereInserted to create the appropriate
     * event. This is the preferred way to add children as it will create
     * the appropriate event.
     *
     * @param newChild  child node to be inserted
     * @param parent    node to which children new node will be added
     * @param index     index of parent's children
     */
    fun insertNodeInto(
        newChild: MutableTreeNode?,
        parent: MutableTreeNode, index: Int
    ) {
        parent.insert(newChild, index)

        val newIndexs = IntArray(1)

        newIndexs[0] = index
        nodesWereInserted(parent, newIndexs)
    }

    /**
     * Message this to remove node from its parent. This will message
     * nodesWereRemoved to create the appropriate event. This is the
     * preferred way to remove a node as it handles the event creation
     * for you.
     *
     * @param node the node to be removed from its parent
     */
    fun removeNodeFromParent(node: MutableTreeNode) {
        val parent = node.parent as MutableTreeNode

        requireNotNull(parent) { "node does not have a parent." }

        val childIndex = IntArray(1)
        val removedArray = arrayOfNulls<Any>(1)

        childIndex[0] = parent.getIndex(node)
        parent.remove(childIndex[0])
        removedArray[0] = node
        nodesWereRemoved(parent, childIndex, removedArray)
    }

    /**
     * Invoke this method after you've inserted some TreeNodes into
     * node.  childIndices should be the index of the new elements and
     * must be sorted in ascending order.
     *
     * @param node         parent node which children count been incremented
     * @param childIndices indexes of inserted children
     */
    fun nodesWereInserted(node: TreeNode?, childIndices: IntArray?) {
        if (listenerList != null && node != null && childIndices != null && childIndices.size > 0) {
            val cCount = childIndices.size
            val newChildren = arrayOfNulls<Any>(cCount)

            for (counter in 0 until cCount) newChildren[counter] = node.getChildAt(childIndices[counter])
            fireTreeNodesInserted(
                this, getPathToRoot(node), childIndices,
                newChildren
            )
        }
    }

    /**
     * Invoke this method after you've removed some TreeNodes from
     * node.  childIndices should be the index of the removed elements and
     * must be sorted in ascending order. And removedChildren should be
     * the array of the children objects that were removed.
     *
     * @param node             parent node from which children were removed
     * @param childIndices     indexes of removed children
     * @param removedChildren  array of the children objects that were removed
     */
    fun nodesWereRemoved(
        node: TreeNode?, childIndices: IntArray?,
        removedChildren: Array<Any?>
    ) {
        if (node != null && childIndices != null) {
            fireTreeNodesRemoved(
                this, getPathToRoot(node), childIndices,
                removedChildren
            )
        }
    }

    /**
     * Invoke this method if you've totally changed the children of
     * node and its children's children...  This will post a
     * treeStructureChanged event.
     *
     * @param node changed node
     */
    fun nodeStructureChanged(node: TreeNode?) {
        if (node != null) {
            fireTreeStructureChanged(this, getPathToRoot(node), null, emptyArray())
        }
    }

    /**
     * Builds the parents of node up to and including the root node,
     * where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the
     * tree.
     *
     * @param aNode the TreeNode to get the path for
     * @return an array of TreeNodes giving the path from the root
     */
    fun getPathToRoot(aNode: TreeNode?): Array<TreeNode?>? {
        return getPathToRoot(aNode, 0)
    }

    /**
     * Builds the parents of node up to and including the root node,
     * where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the
     * tree.
     *
     * @param aNode  the TreeNode to get the path for
     * @param depth  an int giving the number of steps already taken towards
     * the root (on recursive calls), used to size the returned array
     * @return an array of TreeNodes giving the path from the root to the
     * specified node
     */
    protected fun getPathToRoot(aNode: TreeNode?, depth: Int): Array<TreeNode?>? {
        var depth = depth
        val retNodes: Array<TreeNode?>?

        // This method recurses, traversing towards the root in order
        // size the array. On the way back, it fills in the nodes,
        // starting from the root and working back to the original node.

        /* Check for null, in case someone passed in a null node, or
           they passed in an element that isn't rooted at root. */
        if (aNode == null) {
            if (depth == 0) return null
            else retNodes = arrayOfNulls<TreeNode>(depth)
        } else {
            depth++
            if (aNode === root) retNodes = arrayOfNulls<TreeNode>(depth)
            else retNodes = getPathToRoot(aNode.parent, depth)
            retNodes!![retNodes.size - depth] = aNode
        }
        return retNodes
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * @param source the source of the `TreeModelEvent`;
     * typically `this`
     * @param path the path to the parent of the nodes that changed; use
     * `null` to identify the root has changed
     * @param childIndices the indices of the changed elements
     * @param children the changed elements
     */
    protected fun fireTreeNodesChanged(
        source: Any, path: Array<TreeNode?>?,
        childIndices: IntArray?,
        children: Array<Any?>
    ) {
        // Guaranteed to return a non-null array
        val listeners = listenerList!!.getListenerList()
        var e: TreeModelEvent? = null
        // Process the listeners last to first, notifying
        // those that are interested in this event
        var i = listeners.size - 2
        while (i >= 0) {
            if (listeners[i] === TreeModelListener::class.java) {
                // Lazily create the event:
                if (e == null) e = TreeModelEvent(
                    source, path,
                    childIndices, children
                )
                (listeners[i + 1] as TreeModelListener).treeNodesChanged(e)
            }
            i -= 2
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * @param source the source of the `TreeModelEvent`;
     * typically `this`
     * @param path the path to the parent the nodes were added to
     * @param childIndices the indices of the new elements
     * @param children the new elements
     */
    protected fun fireTreeNodesInserted(
        source: Any, path: Array<TreeNode?>?,
        childIndices: IntArray?,
        children: Array<Any?>
    ) {
        // Guaranteed to return a non-null array
        val listeners = listenerList!!.getListenerList()
        var e: TreeModelEvent? = null
        // Process the listeners last to first, notifying
        // those that are interested in this event
        var i = listeners.size - 2
        while (i >= 0) {
            if (listeners[i] === TreeModelListener::class.java) {
                // Lazily create the event:
                if (e == null) e = TreeModelEvent(
                    source, path,
                    childIndices, children
                )
                (listeners[i + 1] as TreeModelListener).treeNodesInserted(e)
            }
            i -= 2
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * @param source the source of the `TreeModelEvent`;
     * typically `this`
     * @param path the path to the parent the nodes were removed from
     * @param childIndices the indices of the removed elements
     * @param children the removed elements
     */
    protected fun fireTreeNodesRemoved(
        source: Any, path: Array<TreeNode?>?,
        childIndices: IntArray?,
        children: Array<Any?>
    ) {
        // Guaranteed to return a non-null array
        val listeners = listenerList!!.getListenerList()
        var e: TreeModelEvent? = null
        // Process the listeners last to first, notifying
        // those that are interested in this event
        var i = listeners.size - 2
        while (i >= 0) {
            if (listeners[i] === TreeModelListener::class.java) {
                // Lazily create the event:
                if (e == null) e = TreeModelEvent(
                    source, path,
                    childIndices, children
                )
                (listeners[i + 1] as TreeModelListener).treeNodesRemoved(e)
            }
            i -= 2
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * @param source the source of the `TreeModelEvent`;
     * typically `this`
     * @param path the path to the parent of the structure that has changed;
     * use `null` to identify the root has changed
     * @param childIndices the indices of the affected elements
     * @param children the affected elements
     */
    protected fun fireTreeStructureChanged(
        source: Any, path: Array<TreeNode?>?,
        childIndices: IntArray?,
        children: Array<Any?>
    ) {
        // Guaranteed to return a non-null array
        val listeners = listenerList!!.getListenerList()
        var e: TreeModelEvent? = null
        // Process the listeners last to first, notifying
        // those that are interested in this event
        var i = listeners.size - 2
        while (i >= 0) {
            if (listeners[i] === TreeModelListener::class.java) {
                // Lazily create the event:
                if (e == null) e = TreeModelEvent(
                    source, path,
                    childIndices, children
                )
                (listeners[i + 1] as TreeModelListener).treeStructureChanged(e)
            }
            i -= 2
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * @param source the source of the `TreeModelEvent`;
     * typically `this`
     * @param path the path to the parent of the structure that has changed;
     * use `null` to identify the root has changed
     */
    private fun fireTreeStructureChanged(source: Any, path: TreePath?) {
        // Guaranteed to return a non-null array
        val listeners = listenerList!!.getListenerList()
        var e: TreeModelEvent? = null
        // Process the listeners last to first, notifying
        // those that are interested in this event
        var i = listeners.size - 2
        while (i >= 0) {
            if (listeners[i] === TreeModelListener::class.java) {
                // Lazily create the event:
                if (e == null) e = TreeModelEvent(source, path)
                (listeners[i + 1] as TreeModelListener).treeStructureChanged(e)
            }
            i -= 2
        }
    }
}

