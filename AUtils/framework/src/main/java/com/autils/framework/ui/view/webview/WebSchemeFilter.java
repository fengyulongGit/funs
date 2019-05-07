package com.autils.framework.ui.view.webview;

/**
 * Created by fengyulong on 2018/5/12.
 */
public abstract class WebSchemeFilter {
    private String[] actions;

    public WebSchemeFilter(String... actions) {
        this.actions = actions;
    }

    public abstract boolean action(String url);

    protected boolean filter(String url) {
        if (url == null) {
            return false;
        }
        for (String action : actions) {
            if (url.toLowerCase().startsWith(action)) {
                return true;
            }
        }
        return false;
    }

    protected boolean contains(String url) {
        if (url == null) {
            return false;
        }
        for (String action : actions) {
            if (url.toLowerCase().contains(action)) {
                return true;
            }
        }
        return false;
    }

    protected boolean match(String url) {
        if (url == null) {
            return false;
        }
        for (String action : actions) {
            if (url.equals(action)) {
                return true;
            }
        }
        return false;
    }

    protected String deleteAction(String url) {
        if (url == null) {
            return url;
        }
        for (String action : actions) {
            url = url.replace(action, "");
        }
        return url;
    }
}
