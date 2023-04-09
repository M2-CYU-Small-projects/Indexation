package fr.m2_cyu_indexation;

import com.formdev.flatlaf.FlatLightLaf;
import fr.m2_cyu_indexation.config.Config;
import fr.m2_cyu_indexation.engine.Engine;
import fr.m2_cyu_indexation.engine.RequestEngine;
import fr.m2_cyu_indexation.engine.dao.ImageDao;
import fr.m2_cyu_indexation.engine.persistence.oracle.OracleConnectionHandler;
import fr.m2_cyu_indexation.engine.persistence.oracle.OracleImageDao;
import fr.m2_cyu_indexation.ui.MainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

/**
 * @author Aldric Vitali Silvestre
 */
public class RequestClientApplication {

    public static void main(String[] args) {

        Config config = new Config();
        CountDownLatch latch = new CountDownLatch(1);
        // Dependency injection part
        try (OracleConnectionHandler connectionHandler = OracleConnectionHandler.fromConfig(config.getOracleConfig())){
            ImageDao dao = new OracleImageDao(connectionHandler);

            Engine engine = new RequestEngine(dao);

            // This provides a more modern look and feel to the UI
            FlatLightLaf.setup();

            SwingUtilities.invokeLater(() -> {
                MainWindow mainWindow = new MainWindow(engine);
                mainWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        latch.countDown();
                    }
                });
            });

            latch.await();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}