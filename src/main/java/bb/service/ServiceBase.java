package bb.service;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ServiceBase<E, PK extends Serializable> {

	protected final static Log log = LogFactory.getLog(ServiceBase.class);

}
