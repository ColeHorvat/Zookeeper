/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author John
 */
public class Maze {

    public boolean[][] maze;

    public static String[] animalsNames = new String[25*25];

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String BG_ANSI_BLACK = "\u001B[40m";
    public static final String BG_ANSI_RED = "\u001B[41m";
    public static final String BG_ANSI_GREEN = "\u001B[42m";
    public static final String BG_ANSI_YELLOW = "\u001B[43m";
    public static final String BG_ANSI_BLUE = "\u001B[44m";
    public static final String BG_ANSI_PURPLE = "\u001B[45m";
    public static final String BG_ANSI_CYAN = "\u001B[46m";
    public static final String BG_ANSI_WHITE = "\u001B[47m";

    public static final String[] colors = new String[] {
            BG_ANSI_RED,
            BG_ANSI_GREEN,
            BG_ANSI_YELLOW,
            BG_ANSI_BLUE,
            BG_ANSI_PURPLE,
            BG_ANSI_CYAN } ;


    public Maze(int rows, int cols, double prob)
    {
        maze = new boolean[rows][cols];
        create(prob);
    }

    private void create(double prob)
    {
        for(int i=0; i<maze.length; i++)
            for(int j=0; j<maze[0].length; j++)
                if ( Math.random()<prob ) maze[i][j] = true;
    }

    public void drawMaze()
    {
        for(int i=0; i<maze.length; i++)
        {
            for(int j=0; j<maze[0].length; j++)
                System.out.print( maze[i][j] ? "#  " : ".  ");

            System.out.println();
        }
    }


    public void drawMazeSets(UnionFind uf)
    {
        for(int i=0; i<maze.length; i++)
        {
            for(int j=0; j<maze[0].length; j++)
            {

                Integer in_set = uf.find(i+","+j);
                System.out.printf( "%-3s", in_set==null ? "#" : in_set);
            }
            System.out.println();
        }
    }


    public void drawMazeSetsInColor(UnionFind uf)
    {
        String[] use_color = new String[ maze.length * maze[0].length];
        String color;
        ArrayList<Integer> inSetNum = new ArrayList<Integer>();
        int color_loop = 0;

        for(int i=0; i<maze.length; i++)
        {
            for(int j=0; j<maze[0].length; j++)
            {
                Integer in_set = uf.find(i+","+j);

                if ( in_set != null )
                {
                    color = use_color[in_set];
                    if ( color == null )
                    {
                        color = colors[ color_loop++ % colors.length ];
                        use_color[in_set] = color;
                    }

                } else color = "";

                if(UnionFindExampleDriver.animalInSets.contains(i+","+j))
                {
                    //System.out.println("ANIMAL NAME");
                    System.out.printf( color + "%s" + ANSI_RESET, animalsNames[in_set]);
                }
                //System.out.printf( color + "%-3s" + ANSI_RESET, in_set==null ? "#" : in_set);
                else if(in_set == null)
                {
                    System.out.printf( color + "%-3s" + ANSI_RESET, "#");
                }
                else if(inSetNum.contains(in_set))
                    System.out.printf( color + "%-3s" + ANSI_RESET, ".");
                else
                {
                    System.out.printf( color + "%-3s" + ANSI_RESET, in_set);
                    inSetNum.add(in_set);
                }

            }
            System.out.println();
        }
    }

}
