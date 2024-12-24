import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        else {
            Point[] pointsCopy = new Point[points.length];
            for (int i = 0; i < pointsCopy.length; i++) {
                pointsCopy[i] = points[i];
            }
            for (int i = 0; i < pointsCopy.length; i++) {
                if (pointsCopy[i] == null) {
                    throw new IllegalArgumentException();
                }
            }
            Arrays.sort(pointsCopy);
            for (int i = 0; i < pointsCopy.length-1; i++) {
                if (pointsCopy[i].compareTo(pointsCopy[i+1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            this.points = pointsCopy;
        }
        this.segments = generateLines(points);
    }

    private ArrayList<LineSegment> generateLines(Point[] points) {
        ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

//        System.out.println("segments.length: " + segments.length);

        for (int i = 0; i < points.length-3; i++) {
            double[] slopes = new double[3];
            Point[] ps = new Point[4];
            ps[0] = points[i];
//            System.out.println("Just created slopes.");
            for (int j = i+1; j < points.length-2; j++) {
                ps[1] = points[j];
                slopes[0] = points[i].slopeTo(points[j]);
//                System.out.println("Points: " + ps[0].toString() + ps[1].toString());
//                System.out.println("Slope between " + ps[0].toString() + " and " + ps[1].toString() + ": " + slopes[0]);
                for (int k = j+1; k < points.length-1; k++) {
                    ps[2] = points[k];
                    slopes[1] = points[i].slopeTo(points[k]);
//                    System.out.println("Points: " + ps[0].toString() + ps[1].toString() + ps[2].toString());
//                    System.out.println("Slope between " + ps[0].toString() + " and " + ps[2].toString() + ": " + slopes[1]);
                    if (slopes[1] == slopes[0]) {
                        for (int l = k+1; l < points.length; l++) {
                            ps[3] = points[l];
                            slopes[2] = points[i].slopeTo(points[l]);
//                            System.out.println("Points: " + ps[0].toString() + ps[1].toString() + ps[2].toString() + ps[3].toString());
//                        System.out.println("Slope between " + ps[0].toString() + " and " + ps[3].toString() + ": " + slopes[2]);
                            if (slopes[2] == slopes[1]) {
                                Arrays.sort(ps);
//                                System.out.println("Adding segment from " + ps[0].toString() + " to " + ps[1].toString() + " to " + ps[2].toString() + " to " + ps[3].toString());
                                segs.add(new LineSegment(ps[0], ps[3]));
                            }
                        }
                    }

                }
            }
        }

        return segs;
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {

        return this.segments.toArray(new LineSegment[segments.size()]);
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
    }

}
