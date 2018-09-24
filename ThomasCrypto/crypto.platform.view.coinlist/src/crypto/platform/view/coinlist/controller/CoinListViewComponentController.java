package crypto.platform.view.coinlist.controller;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import crypto.platform.ui.api.controller.ComponentController;

public class CoinListViewComponentController extends ComponentController {
  private final TreeViewer treeView;

  public CoinListViewComponentController(Composite parent, TreeViewer treeView) {
    super(parent);
    this.treeView = treeView;
  }

  public void setInput(Object input) {
    treeView.setInput(input);
  }
}
