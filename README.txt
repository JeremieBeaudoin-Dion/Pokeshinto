Pokeshinto is a Pokemon-Type RPG game made with pure Java. It has turn-based combat and a free-roam map.

Resources contains all music and art needed for the game.
SRC contains all the Java classes, which are divided into 6 main packages.

Pokeshinto
As the principal package with the name of the game, Pokeshinto holds the principal MVC classes of the game. The main method is in the class Game.java

Images
The classes in images will create the frame of the game, and handle any PhysicalObject to be put on screen.

Combat
Combat has all the classes which are used during a combat state of the game. It handles combat logic, it instanciates the combat, etc.

Combat has 2 subpackages: combatActions and combatAI. CombatActions holds all effects used by pokeshintos during combat, such as skills, passives, triggers, etc. CombatAI holds the AI that fights during combat and their logic to try an defeat the player.

World
World holds the maps and main logic of the adventure-side of the game. 

DuringMenus
When a menu is in game, the classes in DuringMenu handles actions and returns the objects to put on screen.

Minigames
In the main game, there are some minigames, which act independently from the rest.

Helper
Here are helper classes which are usefull for testing.
