package com.app;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Interface that acts as a view for presenter classes and model classes.
 */
public interface IView {
    /**
     * Once a response is returned from a model class, it is passed through this method into
     * the view class in order to use the response.
     * @param view response from the API request
     */
    public void useResponseOne(JSONArray view);

    public void useResponseTwo(JSONObject view);
}
