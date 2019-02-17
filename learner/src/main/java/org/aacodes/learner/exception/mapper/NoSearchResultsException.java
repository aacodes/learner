package org.aacodes.learner.exception.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NO_CONTENT, reason="No search Results")
public class NoSearchResultsException extends RuntimeException {

}
