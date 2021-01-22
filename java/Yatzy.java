import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Yatzy {

    public static int chance(int d1, int d2, int d3, int d4, int d5) {
        return d1 + d2 + d3 + d4 + d5;
    }

    public static int yatzy(int... dice) {
        Map<Integer, Integer> mp = createMapWithOccurences(dice);
        for (Map.Entry<Integer, Integer> entry : mp.entrySet()) {
            if (entry.getValue() == 5)
                return 50;
        }
        return 0;
    }

    public static int ones(int d1, int d2, int d3, int d4, int d5) {
        return countOccurences(new int[]{d1, d2, d3, d4, d5}, 1);
    }

    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        return countOccurences(new int[]{d1, d2, d3, d4, d5}, 2);
    }

    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        return countOccurences(new int[]{d1, d2, d3, d4, d5}, 3);
    }

    protected int[] dice;

    public Yatzy(int d1, int d2, int d3, int d4, int _5) {
        this.dice = new int[]{d1, d2, d3, d4, _5};
    }

    public int fours() {
        return countOccurences(dice, 4);
    }

    public int fives() {
        return countOccurences(dice, 5);
    }

    public int sixes() {
        return countOccurences(dice, 6);
    }

    public static int score_pair(int d1, int d2, int d3, int d4, int d5) {
        int max = 0;
        Map<Integer, Integer> mp = createMapWithOccurences(
                new int[]{d1, d2, d3, d4, d5});
        for (Map.Entry<Integer, Integer> entry : mp.entrySet()) {
            if (entry.getValue() >= 2) {
                if (entry.getKey() * entry.getValue() > max) {
                    max = entry.getKey() * entry.getValue();
                }
            }
        }
        return max;
    }

    public static int two_pair(int d1, int d2, int d3, int d4, int d5) {
        Map<Integer, Integer> mp = createMapWithOccurences(
                new int[]{d1, d2, d3, d4, d5});
        return calculateKind(mp, 2, false);
    }

    public static int four_of_a_kind(int _1, int _2, int d3, int d4, int d5) {
        Map<Integer, Integer> mp = createMapWithOccurences(
                new int[]{_1, _2, d3, d4, d5});
        return calculateKind(mp, 4, false);
    }

    public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5) {
        Map<Integer, Integer> mp = createMapWithOccurences(
                new int[]{d1, d2, d3, d4, d5});
        return calculateKind(mp, 3, false);
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
        Map<Integer, Integer> mp = createMapWithOccurences(
                new int[]{d1, d2, d3, d4, d5});
        // On effectue une recherche sur des nombres données et on vérifie si la
        // taille en sortie correspond
        if (mp.keySet().stream()
                .filter(key -> key.intValue() == 1 || key.intValue() == 2
                        || key.intValue() == 3 || key.intValue() == 4
                        || key.intValue() == 5)
                .collect(Collectors.toList()).size() == 5) {
            return 15;
        } else {
            return 0;
        }
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
        Map<Integer, Integer> mp = createMapWithOccurences(
                new int[]{d1, d2, d3, d4, d5});
        if (mp.keySet().stream()
                .filter(key -> key.intValue() == 2 || key.intValue() == 3
                        || key.intValue() == 4 || key.intValue() == 5
                        || key.intValue() == 6)
                .collect(Collectors.toList()).size() == 5) {
            return 20;
        } else {
            return 0;
        }
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
        int returnValue = 0;
        Map<Integer, Integer> mp = createMapWithOccurences(
                new int[]{d1, d2, d3, d4, d5});
        if (mp.size() == 2) {
            if (mp.containsValue(2) && mp.containsValue(3)) {
                returnValue += calculateKind(mp, 2, true);
                returnValue += calculateKind(mp, 3, true);
            }
        }
        return returnValue;
    }

    // Méthode permettant de compter le nombre d'occurences dans un tableau
    // selon un nombre passé en paramètre
    private static int countOccurences(int[] ints, int number) {
        int sum = 0;
        for (int i : ints) {
            if (i == number)
                sum += number;
        }
        return sum;
    }

    // Méthode permettant de créer une map en comptant le nombre d'occurences
    // trouvée pour chaque nombre trouvé
    private static Map<Integer, Integer> createMapWithOccurences(int[] ints) {
        // On remplit la map et on compte les occurences de chaque nombre
        Map<Integer, Integer> mp = new HashMap<>();
        for (int i : ints) {
            if (mp.containsKey(i)) {
                mp.put(i, mp.get(i) + 1);
            } else {
                mp.put(i, 1);
            }
        }
        return mp;
    }

    /**
     * Méthode permettant de calculer le produit entre clé et valeur
     * 
     * @param map
     * @param occurence
     * @param strictMode
     *            : flag permettant de savoir si on calcule en fonction de la
     *            valeur dans la map ou pas
     * @return
     */
    private static int calculateKind(Map<Integer, Integer> map, int occurence,
            boolean strictMode) {
        int returnValue = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (!strictMode) {
                if (entry.getValue() >= occurence) {
                    returnValue += entry.getKey() * occurence;
                }
            } else {
                if (entry.getValue() == occurence) {
                    returnValue += entry.getKey() * entry.getValue();
                }
            }
        }
        return returnValue;
    }
}
