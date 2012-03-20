package bb.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import bb.domain.HitsFilter;

@Service
public class HitsFilterService {

	public void setFilterFromSession() {
		HitsFilter hf = null;

		HttpSession session = session();

		try {
			session.setAttribute("hitsFilter", hf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HitsFilter getFilterFromSession() {
		HitsFilter hf = null;

		HttpSession session = session();

		try {
			hf = (HitsFilter) session.getAttribute("hitsFilter");
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
