/*
 * Copyright (c) 2012-2017 Veridu Ltd <https://veridu.com>
 * All rights reserved.
 */
package com.veridu.morpheus.tasks.models;

import com.veridu.idos.IdOSAPIFactory;
import com.veridu.morpheus.impl.Constants;
import com.veridu.morpheus.impl.Fact;
import com.veridu.morpheus.impl.User;
import com.veridu.morpheus.interfaces.beans.IDataSource;
import com.veridu.morpheus.interfaces.beans.IFeatureExtractor;
import com.veridu.morpheus.interfaces.beans.IUtils;
import com.veridu.morpheus.interfaces.facts.IFact;
import com.veridu.morpheus.interfaces.models.IModel;
import com.veridu.morpheus.interfaces.models.IPrediction;
import com.veridu.morpheus.interfaces.models.ITask;
import com.veridu.morpheus.interfaces.users.IUser;
import com.veridu.morpheus.utils.Parameters;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by cassio on 10/4/16.
 */
@Component("gender-mlp")
public class BeanGenderMLPTask implements ITask {

    private IFeatureExtractor genderFeatureExtractor;

    private IUtils utils;

    private IDataSource dao;

    private static final IFact fact = new Fact("probRealGenderMLP", "skynet");

    private static final Logger log = Logger.getLogger(BeanGenderMLPTask.class);

    /**
     * Constructor
     *
     * @param genderFeatureExtractor injected gender feature extractor
     * @param utils injected utils bean
     * @param dao injected idOS SQL data source
     */
    @Autowired
    public BeanGenderMLPTask(@Qualifier("genderExtractor") IFeatureExtractor genderFeatureExtractor, IUtils utils,
            IDataSource dao) {
        this.genderFeatureExtractor = genderFeatureExtractor;
        this.utils = utils;
        this.dao = dao;
    }

    /**
     * Run a gender prediction task
     * @param params request parameters
     */
    @Async
    @Override
    public void runTask(@RequestBody Parameters params) {
        long time1 = System.currentTimeMillis();

        String userId = params.userName;
        String pubKey = params.publicKey;
        boolean verbose = params.verbose;

        IdOSAPIFactory factory = utils.getIdOSAPIFactory(utils.generateCredentials(pubKey, userId));
        IUser user = new User(userId);

        if (utils.checkIfCandidatesExist(factory, user, "gender")) {

            IModel model = utils.readModel("/models/" + Constants.GENDER_MLP_MODEL_NAME);
            Instances datasetHeader = this.utils.generateDatasetHeader(this.genderFeatureExtractor.obtainFactList());

            long time2, timediff = 0;

            Instance inst = this.genderFeatureExtractor.createInstance(factory, datasetHeader, user);

            IPrediction pred = null;
            double realUserProb = -1;

            try {
                pred = model.predict(inst);
                realUserProb = pred.realUserProbability();

                dao.upsertScore(factory, user, "genderScore", "gender", realUserProb);

                if (realUserProb >= 0.9999970) {
                    dao.upsertGate(factory, user, "genderGate", "high");
                } else if (realUserProb >= 0.9998139) {
                    dao.upsertGate(factory, user, "genderGate", "medium");
                } else if (realUserProb >= 0.99) {
                    dao.upsertGate(factory, user, "genderGate", "low");
                } else {
                    dao.upsertGate(factory, user, "genderGate", "none");
                }

                time2 = System.currentTimeMillis();
                timediff = time2 - time1;

                //            if (params.verbose)
                log.info(String.format("Gender MLP model predicted real probability for user %s => %.2f in %d ms",
                        userId, pred.realUserProbability(), time2 - time1));

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (pred == null)
                log.error("Gender MLP model could not make prediction for user " + user.getId());

        } else {
            dao.upsertGate(factory, user, "genderGate", "NA");

            log.info(String.format("Gender MLP model found no candidates to score for user %s", userId));
        }
    }
}
