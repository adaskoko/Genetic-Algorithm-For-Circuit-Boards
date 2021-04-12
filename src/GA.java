import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GA {

    int populationSize;
    int epochs;
    double crossingProp;
    double mutationProp;
    Board board;
    Solution[] population;
    PrintWriter logger;

    public GA(int population_size, int epochs, double crossingProp, double mutationProp, Board board, String filename) {
        this.populationSize = population_size;
        this.epochs = epochs;
        this.crossingProp = crossingProp;
        this.mutationProp = mutationProp;
        this.board = board;
        try {
            logger = new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateRandomPopulation(){
        population = new Solution[populationSize];
        Solution sol;

        for(int i = 0; i < populationSize; i++){
            sol = new Solution();

            for(Pair pair : board.pairs)
                sol.paths.add(Path.createRandomPath(pair.p1, pair.p2, board.width, board.height));

            sol.calculateFitness(board.width, board.height);

            population[i] = sol;
        }
        logPopulation();
    }

    public void runGARoulette(){
        generateRandomPopulation();

        Solution[] newPopulation;
        Roulette roulette;

        Solution sol1, sol2;
        Solution[] crossResult;

        for(int i = 0; i< epochs; i++) {
            System.out.println("Epoch: " + i);

            newPopulation = new Solution[populationSize];
            roulette = new Roulette(population);
            int popSize = 0;

            while (popSize < population.length) {
                sol1 = roulette.selectSolution();
                do{
                    sol2 = roulette.selectSolution();
                }while(sol1 == sol2);

                crossResult = Crossing.cross(sol1, sol2, crossingProp);

                newPopulation[popSize++] = Mutation.mutate(crossResult[0], mutationProp, board.width, board.height);
                newPopulation[popSize++] = Mutation.mutate(crossResult[1], mutationProp, board.width, board.height);
            }
            population = newPopulation;

            for(int j = 0; j < populationSize; j++){
                population[j].calculateFitness(board.width, board.height);
            }

            logPopulation();
        }

        logger.println("Ilosc mutacji: " + Mutation.count);
        logger.println("Ilosc prob mutacji: " + Mutation.checks);

        logger.println("Ilosc krzyzowan: " + Crossing.count);
        logger.println("Ilosc prob krzyzowan: " + Crossing.checks);

        logger.close();

        Solution bestSol = getBestSolution();
        System.out.println("fitness " + bestSol.fitness
                + " crossing Penalty " + bestSol.calculateCrossingPenalty(board.width, board.height)
                + " length Penalty " + bestSol.calculateLengthPenalty()
                + " segments Penalty " + bestSol.calculateSegmentPenalty());

        //System.out.println(bestSol.paths);
        System.out.println("Ilosc mutacji: " + Mutation.count);
        System.out.println("Ilosc prob mutacji: " + Mutation.checks);

        System.out.println("Ilosc krzyzowan: " + Crossing.count);
        System.out.println("Ilosc prob krzyzowan: " + Crossing.checks);

        Drawer.drawSolution(board, bestSol, "Best");
    }

    public void runGATournament(int tourSize){
        generateRandomPopulation();

        Solution[] newPopulation;
        Tournament tournament;


        Solution sol1, sol2;
        Solution[] crossResult;

        for(int i = 0; i < epochs; i++){
            System.out.println("Epoch: " + i);

            newPopulation = new Solution[populationSize];
            tournament = new Tournament(population, tourSize);
            int popSize = 0;

            while(popSize < population.length){

                sol1 = tournament.selectSolution();
                do{
                    sol2 = tournament.selectSolution();
                }while(sol1 == sol2);

                crossResult = Crossing.cross(sol1, sol2, crossingProp);

                newPopulation[popSize++] = Mutation.mutate(crossResult[0], mutationProp, board.width, board.height);
                newPopulation[popSize++] = Mutation.mutate(crossResult[1], mutationProp, board.width, board.height);
            }
            population = newPopulation;

            for(int j = 0; j < population.length; j++){
                population[j].calculateFitness(board.width, board.height);
            }

            logPopulation();
        }

        logger.println("Ilosc mutacji: " + Mutation.count);
        logger.println("Ilosc prob mutacji: " + Mutation.checks);

        logger.println("Ilosc krzyzowan: " + Crossing.count);
        logger.println("Ilosc prob krzyzowan: " + Crossing.checks);

        logger.close();

        Solution bestSol = getBestSolution();
        System.out.println("fitness " + bestSol.fitness
                + " crossing Penalty " + bestSol.calculateCrossingPenalty(board.width, board.height)
                + " length Penalty " + bestSol.calculateLengthPenalty()
                + " segments Penalty " + bestSol.calculateSegmentPenalty());

        //System.out.println(bestSol.paths);
        System.out.println("Ilosc mutacji: " + Mutation.count);
        System.out.println("Ilosc prob mutacji: " + Mutation.checks);

        System.out.println("Ilosc krzyzowan: " + Crossing.count);
        System.out.println("Ilosc prob krzyzowan: " + Crossing.checks);

        Drawer.drawSolution(board, bestSol, "Best");
    }

    public void logPopulation(){
        Solution bestSol = getBestSolution();
        double best = bestSol.fitness;
        double worst = getWorstFitness();
        double avg = getAvgFitness();

        if(avg < best){
            System.out.println(population[0].paths.get(0).segments == population[3].paths.get(0).segments);
            System.out.println(population[0].equals(population[3]));
            System.out.println(population.length);
        }

        String log = best + ";" + worst + ";"  + avg;
        //System.out.println(log);

        logger.println(log);
    }

    public Solution getBestSolution(){
        Solution bestSol = population[0];

        for(int i = 1; i < population.length; i++){
            if(population[i].fitness < bestSol.fitness)
                bestSol = population[i];
        }

        return bestSol;
    }

    public double getWorstFitness(){
        double worst = population[0].fitness;
        for(int i =1; i < population.length; i++){
            if(population[i].fitness > worst)
                worst = population[i].fitness;
        }
        return worst;
    }

    public double getAvgFitness(){
        double sum = 0;
        for(int i =1; i < population.length; i++){
            sum += population[i].fitness;
        }
        return (sum / population.length);
    }
}
