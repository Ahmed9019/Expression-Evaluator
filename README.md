# Expression Evaluator (Infix to Postfix Evaluator)

You are required to write a program that takes a symbolic infix expression as input, converts it to postfix notation, evaluates the postfix expression and outputs both; the resulting postfix expression and *its value. * <br />

Note: You should use the stack implementation you did in the last part for any stack data structure needed in the program.

## Input Format

First line has Infix expression. <br />
The next 3 lines have the values for "a", "b", and "c", even if not all of them exist in the equation.

## Constraints

All operands are integer values. <br />
There are at most 3 variables (a, b, and c) in the expression. <br />
Operators could be any of the following: +, -, *, /, and ^. <br />
Expressions can contain parentheses: "(" and ")". <br />
Having 2 consective minuses is possible, where --a = a. <br />
In case of division you should perform an integer division, where "5/2 = 2". <br />
Assume no division by zero will happen.

## Output Format

The first line should print the corresponding postfix expression. <br />
The second line should print the integer value of the result. <br />
Incase the expression is invalid, the output will be Error. <br />

## Input and Output Samples

|N| Input | Output |
|--|-------|--------|
|1|--a--b<br>a=1<br>b=-10<br>c=9 |ab+<br>-9|
|2|-(a+b)<br>a=8<br>b=-2<br>c=9|ab+-<br>-6|
|3|--a^b<br>a=9<br>b=3<br>c=3|ab^<br>729|
|4|a*b*c<br>a=-2<br>b=8<br>c=-3|ab*c*<br>48|
