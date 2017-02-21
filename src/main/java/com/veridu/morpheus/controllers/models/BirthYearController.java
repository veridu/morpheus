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
public class BirthYearController {

    private BeanUtils utils;
    private BeanConfigurationManager beanManager;
    private ITask birthYearTask;

    /**
     * Constructor
     * @param utils injected utils
     * @param beanManager injected manager
     * @param birthYearTask injected class
     */
    @Autowired
    public BirthYearController(BeanUtils utils, BeanConfigurationManager beanManager,
            @Qualifier("birthyear-mlp") ITask birthYearTask) {
        this.utils = utils;
        this.beanManager = beanManager;
        this.birthYearTask = birthYearTask;
    }

    /**
     * Handle post request
     * @param params request parameters
     * @return model response as json
     */
    @PostMapping("/morpheus/birthyear-mlp")
    public ModelResponse makePrediction(@RequestBody Parameters params) {

        if (!LocalUtils.validateRequestParams(params))
            return new ModelResponse(false);

        this.birthYearTask.runTask(params);

        return new ModelResponse(true);
    }

}
