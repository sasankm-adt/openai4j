package ch.rasc.openai4j.moderation;

import feign.Headers;
import feign.RequestLine;

public interface Moderations {
	/**
	 * Classifies if text violates OpenAI's Content Policy
	 * 
	 * @return A moderation object.
	 */
	@RequestLine("POST /moderations")
	@Headers("Content-Type: application/json")
	ModerationResponse moderations(ModerationRequest request);
}
