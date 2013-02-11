package com.jff.grapheditor;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class Perspective implements IPerspectiveFactory {
    
    /**
     * We replace the editor area with three application folders that can be used for placing views.
     * 
     * @param the layout
     */
    public void createInitialLayout( IPageLayout layout ) {        
    }

}
