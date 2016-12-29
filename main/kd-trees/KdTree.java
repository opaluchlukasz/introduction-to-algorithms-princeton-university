import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size = 0;

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point) {
        if (!contains(point)) {
            if (root == null) {
                root = new Node(point, new RectHV(0d, 0d, 1d, 1d), 0);
            } else {
                root.insert(point);
            }
            size++;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D point) {
        Node temp = root;
        while (temp != null) {
            if (temp.point.equals(point)) {
                return true;
            } else {
                if (compare(point, temp) >= 0) {
                    temp = temp.rt;
                } else {
                    temp = temp.lb;
                }
            }
        }
        return false;
    }

    private static int compare(Point2D point, Node node) {
        if (node.isVertical()) {
            return Double.compare(point.x(), node.point.x());
        } else {
            return Double.compare(point.y(), node.point.y());
        }
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> result = new ArrayList<>();
        if (root != null) {
            root.intersect(rect, result);
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D point) {
        if (size > 0) {
            return root.nearest(point, null);
        }

        return null;
    }

    private static class Node {
        // the point
        private Point2D point;
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;
        private int level;
        // the left/bottom subtree
        private Node lb;
        // the right/top subtree
        private Node rt;

        Node(Point2D point, RectHV rectHV, int level) {
            this.point = point;
            this.rect = rectHV;
            this.level = level;
        }

        void insert(Point2D toBeAdded) {
            int cmp = compare(toBeAdded, this);

            if (cmp >= 0) {
                if (rt != null) {
                    rt.insert(toBeAdded);
                } else {
                    addNewNode(toBeAdded);
                }
            } else {
                if (lb != null) {
                    lb.insert(toBeAdded);
                } else {
                    addNewNode(toBeAdded);
                }
            }
        }

        private void addNewNode(Point2D toBeAdded) {
            if (isVertical()) {
                //vertical
                if (Double.compare(toBeAdded.x(), point.x()) >= 0) {
                    rt = new Node(toBeAdded, new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax()), level + 1);
                } else {
                    lb = new Node(toBeAdded, new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax()), level + 1);
                }
            } else {
                //horizontal
                if (Double.compare(toBeAdded.y(), this.point.y()) >= 0) {
                    rt = new Node(toBeAdded, new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax()), level + 1);
                } else {
                    lb = new Node(toBeAdded, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y()), level + 1);
                }
            }
        }

        boolean contains(Point2D point) {
            return rect.contains(point);
        }

        void intersect(RectHV searched, List<Point2D> result) {
            if (this.rect.intersects(searched)) {
                if (searched.contains(point)) {
                    result.add(point);
                }
                if (rt != null) {
                    rt.intersect(searched, result);
                }
                if (lb != null) {
                    lb.intersect(searched, result);
                }
            }
        }

        Point2D nearest(Point2D searched, Point2D nearest) {

            if (!this.contains(searched) && nearest != null) {
                double nearestDistance = searched.distanceSquaredTo(nearest);

                if (isVertical() && nearestDistance < searched.distanceTo(new Point2D(this.point.x(), searched.y()))) {
                    return nearest;
                }
                if (!isVertical() && nearestDistance < searched.distanceTo(new Point2D(searched.x(), this.point.y()))) {
                    return nearest;
                }

            }

            double distance = searched.distanceSquaredTo(this.point);
            if(nearest == null || distance < searched.distanceSquaredTo(nearest)) {
                nearest = this.point;
            }

            List<Node> toBeSearched = new LinkedList<>();
            if (isVertical()) {
                if (this.point.x() >= searched.x()) {
                    toBeSearched.add(this.lb);
                    toBeSearched.add(this.rt);
                } else {
                    toBeSearched.add(this.rt);
                    toBeSearched.add(this.lb);
                }
            } else {
                if (this.point.y() >= searched.y()) {
                    toBeSearched.add(this.lb);
                    toBeSearched.add(this.rt);
                } else {
                    toBeSearched.add(this.rt);
                    toBeSearched.add(this.lb);
                }
            }

            for (Node next : toBeSearched) {
                if (next!=null) {
                    nearest = next.nearest(searched, nearest);
                }
            }
            return nearest;

        }

        private boolean isVertical() {
            return level % 2 == 0;
        }
    }
}
