import java.util.ArrayList;
import java.util.Random;

public class Mutation {

    public static int count = 0;
    public static int checks = 0;

    public static Solution mutateOld(Solution sol, double mutationProp, int width, int height){
        Random random = new Random();

        ArrayList<Path> paths = new ArrayList<>();
        for(Path path : sol.paths){
            Path newPath = path;
            for(int i = 0; i < path.segments.size(); i++){
                if(random.nextDouble() < mutationProp){
                    newPath = mutateSegment(path, i, width, height);
                    //newPath = repairEnding(newPath);
                    //newPath = repairTurningBack(newPath);
                    newPath.repairLoops();
                    newPath.repairSegments();
                }
            }
            paths.add(newPath);
        }
        sol.paths = paths;

        //sol.calculateFitness();
        return sol;
    }

    public static Solution mutate(Solution sol, double mutationProp, int width, int height){
        Random random = new Random();

        ArrayList<Path> paths = new ArrayList<>();
        for(Path path : sol.paths){
            for(int i = 0; i < path.segments.size(); i++){
                checks ++;
                if(random.nextDouble() < mutationProp){
                    count ++;
                    path = mutateSegment(path, i, width, height);
                    //System.out.println(path);
                    //newPath = repairEnding(newPath);
                    //newPath = repairTurningBack(newPath);
                    path.repairLoops();
                    //System.out.println(path);

                    /*for(int k = 0; k < path.points.size() - 1; k++){
                        if(Math.abs(path.points.get(k).x - path.points.get(k + 1).x) > 1|| Math.abs(path.points.get(k).y - path.points.get(k + 1).y) > 1)
                            System.out.println("CHUUJ");
                    }*/

                    path.repairSegments();
                    //i = path.segments.size();
                }
            }
            paths.add(path);
        }
        sol.paths = paths;

        //sol.calculateFitness();
        return sol;
    }

    private static Path mutateSegment(Path path, int i, int width, int height){
        Random random = new Random();
        int dir = random.nextInt(2);
        //System.out.println(path.points);

        Segment segment = path.segments.get(i);
        int segmentStart = 0;
        int segmentEnd;

        for(int j = 0; j < i; j++){
            segmentStart += path.segments.get(j).length;
        }
        segmentEnd = segmentStart + segment.length;

        //System.out.println("start: " + segmentStart + " end: " + segmentEnd);

        ArrayList<Point> newPoints = new ArrayList<>();

        for(int j = 0; j <= segmentStart; j++){
            newPoints.add(path.points.get(j));
        }

        boolean wasMutated = false;

        if(segment.direction == Path.Direction.up || segment.direction == Path.Direction.down) {
            if (dir == 0) { //left
                if (path.points.get(segmentStart).x > 0) {
                    for (int j = segmentStart; j <= segmentEnd; j++) {
                        Point point = new Point(path.points.get(j).x, path.points.get(j).y);
                        point.x--;
                        newPoints.add(point);
                        //path.points.add(j + 1, point);
                        wasMutated = true;
                    }
                }
            } else { //right
                if (path.points.get(segmentStart).x < width - 1) {
                    for (int j = segmentStart; j <= segmentEnd; j++) {
                        Point point = new Point(path.points.get(j).x, path.points.get(j).y);
                        point.x++;
                        newPoints.add(point);
                        //path.points.add(j + 1, point);
                        wasMutated = true;
                    }
                }
            }
        }else{
            if(dir == 0){ //up
                if(path.points.get(segmentStart).y > 0){
                    for(int j = segmentStart; j <= segmentEnd; j++){
                        Point point = new Point(path.points.get(j).x, path.points.get(j).y);
                        point.y--;
                        newPoints.add(point);
                        //path.points.add(j + 1, point);
                        wasMutated = true;
                    }
                }
            }else{ //down
                if(path.points.get(segmentStart).y < height - 1){
                    for(int j = segmentStart; j <= segmentEnd; j++){
                        Point point = new Point(path.points.get(j).x, path.points.get(j).y);
                        point.y++;
                        newPoints.add(point);
                        //path.points.add(j + 1, point);
                        wasMutated = true;
                    }
                }
            }
        }

        if(!wasMutated){
            for(int j = segmentStart + 1; j < segmentEnd; j++){
                Point point = new Point(path.points.get(j).x, path.points.get(j).y);
                newPoints.add(point);
            }
        }

        for(int j = segmentEnd; j < path.points.size(); j++){
            newPoints.add(path.points.get(j));
        }

        path.points = newPoints;

        /*System.out.println(path.points);

        for(int k = 0; k < path.points.size() - 1; k++){
            if(Math.abs(path.points.get(k).x - path.points.get(k + 1).x) > 1|| Math.abs(path.points.get(k).y - path.points.get(k + 1).y) > 1)
                System.out.println("DZIURA");
        }*/

        return path;
    }

    private static Path mutateSegmentOld(Path path, int i, int width, int height){
        Random random = new Random();
        int dir = random.nextInt(2);

        Segment segment = path.segments.get(i);
        int segmentStart = 0;
        int segmentEnd;

        for(int j = 0; j < i; j++){
            segmentStart += path.segments.get(j).length;
        }
        segmentEnd = segmentStart + segment.length;

        ArrayList<Point> newPoints = new ArrayList<>();

        for(int j = 0; j <= segmentStart; j++){
            newPoints.add(path.points.get(j));
        }

        if(segment.direction == Path.Direction.up || segment.direction == Path.Direction.down) {
            if (dir == 0) { //left
                if (path.points.get(segmentStart).x > 0) {
                    for (int j = segmentStart; j <= segmentEnd; j++) {
                        Point point = new Point(path.points.get(j).x, path.points.get(j).y);
                        point.x--;
                        newPoints.add(point);
                        //path.points.add(j + 1, point);
                    }
                }
            } else { //right
                if (path.points.get(segmentStart).x < width - 1) {
                    for (int j = segmentStart; j <= segmentEnd; j++) {
                        Point point = new Point(path.points.get(j).x, path.points.get(j).y);
                        point.x++;
                        newPoints.add(point);
                        //path.points.add(j + 1, point);
                    }
                }
            }
        }else{
            if(dir == 0){ //up
                if(path.points.get(segmentStart).y > 0){
                    for(int j = segmentStart; j <= segmentEnd; j++){
                        Point point = new Point(path.points.get(j).x, path.points.get(j).y);
                        point.y--;
                        newPoints.add(point);
                        //path.points.add(j + 1, point);
                    }
                }
            }else{ //down
                if(path.points.get(segmentStart).y < height - 1){
                    for(int j = segmentStart; j <= segmentEnd; j++){
                        Point point = new Point(path.points.get(j).x, path.points.get(j).y);
                        point.y++;
                        newPoints.add(point);
                        //path.points.add(j + 1, point);
                    }
                }
            }
        }

        for(int j = segmentEnd; j < path.points.size(); j++){
            newPoints.add(path.points.get(j));
        }

        path.points = newPoints;

        return path;
    }

    private static Path repairEnding(Path path){
        for(int i = 0; i < path.points.size() - 1; i++){
            if(path.points.get(i).equals(path.points.get(path.points.size()-1))){ //usuniecie kolejnych jak trafimy na mete
                for(int j = i + 1; j < path.points.size(); j++){
                    path.points.remove(i+1);
                }
                break;
            }
        }
        return path;
    }

    private static Path repairTurningBack(Path path){
        for(int i = 0; i < path.points.size() - 2; i++){
            if(path.points.get(i).equals(path.points.get(i+2))){
                path.points.remove(i+1);
                path.points.remove(i+1);
                i = 0;
            }
        }
        return path;
    }

    private static Path repairSegments(Path path){
        path.segments = new ArrayList<>();

        Point p1 = new Point(path.points.get(0).x, path.points.get(0).y);
        for(int i = 1; i < path.points.size(); i++){

            Point p2 = new Point(path.points.get(i).x, path.points.get(i).y) ;
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
            if(path.segments.size() == 0){
                segment = new Segment(Path.Direction.values()[dir], 1);
                path.segments.add(segment);
            }
            else{
                segment = path.segments.get(path.segments.size() - 1);
                if(segment.direction == Path.Direction.values()[dir])
                    segment.length ++;
                else{
                    segment = new Segment(Path.Direction.values()[dir], 1);
                    path.segments.add(segment);
                }
            }
        }

        return path;
    }
}
