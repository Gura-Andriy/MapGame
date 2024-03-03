package org.example;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapPanel extends JPanel {
    private int mapWidth;
    private int mapHeight;
    private Geometry voronoiDiagram;
    private GeometryFactory geometryFactory = new GeometryFactory();
    private Random random = new Random();
    private int numberOfPoints = 4000;
    private Geometry selectedCell;

    public MapPanel() {
        setMapSize(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        generateVoronoi();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectCell(e.getPoint());
                repaint();
            }
        });
    }

    private void selectCell(Point point) {
        for (int i = 0; i < voronoiDiagram.getNumGeometries(); i++) {
            Geometry cell = voronoiDiagram.getGeometryN(i);
            if (cell.contains(geometryFactory.createPoint(new Coordinate(point.x, point.y)))) {
                selectedCell = cell;
                // Можна додати логіку, щоб зберегти індекс вибраної комірки, якщо це потрібно
                return;
            }
        }
        selectedCell = null; // Якщо нічого не вибрано, скинути виділення
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }



    private void generateVoronoi() {
        List<Coordinate> points = new ArrayList<>();
        // Згенерувати випадкові точки для графа Вороного
        for (int i = 0; i < getNumberOfPoints(); i++) { // i - кількість точок
            double x = random.nextDouble() * getMapWidth();
            double y = random.nextDouble() * getMapHeight();
            points.add(new Coordinate(x, y));
        }

        // Створити граф Вороного з цих точок
        VoronoiDiagramBuilder builder = new VoronoiDiagramBuilder();
        builder.setSites(points);
        voronoiDiagram = builder.getDiagram(geometryFactory);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Спочатку малюємо всі графи чорним кольором
        for (int i = 0; i < voronoiDiagram.getNumGeometries(); i++) {
            Geometry geometry = voronoiDiagram.getGeometryN(i);
            g2d.setColor(Color.BLACK);
            drawGeometry(g2d, geometry, false);
        }

        // Тепер малюємо виділений граф червоним кольором
        if (selectedCell != null) {
            g2d.setColor(Color.RED);
            drawGeometry(g2d, selectedCell, true);
        }
    }

    private void drawGeometry(Graphics2D g2d, Geometry geometry, boolean isSelected) {
        Coordinate[] coordinates = geometry.getCoordinates();
        Path2D path = new Path2D.Double();
        if (coordinates.length > 0) {
            path.moveTo(coordinates[0].x, coordinates[0].y);
            for (int i = 1; i < coordinates.length; i++) {
                path.lineTo(coordinates[i].x, coordinates[i].y);
            }
            path.closePath();
        }

        // Якщо граф виділений, спершу заливаємо його, потім малюємо контур
        if (isSelected) {
            g2d.setColor(new Color(255, 0, 0, 128)); // Напівпрозора заливка для виділеного графа
            g2d.fill(path);
            g2d.setColor(Color.RED); // Повністю червоний контур
            g2d.draw(path);
        } else {
            g2d.draw(path); // Якщо не виділений, просто малюємо контур
        }
    }

    public void setMapSize(int width, int height) {
        this.mapWidth = width;
        this.mapHeight = height;
        setPreferredSize(new Dimension(width, height));
        revalidate(); // Повідомляємо контейнер про зміну розміру для перекладання компонентів
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}
