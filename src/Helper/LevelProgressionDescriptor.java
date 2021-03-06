package Helper;

import pokeshinto.LevelProgression;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class LevelProgressionDescriptor {

    public static void main(String[] args){
        LevelProgression levelProgression = new LevelProgression();

        int level = 0;

        levelProgression.add(1, "Strenght", 2);
        levelProgression.add(2, "Armor", 2);
        levelProgression.add(3, "Skill", 1);
        levelProgression.add(4, "Strenght", 2);
        levelProgression.add(5, "Strenght", 2);
        levelProgression.add(6, "Skill", 1);
        levelProgression.add(7, "Strenght", 2);
        levelProgression.add(8, "Strenght", 2);
        levelProgression.add(9, "Strenght", 2);

        System.out.println(levelProgression.get(level + 1).getKey() + " " + levelProgression.get(level + 1).getValue());
    }
}
