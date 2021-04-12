import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Tournament {

    Solution[] population;
    int size;

    public Tournament(Solution[] population, int size) {
        this.population = population;
        this.size = size;
    }

    public static Solution selectSolutionOld(Solution[] population, int size){
        Solution[] group = new Solution[size];

        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0; i < population.length; i++)
            indexes.add(i);
        Collections.shuffle(indexes);

        for(int i = 0 ; i < size; i++)
            group[i] = population[indexes.get(i)];

        Solution bestSol = group[0];
        for(int i =1; i < group.length; i++){
            if(group[i].fitness < bestSol.fitness)
                bestSol = group[i];
        }

        return bestSol;
    }

    public Solution selectSolution(){
        ArrayList<Solution> group = new ArrayList<>();

        Random random = new Random();

        group.add(population[random.nextInt(population.length)]);
        do{
            Solution sol = population[random.nextInt(population.length)];
            if(!group.contains(sol))
                group.add(sol);
        }while(group.size() < size);

        Solution bestSol = group.get(0);
        for(int i =1; i < group.size(); i++){
            if(group.get(i).fitness < bestSol.fitness)
                bestSol = group.get(i);
        }

        return bestSol;
    }

}
