import java.util.*;

class SummaryRanges {

    private TreeMap<Integer, Integer> map;

    public SummaryRanges() {
        map = new TreeMap<>();
    }

    // Add number
    public void addNum(int val) {

        // Already present
        if (map.containsKey(val)) {
            return;
        }

        // Find neighbors
        Integer left = map.floorKey(val);
        Integer right = map.ceilingKey(val);

        // Case 1: Covered by left interval
        if (left != null && map.get(left) >= val) {
            return;
        }

        boolean mergeLeft =
                (left != null && map.get(left) + 1 == val);

        boolean mergeRight =
                (right != null && right == val + 1);

        // Case 4: Merge both
        if (mergeLeft && mergeRight) {

            int newStart = left;
            int newEnd = map.get(right);

            map.put(newStart, newEnd);
            map.remove(right);

        }
        // Case 2: Merge left only
        else if (mergeLeft) {

            map.put(left, map.get(left) + 1);

        }
        // Case 3: Merge right only
        else if (mergeRight) {

            int end = map.get(right);
            map.remove(right);
            map.put(val, end);

        }
        // Case 5: New interval
        else {

            map.put(val, val);
        }
    }

    // Get intervals
    public int[][] getIntervals() {

        int[][] res = new int[map.size()][2];

        int i = 0;

        for (Map.Entry<Integer, Integer> e : map.entrySet()) {

            res[i][0] = e.getKey();
            res[i][1] = e.getValue();
            i++;
        }

        return res;
    }
}