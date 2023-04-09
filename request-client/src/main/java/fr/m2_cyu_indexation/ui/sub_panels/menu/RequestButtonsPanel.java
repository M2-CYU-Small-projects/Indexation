package fr.m2_cyu_indexation.ui.sub_panels.menu;

import fr.m2_cyu_indexation.ui.GuiPreferences;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.AbstractSubPanel;
import fr.m2_cyu_indexation.ui.sub_panels.request.RequestPanel;
import fr.m2_cyu_indexation.ui.sub_panels.request.impls.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

/**
 * @author Aldric Vitali Silvestre
 */
public class RequestButtonsPanel extends AbstractSubPanel {

    public static final Font BUTTON_FONT = GuiPreferences.BASE_FONT.deriveFont((float) (GuiPreferences.WIDTH / 80.0));

    public RequestButtonsPanel(MainWindow context) {
        super(context);
        init();
    }

    private void init() {
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createRequestButton("Find images with color dominance", this::createMostColorRequestPanel));
        add(createVerticalSpacing());
        add(createRequestButton("Find greyscale images", this::createGreyscaleRequestPanel));
        add(createVerticalSpacing());
        add(createRequestButton("Find similar images", this::createSimilarityRequestPanel));
        add(createVerticalSpacing());
        add(createRequestButton("Find textured images", this::createTexturedRequestPanel));
        add(createVerticalSpacing());
        add(createRequestButton("Find images with point of interest at the center", this::createCenteredInterestRequestPanel));
    }

    private Component createVerticalSpacing() {
        return Box.createVerticalGlue();
    }

    private JButton createRequestButton(String name, Supplier<RequestPanel> panelSupplier) {
        JButton button = new JButton(name);
        button.setFont(BUTTON_FONT);
        button.addActionListener(event -> {
            context.switchPanel(panelSupplier.get());
        });
        return button;
    }

    private RequestPanel createMostColorRequestPanel() {
        return new RequestPanel(
                context,
                "Most color request",
                new MostColorRequestPanel(context)
        );
    }

    private RequestPanel createGreyscaleRequestPanel() {
        return new RequestPanel(
                context,
                "Greyscale images",
                new GreyscaleRequestPanel(context)
        );
    }

    private RequestPanel createSimilarityRequestPanel() {
        return new RequestPanel(
                context,
                "Similar images",
                new SimilarityRequestPanel(context)
        );
    }

    private RequestPanel createTexturedRequestPanel() {
        return new RequestPanel(
                context,
                "Textured images",
                new TextureRequestPanel(context)
        );
    }

    private RequestPanel createCenteredInterestRequestPanel() {
        return new RequestPanel(
                context,
                "Point of interest at the center",
                new CenteredInterestRequestPanel(context)
        );
    }
}
