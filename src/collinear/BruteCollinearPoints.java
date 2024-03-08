package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;
    private int lineSegmentsSize;

    // finds all line segments containing 4 points(works only with max 4 points on a line)
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point on position " + i + " is null");
            }
        }
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copyPoints); // sort to know where the ends of the line segments are

        for (int i = 1; i < copyPoints.length; i++) {
            if (copyPoints[i - 1].compareTo(copyPoints[i]) == 0) {
                throw new IllegalArgumentException("there are equal points");
            }
        }

        lineSegments = new LineSegment[points.length];
        lineSegmentsSize = 0;

//        for (collinear.Point p : points) {
//            System.out.println(p);
//        }
//        System.out.println();

        findLineSegmentsContainingFourPoints(copyPoints);
    }

    private void findLineSegmentsContainingFourPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    Comparator<Point> bySlopeOrder = points[i].slopeOrder();
                    if (bySlopeOrder.compare(points[j], points[k]) == 0) {
                        for (int p = k + 1; p < points.length; p++) {
                            if (bySlopeOrder.compare(points[p], points[j]) == 0) {
                                lineSegments[lineSegmentsSize++] = new LineSegment(points[i], points[p]);
//                                System.out.println(points[i]);
//                                System.out.println(points[j]);
//                                System.out.println(points[k]);
//                                System.out.println(points[p]);
//                                System.out.println(lineSegments[lineSegmentsSize - 1]);
//                                System.out.println();
                            }
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentsSize;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegmentsSize);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

//         Example usage
//        collinear.Point[] points = {new collinear.Point(10, 0), new collinear.Point(0, 10), new collinear.Point(3, 7), new collinear.Point(7, 3),
//            new collinear.Point(20, 21), new collinear.Point(3, 4), new collinear.Point(14, 15), new collinear.Point(6, 7)};
//
//        collinear.BruteCollinearPoints bcp = new collinear.BruteCollinearPoints(points);
//        for (collinear.LineSegment segment : bcp.segments()) {
//            System.out.println(segment);
//        }
    }
}
