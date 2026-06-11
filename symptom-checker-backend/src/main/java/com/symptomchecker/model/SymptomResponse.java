import java.util.List;

public class SymptomResponse {
    private List<String> conditions;
    private String urgency;

    public SymptomResponse(List<String> conditions, String urgency) {
        this.conditions = conditions;
        this.urgency = urgency;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public String getUrgency() {
        return urgency;
    }
}
