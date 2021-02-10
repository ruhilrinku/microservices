package com.rbs.cloud.gateway;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.LinkedMultiValueMap;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class RouteFilter extends ZuulFilter {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private ProxyRequestHelper helper;
	
	@Override
	public boolean shouldFilter() {
		return RequestContext.getCurrentContext().getRouteHost() != null
					&& RequestContext.getCurrentContext().sendZuulResponse();
	}

	@Override
	public Object run() {
		OkHttpClient httpClient = new OkHttpClient();
		
		this.helper.addIgnoredHeaders();
			
			RequestContext context = RequestContext.getCurrentContext();
			HttpServletRequest request = context.getRequest();
			
			String uri = this.helper.buildZuulRequestURI(request);
			
			Headers.Builder headers = new Headers.Builder();
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				Enumeration<String> values = request.getHeaders(name);

				while (values.hasMoreElements()) {
					String value = values.nextElement();
					headers.add(name, value);
				}
			}
			
			context.addZuulRequestHeader("Authorization", request.getHeader("Authorization"));
			logger.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
			
			Request.Builder builder = new Request.Builder()
					.headers(headers.build())
					.url(uri)
					.method(request.getMethod(), null);
			
			try {
				Response response = httpClient.newCall(builder.build()).execute();
	
				LinkedMultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
	
				for (Map.Entry<String, List<String>> entry : response.headers().toMultimap().entrySet()) {
					responseHeaders.put(entry.getKey(), entry.getValue());
				}
				
				this.helper.setResponse(response.code(), response.body().byteStream(),
							responseHeaders);
			} catch (Exception e) {
				logger.error("Error while making call to service, ", e);
			}
			context.setRouteHost(null); 
			return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.ROUTE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER - 1;
	}


}
