/*
 * Copyright (c) 1997, 2017, Oracle and/or its affiliates. All rights reserved.
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
import java.io.Serializable

/**
 * `TreePath` represents an array of objects that uniquely
 * identify the path to a node in a tree. The elements of the array
 * are ordered with the root as the first element of the array. For
 * example, a file on the file system is uniquely identified based on
 * the array of parent directories and the name of the file. The path
 * `/tmp/foo/bar` could be represented by a `TreePath` as
 * `new TreePath(new Object[] {"tmp", "foo", "bar"})`.
 *
 *
 * `TreePath` is used extensively by `JTree` and related classes.
 * For example, `JTree` represents the selection as an array of
 * `TreePath`s. When used with `JTree`, the elements of the
 * path are the objects returned from the `TreeModel`. When `JTree`
 * is paired with `DefaultTreeModel`, the elements of the
 * path are `TreeNode`s. The following example illustrates extracting
 * the user object from the selection of a `JTree`:
 * <pre>
 * DefaultMutableTreeNode root = ...;
 * DefaultTreeModel model = new DefaultTreeModel(root);
 * JTree tree = new JTree(model);
 * ...
 * TreePath selectedPath = tree.getSelectionPath();
 * DefaultMutableTreeNode selectedNode =
 * ((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
 * Object myObject= selectedNode.getUserObject();
</pre> *
 * Subclasses typically need override only `getLastPathComponent`, and `getParentPath`. As `JTree`
 * internally creates `TreePath`s at various points, it's
 * generally not useful to subclass `TreePath` and use with
 * `JTree`.
 *
 *
 * While `TreePath` is serializable, a `NotSerializableException` is thrown if any elements of the path are
 * not serializable.
 *
 *
 * For further information and examples of using tree paths,
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
 * @author Scott Violet
 * @author Philip Milne
 */
// Same-version serialization only
class TreePath : Serializable {
    /** Path representing the parent, null if lastPathComponent represents
     * the root.  */
    private var parentPath: TreePath? = null

    /** Last path component.  */
    private var lastPathComponent: Any? = null

    /**
     * Creates a `TreePath` from an array. The array uniquely
     * identifies the path to a node.
     *
     * @param path an array of objects representing the path to a node
     * @throws IllegalArgumentException if `path` is `null`,
     * empty, or contains a `null` value
     */
    @ConstructorProperties("path")
    constructor(path: Array<Any>) {
        require(!(path == null || path.size == 0)) { "path in TreePath must be non null and not empty." }
        lastPathComponent = path[path.size - 1]
        requireNotNull(lastPathComponent) { "Last path component must be non-null" }
        if (path.size > 1) parentPath = TreePath(path, path.size - 1)
    }

    /**
     * Creates a `TreePath` containing a single element. This is
     * used to construct a `TreePath` identifying the root.
     *
     * @param lastPathComponent the root
     * @see .TreePath
     * @throws IllegalArgumentException if `lastPathComponent` is
     * `null`
     */
    constructor(lastPathComponent: Any) {
        requireNotNull(lastPathComponent) { "path in TreePath must be non null." }
        this.lastPathComponent = lastPathComponent
        parentPath = null
    }

    /**
     * Creates a `TreePath` with the specified parent and element.
     *
     * @param parent the path to the parent, or `null` to indicate
     * the root
     * @param lastPathComponent the last path element
     * @throws IllegalArgumentException if `lastPathComponent` is
     * `null`
     */
    protected constructor(parent: TreePath?, lastPathComponent: Any) {
        requireNotNull(lastPathComponent) { "path in TreePath must be non null." }
        parentPath = parent
        this.lastPathComponent = lastPathComponent
    }

    /**
     * Creates a `TreePath` from an array. The returned
     * `TreePath` represents the elements of the array from
     * `0` to `length - 1`.
     *
     *
     * This constructor is used internally, and generally not useful outside
     * of subclasses.
     *
     * @param path the array to create the `TreePath` from
     * @param length identifies the number of elements in `path` to
     * create the `TreePath` from
     * @throws NullPointerException if `path` is `null`
     * @throws ArrayIndexOutOfBoundsException if `length - 1` is
     * outside the range of the array
     * @throws IllegalArgumentException if any of the elements from
     * `0` to `length - 1` are `null`
     */
    protected constructor(path: Array<Any>, length: Int) {
        lastPathComponent = path[length - 1]
        requireNotNull(lastPathComponent) { "Path elements must be non-null" }
        if (length > 1) parentPath = TreePath(path, length - 1)
    }

    /**
     * Creates an empty `TreePath`.  This is provided for
     * subclasses that represent paths in a different
     * manner. Subclasses that use this constructor must override
     * `getLastPathComponent`, and `getParentPath`.
     */
    protected constructor()

    val path: Array<Any?>
        /**
         * Returns an ordered array of the elements of this `TreePath`.
         * The first element is the root.
         *
         * @return an array of the elements in this `TreePath`
         */
        get() {
            var i = this.pathCount
            val result = arrayOfNulls<Any>(i--)

            var path: TreePath? = this
            while (path != null) {
                result[i--] = path.getLastPathComponent()
                path = path.getParentPath()
            }
            return result
        }

    /**
     * Returns the last element of this path.
     *
     * @return the last element in the path
     */
    fun getLastPathComponent(): Any {
        return lastPathComponent!!
    }

    val pathCount: Int
        /**
         * Returns the number of elements in the path.
         *
         * @return the number of elements in the path
         */
        get() {
            var result = 0
            var path: TreePath? = this
            while (path != null) {
                result++
                path = path.getParentPath()
            }
            return result
        }

    /**
     * Returns the path element at the specified index.
     *
     * @param index the index of the element requested
     * @return the element at the specified index
     * @throws IllegalArgumentException if the index is outside the
     * range of this path
     */
    fun getPathComponent(index: Int): Any {
        val pathLength = this.pathCount

        require(!(index < 0 || index >= pathLength)) {
            "Index " + index +
                    " is out of the specified range"
        }

        var path = this

        for (i in pathLength - 1 downTo index + 1) {
            path = path.getParentPath()
        }
        return path.getLastPathComponent()
    }

    /**
     * Compares this `TreePath` to the specified object. This returns
     * `true` if `o` is a `TreePath` with the exact
     * same elements (as determined by using `equals` on each
     * element of the path).
     *
     * @param o the object to compare
     */
    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o is TreePath) {
            var oTreePath : TreePath = o

            if (this.pathCount != oTreePath.pathCount) return false
            var path: TreePath? = this
            while (path != null
            ) {
                if (path.getLastPathComponent() != oTreePath.lastPathComponent) {
                    return false
                }
                oTreePath = oTreePath.getParentPath()
                path = path.getParentPath()
            }
            return true
        }
        return false
    }

    /**
     * Returns the hash code of this `TreePath`. The hash code of a
     * `TreePath` is the hash code of the last element in the path.
     *
     * @return the hashCode for the object
     */
    override fun hashCode(): Int {
        return getLastPathComponent().hashCode()
    }

    /**
     * Returns true if `aTreePath` is a
     * descendant of this
     * `TreePath`. A `TreePath` `P1` is a descendant of a
     * `TreePath` `P2`
     * if `P1` contains all of the elements that make up
     * `P2's` path.
     * For example, if this object has the path `[a, b]`,
     * and `aTreePath` has the path `[a, b, c]`,
     * then `aTreePath` is a descendant of this object.
     * However, if `aTreePath` has the path `[a]`,
     * then it is not a descendant of this object.  By this definition
     * a `TreePath` is always considered a descendant of itself.
     * That is, `aTreePath.isDescendant(aTreePath)` returns
     * `true`.
     *
     * @param aTreePath the `TreePath` to check
     * @return true if `aTreePath` is a descendant of this path
     */
    fun isDescendant(aTreePath: TreePath?): Boolean {
        var aTreePath = aTreePath
        if (aTreePath === this) return true

        if (aTreePath != null) {
            val pathLength = this.pathCount
            var oPathLength = aTreePath.pathCount

            if (oPathLength < pathLength)  // Can't be a descendant, has fewer components in the path.
                return false
            while (oPathLength-- > pathLength) aTreePath = aTreePath!!.getParentPath()
            return equals(aTreePath)
        }
        return false
    }

    /**
     * Returns a new path containing all the elements of this path
     * plus `child`. `child` is the last element
     * of the newly created `TreePath`.
     *
     * @param   child   the path element to add
     * @throws          NullPointerException if `child` is `null`
     * @return          a new path containing all the elements of this path
     * plus `child`
     */
    fun pathByAddingChild(child: Any): TreePath {
        if (child == null) throw NullPointerException("Null child not allowed")

        return TreePath(this, child)
    }

    /**
     * Returns the `TreePath` of the parent. A return value of
     * `null` indicates this is the root node.
     *
     * @return the parent path
     */
    fun getParentPath(): TreePath {
        return parentPath!!
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * @return a String representation of this object
     */
    override fun toString(): String {
        val tempSpot = StringBuilder("[")

        var counter = 0
        val maxCounter = this.pathCount
        while (counter < maxCounter
        ) {
            if (counter > 0) tempSpot.append(", ")
            tempSpot.append(getPathComponent(counter))
            counter++
        }
        tempSpot.append("]")
        return tempSpot.toString()
    }
}