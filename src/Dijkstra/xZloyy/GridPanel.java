package Dijkstra.xZloyy;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridPanel extends JPanel {
    // Para el tamaño del gridpanel
    private int rows;
    private int cols;
    private int nodeSize;
    
    
    private List<Point> visitedNodes = new ArrayList<>();
    private List<Point> pathNodes = new ArrayList<>();
    private List<Point> wallNodes = new ArrayList<>();
    private Point startNode = null;
    private Point endNode = null;
    private List<Point> weightedNodes = new ArrayList<>();
    private List<Integer> nodeWeights = new ArrayList<>();

    public GridPanel(int rows, int cols, int nodeSize) {
        this.rows = rows;
        this.cols = cols;
        this.nodeSize = nodeSize;
        setPreferredSize(new Dimension(cols * nodeSize, rows * nodeSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                //Esto forma un cuadrado al tener el mismo tamaño en pixeles.
                int x = col * nodeSize;
                int y = row * nodeSize;
                g.setColor(Color.LIGHT_GRAY); // Color de los cuadros
                g.drawRect(x, y, nodeSize, nodeSize); //drawRect dibuja un rectangulo teniendo x,y y el tamaño 
                //Con Point columna,fila para graficas.
                Point point = new Point(col, row);

                if (wallNodes.contains(point)) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, nodeSize, nodeSize); //FillRect llena el rectangulo que se indique.
                } else if (visitedNodes.contains(point)) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, nodeSize, nodeSize);
                } else if (pathNodes.contains(point)) {
                    g.setColor(Color.DARK_GRAY);
                    
                    g.fillRect(x, y, nodeSize, nodeSize);
                } else if (startNode != null && startNode.equals(point)) {
                    g.setColor(Color.RED);
                    g.fillRect(x, y, nodeSize, nodeSize);
                } else if (endNode != null && endNode.equals(point)) {
                    g.setColor(Color.RED);
                    g.fillRect(x, y, nodeSize, nodeSize);
                }
            }
        }
    }

    public void markWall(int row, int col) {
        wallNodes.add(new Point(col, row));
        repaint();
    }

    public void markStart(int row, int col) {
        startNode = new Point(col, row);
        repaint();
    }

    public void markEnd(int row, int col) {
        endNode = new Point(col, row);
        repaint();
    }

    public void clearNode(int row, int col) {
        Point point = new Point(col, row);
        wallNodes.remove(point);
        visitedNodes.remove(point);
        pathNodes.remove(point);
        int idx = weightedNodes.indexOf(point);
        if (idx != -1) {
            weightedNodes.remove(idx);
            nodeWeights.remove(idx);
        }
        if (startNode != null && startNode.equals(point)) startNode = null;
        if (endNode != null && endNode.equals(point)) endNode = null;
        repaint();
    }

    public void markVisited(int row, int col) {
        visitedNodes.add(new Point(col, row));
        repaint();
    }

    public void markPath(int row, int col) {
        pathNodes.add(new Point(col, row));
        repaint();
    }

    public void setNodeWeight(int row, int col, int weight) {
        weightedNodes.add(new Point(col, row));
        nodeWeights.add(weight);
        repaint();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public List<Point> getVisitedNodes() {
        return visitedNodes;
    }

    public List<Point> getPathNodes() {
        return pathNodes;
    }

    public List<Point> getWallNodes() {
        return wallNodes;
    }

    public Point getStartNode() {
        return startNode;
    }

    public Point getEndNode() {
        return endNode;
    }

    public List<Point> getWeightedNodes() {
        return weightedNodes;
    }

    public List<Integer> getNodeWeights() {
        return nodeWeights;
    }
    public void clearAll() {
        wallNodes.clear();
        visitedNodes.clear();
        pathNodes.clear();
        nodeWeights.clear();
        startNode = null;
        endNode = null;
        repaint();
}

}