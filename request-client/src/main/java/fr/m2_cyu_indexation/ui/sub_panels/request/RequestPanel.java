package fr.m2_cyu_indexation.ui.sub_panels.request;

import fr.m2_cyu_indexation.ui.GuiPreferences;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.AbstractSubPanel;
import fr.m2_cyu_indexation.ui.components.TitlePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Aldric Vitali Silvestre
 */
public class RequestPanel extends AbstractSubPanel {

    public static final Dimension TITLE_DIMENSIONS = new Dimension(GuiPreferences.WIDTH, GuiPreferences.HEIGHT / 5);
    public static final Dimension CENTER_DIMENSIONS = new Dimension(GuiPreferences.WIDTH, 3 * GuiPreferences.HEIGHT / 5);
    public static final Dimension BOTTOM_DIMENSIONS = new Dimension(GuiPreferences.WIDTH, GuiPreferences.HEIGHT / 5);

    private AbstractRequestFormPanel formPanel;

    public RequestPanel(MainWindow context, String name, AbstractRequestFormPanel formPanel) {
        super(context);

        TitlePanel titlePanel = new TitlePanel(context, name);
        titlePanel.setPreferredSize(TITLE_DIMENSIONS);

        this.formPanel = formPanel;
        formPanel.initLayout();
        formPanel.setPreferredSize(CENTER_DIMENSIONS);

        ChoiceButtonsPanel choiceButtonsPanel = new ChoiceButtonsPanel(context, this);
        choiceButtonsPanel.setPreferredSize(BOTTOM_DIMENSIONS);

        setLayout(new BorderLayout());

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(choiceButtonsPanel, BorderLayout.SOUTH);
    }

    public void submit() {
        formPanel.submitForm();
    }
}
