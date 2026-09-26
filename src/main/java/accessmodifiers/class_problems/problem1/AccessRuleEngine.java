package accessmodifiers.class_problems.problem1;

public final class AccessRuleEngine {
    private AccessRuleEngine() {
    }

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (!isModifier(fieldModifier) || !isContext(accessorContext)) {
            throw new IllegalArgumentException("Unsupported modifier or access context");
        }
        boolean allowed;
        switch (fieldModifier) {
            case "public":
                allowed = true;
                break;
            case "private":
                allowed = accessorContext.equals("SAME_CLASS");
                break;
            case "default":
            case "protected":
                allowed = !accessorContext.equals("DIFFERENT_PACKAGE");
                break;
            default:
                throw new IllegalArgumentException("Unsupported modifier");
        }
        return allowed ? "ALLOWED" : "DENIED";
    }

    public static String summarizeBatch(String[][] attempts) {
        if (attempts == null) throw new IllegalArgumentException("Attempts cannot be null");
        int allowed = 0;
        int denied = 0;
        for (String[] attempt : attempts) {
            if (attempt == null || attempt.length != 2) {
                throw new IllegalArgumentException("Each attempt must have a modifier and a context");
            }
            if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) allowed++;
            else denied++;
        }
        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    private static boolean isModifier(String modifier) {
        return "private".equals(modifier) || "default".equals(modifier)
            || "protected".equals(modifier) || "public".equals(modifier);
    }

    private static boolean isContext(String context) {
        return "SAME_CLASS".equals(context) || "SAME_PACKAGE".equals(context)
            || "DIFFERENT_PACKAGE".equals(context);
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("default", "DIFFERENT_PACKAGE"));
        System.out.println(summarizeBatch(new String[][] {
            {"protected", "SAME_PACKAGE"}, {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        }));
    }
}
