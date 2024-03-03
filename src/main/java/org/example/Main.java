package org.example;

import javax.swing.SwingUtilities;

public class Main {

    public static void createAndShowGUI() {
        // Встановлення назви вікна та розмірів, які тепер зберігатимуться в ApplicationWindow
        ApplicationWindow window = new ApplicationWindow("Interactive Map Generator", 800, 600);
        // Встановлення розмірів карти в MapPanel, якщо вони відрізняються від розмірів вікна
        window.getMapPanel().setMapSize(800, 600);

        window.setWindowSize(800, 600); // Метод setWindowSize тепер задає розміри вікна
        window.setVisible(true); // Показати вікно
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}
