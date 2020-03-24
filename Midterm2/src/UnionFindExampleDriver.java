/*
TITLE: ZOOKEEPER
AUTHOR: COLE HORVAT
DATE: 3/22/2020
*/

import java.util.*;


/**
 *
 * @author John
 */
public class UnionFindExampleDriver {

    /** This is your UnionFind generic class that does Union-Find
     * (part I of the assignment). If you're not comfortable using
     * your own Union-Find code then you can use the one provided
     * available in moodle (recommended to use yours if it works */

    //GLOBAL VARIABLES
    public static UnionFind<String> uf;

    public static Integer currentCellId;

    public static ArrayList<String> animalInSets = new ArrayList<>();

    public static int[] animalNumber = new int[25*25];

    public static String animalShort;
    public static String animal;

    public static String[] occupyingAnimals;
    public static String[] animalNamesLong = new String[25*25];



    /** Class that contains code to produce the maze/grid randomly
     *  as well as code to colorize the regions
     */
    public static Maze grid;

    public static Scanner sc = new Scanner(System.in);
    public static Random ran = new Random();

    public static void main(String[] args)
    {
        boolean isInitial = true;

        grid = new Maze(25,25, 0.5);

        uf = new UnionFind<String>();

        int[] setSizes = new int[25*25];

        while(true)
        {
            /* This is where you must create the code as discussed in class to find the regions */
            findRegions();
            occupyingAnimals = new String[uf.sets.size()];


            if(isInitial)
            {
                for(int i = 0; i < uf.sets.size(); i++)
                {
                    setSizes[i] = uf.sets.get(i).size();
                }
            }


            isInitial = false;

            System.out.println("**************************************************");
            System.out.println("*               Z O O  K E E P E R               *");
            System.out.println("**************************************************");
            System.out.println("\n1. Show map of ZOO");
            System.out.println("2. Place a ZOO animal in the map");
            System.out.println("3. Show region capacity");
            System.out.println("4. Move and animal to another enclosure");
            System.out.println("5. Exit");

            String userInput = sc.nextLine();

            if(userInput.equals("1"))
                grid.drawMazeSetsInColor(uf);
            else if(userInput.equals("2"))
                placeAnimal(setSizes);
            else if(userInput.equals("3"))
                showRegionCap(setSizes);
            else if(userInput.equals("4"))
                moveAnimal(setSizes);
            else if(userInput.equals("5"))
                System.exit(1);
            else
                System.out.println("Detected incorrect input. Please try again");
        }
    }

    public static void findRegions()
    {
        /**
         * Your code goes here. Program the algorithm as discussed in class
         * and described in class slides.
         */

        for(int row = 0; row < 25; row++)
        {
            for(int col = 0; col < 25; col++)
            {
                //If selected area is NOT an obstruction
                if(!grid.maze[row][col])
                {
                    //Add element
                    String cellName = row + "," + col;
                    uf.add(cellName);


                    //Get current cell id#
                    currentCellId = null;
                    try
                    {
                        currentCellId = uf.find(cellName);
                        //System.out.println("CURRENT cell: " + uf.get(currentCellId) + "\n");
                    }
                    catch(NullPointerException e)
                    {

                    }

                    //System.out.println("CURRENT cell id: " + currentCellId + "\n");

                    //LEFT CELL IDENTIFICATION
                    if(col > 0)
                    {
                        //Find the index of left cell
                        //System.out.println(col);
                        Integer leftCellId = null;
                        try
                        {
                            leftCellId = uf.find(row + "," + (col-1));
                        }
                        catch(NullPointerException e)
                        {

                        }

                        //System.out.println("LEFT cell id: " + leftCellId + "\n");
                        try
                        {
                            if(!leftCellId.equals(null))
                            {

                                //System.out.println("Left UNION\nRow: " + row + "\nColumn: " + col + "\n");
                                uf.union(currentCellId, leftCellId);
                            }
                        }
                        catch(NullPointerException e)
                        {

                        }

                    }

                    currentCellId = uf.find(cellName);
                    //UP CELL IDENTIFICATION
                    if(row > 0)
                    {
                        //Find the index of up cell
                        Integer upCellId = null;
                        try
                        {
                            upCellId = uf.find((row-1) + "," + (col));
                        }
                        catch(NullPointerException e)
                        {

                        }
                        //Set upCell = uf.get(upCellId);
                        //System.out.println("UP cell id: " + upCellId + "\n");
                        //System.out.println("UP cell: " + uf.get(upCellId) + "\n");

                        try
                        {
                            if(!upCellId.equals(null))
                            {
                                //System.out.println("Up UNION\nRow: " + row + "\nColumn: " + col + "\n");
                                uf.union(currentCellId, upCellId);

                            }
                        }
                        catch(NullPointerException e)
                        {

                        }

                    }

                }
            }
        }

    }

    //PLACES AN ANIMAL IN A REGION
    public static void placeAnimal(int[] setSizes)
    {
        //VARIABLES
        int animalNum;
        HashSet selectedSet;
        int animalRegion;
        String placeHolder = "-";

        //USER INPUT
        System.out.println("What's the name of the animal being placed in the zoo? >>");
        animal = sc.nextLine();
        animal = animal.toUpperCase();

        animalShort = animal.substring(0,3);

        System.out.println("How many " + animal + "s are you housing? >>");
        animalNum = Integer.parseInt(sc.nextLine());

        //REGION MENU
        System.out.println("#REGION     CAPACITY    ANIMAL");
        //System.out.println(uf.sets.size());


        for(int i = 0; i < uf.sets.size(); i++)
        {
            //System.out.println(setSizes[i]);
            try
            {
                if (setSizes[i] >= animalNum) {

                    if(animalNamesLong[i] != null)
                    {
                        System.out.println("#" + i + "            " + setSizes[i] + "           " + animalNamesLong[i]);
                    }
                    else
                    {
                        //System.out.println("VALID");
                        System.out.println("#" + i + "            " + setSizes[i] + "           -");
                    }

                }

            } catch (IndexOutOfBoundsException e) {

            }

        }

        //USER INPUT CONTD.
        System.out.println("Place " + animalNum + " " + animal + "s in what region? >>");
        animalRegion = Integer.parseInt(sc.nextLine());

        //CHECK IF SELECTED REGION IS TOO SMALL
        if(animalNum > setSizes[animalRegion])
        {
            System.out.println("Selected region is too small to house these animals");
            return;
        }

        occupyingAnimals[animalRegion] = animalShort;
        animalNumber[animalRegion] = animalNum;

        selectedSet = uf.sets.get(animalRegion);

        //CHECK IF SELECTED REGION IS ALREADY OCCUPIED
        if(Maze.animalsNames[animalRegion] != null)
        {
            System.out.println("An animal is already occupying region #" + animalRegion);
            return;
        }


        //ADD ANIMAL LOCATIONS TO THE "IN_SETS" SET
        while(animalNum > 0)
        {
            Iterator animalCellNames = selectedSet.iterator();
            while(animalCellNames.hasNext())
            {
                double randomChance = ran.nextInt(100);
                String animalCellName = (String) animalCellNames.next();

                if(randomChance < 30 && !(animalInSets.contains(animalCellName)))
                {
                    animalInSets.add(animalCellName);
                    animalNum--;
                }

                if(animalNum <= 0)
                {
                    break;
                }
            }
        }

        animalNamesLong[animalRegion] = animal;
        Maze.animalsNames[animalRegion] = animalShort;

        //System.out.println("Finished");
    }

    //SHOWS THE CAPACITY FOR EACH REGION
    public static void showRegionCap(int[] setSizes)
    {
        //VARIABLES
        int animalCap;

        //USER INPUT
        System.out.println("How many spots do you need? >>");
        animalCap = Integer.parseInt(sc.nextLine());

        //REGION MENU
        System.out.println("#REGION     CAPACITY    ANIMAL");

        for(int i = 0; i < uf.sets.size(); i++)
        {
            //System.out.println(setSizes[i]);
            try
            {
                if (setSizes[i] >= animalCap) {

                    if(animalNamesLong[i] != null)
                    {
                        System.out.println("#" + i + "            " + setSizes[i] + "           " + animalNamesLong[i]);
                    }
                    else
                    {
                        //System.out.println("VALID");
                        System.out.println("#" + i + "            " + setSizes[i] + "           -");
                    }

                }

            } catch (IndexOutOfBoundsException e) {

            }

        }
    }

    //MOVES AN ANIMAL FROM ONE REGION TO ANOTHER
    public static void moveAnimal(int[] setSizes)
    {
        //VARIABLES
        int moveRegion;
        int newRegion;

        //USER INPUT
        System.out.println("Which region# do you want to move? >>");
        moveRegion = Integer.parseInt(sc.nextLine());
        int animalAmount = animalNumber[moveRegion];


        //CHECKS TO SEE IF REGION BEING MOVED HAS ANIMALS IN IT
        if(Maze.animalsNames[moveRegion] != null)
        {
            System.out.println("Region #" + moveRegion + " currently housing " + animalNamesLong[moveRegion] + "s");
        }
        else
        {
            System.out.println("Region #" + moveRegion + " currently houses no animals");
            return;
        }

        System.out.println("You need an area capable of holding " + animalAmount + " of them");

        //REGION MENU
        System.out.println("#REGION     CAPACITY    ANIMAL");
        for(int j = 0; j < setSizes.length; j++)
        {
            try
            {
                if (setSizes[j] >= animalAmount)
                {
                    if(Maze.animalsNames[j] != null)
                    {
                        System.out.println("#" + j + "            " + setSizes[j] + "           " + Maze.animalsNames[j]);
                    }
                    else
                        {
                            System.out.println("#" + j + "            " + setSizes[j] + "           -");
                        }
                }

            }
            catch (IndexOutOfBoundsException e) {

            }
        }

            //GET NEW REGION
            System.out.println("Move them to what region#? >>");
            newRegion = Integer.parseInt(sc.nextLine());

            //CHECK IF NEW REGION IS TOO SMALL FOR NEW ANIMALS
            if(animalAmount > setSizes[newRegion])
            {
                System.out.println("Selected region is too small to house these animals");
                return;
            }

            HashSet moveSet = uf.sets.get(moveRegion);
            HashSet newSet = uf.sets.get(newRegion);

            Iterator moveSetNames = moveSet.iterator();


        //REMOVE ANIMALS FROM OLD REGION
        while(moveSetNames.hasNext())
        {
            String moveSetName = (String) moveSetNames.next();

            if(animalInSets.contains(moveSetName))
            {
                animalInSets.remove(moveSetName);
            }
        }

        //ADD ANIMALS TO NEW REGION
        while(animalAmount > 0)
        {
            Iterator newSetNames = newSet.iterator();
                while(newSetNames.hasNext())
                {
                    double randomChance = ran.nextInt(100);
                    String newSetName = (String) newSetNames.next();

                    if(randomChance < 30 && !(animalInSets.contains(newSetName)))
                    {
                        animalInSets.add(newSetName);
                        animalAmount--;
                    }

                    if(animalAmount <= 0)
                    {
                        break;
                    }
                }
            }
            Maze.animalsNames[moveRegion] = null;
            Maze.animalsNames[newRegion] = animalShort;
            animalNamesLong[moveRegion] = null;
            animalNamesLong[newRegion] = animal;
        }
}

