TextExcel
=========
A wonderful APCS project in Java. Basically ed for a spreadsheet

Grand Scheme
============
*Feel free to do PRs against this document if you feel something should be different*

***Remember, avoid stupid getter/setters, instead be clever!***

Spreadsheet
-----------
`Spreadsheet` contains a matrix of `Value`s that represent it's cells  
Can obtain the `Value` at a index with `valueAt`  

Value hierarchy
---------------
`Value`: base class, all values can be `resolve()`'d in the context of some `Spreadsheet`, which by default will simpily return the value itself. In addition all `Value`s should have a useful `toString` method  
`Number`: a decimal value, represented by a double  
`StringValue`: a string value  
`CellReference`: a value that holds a cell's row and column, and when it is `resolve()`'d it returns the value in that cell  
`DeferredExpression`: a value that, when resolved, returns the evaluation of it's child expression  

Expression hierarchy
--------------------
`Expression`: base class, all expressions can be `evaluate()`'d in the context of some `Spreadsheet`  
`ValueExpression`: a expression that represents a basic value (number, string, cellref or deferred expr)  
`AddExpression`: a expression that evaluates to the addition of the evaluation of its two arguments  
`SubExpression`: same as `AddExpression` with subtraction  
`Term`: another base class  
`MulTerm`: a expression that evaluates to the multiplication of the evalution of its two arguments  
`DivTerm`: same as `MulTerm` with division  
`AssignmentExpression`: a expression that assigns the value on the left of a `=` to the value on the right  

Parser
------
`Parser` class will have a public method that takes a `String` and returns a `Expression` named `parse`  


Main
----
`Main` class will run the basic command line, which will read a line of input, see if it is a special command (`print` or `quit`), then invoke either the special command or parse the line and evaluate the expression, printing the resulting value
