import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import org.w3c.dom.css.Rect;

import java.util.Iterator;

public class PointSET {
    private SET<Point2D> redBlackBST;

    // construct an empty set of points
    public PointSET() {
        redBlackBST = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return redBlackBST.isEmpty();
    }

    // number of points in the set
    public int size() {
        return redBlackBST.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validateArgument(p);

        redBlackBST.add(p);
    }

    private void validateArgument(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("argument is null");
        }
    }

//    private Node insert(Point2D point, Node node) {
//        if (node == null) {
//            return new Node(point, 1);
//        }
//
//        int cmp = point.compareTo(node.point);
//        if (cmp < 0) {
//            node.left = insert(point, node.left);
//        } else if (cmp > 0) {
//            node.right = insert(point, node.right);
//        }
//        else {
//            return node;
//        }
//
//        node.count = 1 + size(node.left) + size(node.right);
//
//        return node;
//    }

//    private int size(Node node) {
//        if (node == null) {
//            return 0;
//        }
//
//        return node.count;
//    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validateArgument(p);

        return redBlackBST.contains(p);
    }

//    private boolean containsPoint(Point2D point) {
//        Node node = root;
//        while (node != null) {
//            int cmp = point.compareTo(node.point);
//            if (cmp < 0) {
//                node = node.left;
//            }
//            else if (cmp > 0) {
//                node = node.right;
//            }
//            else {
//                return true;
//            }
//        }
//
//        return false;
//    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : redBlackBST) {
            StdDraw.point(point.x(), point.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validateArgument(rect);

        Queue<Point2D> points = new Queue<>();
        for (Point2D point : redBlackBST) {
            if (rect.contains(point)) {
                points.enqueue(point);
            }
        }

        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validateArgument(p);

        if (redBlackBST.isEmpty()) {
            return null;
        }

        Iterator<Point2D> iterator = redBlackBST.iterator();
        Point2D nearest = iterator.next();
        double minDistanceSquared = p.distanceSquaredTo(nearest);
        while (iterator.hasNext()) {
            Point2D curPoint = iterator.next();
            double curDistanceSquared = p.distanceSquaredTo(curPoint);
            if (curDistanceSquared < minDistanceSquared) {
                nearest = curPoint;
                minDistanceSquared = curDistanceSquared;
            }
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET pointSET = new PointSET();

        Point2D point1 = new Point2D(0.5, 0.5);
        Point2D point2 = new Point2D(0.3, 0.6);
        Point2D point3 = new Point2D(0.1, 0.7);
        Point2D point4 = new Point2D(0.2, 0.2);

        pointSET.insert(point1);
        pointSET.insert(point2);
        pointSET.insert(point3);

        System.out.println(pointSET.contains(point1) + " " + pointSET.contains(point2) + " "
            + pointSET.contains(point3) + " " + pointSET.contains(point4));

        pointSET.draw();

        Point2D point = new Point2D(0.6, 0.9);
        point.draw();
        System.out.println(pointSET.nearest(point3));

        RectHV rect = new RectHV(0.3, 0.3, 1, 1);
        Iterable<Point2D> points = pointSET.range(rect);
        for (Point2D curPoint : points) {
            System.out.println(curPoint);
        }
    }
}