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

import com.ntech.exception.ErrorTokenException;
import com.ntech.exception.IllegalAPIException;
import com.ntech.exception.IllegalIDException;
import com.ntech.exception.IllegalGalleryException;
import com.ntech.util.ErrorPrompt;
import com.ntech.util.ForwardRequestWrapper;

/**
 * Servlet Filter implementation class APIFilter
 */
public class APIFilter implements Filter {
	
	private final Logger logger = Logger.getLogger(APIFilter.class);
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
		ErrorPrompt.clear();
		try {
			ForwardRequestWrapper forwardRequestWrapper = new ForwardRequestWrapper(request);
			chain.doFilter(forwardRequestWrapper, response);
		} catch (ErrorTokenException e) {
			// TODO Auto-generated catch block
			logger.error("*****Bad_Token*****@"+req.getAttribute("userName"));
			ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1),"bad_token");
			e.printStackTrace();
			chain.doFilter(request, response);
			
		} catch (IllegalIDException e) {
			// TODO Auto-generated catch block
			logger.error("*****BAD_ID*****@"+req.getAttribute("userName"));
			ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1),"bad_id");
			e.printStackTrace();
			chain.doFilter(request, response);
		} catch (IllegalAPIException e) {
			// TODO Auto-generated catch block
			logger.error("*****BAD_API*****@"+req.getAttribute("userName"));
			ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1),e.getMessage());
			e.printStackTrace();
			chain.doFilter(request, response);
		} catch (IllegalGalleryException e) {
			// TODO Auto-generated catch block
			logger.error("*****BAD_GALLERY*****@"+req.getAttribute("userName"));
			ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1),"bad_gallery");
			e.printStackTrace();
			chain.doFilter(request, response);
		}
	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
