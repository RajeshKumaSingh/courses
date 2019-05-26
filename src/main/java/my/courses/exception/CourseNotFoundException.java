package my.courses.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

//@SoapFault(faultCode = FaultCode.CLIENT)  // FaultCode in SoapFalut
@SoapFault(faultCode =FaultCode.CUSTOM, customFaultCode="{http://my/courses/course-details}_COURSE_NOT_OUND")
public class CourseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2571584715131353215L;

	public CourseNotFoundException(String message) {
		super(message); // faltString in SoapFault
	}

}
