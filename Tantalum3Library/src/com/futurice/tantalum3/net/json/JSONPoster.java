/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.futurice.tantalum3.net.json;

import com.futurice.tantalum3.AsyncResult;
import com.futurice.tantalum3.log.L;
import com.futurice.tantalum3.net.HttpPoster;

/**
 *
 * @author combes
 */
public abstract class JSONPoster extends HttpPoster {

    private final JSONModel jsonvo;

    public JSONPoster(final String url, final String postMessage, final JSONModel jsonModel, final AsyncResult asyncTask, final int retriesRemaining) {
        super(url, retriesRemaining, asyncTask, postMessage.getBytes());
        
        this.jsonvo = jsonModel;
    }
    
    protected void onResult(final byte[] bytes) {
        String value = "";

        try {
            value = bytes.toString().trim();
            if (value.startsWith("[")) {
                // Parser expects non-array base object- add one
                value = "{\"base:\"" + value + "}";
            }
            jsonvo.setJSON(value);
        } catch (Exception e) {
            //#debug
            L.e("JSONPoster HTTP response problem", this.getUrl() + " : " + value, e);
            cancel(false);
            task.cancel();
        }
        task.set(result);
    }
}
