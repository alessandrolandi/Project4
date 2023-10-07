package project4;

import java.util.ArrayList;

/*
 * The puzzle uses an array of positive integers.
 * The objective is to find a path from index zero to the last index in the array.
 * At each step we need to know the distance to be traveled and the direction.
 * Each entry in the array is a number indicating the distance
 * to be traveled on this particular leg of the path.
 * The player (your program) needs to decide the direction (if the move should be made to the right or to the left).
 * The puzzles can have more than one solution.
 * This program finds and prints all of the solutions given the objective.
 *
 * @author Alessandro Landi
 *
 */

public class WayFinder {

    /*
     * Splits the given line of a pipe-delimited file according to | characters.
     * @author Joanna Klukowska
     * @param textLine	a line of text to be parsed
     * @return the array containing words (or empty strings) from between | characters
     */

    public static String [] splitInputLine(String textLine){

        if (textLine == null ) return null;

        String [] entries = null;

        entries = textLine.split("\\|");

        return entries;
    }

    public static void main(String [] vector) {

        /* checks to see if the puzzle has valid values i.e. the puzzle values have to be
         * positive integers and less than 99. Ends the program and prints an error statement
         * if puzzle values are not valid
         */

        for(int z = 0; z < vector.length; z++){
            if(Integer.parseInt(vector[z]) < 0 ){
                System.err.println("ERROR: the puzzle values have to be positive integers.");
                return;
            }
            if(Integer.parseInt(vector[z]) > 99 ){
                System.err.println("ERROR: the puzzle values cannot be greater than 99.");
                return;
            }
        }

        /* checks to see if the puzzle's last value is 0
         * Ends the program and prints an error statement
         * if puzzle's last value is not 0
         */

        if(Integer.parseInt(vector[vector.length - 1]) != 0 ){
            System.err.println("ERROR: the last value in the puzzle has to be zero.");
            return;
        }

        ArrayList<String> path = new ArrayList<String>();

        //runs the recursive loop to find all the paths
        path = findPaths( vector, "", 0, path);


        //prints a statement if there are no paths through the puzzle.
        if(path.size() == 0){
            System.out.println("No way through this puzzle.");
        }


        // Add spaces for formatting at each index

        for(int x = 0; x < vector.length; x++){
            vector[x] = vector[x] + " ";
        }

        //Iterates through all the paths and prints out the desired format of the path solution at each step

        if(Integer.parseInt(vector[0].trim()) != 0){
            for(int j = path.size()-1; j >= 0; j--){
                String[] temp = path.get(j).split(",");
                System.out.println();
                for(int w = 1; w < temp.length - 1; w+=1){
                    System.out.print("[ ");
                    String [] copy = vector.clone();
                    int k = Integer.parseInt(temp[w].substring(0, temp[w].length() - 1));

                    copy[k] = vector[k].trim() + temp[w].substring(temp[w].length()-1);
                    for(int y = 0; y < copy.length - 1; y++){
                        System.out.print(copy[y] + ",  ");
                    }

                    System.out.println("0 ]");

                }

            }

        } else{
            // prints the unique case when the puzzle is just 0
            System.out.println("[ 0 ]");
        }



        System.out.println();
        // different print statements given 1 or more path solutions
        if(path.size() != 0){
            if(path.size() == 1){
                System.out.println("There is " + path.size() + " way through the puzzle.");
            } else{
                System.out.println("There are " + path.size() + " ways through the puzzle.");
            }
        }

        System.out.println();

    }

    //recursive algorithm to find all the path solutions

    public static ArrayList<String> findPaths(String [] data, String solution, int position, ArrayList<String> all_paths){
        int flag = 1;
        String[] test = solution.split(",");

        //flag to stop any extra unnecessary steps 

        if(solution.length() > 0){
            for (int i = 1; i < test.length; i+=1){
                int c = Integer.parseInt(test[i].substring(0, test[i].length()-1));
                if( position == c){
                    flag = 0;
                }
            }
        }
        // checks to see if the position is within bounds and if the point is not 0 at any point that is not the end.
        if( position >= 0 && position < data.length && flag == 1 && (Integer.parseInt(data[position]) != 0 || position == data.length-1)){
            solution = solution + "," + position;
            if(Integer.parseInt(data[position]) == 0){
                all_paths.add(solution);
            }
            else{
                int stepsize = Integer.parseInt(data[position]);
                findPaths( data,  solution + "R", position + stepsize, all_paths);
                findPaths( data, solution + "L", position - stepsize, all_paths);

            }

        }

        return all_paths;

    }



}
