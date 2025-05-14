import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, Integer> variableMap = new HashMap<>();

    public void assign(String variable, int value) {
        variableMap.put(variable, value);
    }

    public int getValue(String variable) {
        if (!variableMap.containsKey(variable)) {
            throw new IllegalArgumentException("Variable not defined: " + variable);
        }
        return variableMap.get(variable);
    }
}
