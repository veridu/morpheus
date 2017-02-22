/*
 * Copyright (c) 2012-2017 Veridu Ltd <https://veridu.com>
 * All rights reserved.
 */

package com.veridu.morpheus.features;

import com.veridu.idos.IdOSAPIFactory;
import com.veridu.morpheus.impl.Constants;
import com.veridu.morpheus.interfaces.beans.IDataSource;
import com.veridu.morpheus.interfaces.beans.IFeatureExtractor;
import com.veridu.morpheus.interfaces.beans.IUtils;
import com.veridu.morpheus.interfaces.facts.IFact;
import com.veridu.morpheus.interfaces.users.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cassio on 10/3/16.
 */
@Component("googleExtractor")
public class BeanGoogleFeatureExtractor implements IFeatureExtractor {

    private IDataSource dataSource;

    private IUtils utils;

    private ArrayList<IFact> facts;

    /**
     * Constructor
     *
     * @param dataSource injected idOS SQL data source
     * @param utils injected utils bean
     */
    @Autowired
    public BeanGoogleFeatureExtractor(IDataSource dataSource, IUtils utils) {
        this.dataSource = dataSource;
        this.utils = utils;
    }

    /**
     * called after bean construction
     */
    @PostConstruct
    public void init() {
        this.facts = this.utils.getGoogleNumericFacts();
        this.facts.addAll(this.utils.getGoogleBinaryFacts());
    }

    /**
     * Create an instance with google features
     *
     * @param factory idOS API factory
     * @param dataset data header
     * @param user selected user
     * @return an Instance object with the features regarding google
     */
    @Override
    public Instance createInstance(IdOSAPIFactory factory, Instances dataset, IUser user) {

        Instance inst = new DenseInstance(dataset.numAttributes());

        HashMap<IFact, Double> numericFacts = this.dataSource
                .obtainNumericFactsForProfile(factory, user, Constants.GOOGLE_PROVIDER_NAME);

        HashMap<IFact, Double> binaryFacts = this.dataSource
                .obtainBinaryFactsForProfile(factory, user, Constants.GOOGLE_PROVIDER_NAME);

        int attPos = 0;

        for (IFact fact : this.obtainFactList()) {
            if (numericFacts.containsKey(fact)) {
                Double value = numericFacts.get(fact);
                inst.setValue(attPos, value);
            } else if (binaryFacts.containsKey(fact)) {
                Double value = binaryFacts.get(fact);
                inst.setValue(attPos, value);
            }
            attPos++;
        }

        return inst;
    }

    @Override
    public ArrayList<IFact> obtainFactList() {
        return this.facts;
    }

}
