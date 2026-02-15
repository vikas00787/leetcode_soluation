import java.util.*;

class RangeModule {

    private TreeMap<Integer, Integer> map;

    public RangeModule() {
        map = new TreeMap<>();
    }

    // Add range [left, right)
    public void addRange(int left, int right) {

        if (left >= right) return;

        Integer start = map.floorKey(left);

        // Merge with previous
        if (start != null && map.get(start) >= left) {
            left = start;
            right = Math.max(right, map.get(start));
            map.remove(start);
        }

        // Merge with next ranges
        Integer next = map.ceilingKey(left);

        while (next != null && next <= right) {
            right = Math.max(right, map.get(next));
            map.remove(next);
            next = map.ceilingKey(left);
        }

        map.put(left, right);
    }

    // Query range [left, right)
    public boolean queryRange(int left, int right) {

        Integer start = map.floorKey(left);

        if (start == null) return false;

        return map.get(start) >= right;
    }

    // Remove range [left, right)
    public void removeRange(int left, int right) {

        if (left >= right) return;

        Integer start = map.floorKey(left);

        // Handle left overlap
        if (start != null && map.get(start) > left) {

            int end = map.get(start);
            map.remove(start);

            if (start < left) {
                map.put(start, left);
            }

            if (end > right) {
                map.put(right, end);
            }
        }

        // Remove middle overlaps
        Integer next = map.ceilingKey(left);

        while (next != null && next < right) {

            int end = map.get(next);
            map.remove(next);

            if (end > right) {
                map.put(right, end);
                break;
            }

            next = map.ceilingKey(left);
        }
    }
}




/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */