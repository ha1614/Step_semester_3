package constructors.class_problems;

public final class BusRoute implements Comparable<BusRoute> {
    private final String routeCode;
    private final String routeName;
    private final int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        if (routeCode == null || routeCode.trim().isEmpty() || routeName == null || routeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Route code and name are required");
        }
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 3);
    }

    public String getRouteCode() {
        return routeCode;
    }

    @Override
    public int compareTo(BusRoute other) {
        if (other == null) throw new IllegalArgumentException("Route cannot be null");
        int result = Integer.compare(other.priority, priority);
        if (result != 0) return result;
        result = routeCode.compareToIgnoreCase(other.routeCode);
        if (result != 0) return result;
        result = Integer.compare(routeName.length(), other.routeName.length());
        if (result != 0) return result;
        return routeName.compareToIgnoreCase(other.routeName);
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) throw new IllegalArgumentException("Routes cannot be null");
        BusRoute[] result = routes.clone();
        for (BusRoute route : result) if (route == null) throw new IllegalArgumentException("Route cannot be null");
        // Stable insertion sort: exact ties keep their original order.
        for (int i = 1; i < result.length; i++) {
            BusRoute current = result[i];
            int j = i - 1;
            while (j >= 0 && result[j].compareTo(current) > 0) {
                result[j + 1] = result[j];
                j--;
            }
            result[j + 1] = current;
        }
        return result;
    }

    public static void main(String[] args) {
        BusRoute[] ranked = rankRoutes(new BusRoute[] {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        });
        for (BusRoute route : ranked) System.out.println(route.routeCode);
    }
}
