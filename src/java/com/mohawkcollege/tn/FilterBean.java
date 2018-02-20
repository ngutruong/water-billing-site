/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.tn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.faces.application.ResourceHandler;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thom
 * 
 * StAuth10072: I Truong Nguyen, 000355410 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgment. I have not made my work available to anyone else.
 */
public class FilterBean implements Filter {

    FilterConfig fc;
    ArrayList<String> urlList;
    String defaultpage;

    @Inject
    private Login user;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        fc = filterConfig;
        String urls = fc.getInitParameter("filtering");
        StringTokenizer st = new StringTokenizer(urls, ",");
        defaultpage = fc.getInitParameter("defaultpage");
        urlList = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            urlList.add(st.nextToken());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (!req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            resp.setDateHeader("Expires", 0); // Proxies.
        }

        String requestedURL = new String(req.getRequestURI());

        boolean allowRequest = false;
        for (String url : urlList) {
            String pattern = ".*" + url;
            if (requestedURL.matches(pattern)) {
                allowRequest = true;
            }
        }

        String redirectPage = null;
        if (!allowRequest) {
            if (session == null) {
                redirectPage = defaultpage;
            } else if (session != null) {
                Login u = user;
                if (u == null) {
                    redirectPage = defaultpage;
                } else if (!u.isLoggedin() && !u.getIsAdmin()) {
                    redirectPage = "/WaterBillingFinal/faces/error/accessrestricted.xhtml";
                }
            }
        }
        if (redirectPage != null) {
            resp.sendRedirect(redirectPage);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }
}
