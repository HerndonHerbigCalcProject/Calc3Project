# Project
Calc 3 project by JT Herndon and Alex Herbig
To see full documentation, open documentation/index.html in a web browser.

All basic linear algebra functionality can be found in Matrix.java
Compile
------------
First thing to do is compile, so in a console, type in
~~~sh
$ javac *.java
~~~
Part 1
------------
To view Pascal solutions for part 1 (The thing going from 2 to 12) type in
~~~sh
$ java Part1
~~~
The factorizations and solutions with errors should show up in the output, the error results should be given in PascalResults.csv, and can be opened in excel.
####Test Cases
If you have a file test_case.dat, you can type in
~~~sh
$ java Part1 test_case.dat
~~~
This can accept either a file with a square matrix (n by n entries), or an augmented matrix system (n by n + 1 entries), and will display what is asked of the project descriptions / rubric.
Part 2
------------
For part 2, we made the output in csv format so it could be opened in excel. To execute, type in
~~~ sh
$ java Part2 > part2results.csv
~~~
and then part2results.csv can be opened in excel (or whatever filename you picked). Side note: x01, x02, x03 are entries of x0, xN1j, xN2j, xN3j are entries of the resulting vector from jacobi, xN1g, xN2g, xN3g are entries of gauss-seidal result, and Nj and Ng are number of iterations required for Jacobi and Gauss-Seidal, respectively.
Part 3
------------
Just like part 2, we made the output in csv format. Type in 
~~~ sh
$ java Part3 > part3results.csv
~~~
to have the results of the randomly generated matrices.
####Test Cases
For the testcases, I have no idea how you're formatting it with the matrix, vector, and integer, so I'll leave it to you to manually test our code.
