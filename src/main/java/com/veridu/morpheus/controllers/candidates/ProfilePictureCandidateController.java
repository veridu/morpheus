package com.veridu.morpheus.controllers.candidates;

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
 * Created by cassio on 11/3/16.
 */
@RestController
public class ProfilePictureCandidateController {

    private ITask profilePicCandidatesTask;

    @Autowired
    public ProfilePictureCandidateController(@Qualifier("profilepic-candidates") ITask profilePicCandidatesTask) {
        this.profilePicCandidatesTask = profilePicCandidatesTask;
    }

    @PostMapping("/morpheus/profilepic-candidates")
    public ModelResponse makePrediction(@RequestBody Parameters params) {

        if (!LocalUtils.validateRequestParams(params))
            return new ModelResponse(false);

        this.profilePicCandidatesTask.runTask(params);

        return new ModelResponse(true);
    }

}