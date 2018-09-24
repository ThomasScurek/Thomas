package crypto.platform.ui.api.controller;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;

public abstract class ViewerComponentController extends ComponentController {

	private final Viewer viewer;
	
	public ViewerComponentController(Composite parent, Viewer viewer) {
		super(parent);
		this.viewer = viewer;
	}

	public Viewer getViewer(){
		return this.viewer;
	}
	
	/**
	 * @return the selection of the viewer, or null if none
	 */
	public StructuredSelection getSelection(){
		if(viewer.getSelection() != null)
			return (StructuredSelection) viewer.getSelection();
		return null;
	}
	
	public abstract void setDragSupport(int operations, Transfer[] types, DragSourceListener dragListener);

	public abstract void setDropSupport(int operations, Transfer[] types, DropTargetListener dropListener);

	public void setInput(Object input){
		viewer.setInput(input);
	}
	
	@Override
	public void refresh(){
		viewer.refresh();
		super.refresh();
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener){
		viewer.addSelectionChangedListener(listener);
	}
}
