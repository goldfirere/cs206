---
title: "CS 206: Introduction to Data Structures"
---

<div id="header">

| **CS 206: Introduction to Data Structures**
| Prof. Richard Eisenberg
| Spring 2018
| Bryn Mawr College

</div>

\$navbar\$

General information
===================

<div id="info_table">

----------------------         -----------------------------------------------------------------------------------------------------------------------------------------
Instructor:                    [Richard Eisenberg](http://cs.brynmawr.edu/~rae)
Email:                         `rae@cs.brynmawr.edu`
Office Phone:                  610-526-5061
Home Phone (emergencies only): 484-344-5924
Cell Phone (emergencies only): 201-575-6474 (no texts, please)
Office:                        Park 204
Office Hours:                  Wednesdays 10:30am-12:30pm
                               If these don't work, do not fret. Email instead.
<span class="strut" />
Lecture:                       TR 9:55-11:15am
Lecture Room:                  Park 336
Lecture Recordings:            at Tegrity: access via [Moodle](https://moodle.brynmawr.edu/course/view.php?id=1073); look for link on right side of screen.
Lab:                           W 2:40-4:00pm, Park 231
Website:                       <http://cs.brynmawr.edu/cs206>
GitHub Repo:                   <https://github.com/goldfirere/cs206>
Piazza Q&A Forum:              <https://piazza.com/brynmawr/spring2018/cs206/home>
TAs:                           TBD
----------------------         -----------------------------------------------------------------------------------------------------------------------------------------

</div>
<!--
<div id="ta_hours">

--------------------           ------------------------------     ----------------------
Time                           TA                                 Location
Mondays 6-8pm                  Kennedy Ellison                    Park 231
Mondays 7-9pm                  Ruby Malusa                        Park 231
Mondays 8-10pm                 Jocelyn Dunkley                    **Hilles 110** at Haverford
Tuesdays 6-8pm                 Kellie Dinh                        Park 231
Tuesdays 7-9pm                 Sonya Fucci                        Park 231
Wednesdays 6-8pm               Kellie Dinh                        Park 231
Wednesdays 7-9pm               Jocelyn Dunkley                    Park 231
Thursdays 6-8pm                Kennedy Ellison                    Park 231
Sundays 6-8pm                  Ruby Malusa                        Park 231
Sundays 7-9pm                  Sonya Fucci                        Park 231
--------------------           ------------------------------     ----------------------
</div>
-->

Goals of course
---------------

<div id="goals">

By the end of this course, you will be able to...

* code fluently in Java
* choose among a variety of data structures for a given task, taking
  both space and time concerns into account
* compare and contrast data structures and algorithms
* implement key data structures efficiently

During the course, you will...

* code
* debug
* have fun
* work with other students every class

</div>

This is a second course in computer science, focusing on elementary data structures
and algorithms. A data structure is a way of organizing information in a computer
for easy use. A standard dictionary is a real-life example of an efficient data
structure: by being alphabetized, a dictionary makes looking up the definition of
a given word easy. In contrast, a standard dictionary is very inefficient if you want to
look up a word from its definition. In the same way, data structures in computer
science can be efficient for one task, but poor for another.

This course will cover the key data structures in computer science, including
standard arrays, expandable arrays, linked lists (singly linked, doubly linked,
and circular), binary search trees, and hash tables. These concrete data structures
will be used to power abstract data types such as lists, queues, stacks, sets,
and maps. Along with these data structures, we will discuss the algorithms
(procedures) for operating with them.

In order to understand data structures well, we need to have a way of comparing
one to another. Specifically, we want to know which operations are "slow" and
which are "fast", so we'll need a way of defining "slow" and "fast". We use
*asymptotic complexity analysis* to do this, and we will learn how to use this
analysis to classify algorithms and data structures. We will use sorting algorithms
as a case study in algorithmic comparison.

All semester, students will complete rigorous coding assignments in Java. Java
is a very widely used programming language. It is object-oriented, meaning that
it has a powerful type system, capable of separating out the notion of concrete
data structure from abstract data type. By the end of the semester, students
will have gained considerable experience programming in Java.

Course Philosophy
-----------------

In order to learn any programming skill, you must simply *do* it. Reading a book
or watching me code will not help you get better. This course is thus
*programming-intensive*. You will be expected to spend a significant amount of
time weekly (6-8 hours) outside of class programming to complete the homework
assignments. If you run into a snag, the programming burden may prove to be
even higher. Of course, programming is fun, so the time should fly by.

Much class time in CS206 will be spent working with peers to solve small
programming problems, conduct peer reviews, and complete other exercises.
There will also be lecture components.

<img id="textbook" src="images/textbook.jpg" />

Materials
=========

<div id="materials">

The textbook for this course is:

* **Data Structures: Abstraction and Design Using Java**, by Elliot B. Koffman
and Paul A. T. Wolfgang (2nd edition). John Wiley & Sons, Inc., 2010. Available
at the campus bookstore.

We will also make extensive use of the Java API. The documentation for this API is here:

* [Java 8 API Reference](https://docs.oracle.com/javase/8/docs/api/)

Our programming will be done using [Eclipse](http://eclipse.org/), a professionally-used
*integrated development environment* (IDE). This free software is powerful and can be
intimidating for new users. You will quickly get used to it, and we'll take some time
during labs to familiarize you with it. Instructions for setting up this software on your
machine will be distributed in the first lab.

</div>

