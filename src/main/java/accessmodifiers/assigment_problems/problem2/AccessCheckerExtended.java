package accessmodifiers.assigment_problems.problem2;

public class AccessCheckerExtended {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        boolean allowed;
        switch (fieldModifier) {
            case "private":
                allowed = accessorContext.equals("SAME_CLASS");
                break;
            case "default":
                allowed = accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE");
                break;
            case "protected":
                // Java's cross-package subclass rule: a subclass in a different package can
                // reach an inherited protected member only through a reference of its OWN type
                // (or a subtype) - never through a reference typed as the parent class.
                allowed = accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE")
                        || accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE");
                break;
            case "public":
                allowed = true;
                break;
            default:
                throw new IllegalArgumentException("Unknown modifier: " + fieldModifier);
        }
        return allowed ? "ALLOWED" : "DENIED";
    }

    // Turns an UNDERSCORE_SEPARATED_CODE into "Title Cased Words".
    public static String describeContext(String accessorContext) {
        String[] parts = accessorContext.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].toLowerCase();
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
            if (i != parts.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));    // ALLOWED
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));  // DENIED
        System.out.println(describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")); // Subclass Different Package Own Type
    }
}
