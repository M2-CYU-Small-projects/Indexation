package fr.m2_cyu_indexation.ui.sub_panels;

import fr.m2_cyu_indexation.ui.MainWindow;

import javax.swing.*;

/**
 * @author Aldric Vitali Silvestre
 */
public abstract class AbstractSubPanel extends JPanel {

    protected final MainWindow context;

    public AbstractSubPanel(MainWindow context) {
        this.context = context;
    }

    public MainWindow getContext() {
        return context;
    }

}
