import java.util.ArrayList;
import java.util.Random;

public class Crossing {

    public static int count = 0;
    public static int checks = 0;

    public static Solution[] cross(Solution sol1, Solution sol2, double crossingProp){
        Random random = new Random();

        Solution[] sols = {new Solution(), new Solution()};

        for(int i = 0; i < sol1.paths.size(); i++){
            Path path1 = new Path();
            Path path2 = new Path();

            path1.points = new ArrayList<>();
            for(Point point : sol1.paths.get(i).points)
                path1.points.add(new Point(point.x, point.y));
            path1.segments = new ArrayList<>();
            for(Segment point : sol1.paths.get(i).segments)
                path1.segments.add(new Segment(point.direction, point.length));

            path2.points = new ArrayList<>();
            for(Point point : sol2.paths.get(i).points)
                path2.points.add(new Point(point.x, point.y));
            path2.segments = new ArrayList<>();
            for(Segment point : sol2.paths.get(i).segments)
                path2.segments.add(new Segment(point.direction, point.length));

            checks++;

            if(random.nextDouble() < crossingProp){
                sols[0].paths.add(path2);
                sols[1].paths.add(path1);
                count++;
            }else{
                sols[0].paths.add(path1);
                sols[1].paths.add(path2);
            }
        }
        //sols[0].calculateFitness();
        //sols[1].calculateFitness();

        return sols;
    }
}
