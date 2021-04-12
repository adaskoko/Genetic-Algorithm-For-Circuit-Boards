import java.util.ArrayList;

public class Solution {

    private double CROSSING_PENALTY = 100;
    private double LENGTH_PENALTY = 2;
    private double SEGMENT_PENALTY = 1;


    protected ArrayList<Path> paths;
    protected double fitness;

    public Solution() {
        paths = new ArrayList<>();
    }

    public void calculateFitness(int width, int height){
        fitness = calculateLengthPenalty() + calculateSegmentPenalty() + calculateCrossingPenalty(width, height);
    }

    public double calculateLengthPenalty(){
        double penalty = 0;
        for(Path path : paths){
            penalty += path.points.size() - 1;
        }
        return penalty * LENGTH_PENALTY;
    }

    public double calculateSegmentPenalty(){
        double penalty = 0;
        for(Path path : paths){
            penalty += path.segments.size();
        }
        return penalty * SEGMENT_PENALTY;
    }

    public double calculateCrossingPenaltyOld(){
        double penalty = 0;

        for(int i=0; i<paths.size()-1; i++){
            Path path1 = paths.get(i);
            for(int j=i+1; j<paths.size(); j++){
                Path path2 = paths.get(j);
                for(int k=0; k<path1.points.size(); k++){
                    Point p1 = path1.points.get(k);
                    for(int l=0; l<path2.points.size(); l++){
                        Point p2 = path2.points.get(l);
                        if (p1.equals(p2)) {
                            penalty ++;
                        }
                    }
                }
            }
        }
        return penalty * CROSSING_PENALTY;
    }

    public double calculateCrossingPenalty(int width, int height){
        int[][] points = new int[height][width];

        for(Path path : paths){
            for(Point point : path.points){
                points[point.y][point.x] += 1;
            }
        }

        double penalty = 0;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(points[i][j] > 1)
                    penalty += points[i][j];
            }
        }
        return penalty * CROSSING_PENALTY;
    }
}
