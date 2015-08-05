package se.skltp.cooperation.web.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	private MessageSource messageSource;

	@Autowired
	public DefaultExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handle(HttpMessageNotReadableException e) {
		log.warn("Returning HTTP 400 Bad Request", e);
	}

	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public
	@ResponseBody
	ProblemDetail handleTypeMismatch(HttpServletRequest request, TypeMismatchException e) throws Exception {
		ProblemDetail error = new ProblemDetail();
		buildErrorMessage(request, e, HttpStatus.BAD_REQUEST, error);
		error.setDetail("Argument with the value " + e.getValue() + " is not valid");

		return error;

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ProblemDetail handleResourceNotFound(HttpServletRequest request, ResourceNotFoundException e) {
		ProblemDetail error = new ProblemDetail();
		buildErrorMessage(request, e, HttpStatus.NOT_FOUND, error);

		return error;
	}


	@ExceptionHandler(Exception.class)
	public void handleException(Exception e) throws Exception {
		log.error("An error was caught by the exception handler.", e);
		throw e;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ProblemDetail processValidationError(HttpServletRequest request, MethodArgumentNotValidException e) {
		log.debug("Handling form validation error");

		ValidationError error = new ValidationError();
		buildErrorMessage(request, e, HttpStatus.BAD_REQUEST, error);

		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		for (FieldError fieldError : fieldErrors) {
			log.debug("Adding error message: {} to field: {}", fieldError.getDefaultMessage(), fieldError.getField());
			error.addFieldError(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
		}

		return error;
	}

	private void buildErrorMessage(HttpServletRequest request, Exception e, HttpStatus status, ProblemDetail error) {
		try {
			error.setType(new URI("http://httpstatus.es/" + status.value()));
		} catch (URISyntaxException e1) {
			log.error("Unable to set error type", e);
		}
		error.setTitle(status.getReasonPhrase());
		error.setStatus(status.value());
		error.setDetail(e.getMessage());
		String url = request.getRequestURL().toString();
		if (request.getQueryString() != null) {
			url = url + "?" + request.getQueryString();
		}
		error.setInstance(url);
	}
}
