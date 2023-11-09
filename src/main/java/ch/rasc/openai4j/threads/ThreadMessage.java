package ch.rasc.openai4j.threads;

import java.util.List;
import java.util.Map;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.openai4j.Nullable;

@Value.Immutable
@Value.Style(visibility = ImplementationVisibility.PACKAGE)
@JsonSerialize(as = ImmutableThreadMessage.class)
@JsonInclude(Include.NON_EMPTY)
public interface ThreadMessage {
	/**
	 * The role of the entity that is creating the message. Currently only user is
	 * supported.
	 */
	@Value.Default
	default String role() {
		return "user";
	}

	/**
	 * The content of the message.
	 */
	String content();

	/**
	 * A list of File IDs that the message should use. There can be a maximum of 10 files
	 * attached to a message. Useful for tools like retrieval and code_interpreter that
	 * can access and use files.
	 */
	@Nullable
	@JsonProperty("file_ids")
	List<String> fileIds();

	/**
	 * Set of 16 key-value pairs that can be attached to an object. This can be useful for
	 * storing additional information about the object in a structured format. Keys can be
	 * a maximum of 64 characters long and values can be a maxium of 512 characters long.
	 */
	@Nullable
	Map<String, Object> metadata();

	static Builder builder() {
		return new Builder();
	}

	final class Builder extends ImmutableThreadMessage.Builder {
	}
}