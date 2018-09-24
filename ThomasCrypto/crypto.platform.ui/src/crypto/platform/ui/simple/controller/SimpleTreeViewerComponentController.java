package crypto.platform.ui.simple.controller;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import crypto.platform.ui.api.controller.ViewerComponentController;

public class SimpleTreeViewerComponentController extends ViewerComponentController {

	public SimpleTreeViewerComponentController(Composite parent, Viewer viewer) {
		super(parent, viewer);
	}

	@Override
	public TreeViewer getViewer() {
		return (TreeViewer) super.getViewer();
	}

	@Override
	public void setDragSupport(int operations, Transfer[] types, DragSourceListener dragListener) {
		this.getViewer().addDragSupport(operations, types, dragListener);
	}

	@Override
	public void setDropSupport(int operations, Transfer[] types, DropTargetListener dropListener) {
		this.getViewer().addDropSupport(operations, types, dropListener);
	}
}
