import java.util.Random;

public class Roulette {

    Solution[] population;
    Entry[] entries;

    double accumulatedWeight;

    private class Entry {
        double weight;
        Solution solution;
    }

    public Roulette(Solution[] population) {
        this.population = population;

        entries = new Entry[population.length];
        createEntries();
    }

    public Solution selectSolution(){
        Random random = new Random();
        double weight = random.nextDouble() * accumulatedWeight;
        double sumWeight = 0;
        for(int i = 0; i < population.length; i++){
            sumWeight += entries[i].weight;
            if(sumWeight >= weight)
                return entries[i].solution;
        }

        return null;
    }

    private void createEntries(){
        accumulatedWeight = 0;
        double biggestFitness = population[0].fitness;
        double smallestFitness = population[0].fitness;
        for(int i = 1; i < population.length; i++){
            if(population[i].fitness > biggestFitness)
                biggestFitness = population[i].fitness;
            if(population[i].fitness < smallestFitness)
                smallestFitness = population[i].fitness;
        }

        double startingFitness = biggestFitness + smallestFitness;

        double weight;
        for(int i = 0; i < population.length; i++){
            weight = startingFitness - population[i].fitness;
            accumulatedWeight += weight;
            Entry entry = new Entry();
            entry.solution = population[i];
            entry.weight = weight;
            entries[i] = entry;
        }
    }
}
