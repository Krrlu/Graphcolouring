package Graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

public class GraphColoring {
    static BoolExpr[][] boolvar;
    static List<Integer>[] edges;

    public static void main(String[] args) throws IOException{
        // Create a new context and solver
        Context ctx = new Context();
        Solver solver = ctx.mkSolver();
        String inputPath = System.getProperty("user.dir") + "//input.txt";

         // Read from input.txt
        readtxt(inputPath);

        // Initialize all variables in boolvar
        for (int i = 1; i < boolvar.length; i++) {
            for (int j = 1; j < boolvar[i].length; j++) {
                boolvar [i][j] = ctx.mkBoolConst(i+","+j);
            }
        }

        // Add first constrain that each vertex has color
        coloring(ctx, solver);

        // Add second constrain that each vertex has only one color
        onecolor(ctx, solver);

        // Add third constrain that adjancent vertices have fifferent colors
        diffcolor(ctx, solver);

        //write to a output.txt
        writetxt(solver, inputPath.substring(0, inputPath.length()- 9) + "output.txt");

    }

    public static void coloring(Context ctx, Solver solver){
        for (int i = 1; i < boolvar.length; i++) { // i correspond to vertex
            BoolExpr f = boolvar[i][1]; // Initializing
            for (int j = 2; j < boolvar[i].length; j++) {
                f = ctx.mkOr(f,boolvar[i][j]);
            }
            solver.add(f);
        }
        
    }

    public static void onecolor(Context ctx, Solver solver){
        for (int i = 1; i < boolvar.length; i++) {// i correspond to vertex
            for (int j = 1; j < boolvar[i].length; j++) { // j correspond to color
               for (int k = 1; k < boolvar[i].length; k++) { // k correspond to color
                if(j == k) continue;
                BoolExpr f = ctx.mkAnd(boolvar[i][j], boolvar[i][k]);
                f = ctx.mkNot(f);
                solver.add(f);
               }
            }
        } 
    }

    public static void diffcolor(Context ctx, Solver solver){
        for (int i = 1; i < edges.length; i++) { // i correspond to vertex
            if(edges[i] == null) continue;
              for (int j : edges[i]) { // j correspond to vertex that adjacent to i
                if(j == i) continue; // case where there exist edge between vertex and it self
                for (int k = 1; k < boolvar[i].length; k++) { // k correspond to color
                    BoolExpr f = ctx.mkAnd(boolvar[i][k], boolvar[j][k]);
                    f = ctx.mkNot(f);
                    solver.add(f);
              }
            }
        } 
    }


    public static void readtxt(String path) throws IOException{
        BufferedReader b = new BufferedReader(new FileReader(path));
        String str;

        // trim remove leading space, then obtain the string of integer by spilit whites space
        String [] data = b.readLine().trim().split(" +");
        boolvar = new BoolExpr[Integer.parseInt(data[0])+1][Integer.parseInt(data[1])+1];

        edges = new ArrayList[Integer.parseInt(data[0])+1]; // create the array
        while((str = b.readLine()) != null && str.length() != 0){
            data = str.trim().split(" +");
            if(data.length != 2) continue;
           
            // Initialize arraylist
           if(edges[Integer.parseInt(data[0])] == null){
            edges[Integer.parseInt(data[0])] = new ArrayList<Integer>();
        }
           edges[Integer.parseInt(data[0])].add(Integer.parseInt(data[1]));
        }
        b.close();
    }

    public static void writetxt(Solver solver, String path) throws IOException{
        BufferedWriter w = new BufferedWriter(new FileWriter(path));

        if(solver.check() == Status.UNSATISFIABLE){
            w.write("No Solution");
        }else{
        Model model = solver.getModel();
        for (int i = 1; i < boolvar.length; i++) {
            for (int j = 1; j < boolvar[i].length; j++) {
                if(model.getConstInterp(boolvar[i][j]).toString().compareTo("true")==0){
                    w.write(i + " " + j);
                    w.newLine();
                }
            }
        }
    }
        w.flush();
        w.close();
    }
}
