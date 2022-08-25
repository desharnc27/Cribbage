# Cribbage
Cribbage discard decision maker

The project's purpose is decision making in cribbage game.


How to run (for user):
	- Open Terminal
	- In terminal, navigate to "<where you copied the folder>/target/classes"
	- enter "java graphics.FrameMain"
The last step can be replaced by "java cmdline.CmdMain" if you want to use the app in terminal rather than in the app's user interface.

input: hand (see userGuideInput.txt for help)
output: expected value of every choice of discarding, sorted by score. Note that two lists can be provided: 1.estimated score if the opponent has a discarding behavior matching "behind-the-scenes" statistics stored in this project, 2.estimated average scores if the opponent discards randomly.

For advanced users: "behind-the-scenes" statistics can be recalculated from scratch by running "java statmaking.StatMain [number within 1-7]". Though this takes many minutes and is not necessary for current usage.



------ 

Up to now, algorithms only manage the discarding phase.

Possible futur improvements:

Pegging algorithms would not only add something, it would also allow us to estimate average pegging performances and take it into consideration while computating discarding statistics.

------

Other note (french translation): pour usage normal ("java graphics.FrameMain" ou "java cmdline.CmdMain"), l'application est à 99% traduite en français.

