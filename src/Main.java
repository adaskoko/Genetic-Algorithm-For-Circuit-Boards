import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    public static void mainOld(String[] args){
        Board board = new Board();
        board.readFromFile("lab1_problemy_testowe/zad3.txt");
        System.out.println(board);

        GA ga = new GA(1000, 500, 0.3, 0.5, board, "ruletkaZad3");
        //ga.runGATournament(10);

        ga.runGARoulette();
    }


    public static void main(String[] args){
        Board board = new Board();
        board.readFromFile("lab1_problemy_testowe/zad3.txt");
        System.out.println(board);

        int popSize = 1000;
        int epochs = 100;
        double crossingProp = 0.4;
        double mutationProp = 0.0008;

        int tournamentSize = 3;

        PrintWriter logger;

        String folderPath = "Results/ruletka/" + mutationProp + "v2/";

        try {
            File directory = new File(folderPath);
            if (! directory.exists())
                directory.mkdir();

            logger = new PrintWriter(folderPath + "end" + "_" + popSize
                    + "_" + epochs + "_" + crossingProp + "_" + mutationProp + ".txt");

            for(int i = 0; i < 1; i++){
                GA ga = new GA(popSize, epochs, crossingProp, mutationProp, board,
                        folderPath + i + "_" + popSize + "_" + epochs + "_" + crossingProp + "_" + mutationProp + ".txt");
                ga.runGATournament(tournamentSize);
                //ga.runGARoulette();

                logger.println(ga.getBestSolution().fitness + ";" + ga.getWorstFitness() + ";" + ga.getAvgFitness());
            }

            logger.close();

            System.out.println("koniec");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
