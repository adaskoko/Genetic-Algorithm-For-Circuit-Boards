import java.util.ArrayList;
import java.util.Random;

public class Path {

    protected ArrayList<Point> points;
    protected ArrayList<Segment> segments;

    public Path() {
        points = new ArrayList<>();
        segments = new ArrayList<>();
    }

    @Override
    public String toString(){
        String out = "punkty:\n";
        for(Point point : points){
            out += point;
        }

        out += "\nsegmenty:\n";
        for(Segment segm : segments){
            out += segm;
        }

        return out;
    }

    public enum Direction{
        left, right, up, down
    }

    public static Path createRandomPath(Point p1, Point p2, int width, int height){
        Random random = new Random();

        Path path = new Path();
        path.points.add(p1);
        Point current = p1;

        int forbiddenDir = -1;

        do{
            int x = current.x;
            int y = current.y;
            int dir;

            do{
                dir = random.nextInt(4);

                switch(Direction.values()[dir]){
                    case left:
                        x = current.x - 1;
                        y = current.y;
                        break;
                    case right:
                        x = current.x + 1;
                        y = current.y;
                        break;
                    case down:
                        y = current.y + 1;
                        x = current.x;
                        break;
                    case up:
                        y = current.y - 1;
                        x = current.x;
                        break;
                }
            }while(x >= width || x < 0 || y >= height || y < 0 || dir == forbiddenDir);

            switch(Direction.values()[dir]){
                case left:
                    forbiddenDir = 1;
                    break;
                case right:
                    forbiddenDir = 0;
                    break;
                case down:
                    forbiddenDir = 2;
                    break;
                case up:
                    forbiddenDir = 3;
                    break;
            }

           /* Segment segment;
            if(path.segments.size() == 0){
                segment = new Segment(Direction.values()[dir], 1);
                path.segments.add(segment);
            }
            else{
                segment = path.segments.get(path.segments.size() - 1);
                if(segment.direction == Direction.values()[dir])
                    segment.length ++;
                else{
                    segment = new Segment(Direction.values()[dir], 1);
                    path.segments.add(segment);
                }
            }*/

            current = new Point(x, y);
            path.points.add(current);

        }while(!current.equals(p2));

        path.repairLoops();
        path.repairSegments();

        return path;
    }

    public void repairSegments(){
        segments = new ArrayList<>();

        Point p1 = new Point(points.get(0).x, points.get(0).y);
        for(int i = 1; i < points.size(); i++){

            Point p2 = new Point(points.get(i).x, points.get(i).y) ;
            int dir;
            if(p2.x > p1.x){
                dir = 1;
            }else if(p2.x < p1.x){
                dir = 0;
            }else if(p2.y - p1.y == 1){
                dir = 2;
            }else{
                dir = 3;
            }

            p1 = new Point(p2.x, p2.y);

            Segment segment;
            if(segments.size() == 0){
                segment = new Segment(Direction.values()[dir], 1);
                segments.add(segment);
            }
            else{
                segment = segments.get(segments.size() - 1);
                if(segment.direction == Direction.values()[dir])
                    segment.length ++;
                else{
                    segment = new Segment(Direction.values()[dir], 1);
                    segments.add(segment);
                }
            }
        }
    }


    public void repairLoops(){
        ArrayList<Point> newPoints = new ArrayList<>();

        //System.out.println("elo");
        //System.out.println(points);

        boolean inLoop = false;
        int loopEndId = -1;
        for(int i = 0; i < points.size(); i++){
            if(!inLoop) {
                newPoints.add(points.get(i));

                for (int j = i + 1; j < points.size(); j++) {
                    if (points.get(i).equals(points.get(j))) {
                        inLoop = true;
                        loopEndId = j;
                    }
                }
            }else{
                if(i == loopEndId){
                    inLoop = false;
                }
            }
        }

        points = newPoints;
        //System.out.println(points);

        /*for(int k = 0; k < points.size() - 1; k++){
            if(Math.abs(points.get(k).x - points.get(k + 1).x) > 1|| Math.abs(points.get(k).y - points.get(k + 1).y) > 1)
                System.out.println("CHUUJ");
        }*/
    }
}
