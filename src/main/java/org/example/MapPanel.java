package org.example;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import javax.swing.JPanel;
import java.awt.*;
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

    public MapPanel() {
        setMapSize(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        generateVoronoi();
    }

    private void generateVoronoi() {
        List<Coordinate> points = new ArrayList<>();
        // Згенерувати випадкові точки для графа Вороного
        for (int i = 0; i < 100; i++) { // Припустимо, що у нас є 100 точок
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

        if (voronoiDiagram != null) {
            for (int i = 0; i < voronoiDiagram.getNumGeometries(); i++) {
                Geometry geometry = voronoiDiagram.getGeometryN(i);
                drawGeometry(g2d, geometry);
            }
        }
    }

    private void drawGeometry(Graphics2D g2d, Geometry geometry) {
        Coordinate[] coordinates = geometry.getCoordinates();
        Path2D path = new Path2D.Double();
        if (coordinates.length > 0) {
            path.moveTo(coordinates[0].x, coordinates[0].y);
            for (int i = 1; i < coordinates.length; i++) {
                path.lineTo(coordinates[i].x, coordinates[i].y);
            }
            path.closePath();
        }
        g2d.draw(path);
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
