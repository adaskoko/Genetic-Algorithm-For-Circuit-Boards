public class Point {
    protected int x;
    protected int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "(" + x + ";" + y + ")";
    }

    @Override
    public boolean equals(Object obj){
        return ((Point)obj).x == this.x && ((Point)obj).y == this.y;
    }
}
