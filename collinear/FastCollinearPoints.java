import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    private Point[] points;
    private int numberOfSegments;
    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
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
        this.segments = generateLines();
        this.numberOfSegments = segments.size();
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    private ArrayList<LineSegment> generateLines() {
        ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

        Point[] pointsCopy = this.points;
//        for (Point point : pointsCopy) {
//            System.out.println(point);
//        }
//        int[] pSlopes = new int[pointsCopy.length];

        double slope;
        int lineStartIndex = 0;
        int lineEndIndex = 0;
        int numberSegs = 0;

        Arrays.sort(pointsCopy);
//        System.out.println("Before for-loop");
        for (int i = 0; i < pointsCopy.length; i++) {
            Point origin = pointsCopy[i];
            ArrayList<Point> linePoints = new ArrayList<Point>();
            linePoints.add(origin);
            Arrays.sort(pointsCopy, origin.slopeOrder());
//            System.out.println("Origin: " + origin.toString());
//            System.out.println("Points: " + origin.toString() + " , " + pointsCopy[0]);
//            if (slope != points[i].slopeTo(points[i+1])) {
            if (pointsCopy.length < 2) {
                return segs;
            }
//            else if (origin.compareTo(pointsCopy[0]) != 0) {
//                slope = origin.slopeTo(pointsCopy[0]);
//                linePoints.add(pointsCopy[0]);
//            }
//            else {
                slope = origin.slopeTo(pointsCopy[1]);
                linePoints.add(pointsCopy[1]);
//            }

//            System.out.println("Before second for-loop");
                for (int j = 2; j < pointsCopy.length; j++) {
//                    if (origin.compareTo(new Point(8192, 25088)) == 0) {
//                        System.out.println("Found it.");
//                        System.out.println("Origin: " + origin.toString());
//                        System.out.println("Slope: " + slope);
//                        for (Point point : linePoints) {
//                            System.out.println(point.toString() + " Slope with origin: " + origin.slopeTo(point));
//                        }
                   // }

//                System.out.println("Points: " + origin.toString() + " , " + pointsCopy[j]);
//                System.out.println("Slope: " + slope + " pointsCopy["+ i +"].slopeTo(pointsCopy[" + j + "]) " + pointsCopy[i].slopeTo(pointsCopy[j]));
                    if (origin.slopeTo(pointsCopy[j]) == slope && origin.compareTo(pointsCopy[j]) != 0) {

//                    System.out.println("Slope from " + origin.toString() + " to " + pointsCopy[j].toString() + ": " + slope);

                        linePoints.add(pointsCopy[j]);

                    }
                    else if (linePoints.size() >= 4 && origin.compareTo(pointsCopy[j]) != 0){

                        slope = origin.slopeTo(pointsCopy[j]);
//                        System.out.println("Should add a line now");
                        if (origin.compareTo(minPoint(linePoints)) == 0) {
//                            for (Point point : linePoints) {
//                                System.out.println(point.toString());
//                            }
//                            System.out.println("Max: " + maxPoint(linePoints));
//                            System.out.println("Min: " + minPoint(linePoints));
                            segs.add(new LineSegment(origin, maxPoint(linePoints)));

                            numberSegs++;

                        }

                        linePoints = new ArrayList<Point>();
                        linePoints.add(origin);
                        linePoints.add(pointsCopy[j]);
                        slope = origin.slopeTo(pointsCopy[j]);
                    }
                    else if (origin.compareTo(pointsCopy[j]) != 0){

//                        System.out.println("Didn't add a line.");
                        slope = origin.slopeTo(pointsCopy[j]);
                        linePoints = new ArrayList<Point>();
                        linePoints.add(origin);
                        linePoints.add(pointsCopy[j]);

//                    System.out.println("Slope: " + slope);
                    }
//                    System.out.println("linePoints.size(): " + linePoints.size());
                }
                if (linePoints.size() >= 4) {

                    if (origin.compareTo(minPoint(linePoints)) == 0) {
//                        for (Point point : linePoints) {
//                            System.out.println(point.toString());
//                        }
//                        System.out.println("Max: " + maxPoint(linePoints));
//                        System.out.println("Min: " + minPoint(linePoints));
                        segs.add(new LineSegment(origin, maxPoint(linePoints)));

                        linePoints = new ArrayList<Point>();
                        linePoints.add(origin);
//                        linePoints.add(pointsCopy[j]);
//                        slope = origin.slopeTo(pointsCopy[j]);
                        numberSegs++;

                    }
                }
                Arrays.sort(pointsCopy);
         //   }

        }
//        System.out.println("After for-loop " + numberSegs);
        this.numberOfSegments = numberSegs;

        return segs;
    }

    private Point minPoint(ArrayList<Point> points) {
        Point min = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            if (min.compareTo(points.get(i)) > 0) {
                min = points.get(i);
            }
        }

        return min;
    }

    private Point maxPoint(ArrayList<Point> points) {
        Point max = points.get(0);
        for (int i = 1; i < points.size(); i++) {
//            System.out.println(max.compareTo(points.get(i)));
            if (max.compareTo(points.get(i)) < 0) {
                max = points.get(i);
            }
        }

        return max;
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
