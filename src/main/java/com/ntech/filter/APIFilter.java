package com.ntech.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.ntech.exception.ErrorTokenException;
import com.ntech.exception.PermissionDeniedException;
import com.ntech.util.ForwardRequestWrapper;

/**
 * Servlet Filter implementation class APIFilter
 */
public class APIFilter implements Filter {
	
	private Logger logger = Logger.getLogger(APIFilter.class);
    /**
     * Default constructor. 
     */
    public APIFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		logger.info("------------FILTER-----------");
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		ForwardRequestWrapper forwardRequestWrapper;
		try {
			forwardRequestWrapper = new ForwardRequestWrapper(request);
			chain.doFilter(forwardRequestWrapper.getRequest(), response);
		} catch (ErrorTokenException e) {
			// TODO Auto-generated catch block
			logger.info("*****Bad_Token*****");
			e.printStackTrace();
			
		} catch (PermissionDeniedException e) {
			// TODO Auto-generated catch block
			logger.info("*****BAD_ID*****");
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.info("ParseException-CHECK_ID");
			e.printStackTrace();
		}
	
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
