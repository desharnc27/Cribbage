# Cribbage
Cribbage discard decision maker

The project's purpose is decision making in cribbage game.


How to run (for user): open terminal, navigate to where you copied the project, then in /target/classes, then enter:
```
java graphics.FrameMain
```

	
Or, if you prefer to use the app in command line rather than ui

```
java cmdline.CmdMain
```

Though the cmd version has less options.

Input: hand (see userGuideInput.txt for help)
Output: expected value of every choice of discarding, sorted by score. Note that two lists can be provided: 1.estimated score if the opponent has a discarding behavior matching "behind-the-scenes" statistics stored in this project, 2.estimated average scores if the opponent discards randomly.

For advanced users: "behind-the-scenes" statistics can be recalculated from scratch by running:
```
java statmaking.StatMain [number within 1-7]
```
Though this takes many minutes and is not necessary for current usage. The number indicates the depth level. Going over 4 is long and barely necessary since the convergence is already high at 4.

------ 

Up to now, algorithms only manage the discarding phase.

Possible future improvements:
- Pegging algorithms would not only be good for the pegging phase itself, it would also allow us to estimate average pegging performances and take it into consideration while computing discarding statistics, which, in some cases, would change the optimal discarding choice
- French translation is not completed
- The ui output could show card images rather than text
------

