/*
 Copyright (c) 2013, Paul Houghton and Futurice Oy
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 - Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.
 - Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 */
package org.tantalum.net;

import org.tantalum.Task;

/**
 * HTTP POST a message to a given URL
 *
 * @author phou
 */
public class HttpPoster extends HttpGetter {
    private String url;
    private volatile boolean isChainInputPostData = false;

    /**
     * HTTP POST a message to a given URL
     *
     * Make sure you call setPostData(byte[]) to specify what you want to POST
     * or you will get an IllegalArgumentException
     *
     * @param url - The url we will HTTP POST to, plus optional lines of text to
     * create a unique hashcode for caching this value locally.
     */
    public HttpPoster(final String url) {
        super(url);
        
        this.url = url;
    }

    /**
     * Create an HTTP POST operation
     *
     * @param url
     * @param postData
     */
    public HttpPoster(final String url, final byte[] postData) {
        this(url);

        setPostData(postData);
    }

    /**
     * Set the message to be HTTP POSTed to the server
     *
     * @param postData
     * @return
     */
    public HttpPoster setPostData(final byte[] postData) {
        if (postData == null) {
            throw new IllegalArgumentException(getClassName() + " was passed null message- meaningless POST or PUT operation");
        }
        this.postMessage = new byte[postData.length];
        System.arraycopy(postData, 0, this.postMessage, 0, postData.length);

        return this;
    }

    /**
     * The default when an HttpPoster is not the first Task in a chain is for
     * the previous Task output to be passed as the URL, not post data, to this
     * task.
     *
     * You can override this to have the previous Task in the chain set the post
     * data instead of the URL. The previous Task should then output a byte[]
     *
     * @param isPostData
     * @return
     */
    public HttpPoster setChainInputIsPostData(final boolean isPostData) {
        this.isChainInputPostData = isPostData;

        return this;
    }

    public Object exec(final Object in) {
        if (isChainInputPostData) {
            setPostData((byte[]) in);
            
            return super.exec(url);
        } else {
            return super.exec(in);
        }
    }
}
