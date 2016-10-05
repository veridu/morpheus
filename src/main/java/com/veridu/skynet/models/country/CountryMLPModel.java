package com.veridu.skynet.models.country;

import java.io.Serializable;

import com.veridu.morpheus.impl.GenericModel;
import com.veridu.morpheus.impl.Prediction;
import com.veridu.morpheus.interfaces.models.IPrediction;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;

public class CountryMLPModel extends GenericModel implements Serializable {

    private static final long serialVersionUID = -4020563341837779573L;

    private MultilayerPerceptron mlpClassifier;

    public CountryMLPModel() {
    }

    public CountryMLPModel(MultilayerPerceptron mlpClassifier) {
        this.mlpClassifier = mlpClassifier;
    }

    @Override
    public IPrediction predict(Instance instance) throws Exception {
        double[] dist = this.mlpClassifier.distributionForInstance(instance);
        return new Prediction(dist[0], dist[1]);
    }

    @Override
    public AbstractClassifier getClassifier() {
        return this.mlpClassifier;
    }

}