package org.example;

import javax.swing.SwingUtilities;

public class Main {

    public static void createAndShowGUI() {
        int width = 800;
        int height = 600;
        // Встановлення назви вікна та розмірів, які тепер зберігатимуться в ApplicationWindow
        ApplicationWindow window = new ApplicationWindow("Interactive Map Generator", width, height);
        // Встановлення розмірів карти в MapPanel, якщо вони відрізняються від розмірів вікна
        window.getMapPanel().setMapSize(width, height);

        window.setWindowSize(width, height); // Метод setWindowSize тепер задає розміри вікна
        window.setLocationRelativeTo(null); // Центрування вікна
        window.setVisible(true); // Показати вікно
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}
