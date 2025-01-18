/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Defines the requirements for a tree node object that can change --
 * by adding or removing child nodes, or by changing the contents
 * of a user object stored in the node.
 *
 * @see DefaultMutableTreeNode
 *
 * @see javax.swing.JTree
 *
 *
 * @author Rob Davis
 * @author Scott Violet
 */
interface MutableTreeNode : TreeNode {
    /**
     * Adds `child` to the receiver at `index`.
     * `child` will be messaged with `setParent`.
     *
     * @param child node to be added
     * @param index index of the receiver
     */
    fun insert(child: MutableTreeNode?, index: Int)

    /**
     * Removes the child at `index` from the receiver.
     *
     * @param index index of child to be removed
     */
    fun remove(index: Int)

    /**
     * Removes `node` from the receiver. `setParent`
     * will be messaged on `node`.
     *
     * @param node node to be removed from the receiver
     */
    fun remove(node: MutableTreeNode?)

    /**
     * Resets the user object of the receiver to `object`.
     *
     * @param object object to be set as a receiver
     */
    fun setUserObject(`object`: Any?)

    /**
     * Removes the receiver from its parent.
     */
    fun removeFromParent()

    /**
     * Sets the parent of the receiver to `newParent`.
     *
     * @param newParent node to be set as parent of the receiver
     */
    fun setParent(newParent: MutableTreeNode?)
}