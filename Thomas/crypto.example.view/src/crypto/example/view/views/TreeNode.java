package crypto.example.view.views;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
  T data;
  TreeNode<?> parent;
  List<TreeNode<?>> children;

  public boolean isRoot() {
    return parent == null;
  }

  public boolean isLeaf() {
    return children.size() == 0;
  }

  private List<TreeNode<?>> elementsIndex;

  public TreeNode(T data) {
    this.data = data;
    this.children = new ArrayList<TreeNode<?>>();
    this.elementsIndex = new ArrayList<TreeNode<?>>();
    this.elementsIndex.add(this);
  }

  public <S> TreeNode<S> addChild(S child) {
    TreeNode<S> childNode = new TreeNode<S>(child);
    childNode.parent = this;
    this.children.add(childNode);
    this.registerChildForSearch(childNode);
    return childNode;
  }

  public int getLevel() {
    if (this.isRoot()) {
      return 0;
    } else {
      return parent.getLevel() + 1;
    }
  }

  private void registerChildForSearch(TreeNode<?> node) {
    elementsIndex.add(node);
    if (parent != null) {
      parent.registerChildForSearch(node);
    }
  }

  public TreeNode<?> findTreeNode(Comparable<Object> cmp) {
    for (TreeNode<?> element : this.elementsIndex) {
      Object elData = element.data;
      if (cmp.compareTo(elData) == 0) {
        return element;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return data != null ? data.toString() : "[data null]";
  }
}
