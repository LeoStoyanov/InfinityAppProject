package com.loginscreen;
/**
 * @author Leo Stoyanov
 */
/**
 * Interface that acts as a presenter between view classes and model classes.
 */
public interface IPresenter {
    /**
     * Once the user performs an action, the view class will call this method, which will
     * then call for the respective model class to preform a certain request.
     * @param url request url
     * @param requestType type of request, either GET, PUT, or POST
     */
    public void loadMessage (String url, String requestType);
}
