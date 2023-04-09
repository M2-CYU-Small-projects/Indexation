package fr.m2_cyu_indexation.ui.components;

import fr.m2_cyu_indexation.ui.GuiPreferences;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.AbstractSubPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Aldric Vitali Silvestre
 */
public class TitlePanel extends AbstractSubPanel {

    private JLabel titleLabel;

    public TitlePanel(MainWindow context, String label) {
        super(context);
        init(label);
    }

    private void init(String label) {
        setLayout(new GridLayout(1, 1, 16, 16));

        titleLabel = new JLabel(label, SwingConstants.CENTER);

        Font font = GuiPreferences.BASE_FONT.deriveFont(Font.BOLD, GuiPreferences.WIDTH / 60);
        titleLabel.setFont(font);

        add(titleLabel);
    }

}
