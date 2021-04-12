public class Segment {

    protected Path.Direction direction;
    protected int length;

    public Segment(Path.Direction direction, int length) {
        this.direction = direction;
        this.length = length;
    }

    @Override
    public String toString(){
        return "(" + direction + " " + length + ")";
    }


}
