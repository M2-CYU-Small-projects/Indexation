package fr.m2_cyu_indexation.ui.sub_panels.request;

import fr.m2_cyu_indexation.engine.business.request.Request;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.AbstractSubPanel;
import fr.m2_cyu_indexation.ui.sub_panels.results.ResultPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Aldric Vitali Silvestre
 */
public abstract class AbstractRequestFormPanel extends AbstractSubPanel {

    public static final Dimension FORM_DIMENSIONS = RequestPanel.CENTER_DIMENSIONS;

    private final JDialog loading;

    public AbstractRequestFormPanel(MainWindow context) {
        super(context);
        JOptionPane pane = new JOptionPane();
        pane.setOptions(new Object[]{});
        loading = pane.createDialog(this, "Loading");
        loading.setModal(false);
        loading.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    public abstract void initLayout();

    public abstract void submitForm();

    public Dimension getFormDimensions() {
        return FORM_DIMENSIONS;
    }

    public void submitAndSwitch(Request request) {
        loading.setVisible(true);
        setEnabled(false);
        context.submitRequest(request);

        loading.setVisible(false);
        setEnabled(true);
        context.switchPanel(new ResultPanel(context));
    }
}
