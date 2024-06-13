package Dijkstra.xZloyy;
import java.awt.Point;
import java.util.*;
import javax.swing.JOptionPane;
/**
 *
 * @author jesus
 */
public class Dijkstra {
    private GridPanel gridPanel;
    public Dijkstra(GridPanel gridPanel){
        this.gridPanel = gridPanel;
    }
    
    public void findShortestPath() {
        Point startNode = gridPanel.getStartNode();
        Point endNode = gridPanel.getEndNode();
        List<Point> weightedNodes = gridPanel.getWeightedNodes();
        List<Integer> nodeWeights = gridPanel.getNodeWeights();
        //Validacion para que tengamos los dos nodos
        if (startNode == null || endNode == null) {
            JOptionPane.showMessageDialog(null, "Marca el nodo de fin o inicio pq alguno de esos dos no haz puesto.","ERROR NODOS",JOptionPane.WARNING_MESSAGE);
            return;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        List<PointDistance> distances = new ArrayList<>();
        List<PointPrevious> previousNodes = new ArrayList<>();
        List<Point> visited = new ArrayList<>();

        for (int row = 0; row < gridPanel.getRows(); row++) {
            for (int col = 0; col < gridPanel.getCols(); col++) {
                Point point = new Point(col, row);
                distances.add(new PointDistance(point, Integer.MAX_VALUE));
                previousNodes.add(new PointPrevious(point, null));
            }
        }

        setDistance(distances, startNode, 0);
        pq.add(new Node(startNode, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            Point currentNode = current.point;

            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            if (currentNode.equals(endNode)) break;

            for (Point neighbor : getNeighbors(currentNode)) {
                if (!visited.contains(neighbor) && !gridPanel.getWallNodes().contains(neighbor)) {
                    int newDist = getDistance(distances, currentNode) + getWeight(neighbor, weightedNodes, nodeWeights);
                    if (newDist < getDistance(distances, neighbor)) {
                        setDistance(distances, neighbor, newDist);
                        setPrevious(previousNodes, neighbor, currentNode);
                        pq.add(new Node(neighbor, newDist));
                    }
                }
            }
        }

        // Marca el camino más corto
        Point step = endNode;
        while (step != null && getPrevious(previousNodes, step) != null) {
            gridPanel.markPath(step.y, step.x);
            step = getPrevious(previousNodes, step);
        }
    }

    private int getWeight(Point point, List<Point> weightedNodes, List<Integer> nodeWeights) {
        int index = weightedNodes.indexOf(point);
        return index != -1 ? nodeWeights.get(index) : 1;
    }

    private List<Point> getNeighbors(Point point) {
        List<Point> neighbors = new ArrayList<>();
        int[] directions = {-1, 0, 1, 0, -1}; // Up, right, down, left
        for (int i = 0; i < 4; i++) {
            int newRow = point.y + directions[i];
            int newCol = point.x + directions[i + 1];
            if (newRow >= 0 && newRow < gridPanel.getRows() && newCol >= 0 && newCol < gridPanel.getCols()) {
                neighbors.add(new Point(newCol, newRow));
            }
        }
        return neighbors;
    }

    private int getDistance(List<PointDistance> distances, Point point) {
        for (PointDistance pd : distances) {
            if (pd.point.equals(point)) {
                return pd.distance;
            }
        }
        return Integer.MAX_VALUE; // Esto no deberia de suceder
    }

    private void setDistance(List<PointDistance> distances, Point point, int distance) {
        for (PointDistance pd : distances) {
            if (pd.point.equals(point)) {
                pd.distance = distance;
                return;
            }
        }
    }

    private Point getPrevious(List<PointPrevious> previousNodes, Point point) {
        for (PointPrevious pp : previousNodes) {
            if (pp.point.equals(point)) {
                return pp.previous;
            }
        }
        return null; // espero no vuelva a suceder T.T
    }

    private void setPrevious(List<PointPrevious> previousNodes, Point point, Point previous) {
        for (PointPrevious pp : previousNodes) {
            if (pp.point.equals(point)) {
                pp.previous = previous;
                return;
            }
        }
    }

    private static class Node {
        Point point;
        int cost;

        Node(Point point, int cost) {
            this.point = point;
            this.cost = cost;
        }
    }

    private static class PointDistance {
        Point point;
        int distance;

        PointDistance(Point point, int distance) {
            this.point = point;
            this.distance = distance;
        }
    }

    private static class PointPrevious {
        Point point;
        Point previous;

        PointPrevious(Point point, Point previous) {
            this.point = point;
            this.previous = previous;
        }
    }
}
    
