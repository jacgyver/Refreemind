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

import java.util.*

/**
 * Defines the requirements for an object that can be used as a
 * tree node in a JTree.
 *
 *
 * Implementations of `TreeNode` that override `equals`
 * will typically need to override `hashCode` as well.  Refer
 * to [javax.swing.tree.TreeModel] for more information.
 *
 * For further information and examples of using tree nodes,
 * see [How to Use Tree Nodes](https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html)
 * in *The Java Tutorial.*
 *
 * @author Rob Davis
 * @author Scott Violet
 */
interface TreeNode {
    /**
     * Returns the child `TreeNode` at index
     * `childIndex`.
     *
     * @param   childIndex  index of child
     * @return              the child node at given index
     */
    fun getChildAt(childIndex: Int): TreeNode?

    /**
     * Returns the number of children `TreeNode`s the receiver
     * contains.
     *
     * @return              the number of children the receiver contains
     */
    val childCount: Int

    /**
     * Returns the parent `TreeNode` of the receiver.
     *
     * @return              the parent of the receiver
     */
    val parent: TreeNode?

    /**
     * Returns the index of `node` in the receivers children.
     * If the receiver does not contain `node`, -1 will be
     * returned.
     *
     * @param   node        node to be looked for
     * @return              index of specified node
     */
    fun getIndex(node: TreeNode?): Int

    /**
     * Returns true if the receiver allows children.
     *
     * @return              whether the receiver allows children
     */
    val allowsChildren: Boolean

    /**
     * Returns true if the receiver is a leaf.
     *
     * @return              whether the receiver is a leaf
     */
    val isLeaf: Boolean

    /**
     * Returns the children of the receiver as an `Enumeration`.
     *
     * @return              the children of the receiver as an `Enumeration`
     */
    fun children(): Enumeration<out TreeNode?>?
}