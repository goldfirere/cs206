---
title: CS 206 Style Guide
---

<div id="header">

| **CS 206 Style Guide**
| Prof. Richard Eisenberg
| Spring 2018

</div>

\$navbar\$

Code Formatting Standards and Guidelines
========================================

All organizations and companies have specific conventions for formatting code.
While these formatting rules may vary from place to place, they are essential
for making your code readable and for enabling others to understand, use, and
modify your code in the future.

While I do not expect anyone (including myself) to follow all the rules,
this [*very* detailed guide](https://www.doc.ic.ac.uk/lab/cplus/cstyle.html) was 
used as an inspiration (adapted for Java) for the notes below.

Intentional coding
------------------

 * Every line of code should have an effect on your program. Lines or sections
   of code that have no effect are poor style.
 * Once we have learned about them, use `public static final` constants to avoid
   so-called "magic numbers" -- numerical values that must be chosen just so in
   order for your program to work, but are impossible to understand for a reader.
 * Do not copy-and-paste code, only to change a few details. Instead, use a method
   or other abstraction.

Scoping and encapsulation
-------------------------

 * Declare variables in the smallest scope possible. That is, prefer local variables
   over fields.
 * Never use `public` non-`final` fields. Use `private` fields and accessor methods.
 * Use abstraction (interfaces, classes, etc.) to model the real-world ideas represented
   in your program.

Naming Conventions
------------------

 * Use meaningful names!  For example, if your program needs a variable to represent the radius of a circle, call it `radius`, *not* `r` and *not* `rad`.
 * Use single letter variables for simple loop indices *only*.
 * The use of very obvious, common, meaningful abbreviations is permitted. For example, "number" can be abbreviated as "num" as in `numStudents`.
 * Variable, field, and method names in Java generally are written in camelCase, starting with a lower-case letter and
 putting the first letter of subsequent words in uppercase.
 * Class names are written in upper CamelCase, starting with a capital letter.
 * Constants (`static final` fields) are written in `ALL_CAPS`.

Whitespace
----------

The most-readable programs are written with prudent use of whitespace
(including both blank lines and spaces).

 * Use blank lines to separate major parts of a source file or method. These are like paragraph breaks in English writing.
 * After every `{`, indent by at least 2 spaces until the matching `}`.
 * Separate an operator from its operands by spaces.
 * There should never be a need for 2 blank lines in a row or two spaces in a row.

File header comments
--------------------

Every source code file should contain a header comment that describes the
contents of the file and other pertinent information. It must include the
following information:

  * Your name
  * The file name
  * A description of the contents of the file

For example:

```java
/* Name:    Richard Eisenberg
 * File:    Main.java
 * Desc:
 *
 * The main driver program for project 4.
 *
 * This program reads the file specified as the first command line
 * argument, counts the number of words, spaces, and characters and
 * displays the results in the format specified in the project description.
 *
 */
```

Variable comments
-----------------

*All* fields must be commented. Most local variables should be commented, too.

Method comments
-----------------

*All* methods must be commented. The comments should explain what the method
does, what its parameters are (not just their types!), and what it returns. Another
way to consider method comments is to imagine a contract: "If the caller of the method
does xyz, then the method will do abc." The "xyz" there are called pre-conditions,
and the "abc" there are called the post-conditions.

In-Line Comments
----------------

You should strive for your code to be self-explanatory. However, it is inevitable
that some lines of code are more intricate. In these cases, a comment describing
the code is well-advised. The comment should *not* simply translate the code to
English, but should explain what's really going on. For example:

```java
// Unhelpful comment:
starSides = 5; // set starSides to 5

// Helpful comment:
starSides = 5; // reset starSides to original value
```

Well-structured code will be broken into logical sections that each perform a
simple task. Each of these sections of code (typically starting with an `if`
statement or a loop) should be documented.

An in-line comment too long to appear to the right of your code
appears above the code to which it applies and is indented to the same level as the code. For example:

```java
// increment all the odd values in the array
for (int i = 0; i < n; i++) {
    // add 1 only to the odd values
    if (array[i] % 2 == 1) {
        array[i] = array[i] + 1;
    }
}
```

Indentation Styles
------------------

Choose one of the two styles and use it consistently (note how the braces
are placed):

```java
if (condition) {                            if (condition)
    ...                                     {                    
} else if (condition) {                         ...
    ...                                     }
} else {                                    else if (condition)
    ...                                     {
}                                               ...
                                            }
                                            else
                                            {
                                                ...
                                            }


for (loop control expressions) {            for (loop control expressions)
    ...                                     {
}                                               ...
                                            }


while (condition) {                         while (condition)
   ...                                      {
}                                               ...
                                            }


do {                                        do
   ...                                      {
} while (condition);                            ...
                                            } while (condition);
                                           

switch (variable) {                         switch (variable)
   case constant1:   ...                    {
        break;                                  case constant1:   ...
   case constant2:   ...                             break;
        break;                                  case constant2:   ...
   case default:     ...                             break;
}                                               case default:     ...
                                            }
```
