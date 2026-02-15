import java.util.*;

class RandomizedCollection {

    private List<Integer> list;
    private Map<Integer, Set<Integer>> map;
    private Random random;

    public RandomizedCollection() {

        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
    }

    // Insert
    public boolean insert(int val) {

        boolean isNew = !map.containsKey(val);

        map.putIfAbsent(val, new HashSet<>());

        map.get(val).add(list.size());
        list.add(val);

        return isNew;
    }

    // Remove (FIXED)
    public boolean remove(int val) {

        if (!map.containsKey(val)) return false;

        // Get one index of val
        int idx = map.get(val).iterator().next();

        int lastIndex = list.size() - 1;
        int lastVal = list.get(lastIndex);

        // Remove idx from val set
        map.get(val).remove(idx);

        // If not removing last element, do swap
        if (idx != lastIndex) {

            // Replace idx with lastVal
            list.set(idx, lastVal);

            // Update lastVal index
            map.get(lastVal).remove(lastIndex);
            map.get(lastVal).add(idx);
        }

        // Remove last element
        list.remove(lastIndex);

        // Clean map
        if (map.get(val).isEmpty()) {
            map.remove(val);
        }

        return true;
    }

    // Get Random
    public int getRandom() {

        return list.get(random.nextInt(list.size()));
    }
}