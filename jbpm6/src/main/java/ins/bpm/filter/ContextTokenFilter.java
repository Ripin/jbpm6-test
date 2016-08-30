package ins.bpm.filter;

import ins.bpm.utils.ParameterRequestWrapper;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ContextTokenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)req;
		String uri = httpServletRequest.getRequestURI();
		//支付宝服务窗交互会传xml参数过来，不要过滤
		if(!uri.contains("alipay/alipayCheck")) {
			Map<String, Object> map = new HashMap(req.getParameterMap());
			String values[], val;
			for(Map.Entry<String, Object> entry : map.entrySet()) {
				values = (String[])entry.getValue();
				for (int i = 0; i < values.length; i++) {
					val = values[i];
					if (StringUtils.isBlank(val)) {
						continue;
					}
					
					// 将特殊字符替换
					values[i] = val.replaceAll("<", "*").replaceAll(">", "*");
				}
				map.put(entry.getKey(), values);
			}
			ParameterRequestWrapper wrapper = new ParameterRequestWrapper(httpServletRequest, map);
			req = wrapper;
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
