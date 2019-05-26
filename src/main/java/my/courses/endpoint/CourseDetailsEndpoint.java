package my.courses.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import my.courses.exception.CourseNotFoundException;
import my.courses.wsmodel.CourseDetails;
import my.courses.wsmodel.GetCourseDetailsRequest;
import my.courses.wsmodel.GetCourseDetailsResponse;

@Endpoint
public class CourseDetailsEndpoint {

	// namespace and request type
	@PayloadRoot(namespace="http://my/courses/course-details", localPart="GetCourseDetailsRequest")
	@ResponsePayload  // to send xml back
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		CourseDetails details = new CourseDetails();
		if(request.getId()==1) {
			details.setId(request.getId());
			details.setName("Spring");
			details.setDescription("Welcome to Spring");
		}
		else {
			throw new CourseNotFoundException("Invalid course id: "+request.getId());
		}
		response.setCourseDetails(details);
		return response;

	}
}
