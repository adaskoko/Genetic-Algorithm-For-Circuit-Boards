public class Pair {
    protected Point p1;
    protected Point p2;

    public Pair(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public String toString(){
        return p1.toString() + "->" + p2.toString();
    }
}
