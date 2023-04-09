package fr.m2_cyu_indexation.ui;

import java.awt.*;

/**
 * @author Aldric Vitali Silvestre
 */
public class GuiPreferences {
    public static final int RATIO_WIDTH = 16;
    public static final int RATIO_HEIGHT = 9;

    public static final int HEIGHT = 1060;

    public static final int WIDTH = HEIGHT * RATIO_WIDTH / RATIO_HEIGHT;

    public static final int BASIC = WIDTH / 1280;

    public static final int SCALE = BASIC * 20;

    public static final int FONT_SIZE = WIDTH / 80;

    public static final Font BASE_FONT = new Font("Arial", Font.PLAIN, FONT_SIZE);

    public static final Font ITALIC_FONT = new Font("Arial", Font.ITALIC, FONT_SIZE);
}
