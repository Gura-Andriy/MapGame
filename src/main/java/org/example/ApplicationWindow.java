package org.example;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ApplicationWindow extends JFrame {
    private int windowWidth;
    private int windowHeight;
    private MapPanel mapPanel;

    public ApplicationWindow(String title, int width, int height) {
        super(title);
        this.windowWidth = width;
        this.windowHeight = height;
        this.mapPanel = new MapPanel(); // Припустимо, що конструктор MapPanel не потребує параметрів
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(mapPanel, BorderLayout.CENTER);
        setSize(windowWidth, windowHeight);
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    private void initializeMapPanel() {
        mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);
    }

    public void setWindowSize(int width, int height) {
        setWindowWidth(width);
        setWindowHeight(height);
        setSize(width, height);
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public ApplicationWindow(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = getSize();
                mapPanel.setMapSize(newSize.width, newSize.height);
            }
        });
        initializeMapPanel();
    }
}
