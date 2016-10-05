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
public class CountryController {

    private BeanUtils utils;
    private BeanConfigurationManager beanManager;
    private ITask countryTask;

    @Autowired
    public CountryController(BeanUtils utils, BeanConfigurationManager beanManager,
            @Qualifier("country-mlp") ITask countryTask) {
        this.utils = utils;
        this.beanManager = beanManager;
        this.countryTask = countryTask;
    }

    @PostMapping("/morpheus/country-mlp")
    public ModelResponse makePrediction(@RequestBody Parameters params) {

        if (!LocalUtils.validateRequestParams(params))
            return new ModelResponse(false);

        this.countryTask.runTask(params);

        return new ModelResponse(true);
    }

}