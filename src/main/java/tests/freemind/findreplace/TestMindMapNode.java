/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2006  Christian Foltin <christianfoltin@users.sourceforge.net>
 *See COPYING for Details
 *
 *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/*$Id: TestMindMapNode.java,v 1.1.2.17 2008/05/26 19:25:09 christianfoltin Exp $*/

package tests.freemind.findreplace;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import freemind.controller.filter.FilterInfo;
import freemind.extensions.NodeHook;
import freemind.extensions.PermanentNodeHook;
import freemind.main.XMLElement;
import freemind.modes.HistoryInformation;
import freemind.modes.MapFeedback;
import freemind.modes.MindIcon;
import freemind.modes.MindMap;
import freemind.modes.MindMapCloud;
import freemind.modes.MindMapEdge;
import freemind.modes.MindMapLinkRegistry;
import freemind.modes.MindMapNode;
import freemind.modes.ModeController;
import freemind.modes.attributes.Attribute;

public final class TestMindMapNode implements MindMapNode {
	private String text = "";
	private Vector<TestMindMapNode> children = new Vector<TestMindMapNode>();
	private TestMindMapNode mNewParent;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean hasFoldedParents() {
		if(isRoot())
			return false;
		if(getParentNode().isFolded()) {
			return true;
		}
		return getParentNode().hasFoldedParents();
	}

	public String getObjectId(ModeController controller) {
		return null;
	}

	public ListIterator childrenFolded() {
		return children.listIterator();
	}

	public ListIterator childrenUnfolded() {
		return children.listIterator();
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public FilterInfo getFilterInfo() {
		return null;
	}

	public int getChildPosition(MindMapNode childNode) {
		return children.indexOf(childNode);
	}

	public MindMapNode getPreferredChild() {
		return null;
	}

	public void setPreferredChild(MindMapNode node) {
	}

	public int getNodeLevel() {
		return 0;
	}

	public String getLink() {
		return null;
	}

	public String getShortText(ModeController controller) {
		return null;
	}

	public MindMapEdge getEdge() {
		return null;
	}

	public Color getColor() {
		return null;
	}

	public String getStyle() {
		return null;
	}

	public void setStyle(String style) {
	}

	public boolean hasStyle() {
		return false;
	}

	public MindMapNode getParentNode() {
		return mNewParent;
	}

	public boolean isBold() {
		return false;
	}

	public boolean isItalic() {
		return false;
	}

	public boolean isUnderlined() {
		return false;
	}

	public Font getFont() {
		return null;
	}

	public String getFontSize() {
		return null;
	}

	public String getFontFamilyName() {
		return null;
	}

	public String getPlainTextContent() {
		return null;
	}

	public TreePath getPath() {
		return null;
	}

	public boolean isDescendantOf(MindMapNode node) {
		return false;
	}

	public boolean isRoot() {
		return false;
	}

	public boolean isFolded() {
		return false;
	}

	public boolean isLeft() {
		return false;
	}

	public boolean isOnLeftSideOfRoot() {
		return false;
	}

	public void setLeft(boolean isLeft) {
	}

	public void setFolded(boolean folded) {
	}

	public void setFont(Font font) {
	}

	public void setShiftY(int y) {
	}

	public int getShiftY() {
		return 0;
	}

	public int calcShiftY() {
		return 0;
	}

	public void setVGap(int i) {
	}

	public int getVGap() {
		return 0;
	}

	public int calcVGap() {
		return 0;
	}

	public void setHGap(int i) {
	}

	public int getHGap() {
		return 0;
	}

	public void setLink(String link) {
	}

	public void setFontSize(int fontSize) {
	}

	public void setColor(Color color) {
	}

	public List<MindIcon> getIcons() {
		return null;
	}

	public void addIcon(MindIcon icon, int position) {
	}

	public int removeIcon(int position) {
		return 0;
	}

	public MindMapCloud getCloud() {
		return null;
	}

	public void setCloud(MindMapCloud cloud) {
	}

	public Color getBackgroundColor() {
		return null;
	}

	public void setBackgroundColor(Color color) {
	}

	public List<PermanentNodeHook> getHooks() {
		return null;
	}

	public Collection<PermanentNodeHook> getActivatedHooks() {
		return null;
	}

	public PermanentNodeHook addHook(PermanentNodeHook hook) {
		return null;
	}

	public void invokeHook(NodeHook hook) {
	}

	public void removeHook(PermanentNodeHook hook) {
	}

	public void setToolTip(String key, String tip) {
	}

	public SortedMap<String, String> getToolTip() {
		return null;
	}

	public void setAdditionalInfo(String info) {
	}

	public String getAdditionalInfo() {
		return null;
	}

	public MindMapNode shallowCopy() {
		return null;
	}

	public XMLElement save(Writer writer, MindMapLinkRegistry registry,
			boolean saveHidden, boolean saveChildren) throws IOException {
		return null;
	}

	public Map<String, ImageIcon> getStateIcons() {
		return null;
	}

	public void setStateIcon(String key, ImageIcon icon) {
	}

	public HistoryInformation getHistoryInformation() {
		return null;
	}

	public void setHistoryInformation(HistoryInformation historyInformation) {
	}

	public boolean isVisible() {
		return false;
	}

	public boolean hasExactlyOneVisibleChild() {
		return false;
	}

	public MapFeedback getMapFeedback() {
		return null;
	}

	public void addTreeModelListener(TreeModelListener l) {
	}

	public void removeTreeModelListener(TreeModelListener l) {
	}

	public void insert(MutableTreeNode child, int index) {
		children.insertElementAt((TestMindMapNode) child, index);
	}

	public void remove(int index) {
		children.remove(index);
	}

	public void remove(MutableTreeNode node) {
		children.remove(node);
	}

	public void setUserObject(Object object) {
	}

	public void removeFromParent() {
	}

	public void setParent(MutableTreeNode newParent) {
		mNewParent = (TestMindMapNode) newParent;
	}

	public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	public int getChildCount() {
		return children.size();
	}

	public TreeNode getParent() {
		return mNewParent;
	}

	public int getIndex(TreeNode node) {
		return 0;
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public boolean isLeaf() {
		return false;
	}

	public Enumeration children() {
		return children.elements();
	}

	public String getXmlText() {
		return null;
	}

	public void setXmlText(String structuredText) {
	}

	public String getXmlNoteText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setXmlNoteText(String structuredNoteText) {
		// TODO Auto-generated method stub

	}

	public List getChildren() {
		return children;
	}

	public String getNoteText() {
		return null;
	}

	public void setNoteText(String noteText) {
	}

	public Attribute getAttribute(int pPosition) {
		return null;
	}

	public List<String> getAttributeKeyList() {
		return null;
	}
	
	@Override
	public List<Attribute> getAttributes() {
		return null;
	}

	public int getAttributePosition(String key) {
		return 0;
	}

	public void setAttribute(int pPosition, Attribute pAttribute) {
	}

	public int getAttributeTableLength() {
		return 0;
	}

	public EventListenerList getListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNewChildLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	public void createAttributeTableModel() {
		// TODO Auto-generated method stub

	}

	public String getAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWriteable() {
		return true;
	}

	public boolean isDescendantOfOrEqual(MindMapNode pParentNode) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemind.modes.MindMapNode#removeAllHooks()
	 */
	public void removeAllHooks() {
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#sortedChildrenUnfolded()
	 */
	public ListIterator sortedChildrenUnfolded() {
		return null;
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#hasVisibleChilds()
	 */
	public boolean hasVisibleChilds() {
		return false;
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#addAttribute(freemind.modes.attributes.Attribute)
	 */
	@Override
	public int addAttribute(Attribute pAttribute) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#removeAttribute(int)
	 */
	@Override
	public void removeAttribute(int pPosition) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#insertAttribute(int, freemind.modes.attributes.Attribute)
	 */
	@Override
	public void insertAttribute(int pPosition, Attribute pAttribute) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see freemind.modes.MindMapNode#getMap()
	 */
	@Override
	public MindMap getMap() {
		return null;
	}

	@Override
	public boolean isStrikethrough() {
		return false;
	}

	@Override
	public String getBareStyle() {
		return null;
	}

}
