package uk.co.gresearch.siembol.response.evaluators.fixed;

import uk.co.gresearch.siembol.response.common.Evaluable;
import uk.co.gresearch.siembol.response.common.ResponseEvaluationResult;
import uk.co.gresearch.siembol.response.common.RespondingResult;
import uk.co.gresearch.siembol.response.common.ResponseAlert;

public class FixedResultEvaluator implements Evaluable {
    private final ResponseEvaluationResult returnResult;
    public FixedResultEvaluator(ResponseEvaluationResult returnResult) {
        this.returnResult = returnResult;
    }

    @Override
    public RespondingResult evaluate(ResponseAlert alert) {
        return RespondingResult.fromEvaluationResult(returnResult, alert);
    }
}
