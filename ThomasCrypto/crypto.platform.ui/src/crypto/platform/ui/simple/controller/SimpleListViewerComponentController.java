package crypto.platform.ui.simple.controller;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import crypto.platform.ui.api.controller.ViewerComponentController;

public class SimpleListViewerComponentController extends ViewerComponentController {

	public SimpleListViewerComponentController(Composite parent, Viewer viewer) {
		super(parent, viewer);
	}

	@Override
	public ListViewer getViewer(){
		return (ListViewer)super.getViewer();
	}
	
	@Override
	public void setDragSupport(int operations, Transfer[] types,
			DragSourceListener dragListener) {
		this.getViewer().addDragSupport(operations, types, dragListener);
	}

	@Override
	public void setDropSupport(int operations, Transfer[] types,
			DropTargetListener dropListener) {
		this.getViewer().addDropSupport(operations, types, dropListener);
		
	}

}
