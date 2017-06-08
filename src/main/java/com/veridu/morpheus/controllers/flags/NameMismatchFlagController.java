package com.veridu.morpheus.controllers.flags;

import com.veridu.morpheus.impl.ModelResponse;
import com.veridu.morpheus.interfaces.models.ITask;
import com.veridu.morpheus.utils.LocalUtils;
import com.veridu.morpheus.utils.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cassio on 4/19/17.
 */
@RestController
public class NameMismatchFlagController {

    private ITask nameMismatchTask;

    /**
     * Constructor
     * @param nameMismatchTask injected task
     */
    @Autowired
    public NameMismatchFlagController(@Qualifier("flags-name-mismatch") ITask nameMismatchTask) {
        this.nameMismatchTask = nameMismatchTask;
    }

    /**
     * Handle post request
     * @param params request parameters
     * @return model response as json
     */
    @PostMapping("/morpheus/flags-name-mismatch")
    public ModelResponse makePrediction(@RequestBody Parameters params) {

        if (!LocalUtils.validateRequestParams(params))
            return new ModelResponse(false);

        this.nameMismatchTask.runTask(params);

        return new ModelResponse(true);
    }

}