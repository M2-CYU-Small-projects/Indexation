package fr.m2_cyu_indexation.ui.sub_panels.request;

import fr.m2_cyu_indexation.ui.GuiPreferences;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.AbstractSubPanel;
import fr.m2_cyu_indexation.ui.sub_panels.menu.MenuPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Aldric Vitali Silvestre
 */
public class ChoiceButtonsPanel extends AbstractSubPanel {

    public static final Dimension DIMENSIONS = RequestPanel.BOTTOM_DIMENSIONS;

    public static final Font BUTTON_FONT = GuiPreferences.BASE_FONT.deriveFont((float) (GuiPreferences.WIDTH / 80.0));

    private RequestPanel requestPanel;

    public ChoiceButtonsPanel(MainWindow context, RequestPanel requestPanel) {
        super(context);
        this.requestPanel = requestPanel;
        setLayout(new GridLayout(1, 0, DIMENSIONS.width / 4, DIMENSIONS.height / 4));

        add(createRequestButton());
        add(createMenuButton());
    }

    public JButton createMenuButton() {
        JButton button = new JButton("Cancel");
        button.setFont(BUTTON_FONT);
        button.addActionListener(event -> {
            context.switchPanel(new MenuPanel(context));
        });
        return button;
    }

    public JButton createRequestButton() {
        JButton button = new JButton("Request");
        button.setFont(BUTTON_FONT);
        button.addActionListener(event -> requestPanel.submit());
        return button;
    }
}
