# Graph Colouring

This program can check whether vertices of a finite graph can be painted with finite number of colours, and find a solution if it exists. It uses Java API of [Z3](https://github.com/Z3Prover/z3). This program reads input from a file with the name `input.txt` in the same directory as the program, and outputs the result to a file with the name `output.txt` in the same directory as `input.txt`. 

# Explanation of input and output:
We use number to represent vertices and colours. For example,   the set of vertices is $\{1, 2, ...,V\}$ and the set of colours is $\{1, 2, ...,C\}$.

The `input.txt` should have following format  
$$V\ C$$

$$v_{1}\  w_{1}$$

$$v_{2} \ w_{2}$$

$$ ...$$

$$v_{i} \ w_{i}$$
where each line contains two positive integers. For the first line, V is the number of vertices, and M is the number of colours. Each $v$ and $w$ in a line are adjacent vertices. So, $v_{1}\  w_{1}$ in second line represents edge $(v_{1},  w_{1})$.

The output has following format
$$v_{1}\  c_{1}$$

$$v_{2} \ c_{2}$$

$$ ...$$

$$v_{i} \ c_{i}$$
Where $v$ is a vertex and $c$ is the corresponding colour.

If no solution exists, the output file will contain `No Solution`.