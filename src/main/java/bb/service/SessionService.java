package bb.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class SessionService {
	
	public void setFilterFromSession(String id, Object o) {

		HttpSession session = session();

		try {
			session.setAttribute(id, o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getFilterFromSession(String id) {
		Object hf = null;

		HttpSession session = session();

		try {
			hf = session.getAttribute(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hf;
	}

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		return attr.getRequest().getSession(true);
	}

}
