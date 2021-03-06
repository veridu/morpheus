/*
 * Copyright (c) 2012-2017 Veridu Ltd <https://veridu.com>
 * All rights reserved.
 */

package com.veridu.morpheus.controllers.models;

import com.veridu.morpheus.impl.ModelResponse;
import com.veridu.morpheus.interfaces.models.ITask;
import com.veridu.morpheus.utils.BeanConfigurationManager;
import com.veridu.morpheus.utils.BeanUtils;
import com.veridu.morpheus.utils.LocalUtils;
import com.veridu.morpheus.utils.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cassio on 10/2/16.
 */
@RestController
public class GenderController {

    private BeanUtils utils;
    private BeanConfigurationManager beanManager;
    private ITask genderTask;

    /**
     * Constructor
     *
     * @param utils injected utils
     * @param beanManager injected manager
     * @param genderTask injected task
     */
    @Autowired
    public GenderController(BeanUtils utils, BeanConfigurationManager beanManager,
            @Qualifier("gender-mlp") ITask genderTask) {
        this.utils = utils;
        this.beanManager = beanManager;
        this.genderTask = genderTask;
    }

    /**
     * Handle post request
     * @param params request parameters
     * @return model response as json
     */
    @PostMapping("/morpheus/gender-mlp")
    public ModelResponse makePrediction(@RequestBody Parameters params) {

        if (!LocalUtils.validateRequestParams(params))
            return new ModelResponse(false);

        this.genderTask.runTask(params);

        return new ModelResponse(true);
    }

}
