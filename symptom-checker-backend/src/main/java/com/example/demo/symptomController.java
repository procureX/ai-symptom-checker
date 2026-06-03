import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class symptomController {

    @PostMapping("/symptoms")
    public SymptomResponse checkSymptoms(@RequestBody SymptomRequest request) {
        System.out.println("Received symptoms: " + request.getSymptoms());

        return new SymptomResponse(
            List.of("Common cold", "Flu"),
            "Low"
        );
    }
}
