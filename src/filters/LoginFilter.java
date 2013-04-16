package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 * 
 * This class serves as an auto-session detector. It checks to see if the a session
 * has been created and if the session has had a "userid" set, which would be done
 * upon logging in or registering with the system. If neither of these conditions
 * have been met, then the user is redirected to the login page.
 * 
 * Additionally, this filter also forces each page to be fetched from the server
 * each time. While this isn't best practice, it also prevents users from being
 * able to go back in their cached history, which is especially important for after
 * the user logs out.
 */
@WebFilter("/xmlers/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
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
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// Cast to HttpServlet* classes, get the session
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		
		// If there is no session or the session hasn't set the userid, redirect to login
		if (null == session || null == session.getAttribute("userid")) {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} else {
			chain.doFilter(req, res);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
