package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private static final int MIN_POINTS_COUNT_ON_LINE_SEGMENT = 4;
    private final LineSegment[] lineSegments;
    private int lineSegmentsSize;

    // finds all maximal line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point on position " + i + " is null");
            }
        }

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy); // sort to know where the ends of the line segments are

        for (int i = 1; i < pointsCopy.length; i++) {
            if (pointsCopy[i - 1].compareTo(pointsCopy[i]) == 0) {
                throw new IllegalArgumentException("there are equal points");
            }
        }

        lineSegments = new LineSegment[points.length];
        lineSegmentsSize = 0;

        findMaximalLineSegmentsContainingFourPoints(points, pointsCopy);
    }

    private static class MyLineSegment {
        private final Point p;
        private final Point q;
        private final double slope;

        public MyLineSegment(Point p, Point q) {
            this.p = p;
            this.q = q;
            this.slope = p.slopeTo(q);
        }

        boolean overlapsWith(MyLineSegment other) {
            if (Double.compare(slope, other.slope) == 0) { // check slope
                // endpoint lays in other line segment
                return isBetween(p, other.p, other.q) || isBetween(q, other.p, other.q);
            }

            return false;
        }

        private boolean isBetween(Point p, Point end1, Point end2) {
            return ((p.compareTo(end1) >= 0 && p.compareTo(end2) <= 0) || (p.compareTo(end1) <= 0 && p.compareTo(end2) >= 0));
        }
    }
    private void findMaximalLineSegmentsContainingFourPoints(Point[] pointsOriginal, Point[] points) {
        int myLineSegmentsSize = 0;
        MyLineSegment[] myLineSegments = new MyLineSegment[pointsOriginal.length];

        for (int i = 0; i < points.length; i++) {
            Comparator<Point> bySlopeOrder = pointsOriginal[i].slopeOrder();
            Arrays.sort(points, i, points.length, bySlopeOrder); // current point is first

            printPoints(points);

            int count;
            for (int j = i + 1; j < points.length - 2; j += count) {
//                double curSlope = points[i].slopeTo(points[j]);
//                if (exists(curSlope, slopes, slopesSize)) { // check if line segments overlap
//                    int ind = getIndOfSlope(curSlope, slopes, slopesSize);
//                    if (isBetween(pointsOriginal[i], endPoints[ind][0], endPoints[ind][1])) {
//                        break;
//                    }
//                }

                count = getCollinearCount(points, j, bySlopeOrder);

                // there are at least 4 collinear points(minus 1 because we don't count j)
                if (count >= MIN_POINTS_COUNT_ON_LINE_SEGMENT - 1) {
                    int min = getMinInd(i, points, j, j + count);
                    int max = getMaxInd(i, points, j, j + count);

                    MyLineSegment curMyLineSegment = new MyLineSegment(points[min], points[max]);

                    if (!overlaps(curMyLineSegment, myLineSegments, myLineSegmentsSize)) {
                        lineSegments[lineSegmentsSize++] = new LineSegment(points[min], points[max]);
                        myLineSegments[myLineSegmentsSize++] = curMyLineSegment;
//                        slopes[slopesSize++] = curSlope;
//                        endPoints[endPointsSize][0] = points[min];
//                        endPoints[endPointsSize][1] = points[max];
//                        endPointsSize++;

                        System.out.println(lineSegments[lineSegmentsSize - 1]);
                        System.out.println();
                    }
                }
            }
        }
    }

    private void printPoints(Point[] points) {
        for (Point p : points) {
            System.out.println(p);
        }
        System.out.println();
    }

    private boolean overlaps(MyLineSegment myLineSegment, MyLineSegment[] myLineSegments, int myLineSegmentsSize) {
        for (int i = 0; i < myLineSegmentsSize; i++) {
            if (myLineSegment.overlapsWith(myLineSegments[i])) {
                return true;
            }
        }

        return false;
    }

    private boolean exists(double slope, double[] slopes, int slopesSize) {
        for (int i = 0; i < slopesSize; i++) {
            if (Double.compare(slope, slopes[i]) == 0) {
                return true;
            }
        }

        return false;
    }

    private int getIndOfSlope(double slope, double[] slopes, int slopesSize) {
        for (int i = 0; i < slopesSize; i++) {
            if (Double.compare(slope, slopes[i]) == 0) {
                return i;
            }
        }

        return -1;
    }

    private int getCollinearCount(Point[] points, int start, Comparator<Point> comparator) {
        // start is already collinear
        int count = 1;
        for (int i = start + 1; i < points.length; i++) {
            if (comparator.compare(points[start], points[i]) != 0) {
                break;
            }

            count++;
        }

        return count;
    }

    // end is exclusive
    private int getMinInd(int p, Point[] points, int start, int end) {
        int min = p;
        for (int i = start; i < end; i++) {
            if (points[i].compareTo(points[min]) < 0) {
                min = i;
            }
        }

        return min;
    }

    // end is exclusive
    private int getMaxInd(int p, Point[] points, int start, int end) {
        int max = p;
        for (int i = start; i < end; i++) {
            if (points[i].compareTo(points[max]) > 0) {
                max = i;
            }
        }

        return max;
    }

//    private boolean exists(collinear.Point min, collinear.Point max) {
//        String cur = min + " -> " + max;
//        for (int i = 0; i < lineSegmentsSize; i++) {
//            if (cur.equals(lineSegments[i].toString())) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentsSize;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegmentsSize);
    }

    public static void main(String[] args) {
        In in = new In("F:\\Java\\Projects\\Coursera-Algorithms-1\\input4horizontal.txt");

        // read the n points from a file
//        In in = new In(args[0]);
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

//        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

//         Example usage
//        collinear.Point[] points = {new collinear.Point(10, 0), new collinear.Point(0, 10), new collinear.Point(3, 7), new collinear.Point(7, 3),
//            new collinear.Point(20, 21), new collinear.Point(3, 4), new collinear.Point(14, 15), new collinear.Point(6, 7)};
//        collinear.Point[] points = {new collinear.Point(19000, 10000), new collinear.Point(18000, 10000), new collinear.Point(32000, 10000),
//            new collinear.Point(21000, 10000), new collinear.Point(1234, 5678), new collinear.Point(14000, 10000)};
//
//        collinear.FastCollinearPoints fcp = new collinear.FastCollinearPoints(points);
//        for (collinear.LineSegment segment : fcp.segments()) {
//            System.out.println(segment);
//        }
    }
}
