package accessmodifiers.class_problems.problem2;

import java.util.Locale;

public final class AccessRuleEngineExtended {
    private AccessRuleEngineExtended() {
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
                allowed = accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE");
                break;
            case "protected":
                allowed = accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE")
                    || accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE");
                break;
            default:
                throw new IllegalArgumentException("Unsupported modifier");
        }
        return allowed ? "ALLOWED" : "DENIED";
    }

    public static String describeContext(String accessorContext) {
        if (!isContext(accessorContext)) throw new IllegalArgumentException("Unsupported context");
        String[] parts = accessorContext.toLowerCase(Locale.ROOT).split("_");
        StringBuilder description = new StringBuilder();
        for (String part : parts) {
            if (description.length() > 0) description.append(' ');
            description.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return description.toString();
    }

    private static boolean isModifier(String value) {
        return "private".equals(value) || "default".equals(value)
            || "protected".equals(value) || "public".equals(value);
    }

    private static boolean isContext(String value) {
        return "SAME_CLASS".equals(value) || "SAME_PACKAGE".equals(value)
            || "DIFFERENT_PACKAGE".equals(value)
            || "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(value)
            || "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE".equals(value);
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(describeContext("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
    }
}
