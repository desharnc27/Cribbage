# Cribbage
Cribbage discard decision maker

The project's purpose is decision making in cribbage game.

Up to now, algorithms only manage the discarding phase.


Possible improvements:
Pegging algorithms would not only add something, it would also allow us to estimate average pegging performances and take it into consideration while computating discarding statistics.


Main: CribTestor.java 
input: hand (see userGuideInput.txt for help)
output: expected value of every choice of discarding, sorted by score. Note that two lists are provided: 1.estimated score if the opponent has a discarding behavior matching the statistics calculated in this project, 2.estimated average scores if the opponent discards randomly.
